package com.kelompok_4.share_meal.vPagerFragment.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.FragmentLoginBinding
import com.kelompok_4.share_meal.vPagerFragment.home.HomeActivity


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        binding = FragmentLoginBinding.bind(view)

        // Set status bar color
        activity?.window?.statusBarColor = ContextCompat.getColor(
            requireActivity(),
            R.color.white
        )

        binding.textViewLoginRegister.setOnClickListener {
            findNavController()
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val fields = listOf(
                binding.etLoginUsername.text.toString(),
                binding.etLoginPassword.text.toString(),
            )

            if (fields.any { it.isNullOrEmpty() }) {
                Toast.makeText(
                    activity,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            } else {
                Toast.makeText(
                    activity,
                    "Login Success",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // Navigate to HomeActivity
            if ((activity as OnboardingActivity).auth()) {
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)

                (activity as OnboardingActivity).finish()
            }

        }

        return view
    }

}