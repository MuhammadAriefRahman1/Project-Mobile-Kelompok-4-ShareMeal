package com.kelompok_4.share_meal.home.pages.profil

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.User
import com.kelompok_4.share_meal.databinding.ActivityAturProfilBinding
import com.kelompok_4.share_meal.helpers.DbHelpers
import com.kelompok_4.share_meal.helpers.Helpers

class AturProfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAturProfilBinding
    private lateinit var storageRef: StorageReference
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAturProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set storage reference
        storageRef = FirebaseStorage.getInstance().getReference("users")

        // Set status bar color
        window.statusBarColor = getColor(R.color.white)

        // Set Back Button
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        // Set initial data
        FirebaseDatabase.getInstance().getReference("users").child(Helpers.getUid())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(User::class.java)!!
                    binding.etNamaLengkap.setText(user.nama_lengkap)
                    binding.etAlamat.setText(user.alamat)
                    binding.etNoHp.setText(user.no_hp)

                    if (user.profile_picture != "") {
                        Glide.with(this@AturProfilActivity)
                            .load(user.profile_picture)
                            .into(binding.civProfil)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        // Set Image Picker
        var profilUri: Uri? = null
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                Log.d("AturProfilActivity", "onCreate: $it")
                binding.civProfil.setImageURI(it)
                profilUri = it
            }
        }


        // Set Atur Profil Picture Button
        binding.btnAturProfil.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // Set Save Button
        binding.btnSimpan.setOnClickListener {
            val namaLengkap = binding.etNamaLengkap.text.toString()
            val alamat = binding.etAlamat.text.toString()
            val noHp = binding.etNoHp.text.toString()

            // Cek apakah ada field yang kosong
            val fields = listOf(namaLengkap, alamat, noHp)
            if (fields.any { it.isEmpty() }) {
                Helpers.showToast(this, "Mohon isi semua field")
                return@setOnClickListener
            }

            // Simpan
            fun saveUser() {
                user.nama_lengkap = namaLengkap
                user.alamat = alamat
                user.no_hp = noHp

                DbHelpers.setDataByPath("users/${Helpers.getUid()}", user) {
                    Helpers.makeAlertDialog(
                        this,
                        "Berhasil",
                        "Profil berhasil diubah"
                    ) {
                        onBackPressed()
                    }
                }
            }

            if (profilUri != null) {
                val imageRef = storageRef.child("profile_images/${Helpers.getUid()}.jpg")
                val uploadTask = imageRef.putFile(profilUri!!)

                uploadTask.addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener {
                        user.profile_picture = it.toString()
                        saveUser()

                    }
                }.addOnFailureListener {
                    Log.d("AturProfilActivity", "onCreate: $it")
                }
            } else {
                saveUser()
            }


        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        Helpers.overridePendingExitTransition(this)
    }
}