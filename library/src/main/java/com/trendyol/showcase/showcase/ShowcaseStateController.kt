package com.trendyol.showcase.showcase

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.trendyol.showcase.ui.showcase.ShowcaseActivity
import com.trendyol.showcase.util.ActionType

class ShowcaseStateController(context: Context) : Application.ActivityLifecycleCallbacks {

    init {
        (context.applicationContext as Application).registerActivityLifecycleCallbacks(this)
    }

    //region activity
    private var activity: ShowcaseActivity? = null

    override fun onActivityStarted(activity: Activity) {
        this.activity = activity as? ShowcaseActivity ?: return
        if (shouldFinishShowcase()) {
            finishShowcase()
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit

    override fun onActivityResumed(activity: Activity) = Unit

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) {
        (activity.applicationContext as Application).unregisterActivityLifecycleCallbacks(this)
        this.activity = null
    }

    private fun finishShowcase(): Unit? {
        return this.activity?.finishShowcase(ActionType.LIFECYCLE_REACHED_STOP, -1)
    }
    //endregion

    //region state
    private var prevState: ShowcaseState? = null

    fun onStateChanged(state: ShowcaseState) {
        if (state == ShowcaseState.DESTROY) {
            finishShowcase()
        }
        prevState = state
    }
    //endregion

    private fun shouldFinishShowcase(): Boolean = prevState == ShowcaseState.DESTROY
}

