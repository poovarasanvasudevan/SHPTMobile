package com.shpt.core.event

import android.app.Activity
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.toast
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by poovarasanv on 3/2/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 3/2/17 at 4:05 PM
 */

class MethodExecutionEvent:EventBase {
    override fun beforeExecute(act: Activity, params: JsonObject): Boolean {
        return true;
    }

    override fun afterExecute(act: Activity, params: JsonObject, output: JsonObject) {

    }

    override fun execute(act: Activity, task: JsonObject): JsonObject {
        try {
            doAsync {
                var className = Class.forName("${task.get("className").asString}")

                var obj = className.newInstance()
                var params = task.getAsJsonArray("params").asJsonArray
                var method = className.getDeclaredMethod("${task.get("methodName").asString}", Activity::class.java, JsonArray::class.java)

                uiThread {
                    method.invoke(obj, act, params);
                }

            }


        } catch (e: Exception) {
            act.toast("Oops : ${e.cause.toString()}")
        }

        return JsonObject()
    }

}
