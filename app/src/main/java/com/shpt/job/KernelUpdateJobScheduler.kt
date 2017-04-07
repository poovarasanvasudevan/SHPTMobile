package com.shpt.job

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.RetryConstraint
import com.shpt.core.config.KERNEL_UPDATE_PARAMS
import com.shpt.core.updateKernel
import org.jetbrains.anko.doAsync

/**
 * Created by poovarasanv on 23/3/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 23/3/17 at 6:30 PM
 */

class KernelUpdateJobScheduler : Job(KERNEL_UPDATE_PARAMS) {
	override fun onRun() {
		doAsync {
			updateKernel()
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


