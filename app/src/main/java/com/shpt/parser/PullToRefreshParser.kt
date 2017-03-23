package com.shpt.parser

import android.support.v4.widget.SwipeRefreshLayout
import android.view.ViewGroup
import com.google.gson.JsonObject
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.BladeView
import com.shpt.uiext.SHPTPullToRefresh

/**
 * Created by poovarasanv on 17/1/17.
 * @author poovarasanv
 * @project SHPT
 * @on 17/1/17 at 2:06 PM
 */

class PullToRefreshParser(wrappedParser: Parser<SwipeRefreshLayout>) : WrappableParser<SwipeRefreshLayout>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): BladeView {
        return SHPTPullToRefresh(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()
    }
}
