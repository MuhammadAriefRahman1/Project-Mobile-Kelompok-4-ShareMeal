package com.kelompok_4.share_meal.home.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.Donasi
import com.kelompok_4.share_meal.databinding.FragmentPenerimaanDonasiBinding
import com.kelompok_4.share_meal.helpers.DbHelpers
import com.kelompok_4.share_meal.helpers.Helpers
import com.kelompok_4.share_meal.home.pages.donasi.PenerimaanDonasiRecyclerAdapter

class PenerimaanDonasiFragment : Fragment() {
    private lateinit var binding: FragmentPenerimaanDonasiBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_penerimaan_donasi, container, false)
        binding = FragmentPenerimaanDonasiBinding.bind(view)

        // Set status bar color
        activity?.window?.statusBarColor = resources.getColor(R.color.white)

        // Set RecyclerView
        binding.rvPenerimaanDonasi.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = PenerimaanDonasiRecyclerAdapter()
        }

        val penerimaanDonasiList = ArrayList<Donasi>()

        // Fetch Data
        DbHelpers.fetchDataByPath("donasi") {
            penerimaanDonasiList.clear()
            if (it != null && it.exists()) {
                for (data in it.children) {
                    val donasi = data.getValue(Donasi::class.java)
                    if (donasi != null && donasi.penerima.id_user == Helpers.getUid()) {
                        penerimaanDonasiList.add(donasi)
                    }
                }
                (binding.rvPenerimaanDonasi.adapter as PenerimaanDonasiRecyclerAdapter).addData(
                    penerimaanDonasiList
                )
            }
        }

        // Return View
        return view
    }
}