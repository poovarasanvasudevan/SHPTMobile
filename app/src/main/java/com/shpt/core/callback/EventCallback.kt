package com.shpt.core.callback

import android.app.Activity
import com.flipkart.android.proteus.ProteusContext
import com.flipkart.android.proteus.ProteusLayoutInflater
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.toolbox.EventType
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.flipkart.android.proteus.value.Value
import com.mcxiaoke.koi.ext.longToast
import com.mcxiaoke.koi.ext.toast

/**
 * Created by poovarasanv on 20/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 20/1/17 at 2:40 PM
 */

class EventCallback(activity: Activity) : ProteusLayoutInflater.Callback {
    private var act = activity
    override fun onUnknownViewType(context: ProteusContext?, type: String?, layout: Layout?, data: ObjectValue?, index: Int): ProteusView {
        return null!!;
    }

    override fun onEvent(view: ProteusView?, eventType: EventType?, value: Value?) {
        when (eventType) {
            EventType.OnClick -> {

                act.toast(value!!.asString)
//                val action = value.isObject
//                val actionItems = action.get("event");
//
//                actionItems.forEach {
//                    val task: JsonObject = it.asJsonObject
//
//                    val className = Class.forName("com.shpt.core.event.${task.get("event").asString}")
//                    val obj = className.newInstance()
//
//                    val beforeExecute = className.getDeclaredMethod("beforeExecute", Activity::class.java, JsonObject::class.java)
//                    val execute = className.getDeclaredMethod("execute", Activity::class.java, JsonObject::class.java)
//                    val afterExecute = className.getDeclaredMethod("afterExecute", Activity::class.java, JsonObject::class.java, JsonObject::class.java)
//
//
//                    val before: Boolean = beforeExecute.invoke(obj, act, task) as Boolean
//                    if (before) {
//                        val op: JsonObject = execute.invoke(obj, act, task) as JsonObject
//                        afterExecute.invoke(obj, act, task, op)
//                    }
//                }
            }
            else -> {
                act.longToast("Event Type Not Registered")
            }
        }
    }
}
