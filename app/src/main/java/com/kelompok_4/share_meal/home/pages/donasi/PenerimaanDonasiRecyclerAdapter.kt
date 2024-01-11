package com.kelompok_4.share_meal.home.pages.donasi

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.Donasi
import com.kelompok_4.share_meal.data.StatusDonasi
import com.kelompok_4.share_meal.databinding.LayoutPenerimaanDonasiBinding
import com.kelompok_4.share_meal.helpers.Helpers

class PenerimaanDonasiRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<Donasi> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            LayoutPenerimaanDonasiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PenerimaanDonasi(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is PenerimaanDonasi -> {
                holder.bind(items[position])
                holder.itemView.setOnClickListener {
                    holder.onClick(items[position])
                }
            }
        }
    }

    fun addData(list: ArrayList<Donasi>) {
        items = list
        notifyDataSetChanged()
    }

    class PenerimaanDonasi(val binding: LayoutPenerimaanDonasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(donasi: Donasi) {
            binding.tvNama.text = donasi.nama
            binding.tvDonatur.text = donasi.donatur.nama_lengkap
            binding.tvSatuanJumlah.text = "${donasi.jumlah} ${donasi.satuan}"
            binding.tvKategori.text = donasi.kategori

            if (donasi.gambar != "") {
                Glide.with(itemView.context)
                    .load(donasi.gambar)
                    .into(binding.ivDonasi)
            }

            when (donasi.status) {
                StatusDonasi.PENDING -> {
                    binding.flStatus.setBackgroundResource(R.drawable.badge_disabled)
                    binding.tvStatus.text = "Pending"
                }

                StatusDonasi.DITERIMA -> {
                    binding.flStatus.setBackgroundResource(R.drawable.badge_success)
                    binding.tvStatus.text = "Diterima"
                }

                StatusDonasi.DITOLAK -> {
                    binding.flStatus.setBackgroundResource(R.drawable.badge_danger)
                    binding.tvStatus.text = "Ditolak"
                }

                StatusDonasi.DIPROSES -> {
                    binding.flStatus.setBackgroundResource(R.drawable.badge_warning)
                    binding.tvStatus.text = "Diproses"
                }
            }
        }

        fun onClick(donasi: Donasi) {
            if (donasi.penerima.id_user == Helpers.getUid()) {
                val intent = Intent(itemView.context, DetailPenerimaanDonasiActivity::class.java)
                intent.putExtra("donasi_id", donasi.id)
                itemView.context.startActivity(intent)
                Helpers.overridePendingEnterTransition(itemView.context as Activity)
            }
        }
    }

}