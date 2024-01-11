package com.kelompok_4.share_meal.data

data class Preference(
    var id: String = "",
    var nama: String = "",
    var kategori: String = "",
    var deskripsi: String = "",
    var user_id: String = "",
    var created_at: Long = System.currentTimeMillis(),
    var updated_at: Long = System.currentTimeMillis()
)
