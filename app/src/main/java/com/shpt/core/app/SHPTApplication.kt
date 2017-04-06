package com.shpt.core.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import com.birbit.android.jobqueue.log.CustomLogger
import com.jayway.jsonpath.Option
import com.jayway.jsonpath.spi.json.GsonJsonProvider
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider
import com.poovarasan.androidverify.App
import com.poovarasan.deepstream.DeepstreamClient
import com.shpt.core.ext.PermissionHelper
import com.shpt.core.mqtt.Deepstream
import com.shpt.core.mqtt.MQTT
import com.shpt.core.mqtt.connectMqtt
import org.eclipse.paho.android.service.MqttAndroidClient
import java.util.*


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
		
		lateinit var deepstream: DeepstreamClient
			private set
		
		lateinit var mqtt: MqttAndroidClient
			private set
		
		lateinit var permissionHelper: PermissionHelper
			private set
		
		lateinit var jpathConfig: com.jayway.jsonpath.Configuration
			private set
	}
	
	
	override fun onCreate() {
		super.onCreate()
		
		instance = this
		jobinstance = JobManager(configureJobManager())
		context = this.applicationContext
		mqtt = MQTT.getMQTTClient(applicationContext)!!
		deepstream = Deepstream.getClient()
		permissionHelper = PermissionHelper(this)
		jpathConfig = jPathConfiguration()
		
		App.setContext(this);
		connectMqtt()
		
	}
	
	private fun jPathConfiguration(): com.jayway.jsonpath.Configuration {
		val conf = com.jayway.jsonpath.Configuration.builder()
			.jsonProvider(GsonJsonProvider())
			.mappingProvider(GsonMappingProvider())
			.options(EnumSet.noneOf(Option::class.java))
			.build()
		
		
		return conf
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
			.minConsumerCount(1)    //always keep at least one consumer alive
			.maxConsumerCount(3)    //up to 3 consumers at a time
			.loadFactor(3)          //3 jobs per consumer
			.consumerKeepAlive(120) //wait 2 minute
			.build()
		
		return configuration;
	}
}

