package com.kelompok_4.share_meal.data

data class Penerima(
    val id: String = "",
    val id_user: String = "",
    val nama: String = "",
    val alamat: String = "",
    val deskripsi: String = "",
    var verification: Boolean = false,
)