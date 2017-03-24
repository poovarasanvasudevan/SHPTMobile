package com.shpt.core.event

import android.content.Context
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
    override fun beforeExecute(ctx: Context, params: JsonObject): Boolean {
        return true;
    }

    override fun afterExecute(ctx: Context, params: JsonObject, output: JsonObject) {

    }

    override fun execute(ctx: Context, task: JsonObject): JsonObject {
        try {
            doAsync {
                val className = Class.forName(task.get("className").asString)

                val obj = className.newInstance()
                val params = task.getAsJsonArray("params").asJsonArray
                val method = className.getDeclaredMethod(task.get("methodName").asString, Context::class.java, JsonArray::class.java)

                uiThread {
                    method.invoke(obj, ctx, params);
                }

            }


        } catch (e: Exception) {
            ctx.toast("Oops : ${e.cause.toString()}")
        }

        return JsonObject()
    }

}
