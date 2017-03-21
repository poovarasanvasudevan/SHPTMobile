package com.shpt.uiext

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.flipkart.android.proteus.ProteusView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:01 PM
 */

class SHPTImageView : ImageView, ProteusView {
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
