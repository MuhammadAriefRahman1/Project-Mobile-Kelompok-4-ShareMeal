package com.kelompok_4.share_meal.home.pages.donasi.data

data class OpenDonasiList(
    val id: String,
    val kategori: String,
    val jenis: String,
    val nama: String,
    val deskripsi: String,
    val alamat: String,
)

val openDonasiListDummy = arrayListOf(
    OpenDonasiList(
        "1",
        "Bahan Baku",
        "Beras",
        "Bahan Baku Beras",
        "Beras 5kg",
        "Jl. Raya Bogor No. 5, Jakarta Timur",
    ),
    OpenDonasiList(
        "2",
        "Bahan Baku",
        "Beras",
        "Bahan Baku Beras",
        "Beras 10kg",
        "Jl. Raya Bogor No. 5, Jakarta Timur",
    ),
    OpenDonasiList(
        "3",
        "Bahan Baku",
        "Beras",
        "Bahan Baku Beras",
        "Beras 25kg",
        "Jl. Raya Bogor No. 5, Jakarta Timur",
    ),
)

