package com.shpt.parser

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import com.google.gson.JsonObject
import com.poovarasan.blade.parser.Attributes
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.processor.AttributeProcessor
import com.poovarasan.blade.processor.DrawableResourceProcessor
import com.poovarasan.blade.processor.StringAttributeProcessor
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.BladeView
import com.shpt.core.setImageURL
import com.shpt.core.setSHPTImageURL
import com.shpt.uiext.FrescoImageView


/**
 * Created by poovarasanv on 6/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 6/4/17 at 5:28 PM
 */

class FrescoParser(wrappedParser: Parser<ImageView>) : WrappableParser<ImageView>(wrappedParser) {
	
	override fun createView(parent: ViewGroup, layout: JsonObject, data: JsonObject, styles: Styles, index: Int): BladeView {
		return FrescoImageView(parent.context)
	}
	
	override fun addHandler(key: Attributes.Attribute, handler: AttributeProcessor<ImageView>) {
		super.addHandler(key, handler)
		
		
		addHandler(Attributes.Attribute("imageUrl"), object : StringAttributeProcessor<ImageView>() {
			override fun handle(attributeKey: String?, attributeValue: String?, view: ImageView?) {
				view!!.setImageURL(attributeValue!!)
			}
		})
		
		
		addHandler(Attributes.Attribute("shpturl"), object : StringAttributeProcessor<ImageView>() {
			override fun handle(attributeKey: String?, attributeValue: String?, view: ImageView?) {
				view!!.setSHPTImageURL(attributeValue!!)
			}
		})
		
		addHandler(Attributes.Attribute("imageSrc"), object : DrawableResourceProcessor<ImageView>() {
			override fun setDrawable(p0: ImageView?, p1: Drawable?) {
				p0!!.setImageDrawable(null)
				p0.setImageDrawable(p1)
			}
		})
	}
}
