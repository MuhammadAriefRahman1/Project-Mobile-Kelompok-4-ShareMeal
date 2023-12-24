package com.kelompok_4.share_meal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelompok_4.share_meal.home.HomeActivity
import com.kelompok_4.share_meal.onboarding.OnboardingActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLogin()
    }

    private fun checkLogin() {
        val sharedPreferences = getSharedPreferences("SHARED_PREFERENCE", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            // Navigate to HomeActivity and finish this activity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}