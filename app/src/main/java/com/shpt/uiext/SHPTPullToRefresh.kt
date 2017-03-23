package com.shpt.uiext

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import com.poovarasan.blade.view.BladeView
import com.poovarasan.blade.view.manager.BladeViewManager

/**
 * Created by poovarasanv on 23/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 23/1/17 at 12:29 PM
 */

class SHPTPullToRefresh : SwipeRefreshLayout, BladeView {

    private var viewManager: BladeViewManager? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {

    }

    override fun getViewManager(): BladeViewManager? {
        return viewManager
    }

    override fun setViewManager(BladeViewManager: BladeViewManager) {
        this.viewManager = BladeViewManager
    }
}
