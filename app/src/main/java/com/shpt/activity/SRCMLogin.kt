package com.shpt.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.TextView
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.startActivity
import com.mcxiaoke.koi.ext.toast
import com.mcxiaoke.koi.log.logi
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.bladeappcompat.widget.AppProgressBar
import com.shpt.R
import com.shpt.core.app.BaseActivity
import com.shpt.core.config.Config
import com.shpt.core.config.LAYOUT_BUILDER_FACTORY
import com.shpt.core.config.PARSER
import com.shpt.core.data.Constant
import com.shpt.core.getGlobalData
import com.shpt.core.getLayout
import com.shpt.core.getStyles
import com.shpt.core.models.Layout
import com.shpt.core.prefs.Prefs
import com.shpt.core.serviceevent.RetryServiceEvent
import com.shpt.core.setUpEssential
import com.shpt.uiext.SHPTWebView
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SRCMLogin : BaseActivity() {
	
	var loginWeb: Int = 0
	lateinit var loginWebView: SHPTWebView
	lateinit var loginProgress: AppProgressBar
	
	override fun onStart() {
		super.onStart()
	}
	
	override fun onStop() {
		super.onStop()
	}
	
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		
		if (intent.hasExtra(Constant.PARCEL)) {
			toast(intent.extras.getString(Constant.PARCEL))
		}
		
		
		try {
			
			doAsync {
				
				//fetching Json
				val jsonLayout: Layout = getLayout("srcmlogin")!!
				val styles: Styles = getStyles()!!
				val data = getGlobalData()
				
				uiThread {
					
					super.init(jsonLayout)
					
					val parser = PARSER.parse(jsonLayout.structure).asJsonObject
					val layoutBuilder = LAYOUT_BUILDER_FACTORY
					mainLayout.removeAllViews()
					data.add("local", if (parser.has("data")) parser.getAsJsonObject("data") else JsonObject())
					
					
					val view = layoutBuilder.build(
						mainLayout,
						parser.getAsJsonObject("main"),
						data,
						0,
						styles)
					
					mainLayout.addView(view as View)
					
					setUpEssential(
						view = view,
						layoutBuilder = layoutBuilder,
						viewJson = PARSER.parse(jsonLayout.structure).asJsonObject.getAsJsonObject("main"),
						dataJson = data,
						activity = this@SRCMLogin
					)
					
					
					
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
		} catch (e: Exception) {
			toast(e.localizedMessage)
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
			
			if (url?.contains("account/edit")!!) {
				loginWebView.visibility = View.GONE
				//No Phone Number go to edit page
				saveCookie(url)
				//startActivity<ProfileUpdate>()
				startActivity<Home>()
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				finish()
			}
			
			if (url.contains("common/home")) {
				loginWebView.visibility = View.GONE
				//go to home page
				saveCookie(url)
				startActivity<Home>()
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				finish()
			}
			
			if (url.contains("account/account")) {
				loginWebView.visibility = View.GONE
				saveCookie(url)
				startActivity<Home>()
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
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
			alert("Error : " + error!!.description) {
				
				negativeButton("Close", {
					finish()
				})
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
	
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		return super.onCreateOptionsMenu(menu)
	}
	
	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		return super.onOptionsItemSelected(item)
	}
	
	override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
		return super.onPrepareOptionsMenu(menu)
	}
	
	@Subscribe fun retryServiceEvent(event: RetryServiceEvent) {
		find<TextView>(R.id.statusText).text = event.message
	}
	
	override fun onBackPressed() {
		super.onBackPressed()
	}
}
