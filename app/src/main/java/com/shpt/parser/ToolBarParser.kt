package com.shpt.parser

import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import com.flipkart.android.proteus.ProteusContext
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.ViewTypeParser
import com.flipkart.android.proteus.processor.DimensionAttributeProcessor
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.shpt.uiext.SHPTToolBar

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class ToolBarParser : ViewTypeParser<Toolbar>() {
    override fun addAttributeProcessors() {
        addAttributeProcessor("title", object : StringAttributeProcessor<Toolbar>() {
            override fun setString(view: Toolbar?, value: String?) {
                view?.title = value
            }
        })

        addAttributeProcessor("subtitle", object : StringAttributeProcessor<Toolbar>() {
            override fun setString(view: Toolbar?, value: String?) {
                view?.subtitle = value
            }
        })


        addAttributeProcessor("elevation", object : DimensionAttributeProcessor<Toolbar>() {
            override fun setDimension(view: Toolbar?, dimension: Float) {
                view?.elevation = dimension
            }
        })
    }

    override fun createView(context: ProteusContext, layout: Layout, data: ObjectValue, parent: ViewGroup?, dataIndex: Int): ProteusView {
        return SHPTToolBar(context.applicationContext)
    }

    override fun getType(): String {
        return "SHPTToolbar"
    }

    override fun getParentType(): String? {
        return "View"
    }
}
