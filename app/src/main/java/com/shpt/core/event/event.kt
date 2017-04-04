package com.shpt.core.event

import android.content.Context
import android.view.View
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.poovarasan.blade.EventType
import com.shpt.core.config.CONTEXT
import org.jetbrains.anko.longToast

/**
 * Created by poovarasanv on 4/4/17.
 * @author poovarasanv
 * @project SHPT
 * @on 4/4/17 at 2:22 PM
 */


fun fireEvent(view: View? = null, type: EventType, values: JsonElement, context: Context = CONTEXT) {
	when (type) {
		EventType.OnClick -> {
			val action = values.asJsonObject
			val actionItems = action.getAsJsonArray("event");
			
			actionItems.forEach {
				val task: JsonObject = it.asJsonObject
				
				val className = Class.forName("com.shpt.core.event.${task.get("event").asString}")
				val obj = className.newInstance()
				
				val beforeExecute = className.getDeclaredMethod("beforeExecute", Context::class.java, JsonObject::class.java)
				val execute = className.getDeclaredMethod("execute", Context::class.java, JsonObject::class.java)
				val afterExecute = className.getDeclaredMethod("afterExecute", Context::class.java, JsonObject::class.java, JsonObject::class.java)
				
				
				val before: Boolean = beforeExecute.invoke(obj, context, task) as Boolean
				if (before) {
					val op: JsonObject = execute.invoke(obj, context, task) as JsonObject
					afterExecute.invoke(obj, context, task, op)
				}
			}
		}
		else              -> {
			context.longToast("Event Type Not Registered")
		}
	}
}