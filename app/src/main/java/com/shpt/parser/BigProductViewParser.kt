package com.shpt.parser

import android.view.ViewGroup
import com.google.gson.JsonObject
import com.poovarasan.blade.parser.Attributes
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.processor.StringAttributeProcessor
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.ProteusView
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
