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
import com.mikepenz.iconics.view.IconicsImageView
import com.shpt.uiext.SHPTIcon

/**
 * Created by poovarasanv on 17/1/17.
 * @author poovarasanv
 * @project SHPT
 * @on 17/1/17 at 2:06 PM
 */

class IconParser : ViewTypeParser<IconicsImageView>() {
    override fun addAttributeProcessors() {

        addAttributeProcessor("icon", object : StringAttributeProcessor<IconicsImageView>() {
            override fun setString(view: IconicsImageView?, value: String?) {
                view!!.setIcon(value)
            }
        })

        addAttributeProcessor("size", object : DimensionAttributeProcessor<IconicsImageView>() {
            override fun setDimension(view: IconicsImageView?, dimension: Float) {
                if (view != null) {
                    view.icon.sizeDp(dimension.toInt())
                }
            }
        })

        addAttributeProcessor("color", object : ColorResourceProcessor<IconicsImageView>() {
            override fun setColor(view: IconicsImageView?, colors: ColorStateList?) {
                view?.setColor(colors!!.defaultColor)
            }

            override fun setColor(view: IconicsImageView?, color: Int) {
                view?.setColor(color)
            }
        })
    }

    override fun getType(): String {
        return "Icon"
    }

    override fun getParentType(): String? {
        return "View"
    }

    override fun createView(context: ProteusContext, layout: Layout, data: ObjectValue, parent: ViewGroup?, dataIndex: Int): ProteusView {
        return SHPTIcon(context.applicationContext)
    }
}
