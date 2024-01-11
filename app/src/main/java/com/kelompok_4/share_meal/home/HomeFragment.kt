package com.kelompok_4.share_meal.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.Donasi
import com.kelompok_4.share_meal.data.Penerima
import com.kelompok_4.share_meal.databinding.FragmentHomeBinding
import com.kelompok_4.share_meal.helpers.DbHelpers
import com.kelompok_4.share_meal.helpers.Helpers
import com.kelompok_4.share_meal.home.pages.donasi.PenerimaRecyclerAdapter
import com.kelompok_4.share_meal.home.pages.riwayat.RiwayatRecyclerAdapter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var db: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)

        // Set the status bar color
        activity?.window?.statusBarColor = activity?.getColor(R.color.white)!!

        // Set DB
        db = FirebaseDatabase.getInstance()

        // Set user greeting
        val sharedPreferences = activity?.getSharedPreferences(
            "SHARED_PREFERENCE",
            Context.MODE_PRIVATE
        )
        sharedPreferences?.getString("nama_lengkap", "User")?.let {
            binding.tvHomeGreetings.text = "Selamat datang, $it!"
        }

        // Set Data

        // Set Donasi Baru
        binding.rvHomeDonasiDalamProgres.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = RiwayatRecyclerAdapter()
        }
        val riwayatDonasiList = arrayListOf<Donasi>()

        // Get latest 3 donasi of current user based on timestamp
        DbHelpers.fetchDataByPath("donasi") {
            riwayatDonasiList.clear()
            it!!.children.forEach { donasi ->
                val donasiData = donasi.getValue(Donasi::class.java)
                if (donasiData != null) {
                    if (donasiData.donatur.id == Helpers.getUid()) {
                        riwayatDonasiList.add(donasiData)
                    }
                }
            }
            riwayatDonasiList.sortByDescending { donasi -> donasi.timestamp }

            // Add top 3 donasi to the recycler view
            (binding.rvHomeDonasiDalamProgres.adapter as RiwayatRecyclerAdapter).addData(
                riwayatDonasiList.take(3)
            )
        }

        // Set Penerima Donasi Baru
        binding.rvHomePenerimaDonasi.apply {
            layoutManager = LinearLayoutManager(activity)
            val penerimaDonasiAdapter = PenerimaRecyclerAdapter()
            this.adapter = penerimaDonasiAdapter
        }
        val penerimaDonasiList = arrayListOf<Penerima>()

        // Get latest 3 penerima based on timestamp
        DbHelpers.fetchDataByPath("penerima") {
            penerimaDonasiList.clear()
            it!!.children.forEach { penerima ->
                val penerimaData = penerima.getValue(Penerima::class.java)
                if (penerimaData != null) {
                    penerimaDonasiList.add(penerimaData)
                }
            }
            penerimaDonasiList.sortByDescending { penerima -> penerima.created_at }

            // Add top 3 penerima to the recycler view

            (binding.rvHomePenerimaDonasi.adapter as PenerimaRecyclerAdapter).addData(
                penerimaDonasiList.take(3)
            )
        }

        // Return the fragment view/layout
        return view
    }

}