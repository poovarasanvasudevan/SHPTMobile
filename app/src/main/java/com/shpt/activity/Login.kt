package com.shpt.activity

import android.content.ComponentName
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.toast
import com.shpt.R
import com.shpt.core.app.BaseActivity
import com.shpt.core.config.JOB_SCHEDULER
import com.shpt.core.config.LAYOUT_BUILDER_FACTORY
import com.shpt.core.config.PARSER
import com.shpt.core.config.STYLES
import com.shpt.core.getLayout
import com.shpt.core.models.Layout
import com.shpt.core.serviceevent.RetryServiceEvent
import com.shpt.core.setUpEssential
import com.shpt.job.SHPTJobService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import me.tatarka.support.job.JobInfo
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.coroutines.experimental.bg


class Login : BaseActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		val job = JobInfo.Builder(123, ComponentName(applicationContext, SHPTJobService::class.java))
			.setPeriodic(2000)
			.setPersisted(true)
		
		JOB_SCHEDULER.schedule(job.build())
		
		
		try {
			
			async(context = UI) {
				
				//fetching Json
				val jsonLayout: Layout = bg {
					getLayout("login")!!
				}.await()
				
				super.init(jsonLayout)
				
				val layoutBuilder = LAYOUT_BUILDER_FACTORY
				mainLayout.removeAllViews()
				
				
				val view = layoutBuilder.build(
					mainLayout,
					PARSER.parse(jsonLayout.structure).asJsonObject.getAsJsonObject("main"),
					JsonObject()
					,
					0,
					STYLES.await())
				
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
