package com.example.dunyakasifi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dunyakasifi.databinding.ActivityCountrySelectionBinding

class CountrySelectionActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCountrySelectionBinding
    private lateinit var countryAdapter: CountryAdapter
    
    private val countries = listOf(
        Country("Türkiye", R.drawable.flag_turkey, "İstanbul", "Türk mutfağı"),
        Country("Fransa", R.drawable.flag_france, "Paris", "Eyfel Kulesi"),
        Country("Japonya", R.drawable.flag_japan, "Tokyo", "Sushi"),
        Country("Mısır", R.drawable.flag_egypt, "Kahire", "Piramitler"),
        Country("Brezilya", R.drawable.flag_brazil, "Rio de Janeiro", "Karnaval"),
        Country("Avustralya", R.drawable.flag_australia, "Sidney", "Kanguru")
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountrySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupRecyclerView()
        setupToolbar()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Ülke Seç"
    }
    
    private fun setupRecyclerView() {
        countryAdapter = CountryAdapter(countries) { country ->
            // Navigate to country detail/game
            val intent = Intent(this, CountryDetailActivity::class.java)
            intent.putExtra("country_name", country.name)
            intent.putExtra("country_capital", country.capital)
            intent.putExtra("country_famous", country.famousFor)
            startActivity(intent)
        }
        
        binding.countryRecyclerView.apply {
            layoutManager = GridLayoutManager(this@CountrySelectionActivity, 2)
            adapter = countryAdapter
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

data class Country(
    val name: String,
    val flagResId: Int,
    val capital: String,
    val famousFor: String
) 