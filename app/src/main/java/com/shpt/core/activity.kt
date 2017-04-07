package com.shpt.core

import android.app.Activity
import android.content.Context
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.ext.startActivity
import com.shpt.activity.Login
import com.shpt.activity.SRCMLogin
import com.shpt.core.config.CONTEXT
import com.shpt.core.config.DATABASE
import com.shpt.core.prefs.Prefs
import org.jetbrains.anko.activityManager
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import readJson
import java.io.UnsupportedEncodingException
import java.net.URL
import java.net.URLDecoder


/**
 * Created by poovarasanv on 19/1/17.
 * @author poovarasanv
 * @project SHPT
 * @on 19/1/17 at 11:00 AM
 */


fun Activity.goToSrcmLoginPage() = startActivity<SRCMLogin>()

fun Activity.goToLoginPage() = startActivity<Login>()
fun Context.isServiceRunning(serviceClass: Class<*>): Boolean = activityManager.getRunningServices(Integer.MAX_VALUE).any { serviceClass.name == it.service.className }
fun Context.handleConnectionError() {
	
	val alert1 = alert(Appcompat, "Not Connected to Internet. Retry again?") {
		
		positiveButton("Yes", {
			handleConnectionError()
		})
		
		negativeButton("No", {
			System.exit(0)
			this.build().dismiss()
		})
		
	}
	if (!isConnected()) alert1.show() else alert1.build().dismiss();
}


@Throws(UnsupportedEncodingException::class)
fun splitQuery(url: URL): Map<String, String> {
	val query_pairs = LinkedHashMap<String, String>()
	val query = url.getQuery()
	
	val pairs = query.split("&".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
	for (pair in pairs) {
		val idx = pair.indexOf("=")
		query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"))
	}
	return query_pairs
}


fun updateKernel(): JsonObject {
	val returnJson = CONTEXT.readJson()
	
	val jsonPage = returnJson.getAsJsonObject("pages")
	jsonPage.add("style", returnJson.getAsJsonObject("style"))
	jsonPage.add("global_data", returnJson.getAsJsonObject("global_data"))
	
	val config = returnJson.getAsJsonObject("config")
	DATABASE.use {
		delete("Layout")
		delete("Settings")
		
		for ((key) in jsonPage.entrySet()) {
			insert("Layout", "page" to key, "structure" to jsonPage.getAsJsonObject(key).toString())
		}
		
		for ((key) in config.entrySet()) {
			insert("Settings", "settinggroup" to "config", "settingkey" to key, "settingvalue" to config.get(key).asString)
		}
	}
	
	for ((key) in config.entrySet()) {
		Prefs.with(CONTEXT)
			.write(key, config.get(key).asString)
	}
	
	return returnJson
}