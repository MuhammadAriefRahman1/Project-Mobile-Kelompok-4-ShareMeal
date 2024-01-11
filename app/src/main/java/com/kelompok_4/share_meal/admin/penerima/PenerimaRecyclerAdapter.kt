package com.kelompok_4.share_meal.admin.penerima

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.data.OpenDonasiList
import com.kelompok_4.share_meal.data.Penerima
import com.kelompok_4.share_meal.data.User
import com.kelompok_4.share_meal.data.openDonasiListDummy
import com.kelompok_4.share_meal.databinding.LayoutAdminPenerimaDonasiBinding
import com.kelompok_4.share_meal.helpers.DbHelpers
import com.kelompok_4.share_meal.helpers.Helpers

class PenerimaRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Penerima> = ArrayList()
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

    fun addData(list: List<Penerima>) {
        items = list
        notifyDataSetChanged()
    }

    class AdminPenerimaViewHolder(val binding: LayoutAdminPenerimaDonasiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(penerima: Penerima) {
            binding.tvPenerimaName.text = penerima.nama
            binding.tvPenerimaAddress.text = penerima.alamat
            binding.tvPenerimaDetailIsVerified.text =
                if (penerima.verification == true)
                    "Sudah Diverifikasi"
                else
                    "Belum Diverifikasi"

            binding.flPenerimaIsVerified.background =
                if (penerima.verification == true)
                    AppCompatResources.getDrawable(itemView.context, R.drawable.badge_success)
                else
                    AppCompatResources.getDrawable(itemView.context, R.drawable.badge_danger)

            DbHelpers.fetchSingleDataByPath("users/${penerima.id_user}") {
                val user = it!!.getValue(User::class.java)!!

                if (user.profile_picture != "") {
                    Glide.with(itemView.context).load(user.profile_picture)
                        .into(binding.ivPenerimaPicture)
                }
            }
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