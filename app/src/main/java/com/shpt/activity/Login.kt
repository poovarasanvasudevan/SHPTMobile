package com.shpt.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.toast
import com.poovarasan.blade.toolbox.Styles
import com.shpt.R
import com.shpt.core.app.BaseActivity
import com.shpt.core.config.LAYOUT_BUILDER_FACTORY
import com.shpt.core.config.PARSER
import com.shpt.core.getGlobalData
import com.shpt.core.getLayout
import com.shpt.core.getStyles
import com.shpt.core.models.Layout
import com.shpt.core.serviceevent.RetryServiceEvent
import com.shpt.core.setUpEssential
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class Login : BaseActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		
		try {
			
			doAsync {
				
				//fetching Json
				val jsonLayout: Layout = getLayout("login")!!
				val styles: Styles = getStyles()!!
				val data = getGlobalData()
				uiThread {
					super.init(jsonLayout)
					
					val parser = PARSER.parse(jsonLayout.structure).asJsonObject
					val layoutBuilder = LAYOUT_BUILDER_FACTORY
					mainLayout.removeAllViews()
					
					data.add("local", if (parser.has("data")) parser.getAsJsonObject("data") else JsonObject())
					val view = layoutBuilder.build(
						mainLayout,
						parser.getAsJsonObject("main"),
						data,
						0,
						styles)
					
					mainLayout.addView(view as View)
					
					
					setUpEssential(
						layoutBuilder,
						view,
						PARSER.parse(jsonLayout.structure).asJsonObject.getAsJsonObject("main"),
						JsonObject(),
						this@Login
					)
				}
			}
		} catch (e: Exception) {
			toast(e.cause.toString())
		}
		
	}
	
	
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		return super.onCreateOptionsMenu(menu)
	}
	
	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		return super.onOptionsItemSelected(item)
	}
	
	override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
		return super.onPrepareOptionsMenu(menu)
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
