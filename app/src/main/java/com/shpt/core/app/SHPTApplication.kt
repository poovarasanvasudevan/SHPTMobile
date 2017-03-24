package com.shpt.core.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import com.birbit.android.jobqueue.log.CustomLogger
import com.shpt.core.mqtt.MQTT
import com.squareup.otto.Bus
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

    companion object {
        lateinit var instance: SHPTApplication
            private set
        lateinit var jobinstance: JobManager
            private set
        lateinit var context: Context
            private set
        lateinit var bus: Bus
            private set
    }


    override fun onCreate() {
        super.onCreate()

        instance = this
        jobinstance = JobManager(configureJobManager())
        context = this.applicationContext
        bus = Bus()

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

    private fun configureJobManager(): Configuration {
        val configuration = Configuration.Builder(this)
                .customLogger(object : CustomLogger {
                    override fun v(text: String, vararg args: Any?) {
                        Log.d(TAG, String.format(text, *args))
                    }

                    private val TAG = "JOBS"
                    override fun isDebugEnabled(): Boolean {
                        return true
                    }

                    override fun d(text: String, vararg args: Any) {
                        Log.d(TAG, String.format(text, *args))
                    }

                    override fun e(t: Throwable, text: String, vararg args: Any) {
                        Log.e(TAG, String.format(text, *args), t)
                    }

                    override fun e(text: String, vararg args: Any) {
                        Log.e(TAG, String.format(text, *args))
                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .build()

        return configuration;
    }
}

