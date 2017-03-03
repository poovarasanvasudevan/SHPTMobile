package com.shpt.core.app

import android.app.Application
import android.util.Log
import com.shpt.core.mqtt.MQTT
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttException


/**
 * Created by poovarasanv on 6/2/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 6/2/17 at 9:05 AM
 */

class SHPTApplication : Application() {


    override fun onCreate() {
        super.onCreate()
//
//        Parse.initialize(Parse.Configuration.Builder(this)
//                .applicationId(Config.MY_APP_ID)
//                .server(Config.SERVER)
//                .clientKey(null)
//                .enableLocalDataStore()
//                .build()
//        )
//
//        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE)
//        ParseInstallation.getCurrentInstallation().saveInBackground();
//


        try {

            if (!MQTT.getMQTTClient(applicationContext)!!.isConnected) {
                val token = MQTT.getMQTTClient(applicationContext)!!.connect(MQTT.connectionOptions)
                token.actionCallback = object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken) {
                        Log.d("Application", "onSuccess")
                    }

                    override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                        Log.d("Application", "onFailure")
                    }
                }
            }

        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

}
