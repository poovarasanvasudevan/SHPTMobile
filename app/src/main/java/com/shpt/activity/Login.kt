package com.shpt.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.flipkart.android.proteus.builder.LayoutBuilderFactory
import com.flipkart.android.proteus.toolbox.Styles
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.toast
import com.shpt.R
import com.shpt.core.callback.EventCallback
import com.shpt.core.db.database
import com.shpt.core.models.Layout
import com.shpt.core.registerCustomView
import com.shpt.core.serviceevent.RetryServiceEvent
import com.shpt.uiext.SHPTToolBar
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class Login : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        try {

            doAsync {

                database.use {
                    select("Layout").where("page = {pageName}", "pageName" to "login").exec {
                        val rowParser = classParser<Layout>()
                        val row = parseSingle(rowParser)


                        uiThread {

                            val layoutBuilder = LayoutBuilderFactory().dataParsingLayoutBuilder
                            layoutBuilder.listener = EventCallback(this@Login)
                            mainLayout.removeAllViews()
                            registerCustomView(layoutBuilder)


                            val parser = JsonParser()
                            val view = layoutBuilder.build(mainLayout, parser.parse(row.structure).asJsonObject.getAsJsonObject("main"), JsonObject(), 0, Styles())

                            mainLayout.addView(view as View)

                            //Add Toolbar specifi
                            val toolbarid = layoutBuilder.getUniqueViewId("toolbar")
                            if (toolbarid != null && view.findViewById(toolbarid) != null) {
                                val toolbar = view.find<SHPTToolBar>(toolbarid)
                                setSupportActionBar(toolbar)
                            }
                        }
                    }

                }
            }
        } catch (e: Exception) {
            toast(e.cause.toString())
        }

    }

    @Subscribe fun retryServiceEvent(event: RetryServiceEvent) {
        find<TextView>(R.id.statusText).text = event.message
    }
}
