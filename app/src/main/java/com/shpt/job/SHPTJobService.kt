package com.shpt.job

import com.mcxiaoke.koi.ext.toast
import me.tatarka.support.job.JobParameters
import me.tatarka.support.job.JobService

/**
 * Created by poovarasanv on 4/4/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 4/4/17 at 6:16 PM
 */

class SHPTJobService : JobService() {
	override fun onStopJob(params: JobParameters?): Boolean {
		toast("Job Started")
		return true;
	}
	
	override fun onStartJob(params: JobParameters?): Boolean {
		
		jobFinished(params, false)
		return true;
	}
	
}
