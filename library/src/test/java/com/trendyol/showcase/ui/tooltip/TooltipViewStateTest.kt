package com.trendyol.showcase.ui.tooltip

import android.view.View
import com.trendyol.showcase.R
import com.trendyol.showcase.`should be`
import com.trendyol.showcase.util.Constants.DEFAULT_ARROW_RESOURCE
import org.junit.Test

class TooltipViewStateTest {

    @Test
    fun `when imageUrl is empty then getImageViewVisibility() returns GONE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(imageUrl = "")

        //then
        val expectedResult = View.GONE
        val actualResult = tooltipViewState.getImageViewVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when imageUrl is not empty then getImageViewVisibility() returns VISIBLE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/7/7c/Aspect_ratio_16_9_example.jpg"
        )

        //then
        val expectedResult = View.VISIBLE
        val actualResult = tooltipViewState.getImageViewVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when textPosition is CENTER then getTextPosition() returns 4`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(textPosition = TextPosition.CENTER)

        //then
        val expectedResult = 4
        val actualResult = tooltipViewState.getTextPosition()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when textPosition is END then getTextPosition() returns 3`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(textPosition = TextPosition.END)

        //then
        val expectedResult = 3
        val actualResult = tooltipViewState.getTextPosition()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when textPosition is not CENTER or END then getTextPosition() returns 2`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(textPosition = TextPosition.START)

        //then
        val expectedResult = 2
        val actualResult = tooltipViewState.getTextPosition()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when titleText is empty then getTitleVisibility() returns false`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(titleText = "")

        //then
        val expectedResult = false
        val actualResult = tooltipViewState.getTitleVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when titleText is not empty then getTitleVisibility() returns true`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(titleText = "Title")

        //then
        val expectedResult = true
        val actualResult = tooltipViewState.getTitleVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when descriptionText is empty then getDescriptionVisibility() returns false`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(descriptionText = "")

        //then
        val expectedResult = false
        val actualResult = tooltipViewState.getDescriptionVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when descriptionText is not empty then getDescriptionVisibility() returns true`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(descriptionText = "Description")

        //then
        val expectedResult = true
        val actualResult = tooltipViewState.getDescriptionVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when arrowResource is DEFAULT_ARROW_RESOURCE then getTopArrowResource() returns ic_arrow_up`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(arrowResource = DEFAULT_ARROW_RESOURCE)

        //then
        val expectedResult = R.drawable.ic_showcase_arrow_up
        val actualResult = tooltipViewState.getArrowResource()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when arrowResource is not DEFAULT_ARROW_RESOURCE then getTopArrowResource() returns arrowResource`() {
        //when
        val givenArrowResource = android.R.drawable.arrow_down_float
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(arrowResource = givenArrowResource)

        //then
        val actualResult = tooltipViewState.getArrowResource()

        actualResult `should be` givenArrowResource
    }

    @Test
    fun `when arrowPosition is UP then getTopArrowVisibility() returns VISIBLE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(absoluteArrowPosition = AbsoluteArrowPosition.UP)

        //then
        val expectedResult = View.VISIBLE
        val actualResult = tooltipViewState.getTopArrowVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when arrowPosition is not UP then getBottomArrowVisibility() returns GONE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(absoluteArrowPosition = AbsoluteArrowPosition.DOWN)

        //then
        val expectedResult = View.GONE
        val actualResult = tooltipViewState.getTopArrowVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when arrowPosition is DOWN then getBottomArrowVisibility() returns VISIBLE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(absoluteArrowPosition = AbsoluteArrowPosition.DOWN)

        //then
        val expectedResult = View.VISIBLE
        val actualResult = tooltipViewState.getBottomArrowVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when arrowPosition is not DOWN then getBottomArrowVisibility() returns GONE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(absoluteArrowPosition = AbsoluteArrowPosition.UP)

        //then
        val expectedResult = View.GONE
        val actualResult = tooltipViewState.getBottomArrowVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `Given isArrowVisible false, When getTopArrowVisibility called, Then it should return GONE`() {
        // Given
        val isArrowVisible = false
        val expectedResult = View.GONE
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(isArrowVisible = isArrowVisible)

        // When
        val actualResult = tooltipViewState.getTopArrowVisibility()

        // Then
        actualResult `should be` expectedResult
    }

    @Test
    fun `Given isArrowVisible false, When getBottomArrowVisibility called, Then it should return GONE`() {
        // Given
        val isArrowVisible = false
        val expectedResult = View.GONE
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(isArrowVisible = isArrowVisible)

        // When
        val actualResult = tooltipViewState.getBottomArrowVisibility()

        // Then
        actualResult `should be` expectedResult
    }

    @Test
    fun `when showCloseButton is true then getCloseButtonVisibility() returns VISIBLE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(showCloseButton = true)

        //then
        val expectedResult = View.VISIBLE
        val actualResult = tooltipViewState.getCloseButtonVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when showCloseButton is false then getCloseButtonVisibility() returns GONE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(showCloseButton = false)

        //then
        val expectedResult = View.GONE
        val actualResult = tooltipViewState.getCloseButtonVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when customContent is not null then isContentVisible() returns GONE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(customContent = 0)

        //then
        val expectedResult = View.GONE
        val actualResult = tooltipViewState.getContentVisibility()

        actualResult `should be` expectedResult
    }

    @Test
    fun `when customContent is null then isContentVisible() returns VISIBLE`() {
        //when
        val tooltipViewState = TooltipViewStateFactory.provideTooltipViewState(customContent = null)

        //then
        val expectedResult = View.VISIBLE
        val actualResult = tooltipViewState.getContentVisibility()

        actualResult `should be` expectedResult
    }
}
