package com.shpt.parser

import android.view.ViewGroup
import com.flipkart.android.proteus.ProteusContext
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.ViewTypeParser
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.shpt.uiext.SHPTAddToCartButton
import com.shpt.widget.AddTocardButton


/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class AddToCartButtonParser() : ViewTypeParser<AddTocardButton>() {
    override fun createView(context: ProteusContext, layout: Layout, data: ObjectValue, parent: ViewGroup?, dataIndex: Int): ProteusView {
        return SHPTAddToCartButton(context.applicationContext);
    }

    override fun addAttributeProcessors() {

        addAttributeProcessor("productid", object : StringAttributeProcessor<AddTocardButton>() {
            override fun setString(view: AddTocardButton?, value: String?) {
                view!!.addToCartProduct(value!!.toInt())
            }
        })
    }

    override fun getType(): String {
        return "AddToCartButton"
    }

    override fun getParentType(): String? {
        return "View"
    }

}
