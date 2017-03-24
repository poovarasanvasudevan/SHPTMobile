package com.shpt.core.event

import android.content.Context
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
    fun beforeExecute(ctx: Context, params: JsonObject): Boolean
    fun afterExecute(ctx: Context, params: JsonObject, output: JsonObject)
    fun execute(ctx: Context, params: JsonObject): JsonObject
}
