package com.example.dunyakasifi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dunyakasifi.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityGameBinding
    private var currentQuestion = 0
    private var correctAnswers = 0
    private var totalQuestions = 5
    private lateinit var questions: List<MathQuestion>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        loadCharacter()
        generateQuestions()
        setupGame()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Matematik Oyunu"
    }
    
    private fun loadCharacter() {
        val sharedPrefs = getSharedPreferences("DunyaKasifi", MODE_PRIVATE)
        val characterCreated = sharedPrefs.getBoolean("characterCreated", false)
        
        if (characterCreated) {
            val gender = sharedPrefs.getInt("gender", 0)
            val hairColor = sharedPrefs.getInt("hairColor", 0)
            val glasses = sharedPrefs.getInt("glasses", 0)
            
            // Map selections to actual file names
            val resName = when {
                gender == 0 && hairColor == 0 && glasses == 0 -> "kadin_sari_gozluk"
                gender == 0 && hairColor == 0 && glasses == 1 -> "kadin_sari_sac"
                gender == 0 && hairColor == 1 && glasses == 0 -> "kadin_kahverengi_gozluk"
                gender == 0 && hairColor == 1 && glasses == 1 -> "kadin_kahverengi"
                gender == 1 && hairColor == 0 && glasses == 0 -> "erkek_sari_gozluk"
                gender == 1 && hairColor == 0 && glasses == 1 -> "erkek_sari"
                gender == 1 && hairColor == 1 && glasses == 0 -> "erkek_kahverengi_gozluk"
                gender == 1 && hairColor == 1 && glasses == 1 -> "erkek_kahverengi"
                else -> "kadin_sari_gozluk" // default
            }
            
            val resId = resources.getIdentifier(resName, "drawable", packageName)
            if (resId != 0) {
                binding.characterImage.setImageResource(resId)
            }
        }
    }
    
    private fun generateQuestions() {
        questions = (1..totalQuestions).map {
            val num1 = Random.nextInt(1, 11)
            val num2 = Random.nextInt(1, 11)
            val operation = Random.nextInt(0, 2) // 0: +, 1: -
            
            val question = when (operation) {
                0 -> "$num1 + $num2 = ?"
                else -> "$num1 - $num2 = ?"
            }
            
            val correctAnswer = when (operation) {
                0 -> num1 + num2
                else -> num1 - num2
            }
            
            val wrongAnswer1 = correctAnswer + Random.nextInt(-3, 4)
            val wrongAnswer2 = correctAnswer + Random.nextInt(-3, 4)
            
            val answers = mutableListOf(correctAnswer, wrongAnswer1, wrongAnswer2)
            answers.shuffle()
            
            MathQuestion(question, answers, answers.indexOf(correctAnswer))
        }
    }
    
    private fun setupGame() {
        showQuestion()
        
        binding.answer1Button.setOnClickListener { checkAnswer(0) }
        binding.answer2Button.setOnClickListener { checkAnswer(1) }
        binding.answer3Button.setOnClickListener { checkAnswer(2) }
    }
    
    private fun showQuestion() {
        if (currentQuestion < questions.size) {
            val question = questions[currentQuestion]
            
            binding.questionText.text = question.question
            binding.answer1Button.text = question.answers[0].toString()
            binding.answer2Button.text = question.answers[1].toString()
            binding.answer3Button.text = question.answers[2].toString()
            
            binding.progressText.text = "Soru ${currentQuestion + 1}/$totalQuestions"
            binding.scoreText.text = "Doğru: $correctAnswers"
        } else {
            showGameResult()
        }
    }
    
    private fun checkAnswer(selectedAnswer: Int) {
        val question = questions[currentQuestion]
        
        if (selectedAnswer == question.correctAnswerIndex) {
            correctAnswers++
            Toast.makeText(this, "Doğru cevap! 🎉", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Yanlış cevap! Doğru cevap: ${question.answers[question.correctAnswerIndex]}", Toast.LENGTH_SHORT).show()
        }
        
        currentQuestion++
        
        if (currentQuestion < questions.size) {
            // Show next question after a short delay
            binding.root.postDelayed({
                showQuestion()
            }, 1000)
        } else {
            showGameResult()
        }
    }
    
    private fun showGameResult() {
        binding.gameContainer.visibility = android.view.View.GONE
        binding.resultContainer.visibility = android.view.View.VISIBLE
        
        val percentage = (correctAnswers * 100) / totalQuestions
        val resultText = "Oyun Bitti!\n\nDoğru Cevap: $correctAnswers/$totalQuestions\nBaşarı Oranı: %$percentage"
        
        val finalResultText = if (percentage >= 80) {
            resultText + "\n\nTebrikler! Çok iyi gittin! 🏆"
        } else if (percentage >= 60) {
            resultText + "\n\nİyi gittin! Biraz daha pratik yap! 👍"
        } else {
            resultText + "\n\nTekrar dene! Sen yapabilirsin! 💪"
        }
        
        binding.resultText.text = finalResultText
        
        if (percentage >= 80) {
            saveAchievement()
        }
        
        binding.playAgainButton.setOnClickListener {
            currentQuestion = 0
            correctAnswers = 0
            generateQuestions()
            binding.gameContainer.visibility = android.view.View.VISIBLE
            binding.resultContainer.visibility = android.view.View.GONE
            showQuestion()
        }
        
        binding.backToMenuButton.setOnClickListener {
            finish()
        }
    }
    
    private fun saveAchievement() {
        val sharedPrefs = getSharedPreferences("DunyaKasifi", MODE_PRIVATE)
        val currentScore = sharedPrefs.getInt("mathGameScore", 0)
        if (correctAnswers > currentScore) {
            sharedPrefs.edit().putInt("mathGameScore", correctAnswers).apply()
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

data class MathQuestion(
    val question: String,
    val answers: List<Int>,
    val correctAnswerIndex: Int
) 