package com.kelompok_4.share_meal.vPagerFragment.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        binding = FragmentLoginBinding.bind(view)

        binding.textViewLoginRegister.setOnClickListener {
            findNavController()
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etLoginUsername.text.toString()
            val password = binding.etLoginPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
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
            login(username, password)
        }

        return view
    }

    private fun login(username: String, password: String) {
        val sharedPreferences = requireActivity()
            .getSharedPreferences("SHARED_PREFERENCE", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.putBoolean("isLoggedIn", true)

        editor.apply()

        // Navigate to HomeActivity
        findNavController()
            .navigate(R.id.action_loginFragment_to_mainActivity2)
    }
}