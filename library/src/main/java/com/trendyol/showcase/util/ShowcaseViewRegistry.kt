package com.trendyol.showcase.util

import android.view.View
import java.lang.ref.WeakReference

internal object ShowcaseViewRegistry {
    private val viewReferences = mutableMapOf<Int, WeakReference<View>>()
    
    fun registerView(view: View) {
        viewReferences[view.id] = WeakReference(view)
    }
    
    fun getFocusedView(viewId: Int): View? {
        return viewReferences[viewId]?.get()
    }
    
    fun unregisterView(viewId: Int) {
        viewReferences.remove(viewId)
    }
    
    fun clear() {
        viewReferences.clear()
    }
} 