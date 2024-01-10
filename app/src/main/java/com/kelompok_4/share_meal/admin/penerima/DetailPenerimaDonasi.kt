package com.kelompok_4.share_meal.admin.penerima

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.Penerima
import com.kelompok_4.share_meal.data.User
import com.kelompok_4.share_meal.databinding.ActivityDetailPenerimaDonasiAdminBinding
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

        var binding = ActivityDetailPenerimaDonasiAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Back Button
        binding.ivBack.setOnClickListener {
            finish()
        }

        dbRef_penerima.child(penerima_id.toString()).get().addOnSuccessListener {
            if (it.exists()) {
                val penerima = it.getValue(Penerima::class.java)

                if (penerima != null) {
                    binding.tvPenerimaDetailName.text = penerima.nama
                    binding.tvPenerimaDetailAddress.text = penerima.alamat
                    binding.flPenerimaDetailIsVerified.background =
                        if (penerima.verification == true)
                            AppCompatResources.getDrawable(this, R.drawable.badge_success)
                        else
                            AppCompatResources.getDrawable(this, R.drawable.badge_danger)
                    binding.tvPenerimaDetailIsVerified.text =
                        if (penerima.verification == true)
                            "Sudah Diverifikasi"
                        else
                            "Belum Diverifikasi"
                    binding.tvPenerimaDetailDescription.text = penerima.deskripsi

                    if (penerima.verification == true) {
                        binding.btnDetailPengajuanTerima.visibility = View.GONE
                    }
                }
            }
        }

        // Set Verify button
        binding.btnDetailPengajuanTerima.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Verifikasi Penerima")
                .setMessage("Apakah anda yakin ingin memverifikasi penerima ini?")
                .setPositiveButton("Ya") { dialog, _ ->

                    var penerima: Penerima

                    dbRef_penerima.child(penerima_id.toString()).get().addOnSuccessListener {
                        if (it.exists()) {
                            penerima = it.getValue(Penerima::class.java)!!
                            penerima.verification = true
                            dbRef_penerima.child(penerima_id.toString()).setValue(penerima)

                            dbRef_user.child(penerima.id_user.toString()).get()
                                .addOnSuccessListener {
                                    if (it.exists()) {
                                        val user = it.getValue(User::class.java)!!
                                        user.role = "Penerima"
                                        dbRef_user.child(penerima.id_user.toString()).setValue(user)
                                    }
                                }
                        }
                    }

                    dialog.dismiss()
                    AlertDialog.Builder(this)
                        .setTitle("Verifikasi Penerima")
                        .setMessage("Penerima berhasil diverifikasi")
                        .setPositiveButton("Ok") { _, _ ->
                            finish()
                        }.setOnCancelListener {
                            finish()
                        }
                        .show()
                }
                .setNegativeButton("Tidak") { _, _ -> }
                .show()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        Helpers.overridePendingExitTransition(this)
    }
}