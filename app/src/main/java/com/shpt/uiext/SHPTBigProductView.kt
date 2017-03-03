package com.shpt.uiext

import android.content.Context
import android.util.AttributeSet
import com.flipkart.android.proteus.view.ProteusView
import com.flipkart.android.proteus.view.manager.ProteusViewManager
import com.shpt.widget.BigProductView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:01 PM
 */

class SHPTBigProductView : BigProductView, ProteusView {
    private var viewManager: ProteusViewManager? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}
    constructor(context: Context, productid: Int) : super(context, productid) {}
    override fun getViewManager(): ProteusViewManager? {
        return viewManager
    }

    override fun setViewManager(proteusViewManager: ProteusViewManager) {
        this.viewManager = proteusViewManager
    }
}
