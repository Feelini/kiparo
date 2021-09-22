package com.soldatov.vkino.presentation.ui.film

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private var iframeSrc: String? = null
    private var iframeTrailer: String? = null

    fun setIframe(iframeSrcNew: String?, iframeTrailerNew: String?){
        iframeSrc = iframeSrcNew
        iframeTrailer = iframeTrailerNew
    }

    override fun getItemCount(): Int {
        return if (iframeSrc != null) {
            if (iframeTrailer != null) {
                2
            } else {
                1
            }
        } else {
            0
        }
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            val fragment = WatchFilmFragment()
            if (iframeSrc != null) {
                fragment.arguments = Bundle().apply {
                    putString(IFRAME_SRC, iframeSrc)
                }
            }
            fragment
        } else {
            val fragment = WatchTrailerFragment()
            if (iframeTrailer != null) {
                fragment.arguments = Bundle().apply {
                    putString(IFRAME_TRAILER, iframeTrailer)
                }
            }
            fragment
        }
    }

}