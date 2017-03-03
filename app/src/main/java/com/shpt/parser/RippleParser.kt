package com.shpt.parser

import android.view.ViewGroup
import com.flipkart.android.proteus.parser.Parser
import com.flipkart.android.proteus.parser.WrappableParser
import com.flipkart.android.proteus.toolbox.Styles
import com.flipkart.android.proteus.view.ProteusView
import com.google.gson.JsonObject
import com.shpt.mobile.widget.Ripple
import com.shpt.uiext.SHPTRipple


/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class RippleParser(wrappedParser: Parser<Ripple>) : WrappableParser<Ripple>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): ProteusView {
        return SHPTRipple(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()
    }
}
