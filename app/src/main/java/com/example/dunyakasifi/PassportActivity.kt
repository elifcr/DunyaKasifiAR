package com.example.dunyakasifi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dunyakasifi.databinding.ActivityPassportBinding

class PassportActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityPassportBinding
    private lateinit var passportAdapter: PassportAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        loadPassportData()
        setupRecyclerView()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Pasaportum"
    }
    
    private fun loadPassportData() {
        val sharedPrefs = getSharedPreferences("DunyaKasifi", MODE_PRIVATE)
        val visitedCountries = sharedPrefs.getStringSet("visitedCountries", setOf()) ?: setOf()
        val mathScore = sharedPrefs.getInt("mathGameScore", 0)
        
        binding.visitedCountText.text = "Ziyaret Edilen Ülke: ${visitedCountries.size}/6"
        binding.mathScoreText.text = "En Yüksek Matematik Skoru: $mathScore"
        
        // Calculate progress percentage
        val progress = (visitedCountries.size * 100) / 6
        binding.progressText.text = "İlerleme: %$progress"
    }
    
    private fun setupRecyclerView() {
        val sharedPrefs = getSharedPreferences("DunyaKasifi", MODE_PRIVATE)
        val visitedCountries = sharedPrefs.getStringSet("visitedCountries", setOf()) ?: setOf()
        
        val passportItems = mutableListOf<PassportItem>()
        val countryInfoMap = mapOf(
            "Türkiye" to "Türkiye, zengin tarihi ve eşsiz mutfağıyla ünlüdür. İstanbul, Kapadokya ve Pamukkale gibi önemli turistik yerlere sahiptir.",
            "Fransa" to "Fransa, Eyfel Kulesi ve Louvre Müzesi ile bilinir. Paris, romantizmin başkentidir.",
            "Japonya" to "Japonya, geleneksel ve modern kültürün birleştiği bir ülkedir. Tokyo ve Kyoto en popüler şehirlerindendir.",
            "Mısır" to "Mısır, piramitleri ve Nil Nehri ile ünlüdür. Antik medeniyetlerin beşiğidir.",
            "Brezilya" to "Brezilya, Amazon Ormanları ve Rio Karnavalı ile tanınır. Renkli kültürüyle dikkat çeker.",
            "Avustralya" to "Avustralya, Sydney Opera Binası ve Büyük Set Resifi ile ünlüdür. Doğal yaşamı çok çeşitlidir."
        )
        
        // Add visited countries
        visitedCountries.forEach { countryName ->
            val flagResId = when (countryName) {
                "Türkiye" -> R.drawable.flag_turkey
                "Fransa" -> R.drawable.flag_france
                "Japonya" -> R.drawable.flag_japan
                "Mısır" -> R.drawable.flag_egypt
                "Brezilya" -> R.drawable.flag_brazil
                "Avustralya" -> R.drawable.flag_australia
                else -> R.drawable.flag_turkey
            }
            
            passportItems.add(PassportItem(countryName, flagResId, true))
        }
        
        // Add unvisited countries
        val allCountries = listOf("Türkiye", "Fransa", "Japonya", "Mısır", "Brezilya", "Avustralya")
        allCountries.filter { !visitedCountries.contains(it) }.forEach { countryName ->
            val flagResId = when (countryName) {
                "Türkiye" -> R.drawable.flag_turkey
                "Fransa" -> R.drawable.flag_france
                "Japonya" -> R.drawable.flag_japan
                "Mısır" -> R.drawable.flag_egypt
                "Brezilya" -> R.drawable.flag_brazil
                "Avustralya" -> R.drawable.flag_australia
                else -> R.drawable.flag_turkey
            }
            
            passportItems.add(PassportItem(countryName, flagResId, false))
        }
        
        passportAdapter = PassportAdapter(passportItems) { item ->
            if (item.isVisited) {
                // Ziyaret tarihi örnek: kayıtlarda tutulmuyorsa bugünün tarihi verelim
                val visitDate = System.currentTimeMillis()
                val info = countryInfoMap[item.countryName] ?: "Bu ülke hakkında bilgi bulunamadı."
                val intent = Intent(this, CountryVisitDetailActivity::class.java)
                intent.putExtra("countryName", item.countryName)
                intent.putExtra("flagResId", item.flagResId)
                intent.putExtra("visitDate", visitDate)
                intent.putExtra("info", info)
                startActivity(intent)
            }
        }
        binding.passportRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@PassportActivity)
            adapter = passportAdapter
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

data class PassportItem(
    val countryName: String,
    val flagResId: Int,
    val isVisited: Boolean
) 