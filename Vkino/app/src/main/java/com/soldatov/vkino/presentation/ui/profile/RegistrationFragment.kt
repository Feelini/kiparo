package com.soldatov.vkino.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.soldatov.vkino.databinding.FragmentRegistrBinding

class RegistrationFragment: Fragment() {

    private lateinit var binding: FragmentRegistrBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrBinding.inflate(inflater, container, false)
        return binding.root
    }
}