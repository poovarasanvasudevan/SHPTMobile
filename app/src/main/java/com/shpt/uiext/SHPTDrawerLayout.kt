package com.shpt.uiext

import android.content.Context
import android.support.v4.widget.DrawerLayout
import android.util.AttributeSet
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

class SHPTDrawerLayout : DrawerLayout, ProteusView {
    private var viewManager: ProteusViewManager? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {

//        super.openDrawer(GravityCompat.START)
    }

    override fun getViewManager(): ProteusViewManager? {
        return viewManager
    }

    override fun setViewManager(proteusViewManager: ProteusViewManager) {
        this.viewManager = proteusViewManager
    }
}
