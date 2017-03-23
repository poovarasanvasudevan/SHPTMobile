package com.shpt.widget

import android.content.Context
import android.util.AttributeSet
import com.mikepenz.iconics.view.IconicsButton
import com.shpt.core.app.jobManager
import com.shpt.core.app.networkJobParams
import com.shpt.job.AddToCartJob

/**
 * Created by poovarasanv on 2/3/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 2/3/17 at 11:25 AM
 */

open class AddTocardButton : IconicsButton {
    var product: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    fun addToCartProduct(productId: Int) {
        product = productId
    }

    fun init() {
        setOnClickListener {
            if (product != 0) {
                jobManager
                        .addJobInBackground(AddToCartJob(productId = product, params = networkJobParams), {
                            //need to implement callback of addtocart
                        })

            }
        }
    }
}
