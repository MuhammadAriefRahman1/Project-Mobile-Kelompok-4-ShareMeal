package com.kelompok_4.share_meal.home.pages.donasi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.ActivityPengajuanDonasiBinding
import com.kelompok_4.share_meal.helpers.Helpers

class PengajuanDonasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengajuanDonasiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengajuanDonasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar color
        window.statusBarColor = getColor(R.color.white)

        // Set Back Button
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        // Create Date Picker
        binding.etDonasiExpired.setOnClickListener {

        }

        // Set Submit Button
        binding.btnDonasiDonate.setOnClickListener {
            Toast.makeText(this, "Donasi berhasil diajukan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        Helpers.overridePendingExitTransition(this)
    }
}