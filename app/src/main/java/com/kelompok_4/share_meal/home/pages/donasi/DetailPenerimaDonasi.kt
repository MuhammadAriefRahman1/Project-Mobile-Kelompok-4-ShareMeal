package com.kelompok_4.share_meal.home.pages.donasi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.Penerima
import com.kelompok_4.share_meal.databinding.ActivityDetailPenerimaDonasiUserBinding
import com.kelompok_4.share_meal.helpers.Helpers

class DetailPenerimaDonasi : AppCompatActivity() {

    private lateinit var dbRef_penerima: DatabaseReference
    private lateinit var dbRef_user: DatabaseReference
    private lateinit var penerima_id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set status bar color
        window.statusBarColor = getColor(R.color.white)

        // Get Penerima
        penerima_id = intent.getStringExtra("penerima_id")!!

        // Set DB
        dbRef_penerima = FirebaseDatabase.getInstance().getReference("penerima")
        dbRef_user = FirebaseDatabase.getInstance().getReference("users")

        var binding = ActivityDetailPenerimaDonasiUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Back Button
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        dbRef_penerima.child(penerima_id.toString()).get().addOnSuccessListener {
            if (it.exists()) {
                val penerima = it.getValue(Penerima::class.java)

                if (penerima != null) {
                    binding.tvPenerimaDonasiName.text = penerima.nama
                    binding.tvPenerimaDonasiAddress.text = penerima.alamat
                    binding.tvPenerimaDonasiDescription.text = penerima.deskripsi
                }
            }
        }

        // Set Donasi Button
        binding.btnPenerimaDonasiDonasi.setOnClickListener {
            val intent = Intent(this, PengajuanDonasiActivity::class.java)
            intent.putExtra("penerima_id", penerima_id)
            startActivity(intent)
            Helpers.overridePendingEnterTransition(this)
        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        Helpers.overridePendingExitTransition(this)
    }
}