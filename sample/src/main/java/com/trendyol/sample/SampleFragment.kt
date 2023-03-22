package com.trendyol.sample

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.trendyol.medusalib.navigator.Navigator
import com.trendyol.sample.databinding.FragmentSampleBinding
import com.trendyol.showcase.showcase.ShowcaseManager
import com.trendyol.showcase.ui.showcase.HighlightType
import com.trendyol.showcase.ui.showcase.ShowcaseView
import com.trendyol.showcase.ui.slidablecontent.SlidableContent
import com.trendyol.showcase.ui.slidablecontent.slidableContent
import com.trendyol.showcase.ui.tooltip.ArrowPosition
import com.trendyol.showcase.ui.tooltip.TextPosition
import com.trendyol.showcase.util.ActionType

class SampleFragment : Fragment() {

    private var binding: FragmentSampleBinding? = null

    protected val fragmentNavigator: Navigator?
        get() = (activity as? MainActivity)?.getNavigator()

    private var medusaLifecycleOwner: MedusaLifecycleOwner? = null

    override fun onResume() {
        super.onResume()
        if (medusaLifecycleOwner != null) {
            if (!isHidden && userVisibleHint) {
                medusaLifecycleOwner!!.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (medusaLifecycleOwner != null) {
            if (!isHidden && userVisibleHint) {
                medusaLifecycleOwner!!.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            }
        }
    }

    override fun onDestroyView() {
        if (medusaLifecycleOwner != null) {
            medusaLifecycleOwner!!.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            medusaLifecycleOwner = null
        }
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        if (medusaLifecycleOwner == null) {
            return
        }
        if (!isHidden && userVisibleHint) {
            medusaLifecycleOwner!!.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }
    }

    override fun onStop() {
        if (medusaLifecycleOwner != null) {
            medusaLifecycleOwner!!.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }
        super.onStop()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (medusaLifecycleOwner != null) {
            if (hidden) {
                medusaLifecycleOwner!!.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
            } else {
                medusaLifecycleOwner!!.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (medusaLifecycleOwner != null) {
            if (isVisibleToUser && isResumed) {
                medusaLifecycleOwner!!.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START)
            } else {
                medusaLifecycleOwner!!.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSampleBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        medusaLifecycleOwner = MedusaLifecycleOwner()
        medusaLifecycleOwner!!.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        if (isStatusBarVisible) {
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        with(binding!!) {
            buttonTop.setOnClickListener {
                val context = requireContext()
                ShowcaseManager.Builder()
                    .focus(buttonTop)
                    .titleText("Title For Top!")
                    .descriptionText("Simple, short description for top tooltip.")
                    .titleTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .titleTextFontFamily("sans-serif")
                    .descriptionTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .backgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                    .imageUrl("https://cdn.dsmcdn.com/Assets/t/y/creative/mobile/InstantDelivery/instant-ty-onboarding.png")
                    .showCloseButton(true)
                    .cancellableFromOutsideTouch(true)
                    .arrowPosition(ArrowPosition.AUTO)
                    .highlightType(HighlightType.RECTANGLE)
                    .textPosition(TextPosition.START)
                    .windowBackgroundAlpha(0)
                    .titleTextSize(30f)
                    .statusBarVisible(isStatusBarVisible)
                    .build()
                    .show(
                        this@SampleFragment,
                        REQUEST_CODE_SHOWCASE_CLICKED,
                        medusaLifecycleOwner!!
                    )
                if (isFragmentTransactionTest) {
                    view.postDelayed({
                        fragmentNavigator?.start(OneFragment())
                    }, 3000)
                }
            }

            buttonCenter.setOnClickListener {
                val context = requireContext()
                ShowcaseManager.Builder()
                    .focus(buttonCenter)
                    .titleText("Title For Center!")
                    .descriptionText("Center is here.")
                    .titleTextColor(ContextCompat.getColor(context, R.color.white))
                    .titleTextStyle(Typeface.BOLD)
                    .backgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                    .closeButtonColor(ContextCompat.getColor(context, R.color.white))
                    .showCloseButton(true)
                    .arrowPosition(ArrowPosition.DOWN)
                    .highlightType(HighlightType.CIRCLE)
                    .textPosition(TextPosition.CENTER)
                    .statusBarVisible(isStatusBarVisible)
                    .build()
                    .show(this@SampleFragment, REQUEST_CODE_SHOWCASE_CLICKED, viewLifecycleOwner)
                if (isFragmentTransactionTest) {
                    view.postDelayed({
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.containerMain, OneFragment())
                            .commitAllowingStateLoss()
                    }, 3000)
                }
            }

            buttonBottom.setOnClickListener {
                ShowcaseManager.Builder()
                    .focus(buttonBottom)
                    .titleText("Title without a description")
                    .titleTextSize(16f)
                    .titleTextFontFamily("sans-serif")
                    .titleTextStyle(Typeface.BOLD)
                    .showCloseButton(true)
                    .arrowResource(android.R.drawable.arrow_down_float)
                    .arrowPosition(ArrowPosition.AUTO)
                    .highlightType(HighlightType.RECTANGLE)
                    .highlightPadding(8f)
                    .textPosition(TextPosition.START)
                    .highlightRadius(
                        bottomEndRadius = 16f,
                        topStartRadius = 16f,
                        topEndRadius = 16f,
                        bottomStartRadius = 16f
                    )
                    .cancellableFromOutsideTouch(true)
                    .toolTipVisible(false)
                    .statusBarVisible(isStatusBarVisible)
                    .build()
                    .show(this@SampleFragment, REQUEST_CODE_SHOWCASE_CLICKED, medusaLifecycleOwner!!)
            }

            buttonFocusMultipleView.setOnClickListener {
                ShowcaseManager.Builder()
                    .focus(textView, imageView)
                    .titleText("Multiple View Focus")
                    .titleTextSize(16f)
                    .showCloseButton(true)
                    .arrowPosition(ArrowPosition.UP)
                    .highlightType(HighlightType.RECTANGLE)
                    .highlightPadding(8f)
                    .textPosition(TextPosition.START)
                    .statusBarVisible(isStatusBarVisible)
                    .build()
                    .show(this@SampleFragment, REQUEST_CODE_SHOWCASE_CLICKED, medusaLifecycleOwner!!)
            }

            buttonSlidableContent.setOnClickListener {
                ShowcaseManager.Builder()
                    .focus(buttonSlidableContent)
                    .setSlidableContentList(buildSlidableContentList())
                    .showCloseButton(false)
                    .cancellableFromOutsideTouch(true)
                    .build()
                    .show(this@SampleFragment, REQUEST_CODE_SHOWCASE_CLICKED, medusaLifecycleOwner!!)
            }

            imageTop.setOnClickListener {
                ShowcaseManager.Builder()
                    .focus(imageTop)
                    .customContent(R.layout.view_custom_content)
                    .cancellableFromOutsideTouch(true)
                    .showcaseViewClickable(true)
                    .statusBarVisible(isStatusBarVisible)
                    .build()
                    .show(this@SampleFragment, REQUEST_CODE_SHOWCASE_CLICKED, medusaLifecycleOwner!!)
            }

            buttonVanishingShowcase.setOnClickListener {
                ShowcaseManager.Builder()
                    .focus(buttonVanishingShowcase)
                    .showcaseViewClickable(true)
                    .statusBarVisible(isStatusBarVisible)
                    .showDurationMillis(4000L)
                    .titleText("This showcase will vanish in 4 seconds")
                    .showcaseViewVisibleIndefinitely(false)
                    .build()
                    .show(this@SampleFragment, REQUEST_CODE_SHOWCASE_CLICKED, medusaLifecycleOwner!!)
            }
        }
    }

    private fun buildSlidableContentList(): List<SlidableContent> {
        val context = requireContext()
        val baseSlidableContent = slidableContent {
            imageUrl =
                "https://cdn.dsmcdn.com/Assets/t/y/creative/mobile/InstantDelivery/instant-ty-onboarding.png"
            titleTextColor = ContextCompat.getColor(context, R.color.black)
            titleTextSize = 16f
            titleTextFontFamily = "sans-serif"
            titleTextStyle = Typeface.BOLD
            descriptionTextColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
            descriptionTextSize = 14f
            descriptionTextFontFamily = "sans-serif"
            descriptionTextStyle = Typeface.NORMAL
            textPosition = TextPosition.CENTER
        }

        return with(baseSlidableContent) {
            listOf(
                copy(title = "Title 1", description = "Description 1"),
                copy(title = "Title 2", description = "Description 2"),
                copy(title = "Title 3", description = "Description 3"),
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode && REQUEST_CODE_SHOWCASE_CLICKED == requestCode && data != null) {
            val actionType = data.getSerializableExtra(ShowcaseView.KEY_ACTION_TYPE) as ActionType
            val selectedViewIndex = data.getIntExtra(ShowcaseView.KEY_SELECTED_VIEW_INDEX, -1)
            Log.i(
                "MainActivity",
                "onActivityResult: actionType=${actionType.name} and selected view index=${selectedViewIndex}"
            )
        }
    }

    companion object {
        private const val REQUEST_CODE_SHOWCASE_CLICKED = 101
        private const val isStatusBarVisible = true
        private const val isFragmentTransactionTest = false

        @JvmStatic
        fun newInstance() = SampleFragment()
    }
}
