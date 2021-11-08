package com.soldatov.vkino.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.soldatov.domain.models.profile.RegisterData
import com.soldatov.domain.models.profile.RegisterResult
import com.soldatov.vkino.databinding.FragmentRegistrBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegistrationFragment: Fragment() {

    private lateinit var binding: FragmentRegistrBinding
    private val viewModel by sharedViewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitBtn.setOnClickListener {
            val login = binding.loginText.text.toString()
            val email = binding.emailText.text.toString()
            val password = binding.passText.text.toString()
            val repeatPass = binding.repeatPassText.text.toString()

            val registerData = RegisterData(
                login = login,
                email = email,
                password = password,
                repeatPassword = repeatPass
            )

            viewModel.sendRegisterRequest(registerData).observe(viewLifecycleOwner, {
                when(it){
                    RegisterResult.Success -> {
                        viewModel.logInUser()
                    }
                    is RegisterResult.Error -> {
                        Snackbar.make(requireView(), it.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}