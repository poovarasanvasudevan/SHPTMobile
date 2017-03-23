package com.shpt.parser

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.poovarasan.blade.parser.Attributes
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.processor.DrawableResourceProcessor
import com.poovarasan.blade.processor.StringAttributeProcessor
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.BladeView
import com.shpt.R
import com.shpt.uiext.SHPTImageView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class ImageViewParser(wrappedParser: Parser<ImageView>) : WrappableParser<ImageView>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): BladeView {
        return SHPTImageView(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()

        addHandler(Attributes.Attribute("imageUrl"), object : StringAttributeProcessor<ImageView>() {
            override fun handle(p0: String?, p1: String?, p2: ImageView?) {
                p2!!.setImageDrawable(null)
                Glide.with(p2.context)
                        .load(p1)
                        .error(ContextCompat.getDrawable(p2.context, R.drawable.no_image))
                        .animate(R.anim.zoomin)
                        .into(p2)
            }
        })

        addHandler(Attributes.Attribute("imageSrc"), object : DrawableResourceProcessor<ImageView>() {
            override fun setDrawable(p0: ImageView?, p1: Drawable?) {
                p0!!.setImageDrawable(null)

                Glide.with(p0.context)
                        .load(p1)
                        .error(ContextCompat.getDrawable(p0.context, R.drawable.no_image))
                        .animate(R.anim.zoomin)
                        .into(p0)

            }
        })
    }
}
