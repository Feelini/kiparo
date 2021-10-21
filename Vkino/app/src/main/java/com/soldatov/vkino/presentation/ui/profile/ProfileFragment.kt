package com.soldatov.vkino.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.soldatov.vkino.databinding.FragmentProfileBinding

class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showTabs()
    }

    private fun showTabs() {
        val tabsTitle = listOf("Вход", "Регистрация")
        val viewPagerAdapter = ViewPagerAdapter(this)

        binding.viewPagerAuth.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabAuth, binding.viewPagerAuth) { tab, position ->
            tab.text = tabsTitle[position]
        }.attach()
    }
}