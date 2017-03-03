package com.shpt.parser

import android.content.res.ColorStateList
import android.view.ViewGroup
import com.flipkart.android.proteus.parser.Attributes
import com.flipkart.android.proteus.parser.Parser
import com.flipkart.android.proteus.parser.WrappableParser
import com.flipkart.android.proteus.processor.ColorResourceProcessor
import com.flipkart.android.proteus.processor.DimensionAttributeProcessor
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.toolbox.Styles
import com.flipkart.android.proteus.view.ProteusView
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mikepenz.iconics.view.IconicsImageView
import com.shpt.uiext.SHPTIcon

/**
 * Created by poovarasanv on 17/1/17.
 * @author poovarasanv
 * @project SHPT
 * @on 17/1/17 at 2:06 PM
 */

class IconParser(wrappedParser: Parser<IconicsImageView>) : WrappableParser<IconicsImageView>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): ProteusView {
        return SHPTIcon(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()

        addHandler(Attributes.Attribute("icon"), object : StringAttributeProcessor<IconicsImageView>() {
            override fun handle(p0: String?, p1: String?, p2: IconicsImageView?) {
                p2?.setIcon(p1)
            }
        })

        addHandler(Attributes.Attribute("size"), object : DimensionAttributeProcessor<IconicsImageView>() {
            override fun setDimension(p0: Float, p1: IconicsImageView?, p2: String?, p3: JsonElement?) {
                if (p1 != null) {
                    p1.icon.sizeDp(p0.toInt())
                }
            }
        })


        addHandler(Attributes.Attribute("iconColor"), object : ColorResourceProcessor<IconicsImageView>() {
            override fun setColor(p0: IconicsImageView?, p1: ColorStateList?) {

                p0?.setColor(p1!!.defaultColor)
            }

            override fun setColor(p0: IconicsImageView?, p1: Int) {
                p0?.setColor(p1)
            }
        })
    }
}
