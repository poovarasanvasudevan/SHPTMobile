package com.shpt.core.ext

import com.google.gson.JsonArray
import com.google.gson.JsonObject

/**
 * Created by poovarasanv on 31/3/17.
 * @author poovarasanv
 * @project SHPT
 * @on 31/3/17 at 2:01 PM
 */


fun JsonObject.getString(pattern: String): String {
	
	return this.get(pattern).asString
}

fun JsonObject.getInt(pattern: String): Int {
	return this.get(pattern).asInt
}

fun JsonObject.getArray(pattern: String): JsonArray {
	return this.getAsJsonArray(pattern).asJsonArray
}