package com.trendyol.showcase.ui.slidablecontent

import com.trendyol.showcase.util.Constants.DEFAULT_DESCRIPTION_TEXT_FONT_FAMILY
import com.trendyol.showcase.util.Constants.DEFAULT_DESCRIPTION_TEXT_SIZE
import com.trendyol.showcase.util.Constants.DEFAULT_DESCRIPTION_TEXT_STYLE
import com.trendyol.showcase.util.Constants.DEFAULT_TEXT_COLOR
import com.trendyol.showcase.util.Constants.DEFAULT_TEXT_POSITION
import com.trendyol.showcase.util.Constants.DEFAULT_TITLE_TEXT_FONT_FAMILY
import com.trendyol.showcase.util.Constants.DEFAULT_TITLE_TEXT_SIZE
import com.trendyol.showcase.util.Constants.DEFAULT_TITLE_TEXT_STYLE

fun slidableContent(build: SlidableContent.() -> Unit): SlidableContent {
    val slidableContent = SlidableContent(
        imageUrl = "",
        title = null,
        titleTextColor = DEFAULT_TEXT_COLOR,
        titleTextSize = DEFAULT_TITLE_TEXT_SIZE,
        titleTextFontFamily = DEFAULT_TITLE_TEXT_FONT_FAMILY,
        titleTextFontFamilyResId = null,
        titleTextStyle = DEFAULT_TITLE_TEXT_STYLE,
        description = null,
        descriptionTextColor = DEFAULT_TEXT_COLOR,
        descriptionTextSize = DEFAULT_DESCRIPTION_TEXT_SIZE,
        descriptionTextFontFamily = DEFAULT_DESCRIPTION_TEXT_FONT_FAMILY,
        descriptionTextFontFamilyResId = null,
        descriptionTextStyle = DEFAULT_DESCRIPTION_TEXT_STYLE,
        textPosition = DEFAULT_TEXT_POSITION
    )
    slidableContent.build()
    return slidableContent
}
