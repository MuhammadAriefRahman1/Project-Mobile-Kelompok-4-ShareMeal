package com.kelompok_4.share_meal.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.kelompok_4.share_meal.MainActivity
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        binding = FragmentLoginBinding.bind(view)

        // Set database reference
        val database = Firebase.database
        val dbRef = database.getReference("users")
        auth = FirebaseAuth.getInstance()

        // Set status bar color
        activity?.window?.statusBarColor = ContextCompat.getColor(
            requireActivity(),
            R.color.white
        )

        // Set the "no account" button
        binding.textViewLoginRegister.setOnClickListener {
            findNavController()
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

        // Set the login button
        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()

            val fields = listOf(
                email, // 0: Email
                password, // 1: Password
            )

            if (fields.any { it.isNullOrEmpty() }) {
                Toast.makeText(
                    activity,
                    "Mohon isi semua field",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Authentication with Firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            activity,
                            "Login berhasil",
                            Toast.LENGTH_SHORT
                        ).show()

//                        // Get the user data from Firebase
//                        val userId = auth.currentUser?.uid
//                        val userRef = dbRef.child(userId.toString())
//
//                        // Set the shared preferences
//                        userRef.get().addOnSuccessListener {
//                            val sharedPreferences = activity?.getSharedPreferences(
//                                "SHARED_PREFERENCE",
//                                android.content.Context.MODE_PRIVATE
//                            )
//
//                            val editor = sharedPreferences?.edit()
//
//                            editor?.putString("id_user", it.child("id").value.toString())
//                            editor?.putString(
//                                "nama_lengkap",
//                                it.child("nama_lengkap").value.toString()
//                            )
//                            editor?.putString("email", it.child("email").value.toString())
//                            editor?.putString("alamat", it.child("alamat").value.toString())
//
//                            editor?.putString("no_hp", it.child("no_hp").value.toString())
//                            editor?.putString("role", it.child("role").value.toString())
//                            editor?.putBoolean("isLoggedIn", true)
//
//                            editor?.apply()
//                        }
//
//                        // Log current role to console
//                        userRef.get().addOnSuccessListener {
//                            val role = it.child("role").value.toString()
//                        }

                        // Go to home
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    } else {
                        Toast.makeText(
                            activity,
                            "Email atau password salah",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }

        return view
    }

}
