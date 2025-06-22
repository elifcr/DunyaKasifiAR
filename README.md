# Dünya Kaşifi - Artırılmış Gerçeklik Temelli Mobil Uygulama

Bu proje, çocuklar için eğitici ve eğlenceli bir dünya keşif uygulamasıdır. Artırılmış gerçeklik (AR) teknolojisi kullanarak çocukların farklı ülkeleri keşfetmesini sağlar.

## Özellikler

### 🎮 Ana Özellikler
- **Karakter Oluşturma**: Çocuklar kendi karakterlerini özelleştirebilir
- **Ülke Keşfi**: 6 farklı ülke (Türkiye, Fransa, Japonya, Mısır, Brezilya, Avustralya)
- **Mini Oyunlar**: Matematik yarışması ve diğer eğitici oyunlar
- **AR Deneyimi**: Artırılmış gerçeklik ile ülke simgelerini görüntüleme
- **Pasaport Sistemi**: Ziyaret edilen ülkeler ve kazanılan rozetler

### 🛡️ Güvenlik ve Sağlık
- **Göz Sağlığı Uyarıları**: Düzenli mola hatırlatıcıları
- **Ebeveyn Kontrolü**: Zaman sınırları ve içerik kontrolü
- **Güvenli İçerik**: Yaşa uygun eğitici materyaller

## Teknik Detaylar

### 🛠️ Teknolojiler
- **Dil**: Kotlin
- **Platform**: Android (API 24+)
- **AR**: Google ARCore + Sceneform
- **UI**: Material Design Components
- **Mimari**: MVVM pattern

### 📱 Ekranlar
1. **Ana Menü**: Uygulama giriş noktası
2. **Karakter Oluşturma**: Avatar özelleştirme
3. **Ülke Seçimi**: Keşfedilecek ülkelerin listesi
4. **Ülke Detayı**: Ülke bilgileri ve aktiviteler
5. **Mini Oyunlar**: Eğitici oyunlar
6. **AR Deneyimi**: Artırılmış gerçeklik
7. **Pasaport**: İlerleme ve başarılar
8. **Ayarlar**: Uygulama ayarları

## Kurulum

### Gereksinimler
- Android Studio Arctic Fox veya üzeri
- Android SDK 24+
- ARCore destekli Android cihaz
- Google Play Services

### Adımlar
1. Projeyi klonlayın
2. Android Studio'da açın
3. Gradle sync işlemini tamamlayın
4. ARCore destekli bir cihaza yükleyin

## Kullanım

### İlk Kurulum
1. Uygulamayı açın
2. Karakterinizi oluşturun
3. Ana menüden "Macera Başlasın" seçin
4. Bir ülke seçin

### Oyun Akışı
1. Ülke seçimi
2. Ülke hakkında bilgi öğrenme
3. Mini oyun oynama
4. AR deneyimi yaşama
5. Pasaporta damga alma

## Geliştirme

### Proje Yapısı
```
app/
├── src/main/
│   ├── java/com/example/dunyakasifi/
│   │   ├── MainActivity.kt
│   │   ├── CharacterCreationActivity.kt
│   │   ├── CountrySelectionActivity.kt
│   │   ├── CountryDetailActivity.kt
│   │   ├── GameActivity.kt
│   │   └── ARActivity.kt
│   ├── res/
│   │   ├── layout/
│   │   ├── values/
│   │   └── drawable/
│   └── AndroidManifest.xml
```

### Katkıda Bulunma
1. Fork yapın
2. Feature branch oluşturun
3. Değişikliklerinizi commit edin
4. Pull request gönderin

## Lisans

Bu proje MIT lisansı altında lisanslanmıştır.

## İletişim

Proje hakkında sorularınız için issue açabilirsiniz.

---

**Not**: Bu uygulama eğitim amaçlı geliştirilmiştir ve çocukların güvenliği ön planda tutulmuştur. 