package com.trendyol.showcase.util.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect

open class Shape(screenWidth: Int, screenHeight: Int) {

    val rect = Rect(0, 0, screenWidth, screenHeight)

    protected lateinit var paint: Paint

    open fun draw(windowBackgroundColor: Int, windowBackgroundAlpha: Int, canvas: Canvas) {
        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = windowBackgroundColor
            alpha = windowBackgroundAlpha
        }
        canvas.drawRect(rect, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }
}
