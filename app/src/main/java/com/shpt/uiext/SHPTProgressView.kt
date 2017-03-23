package com.shpt.uiext

import android.content.Context
import android.util.AttributeSet
import com.poovarasan.blade.view.BladeView
import com.poovarasan.blade.view.manager.BladeViewManager
import com.shpt.mobile.widget.ProgressWheel

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 12:53 PM
 */

class SHPTProgressView : ProgressWheel, BladeView {

    private var viewManager: BladeViewManager? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {
        super.spin()
        super.setCircleRadius(530)
    }

    override fun getViewManager(): BladeViewManager? {
        return viewManager
    }

    override fun setViewManager(BladeViewManager: BladeViewManager) {
        this.viewManager = BladeViewManager
    }
}
