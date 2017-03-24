package com.shpt.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.TextView
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.startActivity
import com.mcxiaoke.koi.ext.toast
import com.mcxiaoke.koi.log.logi
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.bladeappcompat.widget.AppProgressBar
import com.shpt.R
import com.shpt.core.LAYOUT_BUILDER_FACTORY
import com.shpt.core.app.BUS
import com.shpt.core.config.Config
import com.shpt.core.data.Constant
import com.shpt.core.db.database
import com.shpt.core.getBackIcon
import com.shpt.core.handleConnectionError
import com.shpt.core.handleMenu
import com.shpt.core.models.Layout
import com.shpt.core.prefs.Prefs
import com.shpt.core.serviceevent.ConnectionServiceEvent
import com.shpt.core.serviceevent.RetryServiceEvent
import com.shpt.uiext.SHPTToolBar
import com.shpt.uiext.SHPTWebView
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.activity_main.*
import logMessage
import org.jetbrains.anko.alert
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SRCMLogin : AppCompatActivity() {

    var menuJson: JsonObject = JsonObject()
    var loginWeb: Int = 0
    lateinit var loginWebView: SHPTWebView
    lateinit var loginProgress: AppProgressBar

    lateinit var menuTop: Menu


    override fun onStart() {
        super.onStart()
        BUS.register(this);
    }

    override fun onStop() {
        BUS.unregister(this);
        super.onStop()
    }

    @Subscribe
    public fun connectionStatus(event: ConnectionServiceEvent) {
        handleConnectionError()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logMessage("Hello")
        if (intent.hasExtra(Constant.PARCEL)) {
            toast(intent.extras.getString(Constant.PARCEL))
        }




        doAsync {
            database.use {
                select("Layout").where("page = {pageTitle}", "pageTitle" to "srcmlogin").exec {

                    val rowParser = classParser<Layout>()
                    val row = parseSingle(rowParser)

                    uiThread {
                        val layoutBuilder = LAYOUT_BUILDER_FACTORY
                        mainLayout.removeAllViews()

                        val parser = JsonParser()
                        val view = layoutBuilder.build(mainLayout, parser.parse(row.structure).asJsonObject.getAsJsonObject("main"), JsonObject(), 0, Styles())

                        mainLayout.addView(view as View)

                        if (parser.parse(row.structure).asJsonObject.has("menu")) {
                            menuJson = parser.parse(row.structure).asJsonObject.getAsJsonObject("menu")
                            invalidateOptionsMenu()
                        } else {
                            menuJson = JsonObject()
                        }

                        val toolbarid = layoutBuilder.getUniqueViewId("toolbar")
                        if (view.findViewById(toolbarid) != null) {
                            val toolbar = view.find<SHPTToolBar>(toolbarid)
                            setSupportActionBar(toolbar)
                            supportActionBar?.setDisplayHomeAsUpEnabled(true)
                            supportActionBar?.setDisplayShowHomeEnabled(true)
                            supportActionBar?.setHomeAsUpIndicator(getBackIcon())
                        }


                        loginWeb = layoutBuilder.getUniqueViewId("loginWeb")
                        if (loginWeb != 0 && view.findViewById(loginWeb) != null) {
                            loginWebView = view.find<SHPTWebView>(loginWeb)
                            loginWebView.setWebViewClient(SHPTWebViewClient())
                        }


                        val loginProgressId = layoutBuilder.getUniqueViewId("loginProgress")
                        if (view.findViewById(loginProgressId) != null) {
                            loginProgress = view.find<AppProgressBar>(loginProgressId)
                        }
                    }

                }
            }
        }
    }

    inner class SHPTWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            loginProgress.visibility = View.VISIBLE

            if (url?.contains("account/edit")!!) {
                loginWebView.visibility = View.GONE
            }

            if (url.contains("common/home")) {
                loginWebView.visibility = View.GONE
            }
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {

            Log.d("LoginWebView", url)

            // logi() { url }
            if (url?.contains("account/edit")!!) {
                loginWebView.visibility = View.GONE
                //No Phone Number go to edit page
                saveCookie(url)
                //startActivity<ProfileUpdate>()
                finish()
            }

            if (url.contains("common/home")) {
                loginWebView.visibility = View.GONE
                //go to home page
                saveCookie(url)
                startActivity<Home>()
                finish()
            }

            if (url.contains("account/account")) {
                loginWebView.visibility = View.GONE
                saveCookie(url)
                startActivity<Home>()
                finish()
            }

            loginProgress.visibility = View.GONE
            loginWebView.visibility = View.VISIBLE
            super.onPageFinished(view, url)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

            return super.shouldOverrideUrlLoading(view, url)
        }


        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            val alertDialog = alert("Error : " + error!!.description) {

                noButton {
                    finish()
                }
            }.show()

            super.onReceivedError(view, request, error)
        }
    }

    fun saveCookie(url: String?) {
        val cookies = CookieManager.getInstance().getCookie(url)

        Log.i("Cookies", cookies)
        val temp = cookies.split(";".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()

        for (ar1 in temp) {
            if (ar1.contains("PHPSESSID")) {
                val temp1 = ar1.split("=".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
                val cookieValue = temp1[1]

                Prefs.with(this).write(Config.COOKIE, cookieValue.trim({ it <= ' ' }))

                logi { cookieValue.trim({ it <= ' ' }) }
            }
        }

    }


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.default_menu, menu)
        if (menu != null) {
            menu.clear()
            handleMenu(menuJson, menu, null, this@SRCMLogin)
        }
        return super.onPrepareOptionsMenu(menu)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.default_menu, menu)
        if (menu != null) {
            menu.clear()
            handleMenu(menuJson, menu, null, this@SRCMLogin)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    @Subscribe fun retryServiceEvent(event: RetryServiceEvent) {
        find<TextView>(R.id.statusText).text = event.message
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
