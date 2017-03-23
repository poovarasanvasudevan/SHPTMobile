package com.shpt.core

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.support.design.widget.NavigationView
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.view.ViewManager
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mikepenz.iconics.view.IconicsButton
import com.mikepenz.iconics.view.IconicsImageView
import com.poovarasan.blade.builder.DataParsingLayoutBuilder
import com.poovarasan.blade.builder.LayoutBuilder
import com.poovarasan.blade.builder.LayoutBuilderFactory
import com.poovarasan.blade.module.Module
import com.poovarasan.blade.parser.Parser
import com.poovarasan.bladeappcompat.AppCompatModule
import com.poovarasan.bladecardview.CardViewModule
import com.poovarasan.bladedesign.DesignModule
import com.shpt.R
import com.shpt.core.callback.EventCallback
import com.shpt.core.config.Config
import com.shpt.mobile.widget.ProgressWheel
import com.shpt.mobile.widget.Ripple
import com.shpt.parser.*
import com.shpt.uiext.SHPTAddToCartButton
import com.shpt.uiext.SHPTProgressView
import com.shpt.uiext.SHPTRipple
import com.shpt.uiext.SHPTWebView
import com.shpt.widget.BigProductView
import com.shpt.widget.JustifiedTextView
import com.shpt.widget.Shadow
import org.jetbrains.anko.custom.ankoView

/**
 * Created by poovarasanv on 17/1/17.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 1:59 PM
 */
fun registerCustomView(builder: LayoutBuilder) {
    val progressParser = builder.getHandler("FrameLayout") as Parser<ProgressWheel>
    builder.registerHandler("ProgressWheel", ProgressWheelParser(progressParser))

    val rippleParser = builder.getHandler("FrameLayout") as Parser<Ripple>
    builder.registerHandler("Ripple", RippleParser(rippleParser))


    val webviewParser = builder.getHandler("FrameLayout") as Parser<SHPTWebView>
    builder.registerHandler("Web", WebViewParser(webviewParser))


    val imageviewParser = builder.getHandler("FrameLayout") as Parser<ImageView>
    builder.registerHandler("Image", ImageViewParser(imageviewParser))


    val justifiedviewParser = builder.getHandler("FrameLayout") as Parser<JustifiedTextView>
    builder.registerHandler("JustifiedTextView", JustifiedTextViewParser(justifiedviewParser))


    val iconParser = builder.getHandler("FrameLayout") as Parser<IconicsImageView>
    builder.registerHandler("Icon", IconParser(iconParser))

    val navViewParser = builder.getHandler("FrameLayout") as Parser<NavigationView>
    builder.registerHandler("NavigationView", NavigationViewParser(navViewParser))

    val drawerViewParser = builder.getHandler("FrameLayout") as Parser<DrawerLayout>
    builder.registerHandler("DrawerLayout", DrawerLayoutParser(drawerViewParser))


    val viewPagerParser = builder.getHandler("FrameLayout") as Parser<ViewPager>
    builder.registerHandler("Pager", ViewPagerParser(viewPagerParser))


    val bigProductParser = builder.getHandler("FrameLayout") as Parser<BigProductView>
    builder.registerHandler("BigProduct", BigProductViewParser(bigProductParser))

}

fun postEvent(tv: TextView, bgColor: Int, msg: String) {
    tv.text = msg
    tv.setBackgroundColor(bgColor)
    tv.visibility = View.VISIBLE
    tv.postDelayed({
        tv.visibility = View.GONE
    }, 3000)
}

fun ImageView.setImageURL(imageUrl: String) {
    Glide.with(this.context)
            .load(imageUrl)
            .error(R.drawable.no_image)
            .animate(R.anim.zoomin)
            .into(this)

}

fun View.setVisibility(visibility: Int) {

    val view = this

    this.animate()
            .translationY(this.getHeight().toFloat())
            .alpha(0.0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    view.visibility = visibility
                }
            });
}


fun ImageView.setSHPTImageURL(imageURL: String, imageSize: ImageSize = ImageSize.MEDIUM) {

    //data/2433_FC.jpg

    val manipulatedImage = imageURL.split(".")
    var changedImage = manipulatedImage[0]
    when (imageSize) {
        ImageSize.SMALL -> {
            //74x74
            changedImage += "-74x74"
        }

        ImageSize.MEDIUM -> {
            //200x250
            changedImage += "-200x250"
        }

        ImageSize.LARGE -> {

            //228x228
            changedImage += "-228x228"
        }

        ImageSize.EXTRALARGE -> {
            //550x550
            changedImage += "-550x550"
        }
    }

    val imageFull = Config.IMAGE_URL + changedImage + "." + manipulatedImage[1]

    Glide.with(this.context)
            .load(imageFull)
            .error(R.drawable.no_image)
            .animate(R.anim.zoomin)
            .into(this)


}

inline fun ViewManager.progressLine(theme: Int = 0) = progressLine(theme) {}
inline fun ViewManager.progressLine(theme: Int = 0, init: SHPTProgressView.() -> Unit) = ankoView(::SHPTProgressView, theme, init)

inline fun ViewManager.shptWebView(theme: Int = 0) = shptWebView(theme) {}
inline fun ViewManager.shptWebView(theme: Int = 0, init: SHPTWebView.() -> Unit) = ankoView(::SHPTWebView, theme, init)

inline fun ViewManager.ripple(theme: Int = 0) = ripple(theme) {}
inline fun ViewManager.ripple(theme: Int = 0, init: SHPTRipple.() -> Unit) = ankoView(::SHPTRipple, theme, init)

inline fun ViewManager.addToCart(theme: Int) = addToCart(theme) {}
inline fun ViewManager.addToCart(theme: Int = 0, init: SHPTAddToCartButton.() -> Unit) = ankoView(::SHPTAddToCartButton, theme, init)

inline fun ViewManager.iconButton(theme: Int) = iconButton(theme) {}
inline fun ViewManager.iconButton(theme: Int = 0, init: IconicsButton.() -> Unit) = ankoView(::IconicsButton, theme, init)

inline fun ViewManager.shadow(theme: Int) = shadow(theme) {}
inline fun ViewManager.shadow(theme: Int = 0, init: Shadow.() -> Unit) = ankoView(::Shadow, theme, init)


fun getLayoutBuilder(act: Activity): DataParsingLayoutBuilder {
    val layoutBuilder = LayoutBuilderFactory().dataParsingLayoutBuilder
    layoutBuilder.listener = EventCallback(act)

    val appCompatModule: Module = AppCompatModule()
    appCompatModule.register(layoutBuilder)

    val cardViewModule: Module = CardViewModule()
    cardViewModule.register(layoutBuilder)

    val designModule: Module = DesignModule()
    designModule.register(layoutBuilder)


    registerCustomView(builder = layoutBuilder)
    return layoutBuilder
}


val Activity.LAYOUT_BUILDER_FACTORY: DataParsingLayoutBuilder
    get() = getLayoutBuilder(this)