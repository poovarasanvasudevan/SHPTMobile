package com.shpt.parser.data

import android.util.Log
import com.google.gson.JsonElement
import com.google.gson.JsonObject


/**
 * Created by poovarasanv on 7/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 7/4/17 at 6:13 PM
 */

class AppDataParser {
	
	companion object {
		fun printJson(jsonElement: JsonElement) {
			
			if (jsonElement.isJsonObject) {
				val ens = (jsonElement as JsonObject).entrySet()
				if (ens != null) {
					for ((key, value) in ens) {
						Log.d("GSONPARSE KEY", key)
						printJson(value)
					}
				}
			} else if (jsonElement.isJsonArray) {
				val jarr = jsonElement.asJsonArray
				for (je in jarr) {
					printJson(je)
				}
			} else if (jsonElement.isJsonNull) {
				// print null
				Log.d("GSONPARSE", "null")
			} else if (jsonElement.isJsonPrimitive) {
				Log.d("GSONPARSE", jsonElement.asString)
			}
		}
	}
}
