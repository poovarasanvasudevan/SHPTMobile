package com.shpt.parser

import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.ViewGroup
import com.flipkart.android.proteus.ProteusContext
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.ViewTypeParser
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.shpt.uiext.SHPTDrawerLayout


/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class DrawerLayoutParser : ViewTypeParser<DrawerLayout>() {
    override fun createView(context: ProteusContext, layout: Layout, data: ObjectValue, parent: ViewGroup?, dataIndex: Int): ProteusView {
        return SHPTDrawerLayout(context.applicationContext)
    }

    override fun addAttributeProcessors() {
        addAttributeProcessor("openDrawer", object : StringAttributeProcessor<DrawerLayout>() {
            override fun setString(view: DrawerLayout?, value: String?) {
                when (value) {
                    "start" -> view!!.openDrawer(Gravity.START)
                    "top" -> view!!.openDrawer(Gravity.TOP)
                    "end" -> view!!.openDrawer(Gravity.END)
                    "center" -> view!!.openDrawer(Gravity.CENTER)
                }
            }
        })

        addAttributeProcessor("fitsSystemWindows", object : StringAttributeProcessor<DrawerLayout>() {
            override fun setString(view: DrawerLayout?, value: String?) {
                if (value == "true") {
                    view!!.fitsSystemWindows = true
                }
            }
        })
    }

    override fun getType(): String {
        return "Drawer"
    }

    override fun getParentType(): String? {
        return "View"
    }
}
