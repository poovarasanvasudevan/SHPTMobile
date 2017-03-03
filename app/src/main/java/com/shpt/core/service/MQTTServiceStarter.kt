package com.shpt.core.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.shpt.core.isServiceRunning
import org.eclipse.paho.android.service.MqttService

/**
 * Created by poovarasanv on 10/2/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 10/2/17 at 3:08 PM
 */

class MQTTServiceStarter : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (!context.isServiceRunning(MqttService::class.java)) {
            context.startService(Intent(context, MqttService::class.java))
        }
    }
}
