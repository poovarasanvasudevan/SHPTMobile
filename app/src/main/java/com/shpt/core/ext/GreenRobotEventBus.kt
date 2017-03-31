package com.shpt.core.ext

import android.content.Context
import org.greenrobot.eventbus.EventBus

/**
 * Created by poovarasanv on 28/3/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 28/3/17 at 8:19 AM
 */

class GreenRobotEventBus : BusBase {
	override fun register(ctx: Context) {
		EventBus.getDefault().register(ctx)
	}
	
	override fun unregister(ctx: Context) {
		EventBus.getDefault().unregister(ctx)
	}
	
	override fun getBus(): BusBase {
		return this
	}
	
	override fun post(post: Any) {
		EventBus.getDefault().post(post);
	}
	
}
