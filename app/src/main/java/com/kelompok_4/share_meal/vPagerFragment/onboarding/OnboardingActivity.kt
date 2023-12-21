package com.kelompok_4.share_meal.vPagerFragment.onboarding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelompok_4.share_meal.R

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Disable back button

    }
}