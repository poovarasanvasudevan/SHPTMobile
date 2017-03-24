package com.shpt.core.event

import android.content.Context
import com.google.gson.JsonArray
import com.mcxiaoke.koi.ext.toast

/**
 * Created by poovarasanv on 3/2/17.
 * @author poovarasanv
 * @project SHPT
 * @on 3/2/17 at 3:22 PM
 */

class CoreEvents {

    fun makeToast(act: Context, params: JsonArray) {
        act.toast(params.toString())
    }
}
