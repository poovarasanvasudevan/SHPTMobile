package com.shpt.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.toast
import com.poovarasan.androidverify.Form
import com.poovarasan.androidverify.InputValidator
import com.shpt.R
import com.shpt.core.app.BaseActivity
import com.shpt.core.config.LAYOUT_BUILDER_FACTORY
import com.shpt.core.config.PARSER
import com.shpt.core.config.REST
import com.shpt.core.config.STYLES
import com.shpt.core.data.Constant
import com.shpt.core.ext.getArray
import com.shpt.core.ext.getString
import com.shpt.core.getLayout
import com.shpt.core.models.Layout
import com.shpt.core.serviceevent.RetryServiceEvent
import com.shpt.core.setUpEssential
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.uiThread


class FormActivity : BaseActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		try {
			
			val parcel = PARSER.parse(intent.getStringExtra(Constant.PARCEL)).asJsonObject;
			val formName = parcel.get("formName").asString;
			val formBuilder = Form.Builder(this)
			formBuilder.showErrors(true)
			
			async(context = UI) {
				
				val jsonLayout: Layout = bg {
					getLayout("forms")!!
				}.await()
				
				
				if (PARSER.parse(jsonLayout.structure).asJsonObject.has(formName)) {
					
					val form = PARSER.parse(jsonLayout.structure).asJsonObject.getAsJsonObject(formName)
					val layout = form.getAsJsonObject("main");
					val data = if (form.has("data")) form.getAsJsonObject("data") else JsonObject()
					val validator = if (form.has("validator")) form.getAsJsonObject("validator") else JsonObject()
					val configuration = if (form.has("configuration")) form.getAsJsonObject("configuration") else JsonObject()
					
					
					val layoutBuilder = LAYOUT_BUILDER_FACTORY
					mainLayout.removeAllViews()
					
					
					val view = layoutBuilder.build(mainLayout, layout, data, 0, STYLES.await())
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
										
										
										var componentView: EditText? = null
										if (view.findViewById(layoutBuilder.getUniqueViewId(componentId)) is EditText) {
											componentView = view.findViewById(layoutBuilder.getUniqueViewId(componentId)) as EditText
										}
										
										if (it.asJsonObject.has("creteria") && view.findViewById(layoutBuilder.getUniqueViewId(componentId)) is EditText) {
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
							
							findViewById(validatorButton).setOnClickListener {
								if (minForm.isValid) {
									if (configuration.has("formSubmitURL")) {
										val formSubmitURL = configuration.get("formSubmitURL").asString
										val formTagParams = configuration.get("formTagParams").asJsonArray
										
										val paramsMap: MutableMap<String, String> = mutableMapOf();
										
										formTagParams.forEach {
											val id = it.asString.replace("@", "").trim()
											val element = findViewById(layoutBuilder.getUniqueViewId(id)) as EditText
											paramsMap.put(id, element.text.toString())
										}
										
										if (configuration.has("attachParams")) {
											val attachParams = configuration.get("attachParams").asJsonArray
											attachParams.forEach {
												paramsMap.put(it.asJsonObject.getString("key"), it.asJsonObject.getString("value"))
											}
										}
										
										val pd = indeterminateProgressDialog(if (configuration.has("progressMessage")) configuration.getString("progressMessage") else "Loading Please wait...")
										pd.setCanceledOnTouchOutside(if (configuration.has("cancelable")) configuration.get("cancelable").asBoolean else true)
										pd.show()
										
										if (formSubmitURL.isNotEmpty() && formSubmitURL.isNotBlank()) {
											
											doAsync {
												val executedText: String = REST.submitForm(formSubmitURL, params = paramsMap).execute().body().string()
												uiThread {
													if (pd.isShowing) {
														pd.dismiss();
													}
													toast(executedText)
												}
											}
										}
										
										toast(" SIZE ==> ${paramsMap.size}")
									}
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
		} catch (e: Exception) {
			toast(e.cause.toString())
		}
		
	}
	
	override fun onStart() {
		super.onStart()
	}
	
	override fun onStop() {
		super.onStop()
	}
	
	@Subscribe fun retryServiceEvent(event: RetryServiceEvent) {
		find<TextView>(R.id.statusText).text = event.message
	}
}
