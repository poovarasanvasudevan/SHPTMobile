package com.shpt.parser

import android.view.ViewGroup
import com.google.gson.JsonObject
import com.poovarasan.blade.parser.Attributes
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.processor.StringAttributeProcessor
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.ProteusView
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
