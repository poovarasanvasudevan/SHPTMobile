package com.shpt.job

import android.content.Context
import com.evernote.android.job.Job
import com.shpt.core.db.database
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

class KernelUpdateJobScheduler(val ctx: Context) : Job() {

    override fun onRunJob(params: Job.Params): Job.Result {
        try {
            doAsync {
                val returnJson = ctx.readJson()
                ctx.database.use {
                    delete("Layout")
                    delete("Settings")

                    for ((key) in returnJson.entrySet()) {
                        insert("Layout", "page" to key, "structure" to returnJson.getAsJsonObject(key).toString())
                    }
                }
            }

        } catch (e: Exception) {
            return Job.Result.RESCHEDULE
        }


        return Job.Result.SUCCESS
    }

    companion object {
        var TAG = "KERNEL_UPDATE"
    }
}


