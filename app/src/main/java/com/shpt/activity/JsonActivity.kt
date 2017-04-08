package com.shpt.activity

import android.os.Bundle
import android.view.View
import com.google.gson.JsonObject
import com.poovarasan.blade.toolbox.Styles
import com.shpt.R
import com.shpt.core.app.BaseActivity
import com.shpt.core.config.LAYOUT_BUILDER_FACTORY
import com.shpt.core.config.PARSER
import com.shpt.core.config.REST
import com.shpt.core.data.Constant
import com.shpt.core.getGlobalData
import com.shpt.core.getLayout
import com.shpt.core.getStyles
import com.shpt.core.setUpEssential
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by poovarasanv on 8/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 8/4/17 at 11:11 AM
 */

class JsonActivity : BaseActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		var layoutJson: JsonObject? = null
		var layout: JsonObject? = null
		if (intent.getStringExtra(Constant.PARCEL) != null) {
			layout = PARSER.parse(intent.getStringExtra(Constant.PARCEL)).asJsonObject
		}
		
		if (layout == null) {
			layout = JsonObject()
		}
		if (layout.has("layoutURL")) {
			doAsync {
				layoutJson = PARSER.parse(REST.get(layout!!.get("layoutURL").asString).execute().body().string()).asJsonObject
			}
		}
		if (layout.has("layoutPage")) {
			doAsync {
				layoutJson = PARSER.parse(getLayout(layout!!.get("layoutPage").asString)!!.structure).asJsonObject
			}
		}
		
		
		
		doAsync {
			val styles: Styles = getStyles()!!
			val data = getGlobalData()
			
			uiThread {
				super.init(layoutJson!!)
				
				
				val parser = layoutJson
				val layoutBuilder = LAYOUT_BUILDER_FACTORY
				mainLayout.removeAllViews()
				
				data.add("local", if (parser!!.has("data")) parser.getAsJsonObject("data") else JsonObject())
				val view = layoutBuilder.build(
					mainLayout,
					parser.getAsJsonObject("main"),
					data,
					0,
					styles)
				
				mainLayout.addView(view as View)
				
				
				setUpEssential(
					layoutBuilder,
					view,
					parser.getAsJsonObject("main"),
					data,
					this@JsonActivity
				)
			}
		}
		
	}
}
