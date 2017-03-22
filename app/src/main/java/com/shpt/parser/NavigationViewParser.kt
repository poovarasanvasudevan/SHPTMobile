package com.shpt.parser

import android.support.design.widget.NavigationView
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.google.gson.JsonObject
import com.poovarasan.blade.parser.Attributes
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.processor.StringAttributeProcessor
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.ProteusView
import com.shpt.uiext.SHPTNavigationView


/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class NavigationViewParser(wrappedParser: Parser<NavigationView>) : WrappableParser<NavigationView>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): ProteusView {
        return SHPTNavigationView(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()

        addHandler(Attributes.Attribute("drawerGravity"), object : StringAttributeProcessor<NavigationView>() {
            override fun handle(s: String, s1: String, nav: NavigationView) {
                val params = LinearLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                when (s1) {
                    "start" -> params.gravity = Gravity.START
                    "top" -> params.gravity = Gravity.TOP
                    "end" -> params.gravity = Gravity.END
                    "center" -> params.gravity = Gravity.CENTER
                }

                nav.layoutParams = params
            }
        })


        addHandler(Attributes.Attribute("fitsSystemWindows"), object : StringAttributeProcessor<NavigationView>() {
            override fun handle(s: String, s1: String, nav: NavigationView) {
                if(s1 == "true"){
                    nav.setFitsSystemWindows(true)
                }
            }
        })
    }
}
