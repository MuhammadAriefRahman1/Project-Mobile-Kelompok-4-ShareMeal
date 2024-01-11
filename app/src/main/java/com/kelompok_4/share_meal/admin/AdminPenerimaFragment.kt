package com.kelompok_4.share_meal.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.admin.penerima.PenerimaRecyclerAdapter
import com.kelompok_4.share_meal.data.Penerima
import com.kelompok_4.share_meal.databinding.FragmentAdminPenerimaBinding

class AdminPenerimaFragment : Fragment() {
    private lateinit var binding: FragmentAdminPenerimaBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            R.layout.fragment_admin_penerima,
            container,
            false
        )
        binding = FragmentAdminPenerimaBinding.bind(view)


        // Set status bar color
        activity?.window?.statusBarColor = activity?.getColor(R.color.white)!!

        // Set DB
        dbRef = FirebaseDatabase.getInstance().getReference("penerima")

        // Recycler View
        binding.rvAdminPenerima.apply {
            layoutManager = LinearLayoutManager(activity)
            val penerimaAdapter = PenerimaRecyclerAdapter()
            this.adapter = penerimaAdapter
        }

        val penerimaList = arrayListOf<Penerima>()

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    penerimaList.clear()
                    for (penerima in snapshot.children) {
                        val penerimaData = penerima.getValue(Penerima::class.java)
                        if (penerimaData != null) {
                            penerimaList.add(penerimaData)
                        }
                    }

                    Log.d("PENERIMA", penerimaList.toString())

                    (binding.rvAdminPenerima.adapter as PenerimaRecyclerAdapter).addData(
                        penerimaList
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        // Set Toolbar Title
        activity?.findViewById<ImageView>(R.id.admin__iv_back)?.visibility = View.GONE
        activity?.findViewById<TextView>(R.id.admin__toolbar_title)?.text = "Penerima Donasi"


        // Return the fragment view/layout
        return view
    }
}