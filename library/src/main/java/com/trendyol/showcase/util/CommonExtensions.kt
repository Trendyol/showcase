package com.trendyol.showcase.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.graphics.RectF
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

internal fun Rect.toRectF() = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())

internal fun Resources.getHeightInPixels() = displayMetrics.heightPixels

internal fun Resources.getDensity() = displayMetrics.density

internal fun Context.isRTL() = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL

internal fun Context.screenWidth(): Int {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics().also { windowManager.defaultDisplay.getMetrics(it) }
    return displayMetrics.widthPixels
}