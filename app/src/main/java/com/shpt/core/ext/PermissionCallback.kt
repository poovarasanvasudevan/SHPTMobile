package com.shpt.core.ext

import java.util.*

/**
 * Created by poovarasanv on 6/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 6/4/17 at 10:42 AM
 */

interface PermissionCallback {
	fun onResponseReceived(mapPermissionGrants: HashMap<String, PermissionHelper.PermissionGrant>)
}
