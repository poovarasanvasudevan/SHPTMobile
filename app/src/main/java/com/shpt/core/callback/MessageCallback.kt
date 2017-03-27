package com.shpt.core.callback

import logMessage
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

/**
 * Created by poovarasanv on 27/3/17.
 * @author poovarasanv
 * @project SHPT
 * @on 27/3/17 at 11:20 AM
 */

class MessageCallback : MqttCallback {
	override fun connectionLost(cause: Throwable?) {
		if (cause != null) {
			logMessage(cause.localizedMessage)
		}
	}
	
	@Throws(Exception::class)
	override fun messageArrived(topic: String, message: MqttMessage) {
		logMessage("Message from : $topic was ${message.toString()}")
	}
	
	override fun deliveryComplete(token: IMqttDeliveryToken) {
		logMessage(token.message.toString())
	}
}
