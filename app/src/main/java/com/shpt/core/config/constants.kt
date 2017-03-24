package com.shpt.core.config

import android.content.Context
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.Params
import com.google.gson.JsonParser
import com.poovarasan.blade.builder.DataParsingLayoutBuilder
import com.shpt.core.api.getAdapter
import com.shpt.core.app.SHPTApplication
import com.shpt.core.db.DatabaseOpenHelper
import com.shpt.core.getLayoutBuilder
import com.shpt.core.prefs.Prefs
import com.shpt.core.rest.Rest
import com.shpt.job.Priority
import com.squareup.otto.Bus

/**
 * Created by poovarasanv on 24/3/17.
 * @author poovarasanv
 * @project SHPT
 * @on 24/3/17 at 9:01 AM
 */

fun config(key: String): String = Prefs
        .with(CONTEXT)
        .read(key)

val CONFIG: Config
    get() = Config


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

val BUS: Bus
    get() = SHPTApplication.bus

val CONTEXT: Context
    get() = SHPTApplication.context

val REST: Rest
    get() = CONTEXT.getAdapter()