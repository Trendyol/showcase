package com.trendyol.showcase.ui.tooltip

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.trendyol.showcase.R
import com.trendyol.showcase.databinding.LayoutTooltipBinding
import com.trendyol.showcase.ui.isVisible
import com.trendyol.showcase.ui.layoutMarginStart
import com.trendyol.showcase.ui.loadImage
import com.trendyol.showcase.ui.setTextSizeInSp
import com.trendyol.showcase.ui.setTint
import com.trendyol.showcase.ui.slidablecontent.SlidableContent
import com.trendyol.showcase.ui.slidablecontent.SlidableContentAdapter
import com.trendyol.showcase.util.ActionType
import com.trendyol.showcase.util.TooltipFieldUtil.calculateStartMarginForArrow

class TooltipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private val binding =
        LayoutTooltipBinding.inflate(LayoutInflater.from(context), rootView as ViewGroup)
    private var clickListener: ((ActionType, Int) -> (Unit))? = null

    init {
        with(binding) {
            layoutContents.setOnClickListener {
                sendActionType(ActionType.SHOWCASE_CLICKED)
            }

            imageViewTooltipClose.setOnClickListener {
                sendActionType(ActionType.EXIT)
            }
        }
    }

    internal fun bind(tooltipViewState: TooltipViewState) {
        with(binding) {
            with(textViewTooltipTitle) {
                typeface = Typeface.create(
                    tooltipViewState.getTitleTextFontFamily(),
                    tooltipViewState.getTitleTextStyle()
                )
                text = tooltipViewState.getTitle()
                textAlignment = tooltipViewState.getTextPosition()
                setTextColor(tooltipViewState.getTitleTextColor())
                isVisible = tooltipViewState.getTitleVisibility()
                setTextSizeInSp(tooltipViewState.getTitleTextSize())
            }
            with(textViewTooltipDescription) {
                typeface = Typeface.create(
                    tooltipViewState.getDescriptionTextFontFamily(),
                    tooltipViewState.getDescriptionTextStyle()
                )
                text = tooltipViewState.getDescription()
                textAlignment = tooltipViewState.getTextPosition()
                setTextColor(tooltipViewState.getDescriptionTextColor())
                isVisible = tooltipViewState.getDescriptionVisibility()
                setTextSizeInSp(tooltipViewState.getDescriptionTextSize())
            }

            setupViewPager(tooltipViewState.showcaseModel.slidableContentList.orEmpty())

            with(imageViewTopArrow) {
                visibility = tooltipViewState.getTopArrowVisibility()
                setTint(tooltipViewState.getBackgroundColor())

                val drawable: Drawable = requireNotNull(
                    ContextCompat.getDrawable(
                        context,
                        tooltipViewState.getArrowResource()
                    )
                )
                val imageStartMargin = calculateStartMarginForArrow(
                    tooltipViewState.arrowMargin,
                    drawable
                )
                layoutMarginStart(imageStartMargin, tooltipViewState.getArrowPercentage())
                setImageDrawable(drawable)
            }
            layoutContents.isClickable = tooltipViewState.isShowcaseViewClickable()
            cardContent.visibility = tooltipViewState.getContentVisibility()
            layoutBubble.background = ColorDrawable(tooltipViewState.getBackgroundColor())
            with(imageView) {
                visibility = tooltipViewState.getImageViewVisibility()
                loadImage(tooltipViewState.getImageUrl())
            }
            textViewSlidableContentPosition.isVisible = tooltipViewState.isSlidableContentVisible()
            viewPager.isVisible = tooltipViewState.isSlidableContentVisible()
            with(imageViewTooltipClose) {
                visibility = tooltipViewState.getCloseButtonVisibility()
                setTint(tooltipViewState.getCloseButtonColor())
            }
            with(imageViewBottomArrow) {
                visibility = tooltipViewState.getBottomArrowVisibility()
                setTint(tooltipViewState.getBackgroundColor())
                val drawable: Drawable = requireNotNull(
                    ContextCompat.getDrawable(
                        context,
                        tooltipViewState.getArrowResource()
                    )
                )
                rotation = 180F
                val imageStartMargin = calculateStartMarginForArrow(
                    tooltipViewState.arrowMargin,
                    drawable
                )
                layoutMarginStart(imageStartMargin, tooltipViewState.getArrowPercentage())
                setImageDrawable(drawable)
            }
        }
    }

    private fun setupViewPager(slidableContentList: List<SlidableContent>) {
        with(binding) {
            viewPager.adapter = SlidableContentAdapter(slidableContentList)
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    textViewSlidableContentPosition.text =
                        String.format(
                            resources.getString(R.string.slidable_content_position_text),
                            position + 1,
                            slidableContentList.size
                        )
                    textViewSlidableContentPosition.isVisible(
                        shouldShowSlidableContentPosition(slidableContentList.size)
                    )
                }
            })
        }
    }

    private fun shouldShowSlidableContentPosition(slidableContentListSize: Int): Boolean {
        return slidableContentListSize > 1
    }

    fun setCustomContent(@LayoutRes customContent: Int) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(customContent, null)
        binding.layoutContents.addView(view)
    }

    fun setClickListener(listener: (ActionType, Int) -> (Unit)) {
        clickListener = listener
    }

    private fun sendActionType(actionType: ActionType, index: Int = -1) {
        clickListener?.invoke(actionType, index)
    }
}
