package com.shpt.uiext

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import com.poovarasan.blade.view.ProteusView
import com.poovarasan.blade.view.manager.ProteusViewManager

/**
 * Created by poovarasanv on 23/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 23/1/17 at 12:29 PM
 */

class SHPTPullToRefresh : SwipeRefreshLayout, ProteusView {

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
