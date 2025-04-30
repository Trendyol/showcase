package com.trendyol.showcase.showcase

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.view.View
import androidx.annotation.*
import androidx.annotation.IntRange
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.trendyol.showcase.R
import com.trendyol.showcase.ui.showcase.HighlightType
import com.trendyol.showcase.ui.showcase.ShowcaseActivity
import com.trendyol.showcase.ui.slidablecontent.SlidableContent
import com.trendyol.showcase.ui.tooltip.ArrowPosition
import com.trendyol.showcase.ui.tooltip.TextPosition
import com.trendyol.showcase.util.Constants
import com.trendyol.showcase.util.TooltipFieldUtil
import com.trendyol.showcase.util.toRectF

class ShowcaseManager private constructor(
    private val showcaseModel: ShowcaseModel,
    @StyleRes val resId: Int?
) {

    fun show(
        fragment: FragmentActivity,
        requestCode: Int? = null,
        lifecycleOwner: LifecycleOwner
    ) {
        val stateController = show(fragment, requestCode)
        ShowcaseLifecycleOwner(lifecycleOwner.lifecycle, stateController)
    }

    private fun show(
        activity: FragmentActivity,
        requestCode: Int? = null,
    ): ShowcaseStateController {
        val stateController = ShowcaseStateController(context = activity)
        if (showcaseModel.isDebugMode) return stateController

        val intent = Intent(activity, ShowcaseActivity::class.java)
        val model = if (resId != null) readFromStyle(activity, resId) else showcaseModel
        intent.putExtra(ShowcaseActivity.BUNDLE_KEY, model)

        if (requestCode == null) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, requestCode)
        }
        return stateController
    }

    fun show(
        fragment: Fragment,
        requestCode: Int? = null,
        lifecycleOwner: LifecycleOwner
    ) {
        val stateController = show(fragment, requestCode)
        ShowcaseLifecycleOwner(lifecycleOwner.lifecycle, stateController)
    }

    private fun show(
        fragment: Fragment,
        requestCode: Int? = null,
    ): ShowcaseStateController {
        val stateController = ShowcaseStateController(context = fragment.requireActivity())
        if (showcaseModel.isDebugMode) return stateController
        fragment.activity?.let { activity ->
            val intent = Intent(activity, ShowcaseActivity::class.java)
            val model = if (resId != null) readFromStyle(activity, resId) else showcaseModel
            intent.putExtra(ShowcaseActivity.BUNDLE_KEY, model)

            if (requestCode == null) {
                fragment.startActivity(intent)
            } else {
                fragment.startActivityForResult(intent, requestCode)
            }
        }
        return stateController
    }

    private fun readFromStyle(context: Context, resId: Int): ShowcaseModel {
        val typedArray = context.obtainStyledAttributes(resId, R.styleable.Showcase_Theme)

        return showcaseModel.copy(
            titleTextColor = typedArray.getColor(R.styleable.Showcase_Theme_titleTextColor, showcaseModel.titleTextColor),
            descriptionTextColor = typedArray.getColor(R.styleable.Showcase_Theme_descriptionTextColor, showcaseModel.descriptionTextColor),
            closeButtonColor = typedArray.getColor(R.styleable.Showcase_Theme_closeButtonColor, showcaseModel.closeButtonColor),
            popupBackgroundColor = typedArray.getColor(R.styleable.Showcase_Theme_popupBackgroundColor, showcaseModel.popupBackgroundColor),
            windowBackgroundColor = typedArray.getColor(R.styleable.Showcase_Theme_windowBackgroundColor, showcaseModel.windowBackgroundColor),
            showCloseButton = typedArray.getBoolean(R.styleable.Showcase_Theme_showCloseButton, showcaseModel.showCloseButton),
            cancellableFromOutsideTouch = typedArray.getBoolean(
                R.styleable.Showcase_Theme_cancellableFromOutsideTouch,
                showcaseModel.cancellableFromOutsideTouch
            ),
            isShowcaseViewClickable = typedArray.getBoolean(
                R.styleable.Showcase_Theme_showcaseViewClickable,
                showcaseModel.isShowcaseViewClickable
            ),
            titleTextFontFamily = typedArray.getString(R.styleable.Showcase_Theme_titleTextFontFamily) ?: showcaseModel.titleTextFontFamily,
            titleTextFontFamilyResId = typedArray.getResourceId(R.styleable.Showcase_Theme_titleTextFontFamilyResId, 0)
                .takeIf { it != 0 } ?: showcaseModel.titleTextFontFamilyResId,
            titleTextStyle = typedArray.getInteger(R.styleable.Showcase_Theme_titleStyle, showcaseModel.titleTextStyle),
            descriptionTextFontFamily = typedArray.getString(R.styleable.Showcase_Theme_descriptionTextFontFamily)
                ?: showcaseModel.descriptionTextFontFamily,
            descriptionTextFontFamilyResId = typedArray.getResourceId(R.styleable.Showcase_Theme_descriptionTextFontFamilyResId, 0)
                .takeIf { it != 0 } ?: showcaseModel.descriptionTextFontFamilyResId,
            descriptionTextStyle = typedArray.getInteger(R.styleable.Showcase_Theme_descriptionTextStyle, showcaseModel.descriptionTextStyle)
        ).also {
            typedArray.recycle()
        }
    }

    class Builder {

        private var focusViews: Array<out View>? = null
        private var titleText: String = Constants.DEFAULT_TEXT
        private var descriptionText: CharSequence = Constants.DEFAULT_TEXT
        private var isShowcaseViewVisibleIndefinitely: Boolean = Constants.DEFAULT_SHOW_FOREVER
        private var isArrowVisible: Boolean = Constants.DEFAULT_ARROW_VISIBILITY
        private var showDuration: Long = Constants.DEFAULT_SHOW_DURATION
        @ColorInt
        private var titleTextColor: Int = Constants.DEFAULT_TEXT_COLOR

        @ColorInt
        private var descriptionTextColor: Int = Constants.DEFAULT_TEXT_COLOR

        @ColorInt
        private var popupBackgroundColor: Int = Constants.DEFAULT_POPUP_COLOR
        private var showCloseButton: Boolean = Constants.DEFAULT_CLOSE_BUTTON_VISIBILITY

        @ColorInt
        private var closeButtonColor: Int = Constants.DEFAULT_TEXT_COLOR
        private var highlightType: HighlightType = Constants.DEFAULT_HIGHLIGHT_TYPE

        @DrawableRes
        private var arrowResource: Int = Constants.DEFAULT_ARROW_RESOURCE
        private var arrowPercentage: Int? = null
        private var arrowPosition: ArrowPosition = Constants.DEFAULT_ARROW_POSITION

        @ColorInt
        private var windowBackgroundColor: Int = Constants.DEFAULT_COLOR_BACKGROUND
        private var windowBackgroundAlpha: Int = Constants.DEFAULT_BACKGROUND_ALPHA
        private var titleTextSize: Float = Constants.DEFAULT_TITLE_TEXT_SIZE
        private var titleTextFontFamily: String = Constants.DEFAULT_TITLE_TEXT_FONT_FAMILY
        private var titleTextFontFamilyResId: Int? = null
        private var titleTextStyle: Int = Constants.DEFAULT_TITLE_TEXT_STYLE
        private var descriptionTextSize: Float = Constants.DEFAULT_DESCRIPTION_TEXT_SIZE
        private var descriptionTextFontFamily: String = Constants.DEFAULT_DESCRIPTION_TEXT_FONT_FAMILY
        private var descriptionTextFontFamilyResId: Int? = null
        private var descriptionTextStyle: Int = Constants.DEFAULT_DESCRIPTION_TEXT_STYLE
        private var highlightPadding: Float = Constants.DEFAULT_HIGHLIGHT_PADDING_EXTRA

        @StyleRes
        private var resId: Int? = null
        private var cancellableFromOutsideTouch: Boolean =
            Constants.DEFAULT_CANCELLABLE_FROM_OUTSIDE_TOUCH
        private var isShowcaseViewClickable: Boolean = Constants.DEFAULT_SHOWCASE_VIEW_CLICKABLE
        private var isDebugMode: Boolean = false
        private var textPosition: TextPosition = Constants.DEFAULT_TEXT_POSITION
        private var imageUrl: String = Constants.DEFAULT_TEXT
        private var radiusTopStart: Float = Constants.DEFAULT_HIGHLIGHT_RADIUS
        private var radiusTopEnd: Float = Constants.DEFAULT_HIGHLIGHT_RADIUS
        private var radiusBottomStart: Float = Constants.DEFAULT_HIGHLIGHT_RADIUS
        private var radiusBottomEnd: Float = Constants.DEFAULT_HIGHLIGHT_RADIUS
        private var isToolTipVisible: Boolean = true

        @LayoutRes
        private var customContent: Int? = null

        private var isStatusBarVisible: Boolean = true

        private var slidableContentList: List<SlidableContent>? = null

        fun focus(view: View) = apply { focusViews = arrayOf(view) }

        fun focus(vararg view: View) = apply { focusViews = view }

        fun titleText(title: String) = apply { titleText = title }

        fun titleTextColor(@ColorInt color: Int) = apply { titleTextColor = color }

        /**
         * Assigns fontFamily like sans-serif, sans-serif-medium. By default, sans-serif is used.
         *
         * @param fontFamily assigns fontFamily to titleText
         */
        fun titleTextFontFamily(fontFamily: String) = apply { titleTextFontFamily = fontFamily }

        /**
         * Sets the title font family using a font resource ID
         *
         * @param resId font resId
         */
        fun titleTextFontFamilyResId(@FontRes resId: Int) = apply { titleTextFontFamilyResId = resId }

        /**
         * Controls whether showcase should be shown indefinitely or not. By default it is true.
         *
         * @param isVisibleIndefinitely assigns boolean value to showForever.
         */
        fun showcaseViewVisibleIndefinitely(isVisibleIndefinitely: Boolean) =
            apply { isShowcaseViewVisibleIndefinitely = isVisibleIndefinitely }

        /**
         * Controls whether arrow icon should be shown or not. By default it is true.
         *
         * @param isVisible assigns boolean value to isArrowVisible.
         */
        fun setArrowVisibility(isVisible: Boolean) = apply { isArrowVisible = isVisible }

        /**
         * Assign duration value to showDuration. By default it is 2000L.
         * To see the effect, set [showcaseViewVisibleIndefinitely] to false.
         *
         * @param duration assigns duration value to showDuration
         */
        fun showDurationMillis(@IntRange(from = 0) duration: Long) =
            apply { showDuration = duration }

        /**
         * Assign textStyle to titleText
         *
         * @param textStyle can only be style parameters in Typeface. It should be in the range of NORMAL AND BOLD_ITALIC.
         */
        fun titleTextStyle(@IntRange(from = Typeface.NORMAL.toLong(), to = Typeface.BOLD_ITALIC.toLong()) textStyle: Int) =
            apply { titleTextStyle = textStyle }

        fun descriptionText(description: CharSequence) = apply { descriptionText = description }

        fun descriptionTextColor(@ColorInt color: Int) = apply { descriptionTextColor = color }

        /**
         * Assigns fontFamily like sans-serif, sans-serif-medium. By default, sans-serif is used.
         *
         * @param fontFamily assigns fontFamily to descriptionText
         */
        fun descriptionTextFontFamily(fontFamily: String) =
            apply { descriptionTextFontFamily = fontFamily }

        /**
         * Sets the description font family using a font resource ID
         *
         * @param resId font resId
         */
        fun descriptionTextFontFamilyResId(@FontRes resId: Int) = apply { 
            descriptionTextFontFamilyResId = resId
        }

        /**
         * Assign textStyle to descriptionText
         *
         * @param textStyle can only be style parameters in Typeface. It should be in the range of NORMAL AND BOLD_ITALIC.
         */
        fun descriptionTextStyle(@IntRange(from = Typeface.NORMAL.toLong(), to = Typeface.BOLD_ITALIC.toLong()) textStyle: Int) =
            apply { descriptionTextStyle = textStyle }

        fun backgroundColor(@ColorInt color: Int) = apply { popupBackgroundColor = color }

        fun closeButtonColor(@ColorInt color: Int) = apply { closeButtonColor = color }

        fun showCloseButton(show: Boolean) = apply { showCloseButton = show }

        /**Custom icon resource for arrow.**/
        fun arrowResource(@DrawableRes resource: Int) = apply { arrowResource = resource }

        fun arrowPosition(position: ArrowPosition) = apply { arrowPosition = position }

        /**
         *
         * Custom positioning for arrow.
         */
        fun arrowPercentage(@IntRange(from = 0, to = 100) percentage: Int) = apply { arrowPercentage = percentage }

        fun highlightType(type: HighlightType) = apply { highlightType = type }

        /**
         *
         * Extra padding for highlight area.
         */
        fun highlightPadding(padding: Float) = apply { highlightPadding = padding }

        fun windowBackgroundColor(@ColorInt color: Int) = apply { windowBackgroundColor = color }
        fun windowBackgroundAlpha(@IntRange(from = 0, to = 255) alpha: Int) = apply { windowBackgroundAlpha = alpha }

        /**
         *
         * titleTextSize in SP.
         */
        fun titleTextSize(size: Float) = apply { titleTextSize = size }

        /**
         *
         * descriptionTextSize in SP.
         */
        fun descriptionTextSize(size: Float) = apply { descriptionTextSize = size }

        /**
         *
         * Resource id of an custom style named Showcase.Theme in project.
         */
        fun resId(@StyleRes res: Int) = apply { resId = res }

        fun cancellableFromOutsideTouch(cancellable: Boolean) = apply { cancellableFromOutsideTouch = cancellable }

        /**
         * Makes the showcase view clickable or not.
         *
         * Default is not clickable
         *
         * @param clickable Set the value to true so that the showcase view is clickable
         */
        fun showcaseViewClickable(clickable: Boolean) =
            apply { isShowcaseViewClickable = clickable }

        /**
         *
         * radius for highlight area.
         */
        fun highlightRadius(
            topStartRadius: Float = Constants.DEFAULT_HIGHLIGHT_RADIUS,
            topEndRadius: Float = Constants.DEFAULT_HIGHLIGHT_RADIUS,
            bottomStartRadius: Float = Constants.DEFAULT_HIGHLIGHT_RADIUS,
            bottomEndRadius: Float = Constants.DEFAULT_HIGHLIGHT_RADIUS
        ) = apply {
            radiusTopStart = topStartRadius
            radiusTopEnd = topEndRadius
            radiusBottomStart = bottomStartRadius
            radiusBottomEnd = bottomEndRadius
        }

        /**
         *
         * show toolTip or not.
         */
        fun toolTipVisible(isToolTipVisible: Boolean) =
            apply { this.isToolTipVisible = isToolTipVisible }

        /**
         * Running in debug mode or not
         */
        fun isDebugMode(isDebug: Boolean) = apply { isDebugMode = isDebug }

        /**
         *
         * Custom positioning for text.
         */
        fun textPosition(position: TextPosition) = apply { textPosition = position }

        fun imageUrl(url: String) = apply { imageUrl = url }

        fun customContent(@LayoutRes content: Int) = apply { customContent = content }

        fun statusBarVisible(isStatusBarVisible: Boolean) = apply { this.isStatusBarVisible = isStatusBarVisible }

        fun setSlidableContentList(slidableContentList: List<SlidableContent>) =
            apply { this.slidableContentList = slidableContentList }

        fun build(): ShowcaseManager {
            if (focusViews.isNullOrEmpty()) {
                throw Exception("view should not be null!")
            }

            val rect = Rect()
            val highlightedViewsRectFList = mutableListOf<RectF>()
            focusViews!!.forEach {
                val viewRect = Rect()
                it.getGlobalVisibleRect(viewRect)
                highlightedViewsRectFList.add(viewRect.toRectF())
                rect.union(viewRect)
            }

            val showcaseModel = ShowcaseModel(
                rectF = rect.toRectF(),
                highlightedViewsRectFList = highlightedViewsRectFList,
                radius = TooltipFieldUtil.calculateRadius(rect),
                titleText = titleText,
                descriptionText = descriptionText,
                titleTextColor = titleTextColor,
                descriptionTextColor = descriptionTextColor,
                popupBackgroundColor = popupBackgroundColor,
                closeButtonColor = closeButtonColor,
                showCloseButton = showCloseButton,
                highlightType = highlightType,
                arrowResource = arrowResource,
                arrowPosition = arrowPosition,
                arrowPercentage = arrowPercentage,
                windowBackgroundColor = windowBackgroundColor,
                windowBackgroundAlpha = windowBackgroundAlpha,
                titleTextSize = titleTextSize,
                titleTextFontFamily = titleTextFontFamily,
                titleTextFontFamilyResId = titleTextFontFamilyResId,
                titleTextStyle = titleTextStyle,
                descriptionTextSize = descriptionTextSize,
                descriptionTextFontFamily = descriptionTextFontFamily,
                descriptionTextFontFamilyResId = descriptionTextFontFamilyResId,
                descriptionTextStyle = descriptionTextStyle,
                highlightPadding = highlightPadding,
                cancellableFromOutsideTouch = cancellableFromOutsideTouch,
                isShowcaseViewClickable = isShowcaseViewClickable,
                isDebugMode = isDebugMode,
                textPosition = textPosition,
                imageUrl = imageUrl,
                customContent = customContent,
                isStatusBarVisible = isStatusBarVisible,
                slidableContentList = slidableContentList,
                radiusTopStart = radiusTopStart,
                radiusTopEnd = radiusTopEnd,
                radiusBottomEnd = radiusBottomEnd,
                radiusBottomStart = radiusBottomStart,
                isToolTipVisible = isToolTipVisible,
                showDuration = showDuration,
                isShowcaseViewVisibleIndefinitely = isShowcaseViewVisibleIndefinitely,
                isArrowVisible = isArrowVisible,
            )

            return ShowcaseManager(showcaseModel = showcaseModel, resId = resId)
        }
    }
}
