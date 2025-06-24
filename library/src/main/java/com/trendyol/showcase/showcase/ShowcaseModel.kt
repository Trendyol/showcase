package com.trendyol.showcase.showcase

import android.graphics.RectF
import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.LayoutRes
import androidx.core.graphics.toRect
import com.trendyol.showcase.ui.slidablecontent.SlidableContent
import com.trendyol.showcase.ui.showcase.HighlightType
import com.trendyol.showcase.ui.tooltip.ArrowPosition
import com.trendyol.showcase.ui.tooltip.TextPosition
import com.trendyol.showcase.util.TooltipFieldUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowcaseModel(
    var rectF: RectF,
    var highlightedViewsRectFList: List<RectF>,
    var radius: Float,
    val titleText: String,
    val descriptionText: CharSequence,
    @ColorInt val titleTextColor: Int,
    @ColorInt val descriptionTextColor: Int,
    @ColorInt val popupBackgroundColor: Int,
    @ColorInt val closeButtonColor: Int,
    val showCloseButton: Boolean,
    val highlightType: HighlightType,
    @DrawableRes val arrowResource: Int,
    val arrowPosition: ArrowPosition,
    val arrowPercentage: Int?,
    val windowBackgroundColor: Int,
    val windowBackgroundAlpha: Int,
    val titleTextSize: Float,
    val titleTextFontFamily: String,
    @FontRes val titleTextFontFamilyResId: Int?,
    val titleTextStyle: Int,
    val descriptionTextSize: Float,
    val descriptionTextFontFamily: String,
    @FontRes val descriptionTextFontFamilyResId: Int?,
    val descriptionTextStyle: Int,
    val highlightPadding: Float,
    val cancellableFromOutsideTouch: Boolean,
    val cancellableFromScroll: Boolean,
    val isShowcaseViewClickable: Boolean,
    val isDebugMode: Boolean,
    val textPosition: TextPosition,
    val imageUrl: String,
    @LayoutRes val customContent: Int?,
    val isStatusBarVisible: Boolean,
    val slidableContentList: List<SlidableContent>?,
    val radiusTopStart: Float,
    val radiusTopEnd: Float,
    val radiusBottomEnd: Float,
    val radiusBottomStart: Float,
    val isToolTipVisible: Boolean,
    val showDuration: Long,
    val isShowcaseViewVisibleIndefinitely: Boolean,
    val isArrowVisible: Boolean,
) : Parcelable {

    fun horizontalCenter() = rectF.left + ((rectF.right - rectF.left) / 2)
    fun verticalCenter() = rectF.top + ((rectF.bottom - rectF.top) / 2)

    fun bottomOfCircle() = verticalCenter() + radius
    fun topOfCircle() = verticalCenter() - radius

    fun updatePositions(newRects: List<RectF>) {
        if (newRects.isNotEmpty()) {
            val unionRect = RectF(newRects[0])
            for (i in 1 until newRects.size) {
                unionRect.union(newRects[i])
            }
            rectF = unionRect
            highlightedViewsRectFList = newRects
            radius = TooltipFieldUtil.calculateRadius(unionRect.toRect())
        }
    }
}
