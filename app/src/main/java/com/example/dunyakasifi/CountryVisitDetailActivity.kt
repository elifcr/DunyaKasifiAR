package com.example.dunyakasifi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dunyakasifi.databinding.ActivityCountryVisitDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class CountryVisitDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCountryVisitDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryVisitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val countryName = intent.getStringExtra("countryName") ?: ""
        val flagResId = intent.getIntExtra("flagResId", 0)
        val visitDate = intent.getLongExtra("visitDate", 0L)
        val info = intent.getStringExtra("info") ?: ""

        binding.countryFlag.setImageResource(flagResId)
        binding.countryName.text = countryName
        binding.countryInfo.text = info
        binding.visitDate.text = "Ziyaret Tarihi: " + SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(visitDate))
    }
} 