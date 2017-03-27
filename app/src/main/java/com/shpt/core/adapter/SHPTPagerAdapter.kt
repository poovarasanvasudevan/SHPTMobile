package com.shpt.core.adapter

import android.content.Context
import android.os.Parcelable
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.poovarasan.blade.toolbox.Styles
import com.shpt.R
import com.shpt.core.config.LAYOUT_BUILDER_FACTORY
import logMessage

/**
 * Created by poovarasanv on 9/2/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 9/2/17 at 6:19 PM
 */

class SHPTPagerAdapter(private val layoutJson: JsonArray, private val context: Context) : PagerAdapter() {

    lateinit var mainLayout : RelativeLayout

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun getCount(): Int {
        return layoutJson.size()
    }
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val thisJson: JsonObject? = layoutJson.get(position).asJsonObject

        val layout: JsonObject = if (thisJson!!.getAsJsonObject("main") == null) JsonObject() else thisJson.getAsJsonObject("main")
        val dataJson: JsonObject? = if (thisJson.getAsJsonObject("data") == null) JsonObject() else thisJson.getAsJsonObject("data")


        val imageLayout = inflater.inflate(R.layout.activity_main, container, false)
        mainLayout = imageLayout.findViewById(R.id.mainLayout) as RelativeLayout

        logMessage(thisJson.toString())
        val layoutBuilder = LAYOUT_BUILDER_FACTORY
        mainLayout.removeAllViews()
        val view = layoutBuilder.build(mainLayout, layout, dataJson, 0, Styles())

        mainLayout.addView(view as View)

        container.addView(mainLayout)
        return imageLayout
    }
    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }
    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}
    override fun saveState(): Parcelable? {
        return null
    }
}
