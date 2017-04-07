package com.shpt.core

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.support.design.widget.NavigationView
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.view.ViewManager
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.pxToDp
import com.mcxiaoke.koi.utils.lollipopOrNewer
import com.mikepenz.iconics.view.IconicsButton
import com.mikepenz.iconics.view.IconicsImageView
import com.poovarasan.blade.builder.DataParsingLayoutBuilder
import com.poovarasan.blade.builder.LayoutBuilder
import com.poovarasan.blade.builder.LayoutBuilderFactory
import com.poovarasan.blade.module.Module
import com.poovarasan.blade.parser.Parser
import com.poovarasan.blade.toolbox.Styles
import com.poovarasan.bladeappcompat.AppCompatModule
import com.poovarasan.bladeappcompat.widget.AppProgressBar
import com.poovarasan.bladeappcompat.widget.AppToolbar
import com.poovarasan.bladecardview.CardViewModule
import com.poovarasan.bladedesign.DesignModule
import com.shpt.R
import com.shpt.core.callback.EventCallback
import com.shpt.core.config.CONTEXT
import com.shpt.core.config.Config
import com.shpt.core.config.DATABASE
import com.shpt.core.config.PARSER
import com.shpt.core.formatter.SessionFormattor
import com.shpt.core.models.Layout
import com.shpt.mobile.widget.Ripple
import com.shpt.parser.*
import com.shpt.uiext.SHPTAddToCartButton
import com.shpt.uiext.SHPTRipple
import com.shpt.uiext.SHPTWebView
import com.shpt.widget.BigProductView
import com.shpt.widget.JustifiedTextView
import com.shpt.widget.Shadow
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import org.jetbrains.anko.displayMetrics

/**
 * Created by poovarasanv on 17/1/17.
 
 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 17/1/17 at 1:59 PM
 */
fun registerCustomView(builder: LayoutBuilder) {
	
	val rippleParser = builder.getHandler("FrameLayout") as Parser<Ripple>
	builder.registerHandler("Ripple", RippleParser(rippleParser))
	
	
	val webviewParser = builder.getHandler("FrameLayout") as Parser<SHPTWebView>
	builder.registerHandler("Web", WebViewParser(webviewParser))
	
	
	
	
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
	
	
	val shptAutoComplete = builder.getHandler("FrameLayout") as Parser<AutoCompleteTextView>
	builder.registerHandler("SHPTAutoComplete", AutoCompleteParser(shptAutoComplete))
	
	
	val imageviewParser = builder.getHandler("FrameLayout") as Parser<ImageView>
	builder.registerHandler("URLImage", ImageViewParser(imageviewParser))
	
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
		.translationY(this.height.toFloat())
		.alpha(0.0f)
		.setDuration(300)
		.setListener(object : AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator?) {
				super.onAnimationEnd(animation)
				view.visibility = visibility
			}
		})
}


fun ImageView.setSHPTImageURL(imageURL: String, imageSize: ImageSize = ImageSize.MEDIUM) {
	
	//data/2433_FC.jpg
	
	val manipulatedImage = imageURL.split(".")
	var changedImage = manipulatedImage[0]
	when (imageSize) {
		ImageSize.SMALL      -> {
			//74x74
			changedImage += "-74x74"
		}
		
		ImageSize.MEDIUM     -> {
			//200x250
			changedImage += "-200x250"
		}
		
		ImageSize.LARGE      -> {
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


fun ViewManager.progressLine(theme: Int = 0) = progressLine(theme) {}
fun ViewManager.progressLine(theme: Int = 0, init: AppProgressBar.() -> Unit) = ankoView(::AppProgressBar, theme, init)

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

inline fun ViewManager.fresco(theme: Int) = shadow(theme) {}
inline fun ViewManager.fresco(theme: Int = 0, init: ImageView.() -> Unit) = ankoView(::ImageView, theme, init)


fun getLayoutBuilder(): DataParsingLayoutBuilder {
	val layoutBuilder = LayoutBuilderFactory().dataParsingLayoutBuilder
	layoutBuilder.listener = EventCallback(CONTEXT)
	layoutBuilder.registerFormatter(SessionFormattor())
	
	/*
	* @see AppCompatModule
	* This Registers Appcompat Module into App Module
	* @author poovarasan
	* */
	val appCompatModule: Module = AppCompatModule()
	appCompatModule.register(layoutBuilder)
	
	val cardViewModule: Module = CardViewModule()
	cardViewModule.register(layoutBuilder)
	
	val designModule: Module = DesignModule()
	designModule.register(layoutBuilder)
	
	
	registerCustomView(builder = layoutBuilder)
	return layoutBuilder
}

/*
* Set up essential components
* like toolbar...
*
* @fix Need to fix menu item component as Sticky (or) has to move into Base Activity
* */
fun setUpEssential(
	layoutBuilder: DataParsingLayoutBuilder,
	view: View,
	viewJson: JsonObject,
	dataJson: JsonObject,
	activity: Activity) {
	
	val toolbarid = layoutBuilder.getUniqueViewId("toolbar")
	if (toolbarid != null && view.findViewById(toolbarid) != null) {
		val toolbar = view.find<AppToolbar>(toolbarid)
		(activity as AppCompatActivity).setSupportActionBar(toolbar)
		(activity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
		(activity).supportActionBar!!.setDisplayShowHomeEnabled(true)
		(activity).supportActionBar!!.setHomeAsUpIndicator(getBackIcon())
	}
}


fun getStyles(): Styles? {
	var styles: Styles? = null
	
	DATABASE.use {
		select("Layout").where("page = {pageName}", "pageName" to "style").exec {
			val rowParser = classParser<Layout>()
			val row = parseSingle(rowParser)
			styles = Gson().fromJson(row.structure, Styles::class.java)
		}
	}
	
	
	
	
	if (styles == null) {
		styles = Gson().fromJson(JsonObject(), Styles::class.java)
	}
	
	return styles
}


fun getGlobalData(): JsonObject {
	var globalData: JsonObject? = null
	
	
	DATABASE.use {
		select("Layout").where("page = {pageName}", "pageName" to "global_data").exec {
			val rowParser = classParser<Layout>()
			val row = parseSingle(rowParser)
			globalData = PARSER.parse(row.structure).asJsonObject
		}
	}
	
	if (globalData == null) {
		globalData = JsonObject()
	}
	
	val fullData = JsonObject()
	fullData.add("global_data", globalData)
	
	return fullData
}

fun getLayout(layoutName: String): Layout? {
	
	var layout: Layout? = null
	
	DATABASE.use {
		select("Layout").where("page = {pageName}", "pageName" to layoutName).exec {
			val rowParser = classParser<Layout>()
			layout = parseSingle(rowParser)
		}
	}
	
	if (layout == null) {
		layout = Layout(123, "", JsonObject().toString())
	}
	
	return layout
}

fun Activity.themedColor(color: String = "colorPrimary"): Int {
	var colorAttr: Int? = null
	
	if (lollipopOrNewer()) {
		when (color) {
			"colorPrimary"     -> colorAttr = android.R.attr.colorPrimary
			"colorPrimaryDark" -> colorAttr = android.R.attr.colorPrimaryDark
			"colorAccent"      -> colorAttr = android.R.attr.colorAccent
			else               -> colorAttr = android.R.attr.colorPrimary
		}
	} else {
		colorAttr = CONTEXT.resources.getIdentifier(color, "attr", CONTEXT.packageName)
	}
	val outValue = TypedValue()
	
	this.theme.resolveAttribute(colorAttr, outValue, true)
	return outValue.data
}


fun wpercent(percentage: Float): Float {
	val dm = CONTEXT.displayMetrics
	val totalWidth = dm.widthPixels.pxToDp()
	return (totalWidth * percentage) / 100
}

fun hpercent(percentage: Float): Float {
	val dm = CONTEXT.displayMetrics
	val totalWidth = dm.heightPixels.pxToDp()
	return (totalWidth * percentage) / 100
}