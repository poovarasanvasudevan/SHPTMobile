package com.shpt.core.event

import android.content.Context
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.toast
import logMessage

/**
 * Created by poovarasanv on 3/2/17.
 * @author poovarasanv
 * @project SHPT
 * @on 3/2/17 at 4:12 PM
 */

class ToastEvent : EventBase {
    override fun beforeExecute(ctx: Context, params: JsonObject): Boolean {

        return true
    }

    override fun afterExecute(ctx: Context, params: JsonObject, output: JsonObject) {

    }

    override fun execute(ctx: Context, params: JsonObject): JsonObject {

        if (params.has("text")) {
            ctx.toast(params.get("text").asString)
        } else {
            if (params.has("whattotoast")) {
                if (params.has(params.get("whattotoast").asString)) {
                    ctx.toast(params.get(params.get("whattotoast").asString).asString)
                } else {
                    logMessage(" ${params.get("whattotoast").asString} not found in params")
                }
            } else {
                logMessage("Nothing to toast")
            }
        }
        return JsonObject()
    }

}
