package com.example.dunyakasifi

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dunyakasifi.databinding.ActivityMapQuizBinding

class MapQuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapQuizBinding
    private val countries = listOf(
        Triple("Türkiye", R.drawable.flag_turkey, "Ankara"),
        Triple("Fransa", R.drawable.flag_france, "Paris"),
        Triple("Japonya", R.drawable.flag_japan, "Tokyo"),
        Triple("Brezilya", R.drawable.flag_brazil, "Brasilia")
    )
    private var currentQuestion = 0
    private var correctAnswers = 0
    private lateinit var questionOrder: List<Triple<String, Int, String>>
    private lateinit var optionOrder: List<List<Triple<String, Int, String>>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupGame()
    }

    private fun setupGame() {
        currentQuestion = 0
        correctAnswers = 0
        questionOrder = countries.shuffled().take(5)
        optionOrder = questionOrder.map { q ->
            (countries - q).shuffled().take(3).plus(q).shuffled()
        }
        binding.scoreText.visibility = TextView.GONE
        binding.restartButton.visibility = TextView.GONE
        showQuestion()
    }

    private fun showQuestion() {
        if (currentQuestion >= questionOrder.size) {
            binding.questionText.text = "Oyun Bitti!\nDoğru: $correctAnswers/${questionOrder.size}"
            binding.scoreText.text = "Skor: $correctAnswers / ${questionOrder.size}"
            binding.scoreText.visibility = TextView.VISIBLE
            binding.restartButton.visibility = TextView.VISIBLE
            setOptionsEnabled(false)
            return
        }
        val q = questionOrder[currentQuestion]
        val options = optionOrder[currentQuestion]
        binding.questionText.text = "Hangi ülke?\n${q.first}"
        val flagViews = listOf(binding.option1, binding.option2, binding.option3, binding.option4)
        for (i in flagViews.indices) {
            flagViews[i].setImageResource(options[i].second)
            flagViews[i].setOnClickListener {
                if (options[i].first == q.first) {
                    correctAnswers++
                    Toast.makeText(this, "Doğru!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Yanlış! Doğru cevap: ${q.first}", Toast.LENGTH_SHORT).show()
                }
                currentQuestion++
                showQuestion()
            }
        }
        setOptionsEnabled(true)
    }

    private fun setOptionsEnabled(enabled: Boolean) {
        val flagViews = listOf(binding.option1, binding.option2, binding.option3, binding.option4)
        for (v in flagViews) v.isEnabled = enabled
    }

    override fun onResume() {
        super.onResume()
        binding.restartButton.setOnClickListener {
            setupGame()
        }
    }
} 