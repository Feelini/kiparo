package com.soldatov.vkino.presentation.ui.film

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.soldatov.vkino.databinding.FragmentWatchFilmBinding

private const val IFRAME_SRC = "com.soldatov.vkino.presentation.ui.film.IFRAME_SRC"

class WatchFilmFragment : Fragment() {

    private lateinit var binding: FragmentWatchFilmBinding

    companion object{
        fun create(iframeSrc: String?): WatchFilmFragment{
            val fragment = WatchFilmFragment()
            fragment.arguments = Bundle().apply {
                putString(IFRAME_SRC, iframeSrc)
            }
            return fragment
        }
    }

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
        if (arguments?.containsKey(IFRAME_SRC) == true){
            val iframeLink = "<iframe style=\"transform: translate(-10px, -10px); " +
                    "width: calc(100% + 20px); height: calc(100% + 20px)\" " +
                    "src=\"${arguments?.getString(IFRAME_SRC)}\" frameborder=\"0\" id=\"onik-player\" " +
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