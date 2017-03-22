package com.shpt.parser

import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.poovarasan.blade.parser.Attributes
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.processor.DimensionAttributeProcessor
import com.poovarasan.blade.processor.StringAttributeProcessor
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.ProteusView
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
