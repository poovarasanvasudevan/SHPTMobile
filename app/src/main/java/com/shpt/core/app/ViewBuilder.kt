package com.shpt.core.app

import android.app.Activity
import android.support.annotation.Nullable
import com.bumptech.glide.Glide
import com.flipkart.android.proteus.*
import com.flipkart.android.proteus.support.design.DesignModule
import com.flipkart.android.proteus.support.v4.SupportV4Module
import com.flipkart.android.proteus.support.v7.CardViewModule
import com.flipkart.android.proteus.support.v7.RecyclerViewModule
import com.flipkart.android.proteus.value.DrawableValue
import com.flipkart.android.proteus.value.Layout
import com.shpt.core.callback.EventCallback
import com.shpt.core.ext.CustomComponentModule


/**
 * Created by poovarasanv on 21/3/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 21/3/17 at 10:54 AM
 */

class ViewBuilder {
    companion object {
        private var instance: Proteus? = null
        private var proteusContext: ProteusContext? = null

        @Synchronized
        fun getProteusInstance(): Proteus {
            if (instance == null) {
                instance = ProteusBuilder()
                        .register(SupportV4Module.create())
                        .register(RecyclerViewModule.create())
                        .register(CardViewModule.create())
                        .register(DesignModule.create())
                        .register(CustomComponentModule.create())
                        .build()
            }
            return instance!!
        }


        fun getProteusContext(act: Activity, style: Styles, layout: MutableMap<String, Layout>): ProteusContext {
            if (proteusContext == null) {
                proteusContext = getProteusInstance()
                        .createContextBuilder(act.applicationContext)
                        .setLayoutManager(getLayoutManager(layout))
                        .setCallback(getEventCallback(act))
                        .setImageLoader(getImageLoader())
                        .setStyleManager(getStyleManager(style))
                        .build()
            }
            return proteusContext!!
        }

        fun getEventCallback(act: Activity): ProteusLayoutInflater.Callback {
            return EventCallback(act)
        }

        fun getImageLoader(): ProteusLayoutInflater.ImageLoader {
            return ProteusLayoutInflater.ImageLoader { proteusView: ProteusView, s: String, asyncCallback: DrawableValue.AsyncCallback ->
                run {

                    val image = Glide
                            .with(proteusView.viewManager.context)
                            .load(s)
                            .asBitmap()
                            .into(300, 300)
                            .get()

                    asyncCallback.setBitmap(image)
                }
            }
        }

        fun getLayoutManager(layout: MutableMap<String, Layout>): LayoutManager {
            return object : LayoutManager() {
                override fun getLayouts(): MutableMap<String, Layout> {
                    return layout
                }
            }
        }

        fun getStyleManager(styles: Styles): StyleManager {
            return object : StyleManager() {

                @Nullable
                override fun getStyles(): Styles? {
                    return styles
                }
            }
        }
    }
}
