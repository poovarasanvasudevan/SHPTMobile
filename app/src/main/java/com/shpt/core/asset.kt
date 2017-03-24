import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import com.google.gson.JsonObject
import com.google.gson.JsonParser

import com.shpt.core.config.Config
import com.shpt.core.config.REST


fun Context.readJson(): JsonObject {
    val layoutString: String = REST.getLayout(Config.LAYOUT_BASE).execute().body().string()

    val parser = JsonParser()
    return parser.parse(layoutString).asJsonObject
}

fun Context.readProduct(productId: Int): JsonObject {
    val productDetail: String = REST.getProductDetail(Config.GET_PRODUCT, productId).execute().body().string()
    val parser = JsonParser()
    return parser.parse(productDetail).asJsonObject

}

fun Context.color(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}