package com.kelompok_4.share_meal.onboarding.pagerFragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.FragmentOnboarding3Binding

class Onboarding3Fragment : Fragment() {
    private lateinit var binding: FragmentOnboarding3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_onboarding3, container, false)

        binding = FragmentOnboarding3Binding.bind(view)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.btnOnboardingBack.setOnClickListener {
            viewPager?.currentItem = 1
        }

        binding.btnOnboardingFinish.setOnClickListener {
            val sharedPreferences = requireActivity()
                .getSharedPreferences("SHARED_PREFERENCE", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("onBoardingFinished", true)
            editor.apply()

            findNavController()
                .navigate(R.id.action_viewPagerFragment_to_loginFragment)
        }

        return view
    }


}