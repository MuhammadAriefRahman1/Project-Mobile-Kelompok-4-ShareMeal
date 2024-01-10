package com.kelompok_4.share_meal.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kelompok_4.share_meal.R

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        window?.statusBarColor = ContextCompat.getColor(
            this,
            R.color.white
        )

    }
}