package com.trendyol.showcase.ui.showcase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.trendyol.showcase.showcase.ShowcaseModel
import com.trendyol.showcase.util.ActionType
import com.trendyol.showcase.util.ShowcaseViewRegistry

class ShowcaseActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private var focusedViewId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, 0)

        handler = Handler(Looper.getMainLooper())
        val showcaseModel = intent?.extras?.getParcelable(BUNDLE_KEY) as? ShowcaseModel
        focusedViewId = intent?.getIntExtra(FOCUSED_VIEW_ID_KEY, -1) ?: -1
        showcaseModel?.let { model ->
            val view = ShowcaseView(this).apply {
                setShowcaseModel(model)
                if (focusedViewId != -1) {
                    setFocusedViewId(focusedViewId)
                }
                setClickListener { actionType, index ->
                    finishShowcase(actionType, index)
                }
            }
            setContentView(view)
            if (model.isShowcaseViewVisibleIndefinitely.not()) {
                handler.postDelayed(
                    { finishShowcase(ActionType.EXIT) },
                    model.showDuration
                )
            }
            updateStatusBar(model.isStatusBarVisible)
        }
    }

    fun finishShowcase(actionType: ActionType, index: Int = -1) {
        val bundle = Bundle().apply {
            putSerializable(ShowcaseView.KEY_ACTION_TYPE, actionType)
            putInt(ShowcaseView.KEY_SELECTED_VIEW_INDEX, index)
        }
        handler.removeCallbacksAndMessages(null)
        // Clean up the view registry
        if (focusedViewId != -1) {
            ShowcaseViewRegistry.unregisterView(focusedViewId)
        }
        setResult(Activity.RESULT_OK, Intent().apply { putExtras(bundle) })
        finish()
        overridePendingTransition(0, android.R.anim.fade_out)
    }

    override fun onBackPressed() {
        finishShowcase(ActionType.EXIT)
    }

    private fun updateStatusBar(isStatusBarVisible: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        if (isStatusBarVisible) {
            windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_TOUCH
            windowInsetsController.show(WindowInsetsCompat.Type.statusBars())
        } else {
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
        }
    }

    companion object {

        internal const val BUNDLE_KEY = "bundle_key"
        internal const val FOCUSED_VIEW_ID_KEY = "focused_view_id_key"
    }
}
