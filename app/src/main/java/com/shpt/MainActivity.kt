package com.shpt

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.ext.startActivity
import com.shpt.activity.Login
import com.shpt.activity.SRCMLogin
import com.shpt.core.config.Config
import com.shpt.core.db.database
import com.shpt.core.prefs.Prefs
import com.shpt.core.progressLine
import com.shpt.core.serviceevent.RetryServiceEvent
import logMessage
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import readJson


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        relativeLayout {

            lparams {
                width = matchParent
                height = matchParent
            }

            progressLine {

                setBarWidth(dip(3))
            }.lparams {
                width = dip(40)
                height = dip(45)
                centerHorizontally()
                alignParentBottom()
                bottomMargin = dip(50)
            }
        }

        goToNext()
    }


    fun goToNext() {

        if (!isConnected()) {
            alert("Not Connected to Internet. Retry again?") {
                yesButton {
                    goToNext()
                }
                noButton {
                    finish()
                }
            }.show()
        } else {
            DownloadKernel().execute()
        }

    }

    inner class DownloadKernel : AsyncTask<Void, Void, JsonObject>() {
        override fun doInBackground(vararg params: Void?): JsonObject {
            val returnJson = readJson()
            database.use {
                delete("Layout")
                delete("Settings")

                for ((key) in returnJson.entrySet()) {
                    insert("Layout", "page" to key, "structure" to returnJson.getAsJsonObject(key).toString())
                }
            }

            return returnJson
        }

        override fun onPostExecute(result: JsonObject?) {

            logMessage(result.toString())

            if (Prefs.with(this@MainActivity).contains(Config.COOKIE)) {
                startActivity<SRCMLogin>()
                finish()
            } else {

                startActivity<Login>()
                finish()
            }

            super.onPostExecute(result)
        }

    }


    @Subscribe fun retryServiceEvent(event: RetryServiceEvent) {
        find<TextView>(R.id.statusText).text = event.message
    }
}
