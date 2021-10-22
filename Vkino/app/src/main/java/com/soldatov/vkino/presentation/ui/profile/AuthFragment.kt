package com.soldatov.vkino.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.soldatov.domain.models.profile.LoginData
import com.soldatov.vkino.databinding.FragmentAuthBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AuthFragment: Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private val viewModel by sharedViewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitBtn.setOnClickListener {
            val login = binding.loginText.text.toString()
            val password = binding.passText.text.toString()

            viewModel.sendLoginRequest(LoginData(login, password)).observe(viewLifecycleOwner, {
                if (it == null){
                    Snackbar.make(requireView(), "Ошибка авторизации", Snackbar.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}