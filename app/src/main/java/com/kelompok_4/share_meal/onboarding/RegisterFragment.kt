package com.kelompok_4.share_meal.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.kelompok_4.share_meal.MainActivity
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.User
import com.kelompok_4.share_meal.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        binding = FragmentRegisterBinding.bind(view)

        // Set database reference
        val database = Firebase.database
        val dbRef = database.getReference("users")
        auth = FirebaseAuth.getInstance()

        // Set the status bar color
        activity?.window?.statusBarColor = resources.getColor(R.color.white)

        // Set the "has account" button
        binding.textViewRegisterLogin.setOnClickListener {
            findNavController()
                .navigate(R.id.action_registerFragment_to_loginFragment)

        }

        // Set the register button
        binding.btnRegister.setOnClickListener {
            val namaLengkap = binding.etRegisterNamaLengkap.text.toString()
            val email = binding.etRegisterEmail.text.toString()
            val password = binding.etRegisterPassword.text.toString()
            val confirmPassword = binding.etRegisterConfirmPassword.text.toString()

            // Put all fields into a list for easier validation
            val fields = listOf(
                namaLengkap, // 0: Nama Lengkap
                email, // 1: Email
                password, // 2: Password
                confirmPassword, // 3: Konfirmasi Password
            )

            // Validate all fields
            if (fields.any { it.isNullOrEmpty() }) {
                Toast.makeText(
                    activity,
                    "Harap isi semua field",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            } else if (fields.get(2) != fields.get(3)) {
                Toast.makeText(
                    activity,
                    "Password and Konfirmasi Password harus sama",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            activity,
                            "Berhasil mendaftar",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Get the user id
                        val userId = auth.currentUser?.uid

                        // Create a new user object
                        val user = User(
                            id = userId!!,
                            nama_lengkap = namaLengkap,
                            email = email,
                            alamat = "",
                            no_hp = "",
                            role = "Donatur"
                        )

                        // Add the user to the database
                        dbRef.child(userId).setValue(user)

//                        // Update the UI
//                        val sharedPreferences = activity?.getSharedPreferences(
//                            "SHARED_PREFERENCE",
//                            Context.MODE_PRIVATE
//                        )
//
//                        val editor = sharedPreferences?.edit()
//
//                        editor?.putString("id_user", user.id)
//                        editor?.putString("role", user.role)
//                        editor?.putString("nama_lengkap", user.nama_lengkap)
//                        editor?.putString("alamat", user.alamat)
//                        editor?.putString("no_hp", user.no_hp)
//                        editor?.putString("email", email)
//
//                        editor?.apply()

                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(
                            activity,
                            "Gagal mendaftar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }


        return view
    }


}