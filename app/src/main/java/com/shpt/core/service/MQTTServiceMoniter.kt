package com.shpt.core.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mcxiaoke.koi.ext.isConnected
import com.shpt.core.mqtt.connectMqtt

/**
 * Created by poovarasanv on 27/3/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 27/3/17 at 11:42 AM
 */

class MQTTServiceMoniter : BroadcastReceiver() {
	override fun onReceive(context: Context, intent: Intent) {
		if (context.isConnected()) {
			connectMqtt()
		}
	}
}
