package com.shpt.parser

import android.content.res.ColorStateList
import android.support.v7.widget.CardView
import android.view.ViewGroup
import com.flipkart.android.proteus.ProteusContext
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.ViewTypeParser
import com.flipkart.android.proteus.processor.ColorResourceProcessor
import com.flipkart.android.proteus.processor.DimensionAttributeProcessor
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.shpt.uiext.SHPTCardView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class CardViewParser : ViewTypeParser<CardView>() {
    override fun createView(context: ProteusContext, layout: Layout, data: ObjectValue, parent: ViewGroup?, dataIndex: Int): ProteusView {
        return SHPTCardView(context)
    }

    override fun addAttributeProcessors() {
        addAttributeProcessor("bgcolor", object : ColorResourceProcessor<CardView>() {
            override fun setColor(view: CardView?, color: Int) {
                view!!.setCardBackgroundColor(color)
            }

            override fun setColor(view: CardView?, colors: ColorStateList?) {
                view!!.cardBackgroundColor = colors
            }
        })


        addAttributeProcessor("cardElevation", object : DimensionAttributeProcessor<CardView>() {
            override fun setDimension(view: CardView?, dimension: Float) {
                view!!.elevation = dimension
            }
        })
    }

    override fun getType(): String {
        return "Card"
    }

    override fun getParentType(): String? {
        return "View"
    }
}
