package com.kelompok_4.share_meal.home.pages

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.FragmentProfilBinding
import com.kelompok_4.share_meal.helpers.Helpers
import com.kelompok_4.share_meal.home.HomeActivity
import com.kelompok_4.share_meal.home.pages.profil.AturPreferenceActivity
import com.kelompok_4.share_meal.home.pages.profil.PengajuanPenerimaActivity
import com.kelompok_4.share_meal.home.pages.profil.PrediksiDonasiActivity

class ProfilFragment : Fragment() {
    private lateinit var binding: FragmentProfilBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil, container, false)
        binding = FragmentProfilBinding.bind(view)

        // Set database reference
        dbRef = Firebase.database.getReference("penerima")

        // Initialize shared preferences
        val sharedPreferences = activity?.getSharedPreferences(
            "SHARED_PREFERENCE",
            Context.MODE_PRIVATE
        )

        // Set the status bar color
        activity?.window?.statusBarColor = resources.getColor(R.color.colorPrimary)

        // Set Buttons
        binding.cvProfilKeluar.setOnClickListener {
            (activity as HomeActivity).logout(view.rootView)
        }
        binding.cvProfilPrediksi.setOnClickListener {
            startActivity(Intent(activity, PrediksiDonasiActivity::class.java))
            Helpers.overridePendingEnterTransition(requireActivity())
        }
        binding.cvProfilAturPreference.setOnClickListener {
            startActivity(Intent(activity, AturPreferenceActivity::class.java))
            Helpers.overridePendingEnterTransition(requireActivity())
        }

        binding.cvProfilRegisPenerima.setOnClickListener {
            // Check if user is already registered as a penerima (by checking if the user_id is found in an existing penerima)
            val userId = sharedPreferences?.getString("id_user", "")

            dbRef.orderByChild("id_user").equalTo(userId).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (data in snapshot.children) {
                                if (data.child("id_user").value == userId) {
                                    // If user is already registered as a penerima, show a toast
                                    activity?.let { it1 ->
                                        AlertDialog.Builder(it1)
                                            .setTitle("Pemberitahuan")
                                            .setMessage("Anda sudah terdaftar sebagai penerima, silahkan tunggu konfirmasi dari admin")
                                            .setPositiveButton("OK") { dialog, which ->
                                                dialog.dismiss()
                                            }
                                            .show()
                                    }
                                } else {
                                    // If user is not yet registered as a penerima, navigate to the register penerima page
                                    registerPenerima(userId.toString())
                                }
                            }
                        } else {
                            // If user is not yet registered as a penerima, navigate to the register penerima page
                            registerPenerima(userId.toString())
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Do nothing
                    }
                }
            )

        }


        // Set profile
        sharedPreferences?.getString("nama_lengkap", "User")?.let {
            binding.tvProfilNama.text = it
        }

        sharedPreferences?.getString("role", "User")?.let {
            binding.tvProfilRole.text = it
        }

        // Set Role Specific Buttons
        var role = sharedPreferences?.getString("role", "User")
        if (role == "Donatur") {
            binding.cvProfilRegisPenerima.visibility = View.VISIBLE
        } else if (role == "Penerima") {
            binding.cvProfilRegisPenerima.visibility = View.GONE
        }


        // Return the fragment view/layout
        return view
    }

    fun registerPenerima(userId: String) {
        val intent = Intent(activity, PengajuanPenerimaActivity::class.java)
        val options = ActivityOptions
            .makeCustomAnimation(
                requireContext(),
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        intent.putExtra("id_user", userId)
        startActivity(intent, options.toBundle())
    }

}