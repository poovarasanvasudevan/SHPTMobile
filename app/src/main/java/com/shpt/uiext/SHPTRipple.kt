package com.shpt.uiext

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.poovarasan.blade.view.BladeView
import com.poovarasan.blade.view.manager.BladeViewManager
import com.shpt.mobile.widget.Ripple

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:01 PM
 */

class SHPTRipple : Ripple, BladeView {
    private var viewManager: BladeViewManager? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {
        super.setRippleColor(Color.GRAY)
        super.setRippleOverlay(true)
    }

    override fun getViewManager(): BladeViewManager? {
        return viewManager
    }

    override fun setViewManager(BladeViewManager: BladeViewManager) {
        this.viewManager = BladeViewManager
    }
}
