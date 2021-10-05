package com.soldatov.vkino.presentation.ui.film

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.soldatov.vkino.databinding.FragmentWatchTrailerBinding

const val IFRAME_TRAILER = "com.soldatov.vkino.presentation.ui.film.IFRAME_TRAILER"

class WatchTrailerFragment : Fragment() {

    private lateinit var binding: FragmentWatchTrailerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchTrailerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.containsKey(IFRAME_TRAILER) == true){
            val iframeLink = "<iframe style=\"transform: translate(-10px, -10px); " +
                    "width: calc(100% + 20px); height: calc(100% + 20px)\" " +
                    "src=\"${arguments?.getString(IFRAME_TRAILER)}\" frameborder=\"0\" id=\"onik-player\" " +
                    "allowfullscreen=\"\"></iframe>"
            binding.trailerOnlineView.settings.javaScriptEnabled = true
            binding.trailerOnlineView.settings.allowFileAccess = true
            binding.trailerOnlineView.settings.userAgentString =
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/534.36 (KHTML, like Gecko) Chrome/13.0.766.0 Safari/534.36"
            binding.trailerOnlineView.webChromeClient = activity?.let { FilmWebClient(it) }
            binding.trailerOnlineView.webViewClient = WebViewClient()
            binding.trailerOnlineView.loadDataWithBaseURL(
                "https://vkino.fun/",
                iframeLink,
                "text/html",
                "UTF-8",
                ""
            )
        }
    }
}