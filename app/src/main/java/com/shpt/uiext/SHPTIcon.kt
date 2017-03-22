package com.shpt.uiext

import android.content.Context
import android.util.AttributeSet
import com.mikepenz.iconics.view.IconicsImageView
import com.poovarasan.blade.view.ProteusView
import com.poovarasan.blade.view.manager.ProteusViewManager

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:01 PM
 */

class SHPTIcon : IconicsImageView, ProteusView {
    private var viewManager: ProteusViewManager? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {
    }

    override fun getViewManager(): ProteusViewManager? {
        return viewManager
    }

    override fun setViewManager(proteusViewManager: ProteusViewManager) {
        this.viewManager = proteusViewManager
    }
}
