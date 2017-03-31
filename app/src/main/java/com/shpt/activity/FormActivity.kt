package com.shpt.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mcxiaoke.koi.ext.toast
import com.poovarasan.androidverify.Form
import com.poovarasan.androidverify.InputValidator
import com.poovarasan.blade.toolbox.Styles
import com.shpt.R
import com.shpt.core.config.DATABASE
import com.shpt.core.config.LAYOUT_BUILDER_FACTORY
import com.shpt.core.config.PARSER
import com.shpt.core.data.Constant
import com.shpt.core.ext.getArray
import com.shpt.core.ext.getString
import com.shpt.core.models.Layout
import com.shpt.core.serviceevent.RetryServiceEvent
import com.shpt.core.setUpEssential
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread


class FormActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		try {
			
			val parcel = PARSER.parse(intent.getStringExtra(Constant.PARCEL)).asJsonObject;
			val formName = parcel.get("formName").asString;
			val formBuilder = Form.Builder(this)
			formBuilder.showErrors(true)
			
			doAsync {
				DATABASE.use {
					select("Layout").where("page = {pageName}", "pageName" to "forms").exec {
						val rowParser = classParser<Layout>()
						val row = parseSingle(rowParser)
						
						
						uiThread {
							
							if (PARSER.parse(row.structure).asJsonObject.has(formName)) {
								val form = PARSER.parse(row.structure).asJsonObject.getAsJsonObject(formName)
								val layout = form.getAsJsonObject("main");
								val data = if (form.has("data")) form.getAsJsonObject("data") else JsonObject()
								val validator = if (form.has("validator")) form.getAsJsonObject("validator") else JsonObject()
								
								
								val layoutBuilder = LAYOUT_BUILDER_FACTORY
								mainLayout.removeAllViews()
								
								val parser = JsonParser()
								val view = layoutBuilder.build(mainLayout, layout, data, 0, Styles())
								mainLayout.addView(view as View)
								
								
								setUpEssential(
									layoutBuilder,
									view,
									layout,
									data,
									this@FormActivity
								)
								
								if (validator.has("validateOnClickButton")) {
									val validationButtonID = validator.getString("validateOnClickButton").replace("#", "").trim()
									
									val validatorButton: Int? = layoutBuilder.getUniqueViewId(validationButtonID)
									if (validatorButton != null && view.findViewById(validatorButton) != null) {
										//begin validation Costruction
										if (validator.has("validateOptions")) {
											val validateOptions = validator.getArray("validateOptions")
											
											
											validateOptions.forEach {
												val componentId = it.asJsonObject.getString("id").replace("#", "").trim()
												
												if (componentId != "" && layoutBuilder.getUniqueViewId(componentId) != null && view.findViewById(layoutBuilder.getUniqueViewId(componentId)) != null) {
													val componentView = view.findViewById(layoutBuilder.getUniqueViewId(componentId)) as EditText
													
													if (it.asJsonObject.has("creteria")) {
														val creteria = it.asJsonObject.getArray("creteria")
														
														val inputValidator = InputValidator.Builder(applicationContext)
														inputValidator.on(componentView)
														
														creteria.forEach {
															val type = it.asJsonObject.getString("type");
															
															when (type) {
																"REQUIRED"  -> {
																	inputValidator.required(true);
																	inputValidator.requiredMessage(it.asJsonObject.getString("errorMessage"))
																}
																"EMAIL"     -> {
																	inputValidator.validatorType(InputValidator.IS_EMAIL)
																	inputValidator.errorMessage(it.asJsonObject.getString("errorMessage"))
																}
																"MINLENGTH" -> {
																	inputValidator.minLength(it.asJsonObject.get("value").asInt)
																	inputValidator.errorMessage(it.asJsonObject.getString("errorMessage"))
																}
																"MAXLENGTH" -> {
																	inputValidator.maxLength(it.asJsonObject.get("value").asInt)
																	inputValidator.errorMessage(it.asJsonObject.getString("errorMessage"))
																}
																"PHONE"     -> {
																	inputValidator.validatorType(InputValidator.IS_PHONE_NUMBER)
																	inputValidator.errorMessage(it.asJsonObject.getString("errorMessage"))
																}
																"REGEX"     -> {
																	inputValidator.regex(it.asJsonObject.get("value").asString)
																	inputValidator.errorMessage(it.asJsonObject.getString("errorMessage"))
																}
																"MIN"       -> {
																	inputValidator.minValue(it.asJsonObject.get("value").asInt)
																	inputValidator.errorMessage(it.asJsonObject.getString("errorMessage"))
																}
																"MAX"       -> {
																	inputValidator.maxValue(it.asJsonObject.get("value").asInt)
																	inputValidator.errorMessage(it.asJsonObject.getString("errorMessage"))
																}
															}
														}
														formBuilder.addInputValidator(inputValidator.build())
													}
												}
											}
										}
										
										val minForm = formBuilder.build()
										
										view.findViewById(validatorButton).setOnClickListener {
											if (minForm.isValid) {
												//process
												toast("Valid Form")
											} else {
												toast("Invalid Form")
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (e: Exception) {
			toast(e.cause.toString())
		}
		
	}
	
	@Subscribe fun retryServiceEvent(event: RetryServiceEvent) {
		find<TextView>(R.id.statusText).text = event.message
	}
}
