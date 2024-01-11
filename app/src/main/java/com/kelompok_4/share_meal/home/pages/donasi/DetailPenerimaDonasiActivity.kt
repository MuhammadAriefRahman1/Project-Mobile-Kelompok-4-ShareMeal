package com.kelompok_4.share_meal.home.pages.donasi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.Penerima
import com.kelompok_4.share_meal.databinding.ActivityDetailPenerimaDonasiUserBinding
import com.kelompok_4.share_meal.helpers.DbHelpers
import com.kelompok_4.share_meal.helpers.Helpers
import com.kelompok_4.share_meal.home.pages.profil.PreferenceRecyclerAdapter

class DetailPenerimaDonasiActivity : AppCompatActivity() {

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

        // Set Rv
        binding.rvListPreference.apply {
            this.adapter = PreferenceRecyclerAdapter()
        }

        // Set Data
        DbHelpers.fetchSingleDataByPath("penerima/${penerima_id}") {
            val penerima = it!!.getValue(Penerima::class.java)!!

            binding.tvPenerimaDonasiName.text = penerima.nama
            binding.tvPenerimaDonasiAddress.text = penerima.alamat
            binding.tvPenerimaDonasiDescription.text = penerima.deskripsi

            DbHelpers.fetchSingleDataByPath("users/${penerima.id_user}") {
                val user = it!!.getValue(com.kelompok_4.share_meal.data.User::class.java)!!

                binding.tvPenanggungjawabName.text = user.nama_lengkap
                binding.tvPenanggungjawabPhone.text = user.no_hp
                binding.tvPenanggungjawabEmail.text = user.email

                if (user.profile_picture != "") {
                    Glide.with(this).load(user.profile_picture).into(binding.ivPenerima)
                }
            }

            DbHelpers.fetchDataByPath("preference") {
                val preferenceList = arrayListOf<com.kelompok_4.share_meal.data.Preference>()
                preferenceList.clear()
                (binding.rvListPreference.adapter as PreferenceRecyclerAdapter).notifyDataSetChanged()
                it!!.children.forEach { preference ->
                    val preference =
                        preference.getValue(com.kelompok_4.share_meal.data.Preference::class.java)!!

                    if (preference.user_id == penerima.id_user) {
                        preferenceList.add(preference)
                    }
                }
                (binding.rvListPreference.adapter as PreferenceRecyclerAdapter).addData(
                    preferenceList
                )
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