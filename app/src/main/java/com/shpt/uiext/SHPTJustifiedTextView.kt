package com.shpt.uiext

import android.content.Context
import android.view.View
import com.flipkart.android.proteus.ProteusView
import com.shpt.widget.JustifiedTextView

/**
 * Created by poovarasanv on 18/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 18/1/17 at 6:48 PM
 */

class SHPTJustifiedTextView(context: Context?) : JustifiedTextView(context), ProteusView {
    override fun getAsView(): View {
        return this
    }

    private var viewManager: ProteusView.Manager? = null
    override fun getViewManager(): ProteusView.Manager? {
        return viewManager
    }

    override fun setViewManager(p0: ProteusView.Manager?) {
        this.viewManager = p0
    }

}
