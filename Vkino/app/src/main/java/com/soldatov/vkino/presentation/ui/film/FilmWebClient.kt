package com.soldatov.vkino.presentation.ui.film

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.View.*
import android.webkit.WebChromeClient
import android.widget.FrameLayout

class FilmWebClient(private val activity: Activity): WebChromeClient() {

    private var customView: View? = null
    private var originalSystemUiVisibility: Int = 0
    private var customViewCallback: CustomViewCallback? = null

    override fun getDefaultVideoPoster(): Bitmap? {
        return if (customView == null) {
            null
        } else BitmapFactory.decodeResource(activity.resources, 2130837573)
    }

    override fun onHideCustomView() {
        (activity.window.decorView as FrameLayout).removeView(customView)
        customView = null
        activity.window.decorView.systemUiVisibility = originalSystemUiVisibility
        customViewCallback?.onCustomViewHidden()
        customViewCallback = null
    }

    override fun onShowCustomView(paramView: View, paramCustomViewCallback: CustomViewCallback) {
        if (customView != null) {
            onHideCustomView()
            return
        }
        customView = paramView
        originalSystemUiVisibility = activity.window.decorView.systemUiVisibility
        customViewCallback = paramCustomViewCallback
        (activity.window.decorView as FrameLayout).addView(
            customView,
            FrameLayout.LayoutParams(-1, -1)
        )
        activity.window.decorView.systemUiVisibility = (SYSTEM_UI_FLAG_HIDE_NAVIGATION and
                SYSTEM_UI_FLAG_FULLSCREEN and
                SYSTEM_UI_FLAG_LAYOUT_STABLE and
                SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION and
                SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN and
                SYSTEM_UI_FLAG_IMMERSIVE)
    }
}