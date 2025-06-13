package com.trendyol.showcase.ui

import android.content.res.ColorStateList
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import com.trendyol.showcase.ui.tooltip.AbsoluteArrowPosition
import com.trendyol.showcase.ui.tooltip.TooltipView
import com.trendyol.showcase.ui.tooltip.TooltipViewState

internal fun TooltipView.setTooltipViewState(tooltipViewState: TooltipViewState) {
    bind(tooltipViewState)
}

internal fun TooltipView.placeTooltip(margin: Int, arrowPosition: AbsoluteArrowPosition) {
    if (arrowPosition == AbsoluteArrowPosition.UP) {
        (layoutParams as? ConstraintLayout.LayoutParams)?.apply {
            topToTop = 0 // parent
            bottomToBottom = -1
            topMargin = margin
        }
    } else if (arrowPosition == AbsoluteArrowPosition.DOWN) {
        (layoutParams as? ConstraintLayout.LayoutParams)?.apply {
            topToTop = -1
            bottomToBottom = 0 // parent
            bottomMargin = margin
        }
    }
}

internal fun TextView.setTextSizeInSp(size: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
}

internal fun ImageView.layoutMarginStart(margin: Int, percentage: Int?) {
    (layoutParams as? ConstraintLayout.LayoutParams)?.apply {
        percentage?.let {
            endToEnd = 0
            horizontalBias = (it.toFloat() / 100)
        } ?: run {
            this.marginStart = margin
        }
        this@layoutMarginStart.layoutParams = this
    }
}

internal fun ImageView.setDrawableRes(@DrawableRes drawableRes: Int) {
    setImageDrawable(ContextCompat.getDrawable(context, drawableRes))
}

internal fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

internal fun ImageView.setTint(@ColorInt color: Int) {
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
}

internal fun ImageView.loadImage(imageUrl: String) {
    Glide.with(context).load(imageUrl).into(this)
}
