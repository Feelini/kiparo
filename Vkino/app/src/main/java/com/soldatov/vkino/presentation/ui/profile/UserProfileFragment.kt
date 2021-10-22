package com.soldatov.vkino.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.soldatov.domain.models.profile.Gender
import com.soldatov.domain.models.profile.UserInfo
import com.soldatov.domain.models.profile.UserInfoResult
import com.soldatov.vkino.R
import com.soldatov.vkino.databinding.FragmentUserProfileBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserProfileFragment: Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private val viewModel by sharedViewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.userInfo.observe(viewLifecycleOwner, {
            when(it){
                is UserInfoResult.Success -> {
                    showUserInfo(it.data)
                }
                is UserInfoResult.Error -> {
                    Snackbar.make(requireView(), it.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun showUserInfo(userInfo: UserInfo) {
        binding.nameText.setText(userInfo.firstName)
        binding.lastNameText.setText(userInfo.lastName)
        binding.loginText.setText(userInfo.login)
        binding.emailText.setText(userInfo.email)

        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.item_spinner, Gender.values().map { it.value })
        binding.gender.adapter = arrayAdapter
        binding.gender.setSelection(arrayAdapter.getPosition(userInfo.gender.value))

        binding.bdText.setText(userInfo.birthday)
        binding.aboutText.setText(userInfo.about)

        binding.quitProfile.setOnClickListener {
            viewModel.quitProfile()
            findNavController().popBackStack()
        }
    }
}