package com.shpt.core.ext

import android.content.Context

/**
 * Created by poovarasanv on 28/3/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 28/3/17 at 8:17 AM
 */

interface BusBase {
	fun register(ctx: Context)
	
	fun unregister(ctx: Context)
	
	fun getBus(): BusBase
	
	fun post(post: Any)
}
