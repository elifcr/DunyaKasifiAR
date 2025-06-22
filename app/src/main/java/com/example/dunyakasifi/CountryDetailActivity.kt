package com.example.dunyakasifi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dunyakasifi.databinding.ActivityCountryDetailBinding

class CountryDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCountryDetailBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        loadCountryData()
        setupButtons()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("country_name") ?: "Ülke Detayı"
    }
    
    private fun loadCountryData() {
        val countryName = intent.getStringExtra("country_name") ?: ""
        val countryCapital = intent.getStringExtra("country_capital") ?: ""
        val countryFamous = intent.getStringExtra("country_famous") ?: ""
        
        binding.countryName.text = countryName
        binding.countryCapital.text = "Başkent: $countryCapital"
        binding.countryFamous.text = "Ünlü: $countryFamous"
        
        // Set country flag based on name
        val flagResId = when (countryName) {
            "Türkiye" -> R.drawable.flag_turkey
            "Fransa" -> R.drawable.flag_france
            "Japonya" -> R.drawable.flag_japan
            "Mısır" -> R.drawable.flag_egypt
            "Brezilya" -> R.drawable.flag_brazil
            "Avustralya" -> R.drawable.flag_australia
            else -> R.drawable.flag_turkey
        }
        
        binding.countryFlag.setImageResource(flagResId)
    }
    
    private fun setupButtons() {
        binding.playGameButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java).apply {
                putExtra("country_name", intent.getStringExtra("country_name"))
            })
        }
        
        binding.learnMoreButton.setOnClickListener {
            showCountryInfo()
        }
        
        binding.visitCountryButton.setOnClickListener {
            markCountryAsVisited()
            showVisitSuccess()
        }
    }
    
    private fun showCountryInfo() {
        val countryName = intent.getStringExtra("country_name") ?: ""
        val info = when (countryName) {
            "Türkiye" -> "Türkiye, Avrupa ve Asya kıtalarında yer alan bir ülkedir. İstanbul Boğazı ile iki kıtayı birbirine bağlar. Türk mutfağı dünya çapında ünlüdür."
            "Fransa" -> "Fransa, Batı Avrupa'da yer alan bir ülkedir. Moda, sanat ve gastronomi alanlarında dünya çapında ünlüdür. Eyfel Kulesi Paris'in sembolüdür."
            "Japonya" -> "Japonya, Doğu Asya'da yer alan bir ada ülkesidir. Teknoloji ve geleneksel kültürün harmanlandığı bir ülkedir. Sushi dünya çapında ünlüdür."
            "Mısır" -> "Mısır, Kuzey Afrika'da yer alan bir ülkedir. Antik piramitleri ve Nil Nehri ile ünlüdür. Firavunlar döneminden kalma tarihi eserler bulunur."
            "Brezilya" -> "Brezilya, Güney Amerika'da yer alan en büyük ülkedir. Amazon ormanları ve karnavalı ile ünlüdür. Futbol kültürü çok gelişmiştir."
            "Avustralya" -> "Avustralya, Okyanusya kıtasında yer alan bir ülkedir. Kanguru ve koala gibi özgün hayvanları ile ünlüdür. Büyük Set Resifi dünya mirasıdır."
            else -> "Bu ülke hakkında bilgi bulunamadı."
        }
        
        // Show dialog with country information
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(countryName)
            .setMessage(info)
            .setPositiveButton("Tamam") { _, _ -> }
            .create()
        dialog.show()
    }
    
    private fun markCountryAsVisited() {
        val countryName = intent.getStringExtra("country_name") ?: ""
        val sharedPrefs = getSharedPreferences("DunyaKasifi", MODE_PRIVATE)
        val visitedCountries = sharedPrefs.getStringSet("visitedCountries", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        visitedCountries.add(countryName)
        sharedPrefs.edit().putStringSet("visitedCountries", visitedCountries).apply()
    }
    
    private fun showVisitSuccess() {
        val countryName = intent.getStringExtra("country_name") ?: ""
        android.widget.Toast.makeText(this, "$countryName ziyaret edildi! Pasaportuna damga eklendi! 🎉", android.widget.Toast.LENGTH_LONG).show()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
} 