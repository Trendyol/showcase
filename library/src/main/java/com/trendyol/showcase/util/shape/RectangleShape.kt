package com.trendyol.showcase.util.shape

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF

class RectangleShape(
    screenWidth: Int,
    screenHeight: Int,
    private val left: Float,
    private val top: Float,
    private val right: Float,
    private val bottom: Float,
    private val radiusTopStart: Float,
    private val radiusTopEnd: Float,
    private val radiusBottomEnd: Float,
    private val radiusBottomStart: Float,
) : Shape(screenWidth, screenHeight) {

    private val path = Path()
    private val corners = floatArrayOf(
        radiusTopStart,
        radiusTopStart,
        radiusTopEnd,
        radiusTopEnd,
        radiusBottomEnd,
        radiusBottomEnd,
        radiusBottomStart,
        radiusBottomStart
    )

    override fun draw(windowBackgroundColor: Int, windowBackgroundAlpha: Int, canvas: Canvas) {
        super.draw(windowBackgroundColor, windowBackgroundAlpha, canvas)
        val rectF = RectF(left, top, right, bottom)
        path.addRoundRect(rectF, corners, Path.Direction.CW)
        canvas.drawPath(path, paint)
    }
}
