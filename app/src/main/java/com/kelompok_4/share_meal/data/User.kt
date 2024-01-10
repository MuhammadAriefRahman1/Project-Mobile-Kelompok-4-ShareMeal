package com.kelompok_4.share_meal.data

data class User(
    val id: String = "",
    val email: String = "",
    val nama_lengkap: String = "",
    val alamat: String = "",
    val no_hp: String = "",
    var role: String = "",
)