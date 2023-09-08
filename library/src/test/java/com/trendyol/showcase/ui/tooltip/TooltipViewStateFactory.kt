package com.trendyol.showcase.ui.tooltip

import android.graphics.Color
import android.graphics.RectF
import android.graphics.Typeface
import com.trendyol.showcase.R
import com.trendyol.showcase.showcase.ShowcaseModel
import com.trendyol.showcase.ui.showcase.HighlightType

internal object TooltipViewStateFactory {

    fun provideTooltipViewState(
        titleText: String = "",
        descriptionText: String = "",
        titleTextColor: Int = Color.BLACK,
        descriptionTextColor: Int = Color.BLACK,
        backgroundColor: Int = Color.WHITE,
        closeButtonColor: Int = Color.BLACK,
        showCloseButton: Boolean = true,
        arrowResource: Int = R.drawable.ic_showcase_arrow_up,
        arrowPosition: ArrowPosition = ArrowPosition.DOWN,
        absoluteArrowPosition: AbsoluteArrowPosition = AbsoluteArrowPosition.DOWN,
        arrowPercentage: Int = 50,
        titleTextSize: Float = 16F,
        descriptionTextSize: Float = 14F,
        textPosition: TextPosition = TextPosition.CENTER,
        imageUrl: String = "",
        customContent: Int? = null,
        isStatusBarVisible: Boolean = false,
        arrowMargin: Int = 0
    ): TooltipViewState {
        val showcaseModel = ShowcaseModel(
            rectF = RectF(),
            highlightedViewsRectFList = emptyList(),
            radius = 0f,
            titleText = titleText,
            descriptionText = descriptionText,
            titleTextColor = titleTextColor,
            descriptionTextColor = descriptionTextColor,
            popupBackgroundColor = backgroundColor,
            closeButtonColor = closeButtonColor,
            showCloseButton = showCloseButton,
            highlightType = HighlightType.RECTANGLE,
            arrowResource = arrowResource,
            arrowPosition = arrowPosition,
            arrowPercentage = arrowPercentage,
            windowBackgroundColor = 0,
            windowBackgroundAlpha = 0,
            titleTextSize = titleTextSize,
            descriptionTextSize = descriptionTextSize,
            highlightPadding = 0f,
            cancellableFromOutsideTouch = false,
            isShowcaseViewClickable = false,
            isDebugMode = false,
            textPosition = textPosition,
            imageUrl = imageUrl,
            customContent = customContent,
            isStatusBarVisible = isStatusBarVisible,
            radiusTopStart = 0F,
            radiusBottomEnd = 0F,
            radiusBottomStart = 0F,
            radiusTopEnd = 0F,
            descriptionTextFontFamily = "sans-serif",
            isToolTipVisible = true,
            slidableContentList = listOf(),
            titleTextStyle = Typeface.BOLD,
            descriptionTextStyle = Typeface.NORMAL,
            titleTextFontFamily = "sans-serif",
            showDuration = 2000L,
            isShowcaseViewVisibleIndefinitely = true
        )
        return provideTooltipViewState(showcaseModel, absoluteArrowPosition, arrowMargin)
    }

    private fun provideTooltipViewState(
        showcaseModel: ShowcaseModel,
        arrowPosition: AbsoluteArrowPosition = AbsoluteArrowPosition.DOWN,
        arrowMargin: Int = 0
    ): TooltipViewState {
        return TooltipViewState(
            showcaseModel = showcaseModel,
            arrowPosition = arrowPosition,
            arrowMargin = arrowMargin
        )
    }
}
