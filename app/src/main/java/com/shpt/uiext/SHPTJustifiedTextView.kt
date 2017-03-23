package com.shpt.uiext

import android.content.Context
import com.poovarasan.blade.view.BladeView
import com.poovarasan.blade.view.manager.BladeViewManager
import com.shpt.widget.JustifiedTextView

/**
 * Created by poovarasanv on 18/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 18/1/17 at 6:48 PM
 */

class SHPTJustifiedTextView(context: Context?) : JustifiedTextView(context), BladeView {
    private var viewManager: BladeViewManager? = null
    override fun getViewManager(): BladeViewManager? {
        return viewManager
    }

    override fun setViewManager(p0: BladeViewManager?) {
        this.viewManager = p0
    }

}
