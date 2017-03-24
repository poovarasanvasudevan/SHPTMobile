package com.shpt.job

import android.text.Html
import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.toast
import com.shpt.core.api.rest
import com.shpt.core.config.Config
import com.shpt.core.config.PARSER
import logMessage

/**
 * Created by poovarasanv on 23/3/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 23/3/17 at 4:45 PM
 */

class AddToCartJob(params: Params, val productId: Int) : Job(params) {

    override fun onRun() {
        val response: String = applicationContext.rest.addToCart(
                url = Config.ADD_TO_CART,
                productid = productId,
                quantity = 1
        ).execute().body().string()

        val jsonResponse: JsonObject = PARSER.parse(response).asJsonObject
        if (jsonResponse.has("success")) {
            applicationContext.toast(Html.fromHtml(jsonResponse.get("success").asString))
        } else if (jsonResponse.has("failure")) {
            applicationContext.toast(Html.fromHtml(jsonResponse.get("failure").asString))
        }
    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        logMessage(throwable.localizedMessage)

        return RetryConstraint.RETRY
    }

    override fun onAdded() {
        logMessage("Add to Cart Job Added")
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {

    }

}
