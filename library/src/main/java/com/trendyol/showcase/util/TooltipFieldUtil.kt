package com.trendyol.showcase.util

import android.graphics.Rect
import android.graphics.drawable.Drawable
import com.trendyol.showcase.showcase.ShowcaseModel
import com.trendyol.showcase.ui.tooltip.AbsoluteArrowPosition
import com.trendyol.showcase.ui.tooltip.ArrowPosition
import kotlin.math.pow
import kotlin.math.sqrt

internal object TooltipFieldUtil {

    fun decideArrowPosition(
        showcaseModel: ShowcaseModel,
        screenHeight: Int
    ): AbsoluteArrowPosition =
        when (showcaseModel.arrowPosition) {
            ArrowPosition.UP -> AbsoluteArrowPosition.UP
            ArrowPosition.DOWN -> AbsoluteArrowPosition.DOWN
            ArrowPosition.AUTO -> calculateArrowPosition(
                showcaseModel.verticalCenter(),
                screenHeight
            )
        }

    private fun calculateArrowPosition(
        verticalCenter: Float,
        screenHeight: Int
    ): AbsoluteArrowPosition =
        if (screenHeight / 2 > verticalCenter) {
            AbsoluteArrowPosition.UP
        } else {
            AbsoluteArrowPosition.DOWN
        }

    fun calculateRadius(rect: Rect): Float {
        val x = rect.width().toDouble() / 2
        val y = rect.height().toDouble() / 2

        return sqrt(x.pow(2) + y.pow(2)).toFloat()
    }

    fun calculateMarginForCircle(
        top: Float,
        bottom: Float,
        arrowPosition: AbsoluteArrowPosition,
        statusBarHeight: Int,
        isStatusBarVisible: Boolean,
        screenHeight: Int
    ): Int = when (arrowPosition) {
        AbsoluteArrowPosition.UP -> bottom.toInt() + if (isStatusBarVisible) statusBarHeight else 0
        AbsoluteArrowPosition.DOWN -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                (screenHeight - top).toInt()
            } else {
                val diff = if (isStatusBarVisible) -statusBarHeight else 0
                (screenHeight - top + diff).toInt()
            }
        }
    }

    fun calculateMarginForRectangle(
        top: Float,
        bottom: Float,
        arrowPosition: AbsoluteArrowPosition,
        statusBarHeight: Int,
        isStatusBarVisible: Boolean,
        screenHeight: Int
    ): Int = when (arrowPosition) {
        AbsoluteArrowPosition.UP -> bottom.toInt() + if (isStatusBarVisible) statusBarHeight else 0
        AbsoluteArrowPosition.DOWN -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                (screenHeight - top).toInt()
            } else {
                val diff = if (isStatusBarVisible) -statusBarHeight else 0
                (screenHeight - top + diff).toInt()
            }
        }
    }

    fun calculateStartMarginForArrow(arrowMargin: Int, resource: Drawable): Int {
        return arrowMargin - (resource.intrinsicWidth) / 2
    }
}
