package com.shpt.core.formatter

import com.google.gson.JsonElement

import com.poovarasan.blade.toolbox.Formatter
import com.shpt.core.config.config

/**
 * Created by poovarasanv on 3/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 3/4/17 at 12:09 PM
 */

class SessionFormattor : Formatter() {
	override fun format(elementValue: JsonElement): String {
		return config(elementValue.asString)
	}
	
	override fun getName(): String {
		return "SESSION"
	}
}
