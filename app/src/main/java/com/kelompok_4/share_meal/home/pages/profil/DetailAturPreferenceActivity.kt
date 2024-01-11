package com.kelompok_4.share_meal.home.pages.profil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kelompok_4.share_meal.data.Preference
import com.kelompok_4.share_meal.databinding.ActivityDetailAturPreferenceBinding
import com.kelompok_4.share_meal.helpers.DbHelpers
import com.kelompok_4.share_meal.helpers.Helpers

class DetailAturPreferenceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAturPreferenceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAturPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar color
        window.statusBarColor = getColor(com.kelompok_4.share_meal.R.color.white)

        // Set back button
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        // Get preference_id
        var preference_id = intent.getStringExtra("preference_id")

        // Check if edit or add
        if (preference_id != null) {
            // Edit
            binding.tvTitle.text = "Edit Preference"
            binding.btnHapus.visibility = android.view.View.VISIBLE

            FirebaseDatabase.getInstance().getReference("preference/$preference_id")
                .addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                val preference = snapshot.getValue(Preference::class.java)!!
                                binding.etKategori.setText(preference.kategori)
                                binding.etTipe.setText(preference.nama)
                                binding.etDeskripsi.setText(preference.deskripsi)
                            } else {
                                onBackPressed()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    }
                )
//            DbHelpers.fetchDataByPath("preference/$preference_id") {
//                if (it != null) {
//                    val preference = it.getValue(Preference::class.java)!!
//                    binding.etKategori.setText(preference.kategori)
//                    binding.etTipe.setText(preference.nama)
//                    binding.etDeskripsi.setText(preference.deskripsi)
//                } else {
//
//                    onBackPressed()
//                }
//            }

            binding.btnHapus.setOnClickListener {
                Helpers.alertDialogWithBtn(
                    this,
                    "Hapus Preference",
                    "Apakah anda yakin ingin menghapus preference ini?",
                    "Iya, Hapus",
                    "Batal",
                    {
                        DbHelpers.deleteDataByPath("preference/$preference_id") {
                            Helpers.makeAlertDialog(
                                this,
                                "Berhasil",
                                "Berhasil menghapus preference",
                            ) {
                                onBackPressed()
                            }
                        }
                    },
                    {}

                )
            }
        } else {
            // Add
            binding.tvTitle.text = "Tambah Preference"
        }

        binding.btnSimpanPreference.setOnClickListener {
            val kategori = binding.etKategori.text.toString()
            val nama = binding.etTipe.text.toString()
            val deskripsi = binding.etDeskripsi.text.toString()

            val fields = arrayListOf(
                kategori,
                nama,
                deskripsi
            )

            if (fields.any { it.isNullOrEmpty() }) {
                Helpers.showToast(this, "Harap isi semua field")
                return@setOnClickListener
            }

            // Save Preference

            if (preference_id == null) {
                preference_id = FirebaseDatabase.getInstance().reference.push().key!!
            }

            val preference = Preference(
                preference_id!!,
                kategori = kategori,
                nama = nama,
                deskripsi = deskripsi,
                user_id = Helpers.getUid()
            )

            DbHelpers.setDataByPath("preference/$preference_id", preference) {
                Helpers.makeAlertDialog(
                    this,
                    "Berhasil",
                    "Berhasil menyimpan preference",
                ) {
                    onBackPressed()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        Helpers.overridePendingExitTransition(this)
    }
}