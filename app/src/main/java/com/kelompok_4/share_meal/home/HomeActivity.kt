package com.kelompok_4.share_meal.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.kelompok_4.share_meal.MainActivity
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.ActivityHomeBinding
import com.kelompok_4.share_meal.home.pages.DonasiFragment
import com.kelompok_4.share_meal.home.pages.PenerimaanDonasiFragment
import com.kelompok_4.share_meal.home.pages.ProfilFragment
import com.kelompok_4.share_meal.home.pages.RiwayatFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Remove bottom nav if different role
        val sharedPreferences = getSharedPreferences("SHARED_PREFERENCE", Context.MODE_PRIVATE)
        val role = sharedPreferences.getString("role", "Donatur")
        if (role != "Penerima") {
            binding.bottomNavigationViewMain.menu.removeItem(R.id.nav_donasi__penerima)
        }

        binding.bottomNavigationViewMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    swapFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.nav_riwayat -> {
                    swapFragment(RiwayatFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.nav_donasi -> {
                    swapFragment(DonasiFragment())
                    return@setOnItemSelectedListener true
                }

//                R.id.nav_notifikasi -> {
//                    Toast.makeText(
//                        this,
//                        "Profile",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnItemSelectedListener true
//                }

                R.id.nav_donasi__penerima -> {
                    swapFragment(PenerimaanDonasiFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.nav_profil -> {
                    swapFragment(ProfilFragment())
                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }

        }

    }

    private fun swapFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView2, fragment)
            .commit()
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