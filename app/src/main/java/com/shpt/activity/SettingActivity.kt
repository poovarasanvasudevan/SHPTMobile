package com.shpt.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.isConnected
import com.shpt.BuildConfig
import com.shpt.R
import com.shpt.core.updateKernel
import com.shpt.widget.AppCompatPreferenceActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat

/**
 * Created by poovarasanv on 5/4/17.
 * @author poovarasanv
 * @project SHPT
 * @on 5/4/17 at 11:05 AM
 */

class SettingActivity : AppCompatPreferenceActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		setupActionBar()
		supportActionBar.title = "Settings"
		
		addPreferencesFromResource(R.xml.prefs)
		val horizontalMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
			2f, resources.displayMetrics).toInt()
		
		val verticalMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
			2f, resources.displayMetrics).toInt()
		
		val topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
			(20.toInt() + 30).toFloat(),
			resources.displayMetrics).toInt()
		
		listView.setPadding(horizontalMargin, topMargin, horizontalMargin, verticalMargin)
		
		
		val locationTracking = findPreference("locationTracking") as SwitchPreference
		locationTracking.setOnPreferenceChangeListener { preference, newValue ->
			toast(newValue.toString())
			true
		}
		
		val version = findPreference("version")
		version.summary = "You are using Veriosn:${BuildConfig.VERSION_NAME} of shpt.in application"
		
		
		version.setOnPreferenceClickListener {
			indeterminateProgressDialog("Checking for Updates...") {
				
			}.show()
			true
		}
		
		val buildNumber = findPreference("buildNumber")
		buildNumber.summary = "You are using 1.16558 build of shpt.in Application"
		val kUpdateProgressBar: ProgressDialog? = null
		buildNumber.setOnPreferenceClickListener {
			doKernelUpdate()
			true
		}
		
	}
	
	fun doKernelUpdate() {
		if (isConnected()) {
			val progressBar = indeterminateProgressDialog("Syncing to Latest Build...")
			progressBar.show()
			doAsync {
				val returnJson: JsonObject = updateKernel()
				uiThread {
					if (progressBar.isShowing) {
						progressBar.setMessage("Syncing Success...")
						progressBar.dismiss()
						
						toast("Syncing Success...")
					}
				}
			}
		} else {
			alert(Appcompat, "Internet not available. Retry?", "Error") {
				yesButton {
					doKernelUpdate()
				}
				
				noButton {
					this.build().dismiss()
				}
			}
		}
	}
	
	private fun setupActionBar() {
		layoutInflater.inflate(R.layout.toolbar, findViewById(android.R.id.content) as ViewGroup)
		val toolbar = findViewById(R.id.toolbar) as Toolbar
		setSupportActionBar(toolbar)
		val actionBar = supportActionBar
		actionBar.setDisplayHomeAsUpEnabled(true)
	}
	
	override fun onIsMultiPane(): Boolean {
		return isXLargeTablet(this)
	}
	
	class MyPreferenceFragment : PreferenceFragment() {
		override fun onCreate(savedInstanceState: Bundle?) {
			super.onCreate(savedInstanceState)
			addPreferencesFromResource(R.xml.prefs)
		}
	}
	
	/**
	 * Helper method to determine if the device has an extra-large screen. For
	 * example, 10" tablets are extra-large.
	 */
	private fun isXLargeTablet(context: Context): Boolean {
		return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_XLARGE
	}
	
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		return super.onCreateOptionsMenu(menu)
	}
	
	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		when (item!!.itemId) {
			android.R.id.home -> {
				overridePendingTransition(R.anim.fadein, R.anim.fadeout)
				finish()
			}
		}
		
		return super.onOptionsItemSelected(item)
	}
	
	override fun onBackPressed() {
		super.onBackPressed()
		overridePendingTransition(R.anim.fadein, R.anim.fadeout)
	}
}
