package com.shpt.core

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import com.google.gson.JsonParser
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.ext.startActivity
import com.shpt.activity.Login
import com.shpt.activity.SRCMLogin
import org.jetbrains.anko.activityManager
import org.jetbrains.anko.alert
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

    val alert1 = alert("Not Connected to Internet. Retry again?") {

        positiveButton("Yes", {
            handleConnectionError()
        })

        negativeButton("No", DialogInterface::dismiss)

    }
    if (!isConnected()) alert1.show() else alert1.dismiss()
}

val parser: JsonParser
    get() = JsonParser()

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