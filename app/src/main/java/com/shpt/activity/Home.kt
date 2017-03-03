package com.shpt.activity

import android.app.SearchManager
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.flipkart.android.proteus.builder.LayoutBuilderFactory
import com.flipkart.android.proteus.toolbox.Styles
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.toast
import com.shpt.R
import com.shpt.core.*
import com.shpt.core.callback.EventCallback
import com.shpt.core.db.database
import com.shpt.core.filter.ProductSearchFilter
import com.shpt.core.models.Layout
import com.shpt.core.serviceevent.ConnectionServiceEvent
import com.shpt.core.serviceevent.RetryServiceEvent
import logMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.navigationView
import org.jetbrains.anko.support.v4.drawerLayout


class Home : AppCompatActivity() {

    var menuJson: JsonObject = JsonObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {

            toolbar {
                id = R.id.toolbar
                setTitleTextColor(Color.WHITE)
                backgroundResource = R.color.colorPrimary
                popupTheme = R.style.ThemeOverlay_AppCompat_Light

            }.lparams {
                width = matchParent
                height = dip(56)
            }




            drawerLayout {
                id = R.id.drawer
                verticalLayout {
                    linearLayout {

                        iconButton {
                            text = "{gmd_search}"
                            backgroundColor = Color.WHITE
                            textSize = dip(7).toFloat()
                        }.lparams {
                            width = matchParent
                            weight = 3.5f
                            height = matchParent
                        }

                        autoCompleteTextView {
                            hint = "Search for Books,CD,DVD..."
                            background = null
                            compoundDrawablePadding = dip(10)

                            setAdapter(ProductSearchFilter(applicationContext, android.R.layout.simple_dropdown_item_1line))
                        }.lparams {
                            width = matchParent
                            weight = 1f
                            height = matchParent
                        }


                        iconButton {
                            text = "{gmd_apps}"
                            textSize = dip(8).toFloat()
                            backgroundColor = Color.WHITE
                        }.lparams {
                            width = matchParent
                            weight = 3.5f
                            height = matchParent
                        }



                        background = context.getDrawable(R.drawable.shadow_184454)
                    }.lparams {
                        width = matchParent
                        height = dip(45)
                    }
                    relativeLayout {
                        relativeLayout {
                            id = R.id.mainLayout1

                            progressLine {
                                setBarWidth(dip(3))
                            }.lparams {
                                width = dip(40)
                                height = dip(40)
                                centerHorizontally()
                                centerVertically()
                            }

                        }.lparams(width = matchParent, height = matchParent)

                        textView {
                            id = R.id.event
                            gravity = Gravity.CENTER
                            textColor = Color.WHITE
                            textSize = dip(5).toFloat()
                            visibility = View.GONE

                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            alignParentBottom()
                        }

                        textView {
                            id = R.id.statusText
                            gravity = Gravity.CENTER
                            textColor = Color.BLACK

                        }.lparams {
                            bottomMargin = dip(45)
                            width = wrapContent
                            height = wrapContent
                            alignParentBottom()
                        }

                    }.lparams(width = matchParent, height = matchParent)

                }.lparams(width = matchParent, height = matchParent)

                navigationView {
                    id = R.id.navView

                    inflateMenu(R.menu.default_menu)
                }.lparams {
                    width = wrapContent
                    height = matchParent
                    gravity = Gravity.START
                }
            }.lparams {
                width = matchParent
                height = matchParent
                below(R.id.toolbar)
            }
        }


        setSupportActionBar(find<Toolbar>(R.id.toolbar))
        supportActionBar!!.elevation = 0f
        try {


            doAsync {

                database.use {
                    select("Layout").where("page = {pageName}", "pageName" to "home").exec {
                        val rowParser = classParser<Layout>()
                        val row = parseSingle(rowParser)
                        uiThread {

                            val layoutBuilder = LayoutBuilderFactory().dataParsingLayoutBuilder
                            layoutBuilder.listener = EventCallback(this@Home)


                            registerCustomView(layoutBuilder)


                            val parser = JsonParser()
                            val view = layoutBuilder.build(find<RelativeLayout>(R.id.mainLayout1), parser.parse(row.structure).asJsonObject.getAsJsonObject("main"), JsonObject(), 0, Styles())
                            find<RelativeLayout>(R.id.mainLayout1).removeAllViews()
                            find<RelativeLayout>(R.id.mainLayout1).addView(view as View)


                            if (parser.parse(row.structure).asJsonObject.has("menu")) {
                                menuJson = parser.parse(row.structure).asJsonObject.getAsJsonObject("menu")
                                invalidateOptionsMenu()
                            } else {
                                menuJson = JsonObject()
                            }

                            val actionBarDrawerToggle = object : ActionBarDrawerToggle(this@Home, find<DrawerLayout>(R.id.drawer), find<Toolbar>(R.id.toolbar), R.string.drawer_open, R.string.drawer_close) {

                                override fun onDrawerClosed(drawerView: View) {
                                    super.onDrawerClosed(drawerView)
                                }

                                override fun onDrawerOpened(drawerView: View) {
                                    super.onDrawerOpened(drawerView)
                                }

                            }
                            find<DrawerLayout>(R.id.drawer).setDrawerListener(actionBarDrawerToggle)
                            actionBarDrawerToggle.syncState()



                            find<NavigationView>(R.id.navView).setNavigationItemSelectedListener {

                                find<DrawerLayout>(R.id.drawer).closeDrawers()
                                when (it.itemId) {

                                }
                                true
                            }
                        }

                    }
                }

            }
        } catch (e: Exception) {
            toast(e.cause.toString())
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.default_menu, menu)
        if (menu != null) {
            menu.clear()
            handleMenu(menuJson, menu, find<NavigationView>(R.id.navView).menu, this@Home)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)

        if (Intent.ACTION_SEARCH == intent.action) {
            logMessage("query" + intent.getStringExtra(SearchManager.QUERY))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.default_menu, menu)
        if (menu != null) {
            menu.clear()
            handleMenu(menuJson, menu, find<NavigationView>(R.id.navView).menu, this@Home)
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop()
    }

    @Subscribe public fun retryServiceEvent(event: RetryServiceEvent) {
        find<TextView>(R.id.statusText).text = event.message
    }

    @Subscribe public fun connectionStatus(event: ConnectionServiceEvent) {
        if (event.status) {
            handleConnectionError()
            postEvent(find<TextView>(R.id.event), ContextCompat.getColor(applicationContext, R.color.md_green_400), event.message)
        } else {
            handleConnectionError()
            postEvent(find<TextView>(R.id.event), ContextCompat.getColor(applicationContext, R.color.md_red_500), event.message)
        }
    }

}
