package com.shpt.uiext

import android.content.Context
import android.util.AttributeSet
import com.poovarasan.blade.view.ProteusView
import com.poovarasan.blade.view.manager.ProteusViewManager
import com.shpt.mobile.widget.ProgressWheel

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 12:53 PM
 */

class SHPTProgressView : ProgressWheel, ProteusView {

    private var viewManager: ProteusViewManager? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {
        super.spin()
        super.setCircleRadius(530)
    }

    override fun getViewManager(): ProteusViewManager? {
        return viewManager
    }

    override fun setViewManager(proteusViewManager: ProteusViewManager) {
        this.viewManager = proteusViewManager
    }
}
