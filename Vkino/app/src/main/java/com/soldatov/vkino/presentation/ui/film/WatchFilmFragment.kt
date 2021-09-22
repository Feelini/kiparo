package com.soldatov.vkino.presentation.ui.film

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.soldatov.vkino.databinding.FragmentWatchFilmBinding

const val IFRAME_SRC = "iframeSrc"

class WatchFilmFragment : Fragment() {

    private lateinit var binding: FragmentWatchFilmBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(IFRAME_SRC) }?.apply {
            val iframeLink = "<iframe style=\"transform: translate(-10px, -10px); " +
                    "width: calc(100% + 20px); height: calc(100% + 20px)\" " +
                    "src=\"${getString(IFRAME_SRC)}\" frameborder=\"0\" id=\"onik-player\" " +
                    "allowfullscreen=\"\"></iframe>"
            binding.filmOnlineView.settings.javaScriptEnabled = true
            binding.filmOnlineView.settings.allowFileAccess = true
            binding.filmOnlineView.webChromeClient = activity?.let { FilmWebClient(it) }
            binding.filmOnlineView.webViewClient = WebViewClient()
            binding.filmOnlineView.loadDataWithBaseURL(
                "https://vkino.fun/",
                iframeLink,
                "text/html",
                "UTF-8",
                ""
            )
        }
    }
}