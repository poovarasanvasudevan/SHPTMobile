package com.shpt.parser

import android.view.ViewGroup
import com.google.gson.JsonObject
import com.poovarasan.blade.parser.Attributes
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.processor.StringAttributeProcessor
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.ProteusView
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
