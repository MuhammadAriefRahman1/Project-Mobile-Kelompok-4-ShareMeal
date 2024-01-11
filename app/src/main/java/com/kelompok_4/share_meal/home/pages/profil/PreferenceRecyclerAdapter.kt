package com.kelompok_4.share_meal.home.pages.profil

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelompok_4.share_meal.data.OpenDonasiList
import com.kelompok_4.share_meal.data.Preference
import com.kelompok_4.share_meal.data.openDonasiListDummy
import com.kelompok_4.share_meal.databinding.LayoutItemPreferenceDonasiBinding
import com.kelompok_4.share_meal.helpers.Helpers

class PreferenceRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Preference> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            LayoutItemPreferenceDonasiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PreferenceDonasiPenerima(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is PreferenceDonasiPenerima -> {
                holder.bind(items[position])

                holder.itemView.setOnClickListener {
                    holder.onClick(items[position])
                }
            }
        }
    }

    fun addData(list: List<Preference>) {
        items = list
        notifyDataSetChanged()
    }

    class PreferenceDonasiPenerima(val binding: LayoutItemPreferenceDonasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(preference: Preference) {
            binding.tvNama.text = preference.nama
            binding.tvKategori.text = preference.kategori
        }

        fun onClick(preference: Preference) {
            if (preference.user_id == Helpers.getUid()) {
                val intent = Intent(itemView.context, DetailAturPreferenceActivity::class.java)
                intent.putExtra("preference_id", preference.id)
                itemView.context.startActivity(intent)
                Helpers.overridePendingEnterTransition(itemView.context as Activity)
            }
        }
    }

    fun getDonasiById(id: String): OpenDonasiList {
        return openDonasiListDummy.find { it.id == id }!!
    }
}