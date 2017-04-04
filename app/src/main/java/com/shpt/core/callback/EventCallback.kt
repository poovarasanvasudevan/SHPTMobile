package com.shpt.core.callback

import android.content.Context
import android.support.annotation.Nullable
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.View
import android.widget.Adapter
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.poovarasan.blade.EventType
import com.poovarasan.blade.builder.LayoutBuilderCallback
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.BladeView
import com.shpt.core.event.fireEvent

/**
 * Created by poovarasanv on 20/1/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 20/1/17 at 2:40 PM
 */

class EventCallback(activity: Context) : LayoutBuilderCallback {
	
	private var act = activity
	
	override fun onUnknownAttribute(attribute: String, value: JsonElement, view: BladeView) {
		Log.i("unknown-attribute", attribute + " in " + view.viewManager.layout.toString())
	}
	
	@Nullable
	override fun onUnknownViewType(type: String, parent: View, layout: JsonObject, data: JsonObject, index: Int, styles: Styles): BladeView? {
		return null
	}
	
	override fun onLayoutRequired(type: String, parent: BladeView): JsonObject? {
		return null
	}
	
	override fun onViewBuiltFromViewProvider(view: BladeView, parent: View, type: String, index: Int) {
		
	}
	
	override fun onEvent(view: BladeView, value: JsonElement, eventType: EventType): View {
		fireEvent(type = eventType, values = value, context = act)
		
		Log.d("event", value.toString())
		return view as View
	}
	
	override fun onPagerAdapterRequired(parent: BladeView, children: List<BladeView>, layout: JsonObject): PagerAdapter? {
		return null
	}
	
	override fun onAdapterRequired(parent: BladeView, children: List<BladeView>, layout: JsonObject): Adapter? {
		return null
	}
}
