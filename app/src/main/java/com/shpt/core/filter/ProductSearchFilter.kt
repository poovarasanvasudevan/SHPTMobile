package com.shpt.core.filter

/**
 * Created by poovarasanv on 3/3/17.
 * @author poovarasanv
 * @project SHPT
 * @on 3/3/17 at 4:06 PM
 */

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filterable
import android.widget.TextView
import org.jetbrains.anko.*

abstract class ProductSearchFilter(ctx: Context, items: List<ProductListItem>) : ArrayAdapter<ProductListItem>(ctx, 0, items), Filterable {
    // Reusable context allows adding multiple views, so it can be used in adapters without overhead
    private val ankoContext = AnkoContext.createReusable(ctx, this)

    protected abstract val listItemClasses: List<Class<out ProductListItem>>

    private val types: Map<Class<out ProductListItem>, Int> by lazy {
        listItemClasses.withIndex().fold(hashMapOf<Class<out ProductListItem>, Int>()) {
            map, t ->
            map.put(t.value, t.index); map
        }
    }

    override fun getViewTypeCount(): Int = types.size
    override fun getItemViewType(position: Int) = types[getItem(position)?.javaClass as Class<out ProductListItem>] ?: 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val item = getItem(position)
        if (item != null) {
            val view = convertView ?: item.createView(ankoContext)
            item.apply(view)
            return view
        } else return convertView
    }
}

interface ProductListItem : AnkoComponent<ProductSearchFilter> {
    fun apply(convertView: View)
}

internal open class TextListItem(val text: String) : ProductListItem {
    protected inline fun createTextView(ui: AnkoContext<ProductSearchFilter>, init: TextView.() -> Unit) = ui.apply {
        textView {
            id = android.R.id.text1
            text = "Text list item" // default text (for the preview)
            init()
        }
    }.view

    override fun createView(ui: AnkoContext<ProductSearchFilter>) = createTextView(ui) {
        gravity = Gravity.CENTER_VERTICAL
        padding = ui.dip(20)
        textSize = 18f
    }

    private fun getHolder(convertView: View): Holder {
        return (convertView.tag as? Holder) ?: Holder(convertView as TextView).apply {
            convertView.tag = this
        }
    }

    override fun apply(convertView: View) {
        val h = getHolder(convertView)
        h.textView.text = text
    }

    internal class Holder(val textView: TextView)
}