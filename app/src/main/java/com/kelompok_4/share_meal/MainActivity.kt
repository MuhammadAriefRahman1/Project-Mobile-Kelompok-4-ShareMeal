package com.kelompok_4.share_meal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.kelompok_4.share_meal.data.User
import com.kelompok_4.share_meal.home.AdminActivity
import com.kelompok_4.share_meal.home.HomeActivity
import com.kelompok_4.share_meal.onboarding.OnboardingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set Status Bar Color
        window?.statusBarColor = resources.getColor(R.color.white)


        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        dbRef = Firebase.database.getReference("users")

        // Check if user is logged in
        auth.currentUser?.let {
            dbRef.child(it.uid).get().addOnSuccessListener { snapshot ->

                val user = snapshot.getValue(User::class.java)

                Log.d("USER", user.toString())

                val sharedPreferences = getSharedPreferences("SHARED_PREFERENCE", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putString("id_user", user?.id)
                editor.putString("nama_lengkap", user?.nama_lengkap)
                editor.putString("no_hp", user?.no_hp)
                editor.putString("email", if (user?.email.isNullOrEmpty()) "" else user?.email)
                editor.putString("role", user?.role)
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                val role = snapshot.child("role").value.toString()

                if (role == "Admin") {
                    val intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            }


            // User is logged in

        } ?: run {
            // User is not logged in
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}