package com.shpt.uiext

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.flipkart.android.proteus.ProteusView
import com.shpt.mobile.widget.Ripple

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:01 PM
 */

class SHPTRipple : Ripple, ProteusView {
    override fun getAsView(): View {
        return this
    }

    private var viewManager: ProteusView.Manager? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {
        super.setRippleColor(Color.GRAY)
        super.setRippleOverlay(true)
    }

    override fun getViewManager(): ProteusView.Manager? {
        return viewManager
    }

    override fun setViewManager(proteusViewManager: ProteusView.Manager) {
        this.viewManager = proteusViewManager
    }
}
