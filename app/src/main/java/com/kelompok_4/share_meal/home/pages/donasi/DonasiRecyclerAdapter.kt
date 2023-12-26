package com.kelompok_4.share_meal.home.pages.donasi

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kelompok_4.share_meal.databinding.LayoutListOpenDonasiBinding
import com.kelompok_4.share_meal.home.pages.donasi.data.OpenDonasiList

class DonasiRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<OpenDonasiList> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            LayoutListOpenDonasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DonasiViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is DonasiViewHolder -> {
                holder.bind(items[position])
                holder.itemView.setOnClickListener {
                    holder.onClick(items[position])
                }
            }
        }
    }

    fun addData(list: List<OpenDonasiList>) {
        items = list
    }

    class DonasiViewHolder(val binding: LayoutListOpenDonasiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val donasiNama = binding.tvDonationName
        val donasiAddress = binding.tvDonationAddress
        val donasiCategory = binding.tvDonationCategory
        val donasiType = binding.tvDonationType

        fun bind(donasi: OpenDonasiList) {
            donasiNama.text = donasi.nama
            donasiAddress.text = donasi.alamat
            donasiCategory.text = donasi.kategori
            donasiType.text = donasi.jenis
        }

        fun onClick(donasi: OpenDonasiList) {
            Toast.makeText(
                itemView.context,
                "Membuka donasi ${donasi.nama}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}