package com.shpt.core.event

import android.app.Activity
import android.content.Intent
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.toast
import com.shpt.core.data.Constant

/**
 * Created by poovarasanv on 3/2/17.
 * @author poovarasanv
 * @project SHPT
 * @on 3/2/17 at 4:01 PM
 */

class ActivityGoToEvent : EventBase {
    override fun beforeExecute(act: Activity, params: JsonObject): Boolean {
        return true;
    }

    override fun afterExecute(act: Activity, params: JsonObject, output: JsonObject) {
    }

    override fun execute(act: Activity, params: JsonObject): JsonObject {
        try {
            val activityName: Class<Activity> = Class.forName("com.shpt.activity.${params.get("activity").asString}") as Class<Activity>

            val intent = Intent(act, activityName)
            if (params.has("data")) {
                intent.putExtra(Constant.PARCEL, params.get("data").toString())
            }
            act.startActivity(intent)
        } catch (e: Exception) {
            act.toast("Oops : ${e.cause.toString()}")
        }
        return JsonObject()
    }

}
