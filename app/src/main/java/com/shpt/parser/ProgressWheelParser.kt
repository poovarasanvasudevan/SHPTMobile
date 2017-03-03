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

class ProgressWheelParser(wrappedParser: Parser<ProgressWheel>) : WrappableParser<ProgressWheel>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): ProteusView {
        return SHPTProgressView(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()

        addHandler(Attributes.Attribute("barColor"), object : ColorResourceProcessor<ProgressWheel>() {
            override fun setColor(progressWheel: ProgressWheel, i: Int) {
                progressWheel.setBarColor(i)
            }

            override fun setColor(progressWheel: ProgressWheel, colorStateList: ColorStateList) {
                progressWheel.setBarColor(colorStateList.defaultColor)
            }
        })


        addHandler(Attributes.Attribute("barWidth"), object : DimensionAttributeProcessor<ProgressWheel>() {
            override fun setDimension(v: Float, progressWheel: ProgressWheel, s: String, jsonElement: JsonElement) {
                progressWheel.setBarWidth(v.toInt())
            }
        })


        addHandler(Attributes.Attribute("intermediate"), object : StringAttributeProcessor<ProgressWheel>() {
            override fun handle(s: String, s1: String, progressWheel: ProgressWheel) {
                if (s == "true") {
                    progressWheel.spin()
                }
            }
        })

    }
}
