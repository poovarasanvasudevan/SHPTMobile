package com.shpt.parser

import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import com.flipkart.android.proteus.parser.Attributes
import com.flipkart.android.proteus.parser.Parser
import com.flipkart.android.proteus.parser.WrappableParser
import com.flipkart.android.proteus.processor.DimensionAttributeProcessor
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.toolbox.Styles
import com.flipkart.android.proteus.view.ProteusView
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.shpt.uiext.SHPTToolBar

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class ToolBarParser(wrappedParser: Parser<Toolbar>) : WrappableParser<Toolbar>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): ProteusView {
        return SHPTToolBar(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()

        addHandler(Attributes.Attribute("title"), object : StringAttributeProcessor<Toolbar>() {
            override fun handle(s: String, s1: String, progressWheel: Toolbar) {
                progressWheel.title = s1
            }
        })


        addHandler(Attributes.Attribute("subtitle"), object : StringAttributeProcessor<Toolbar>() {
            override fun handle(s: String, s1: String, progressWheel: Toolbar) {
                progressWheel.subtitle = s1
            }
        })

        addHandler(Attributes.Attribute("elevation"),object : DimensionAttributeProcessor<Toolbar>() {
            override fun setDimension(p0: Float, p1: Toolbar?, p2: String?, p3: JsonElement?) {
                p1!!.elevation = p0
            }

        })
    }
}
