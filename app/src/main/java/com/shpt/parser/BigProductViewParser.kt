package com.shpt.parser

import android.view.ViewGroup
import com.flipkart.android.proteus.ProteusContext
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.ViewTypeParser
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.shpt.uiext.SHPTBigProductView
import com.shpt.widget.BigProductView


/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class BigProductViewParser : ViewTypeParser<BigProductView>() {
    override fun addAttributeProcessors() {
        addAttributeProcessor("productid", object : StringAttributeProcessor<BigProductView>() {
            override fun setString(view: BigProductView?, value: String?) {
                view!!.init(view.context, value!!.toInt())
            }

        })
    }

    override fun getParentType(): String? {
        return "View"
    }

    override fun getType(): String {
        return "BigProductView"
    }

    override fun createView(context: ProteusContext, layout: Layout, data: ObjectValue, parent: ViewGroup?, dataIndex: Int): ProteusView {
        return SHPTBigProductView(context.applicationContext)
    }

}
