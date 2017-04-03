package com.shpt.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.toast
import com.poovarasan.blade.toolbox.Styles
import com.shpt.R
import com.shpt.core.config.LAYOUT_BUILDER_FACTORY
import com.shpt.core.config.PARSER
import com.shpt.core.getLayout
import com.shpt.core.getStyles
import com.shpt.core.models.Layout
import com.shpt.core.serviceevent.RetryServiceEvent
import com.shpt.core.setUpEssential
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.coroutines.experimental.bg


class Login : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		try {
			
			async(context = UI) {
				val jsonLayout: Layout = bg {
					getLayout("login")!!
				}.await()
				
				
				val styles: Styles = bg {
					getStyles()!!
				}.await()
				
				
				val layoutBuilder = LAYOUT_BUILDER_FACTORY
				mainLayout.removeAllViews()
				
				
				val view = layoutBuilder.build(
					mainLayout,
					PARSER.parse(jsonLayout.structure).asJsonObject.getAsJsonObject("main"),
					JsonObject()
					,
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
		} catch (e: Exception) {
			toast(e.cause.toString())
		}
		
	}
	
	@Subscribe fun retryServiceEvent(event: RetryServiceEvent) {
		find<TextView>(R.id.statusText).text = event.message
	}
}
