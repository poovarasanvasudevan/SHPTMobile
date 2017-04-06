package com.shpt.core.ext

/**
 * Created by poovarasanv on 6/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 6/4/17 at 10:41 AM
 */


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.util.*


/**
 * <h1>Nirav Tukadiya</h1>
 *
 *
 *
 *
 
 * @author Neil (Nirav Tukadiya) (niravt@meditab.com | neil.n@meditab.com) Meditab Software Inc.
 * *
 * @version 1.0
 * *
 * @since 24/9/15 10:28 PM
 */
class PermissionHelper(private val mContext: Context?) {
	private var callback: PermissionCallback? = null
	private var mapPermissionsGrants: HashMap<String, PermissionGrant>? = null
	
	/**
	 * Checks whether the permission is granted or not.
	 
	 * @param permission permission string
	 * *
	 * @return true or false
	 */
	fun isPermissionGranted(permission: String?): Boolean {
		return mContext != null && permission != null && ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED
	}
	
	/**
	 * Request permissions
	 
	 * @param permissions        array of permission strings.
	 * *
	 * @param permissionCallback callback
	 */
	@Throws(NullPointerException::class)
	fun requestPermissions(permissions: Array<String>, permissionCallback: PermissionCallback?) {
		this.callback = permissionCallback
		mapPermissionsGrants = HashMap<String, PermissionGrant>()
		val lstToBeRequestedPermissions = ArrayList<String>()
		for (requestedPermission in permissions) {
			
			if (!isPermissionGranted(requestedPermission)) {
				lstToBeRequestedPermissions.add(requestedPermission)
				mapPermissionsGrants!!.put(requestedPermission, PermissionGrant
					.DENIED)
			} else if (isPermissionGranted(requestedPermission)) {
				mapPermissionsGrants!!.put(requestedPermission, PermissionGrant.GRANTED)
			} else if (!ActivityCompat.shouldShowRequestPermissionRationale(
				(mContext as Activity?)!!, requestedPermission)) {
				mapPermissionsGrants!!.put(requestedPermission, PermissionGrant
					.NEVERSHOW)
			}
		}
		
		if (!lstToBeRequestedPermissions.isEmpty()) {
			if (mContext == null) {
				throw NullPointerException("Activity instance cannot be null.")
			} else {
				ActivityCompat.requestPermissions((mContext as Activity?)!!,
					lstToBeRequestedPermissions.toTypedArray(),
					PERMISSION)
			}
		} else {
			permissionCallback?.onResponseReceived(mapPermissionsGrants!!)
		}
		
	}
	
	/**
	 * checks whether permission is granted or not and pass the result to [PermissionCallback]
	 
	 * @param permissions  array of permission strings.
	 * *
	 * @param grantResults results of requested permissions
	 */
	fun onRequestPermissionsResult(permissions: Array<String>, grantResults: IntArray) {
		var index = 0
		for (s in permissions) {
			if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
				mapPermissionsGrants!!.put(s, PermissionGrant.GRANTED)
			} else if (!ActivityCompat.shouldShowRequestPermissionRationale(
				(mContext as Activity?)!!, s)) {
				mapPermissionsGrants!!.put(s, PermissionGrant.NEVERSHOW)
			} else if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
				mapPermissionsGrants!!.put(s, PermissionGrant.DENIED)
			}
			
			index++
		}
		
		if (callback != null) {
			callback!!.onResponseReceived(mapPermissionsGrants!!)
		}
		
	}
	
	
	fun openApplicationSettings() {
		val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
		intent.data = Uri.parse("package:" + mContext!!.packageName)
		mContext.startActivity(intent)
	}
	
	/**
	 * possible values for permissions.
	 *
	 *
	 * [- permission has been granted][.GRANTED]
	 * [- permission has been denied][.DENIED]
	 * [- permission has been denied and never show has been selected.][.NEVERSHOW]
	 */
	enum class PermissionGrant {
		GRANTED, DENIED, NEVERSHOW
	}
	
	companion object {
		
		private val PERMISSION = 100
	}
}
