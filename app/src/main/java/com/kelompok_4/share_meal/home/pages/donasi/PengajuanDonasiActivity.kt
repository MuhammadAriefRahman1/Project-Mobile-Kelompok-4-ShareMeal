package com.kelompok_4.share_meal.home.pages.donasi

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.Donasi
import com.kelompok_4.share_meal.data.Penerima
import com.kelompok_4.share_meal.data.User
import com.kelompok_4.share_meal.databinding.ActivityPengajuanDonasiBinding
import com.kelompok_4.share_meal.helpers.DbHelpers
import com.kelompok_4.share_meal.helpers.Helpers
import java.util.Calendar

class PengajuanDonasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengajuanDonasiBinding
    private lateinit var penerima_id: String
    private lateinit var dbRef: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengajuanDonasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get penerima_id
        penerima_id = intent.getStringExtra("penerima_id")!!

        // Set DB
        dbRef = FirebaseDatabase.getInstance()

        // Set status bar color
        window.statusBarColor = getColor(R.color.white)

        // Set Back Button
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        // Set Image Picker
        var donasiImage: Uri? = null
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                Log.d("AturProfilActivity", "onCreate: $it")
                binding.ivDonasi.setImageURI(it)
                donasiImage = it
            }
        }

        // Create Date Picker
        binding.etDonasiExpired.setOnClickListener {
            Calendar.getInstance().apply {
                DatePickerDialog(
                    this@PengajuanDonasiActivity,
                    { _, year, month, dayOfMonth ->
                        binding.etDonasiExpired.setText("$dayOfMonth-${month + 1}-$year")
                    },
                    get(Calendar.YEAR),
                    get(Calendar.MONTH),
                    get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        // Set Atur Profil Picture Button
        binding.btnAturFoto.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // Set Submit Button
        binding.btnDonasiDonate.setOnClickListener {
            val kategori = binding.etDonasiKategori.text.toString()
            val nama = binding.etDonasiTipe.text.toString()
            val deskripsi = binding.etDonasiDeskripsi.text.toString()
            val satuan = binding.etDonasiSatuan.text.toString()
            val jumlah = binding.etDonasiJumlah.text.toString()
            val ekspirasi = binding.etDonasiExpired.text.toString()

            val fields = listOf(
                kategori,
                nama,
                deskripsi,
                satuan,
                jumlah,
                ekspirasi,
            )

            // Validate
            if (fields.any { it.isNullOrEmpty() } || donasiImage == null) {
                Helpers.showToast(this, "Harap isi semua field")
                return@setOnClickListener
            }

            // Create Donasi

            // Find Current User
            DbHelpers.fetchSingleDataByPath("users/${Helpers.getUid()}") {
                val user = it!!.getValue(User::class.java)!!

                DbHelpers.fetchSingleDataByPath("penerima/${penerima_id}") { it ->
                    val penerima = it!!.getValue(Penerima::class.java)!!

                    val donasiId = dbRef.getReference("donasi").push().key

                    DbHelpers.uploadImage("donasi/${donasiId}", donasiImage!!) { donasiUri ->
                        val donasi = Donasi(
                            id = donasiId!!,
                            donatur = user,
                            penerima = penerima,
                            kategori = kategori,
                            nama = nama,
                            deskripsi = deskripsi,
                            satuan = satuan,
                            jumlah = jumlah.toDouble(),
                            ekspirasi = ekspirasi,
                            gambar = donasiUri,
                        )

                        DbHelpers.setDataByPath("donasi/${donasiId}", donasi) {
                            Helpers.makeAlertDialog(
                                this,
                                "Donasi Berhasil",
                                "Donasi berhasil disimpan. Silahkan tunggu konfirmasi dari penerima."
                            ) {
                                finish()
                            }
                        }
                    }

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