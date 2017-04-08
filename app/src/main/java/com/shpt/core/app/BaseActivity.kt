package com.shpt.core.app

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.getActivity
import com.mcxiaoke.koi.ext.toast
import com.shpt.R
import com.shpt.core.config.BUS
import com.shpt.core.config.PARSER
import com.shpt.core.handleConnectionError
import com.shpt.core.handleMenu
import com.shpt.core.models.Layout
import com.shpt.core.serviceevent.ConnectionServiceEvent
import com.shpt.core.serviceevent.NotificationEvent
import org.greenrobot.eventbus.Subscribe

/**
 * Created by poovarasanv on 3/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 3/4/17 at 6:06 PM
 */

open class BaseActivity : AppCompatActivity() {
	
	
	var layoutJson: Layout? = null
	var menuJson: JsonObject? = null
	var sidebarMenu: Menu? = null
	
	fun act(): Activity {
		return getActivity()
	}
	
	fun init(layout: Layout) {
		layoutJson = layout
		
		if (PARSER.parse(layout.structure).asJsonObject.has("menu")) {
			menuJson = PARSER.parse(layout.structure).asJsonObject.getAsJsonObject("menu")
			invalidateOptionsMenu()
		} else {
			menuJson = JsonObject()
		}
	}
	
	fun init(layout: JsonObject) {
		if (layout.has("menu")) {
			menuJson = layout.getAsJsonObject("menu")
			invalidateOptionsMenu()
		} else {
			menuJson = JsonObject()
		}
	}
	
	fun sidebar(menu: Menu) {
		this.sidebarMenu = menu
	}
	
	fun refreshMenu(layout: Layout) {
		
		if (PARSER.parse(layout.structure).asJsonObject.has("menu")) {
			menuJson = PARSER.parse(layout.structure).asJsonObject.getAsJsonObject("menu")
			invalidateOptionsMenu()
		} else {
			menuJson = JsonObject()
		}
	}
	
	override fun onResume() {
		//invalidateOptionsMenu()
		super.onResume()
	}
	
	
	override fun onStart() {
		super.onStart()
		BUS.register(this);
	}
	
	override fun onStop() {
		BUS.unregister(this);
		super.onStop()
	}
	
	@Subscribe public fun notificationEvent(event: NotificationEvent) {
		toast(event.message)
	}
	
	@Subscribe public fun connectionStatus(event: ConnectionServiceEvent) {
		handleConnectionError()
	}
	
	override fun onBackPressed() {
		super.onBackPressed()
	}
	
	
	override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//		menuInflater.inflate(R.menu.default_menu, menu)
//		if (menu != null && menuJson != null) {
//			menu.clear()
//			handleMenu(menuJson!!, menu, if (sidebarMenu == null) null else sidebarMenu, this)
//		}
		return super.onPrepareOptionsMenu(menu)
	}
	
	
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.default_menu, menu)
		if (menu != null && menuJson != null) {
			menu.clear()
			handleMenu(menuJson!!, menu, if (sidebarMenu == null) null else sidebarMenu, this)
		}
		return super.onCreateOptionsMenu(menu)
	}
	
	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		when (item!!.itemId) {
			android.R.id.home -> {
				supportFinishAfterTransition()
				return true
			}
		}
		
		return super.onOptionsItemSelected(item)
	}
}
