package com.shpt.uiext

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.view.View
import com.flipkart.android.proteus.ProteusView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:01 PM
 */

class SHPTAppBar(context: Context?) : AppBarLayout(context), ProteusView {
    private var viewManager: ProteusView.Manager? = null

    override fun getViewManager(): ProteusView.Manager {
        return viewManager!!
    }

    override fun getAsView(): View {
        return this
    }

    override fun setViewManager(manager: ProteusView.Manager?) {
        this.viewManager = manager
    }

}
