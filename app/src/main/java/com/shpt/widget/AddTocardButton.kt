package com.shpt.widget

import android.content.Context
import android.util.AttributeSet
import com.mcxiaoke.koi.ext.toast
import com.mikepenz.iconics.view.IconicsButton
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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
                doAsync {
                    //do Add to cart logic

                    uiThread {
                        toast("Product Added to Cart")
                    }
                }
            }
        }
    }
}
