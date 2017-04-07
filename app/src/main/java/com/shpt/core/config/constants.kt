package com.shpt.core.config

import android.content.Context
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.Params
import com.google.gson.JsonParser
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.ParseContext
import com.poovarasan.blade.builder.DataParsingLayoutBuilder
import com.poovarasan.deepstream.DeepstreamClient
import com.shpt.core.api.getAdapter
import com.shpt.core.app.SHPTApplication
import com.shpt.core.db.DatabaseOpenHelper
import com.shpt.core.ext.PermissionHelper
import com.shpt.core.getLayoutBuilder
import com.shpt.core.prefs.Prefs
import com.shpt.core.rest.Rest
import com.shpt.job.Priority
import org.eclipse.paho.android.service.MqttAndroidClient
import org.greenrobot.eventbus.EventBus

/**
 * Created by poovarasanv on 24/3/17.
 * @author poovarasanv
 * @project SHPT
 * @on 24/3/17 at 9:01 AM
 */

fun config(key: String): String = Prefs
	.with(CONTEXT)
	.read(key)



val LAYOUT_BUILDER_FACTORY: DataParsingLayoutBuilder
	get() = getLayoutBuilder()

val PARSER: JsonParser
	get() = JsonParser()

val DATABASE: DatabaseOpenHelper
	get() = DatabaseOpenHelper.getInstance(CONTEXT)

val JOB_MANAGER: JobManager
	get() = SHPTApplication.jobinstance

val NETWORK_JOB_PARAMS: Params
	get() = Params(Priority.HIGH)
		.requireNetwork()
		.persist()
		.groupBy("high_priority")

val KERNEL_UPDATE_PARAMS: Params
	get() = Params(Priority.HIGH)
		.requireNetwork()
		.persist()
		.groupBy("kernel_update")
		.setPersistent(true)

val BUS: EventBus
	get() = EventBus.getDefault()

val CONTEXT: Context
	get() = SHPTApplication.context

val REST: Rest
	get() = CONTEXT.getAdapter()

val MQTT_OBJ: MqttAndroidClient
	get() = SHPTApplication.mqtt

val DEEPSTREAM: DeepstreamClient
	get() = SHPTApplication.deepstream


val PERMISSION_HELPER: PermissionHelper
	get() = SHPTApplication.permissionHelper

val JPATH: ParseContext
	get() = JsonPath.using(com.shpt.core.app.SHPTApplication.jpathConfig)