
E-Commerce Microservices Platform

🎯 Genel Bakış
Bu proje, modern mikroservis mimarisi prensiplerine uygun olarak geliştirilmiş, yüksek performanslı ve ölçeklenebilir bir e-ticaret platformudur. Her bir mikroservis kendi sorumluluğunu yerine getirerek, bağımsız geliştirme, dağıtım ve ölçeklendirme imkanı sunar.

🛠️ Teknolojiler
Backend

Java - Programlama dili
Spring Boot - Mikroservis framework
Spring Security - Güvenlik ve kimlik doğrulama
Spring Data JPA - Veritabanı işlemleri

Frontend

React.js - Kullanıcı arayüzü
Axios - HTTP istekleri
React Router - Sayfa yönlendirme

Veritabanları

PostgreSQL - İlişkisel veritabanı (User, Product servisleri)
MongoDB - NoSQL veritabanı
Redis - Cache ve sepet yönetimi

Mesajlaşma & Arama

RabbitMQ - Asenkron mesajlaşma sistemi
Elasticsearch - Ürün arama motoru

DevOps & Tools

Docker - Containerization
Docker Compose - Çoklu container yönetimi
Maven - Bağımlılık yönetimi

Güvenlik

JWT (JSON Web Token) - Token tabanlı kimlik doğrulama
BCrypt - Şifre hashleme

🔧 Mikroservisler
1. User Service

JWT token oluşturma ve doğrulama
Kullanıcı kayıt ve kimlik doğrulama (login/register)
Kullanıcı profil yönetimi
Kullanıcı bilgilerini güncelleme
Kullanıcı arama ve listeleme
Token yenileme (refresh token)
Port: 8082
Database: PostgreSQL

2. Product Service

Ürün ekleme, güncelleme ve silme
Ürün listeleme ve detay görüntüleme
Elasticsearch ile hızlı ürün arama
Kategori yönetimi
Port: 8083
Database: MongoDB
Search: Elasticsearch

3. Basket Service

Redis tabanlı yüksek performanslı sepet yönetimi
Sepete ürün ekleme/çıkarma
Sipariş oluşturma
Sepet içeriğini görüntüleme
Port: 8084
Cache: Redis

4. Notification Service

RabbitMQ ile asenkron email gönderimi
Sipariş bildirim sistemi
Kullanıcı bildirimleri
Port: 8085
Message Queue: RabbitMQ
Database: MongoDB

✨ Özellikler

✅ JWT tabanlı güvenli kimlik doğrulama sistemi
✅ Redis ile yüksek performanslı sepet yönetimi
✅ RabbitMQ ile asenkron mesajlaşma
✅ Elasticsearch ile hızlı ve etkili ürün arama
✅ Docker ile containerized altyapı
✅ Mikroservisler arası token validation
✅ Email notification sistemi
✅ Responsive kullanıcı arayüzü
✅ RESTful API mimarisi
✅ Bağımsız ölçeklenebilir servisler

🚀 Kurulum
Gereksinimler

Java 17+
Node.js 16+
Docker & Docker Compose
Maven 3.8+

1. Projeyi Klonlayın
bashgit clone https://github.com/meryemorc/ecommerce-microservices.git
cd ecommerce-microservices
2. Docker Container'ları Başlatın
bashdocker-compose up -d
Bu komut aşağıdaki servisleri başlatacaktır:

Redis (Port: 6379)
Elasticsearch (Port: 9200)
RabbitMQ (Port: 5672, Management UI: 15672)
PostgreSQL (Port: 5432)

3. Backend Servislerini Başlatın
Her bir mikroservis için:
bashcd [service-name]
mvn clean install
mvn spring-boot:run
Servis başlatma sırası:

User Service (8082)
Product Service (8083)
Basket Service (8084)
Notification Service (8085)

4. Frontend'i Başlatın
bashcd frontend
npm install
npm start
```
