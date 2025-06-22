package com.example.dunyakasifi

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dunyakasifi.databinding.ActivityMainBinding
import com.unity3d.player.UnityPlayerGameActivity

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                launchUnityActivity()
            } else {
                Toast.makeText(this, "AR özelliği için kamera izni gerekli.", Toast.LENGTH_LONG).show()
            }
        }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupButtons()
        checkCharacterCreation()
    }
    
    private fun setupButtons() {
        binding.startGameButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
        
        binding.characterCreationButton.setOnClickListener {
            startActivity(Intent(this, CharacterCreationActivity::class.java))
        }
        
        binding.countrySelectionButton.setOnClickListener {
            startActivity(Intent(this, CountrySelectionActivity::class.java))
        }
        
        binding.arExplorationButton.setOnClickListener {
            checkCameraPermissionAndLaunchUnity()
        }
        
        binding.passportButton.setOnClickListener {
            startActivity(Intent(this, PassportActivity::class.java))
        }
        
        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        
        binding.flagMatchButton.setOnClickListener {
            startActivity(Intent(this, FlagMatchActivity::class.java))
        }
        
        binding.mapQuizButton.setOnClickListener {
            startActivity(Intent(this, MapQuizActivity::class.java))
        }
    }
    
    private fun checkCameraPermissionAndLaunchUnity() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                launchUnityActivity()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // TODO: Kullanıcıya neden izne ihtiyacınız olduğunu açıklayan bir diyalog gösterebilirsiniz.
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
    
    private fun launchUnityActivity() {
        startActivity(Intent(this, UnityPlayerGameActivity::class.java))
    }
    
    private fun checkCharacterCreation() {
        val sharedPrefs = getSharedPreferences("DunyaKasifi", MODE_PRIVATE)
        val characterCreated = sharedPrefs.getBoolean("characterCreated", false)
        
        if (!characterCreated) {
            // Eğer karakter oluşturulmamışsa, karakter oluşturma ekranına yönlendir
            startActivity(Intent(this, CharacterCreationActivity::class.java))
            finish()
        }
    }
} 