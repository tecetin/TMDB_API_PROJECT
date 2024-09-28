#TMDB API Test Otomasyon Projesi
Genel Bakış
Bu proje, The Movie Database (TMDB) uygulaması için API ve veritabanı işlemlerini kullanarak temel kullanıcı etkileşimlerini otomatikleştiren kapsamlı bir test otomasyon framework'üdür. Framework, kullanıcı girişi otomasyonu, kullanıcı bilgilerini çekme ve favori film ve dizi yönetimi gibi özellikleri içermektedir. Test otomasyonu, Java, TestNG, Postman ve PostgreSQL gibi çeşitli teknolojiler kullanılarak geliştirilmiştir.

Temel Özellikler
API ve JDBC Entegrasyonu: Proje, TMDB API’sini kullanarak platformla etkileşimde bulunur ve PostgreSQL veritabanı ile favori film ve dizileri yönetir.

Kullanıcı Girişi Otomasyonu: API istekleri aracılığıyla kullanıcı giriş sürecini otomatikleştirir ve kullanıcı kimlik bilgilerini API yanıtları ile doğrular.

API Tabanlı Kullanıcı Bilgileri Çekme: TMDB API'si üzerinden kullanıcı bilgilerini çeker ve doğru şekilde getirildiğini doğrular.

Favori Film ve Dizi Yönetimi:

Kullanıcının favori film ve dizilerini saklamak ve yönetmek için bir PostgreSQL veritabanı oluşturulur.
Sistem, API çağrıları aracılığıyla yeni favori girişleri ekleyebilir ve bunları yerel veritabanı ile senkronize edebilir.
Veritabanı ve API Senkronizasyonu: PostgreSQL veritabanında saklanan kullanıcının favori film ve dizilerini, API üzerinden gelen verilerle karşılaştırarak tutarlılığı sağlar.

Kullanılan Teknolojiler
Java: Otomasyon mantığını oluşturmak için kullanılan ana programlama dili.
TestNG: Test senaryolarını düzenlemek ve çalıştırmak için kullanılan test framework'ü.
Postman: API isteklerini göndermek ve yanıtları doğrulamak için kullanılan API testi ve geliştirme aracı.
PostgreSQL: Kullanıcı ve favori içerik bilgilerini saklamak için kullanılan veritabanı yönetim sistemi.
TMDB API: Kullanıcı bilgilerini çekmek ve favori film ve dizileri yönetmek için kullanılan API.
Kurulum
API Anahtarı ve Bearer Token:
TMDB platformunda bir hesap oluşturarak bir API anahtarı ve bearer token elde etmelisiniz.
TMDB API ile etkileşimde bulunurken bu anahtar ve token'ı kimlik doğrulama için kullanın.
Veritabanı Kurulumu:
Bir PostgreSQL veritabanı oluşturun ve bağlantı bilgilerini projeye yapılandırın.
Kullanıcı bilgilerini ve favori film/dizileri saklamak için tablolar oluşturun.
Nasıl Çalışır
Kullanıcı Kimlik Doğrulama:
Sistem, elde edilen bearer token ile bir istek göndererek TMDB API'si aracılığıyla kullanıcıları doğrular.
Kullanıcı Verilerinin Çekilmesi:
Kimlik doğrulamasının ardından sistem, kullanıcıya özgü verileri (hesap bilgileri, mevcut favori filmler) çeker.
Favori Yönetimi:
Kullanıcılar, favorilerine film ve dizi ekleyebilir, bu içerikler hem PostgreSQL veritabanında hem de API'de favori listesine eklenir.
Senkronizasyon:
Framework, PostgreSQL veritabanında saklanan favori filmleri ve dizileri TMDB API'deki verilerle karşılaştırır ve tutarlılığı sağlar.

Yapılandırma Ayarları
Programın doğru şekilde çalıştırılabilmesi için aşağıdaki adımları takip edin:

Kullanıcı Bilgileri:
configuration.properties dosyasına geçerli bir userName ve password ekleyin.
API Ayarları:
TestBase sınıfında bulunan token ve accountId değerlerini TMDB sitesinden alarak ilgili alanlara ekleyin.
Bu adımlar tamamlandıktan sonra, test senaryoları sorunsuz bir şekilde çalıştırılabilir.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

TMDB API Test Automation Project
Overview
This project is a comprehensive test automation framework designed for The Movie Database (TMDB) application. It leverages APIs and database operations to automate key user interactions with the TMDB platform. The framework includes features such as user login automation, fetching user details, and managing favorite movies and TV shows. The test automation uses various technologies, including Java, TestNG, Postman, and PostgreSQL.

Key Features
API and JDBC Integration: The project utilizes TMDB's API to interact with the platform, while PostgreSQL is used to manage and store favorite movies and TV shows.

User Login Automation: Automates the user login process through API requests and verifies user credentials using API responses.

API-Based User Information Retrieval: Fetches user details from the TMDB API and ensures that user-specific information is correctly retrieved and validated.

Favorite Movies and TV Shows Management:

A PostgreSQL database is created to store and manage the user's favorite movies and TV shows.
The system can add new favorite entries through API calls and synchronize them with the local database.
Database and API Synchronization: Compares the user's favorite movies and TV shows stored in the PostgreSQL database with the API data to ensure consistency.

Technologies Used
Java: Core programming language used for the automation logic.
TestNG: Testing framework for organizing and executing test cases.
Postman: API testing and development tool for sending API requests and verifying responses.
PostgreSQL: Database management system used to store user and favorite content details.
TMDB API: Used for fetching user details and managing favorite movies and TV shows.
Setup
API Key & Bearer Token:
You need to create an account on the TMDB platform to obtain an API key and bearer token.
Use this key and token for authentication when interacting with the TMDB API.
Database Setup:
Set up a PostgreSQL database and configure the connection in the project.
Create tables for storing user information and favorite movies/TV shows.
How It Works
User Authentication:
The system authenticates users using TMDB's API by sending a request with the obtained bearer token.
Fetching User Data:
After authentication, the system retrieves user-specific data such as account details and existing favorite movies.
Favorite Management:
Users can add movies and TV shows to their favorites, which are then stored in both the PostgreSQL database and TMDB’s favorite list via API calls.
Synchronization:
The framework compares the favorite movies and TV shows stored in the PostgreSQL database with those listed in the TMDB API to ensure that the data is accurate and consistent.

Configuration Settings
To ensure the program runs correctly, follow these steps:

User Credentials:

Update the configuration.properties file with valid userName and password values.
API Settings:

In the TestBase class, update the token and accountId values, which need to be retrieved from the TMDB site.
Once these steps are completed, the test scenarios can be executed smoothly.
