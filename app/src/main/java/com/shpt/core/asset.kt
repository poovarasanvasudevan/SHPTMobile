import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import com.google.gson.JsonObject
import com.google.gson.JsonParser

import com.shpt.core.config.Config
import com.shpt.core.config.PARSER
import com.shpt.core.config.REST
import java.io.IOException


fun Context.readJson(): JsonObject {
	//val layoutString: String = REST.getLayout(Config.LAYOUT_BASE).execute().body().string()
	val layoutString: String = loadJSONFromAsset()!!
	val parseObj = PARSER.parse(layoutString).asJsonObject
	return parseObj
}


fun Context.loadJSONFromAsset(): String? {
	var json: String? = null
	try {
		val iis = this.getAssets().open("layout/login_bak.json")
		val size = iis.available()
		val buffer = ByteArray(size)
		iis.read(buffer)
		iis.close()
		json = String(buffer)
	} catch (ex: IOException) {
		ex.printStackTrace()
		return null
	}
	
	return json
}

fun Context.readProduct(productId: Int): JsonObject {
	val productDetail: String = REST.getProductDetail(Config.GET_PRODUCT, productId).execute().body().string()
	val parser = JsonParser()
	return parser.parse(productDetail).asJsonObject
	
}

fun Context.color(@ColorRes color: Int): Int {
	return ContextCompat.getColor(this, color)
}