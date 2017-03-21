package com.shpt.uiext

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.View
import com.flipkart.android.proteus.ProteusView

/**
 * Created by poovarasanv on 23/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 23/1/17 at 12:29 PM
 */

class SHPTPullToRefresh : SwipeRefreshLayout, ProteusView {
    override fun getAsView(): View {
        return this
    }

    private var viewManager: ProteusView.Manager? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {

    }

    override fun getViewManager(): ProteusView.Manager? {
        return viewManager
    }

    override fun setViewManager(proteusViewManager: ProteusView.Manager) {
        this.viewManager = proteusViewManager
    }
}
