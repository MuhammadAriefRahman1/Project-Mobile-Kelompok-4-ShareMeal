package com.kelompok_4.share_meal.home.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.Donasi
import com.kelompok_4.share_meal.databinding.FragmentRiwayatBinding
import com.kelompok_4.share_meal.helpers.DbHelpers
import com.kelompok_4.share_meal.helpers.Helpers
import com.kelompok_4.share_meal.home.pages.riwayat.RiwayatRecyclerAdapter

class RiwayatFragment : Fragment() {
    private lateinit var binding: FragmentRiwayatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_riwayat, container, false)
        binding = FragmentRiwayatBinding.bind(view)

        // Set status bar color
        activity?.window?.statusBarColor = resources.getColor(R.color.white)

        // Set RecyclerView
        binding.rvRiwayat.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = RiwayatRecyclerAdapter()
        }

        val riwayatList = ArrayList<Donasi>()

        // Fetch Data
        DbHelpers.fetchDataByPath("donasi") {
            riwayatList.clear()
            if (it != null && it.exists()) {
                for (data in it.children) {
                    val donasi = data.getValue(Donasi::class.java)
                    if (donasi != null && donasi.donatur.id == Helpers.getUid()) {
                        riwayatList.add(donasi)
                    }
                }
                (binding.rvRiwayat.adapter as RiwayatRecyclerAdapter).addData(riwayatList)
            }
        }

        // Return View
        return view
    }

}