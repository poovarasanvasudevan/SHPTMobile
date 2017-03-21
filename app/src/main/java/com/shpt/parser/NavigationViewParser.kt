package com.shpt.parser

import android.support.design.widget.NavigationView
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.flipkart.android.proteus.ProteusContext
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.ViewTypeParser
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.shpt.uiext.SHPTNavigationView


/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class NavigationViewParser : ViewTypeParser<NavigationView>() {
    override fun addAttributeProcessors() {
        addAttributeProcessor("drawerGravity", object : StringAttributeProcessor<NavigationView>() {
            override fun setString(view: NavigationView?, value: String?) {
                val params = LinearLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
                when (value) {
                    "start" -> params.gravity = Gravity.START
                    "top" -> params.gravity = Gravity.TOP
                    "end" -> params.gravity = Gravity.END
                    "center" -> params.gravity = Gravity.CENTER
                }

                view!!.layoutParams = params
            }

        })

        addAttributeProcessor("fitsSystemWindows", object : StringAttributeProcessor<NavigationView>() {
            override fun setString(view: NavigationView?, value: String?) {
                if (value == "true") {
                    view?.fitsSystemWindows = true
                }
            }
        })
    }

    override fun createView(context: ProteusContext, layout: Layout, data: ObjectValue, parent: ViewGroup?, dataIndex: Int): ProteusView {
        return SHPTNavigationView(context.applicationContext)
    }

    override fun getParentType(): String? {
        return "View"
    }

    override fun getType(): String {
        return "Nav"
    }
}
