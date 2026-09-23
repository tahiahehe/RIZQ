# Rizq - BACKEND 
This is the server code: controllers, models, repositories, services, security, MySQL config.
The HTML pages/CSS/images are in rizq-frontend.zip.

HOW TO RUN THE FULL WEBSITE:
1. Unzip both zips.
2. Copy everything inside rizq-frontend/resources/ into rizq-backend/src/main/resources/
   (the backend project then has src/main/java + src/main/resources together).
3. In phpMyAdmin (XAMPP): create database "rizq_db" (utf8mb4_unicode_ci).
4. Set your MySQL password in src/main/resources/application.properties (XAMPP default: user root, empty password).
5. Open the rizq-backend folder in IntelliJ, let Maven load, run RizqApplication.java.
6. Open http://localhost:8080
   Logins: admin@rizq.com/admin123 | ngo@rizq.com/ngo123 | donor@rizq.com/donor123
