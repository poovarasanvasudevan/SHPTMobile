package com.shpt.parser

import android.content.res.ColorStateList
import android.support.v7.widget.CardView
import android.view.ViewGroup
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.poovarasan.blade.parser.Attributes
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.processor.ColorResourceProcessor
import com.poovarasan.blade.processor.DimensionAttributeProcessor
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.BladeView
import com.shpt.uiext.SHPTCardView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class CardViewParser(wrappedParser: Parser<CardView>) : WrappableParser<CardView>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): BladeView {
        return SHPTCardView(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()

        addHandler(Attributes.Attribute("bgColor"), object : ColorResourceProcessor<CardView>() {
            override fun setColor(p0: CardView?, p1: Int) = p0!!.setCardBackgroundColor(p1)

            override fun setColor(p0: CardView?, p1: ColorStateList?) {
                p0!!.cardBackgroundColor = p1
            }
        })


        addHandler(Attributes.Attribute("cardElevation"), object : DimensionAttributeProcessor<CardView>() {
            override fun setDimension(p0: Float, p1: CardView?, p2: String?, p3: JsonElement?) {
                p1!!.cardElevation = p0
            }
        })
    }
}
