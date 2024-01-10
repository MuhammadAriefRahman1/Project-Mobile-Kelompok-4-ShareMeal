package com.kelompok_4.share_meal.home.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.Penerima
import com.kelompok_4.share_meal.databinding.FragmentDonasiBinding
import com.kelompok_4.share_meal.home.pages.donasi.PenerimaRecyclerAdapter

class DonasiFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentDonasiBinding
    private lateinit var dbRef_penerima: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val donasiView = inflater.inflate(R.layout.fragment_donasi, container, false)
        binding = FragmentDonasiBinding.bind(donasiView)

        // Set DB
        dbRef_penerima = FirebaseDatabase.getInstance().getReference("penerima")

        // Set status bar color
        activity?.window?.statusBarColor = activity?.getColor(R.color.white)!!

        // Search Bar
        binding.teDonasiSearch.addTextChangedListener {
            if (binding.teDonasiSearch.text.toString().isNotEmpty()) {
                binding.btnDonasiSearch.visibility = View.VISIBLE
            } else {
                binding.btnDonasiSearch.visibility = View.GONE
            }
        }


        // Spinner (Category Dropdown)
        var categoryDropdownAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("Kategori", "Bahan Baku", "Makanan Cepat Saji"),
        )
        categoryDropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDonasiKategori.adapter = categoryDropdownAdapter
        binding.spinnerDonasiKategori.onItemSelectedListener = this

        binding.btnDonasiSearch.setOnClickListener {
            val search = binding.teDonasiSearch.text.toString()

            Toast.makeText(
                activity,
                "Mencari donasi dengan nama $search",
                Toast.LENGTH_SHORT
            ).show(
            )

            binding.teDonasiSearch.text?.clear()
            binding.btnDonasiSearch.visibility = View.GONE
        }

        // Recycler View
        binding.rvDonasi.apply {
            layoutManager = LinearLayoutManager(activity)
            val penerima = PenerimaRecyclerAdapter()
            this.adapter = penerima
        }.let {
            val penerimaList = arrayListOf<Penerima>()

            dbRef_penerima.addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            penerimaList.clear()
                            for (penerima in snapshot.children) {
                                val penerimaData = penerima.getValue(Penerima::class.java)
                                if (penerimaData != null) {
                                    penerimaList.add(penerimaData)
                                }
                            }

                            (binding.rvDonasi.adapter as PenerimaRecyclerAdapter).addData(
                                penerimaList
                            )
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                }
            )
        }

        return donasiView
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}