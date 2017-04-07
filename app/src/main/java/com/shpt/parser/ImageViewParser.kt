package com.shpt.parser

import android.view.ViewGroup
import android.widget.ImageView
import com.google.gson.JsonObject
import com.poovarasan.blade.parser.Attributes
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.processor.StringAttributeProcessor
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.BladeView
import com.shpt.core.setImageURL
import com.shpt.uiext.SHPTImageView

/**
 * Created by poovarasanv on 7/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 7/4/17 at 2:57 PM
 */

class ImageViewParser(wrappedParser: Parser<ImageView>) : WrappableParser<ImageView>(wrappedParser) {
	override fun createView(parent: ViewGroup?, layout: JsonObject?, data: JsonObject?, styles: Styles?, index: Int): BladeView {
		return SHPTImageView(parent!!.context)
	}
	
	
	override fun prepareHandlers() {
		super.prepareHandlers()
		
		addHandler(Attributes.Attribute("imageURL"), object : StringAttributeProcessor<ImageView>() {
			override fun handle(attributeKey: String?, attributeValue: String?, view: ImageView?) {
				view!!.setImageURL(attributeValue!!)
			}
			
		})
	}
}
