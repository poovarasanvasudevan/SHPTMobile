
import android.content.Context
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.shpt.core.api.rest
import com.shpt.core.config.Config


fun Context.readJson(): JsonObject {
    val layoutString: String = rest.getLayout(Config.LAYOUT_BASE).execute().body().string()

    val parser = JsonParser()
    return parser.parse(layoutString).asJsonObject
}

fun Context.readProduct(productId: Int): JsonObject {
    val productDetail: String = rest.getProductDetail(Config.GET_PRODUCT, productId).execute().body().string()
    val parser = JsonParser()
    return parser.parse(productDetail).asJsonObject

}