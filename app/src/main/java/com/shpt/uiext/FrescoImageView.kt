package com.shpt.uiext

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.poovarasan.blade.view.BladeView
import com.poovarasan.blade.view.manager.BladeViewManager

/**
 * Created by poovarasanv on 6/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 6/4/17 at 5:19 PM
 */

class FrescoImageView : ImageView, BladeView {
	
	
	constructor(context: Context) : super(context) {
		init()
	}
	
	constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
		init()
	}
	
	constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
		init()
	}
	
	private var viewManager: BladeViewManager? = null
	override fun getViewManager(): BladeViewManager? {
		return viewManager
	}
	
	override fun setViewManager(bladeViewManager: BladeViewManager) {
		this.viewManager = bladeViewManager
	}
	
	fun init() {
		
	}
}
