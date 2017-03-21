package com.shpt.parser

import android.content.res.ColorStateList
import android.view.ViewGroup
import com.flipkart.android.proteus.ProteusContext
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.ViewTypeParser
import com.flipkart.android.proteus.processor.ColorResourceProcessor
import com.flipkart.android.proteus.processor.DimensionAttributeProcessor
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.shpt.mobile.widget.ProgressWheel
import com.shpt.uiext.SHPTProgressView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 12:49 PM
 */

class ProgressWheelParser : ViewTypeParser<ProgressWheel>() {
    override fun addAttributeProcessors() {
        addAttributeProcessor("barColor", object : ColorResourceProcessor<ProgressWheel>() {
            override fun setColor(view: ProgressWheel?, color: Int) {
                view?.setBarColor(color)
            }

            override fun setColor(view: ProgressWheel?, colors: ColorStateList?) {
                view?.setBarColor(colors!!.defaultColor)
            }
        })

        addAttributeProcessor("barWidth", object : DimensionAttributeProcessor<ProgressWheel>() {
            override fun setDimension(view: ProgressWheel?, dimension: Float) {
                view?.setBarWidth(dimension.toInt())
            }
        })

        addAttributeProcessor("intermediate", object : StringAttributeProcessor<ProgressWheel>() {
            override fun setString(view: ProgressWheel?, value: String?) {

                if (value == "true") {
                    view?.spin()
                }
            }

        })
    }

    override fun getParentType(): String? {
        return "View"
    }

    override fun createView(context: ProteusContext, layout: Layout, data: ObjectValue, parent: ViewGroup?, dataIndex: Int): ProteusView {
        return SHPTProgressView(context.applicationContext)
    }

    override fun getType(): String {
        return "ProgressWheel"
    }
}
