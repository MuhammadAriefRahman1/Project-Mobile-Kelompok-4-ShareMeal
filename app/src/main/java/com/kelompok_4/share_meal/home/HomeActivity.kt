package com.kelompok_4.share_meal.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.kelompok_4.share_meal.MainActivity
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.ActivityHomeBinding
import com.kelompok_4.share_meal.home.pages.DonasiFragment
import com.kelompok_4.share_meal.home.pages.ProfilFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.bottomNavigationViewMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView2, HomeFragment())
                        .commit()

                    return@setOnItemSelectedListener true
                }

                R.id.nav_riwayat -> {
                    Toast.makeText(
                        this,
                        "Search",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnItemSelectedListener true
                }

                R.id.nav_donasi -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView2, DonasiFragment())
                        .commit()

                    return@setOnItemSelectedListener true
                }

                R.id.nav_notifikasi -> {
                    Toast.makeText(
                        this,
                        "Profile",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnItemSelectedListener true
                }

                R.id.nav_profil -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView2, ProfilFragment())
                        .commit()

                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()

        // Close the app
        finish()
    }

    fun logout(view: View?) {
        // Show a confirmation dialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Apakah anda yakin ingin logout?")
        builder.setPositiveButton("Ya") { _, _ ->
            // Logout
            val sharedPreferences =
                getSharedPreferences("SHARED_PREFERENCE", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.clear()
            editor.putBoolean("onBoardingFinished", true)
            editor.apply()

            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("Tidak") { _, _ -> }
        builder.show()


    }

    fun DEBUGClearSharedPreferences(view: View?) {
        val sharedPreferences = getSharedPreferences("SHARED_PREFERENCE", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}