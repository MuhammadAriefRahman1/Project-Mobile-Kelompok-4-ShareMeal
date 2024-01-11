package com.kelompok_4.share_meal.home.pages

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.User
import com.kelompok_4.share_meal.databinding.FragmentProfilBinding
import com.kelompok_4.share_meal.helpers.DbHelpers
import com.kelompok_4.share_meal.helpers.Helpers
import com.kelompok_4.share_meal.home.HomeActivity
import com.kelompok_4.share_meal.home.pages.profil.AturPreferenceActivity
import com.kelompok_4.share_meal.home.pages.profil.AturProfilActivity
import com.kelompok_4.share_meal.home.pages.profil.PengajuanPenerimaActivity

class ProfilFragment : Fragment() {
    private lateinit var binding: FragmentProfilBinding
    private lateinit var dbRef_penerima: DatabaseReference
    private lateinit var dbRef_users: DatabaseReference
    private lateinit var db_storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil, container, false)
        binding = FragmentProfilBinding.bind(view)

        // Set database reference
        dbRef_penerima = Firebase.database.getReference("penerima")
        dbRef_users = Firebase.database.getReference("users")
        db_storage = FirebaseStorage.getInstance()

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
        binding.cvProfilAturProfil.setOnClickListener {
            startActivity(Intent(activity, AturProfilActivity::class.java))
            Helpers.overridePendingEnterTransition(requireActivity())
        }
//        binding.cvProfilPrediksi.setOnClickListener {
//            startActivity(Intent(activity, PrediksiDonasiActivity::class.java))
//            Helpers.overridePendingEnterTransition(requireActivity())
//        }
        binding.cvProfilAturPreference.setOnClickListener {
            startActivity(Intent(activity, AturPreferenceActivity::class.java))
            Helpers.overridePendingEnterTransition(requireActivity())
        }

        binding.cvProfilRegisPenerima.setOnClickListener {
            val uid = Helpers.getUid()

            DbHelpers.fetchSingleDataByCondition("penerima", Pair("id_user", uid)) { penerima ->
                // Check if user is already registered as a penerima
                if (penerima != null && penerima.exists()) {
                    Helpers.makeAlertDialog(
                        requireActivity(),
                        "Pemberitahuan",
                        "Anda sudah terdaftar sebagai penerima, silahkan tunggu konfirmasi dari admin"
                    )
                } else {
                    // Check if user has completed their profile
                    DbHelpers.fetchSingleDataByPath("users/$uid") { db_user ->
                        val user = db_user!!.getValue(User::class.java)!!

                        if (user.alamat == "" || user.no_hp == "" || user.alamat == "") {
                            Helpers.makeAlertDialog(
                                requireActivity(),
                                "Pemberitahuan",
                                "Silahkan lengkapi profil anda terlebih dahulu"
                            )
                        } else {
                            // If user is not yet registered as a penerima, navigate to the register penerima page
                            registerPenerima()
                        }
                    }


                }
            }

        }

        DbHelpers.fetchSingleDataByPath("users/${Helpers.getUid()}") {
            val user = it!!.getValue(User::class.java)!!
            binding.tvProfilNama.text = user.nama_lengkap
            binding.tvProfilRole.text = user.role

            // Get profile picture without helpers
            if (user.profile_picture != "") {
                Glide.with(view)
                    .load(user.profile_picture)
                    .into(binding.civProfil)
            }

            // Set Role Specific Buttons

            if (user.role == "Donatur") {
                binding.cvProfilRegisPenerima.visibility = View.VISIBLE
                binding.cvProfilAturPreference.visibility = View.GONE
            } else if (user.role == "Penerima") {
                binding.cvProfilRegisPenerima.visibility = View.GONE
                binding.cvProfilAturPreference.visibility = View.VISIBLE
            }
        }

        return view

    }

    fun registerPenerima() {
        val intent = Intent(activity, PengajuanPenerimaActivity::class.java)
        intent.putExtra("id_user", Helpers.getUid())
        startActivity(intent)
        Helpers.overridePendingEnterTransition(requireActivity())
    }

}