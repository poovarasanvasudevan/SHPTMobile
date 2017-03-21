package com.shpt.parser

import android.support.design.widget.AppBarLayout
import android.view.ViewGroup
import com.flipkart.android.proteus.ProteusContext
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.ViewTypeParser
import com.flipkart.android.proteus.processor.DimensionAttributeProcessor
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.shpt.uiext.SHPTAppBar

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class AppBarParser : ViewTypeParser<AppBarLayout>() {
    override fun getType(): String {
        return "SHPTAppBar"
    }

    override fun getParentType(): String? {
        return "View"
    }

    override fun addAttributeProcessors() {
        addAttributeProcessor("elevation", object : DimensionAttributeProcessor<AppBarLayout>() {
            override fun setDimension(view: AppBarLayout?, dimension: Float) {
                view!!.elevation = dimension
            }
        })
    }

    override fun createView(context: ProteusContext, layout: Layout, data: ObjectValue, parent: ViewGroup?, dataIndex: Int): ProteusView {
        return SHPTAppBar(context.applicationContext)
    }

}
