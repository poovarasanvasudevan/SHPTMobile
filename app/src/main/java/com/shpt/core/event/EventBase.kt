package com.shpt.core.event

import android.app.Activity
import com.google.gson.JsonObject

/**
 * Created by poovarasanv on 3/2/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 3/2/17 at 3:57 PM
 */

interface EventBase {
    fun beforeExecute(act: Activity, params: JsonObject): Boolean
    fun afterExecute(act: Activity, params: JsonObject, output: JsonObject)
    fun execute(act: Activity, params: JsonObject): JsonObject
}
