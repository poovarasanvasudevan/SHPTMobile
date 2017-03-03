package com.shpt.parser

import android.view.ViewGroup
import com.flipkart.android.proteus.parser.Attributes
import com.flipkart.android.proteus.parser.Parser
import com.flipkart.android.proteus.parser.WrappableParser
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.toolbox.Styles
import com.flipkart.android.proteus.view.ProteusView
import com.google.gson.JsonObject
import com.shpt.uiext.SHPTJustifiedTextView
import com.shpt.widget.JustifiedTextView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class JustifiedTextViewParser(wrappedParser: Parser<JustifiedTextView>) : WrappableParser<JustifiedTextView>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): ProteusView {
        return SHPTJustifiedTextView(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()

        addHandler(Attributes.Attribute("text"), object : StringAttributeProcessor<JustifiedTextView>() {
            override fun handle(p0: String?, p1: String?, p2: JustifiedTextView?) {
                p2!!.text = p1!!
            }
        })
    }
}
