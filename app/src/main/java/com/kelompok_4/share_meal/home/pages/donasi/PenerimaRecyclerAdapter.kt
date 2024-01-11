package com.kelompok_4.share_meal.home.pages.donasi

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kelompok_4.share_meal.data.OpenDonasiList
import com.kelompok_4.share_meal.data.Penerima
import com.kelompok_4.share_meal.data.Preference
import com.kelompok_4.share_meal.data.User
import com.kelompok_4.share_meal.data.openDonasiListDummy
import com.kelompok_4.share_meal.databinding.LayoutPenerimaDonasiUserBinding
import com.kelompok_4.share_meal.helpers.DbHelpers
import com.kelompok_4.share_meal.helpers.Helpers

class PenerimaRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Penerima> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            LayoutPenerimaDonasiUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PenerimaDonasiUser(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is PenerimaDonasiUser -> {
                holder.bind(items[position])
                holder.itemView.setOnClickListener {
                    holder.onClick(items[position])
                }
            }
        }
    }

    fun addData(list: List<Penerima>) {
        items = list
        notifyDataSetChanged()
    }

    class PenerimaDonasiUser(val binding: LayoutPenerimaDonasiUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(penerima: Penerima) {
            binding.tvPenerimaName.text = penerima.nama
            binding.tvPenerimaAddress.text = penerima.alamat

            var countPreference = 0
            DbHelpers.fetchDataByPath("preference") {
                countPreference = 0
                it!!.children.forEach { preference ->
                    val preference = preference.getValue(Preference::class.java)!!

                    if (preference.user_id == penerima.id_user) {
                        countPreference++
                    }
                }
                binding.tvPenerimaPreference.text = "$countPreference Preferensi"
            }

            DbHelpers.fetchSingleDataByPath("users/${penerima.id_user}") {
                val user = it!!.getValue(User::class.java)!!
                if (user.profile_picture != "") {
                    Glide.with(itemView.context).load(user.profile_picture).into(binding.ivPenerima)
                }
            }
        }

        fun onClick(penerima: Penerima) {
            val intent = Intent(itemView.context, DetailPenerimaDonasiActivity::class.java)

            // Set a custom transition

            intent.putExtra("penerima_id", penerima.id)
            itemView.context.startActivity(intent)
            Helpers.overridePendingEnterTransition(itemView.context as Activity)
        }
    }

    fun getDonasiById(id: String): OpenDonasiList {
        return openDonasiListDummy.find { it.id == id }!!
    }
}