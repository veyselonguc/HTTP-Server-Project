# HTTP-Server-Project
 Bu proje, temel düzeyde bir HTTP sunucusunun Java kullanılarak nasıl geliştirileceğini göstermeyi amaçlamaktadır. TCP soketleri ile çalışan bu sunucu, HTTP isteklerini manuel olarak işler ve dosya sistemi üzerinden dinamik içerik sunabilir.

## 🚀 Özellikler
✅ Temel HTTP metod desteği: GET, POST, PUT, DELETE
✅ Statik dosya sunumu (.html, .css, .js, .json, .png, .jpg)
✅ Dizin içeriğini HTML olarak listeleme
✅ Basit MIME tipi çözümleyici
✅ Dosya yükleme (POST) ve güncelleme (PUT) desteği
✅ Sıfır bağımlılıkla saf Java kullanımı

## 📁 Proje Yapısı
```bash
.
├── MainHttpServer.java       # Ana sunucu sınıfı (ServerSocket ile bağlantıları yönetir)
├── ClientHandler.java        # Her bir istemciyi ayrı thread ile yöneten sınıf
├── HttpResponse.java         # HTTP yanıtlarını oluşturan yardımcı sınıf
├── FileHandler.java          # GET, POST, PUT, DELETE işlemlerini yöneten sınıf
└── ContentTypeResolver.java  # MIME tiplerini belirleyen yardımcı sınıf
```

## ⚙️ Kurulum ve Çalıştırma

### Gereksinimler
  -Java JDK 8+
  -Terminal veya Java destekli bir IDE (IntelliJ, Eclipse, VS Code)

### Adımlar
  1- Proje dosyalarını aynı klasöre yerleştirin.
  2- Aşağıdaki komutları terminal üzerinden çalıştırın:
```bash
javac *.java
java MainHttpServer
```
  3- Sunucu varsayılan olarak localhost:8080 adresinde dinlemeye başlar.

## 🌐 Kullanım

### 📄 GET İsteği
```bash
curl http://localhost:8080/index.html
```

### 📤 POST (Yeni Dosya Yükleme)
```bash
curl -X POST -H "Content-Length: 20" --data "İçerik örneği" http://localhost:8080/yeni.txt
```

### ✏️ PUT (Dosya Güncelleme)
```bash
curl -X PUT -H "Content-Length: 15" --data "Güncel veri" http://localhost:8080/yeni.txt
```

### ❌ DELETE (Dosya Silme)
```bash
curl -X DELETE http://localhost:8080/yeni.txt
```

## 🛠️ Geliştirme Notları

-Statik içerikler ./src/www/ dizininden sunulur.
-MIME tipi, ContentTypeResolver sınıfı tarafından belirlenir.
-HTTP istekleri manuel olarak çözümlenir, harici bir framework kullanılmaz.
-Çoklu istemciler için her bağlantı ClientHandler üzerinden bir thread olarak çalışır.
