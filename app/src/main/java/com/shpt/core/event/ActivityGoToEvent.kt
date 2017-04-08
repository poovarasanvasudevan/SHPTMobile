package com.shpt.core.event

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.toast
import com.shpt.R
import com.shpt.core.data.Constant

/**
 * Created by poovarasanv on 3/2/17.
 * @author poovarasanv
 * @project SHPT
 * @on 3/2/17 at 4:01 PM
 */

class ActivityGoToEvent : EventBase {
	override fun beforeExecute(ctx: Context, params: JsonObject): Boolean {
		return true;
	}
	
	override fun afterExecute(ctx: Context, params: JsonObject, output: JsonObject) {
	}
	
	override fun execute(ctx: Context, params: JsonObject): JsonObject {
		try {
			val activityName: Class<Activity> = Class.forName("com.shpt.activity.${params.get("activity").asString}") as Class<Activity>
			
			val intent = Intent(ctx, activityName)
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
			if (params.has("data")) {
				intent.putExtra(Constant.PARCEL, params.get("data").toString())
			}
			ctx.startActivity(intent)
			if (ctx is Activity)
				(ctx).overridePendingTransition(R.anim.fadein, R.anim.fadeout)
			
		} catch (e: Exception) {
			ctx.toast("Oops : ${e.cause.toString()}")
		}
		return JsonObject()
	}
	
}
