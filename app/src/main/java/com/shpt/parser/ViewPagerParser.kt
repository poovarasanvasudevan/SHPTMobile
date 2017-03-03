package com.shpt.parser

import android.support.v4.view.ViewPager
import android.view.ViewGroup
import com.flipkart.android.proteus.parser.Attributes
import com.flipkart.android.proteus.parser.Parser
import com.flipkart.android.proteus.parser.WrappableParser
import com.flipkart.android.proteus.processor.StringAttributeProcessor
import com.flipkart.android.proteus.toolbox.Styles
import com.flipkart.android.proteus.view.ProteusView
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mcxiaoke.koi.ext.toast
import com.shpt.core.adapter.SHPTPagerAdapter
import com.shpt.core.api.getAdapter
import com.shpt.core.processor.JsonObjectProcessor
import com.shpt.uiext.SHPTViewPager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 2:06 PM
 */

class ViewPagerParser(wrappedParser: Parser<ViewPager>) : WrappableParser<ViewPager>(wrappedParser) {

    override fun createView(viewGroup: ViewGroup, jsonObject: JsonObject, jsonObject1: JsonObject, styles: Styles, i: Int): ProteusView {
        return SHPTViewPager(viewGroup.context)
    }

    override fun prepareHandlers() {
        super.prepareHandlers()


        addHandler(Attributes.Attribute("layout"), object : JsonObjectProcessor<ViewPager>() {
            override fun handle(var1: String?, var2: String?, var3: ViewPager?) {

                try {
                    val pagerJson = JsonParser().parse(var2).asJsonObject

                    if (pagerJson.has("layout_main")) {
                        val layoutJson = pagerJson.getAsJsonArray("layout_main")
                        if (var3 != null) {
                            var3.adapter = SHPTPagerAdapter(layoutJson, var3.context)
                            var3.offscreenPageLimit = layoutJson.size()
                        }
                    }
                } catch(e: Exception) {
                    var3?.toast(e.cause.toString())
                }
            }
        })


        /**
         *
         * Fetch the ViewPager Layout Json fom url and build the View pager Adapter
         *
         * @note : View Pager through url Fetching should take place in Async and Building adapter taks place in mainThread
         * @author Poovarasan Vasudevan
         *
         * */

        addHandler(Attributes.Attribute("layout_url"), object : StringAttributeProcessor<ViewPager>() {
            override fun handle(p0: String?, p1: String?, p2: ViewPager?) {
                try {

                    doAsync {
                        val pagerJson = JsonParser().parse(p2!!.context.getAdapter().getJsonArrayLayout(p1!!).execute().body().string()).asJsonObject

                        uiThread {
                            if (p2 != null && pagerJson.has("layout_main")) {
                                p2.adapter = SHPTPagerAdapter(pagerJson.getAsJsonArray("layout_main"), p2.context)
                                p2.offscreenPageLimit = pagerJson.size()
                            }
                        }
                    }

                } catch(e: Exception) {
                    p2?.toast(e.cause.toString())
                }
            }
        })
    }
}
