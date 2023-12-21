package com.kelompok_4.share_meal.vPagerFragment.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.FragmentViewPagerBinding
import com.kelompok_4.share_meal.vPagerFragment.ViewPagerAdapter
import com.kelompok_4.share_meal.vPagerFragment.onboarding.pagerFragments.Onboarding1Fragment
import com.kelompok_4.share_meal.vPagerFragment.onboarding.pagerFragments.Onboarding2Fragment
import com.kelompok_4.share_meal.vPagerFragment.onboarding.pagerFragments.Onboarding3Fragment

class ViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        binding = FragmentViewPagerBinding.bind(view)

        val fragmentList = arrayListOf<Fragment>(
            Onboarding1Fragment(),
            Onboarding2Fragment(),
            Onboarding3Fragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter

        return view
    }
}