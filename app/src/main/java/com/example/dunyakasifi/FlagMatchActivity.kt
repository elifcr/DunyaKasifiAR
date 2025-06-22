package com.example.dunyakasifi

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dunyakasifi.databinding.ActivityFlagMatchBinding

class FlagMatchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlagMatchBinding
    private val countries = listOf(
        Pair("Türkiye", R.drawable.flag_turkey),
        Pair("Fransa", R.drawable.flag_france),
        Pair("Japonya", R.drawable.flag_japan),
        Pair("Brezilya", R.drawable.flag_brazil)
    )
    private val matches = mutableMapOf<Int, Int>() // countryIdx -> flagIdx
    private var selectedCountryIdx: Int? = null
    private var selectedFlagIdx: Int? = null
    private lateinit var shuffled: List<Pair<String, Int>>
    private lateinit var flagOrder: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlagMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupGame()
    }

    private fun setupGame() {
        shuffled = countries.shuffled()
        flagOrder = shuffled.map { it.second }.shuffled()
        val countryViews = listOf(binding.country1, binding.country2, binding.country3, binding.country4)
        val flagViews = listOf(binding.flag1, binding.flag2, binding.flag3, binding.flag4)
        val matchFlagViews = listOf(binding.matchFlag1, binding.matchFlag2, binding.matchFlag3, binding.matchFlag4)

        // Reset UI
        for (i in 0..3) {
            countryViews[i].alpha = 1f
            flagViews[i].alpha = 1f
            matchFlagViews[i].setImageDrawable(null)
            matchFlagViews[i].visibility = ImageView.INVISIBLE
        }
        matches.clear()
        selectedCountryIdx = null
        selectedFlagIdx = null
        binding.scoreText.visibility = TextView.GONE
        binding.restartButton.visibility = TextView.GONE
        binding.checkButton.isEnabled = true

        // Set country names
        for (i in countryViews.indices) {
            countryViews[i].text = shuffled[i].first
            countryViews[i].setOnClickListener {
                if (matches.containsKey(i)) return@setOnClickListener
                selectedCountryIdx = i
                highlightSelection(countryViews, flagViews)
                tryMatch(countryViews, flagViews, matchFlagViews)
            }
        }
        // Set flag images
        for (i in flagViews.indices) {
            flagViews[i].setImageResource(flagOrder[i])
            flagViews[i].setOnClickListener {
                if (matches.values.contains(i)) return@setOnClickListener
                selectedFlagIdx = i
                highlightSelection(countryViews, flagViews)
                tryMatch(countryViews, flagViews, matchFlagViews)
            }
        }
        // Kontrol butonu
        binding.checkButton.setOnClickListener {
            val correct = matches.count { (cIdx, fIdx) ->
                shuffled[cIdx].second == flagOrder[fIdx]
            }
            // Doğru/yanlışları göster
            for ((cIdx, fIdx) in matches) {
                if (shuffled[cIdx].second == flagOrder[fIdx]) {
                    matchFlagViews[cIdx].setBackgroundColor(getColor(R.color.success))
                } else {
                    matchFlagViews[cIdx].setBackgroundColor(getColor(R.color.error))
                }
            }
            binding.scoreText.text = "Doğru eşleşme: $correct/4"
            binding.scoreText.visibility = TextView.VISIBLE
            binding.restartButton.visibility = TextView.VISIBLE
            binding.checkButton.isEnabled = false
        }
        // Yeniden oyna butonu
        binding.restartButton.setOnClickListener {
            setupGame()
        }
    }

    private fun highlightSelection(countryViews: List<TextView>, flagViews: List<ImageView>) {
        for (i in countryViews.indices) {
            countryViews[i].setBackgroundColor(if (selectedCountryIdx == i) getColor(android.R.color.holo_blue_light) else getColor(android.R.color.white))
        }
        for (i in flagViews.indices) {
            flagViews[i].setBackgroundColor(if (selectedFlagIdx == i) getColor(android.R.color.holo_blue_light) else getColor(android.R.color.white))
        }
    }

    private fun tryMatch(
        countryViews: List<TextView>,
        flagViews: List<ImageView>,
        matchFlagViews: List<ImageView>
    ) {
        if (selectedCountryIdx != null && selectedFlagIdx != null) {
            val cIdx = selectedCountryIdx!!
            val fIdx = selectedFlagIdx!!
            matches[cIdx] = fIdx
            matchFlagViews[cIdx].setImageResource(flagOrder[fIdx])
            matchFlagViews[cIdx].visibility = ImageView.VISIBLE
            countryViews[cIdx].alpha = 0.5f
            flagViews[fIdx].alpha = 0.5f
            selectedCountryIdx = null
            selectedFlagIdx = null
            highlightSelection(countryViews, flagViews)
        }
    }
} 