package com.soldatov.vkino.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by sharedViewModel<ProfileViewModel>()

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

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.isLoggedIn().observe(viewLifecycleOwner, {
            if (it){
                findNavController().navigate(R.id.action_profileFragment_to_userProfileFragment)
            } else {
                showTabs()
            }
        })
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