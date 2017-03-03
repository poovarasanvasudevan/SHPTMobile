package com.shpt.parser

import android.support.design.widget.AppBarLayout
import android.view.ViewGroup
import com.flipkart.android.proteus.parser.Attributes
import com.flipkart.android.proteus.parser.Parser
import com.flipkart.android.proteus.parser.WrappableParser
import com.flipkart.android.proteus.processor.DimensionAttributeProcessor
import com.flipkart.android.proteus.toolbox.Styles
import com.flipkart.android.proteus.view.ProteusView
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.shpt.uiext.SHPTAppBar

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class AppBarParser(wrappedParser: Parser<AppBarLayout>) : WrappableParser<AppBarLayout>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): ProteusView {
        return SHPTAppBar(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()


        addHandler(Attributes.Attribute("elevation"),object : DimensionAttributeProcessor<AppBarLayout>() {
            override fun setDimension(p0: Float, p1: AppBarLayout?, p2: String?, p3: JsonElement?) {
                p1!!.elevation = p0
            }

        })
    }
}
