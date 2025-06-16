package com.trendyol.showcase.ui.showcase

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.trendyol.showcase.databinding.LayoutShowcaseBinding
import com.trendyol.showcase.showcase.ShowcaseModel
import com.trendyol.showcase.ui.placeTooltip
import com.trendyol.showcase.ui.setTooltipViewState
import com.trendyol.showcase.ui.tooltip.AbsoluteArrowPosition
import com.trendyol.showcase.ui.tooltip.TooltipViewState
import com.trendyol.showcase.util.ActionType
import com.trendyol.showcase.util.OnTouchClickListener
import com.trendyol.showcase.util.TooltipFieldUtil
import com.trendyol.showcase.util.getHeightInPixels
import com.trendyol.showcase.util.isRTL
import com.trendyol.showcase.util.screenWidth
import com.trendyol.showcase.util.shape.CircleShape
import com.trendyol.showcase.util.shape.RectangleShape
import com.trendyol.showcase.util.statusBarHeight

class ShowcaseView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private val binding = LayoutShowcaseBinding.inflate(LayoutInflater.from(context), this, true)
    private var showcaseModel: ShowcaseModel? = null
    private var clickListener: ((ActionType, Int) -> (Unit))? = null
    private var insetTop: Int = 0
    private var insetBottom: Int = 0

    init {
        ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())

            // Always apply the same padding regardless of API level
            view.updatePadding(
                top = systemBars.top,
                bottom = systemBars.bottom,
                left = systemBars.left,
                right = systemBars.right
            )
            insetTop = systemBars.top
            insetBottom = systemBars.bottom

            //post { bind() }
            insets
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        val showcaseModel = this.showcaseModel ?: return super.dispatchDraw(canvas)

        showcaseModel.also { model ->
            val shape = when (model.highlightType) {
                HighlightType.CIRCLE -> {
                    CircleShape(
                        screenWidth = width,
                        screenHeight = height,
                        x = model.horizontalCenter(),
                        y = model.verticalCenter(),
                        radius = model.radius + model.highlightPadding
                    )
                }
                HighlightType.RECTANGLE -> {
                    RectangleShape(
                        screenWidth = width,
                        screenHeight = height,
                        left = model.rectF.left - (model.highlightPadding / 2),
                        top = model.rectF.top - (model.highlightPadding / 2),
                        right = model.rectF.right + (model.highlightPadding / 2),
                        bottom = model.rectF.bottom + (model.highlightPadding / 2),
                        radiusTopStart = model.radiusTopStart,
                        radiusTopEnd = model.radiusTopEnd,
                        radiusBottomEnd = model.radiusBottomEnd,
                        radiusBottomStart = model.radiusBottomStart
                    )
                }
            }
            shape.draw(model.windowBackgroundColor, model.windowBackgroundAlpha, canvas)
        }

        super.dispatchDraw(canvas)
    }

    override fun onAttachedToWindow() {
        bind()
        super.onAttachedToWindow()
    }

    fun setShowcaseModel(model: ShowcaseModel?) {
        showcaseModel = model
        bind()
    }

    fun setClickListener(listener: (ActionType, Int) -> (Unit)) {
        clickListener = listener
        binding.tooltipView.setClickListener(listener)
    }

    private fun bind() {
        val showcaseModel = this.showcaseModel ?: return

        listenClickEvents()
        val arrowPosition = TooltipFieldUtil.decideArrowPosition(showcaseModel, resources.getHeightInPixels())
        val arrowStartMargin = if (context.isRTL()) {
            context.screenWidth() - showcaseModel.horizontalCenter().toInt()
        } else {
            showcaseModel.horizontalCenter().toInt()
        }
        val marginFromBottom = getMarginFromBottom(showcaseModel, arrowPosition)
        val showcaseViewState = ShowcaseViewState(margin = marginFromBottom)
        val tooltipViewState = TooltipViewState(
            showcaseModel = showcaseModel,
            arrowPosition = arrowPosition,
            arrowMargin = arrowStartMargin
        )
        setCustomContent()

        with(binding.tooltipView) {
            isVisible = tooltipViewState.isToolTipVisible()
            placeTooltip(showcaseViewState.margin, tooltipViewState.arrowPosition)
            setTooltipViewState(tooltipViewState)
        }
    }

    private fun getMarginFromBottom(
        showcaseModel: ShowcaseModel,
        arrowPosition: AbsoluteArrowPosition
    ): Int {
        return when (showcaseModel.highlightType) {
            HighlightType.CIRCLE -> TooltipFieldUtil.calculateMarginForCircle(
                top = showcaseModel.topOfCircle(),
                bottom = showcaseModel.bottomOfCircle(),
                arrowPosition = arrowPosition,
                statusBarHeight = statusBarHeight(),
                isStatusBarVisible = showcaseModel.isStatusBarVisible,
                screenHeight = resources.displayMetrics.heightPixels
            )
            HighlightType.RECTANGLE -> TooltipFieldUtil.calculateMarginForRectangle(
                top = showcaseModel.rectF.top,
                bottom = showcaseModel.rectF.bottom,
                arrowPosition = arrowPosition,
                statusBarHeight = statusBarHeight(),
                isStatusBarVisible = showcaseModel.isStatusBarVisible,
                screenHeight = resources.displayMetrics.heightPixels
            )
        }
    }

    private fun listenClickEvents() {
        OnTouchClickListener().apply {
            clickListener = { _, x, y ->
                if (isHighlightedAreaClicked(x, y)) {
                    val index = getClickedViewIndex(x, y)
                    sendActionType(ActionType.HIGHLIGHT_CLICKED, index)
                } else if (isOutsideClickable()) {
                    sendActionType(ActionType.EXIT)
                }
            }
            scrollListener = { view, startX, startY, endX, endY ->
                if (showcaseModel?.cancellableFromScroll == true) {
                    sendActionType(ActionType.EXIT)
                }
            }
        }.also {
            setOnTouchListener(it)
        }
    }

    private fun isOutsideClickable(): Boolean {
        return showcaseModel?.cancellableFromOutsideTouch == true
    }

    private fun isHighlightedAreaClicked(x: Float, y: Float): Boolean {
        return showcaseModel?.let {
            val newRectF = RectF(it.rectF)

            when (it.highlightType) {
                HighlightType.CIRCLE -> {
                    newRectF.left = (it.horizontalCenter() - it.radius - it.highlightPadding)
                    newRectF.right = (it.horizontalCenter() + it.radius + it.highlightPadding)
                    newRectF.top =
                        (it.verticalCenter() - it.radius - it.highlightPadding)
                    newRectF.bottom =
                        (it.verticalCenter() + it.radius + it.highlightPadding)
                }
                HighlightType.RECTANGLE -> {
                    newRectF.left -= (it.highlightPadding / 2)
                    newRectF.right += (it.highlightPadding / 2)
                    newRectF.top -= (it.highlightPadding / 2)
                    newRectF.bottom += (it.highlightPadding / 2)
                }
            }
            newRectF.contains(x, y)
        } ?: false
    }

    private fun getClickedViewIndex(x: Float, y: Float): Int {
        val list = showcaseModel?.highlightedViewsRectFList
        return list?.indexOfFirst { it.contains(x, y) }
            ?: CONST_VIEW_NOT_FOUND
    }

    private fun setCustomContent() {
        val customContent = showcaseModel?.customContent ?: return
        binding.tooltipView.setCustomContent(customContent)
    }

    private fun sendActionType(actionType: ActionType, index: Int = CONST_VIEW_NOT_FOUND) {
        clickListener?.invoke(actionType, index)
    }

    companion object {

        const val KEY_ACTION_TYPE = "action-type"
        const val KEY_SELECTED_VIEW_INDEX = "clicked-view-index"
        private const val CONST_VIEW_NOT_FOUND = -1
    }
}
