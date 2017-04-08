package com.shpt.core.event

import android.app.Notification
import android.content.Context
import com.google.gson.JsonArray
import com.mcxiaoke.koi.ext.toast
import com.shpt.R
import com.shpt.core.config.NOTIFICATION

/**
 * Created by poovarasanv on 3/2/17.
 * @author poovarasanv
 * @project SHPT
 * @on 3/2/17 at 3:22 PM
 */

class CoreEvents {
	
	fun makeToast(act: Context, params: JsonArray) {
		act.toast(params.toString())
	}
	
	
	fun makeNotification(act: Context, params: JsonArray) {
		
		NOTIFICATION
			.load()
			.title(params.get(0).asString)
			.message(params.get(1).asString)
			.smallIcon(R.drawable.pugnotification_ic_launcher)
			.largeIcon(R.drawable.pugnotification_ic_launcher)
			.flags(Notification.DEFAULT_ALL)
			.simple()
			.build();
	}
}
