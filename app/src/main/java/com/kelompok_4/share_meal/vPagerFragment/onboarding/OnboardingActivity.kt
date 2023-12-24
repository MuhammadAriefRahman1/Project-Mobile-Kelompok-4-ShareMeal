package com.kelompok_4.share_meal.vPagerFragment.onboarding

import android.content.Context
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


    fun auth(): Boolean {
        val sharedPreferences = this
            .getSharedPreferences("SHARED_PREFERENCE", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.apply()

        return true
    }

}