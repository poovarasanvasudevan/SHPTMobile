package com.shpt.job

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.RetryConstraint
import com.shpt.core.config.CONTEXT
import com.shpt.core.config.DATABASE
import com.shpt.core.config.KERNEL_UPDATE_PARAMS
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.doAsync
import readJson

/**
 * Created by poovarasanv on 23/3/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 23/3/17 at 6:30 PM
 */

class KernelUpdateJobScheduler() : Job(KERNEL_UPDATE_PARAMS) {
	override fun onRun() {
		
		doAsync {
			val returnJson = CONTEXT.readJson()
			DATABASE.use {
				delete("Layout")
				delete("Settings")
				
				for ((key) in returnJson.entrySet()) {
					insert("Layout", "page" to key, "structure" to returnJson.getAsJsonObject(key).toString())
				}
			}
		}
	}
	
	override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
		return RetryConstraint.RETRY
	}
	
	override fun onAdded() {
		
	}
	
	override fun onCancel(cancelReason: Int, throwable: Throwable?) {
		
	}
	
	companion object {
		var TAG = "KERNEL_UPDATE"
	}
}


