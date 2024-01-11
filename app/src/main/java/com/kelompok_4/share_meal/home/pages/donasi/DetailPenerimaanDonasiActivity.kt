package com.kelompok_4.share_meal.home.pages.donasi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.Donasi
import com.kelompok_4.share_meal.data.StatusDonasi
import com.kelompok_4.share_meal.data.User
import com.kelompok_4.share_meal.databinding.ActivityDetailPenerimaanDonasiBinding
import com.kelompok_4.share_meal.helpers.DbHelpers
import com.kelompok_4.share_meal.helpers.Helpers

class DetailPenerimaanDonasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPenerimaanDonasiBinding
    private lateinit var donasi_id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPenerimaanDonasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get Donasi
        donasi_id = intent.getStringExtra("donasi_id")!!

        // Set status bar color
        window.statusBarColor = getColor(R.color.white)

        // Set Back Button
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        // Set Info
        FirebaseDatabase.getInstance().getReference("donasi")
            .child(donasi_id).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val donasi = snapshot!!.getValue(Donasi::class.java)!!

                        binding.tvNama.text = donasi.nama
                        binding.tvKategori.text = donasi.kategori
                        binding.tvSatuanJumlah.text = "${donasi.jumlah} ${donasi.satuan}"
                        binding.tvDescription.text = donasi.deskripsi
                        binding.tvDonatur.text = donasi.donatur.nama_lengkap
                        binding.tvStatus.text = "Status: ${donasi.status}"

                        if (donasi.gambar != "") {
                            Glide.with(this@DetailPenerimaanDonasiActivity)
                                .load(donasi.gambar)
                                .into(binding.ivDonasi)
                        }

                        // Kalau donasi diproses, tampilkan btn konfirmasi
                        if (donasi.status == StatusDonasi.DIPROSES) {
                            binding.btnConfirm.visibility = View.GONE
                            binding.btnReject.visibility = View.GONE
                            binding.btnSelesai.visibility = View.VISIBLE
                        }

                        // Kalau donasi ditolak, buang semua tombol
                        if (donasi.status == StatusDonasi.DITOLAK) {
                            binding.btnConfirm.visibility = View.GONE
                            binding.btnReject.visibility = View.GONE
                        }

                        // Kalau donasi diterima, buang semua tombol
                        if (donasi.status == StatusDonasi.DITERIMA) {
                            binding.btnConfirm.visibility = View.GONE
                            binding.btnReject.visibility = View.GONE
                            binding.btnSelesai.visibility = View.GONE
                            binding.btnKontak.visibility = View.VISIBLE
                        }

                        binding.btnKontak.setOnClickListener {
                            DbHelpers.fetchSingleDataByPath("users/${donasi.donatur.id}") {
                                val donatur = it!!.getValue(User::class.java)!!
                                Helpers.makeAlertDialog(
                                    this@DetailPenerimaanDonasiActivity,
                                    "Kontak Donatur",
                                    """
                                    Nama: ${donatur.nama_lengkap}
                                    No. HP: ${donatur.no_hp} 
                                    Email: ${donatur.email}
                                    Alamat: ${donatur.alamat}
                                 
                                """.trimIndent()
                                )
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                }
            )

        // Set Btns

        binding.btnConfirm.setOnClickListener {
            Helpers.alertDialogWithBtn(
                this,
                "Konfirmasi Donasi",
                "Apakah anda yakin ingin mengkonfirmasi donasi ini?",
                "Konfirmasi",
                "Batal",
                {
                    DbHelpers.fetchSingleDataByPath("donasi/$donasi_id") {
                        var donasi = it!!.getValue(Donasi::class.java)!!
                        donasi.status = StatusDonasi.DIPROSES
                        DbHelpers.setDataByPath("donasi/$donasi_id", donasi) {
                            Helpers.makeAlertDialog(
                                this,
                                "Donasi Dikonfirmasi",
                                "Donasi telah dikonfirmasi, silahkan tunggu donasi diantar oleh donatur"
                            ) {
                                onBackPressed()
                            }
                        }
                    }
                }
            )
        }

        binding.btnReject.setOnClickListener {
            Helpers.alertDialogWithBtn(
                this,
                "Tolak Donasi",
                "Apakah anda yakin ingin menolak donasi ini?",
                "Tolak",
                "Batal",
                {
                    DbHelpers.fetchSingleDataByPath("donasi/$donasi_id") {
                        var donasi = it!!.getValue(Donasi::class.java)!!
                        donasi.status = StatusDonasi.DITOLAK
                        DbHelpers.setDataByPath("donasi/$donasi_id", donasi) {
                            Helpers.makeAlertDialog(
                                this,
                                "Donasi Ditolak",
                                "Donasi telah ditolak"
                            ) {
                                onBackPressed()
                            }
                        }
                    }
                }
            )
        }

        binding.btnSelesai.setOnClickListener {
            Helpers.alertDialogWithBtn(
                this,
                "Selesaikan Donasi",
                "Apakah anda yakin ingin menyelesaikan donasi ini? pastikan donasi telah diterima.",
                "Selesaikan",
                "Batal",
                {
                    DbHelpers.fetchSingleDataByPath("donasi/$donasi_id") {
                        var donasi = it!!.getValue(Donasi::class.java)!!
                        donasi.status = StatusDonasi.DITERIMA
                        DbHelpers.setDataByPath("donasi/$donasi_id", donasi) {
                            Helpers.makeAlertDialog(
                                this,
                                "Donasi Selesai",
                                "Donasi telah diselesaikan! Anda dapat mengkontak donatur kembali apabila dibutuhkan"
                            ) {
                                onBackPressed()
                            }
                        }
                    }
                }
            )
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        Helpers.overridePendingExitTransition(this)
    }
}