package com.example.dunyakasifi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dunyakasifi.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySettingsBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        loadSettings()
        setupClickListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Ayarlar"
    }
    
    private fun loadSettings() {
        val sharedPrefs = getSharedPreferences("DunyaKasifi", MODE_PRIVATE)
        
        binding.soundSwitch.isChecked = sharedPrefs.getBoolean("soundEnabled", true)
        binding.musicSwitch.isChecked = sharedPrefs.getBoolean("musicEnabled", true)
        binding.notificationsSwitch.isChecked = sharedPrefs.getBoolean("notificationsEnabled", true)
        binding.eyeHealthSwitch.isChecked = sharedPrefs.getBoolean("eyeHealthEnabled", true)
        
        val timeLimit = sharedPrefs.getInt("timeLimit", 30)
        binding.timeLimitText.text = "$timeLimit dakika"
    }
    
    private fun setupClickListeners() {
        binding.soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveSetting("soundEnabled", isChecked)
            if (isChecked) {
                Toast.makeText(this, "Ses efektleri açıldı", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Ses efektleri kapatıldı", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveSetting("musicEnabled", isChecked)
            if (isChecked) {
                Toast.makeText(this, "Arka plan müziği açıldı", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Arka plan müziği kapatıldı", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveSetting("notificationsEnabled", isChecked)
            if (isChecked) {
                Toast.makeText(this, "Bildirimler açıldı", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Bildirimler kapatıldı", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.eyeHealthSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveSetting("eyeHealthEnabled", isChecked)
            if (isChecked) {
                Toast.makeText(this, "Göz sağlığı uyarıları açıldı", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Göz sağlığı uyarıları kapatıldı", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.timeLimitButton.setOnClickListener {
            showTimeLimitDialog()
        }
        
        binding.resetProgressButton.setOnClickListener {
            showResetDialog()
        }
        
        binding.aboutButton.setOnClickListener {
            showAboutDialog()
        }
    }
    
    private fun saveSetting(key: String, value: Boolean) {
        val sharedPrefs = getSharedPreferences("DunyaKasifi", MODE_PRIVATE)
        sharedPrefs.edit().putBoolean(key, value).apply()
    }
    
    private fun showTimeLimitDialog() {
        val options = arrayOf("15 dakika", "30 dakika", "45 dakika", "60 dakika")
        val currentLimit = getSharedPreferences("DunyaKasifi", MODE_PRIVATE).getInt("timeLimit", 30)
        val currentIndex = when (currentLimit) {
            15 -> 0
            30 -> 1
            45 -> 2
            60 -> 3
            else -> 1
        }
        
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Zaman Limiti")
            .setSingleChoiceItems(options, currentIndex) { dialog, which ->
                val newLimit = when (which) {
                    0 -> 15
                    1 -> 30
                    2 -> 45
                    3 -> 60
                    else -> 30
                }
                getSharedPreferences("DunyaKasifi", MODE_PRIVATE)
                    .edit()
                    .putInt("timeLimit", newLimit)
                    .apply()
                binding.timeLimitText.text = "$newLimit dakika"
                dialog.dismiss()
                Toast.makeText(this, "Zaman limiti $newLimit dakika olarak ayarlandı", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("İptal") { dialog, _ -> dialog.dismiss() }
            .show()
    }
    
    private fun showResetDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("İlerlemeyi Sıfırla")
            .setMessage("Tüm ilerlemeniz silinecek. Bu işlem geri alınamaz. Devam etmek istiyor musunuz?")
            .setPositiveButton("Evet, Sıfırla") { _, _ ->
                resetProgress()
                Toast.makeText(this, "İlerleme sıfırlandı", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("İptal") { dialog, _ -> dialog.dismiss() }
            .show()
    }
    
    private fun resetProgress() {
        val sharedPrefs = getSharedPreferences("DunyaKasifi", MODE_PRIVATE)
        sharedPrefs.edit()
            .remove("visitedCountries")
            .remove("mathGameScore")
            .remove("characterCreated")
            .apply()
    }
    
    private fun showAboutDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Dünya Kaşifi Hakkında")
            .setMessage("Dünya Kaşifi v1.0\n\n" +
                    "Bu uygulama çocukların dünya ülkelerini keşfetmesi ve öğrenmesi için tasarlanmıştır.\n\n" +
                    "Özellikler:\n" +
                    "• 6 farklı ülke keşfi\n" +
                    "• Eğitici mini oyunlar\n" +
                    "• Pasaport sistemi\n" +
                    "• Göz sağlığı uyarıları\n" +
                    "• Ebeveyn kontrolü\n\n" +
                    "Güvenli ve eğitici bir deneyim için tasarlanmıştır.")
            .setPositiveButton("Tamam") { dialog, _ -> dialog.dismiss() }
            .show()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
} 