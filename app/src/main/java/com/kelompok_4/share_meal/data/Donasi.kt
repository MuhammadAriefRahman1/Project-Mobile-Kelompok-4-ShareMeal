package com.kelompok_4.share_meal.data


data class Donasi(
    var id: String = "",
    var penerima: Penerima = Penerima(),
    var donatur: User = User(),
    var kategori: String = "",
    var nama: String = "",
    var deskripsi: String = "",
    var satuan: String = "",
    var jumlah: Double = 0.0,
    var ekspirasi: String = "",
    var timestamp: Long = 0,
    var gambar: String = "",
    var status: StatusDonasi = StatusDonasi.PENDING,
)

enum class StatusDonasi {
    PENDING,
    DIPROSES,
    DITERIMA,
    DITOLAK,
}