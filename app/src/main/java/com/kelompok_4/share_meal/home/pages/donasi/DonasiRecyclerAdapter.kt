package com.kelompok_4.share_meal.home.pages.donasi

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelompok_4.share_meal.databinding.LayoutDonasiListOpenDonasiBinding
import com.kelompok_4.share_meal.home.pages.donasi.data.OpenDonasiList
import com.kelompok_4.share_meal.home.pages.donasi.data.openDonasiListDummy

class DonasiRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<OpenDonasiList> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            LayoutDonasiListOpenDonasiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
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

    class DonasiViewHolder(val binding: LayoutDonasiListOpenDonasiBinding) :
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
            val intent = Intent(itemView.context, DetailDonasiActivity::class.java)
            intent.putExtra("donasi_id", donasi.id)
            itemView.context.startActivity(intent)
        }
    }

    fun getDonasiById(id: String): OpenDonasiList {
        return openDonasiListDummy.find { it.id == id }!!
    }
}