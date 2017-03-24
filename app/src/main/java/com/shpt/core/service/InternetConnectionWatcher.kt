package com.shpt.core.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mcxiaoke.koi.ext.isConnected
import com.shpt.core.config.BUS
import com.shpt.core.serviceevent.ConnectionServiceEvent


/**
 * Created by poovarasanv on 10/2/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 10/2/17 at 3:02 PM
 */

class InternetConnectionWatcher : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (context.isConnected()) {
            BUS.post(ConnectionServiceEvent(true, "Connected to Internet"))
        } else {
            BUS.post(ConnectionServiceEvent(false, "Internet UnAvailable"))
        }
    }
}
