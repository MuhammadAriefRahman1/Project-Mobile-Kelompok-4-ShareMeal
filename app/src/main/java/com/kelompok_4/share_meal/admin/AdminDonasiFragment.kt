package com.kelompok_4.share_meal.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.Donasi
import com.kelompok_4.share_meal.databinding.FragmentAdminDonasiBinding
import com.kelompok_4.share_meal.helpers.DbHelpers
import com.kelompok_4.share_meal.home.pages.donasi.PenerimaanDonasiRecyclerAdapter

class AdminDonasiFragment : Fragment() {
    private lateinit var binding: FragmentAdminDonasiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_donasi, container, false)
        binding = FragmentAdminDonasiBinding.bind(view)

        // Set Toolbar Title
        activity?.findViewById<View>(R.id.admin__iv_back)?.visibility = View.GONE
        activity?.findViewById<TextView>(R.id.admin__toolbar_title)?.text = "Daftar Donasi"

        // Rv
        binding.rvAdminDonasi.adapter = PenerimaanDonasiRecyclerAdapter()

        DbHelpers.fetchDataByPath("donasi") {
            val donasiList = ArrayList<Donasi>()

            for (item in it!!.children) {
                val donasi = item.getValue(Donasi::class.java)!!
                donasiList.add(donasi)
            }

            (binding.rvAdminDonasi.adapter as PenerimaanDonasiRecyclerAdapter).addData(donasiList)
        }

        return view
    }

}