package com.soldatov.vkino.presentation.ui.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.*

class UserProfileFragment : Fragment() {

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
            when (it) {
                is UserInfoResult.Success -> {
                    showUserInfo(it.data)
                }
                is UserInfoResult.Error -> {
                    Snackbar.make(requireView(), it.message, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    @SuppressLint("SimpleDateFormat")
    private fun showUserInfo(userInfo: UserInfo) {
        Picasso.with(context).load(userInfo.image).into(binding.userPhoto)
        binding.nameText.setText(userInfo.firstName)
        binding.lastNameText.setText(userInfo.lastName)
        binding.loginText.setText(userInfo.login)
        binding.emailText.setText(userInfo.email)
        if (userInfo.pendingEmail != "") {
            val text = "Для подтверждения почтового ящика ${userInfo.pendingEmail} выслано письмо"
            binding.emailField.helperText = text
        }

        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.item_spinner, Gender.values().map { it.value })
        binding.gender.adapter = arrayAdapter
        binding.gender.setSelection(arrayAdapter.getPosition(userInfo.gender.value))

        binding.bdText.setText(userInfo.birthday)
        binding.bdText.setOnClickListener {
            val calendar = Calendar.getInstance()
            if (binding.bdText.text.toString() != ""){
                val simpleDateFormat = SimpleDateFormat("d.MM.yyyy")
                calendar.time = simpleDateFormat.parse(binding.bdText.text.toString())
            }
            DatePickerDialog(requireContext(), { datePicker, year, month, day ->
                val bdText = "${day}.${month+1}.${year}"
                binding.bdText.setText(bdText)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.aboutText.setText(userInfo.about)

        binding.quitProfile.setOnClickListener {
            viewModel.quitProfile()
            findNavController().popBackStack()
        }

        binding.saveProfile.setOnClickListener {
            val userInfo = UserInfo(
                id = 1,
                login = binding.loginText.text.toString(),
                email = binding.emailText.text.toString(),
                pendingEmail = "",
                firstName = binding.nameText.text.toString(),
                lastName = binding.lastNameText.text.toString(),
                about = binding.aboutText.text.toString(),
                gender = if (binding.gender.selectedItemPosition == 0) Gender.MALE else Gender.FEMALE,
                fullName = "",
                birthday = binding.bdText.text.toString(),
                image = ""
            )
            viewModel.updateUserInfo(userInfo)
            Snackbar.make(requireView(), "Данные обновлены", Snackbar.LENGTH_LONG).show()
        }
    }
}