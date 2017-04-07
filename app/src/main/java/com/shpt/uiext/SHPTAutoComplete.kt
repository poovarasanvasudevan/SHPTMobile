package com.shpt.uiext

import android.content.Context
import android.util.AttributeSet
import android.widget.AutoCompleteTextView
import com.poovarasan.blade.view.BladeView
import com.poovarasan.blade.view.manager.BladeViewManager

/**
 * Created by poovarasanv on 7/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 7/4/17 at 8:39 AM
 */

class SHPTAutoComplete : AutoCompleteTextView, BladeView {
	private var viewManager: BladeViewManager? = null
	
	constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
	
	constructor(context: Context) : super(context) {
		
	}
	
	override fun getViewManager(): BladeViewManager? {
		return viewManager
	}
	
	override fun setViewManager(bladeViewManager: BladeViewManager?) {
		this.viewManager = bladeViewManager
	}
	
}
