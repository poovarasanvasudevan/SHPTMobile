package com.shpt.job

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.RetryConstraint
import com.shpt.core.config.CONTEXT
import com.shpt.core.config.DATABASE
import com.shpt.core.config.KERNEL_UPDATE_PARAMS
import logMessage
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
		try {
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
			
		} catch (e: Exception) {
			logMessage(e.localizedMessage)
		}
	}
	
	override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
		return RetryConstraint.RETRY
	}
	
	override fun onAdded() {
		//TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
	
	override fun onCancel(cancelReason: Int, throwable: Throwable?) {
		//TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
	
	companion object {
		var TAG = "KERNEL_UPDATE"
	}
}


