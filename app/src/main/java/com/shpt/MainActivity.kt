package com.shpt

import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.ext.startActivity
import com.shpt.activity.Login
import com.shpt.activity.SRCMLogin
import com.shpt.core.app.BaseActivity
import com.shpt.core.config.Config
import com.shpt.core.prefs.Prefs
import com.shpt.core.progressLine
import com.shpt.core.serviceevent.RetryServiceEvent
import com.shpt.core.themedColor
import com.shpt.core.updateKernel
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.*


class MainActivity : BaseActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		// setContentView(R.layout.activity_main)
		
		
		relativeLayout {
			
			lparams {
				width = matchParent
				height = matchParent
			}
			
			progressLine {
				barColor = themedColor(color = "colorPrimary")
				barWidth = dip(3)
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
			val returnJson = updateKernel()
			return returnJson
		}
		
		override fun onPostExecute(result: JsonObject?) {
			
			
			if (Prefs.with(this@MainActivity).contains(Config.COOKIE)) {
				startActivity<SRCMLogin>()
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				finish()
			} else {
				
				startActivity<Login>()
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				finish()
			}
			
			super.onPostExecute(result)
		}
		
	}
	
	@Subscribe fun retryServiceEvent(event: RetryServiceEvent) {
		find<TextView>(R.id.statusText).text = event.message
	}
}
