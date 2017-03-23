package com.shpt.uiext

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebSettings
import com.poovarasan.blade.view.BladeView
import com.poovarasan.blade.view.BladeWebView
import com.poovarasan.blade.view.manager.BladeViewManager

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:01 PM
 */

class SHPTWebView : BladeWebView, BladeView {
    private var viewManager: BladeViewManager? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    override fun getViewManager(): BladeViewManager? {
        return viewManager
    }

    override fun setViewManager(BladeViewManager: BladeViewManager) {
        this.viewManager = BladeViewManager
    }


    fun init() {
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        this.setWebChromeClient(android.webkit.WebChromeClient())
    }

}
