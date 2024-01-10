package com.kelompok_4.share_meal.home.pages.profil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.ActivityAturPreferenceBinding
import com.kelompok_4.share_meal.helpers.Helpers

class AturPreferenceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAturPreferenceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAturPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar color
        window.statusBarColor = getColor(R.color.white)

        // Set back button
        binding.ivBack.setOnClickListener {
            onBackPressed()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Helpers.overridePendingExitTransition(this)
    }
}