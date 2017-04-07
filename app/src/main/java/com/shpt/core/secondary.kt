package com.shpt.core

import android.app.Activity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.poovarasan.blade.EventType
import com.shpt.core.config.JPATH
import com.shpt.core.event.fireEvent
import org.jetbrains.anko.searchManager


/**
 * Created by poovarasanv on 8/2/17.
 * @author poovarasanv
 * @project SHPT
 * @on 8/2/17 at 11:52 AM
 */


fun handleMenu(json: JsonObject, menu: Menu, sidebarMenuObj: Menu?, act: Activity) {
	
	//AppDataParser.printJson2(json)
	
	if (json.has("toolbar")) {
		val menuItems = JPATH.parse(json).read<JsonArray>("$.toolbar.children")
		menuItems.forEach {
			
			val currentElement: JsonElement = it
			
			var searchable = false
			val menuItem = menu.add(if (it.asJsonObject.has("title")) it.asJsonObject.get("title").asString else "Default Title")
			
			
			
			if (it.asJsonObject.has("showaction")) {
				when (it.asJsonObject.get("showaction").asString) {
					"always"  -> menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
					"if_room" -> menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
					"never"   -> menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
					else      -> menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
				}
			} else {
				menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)
			}
			if (it.asJsonObject.has("visibility")) {
				if (it.asJsonObject.get("visibility").asString != "visible") {
					menuItem.isVisible = false
				}
			}
			
			if (it.asJsonObject.has("icon")) {
				if (it.asJsonObject.get("icon").asString != "") {
					menuItem.icon = act.getMenuIcon(it.asJsonObject.get("icon").asString)
				} else {
					menuItem.icon = act.getMenuIcon("gmd_error")
				}
			}
			
			if (it.asJsonObject.has("searchcomponent")) {
				if (it.asJsonObject.get("searchcomponent").asBoolean) {
					menuItem.actionView = SearchView(act)
					searchable = true
				}
			}
			
			if (searchable) {
				val searchView = menuItem.actionView as SearchView
				searchView.setSearchableInfo(act.searchManager.getSearchableInfo(act.componentName))
				//searchView.setIconifiedByDefault(false)
				
			}
			
			if (it.asJsonObject.has("onClick")) {
				menuItem.setOnMenuItemClickListener {
					fireEvent(
						type = EventType.OnClick,
						values = currentElement.asJsonObject.get("onClick"),
						context = act
					)
					true
				}
			}
			
		}
	}
	if (json.has("sidebar") && sidebarMenuObj != null) {
		val menuItems = JPATH.parse(json).read<JsonArray>("$.sidebar.children")
		sidebarMenuObj.clear()
		menuItems.forEach {
			if (it.asJsonObject.has("children")) {
				val currentElement: JsonElement = it
				
				val submenu = sidebarMenuObj.addSubMenu(if (it.asJsonObject.has("title")) it.asJsonObject.get("title").asString else "Default Title")
				val children = it.asJsonObject.getAsJsonArray("children")
				
				
				children.forEach {
					val menuItem = submenu!!.add(if (it.asJsonObject.has("title")) it.asJsonObject.get("title").asString else "Default Title")
					
					if (it.asJsonObject.has("visibility")) {
						if (it.asJsonObject.get("visibility").asString != "visible") {
							menuItem.isVisible = false
						}
					}
					
					if (it.asJsonObject.has("icon")) {
						if (it.asJsonObject.get("icon").asString != "") {
							menuItem.icon = act.getMenuIcon(it.asJsonObject.get("icon").asString)
						} else {
							menuItem.icon = act.getMenuIcon("gmd_error")
						}
					}
					
					if (it.asJsonObject.has("onClick")) {
						menuItem.setOnMenuItemClickListener {
							
							fireEvent(
								type = EventType.OnClick,
								values = currentElement.asJsonObject.get("onClick"),
								context = act
							)
							true
						}
					}
				}
				
			} else {
				
				val menuItem = sidebarMenuObj.add(if (it.asJsonObject.has("title")) it.asJsonObject.get("title").asString else "Default Title")
				val currentElement: JsonElement = it
				
				if (it.asJsonObject.has("visibility")) {
					if (it.asJsonObject.get("visibility").asString != "visible") {
						menuItem.isVisible = false
					}
				}
				
				if (it.asJsonObject.has("icon")) {
					if (it.asJsonObject.get("icon").asString != "") {
						menuItem.icon = act.getMenuIcon(it.asJsonObject.get("icon").asString)
					} else {
						menuItem.icon = act.getMenuIcon("gmd_error")
					}
				}
				
				if (it.asJsonObject.has("onClick")) {
					menuItem.setOnMenuItemClickListener {
						fireEvent(
							type = EventType.OnClick,
							values = currentElement.asJsonObject.get("onClick"),
							context = act
						)
						true
					}
				}
			}
		}
	}
}
