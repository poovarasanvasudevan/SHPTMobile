package com.shpt.core

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable

/**
 * Created by poovarasanv on 18/1/17.
 * @author poovarasanv
 * @project SHPT
 * @on 18/1/17 at 10:28 AM
 */


fun Context.getBackIcon(): Drawable {
    //GoogleMaterial.Icon.gmd_apps
    return IconicsDrawable(this)
            .icon(GoogleMaterial.Icon.gmd_arrow_back)
            .color(Color.WHITE)
            .sizeDp(18)
}


fun Context.getIcon(iconName: String = "gmd_arrow_back", iconColor: Int = Color.BLACK, size: Int = 18, bgColor: Int = Color.TRANSPARENT): Drawable {

    return IconicsDrawable(this)
            .icon(iconName)
            .backgroundColor(bgColor)
            .color(iconColor)
            .sizeDp(size)
}


fun Context.getMenuIcon(iconName: String): Drawable {
    return getIcon(iconName, Color.WHITE, 18)
}

enum class ImageSize {
    SMALL, MEDIUM, LARGE, EXTRALARGE
}