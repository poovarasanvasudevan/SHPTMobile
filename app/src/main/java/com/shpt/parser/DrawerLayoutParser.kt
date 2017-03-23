package com.shpt.parser

import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.ViewGroup
import com.google.gson.JsonObject
import com.poovarasan.blade.parser.Attributes
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.processor.StringAttributeProcessor
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.BladeView
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

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): BladeView {
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
