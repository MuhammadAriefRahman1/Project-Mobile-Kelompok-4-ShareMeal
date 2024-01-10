package com.kelompok_4.share_meal.home.pages.profil

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kelompok_4.share_meal.R
import com.kelompok_4.share_meal.databinding.ActivityPrediksiDonasiBinding
import org.json.JSONObject

class PrediksiDonasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrediksiDonasiBinding
    private lateinit var mQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrediksiDonasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar color
        window.statusBarColor = getColor(R.color.white)

        // Set Queue
        mQueue = Volley.newRequestQueue(this)

        // Set back button
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        // Set Spinner Kategori
        val spinnerAdapterTipe = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf(
                "Makanan Siap Saji",
                "Bahan Pokok",
                "Buah Buahan"
            )

        )
        spinnerAdapterTipe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spPrediksiTipe.adapter = spinnerAdapterTipe


        // Set Spinner Tipe
        val spinnerAdapterNama = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf(
                "Mie Instan",
                "Gula",
                "Minyak Goreng",
                "Sarden",
                "Beras",
                "Buah Jeruk",
                "Mie instan",
                "Buah Salak",
                "Bubur Instan"
            )

        )
        spinnerAdapterNama.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spPrediksiNama.adapter = spinnerAdapterNama

        // Set Spinner Satuan
        val spinnerAdapterSatuan = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf(
                "buah",
                "kg",
                "liter",
                "kaleng",
                "bungkus"
            )

        )
        spinnerAdapterSatuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spPrediksiSatuan.adapter = spinnerAdapterSatuan


        // Set Submit
        binding.btnPrediksiSubmit.setOnClickListener {
            val tipe = binding.spPrediksiTipe.selectedItem.toString()
            val nama = binding.spPrediksiNama.selectedItem.toString()
            val satuan = binding.spPrediksiSatuan.selectedItem.toString()
            val jumlah = binding.etPrediksiJumlah.text.toString()

            var prediksiData = JSONObject()

            prediksiData.put("kategori", tipe)
            prediksiData.put("tipe", nama)
            prediksiData.put("satuan", satuan)
            prediksiData.put("jumlah", jumlah.toInt())

            Log.d("API", prediksiData.toString())

            var api_url = "https://2712-2a09-bac5-3a5c-16c8-00-245-5f.ngrok-free.app/predict"

            // Create a post request to the API, and sending the data
            val request = JsonObjectRequest(
                Request.Method.POST,
                api_url,
                prediksiData,
                { response ->
                    try {
                        Log.d("API SUCCESS", response.toString())
                    } catch (e: Exception) {
                        Log.d("API ERROR", e.toString())
                    }

                },
                { error ->
                    Log.d("API ERROR", error.toString())
                }
            )


            // Add the request to the RequestQueue.
            mQueue.add(request)

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}