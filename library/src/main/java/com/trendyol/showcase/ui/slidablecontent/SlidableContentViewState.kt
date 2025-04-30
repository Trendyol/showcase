package com.trendyol.showcase.ui.slidablecontent

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.trendyol.showcase.ui.tooltip.TextPosition

internal class SlidableContentViewState(val slidableContent: SlidableContent) {

    fun isTitleVisible() = slidableContent.title.isNullOrEmpty().not()

    fun isDescriptionVisible() = slidableContent.description.isNullOrEmpty().not()

    fun getTextPosition(): Int {
        return when (slidableContent.textPosition) {
            TextPosition.CENTER -> 4
            TextPosition.END -> 3
            else -> 2
        }
    }

    fun getTitleTypeface(context: Context): Typeface? {
        return slidableContent.titleTextFontFamilyResId?.let { resId ->
            ResourcesCompat.getFont(context, resId)
        } ?: Typeface.create(slidableContent.titleTextFontFamily, slidableContent.titleTextStyle)
    }

    fun getDescriptionTypeface(context: Context): Typeface? {
        return slidableContent.descriptionTextFontFamilyResId?.let { resId ->
            ResourcesCompat.getFont(context, resId)
        } ?: Typeface.create(slidableContent.descriptionTextFontFamily, slidableContent.descriptionTextStyle)
    }
}