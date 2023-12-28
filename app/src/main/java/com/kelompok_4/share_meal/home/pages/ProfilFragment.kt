package com.kelompok_4.share_meal.home.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.FragmentProfilBinding
import com.kelompok_4.share_meal.home.HomeActivity

class ProfilFragment : Fragment() {
    private lateinit var binding: FragmentProfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil, container, false)
        binding = FragmentProfilBinding.bind(view)

        // Set the status bar color
        activity?.window?.statusBarColor = resources.getColor(R.color.colorPrimary)

        // Set Buttons
        binding.cvProfilKeluar.setOnClickListener {
            (activity as HomeActivity).DEBUGLogout(view.rootView)
        }

        // Return the fragment view/layout
        return view
    }

}