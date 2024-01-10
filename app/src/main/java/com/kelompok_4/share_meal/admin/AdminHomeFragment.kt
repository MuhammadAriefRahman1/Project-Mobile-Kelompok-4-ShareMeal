package com.kelompok_4.share_meal.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.FragmentAdminHomeBinding

class AdminHomeFragment : Fragment() {
    private lateinit var binding: FragmentAdminHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_home, container, false)
        binding = FragmentAdminHomeBinding.bind(view)

        // Set Toolbar Title
        activity?.findViewById<ImageView>(R.id.admin__iv_back)?.visibility = View.GONE
        activity?.findViewById<TextView>(R.id.admin__toolbar_title)?.text = "Share Meal"

        // Return View
        return view
    }
}