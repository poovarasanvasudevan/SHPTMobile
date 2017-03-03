package com.shpt.core.filter

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import com.google.gson.JsonArray
import com.shpt.core.api.rest
import com.shpt.core.config.Config
import com.shpt.core.models.ProductSearch
import com.shpt.core.parser
import logMessage
import org.jetbrains.anko.*

/**
 * Created by poovarasanv on 3/3/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 3/3/17 at 4:06 PM
 */

class ProductSearchFilter : ArrayAdapter<ProductSearch>, Filterable {
    lateinit var mProducts: List<ProductSearch>

    constructor(context: Context, @LayoutRes resource: Int) : super(context, resource) {
        init()
    }

    constructor(context: Context, @LayoutRes resource: Int, @IdRes textViewResourceId: Int) : super(context, resource, textViewResourceId) {
        init()
    }

    constructor(context: Context, @LayoutRes resource: Int, objects: Array<ProductSearch>) : super(context, resource, objects) {
        init()
    }

    constructor(context: Context, @LayoutRes resource: Int, @IdRes textViewResourceId: Int, objects: Array<ProductSearch>) : super(context, resource, textViewResourceId, objects) {
        init()
    }

    constructor(context: Context, @LayoutRes resource: Int, objects: List<ProductSearch>) : super(context, resource, objects) {
        init()
    }

    constructor(context: Context, @LayoutRes resource: Int, @IdRes textViewResourceId: Int, objects: List<ProductSearch>) : super(context, resource, textViewResourceId, objects) {

        init()
    }

    fun init() {
        mProducts = mutableListOf<ProductSearch>()
    }

    override fun getFilter(): Filter {

        val mFilter = MyFilter()



        return mFilter;
    }

    override fun getCount(): Int {
        return mProducts.size
    }

    override fun getItem(position: Int): ProductSearch {
        return mProducts[position]
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = context.linearLayout {
            textView {
                text = mProducts[position].productName
            }
            padding = dip(5)
        }

        return view

    }


    inner class MyFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = Filter.FilterResults()

            if (constraint != null && !constraint.isBlank() && constraint.length > 2) {
                var term = constraint.toString()

                doAsync {
                    val result: JsonArray = parser.parse(context.rest.getProductSearch(Config.SEARCH_PRODUCT, term).execute().body().string()).asJsonArray

                    uiThread {


//                        result.forEach {
//                            val productId: Int = splitQuery(URL(it.asJsonObject.get("href").asString))["product_id"]!!.toInt()
//                            val productSearch = ProductSearch(0, it.asJsonObject.get("name").asString)
//                            mProducts.toMutableList().add(productSearch)
//                        }
                        logMessage(result.toString())

                        filterResults.count = result.count()
                        filterResults.values = mProducts
                    }
                }
            }

            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}
