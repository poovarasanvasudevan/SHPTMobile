package com.shpt.core.callback

import com.poovarasan.deepstream.EventListener
import logMessage

/**
 * Created by poovarasanv on 4/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 4/4/17 at 11:41 AM
 */

class DeepstreamCallback : EventListener {
	override fun onEvent(eventName: String?, args: Any?) {
		logMessage(args.toString())
	}
}
