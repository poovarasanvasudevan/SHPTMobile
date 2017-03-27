package com.shpt.core.mqtt

import android.content.Context
import com.shpt.core.callback.MessageCallback
import com.shpt.core.config.Config

import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttConnectOptions

/**
 * Created by poovarasanv on 16/2/17.
 * @author poovarasanv
 * @project SHPT
 * @on 16/2/17 at 10:50 AM
 */

object MQTT {

    internal var client: MqttAndroidClient? = null
    internal var token: IMqttToken? = null

    fun getMQTTClient(context: Context): MqttAndroidClient? {
        if (client == null) {
            client = MqttAndroidClient(context, Config.MQTT_SERVER, "poovarasan")
	        client!!.setCallback(MessageCallback())
        }

        return client
    }

    fun getMQTTToken(context: Context): IMqttToken? {
        if (token == null || !getMQTTClient(context)!!.isConnected) {
            token = getMQTTClient(context)!!.connect(connectionOptions)
        }

        return token
    }

    val connectionOptions: MqttConnectOptions
        get() {
            val options = MqttConnectOptions()
            options.isCleanSession = false

            return options
        }
}
