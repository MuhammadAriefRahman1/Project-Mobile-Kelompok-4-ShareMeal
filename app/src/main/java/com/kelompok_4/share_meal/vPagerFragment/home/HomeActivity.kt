package com.kelompok_4.share_meal.vPagerFragment.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.ActivityHomeBinding
import com.kelompok_4.share_meal.vPagerFragment.home.pages.DonasiFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    private lateinit var homeFragment: HomeFragment

    //     lateinit var riwayatFragment: RiwayatFragment
    lateinit var donasiFragment: DonasiFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationViewMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView2, homeFragment)
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
                    donasiFragment = DonasiFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView2, donasiFragment)
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
                    Toast.makeText(
                        this,
                        "Profile",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnItemSelectedListener true
                }

                else -> return@setOnItemSelectedListener false
            }

        }

    }


}