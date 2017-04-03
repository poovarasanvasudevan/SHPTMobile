package com.shpt.core.app

import android.support.v7.app.AppCompatActivity
import com.mcxiaoke.koi.ext.toast
import com.shpt.core.config.BUS
import com.shpt.core.handleConnectionError
import com.shpt.core.serviceevent.ConnectionServiceEvent
import com.shpt.core.serviceevent.NotificationEvent
import logMessage
import org.greenrobot.eventbus.Subscribe

/**
 * Created by poovarasanv on 3/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 3/4/17 at 6:06 PM
 */

open class BaseActivity : AppCompatActivity() {
	
	override fun onStart() {
		super.onStart()
		BUS.register(this);
	}
	
	override fun onStop() {
		BUS.unregister(this);
		super.onStop()
	}
	
	@Subscribe public fun notificationEvent(event: NotificationEvent) {
		toast(event.message)
	}
	
	@Subscribe public fun connectionStatus(event: ConnectionServiceEvent) {
		logMessage("status Changes")
		handleConnectionError()
	}
}
