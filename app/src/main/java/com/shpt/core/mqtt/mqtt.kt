package com.shpt.core.mqtt

import android.util.Log
import com.mcxiaoke.koi.ext.isConnected
import com.poovarasan.deepstream.ConnectionState
import com.shpt.core.config.CONTEXT
import com.shpt.core.config.DEEPSTREAM
import com.shpt.core.config.MQTT_OBJ
import logMessage
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttException


/**
 * Created by poovarasanv on 27/3/17.
 * @author poovarasanv
 * @project SHPT
 * @on 27/3/17 at 11:44 AM
 */
fun connectMqtt() {
	try {
		if (!MQTT_OBJ.isConnected) {
			if (CONTEXT.isConnected()) {
				val token = MQTT_OBJ.connect(MQTT.connectionOptions)
				token.actionCallback = object : IMqttActionListener {
					override fun onSuccess(asyncActionToken: IMqttToken) {
						Log.d("Application", "onSuccess")
						MQTT_OBJ.subscribe("foo/bar", 2)
					}
					
					override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
						Log.d("Application", "onFailure")
					}
				}
			}
		}
	} catch (e: MqttException) {
		logMessage(e.localizedMessage)
	}
}

fun connectDeepstream() {
	try {
		if (DEEPSTREAM.connectionState != ConnectionState.OPEN) {
			if (CONTEXT.isConnected()) {
				val result = DEEPSTREAM.login()
				if (result.loggedIn()) {
					logMessage("Log in success!")
				}
			}
		}
		
	} catch (e: Exception) {
		logMessage(e.localizedMessage)
	}
}