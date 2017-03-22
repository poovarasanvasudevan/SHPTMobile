package com.shpt.parser

import android.content.res.ColorStateList
import android.view.ViewGroup
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.poovarasan.blade.parser.Attributes
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.processor.ColorResourceProcessor
import com.poovarasan.blade.processor.DimensionAttributeProcessor
import com.poovarasan.blade.processor.StringAttributeProcessor
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.ProteusView
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
