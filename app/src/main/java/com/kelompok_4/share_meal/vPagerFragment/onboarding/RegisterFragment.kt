package com.kelompok_4.share_meal.vPagerFragment.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.FragmentRegisterBinding
import com.kelompok_4.share_meal.vPagerFragment.home.HomeActivity

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        binding = FragmentRegisterBinding.bind(view)

        binding.textViewRegisterLogin.setOnClickListener {
            findNavController()
                .navigate(R.id.action_registerFragment_to_loginFragment)

        }

        binding.btnRegister.setOnClickListener {
            val fields = listOf(
                binding.etRegisterNamaLengkap.text.toString(),
                binding.etRegisterUsername.text.toString(),
                binding.etRegisterPassword.text.toString(),
                binding.etRegisterConfirmPassword.text.toString(),
            )

            if (fields.any { it.isNullOrEmpty() }) {
                Toast.makeText(
                    activity,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            } else if (fields.get(2) != fields.get(3)) {
                Toast.makeText(
                    activity,
                    "Password and Confirm Password must be the same",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                Toast.makeText(
                    activity,
                    "Register Success",
                    Toast.LENGTH_SHORT
                ).show()

            }

            if ((activity as OnboardingActivity).auth()) {
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                (activity as OnboardingActivity).finish()
            }
        }


        return view
    }


}