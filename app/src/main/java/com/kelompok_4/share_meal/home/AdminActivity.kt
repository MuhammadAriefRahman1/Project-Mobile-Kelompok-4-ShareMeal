package com.kelompok_4.share_meal.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kelompok_4.share_meal.MainActivity
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.admin.AdminHomeFragment
import com.kelompok_4.share_meal.admin.AdminPenerimaFragment
import com.kelompok_4.share_meal.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Status Bar Color
        window?.statusBarColor = resources.getColor(R.color.white)

        // Set Toolbar Logout Button
        binding.adminIvLogout.setOnClickListener {
            // Show a confirmation dialog
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Logout")
            builder.setMessage("Apakah anda yakin ingin logout?")
            builder.setPositiveButton("Ya") { _, _ ->
                // Logout
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            builder.setNegativeButton("Tidak") { _, _ -> }
            builder.show()
        }

        // Set Bottom Navigation View
        binding.adminBottomNavigationViewMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.admin__nav_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.admin__fragmentViewContainer, AdminHomeFragment())
                        .commit()

                    return@setOnItemSelectedListener true
                }

                R.id.admin__nav_donasi -> {
                    return@setOnItemSelectedListener true

                }

                R.id.admin__nav_donatur -> {


                    return@setOnItemSelectedListener true
                }

                R.id.admin__nav_penerima -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.admin__fragmentViewContainer, AdminPenerimaFragment())
                        .commit()
                    
                    return@setOnItemSelectedListener true
                }

                R.id.admin__nav_notifikasi -> {
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }
}