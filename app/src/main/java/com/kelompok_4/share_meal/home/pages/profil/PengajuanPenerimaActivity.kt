package com.kelompok_4.share_meal.home.pages.profil

import android.app.AlertDialog
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.Penerima
import com.kelompok_4.share_meal.databinding.ActivityPengajuanPenerimaBinding

class PengajuanPenerimaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengajuanPenerimaBinding
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengajuanPenerimaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set database reference
        dbRef = FirebaseDatabase.getInstance().getReference("penerima")

        // Set status bar color
        window.statusBarColor = getColor(R.color.white)

        // Set back button
        binding.ivBack.setOnClickListener {
            finish()
        }

        // Set Description
        binding.etPengajuanPenerimaDeskripsi.setOnFocusChangeListener { view, b ->
            if (b) {
                binding.etPengajuanPenerimaDeskripsi.hint = ""
            } else {
                binding.etPengajuanPenerimaDeskripsi.hint = "Ketik Deskripsi..."
            }
        }
        binding.etPengajuanPenerimaDeskripsi.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT) {
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        // Set Button
        binding.btnPengajuanPenerimaSimpan.setOnClickListener {
            // Get data
            val nama = binding.etPengajuanPenerimaNama.text.toString()
            val alamat = binding.etPengajuanPenerimaAlamat.text.toString()
            val deskripsi = binding.etPengajuanPenerimaDeskripsi.text.toString()
            val userId = intent.getStringExtra("id_user")

            // Save data to database
            val penerimaId = dbRef.push().key
            val penerima =
                Penerima(penerimaId.toString(), userId.toString(), nama, alamat, deskripsi, false)
            dbRef.child(penerimaId.toString()).setValue(penerima)
                .addOnSuccessListener {
                    AlertDialog.Builder(this)
                        .setTitle("Pengajuan Penerima Berhasil")
                        .setMessage("Pengajuan penerima berhasil disimpan. Silahkan tunggu konfirmasi dari admin.")
                        .setPositiveButton("OK") { dialogInterface, i ->
                            finish()
                        }
                        .show()
                }.addOnFailureListener {
                    AlertDialog.Builder(this)
                        .setTitle("Pengajuan Penerima Gagal")
                        .setMessage("Pengajuan penerima gagal disimpan. Silahkan coba lagi.")
                        .setPositiveButton("OK") { dialogInterface, i ->
                            finish()
                        }
                        .show()
                }


        }
    }
}