package com.shpt.core.ext

import com.flipkart.android.proteus.ProteusBuilder
import com.shpt.parser.*


/**
 * Created by poovarasanv on 21/3/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 21/3/17 at 1:41 PM
 */

open class CustomComponentModule : ProteusBuilder.Module {
    companion object {
        private var instance: CustomComponentModule? = null

        @Synchronized
        fun create(): CustomComponentModule {
            if (instance == null) {
                instance = CustomComponentModule()
            }
            return instance!!
        }
    }

    override fun registerWith(builder: ProteusBuilder?) {
        builder?.register(AddToCartButtonParser())
        builder?.register(AppBarParser())
        builder?.register(BigProductViewParser())
        builder?.register(CardViewParser())
        builder?.register(DrawerLayoutParser())
        builder?.register(IconParser())
        builder?.register(ImageViewParser())
        builder?.register(JustifiedTextViewParser())
        builder?.register(NavigationViewParser())
        builder?.register(ProgressWheelParser())
        builder?.register(PullToRefreshParser())
        builder?.register(RippleParser())
        builder?.register(ToolBarParser())
        builder?.register(ViewPagerParser())
        builder?.register(WebViewParser())
    }
}
