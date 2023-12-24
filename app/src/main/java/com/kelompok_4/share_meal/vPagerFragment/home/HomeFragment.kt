package com.kelompok_4.share_meal.vPagerFragment.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kelompok_4.share_meal.MainActivity
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

        binding.DEVBtnHomeLogout.setOnClickListener {
            val sharedPreferences = requireActivity()
                .getSharedPreferences("SHARED_PREFERENCE", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoggedIn", false)

            editor.apply()

            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            (activity as HomeActivity).finish()
        }

        binding.DEVBtnHomeClearpref.setOnClickListener {
            val sharedPreferences = requireActivity()
                .getSharedPreferences("SHARED_PREFERENCE", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            (activity as HomeActivity).finish()
        }

        return view
    }

}