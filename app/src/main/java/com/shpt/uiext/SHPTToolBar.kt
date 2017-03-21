package com.shpt.uiext

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import com.flipkart.android.proteus.ProteusView
import com.shpt.R

/**
 * Created by poovarasanv on 17/1/17.
 * @author poovarasanv
 * @project SHPT
 * @on 17/1/17 at 2:01 PM
 */

class SHPTToolBar : Toolbar, ProteusView {
    override fun getAsView(): View {
        return this
    }

    private var viewManager: ProteusView.Manager? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {
        super.setTitleTextColor(Color.WHITE)
        super.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light)
    }

    override fun getViewManager(): ProteusView.Manager? {
        return viewManager
    }

    override fun setViewManager(proteusViewManager: ProteusView.Manager) {
        this.viewManager = proteusViewManager
    }
}
