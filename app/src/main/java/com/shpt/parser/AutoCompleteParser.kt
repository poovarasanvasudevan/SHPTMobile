package com.shpt.parser

import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.jayway.jsonpath.TypeRef
import com.poovarasan.blade.parser.Attributes
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.parser.WrappableParser
import com.poovarasan.blade.processor.JsonDataProcessor
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.blade.view.BladeView
import com.shpt.core.config.JPATH
import com.shpt.core.config.PARSER
import com.shpt.core.config.REST
import com.shpt.uiext.SHPTAutoComplete
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by poovarasanv on 7/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 7/4/17 at 8:41 AM
 */

class AutoCompleteParser(wrappedParser: Parser<AutoCompleteTextView>) : WrappableParser<AutoCompleteTextView>(wrappedParser) {
	
	override fun createView(parent: ViewGroup, layout: JsonObject, data: JsonObject, styles: Styles, index: Int): BladeView {
		return SHPTAutoComplete(parent.context)
	}
	
	override fun prepareHandlers() {
		super.prepareHandlers()
		
		addHandler(Attributes.Attribute("data"), object : JsonDataProcessor<AutoCompleteTextView>() {
			override fun handle(key: String, value: JsonElement, view: AutoCompleteTextView) {
				val dataArray = value.asJsonArray
				val adapterData = arrayOfNulls<String>(dataArray.size())
				for (i in 0..dataArray.size() - 1) {
					adapterData[i] = dataArray.get(i).asString
				}
				
				val arrayAdapter = ArrayAdapter<String>(view.context, android.R.layout.select_dialog_item, adapterData)
				view.setAdapter(arrayAdapter)
			}
		})
		
		addHandler(Attributes.Attribute("dataUrl"), object : JsonDataProcessor<AutoCompleteTextView>() {
			override fun handle(key: String?, value: JsonElement?, view: AutoCompleteTextView?) {
				val jsonData = value!!.asJsonObject
				if (jsonData.has("url")) {
					doAsync {
						val json = REST.get(jsonData.get("url").asString).execute().body().string()
						val jsonParse = PARSER.parse(json).asJsonArray
						uiThread {
							val displayList = arrayListOf<String>()
							if (jsonData.has("displayText")) {
								val typeRef = object : TypeRef<Array<String?>>() {
								}
								displayList.plus(JPATH.parse(jsonParse).read(jsonData.get("displayText").asString, typeRef))
							} else {
								for (i in 0..jsonParse.size() - 1) {
									displayList[i] = jsonParse.get(i).asString
								}
							}
							val arrayAdapter = ArrayAdapter<String>(view!!.context, android.R.layout.select_dialog_item, displayList)
							view.setAdapter(arrayAdapter)
						}
					}
					
					
				}
			}
		})
	}
}
