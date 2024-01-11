package com.kelompok_4.share_meal.home.pages.profil

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.Preference
import com.kelompok_4.share_meal.databinding.ActivityAturPreferenceBinding
import com.kelompok_4.share_meal.helpers.DbHelpers
import com.kelompok_4.share_meal.helpers.Helpers

class AturPreferenceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAturPreferenceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAturPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar color
        window.statusBarColor = getColor(R.color.white)

        // Set back button
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        // Set Add
        binding.fabAddPreference.setOnClickListener {
            val intent = Intent(this, DetailAturPreferenceActivity::class.java)
            startActivity(intent)
            Helpers.overridePendingEnterTransition(this)
        }

        // Add recycler view
        binding.rvListPreference.apply {
            val layoutManager = LinearLayoutManager(this@AturPreferenceActivity)
            this.adapter = PreferenceRecyclerAdapter()
        }

        val preferenceList = arrayListOf<Preference>()
        DbHelpers.fetchDataByPath("preference") {
            preferenceList.clear()
            (binding.rvListPreference.adapter as PreferenceRecyclerAdapter).notifyDataSetChanged()
            it!!.children.forEach { preference ->
                val preference = preference.getValue(Preference::class.java)!!

                if (preference.user_id == Helpers.getUid()) {
                    preferenceList.add(preference)
                }
            }
            (binding.rvListPreference.adapter as PreferenceRecyclerAdapter).addData(preferenceList)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        Helpers.overridePendingExitTransition(this)
    }
}