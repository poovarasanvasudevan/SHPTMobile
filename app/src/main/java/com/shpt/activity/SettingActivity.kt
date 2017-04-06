package com.shpt.activity

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.preference.SwitchPreference
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.utils.marshmallowOrNewer
import com.shpt.BuildConfig
import com.shpt.R
import com.shpt.core.config.Config
import com.shpt.core.config.JOB_MANAGER
import com.shpt.core.config.PARSER
import com.shpt.core.config.REST
import com.shpt.core.ext.PermissionCallback
import com.shpt.core.ext.PermissionHelper
import com.shpt.job.KernelUpdateJobScheduler
import com.shpt.widget.AppCompatPreferenceActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import java.util.*


/**
 * Created by poovarasanv on 5/4/17.
 * @author poovarasanv
 * @project SHPT
 * @on 5/4/17 at 11:05 AM
 */

class SettingActivity : AppCompatPreferenceActivity() {
	
	var permissionHelper: PermissionHelper? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		setupActionBar()
		supportActionBar.title = "Settings"
		
		
		permissionHelper = PermissionHelper(this)
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
			val isTrue = newValue.toString().toBoolean()
			var returnResult = false;
			if (isTrue && marshmallowOrNewer()) {
				if (!permissionHelper!!.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION) && !permissionHelper!!.isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION)) {
					permissionHelper!!.requestPermissions(arrayOf(
						Manifest.permission.ACCESS_FINE_LOCATION,
						Manifest.permission.ACCESS_COARSE_LOCATION
					), object : PermissionCallback {
						override fun onResponseReceived(mapPermissionGrants: HashMap<String, PermissionHelper.PermissionGrant>) {
							val fine = mapPermissionGrants.get(Manifest.permission.ACCESS_FINE_LOCATION)
							val coarse = mapPermissionGrants.get(Manifest.permission.ACCESS_COARSE_LOCATION)
							
							when (fine) {
								PermissionHelper.PermissionGrant.GRANTED   -> {
									returnResult = true
									toast("Location Enabled")
								}
								
								PermissionHelper.PermissionGrant.DENIED    -> {
									returnResult = false
									toast("Permission Denied")
								}
								
								PermissionHelper.PermissionGrant.NEVERSHOW -> {
									returnResult = false
									alert(Appcompat, "You are not able to Grant Permission here. Press Ok to Grant Permission from App Setting.", "Error") {
										yesButton {
											toast("Enable location Permission")
											permissionHelper!!.openApplicationSettings()
										}
										
										noButton {
											returnResult = false
											this.build().dismiss()
										}
									}.show()
								}
							}
						}
					})
				}
			} else {
				returnResult = true
				toast("Location Sharing Disabled...")
			}
			true
		}
		
		val version = findPreference("version")
		version.summary = "You are using Veriosn:${BuildConfig.VERSION_NAME} of shpt.in application"
		
		
		version.setOnPreferenceClickListener {
			val pg = indeterminateProgressDialog("Checking for Updates...")
			pg.show()
			checkAppUpdate()
			if (pg.isShowing) {
				pg.dismiss()
			}
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
	
	fun checkAppUpdate() {
		doAsync {
			val returnJson = REST.checkUpdate(Config.UPDATE_CHECKER, "com.sahajvani.app").execute()
			
			uiThread {
				if (returnJson.isSuccessful) {
					val returnGson = PARSER.parse(returnJson.body().string()).asJsonObject
					
					if (returnGson.has("package_name") && returnGson.has("status")) {
						if (returnGson.get("version").asString.toFloat() > BuildConfig.VERSION_NAME.toFloat()) {
							alert(Appcompat, "New Version Available", "Update") {
								yesButton {
									toast("Update")
									this.build().dismiss()
								}
								
								noButton {
									this.build().dismiss()
								}
							}.show()
						}
					}
				}
				
			}
		}
	}
	
	fun doKernelUpdate() {
		if (isConnected()) {
			val progressBar = indeterminateProgressDialog("Syncing to Latest Build...")
			progressBar.show()
			JOB_MANAGER.addJobInBackground(KernelUpdateJobScheduler()) { progressBar.setMessage("Syncing Data...") }
			progressBar.dismiss()
			toast("Syncing Success...")
			
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
	
	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		permissionHelper!!.onRequestPermissionsResult(permissions, grantResults)
	}
	
	override fun onBackPressed() {
		super.onBackPressed()
		overridePendingTransition(R.anim.fadein, R.anim.fadeout)
	}
}
