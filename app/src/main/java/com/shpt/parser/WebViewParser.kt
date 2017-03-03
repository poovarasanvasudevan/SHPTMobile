package com.shpt.parser

import android.view.ViewGroup
import com.flipkart.android.proteus.parser.Attributes
import com.flipkart.android.proteus.parser.Parser
import com.flipkart.android.proteus.parser.WrappableParser
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.toolbox.Styles
import com.flipkart.android.proteus.view.ProteusView
import com.google.gson.JsonObject
import com.shpt.uiext.SHPTWebView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class WebViewParser(wrappedParser: Parser<SHPTWebView>) : WrappableParser<SHPTWebView>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): ProteusView {
        return SHPTWebView(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()

        addHandler(Attributes.Attribute("url"), object : StringAttributeProcessor<SHPTWebView>() {
            override fun handle(s: String, s1: String, web: SHPTWebView) {

                val headerMap : Map<String,String> = HashMap<String,String>()
                headerMap.plus("Cache-Control" to "no-cache")
                headerMap.plus("Cache-Control" to "no-store")
                web.loadUrl(s1)
            }
        })

        addHandler(Attributes.Attribute("html"), object : StringAttributeProcessor<SHPTWebView>() {
            override fun handle(s: String, s1: String, web: SHPTWebView) {
                web.loadData(s1, "text/html; charset=UTF-8", null)
            }
        })
    }
}
