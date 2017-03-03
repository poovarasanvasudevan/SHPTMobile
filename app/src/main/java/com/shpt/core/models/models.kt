package com.shpt.core.models

import com.shpt.core.ext.calendar
import org.jetbrains.anko.db.RowParser

/**
 * Created by poovarasanv on 28/2/17.
 * @author poovarasanv
 * @project SHPT
 * @on 28/2/17 at 11:41 AM
 */


class Layout(val _id: Long, val page: String, val structure: String)
class ProductSearch(val productId : Int, val productName: String)

class LayoutRowParser : RowParser<Triple<Int, String, String>> {
    override fun parseRow(columns: Array<Any?>): Triple<Int, String, String> {
        return Triple(columns[0] as Int, columns[1] as String, columns[2] as String)
    }
}


class Product(val product_id: String, val productName: String, val productAmount: String, val productDescription: String, val productImage: String)

class RecentlyViewed(
        val _id: String,
        val productid: Int,
        val productname: String,
        val viewedtime: String = calendar.time.toString()
)

class RecentlySearched(
        val _id: String,
        val productid: Int,
        val productname: String,
        val searchtime: String = calendar.time.toString()
)