package com.soldatov.vkino.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.FragmentRootBinding
import com.soldatov.vkino.presentation.ui.home.HomeFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RootFragment : Fragment() {

    private lateinit var binding: FragmentRootBinding
    private val viewModel by sharedViewModel<HomeFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRootBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController =
            (childFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment).navController
        NavigationUI.setupWithNavController(binding.bottomNavView, navController)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getFilterParams().observe(viewLifecycleOwner, {
            var counter = 0
            if (it.chosenCategories.isNotEmpty()) counter += 1
            if (it.chosenGenres.isNotEmpty()) counter += 1
            if (it.chosenCountries.isNotEmpty()) counter += 1
            if (it.chosenActors.isNotEmpty()) counter += 1
            if (it.chosenQualities.isNotEmpty()) counter += 1
            if (it.chosenYears != null) counter += 1

            if (counter != 0) {
                val badge = binding.bottomNavView.getOrCreateBadge(R.id.filterFragment)
                badge.isVisible = true
                badge.number = counter
            } else {
                val badge = binding.bottomNavView.getBadge(R.id.filterFragment)
                if (badge != null) {
                    badge.isVisible = false
                    badge.clearNumber()
                }
            }
        })
    }
}