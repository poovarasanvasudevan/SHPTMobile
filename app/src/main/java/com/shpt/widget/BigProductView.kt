package com.shpt.widget

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.widget.LinearLayout
import com.shpt.R
import com.shpt.core.*
import com.shpt.core.util.TextDecorator
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import readProduct

/**
 * Created by poovarasanv on 1/3/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 1/3/17 at 11:31 AM
 */

open class BigProductView : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, product: Int) : super(context) {
        init(context, product)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    fun init(ctx: Context, productid: Int) {

        doAsync {
            val productJson = ctx.readProduct(productid)

            if (productJson.has("product_id")) {

                var productPrice = productJson.get("price").asString.split(".")[0]
                val specialPrice = if (productJson.get("special").asString != "") "₹" + productJson.get("special").asString.split(".")[0] else ""


                if (specialPrice != "") {
                    productPrice = "₹${productPrice}  ${specialPrice}"
                } else {
                    productPrice = "₹${productPrice}"
                }

                uiThread {
                    frameLayout {

                        lparams {
                            width = matchParent
                            height = dip(150)
                        }


                        cardView {
                            padding = dip(2)
                            ripple {

                                linearLayout {
                                    orientation = HORIZONTAL
                                    val productImage = fresco {
                                        setSHPTImageURL(productJson.get("image").asString, ImageSize.MEDIUM)

                                    }.lparams {
                                        width = dip(150)
                                        height = matchParent
                                        weight = 2f
                                    }

                                    val descLayout = relativeLayout {

                                        linearLayout {
                                            orientation = VERTICAL
                                            textView {
                                                text = productJson.get("name").asString
                                                textSize = dip(6).toFloat()
                                                textColor = Color.BLACK

                                            }.lparams {
                                                topMargin = dip(4)
                                                width = matchParent
                                                height = wrapContent
                                            }

                                            val currencyText = textView {
                                                text = productPrice
                                                textSize = dip(6).toFloat()
                                                textColor = Color.BLACK

                                            }.lparams {
                                                bottomMargin = dip(2)
                                                width = matchParent
                                                height = wrapContent
                                            }

                                            if (specialPrice != "") {
                                                TextDecorator.decorate(currencyText, productPrice)
                                                        .strikethrough(0, specialPrice.length + 1)
                                                        .setTextColor(R.color.md_red_500, 0, specialPrice.length + 1)
                                                        .setTextColor(R.color.md_green_500, specialPrice.length + 1, productPrice.length)
                                                        .build()
                                            } else {
                                                TextDecorator.decorate(currencyText, productPrice)
                                                        .setTextColor(R.color.md_green_500, 0, productPrice.length)
                                                        .build()
                                            }

                                            if (productJson.has("extras")) {
                                                textView {
                                                    text = productJson.get("extras").asString
                                                    textSize = dip(5).toFloat()
                                                    textColor = Color.BLACK
                                                }.lparams {
                                                    bottomMargin = dip(4)
                                                    width = matchParent
                                                    height = wrapContent
                                                }
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = matchParent
                                            above(R.id.bigProductViewDescLayout)
                                        }


                                        relativeLayout {

                                            id = R.id.bigProductViewDescLayout
                                            frameLayout {
                                                addToCart {
                                                    addToCartProduct(productid)

                                                    setBackgroundColor(ctx.getColor(R.color.md_green_500))
                                                    padding = dip(5)
                                                    text = "{gmd-add-shopping-cart}"
                                                    textColor = Color.WHITE
                                                    textSize = dip(8).toFloat()
                                                    isClickable = true
                                                }.lparams {
                                                    margin = dip(3)
                                                    width = matchParent
                                                    height = matchParent
                                                }
                                            }.lparams {
                                                alignParentRight()
                                                width = dip(45)
                                                height = dip(45)
                                            }
                                        }.lparams {
                                            alignParentBottom()
                                            width = wrapContent
                                            height = dip(45)
                                        }

                                    }.lparams {
                                        weight = 4f
                                        leftMargin = dip(4)
                                        width = matchParent
                                        height = matchParent
                                    }

                                    lparams(width = matchParent, height = matchParent)
                                }

                            }
                        }.lparams {
                            margin = dip(5)
                            width = matchParent
                            height = matchParent
                        }

                    }
                }
            }
        }
    }
}
