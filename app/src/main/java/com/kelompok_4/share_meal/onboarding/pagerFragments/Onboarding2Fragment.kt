package com.kelompok_4.share_meal.onboarding.pagerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.FragmentOnboarding2Binding

class Onboarding2Fragment : Fragment() {
    private lateinit var binding: FragmentOnboarding2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_onboarding2, container, false)

        binding = FragmentOnboarding2Binding.bind(view)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

//        binding.btnOnboardingNext.setOnClickListener {
//            viewPager?.currentItem = 2
//        }
//
//        binding.btnOnboardingBack.setOnClickListener {
//            viewPager?.currentItem = 0
//        }

        return view
    }

}