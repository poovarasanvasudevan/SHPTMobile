package com.shpt.parser

import android.view.ViewGroup
import com.flipkart.android.proteus.ProteusContext
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.ViewTypeParser
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.shpt.uiext.SHPTWebView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class WebViewParser : ViewTypeParser<SHPTWebView>() {
    override fun getParentType(): String? {
        return "View"
    }

    override fun createView(context: ProteusContext, layout: Layout, data: ObjectValue, parent: ViewGroup?, dataIndex: Int): ProteusView {
        return SHPTWebView(context.applicationContext)
    }

    override fun getType(): String {
        return "Web"
    }

    override fun addAttributeProcessors() {
        addAttributeProcessor("url", object : StringAttributeProcessor<SHPTWebView>() {
            override fun setString(view: SHPTWebView?, value: String?) {
                val headerMap: Map<String, String> = HashMap<String, String>()
                headerMap.plus("Cache-Control" to "no-cache")
                headerMap.plus("Cache-Control" to "no-store")
                view?.loadUrl(value)
            }

        })

        addAttributeProcessor("html", object : StringAttributeProcessor<SHPTWebView>() {
            override fun setString(view: SHPTWebView?, value: String?) {
                view?.loadData(value, "text/html; charset=UTF-8", null)
            }
        })
    }
}
