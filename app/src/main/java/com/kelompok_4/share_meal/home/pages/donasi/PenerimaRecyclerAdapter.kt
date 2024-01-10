package com.kelompok_4.share_meal.home.pages.donasi

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelompok_4.share_meal.data.OpenDonasiList
import com.kelompok_4.share_meal.data.Penerima
import com.kelompok_4.share_meal.data.openDonasiListDummy
import com.kelompok_4.share_meal.databinding.LayoutPenerimaDonasiUserBinding
import com.kelompok_4.share_meal.helpers.Helpers

class PenerimaRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<Penerima> = ArrayList()
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

    fun addData(list: ArrayList<Penerima>) {
        items = list
        notifyDataSetChanged()
    }

    class PenerimaDonasiUser(val binding: LayoutPenerimaDonasiUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val penerimaName = binding.tvPenerimaName
        val penerimaAddress = binding.tvPenerimaAddress
        val penerimaPreference = binding.tvPenerimaPreference

        fun bind(penerima: Penerima) {
            penerimaName.text = penerima.nama
            penerimaAddress.text = penerima.alamat
            penerimaPreference.text = "0 Preferensi"
        }

        fun onClick(penerima: Penerima) {
            val intent = Intent(itemView.context, DetailPenerimaDonasi::class.java)

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