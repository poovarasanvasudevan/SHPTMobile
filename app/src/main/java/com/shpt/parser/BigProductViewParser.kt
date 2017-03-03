package com.shpt.parser

import android.view.ViewGroup
import com.flipkart.android.proteus.parser.Attributes
import com.flipkart.android.proteus.parser.Parser
import com.flipkart.android.proteus.parser.WrappableParser
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.toolbox.Styles
import com.flipkart.android.proteus.view.ProteusView
import com.google.gson.JsonObject
import com.shpt.uiext.SHPTBigProductView
import com.shpt.widget.BigProductView


/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class BigProductViewParser(wrappedParser: Parser<BigProductView>) : WrappableParser<BigProductView>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): ProteusView {
        return SHPTBigProductView(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()


        addHandler(Attributes.Attribute("productid"), object : StringAttributeProcessor<BigProductView>() {
            override fun handle(p0: String?, p1: String?, p2: BigProductView?) {
                p2!!.init(p2.context, p1!!.toInt())
            }
        })
    }
}
