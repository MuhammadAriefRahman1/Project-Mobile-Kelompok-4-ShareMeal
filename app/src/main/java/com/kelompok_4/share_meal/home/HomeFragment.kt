package com.kelompok_4.share_meal.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)

        // Set the status bar color
        activity?.window?.statusBarColor = activity?.getColor(R.color.white)!!

        // Set user greeting
        val sharedPreferences = activity?.getSharedPreferences(
            "SHARED_PREFERENCE",
            Context.MODE_PRIVATE
        )
        sharedPreferences?.getString("nama_lengkap", "User")?.let {
            binding.tvHomeGreetings.text = "Selamat datang, $it!"
        }

        // Return the fragment view/layout
        return view
    }

}