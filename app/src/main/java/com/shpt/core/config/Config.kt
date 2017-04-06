package com.shpt.core.config

/**
 * Created by poovarasanv on 6/2/17.
 * @author poovarasanv
 * @project SHPT
 * @on 6/2/17 at 9:09 AM
 */
object Config {
    val COOKIE: String = "shpt_login_cookie"
    val BASE: String = "http://localhost:8092/"

    val IMAGE_URL = "${BASE}image/cache/"
    val LOGIN_URL = "$BASE?route=account/login"
    val GET_PRODUCT = "$BASE?route=mobile/api/product"
    val SEARCH_PRODUCT = "$BASE?route=product/search/ajax"
    val ADD_TO_CART = "$BASE?route=checkout/cart/add"

    val LAYOUT_BASE = "http://localhost:8081/api/v1/layout"

    val WEB_USER_AGENT = "Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17"
    val MQTT_SERVER = "tcp://localhost:1883"
    val MY_APP_ID = "myAppId"
    var SERVER = "http://localhost:1337/parse"
    var CLIENT_KEY = "2ead5328dda34e688816040a0e78948a"
    var TEST_MODE = true
	
	
	var UPDATE_CHECKER = "http://carreto.pt/tools/android-store-version/"

//    var CIVICRM_URL = config("CIVICRM_URL")
}