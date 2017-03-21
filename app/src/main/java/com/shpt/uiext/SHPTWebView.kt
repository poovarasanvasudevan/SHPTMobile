package com.shpt.uiext

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebSettings
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.view.ProteusWebView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:01 PM
 */

class SHPTWebView : ProteusWebView, ProteusView {
    private var viewManager: ProteusView.Manager? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    override fun getViewManager(): ProteusView.Manager? {
        return viewManager
    }

    override fun setViewManager(proteusViewManager: ProteusView.Manager) {
        this.viewManager = proteusViewManager
    }


    fun init() {
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        this.setWebChromeClient(android.webkit.WebChromeClient())
    }

}
