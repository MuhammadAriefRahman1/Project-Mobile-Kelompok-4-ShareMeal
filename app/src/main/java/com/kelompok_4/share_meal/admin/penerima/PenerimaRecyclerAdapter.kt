package com.kelompok_4.share_meal.admin.penerima

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.OpenDonasiList
import com.kelompok_4.share_meal.data.Penerima
import com.kelompok_4.share_meal.data.openDonasiListDummy
import com.kelompok_4.share_meal.databinding.LayoutAdminPenerimaDonasiBinding
import com.kelompok_4.share_meal.helpers.Helpers

class PenerimaRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<Penerima> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            LayoutAdminPenerimaDonasiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return AdminPenerimaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is AdminPenerimaViewHolder -> {
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

    class AdminPenerimaViewHolder(val binding: LayoutAdminPenerimaDonasiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val penerimaName = binding.tvPenerimaName
        val penerimaAddress = binding.tvPenerimaAddress
        val penerimaVerified = binding.tvPenerimaDetailIsVerified
        val penerimaFl = binding.flPenerimaIsVerified

        fun bind(penerima: Penerima) {
            penerimaName.text = penerima.nama
            penerimaAddress.text = penerima.alamat
            penerimaVerified.text =
                if (penerima.verification == true)
                    "Sudah Diverifikasi"
                else
                    "Belum Diverifikasi"

            penerimaFl.background =
                if (penerima.verification == true)
                    AppCompatResources.getDrawable(itemView.context, R.drawable.badge_success)
                else
                    AppCompatResources.getDrawable(itemView.context, R.drawable.badge_danger)
        }

        fun onClick(penerima: Penerima) {
            val intent = Intent(itemView.context, DetailPenerimaDonasi::class.java)

            // Set a custom transition
            val options = ActivityOptions
                .makeCustomAnimation(
                    itemView.context as Activity,
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )

            intent.putExtra("penerima_id", penerima.id)
            itemView.context.startActivity(intent, options.toBundle())
            Helpers.overridePendingEnterTransition(itemView.context as Activity)
        }
    }

    fun getDonasiById(id: String): OpenDonasiList {
        return openDonasiListDummy.find { it.id == id }!!
    }
}