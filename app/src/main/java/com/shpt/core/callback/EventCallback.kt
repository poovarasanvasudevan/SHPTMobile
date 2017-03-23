package com.shpt.core.callback

import android.app.Activity
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
import org.jetbrains.anko.longToast

/**
 * Created by poovarasanv on 20/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 20/1/17 at 2:40 PM
 */

class EventCallback(activity: Activity) : LayoutBuilderCallback {

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
        when (eventType) {
            EventType.OnClick -> {
                val action = value.asJsonObject
                val actionItems = action.getAsJsonArray("event");

                actionItems.forEach {
                    val task: JsonObject = it.asJsonObject

                    val className = Class.forName("com.shpt.core.event.${task.get("event").asString}")
                    val obj = className.newInstance()

                    val beforeExecute = className.getDeclaredMethod("beforeExecute", Activity::class.java, JsonObject::class.java)
                    val execute = className.getDeclaredMethod("execute", Activity::class.java, JsonObject::class.java)
                    val afterExecute = className.getDeclaredMethod("afterExecute", Activity::class.java, JsonObject::class.java, JsonObject::class.java)


                    val before: Boolean = beforeExecute.invoke(obj, act, task) as Boolean
                    if (before) {
                        val op: JsonObject = execute.invoke(obj, act, task) as JsonObject
                        afterExecute.invoke(obj, act, task, op)
                    }
                }
            }
            else -> {
                act.longToast("Event Type Not Registered")
            }
        }
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
