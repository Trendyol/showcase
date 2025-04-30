package com.trendyol.showcase.ui.slidablecontent

import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.annotation.FontRes
import com.trendyol.showcase.ui.tooltip.TextPosition
import kotlinx.parcelize.Parcelize

@Parcelize
data class SlidableContent(
    var imageUrl: String,
    var title: String?,
    @ColorInt var titleTextColor: Int,
    var titleTextSize: Float,
    var titleTextFontFamily: String,
    @FontRes var titleTextFontFamilyResId: Int?,
    var titleTextStyle: Int,
    var description: String?,
    @ColorInt var descriptionTextColor: Int,
    var descriptionTextSize: Float,
    var descriptionTextFontFamily: String,
    @FontRes var descriptionTextFontFamilyResId: Int?,
    var descriptionTextStyle: Int,
    var textPosition: TextPosition,
) : Parcelable
