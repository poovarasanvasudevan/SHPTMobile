package com.shpt.uiext

import android.content.Context
import com.flipkart.android.proteus.view.ProteusView
import com.flipkart.android.proteus.view.manager.ProteusViewManager
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
    private var viewManager: ProteusViewManager? = null
    override fun getViewManager(): ProteusViewManager? {
        return viewManager
    }

    override fun setViewManager(p0: ProteusViewManager?) {
        this.viewManager = p0
    }

}
