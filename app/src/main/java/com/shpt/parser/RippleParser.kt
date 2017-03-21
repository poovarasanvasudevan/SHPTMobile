package com.shpt.parser

import android.view.ViewGroup
import com.flipkart.android.proteus.ProteusContext
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.ViewTypeParser
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.shpt.mobile.widget.Ripple
import com.shpt.uiext.SHPTRipple


/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class RippleParser : ViewTypeParser<Ripple>() {
    override fun addAttributeProcessors() {

    }

    override fun getParentType(): String? {
        return "View"
    }

    override fun createView(context: ProteusContext, layout: Layout, data: ObjectValue, parent: ViewGroup?, dataIndex: Int): ProteusView {
        return SHPTRipple(context.applicationContext)
    }

    override fun getType(): String {
        return "Ripple"
    }
}
