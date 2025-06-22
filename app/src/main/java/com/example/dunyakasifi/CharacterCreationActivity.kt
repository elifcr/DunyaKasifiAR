package com.example.dunyakasifi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dunyakasifi.databinding.ActivityCharacterCreationBinding

class CharacterCreationActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCharacterCreationBinding
    private var selectedGender = 0 // 0: Female, 1: Male
    private var selectedHairColor = 0 // 0: Blonde, 1: Brown
    private var selectedGlasses = 0 // 0: With Glasses, 1: Without Glasses
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupCharacterOptions()
        setupCreateButton()
    }
    
    private fun setupCharacterOptions() {
        binding.genderGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedGender = when (checkedId) {
                R.id.genderFemale -> 0
                R.id.genderMale -> 1
                else -> 0
            }
            updateCharacterPreview()
        }
        binding.hairColorGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedHairColor = when (checkedId) {
                R.id.hairColorBlonde -> 0
                R.id.hairColorBrown -> 1
                else -> 0
            }
            updateCharacterPreview()
        }
        binding.glassesGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedGlasses = when (checkedId) {
                R.id.glassesYes -> 0
                R.id.glassesNo -> 1
                else -> 0
            }
            updateCharacterPreview()
        }
    }
    
    private fun updateCharacterPreview() {
        // Map selections to actual file names
        val resName = when {
            selectedGender == 0 && selectedHairColor == 0 && selectedGlasses == 0 -> "kadin_sari_gozluk"
            selectedGender == 0 && selectedHairColor == 0 && selectedGlasses == 1 -> "kadin_sari_sac"
            selectedGender == 0 && selectedHairColor == 1 && selectedGlasses == 0 -> "kadin_kahverengi_gozluk"
            selectedGender == 0 && selectedHairColor == 1 && selectedGlasses == 1 -> "kadin_kahverengi"
            selectedGender == 1 && selectedHairColor == 0 && selectedGlasses == 0 -> "erkek_sari_gozluk"
            selectedGender == 1 && selectedHairColor == 0 && selectedGlasses == 1 -> "erkek_sari"
            selectedGender == 1 && selectedHairColor == 1 && selectedGlasses == 0 -> "erkek_kahverengi_gozluk"
            selectedGender == 1 && selectedHairColor == 1 && selectedGlasses == 1 -> "erkek_kahverengi"
            else -> "kadin_sari_gozluk" // default
        }
        
        val resId = resources.getIdentifier(resName, "drawable", packageName)
        if (resId != 0) {
            binding.characterPreview.setImageResource(resId)
        } else {
            binding.characterPreview.setImageResource(android.R.color.transparent)
        }
    }
    
    private fun setupCreateButton() {
        binding.createCharacterButton.setOnClickListener {
            if (validateSelections()) {
                saveCharacter()
                Toast.makeText(this, "Karakter başarıyla oluşturuldu!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Lütfen tüm seçenekleri belirle!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun validateSelections(): Boolean {
        return binding.genderGroup.checkedRadioButtonId != -1 &&
               binding.hairColorGroup.checkedRadioButtonId != -1 &&
               binding.glassesGroup.checkedRadioButtonId != -1
    }
    
    private fun saveCharacter() {
        val sharedPrefs = getSharedPreferences("DunyaKasifi", MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putInt("gender", selectedGender)
        editor.putInt("hairColor", selectedHairColor)
        editor.putInt("glasses", selectedGlasses)
        editor.putBoolean("characterCreated", true)
        editor.apply()
    }
} 