package com.shpt.parser

import android.view.ViewGroup
import com.google.gson.JsonObject
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.ProteusView
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
