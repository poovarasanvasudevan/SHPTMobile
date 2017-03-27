package com.shpt.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.toast
import com.poovarasan.blade.toolbox.Styles
import com.shpt.R
import com.shpt.core.config.DATABASE
import com.shpt.core.config.LAYOUT_BUILDER_FACTORY
import com.shpt.core.models.Layout
import com.shpt.core.serviceevent.RetryServiceEvent
import com.shpt.core.setUpEssential
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class Login : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		try {
			doAsync {
				DATABASE.use {
					select("Layout").where("page = {pageName}", "pageName" to "login").exec {
						val rowParser = classParser<Layout>()
						val row = parseSingle(rowParser)
						
						
						uiThread {
							
							val layoutBuilder = LAYOUT_BUILDER_FACTORY
							mainLayout.removeAllViews()
							
							val parser = JsonParser()
							val view = layoutBuilder.build(mainLayout, parser.parse(row.structure).asJsonObject.getAsJsonObject("main"), JsonObject(), 0, Styles())
							
							mainLayout.addView(view as View)
							
							
							setUpEssential(
								layoutBuilder,
								view,
								parser.parse(row.structure).asJsonObject.getAsJsonObject("main"),
								JsonObject(),
								this@Login
							)
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
