package com.shpt.parser

import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.ViewGroup
import com.flipkart.android.proteus.parser.Attributes
import com.flipkart.android.proteus.parser.Parser
import com.flipkart.android.proteus.parser.WrappableParser
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.toolbox.Styles
import com.flipkart.android.proteus.view.ProteusView
import com.google.gson.JsonObject
import com.shpt.uiext.SHPTDrawerLayout


/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class DrawerLayoutParser(wrappedParser: Parser<DrawerLayout>) : WrappableParser<DrawerLayout>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): ProteusView {
        return SHPTDrawerLayout(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()

        addHandler(Attributes.Attribute("openDrawer"), object : StringAttributeProcessor<DrawerLayout>() {
            override fun handle(s: String, s1: String, nav: DrawerLayout) {

                when (s1) {
                    "start" -> nav.openDrawer(Gravity.START)
                    "top" -> nav.openDrawer(Gravity.TOP)
                    "end" -> nav.openDrawer(Gravity.END)
                    "center" -> nav.openDrawer(Gravity.CENTER)
                }
            }
        })



        addHandler(Attributes.Attribute("fitsSystemWindows"), object : StringAttributeProcessor<DrawerLayout>() {
            override fun handle(s: String, s1: String, nav: DrawerLayout) {
                if(s1 == "true"){
                    nav.setFitsSystemWindows(true)
                }
            }
        })
    }
}
