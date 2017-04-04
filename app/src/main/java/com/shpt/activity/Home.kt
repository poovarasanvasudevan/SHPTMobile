package com.shpt.activity

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mcxiaoke.koi.adapter.QuickAdapter
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.ext.onTextChange
import com.mcxiaoke.koi.ext.quickAdapterOf
import com.mcxiaoke.koi.ext.toast
import com.shpt.R
import com.shpt.core.*
import com.shpt.core.app.BaseActivity
import com.shpt.core.config.*
import com.shpt.core.models.Layout
import com.shpt.core.models.ProductSearch
import com.shpt.core.serviceevent.RetryServiceEvent
import kotlinx.coroutines.experimental.async
import logMessage
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.design.navigationView
import org.jetbrains.anko.support.v4.drawerLayout
import java.net.URL


class Home : BaseActivity() {
	
	lateinit var productSearchList: ListView
	lateinit var productSearchAdapter: QuickAdapter<ProductSearch>
	
	lateinit var jsonLayout: Layout
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		logMessage("Create Home")
		
		productSearchAdapter = quickAdapterOf<ProductSearch>(android.R.layout.simple_list_item_1) {
			binder, data ->
			binder.setText(android.R.id.text1, data.productName)
		}
		
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
						
						editText {
							hint = "Search for Books,CD,DVD..."
							background = null
							textSize = dip(5).toFloat()
							compoundDrawablePadding = dip(10)
							onTextChange { text, start, before, count ->
								if (isConnected() && count > 2) {
									productSearchList.visibility = View.VISIBLE
//                                    if(find<RelativeLayout>(R.id.mainLayout1) !=null){
//                                        find<RelativeLayout>(R.id.mainLayout1).visibility = View.GONE
//                                    }
									
									updateSearch(text.toString())
								} else {
									productSearchList.visibility = View.GONE
//                                    if(find<RelativeLayout>(R.id.mainLayout1) !=null){
//                                        find<RelativeLayout>(R.id.mainLayout1).visibility = View.VISIBLE
//                                    }
									
								}
							}
							
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
						
						productSearchList = listView {
							adapter = productSearchAdapter
							visibility = View.GONE
						}.lparams {
							width = matchParent
							height = matchParent
						}
						
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
			
			async(context = kotlinx.coroutines.experimental.android.UI) {
				
				jsonLayout = bg {
					getLayout("home")!!
				}.await()
				
				logMessage(jsonLayout.structure);
				super.init(jsonLayout)
				
				val layoutBuilder = LAYOUT_BUILDER_FACTORY
				
				val view = layoutBuilder.build(
					find<RelativeLayout>(R.id.mainLayout1),
					PARSER.parse(jsonLayout.structure).asJsonObject.getAsJsonObject("main"),
					JsonObject(),
					0,
					STYLES.await())
				
				find<RelativeLayout>(R.id.mainLayout1).removeAllViews()
				find<RelativeLayout>(R.id.mainLayout1).addView(view as View)
				
				
				setUpEssential(
					layoutBuilder,
					view,
					PARSER.parse(jsonLayout.structure).asJsonObject.getAsJsonObject("main"),
					JsonObject(),
					this@Home
				)
				
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
		} catch (e: Exception) {
			toast(e.cause.toString())
		}
		
	}
	
	override fun onSaveInstanceState(outState: Bundle?) {
		super.onSaveInstanceState(outState)
		
		outState!!.putString("layout", jsonLayout.structure)
	}
	
	override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
		super.onRestoreInstanceState(savedInstanceState)
		super.refreshMenu(savedInstanceState!!.getString("layout"))
	}
	
	fun updateSearch(term: String) {
		doAsync {
			val result: JsonArray = PARSER.parse(REST.getProductSearch(Config.SEARCH_PRODUCT, term).execute().body().string()).asJsonArray
			uiThread {
				
				val productList = mutableListOf<ProductSearch>()
				result.forEach {
					val productId: String? = splitQuery(URL(it.asJsonObject.get("href").asString.replace("amp;", "")))["product_id"]
					if (productId != null) {
						val productSearch = ProductSearch(productId.toInt(), it.asJsonObject.get("name").asString)
						productList.add(productSearch)
					}
				}
				
				productSearchAdapter.addAll(productList)
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
	
	override fun onStart() {
		logMessage("home start")
		super.onStart()
	}
	
	override fun onPause() {
		logMessage("home pause")
		super.onPause()
	}
	
	override fun onStop() {
		logMessage("home stop")
		super.onStop()
	}
	
	
	override fun onResume() {
		logMessage("home resume")
		super.onResume()
	}
	/******************************************************************************
	 * Events Moniter Background Tasks
	 * It emits from BUS and based on Object EVents it will takes actions
	 *******************************************************************************/
	@Subscribe public fun retryServiceEvent(event: RetryServiceEvent) {
		find<TextView>(R.id.statusText).text = event.message
	}
	
	
	
}
