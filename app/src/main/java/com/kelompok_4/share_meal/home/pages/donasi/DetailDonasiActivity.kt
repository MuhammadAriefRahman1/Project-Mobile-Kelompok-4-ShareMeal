package com.kelompok_4.share_meal.home.pages.donasi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.ActivityDetailDonasiBinding

class DetailDonasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDonasiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDonasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar color
        window.statusBarColor = getColor(R.color.white)

        // Set data
        val donasiId = intent.getStringExtra("donasi_id")
        val donasi = DonasiRecyclerAdapter().getDonasiById(donasiId.toString())

        binding.tvDonationName.text = donasi.nama
        binding.tvDonationAddress.text = donasi.alamat
        binding.tvDonationCategory.text = donasi.kategori
        binding.tvDonationType.text = donasi.jenis
        binding.tvDonationDescription.text = donasi.deskripsi

        // Set back button
        binding.ivBack.setOnClickListener {
            finish()
        }

    }

}