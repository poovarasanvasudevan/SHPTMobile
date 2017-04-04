package com.shpt.core.mqtt

import com.poovarasan.deepstream.DeepstreamClient
import com.shpt.core.callback.DeepstreamCallback


/**
 * Created by poovarasanv on 4/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 4/4/17 at 11:29 AM
 */

object Deepstream {
	
	internal var deepstreamClient: DeepstreamClient? = null;
	
	fun getClient(): DeepstreamClient {
		if (deepstreamClient == null) {
			deepstreamClient = DeepstreamClient("localhost:8099")
			
			//	val r = deepstreamClient!!.record.getRecord("driver/14")
			//	r.subscribe("message") { recordName, path, data -> logMessage(data.toString()) }
			
			deepstreamClient!!.event.subscribe("message", DeepstreamCallback())
		}
		
		return deepstreamClient!!
	}
}
