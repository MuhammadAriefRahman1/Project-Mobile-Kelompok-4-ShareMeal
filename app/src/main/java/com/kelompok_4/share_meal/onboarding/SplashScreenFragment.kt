package com.kelompok_4.share_meal.onboarding

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kelompok_4.share_meal.R

class SplashScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (isOnboardingFinished()) {
            findNavController()
                .navigate(R.id.action_splashScreenFragment_to_loginFragment)
        } else {
            Handler().postDelayed({
                findNavController()
                    .navigate(R.id.action_splashScreenFragment_to_viewPagerFragment)
            }, 2000)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    private fun isOnboardingFinished(): Boolean {
        val sharedPreferences = requireActivity()
            .getSharedPreferences("SHARED_PREFERENCE", android.content.Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("onBoardingFinished", false)
    }
}