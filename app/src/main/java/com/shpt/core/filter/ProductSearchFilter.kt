package com.shpt.core.filter

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.google.gson.JsonArray
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.inflate
import com.shpt.R
import com.shpt.core.api.rest
import com.shpt.core.config.Config
import com.shpt.core.models.ProductSearch
import com.shpt.core.parser
import com.shpt.core.splitQuery
import logMessage
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

/**
 * Created by poovarasanv on 3/3/17.
 * @author poovarasanv
 * @project SHPT
 * @on 3/3/17 at 4:06 PM
 */

class ProductSearchFilter(context: Context, @LayoutRes resource: Int) : ArrayAdapter<ProductSearch>(context, resource), Filterable {
    var mProducts: List<ProductSearch> = mutableListOf<ProductSearch>()
    override fun getFilter(): Filter = MyFilter()
    override fun getCount(): Int = mProducts.size
    override fun getItem(position: Int): ProductSearch = mProducts[position]
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        logMessage(mProducts[position].productName)
        val view = context.inflate(R.layout.product_search, parent!!,false)
        view.find<TextView>(R.id.productName).text = mProducts[position].productName
        return view
    }


    inner class MyFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = Filter.FilterResults()

            if (constraint != null && !constraint.isBlank() && constraint.length > 2) {
                val term = constraint.toString()

                doAsync {
                    val result: JsonArray = parser.parse(context.rest.getProductSearch(Config.SEARCH_PRODUCT, term).execute().body().string()).asJsonArray

                    uiThread {

                        result.forEach {
                            val productId: String? = splitQuery(URL(it.asJsonObject.get("href").asString.replace("amp;", "")))["product_id"]

                            if (productId != null) {
                                val productSearch = ProductSearch(productId.toInt(), it.asJsonObject.get("name").asString)
                                mProducts.toMutableList().add(productSearch)
                            }

                        }
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
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }

    }

}
