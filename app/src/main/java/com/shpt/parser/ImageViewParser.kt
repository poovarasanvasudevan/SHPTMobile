package com.shpt.parser

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.flipkart.android.proteus.ProteusContext
import com.flipkart.android.proteus.ProteusView
import com.flipkart.android.proteus.ViewTypeParser
import com.flipkart.android.proteus.processor.DrawableResourceProcessor
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.value.Layout
import com.flipkart.android.proteus.value.ObjectValue
import com.shpt.R
import com.shpt.core.setImageURL
import com.shpt.uiext.SHPTImageView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class ImageViewParser : ViewTypeParser<ImageView>() {
    override fun addAttributeProcessors() {
        addAttributeProcessor("imageUrl", object : StringAttributeProcessor<ImageView>() {
            override fun setString(view: ImageView?, value: String?) {
                view?.setImageURL(value!!)
            }
        })

        addAttributeProcessor("imageSrc", object : DrawableResourceProcessor<ImageView>() {
            override fun setDrawable(view: ImageView?, drawable: Drawable?) {
                Glide.with(view?.context)
                        .load(drawable)
                        .error(ContextCompat.getDrawable(view?.context, R.drawable.no_image))
                        .animate(R.anim.zoomin)
                        .into(view)
            }
        })
    }

    override fun getParentType(): String? {
        return "View"
    }

    override fun getType(): String {
        return "Image"
    }

    override fun createView(context: ProteusContext, layout: Layout, data: ObjectValue, parent: ViewGroup?, dataIndex: Int): ProteusView {
        return SHPTImageView(context.applicationContext)
    }
}
