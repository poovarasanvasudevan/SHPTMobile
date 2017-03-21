package com.shpt.parser

import android.view.ViewGroup
import com.flipkart.android.proteus.ProteusContext
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.ViewTypeParser
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.shpt.uiext.SHPTJustifiedTextView
import com.shpt.widget.JustifiedTextView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class JustifiedTextViewParser : ViewTypeParser<JustifiedTextView>() {
    override fun getParentType(): String? {
        return "View"
    }

    override fun addAttributeProcessors() {
        addAttributeProcessor("text", object : StringAttributeProcessor<JustifiedTextView>() {
            override fun setString(view: JustifiedTextView?, value: String?) {
                view?.text = value!!
            }
        })
    }

    override fun getType(): String {
        return "JustifiedText"
    }

    override fun createView(context: ProteusContext, layout: Layout, data: ObjectValue, parent: ViewGroup?, dataIndex: Int): ProteusView {
        return SHPTJustifiedTextView(context.applicationContext)
    }
}
