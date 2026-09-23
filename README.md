# Rizq — Share Food. Spread Hope. (Spring Boot + Thymeleaf + MySQL)

Complete website: home, browse, donate, NGO directory + profile, about, contact,
login/register, and Donor / NGO / Admin dashboards.

## What is connected to what

- **HTML → CSS**: every page uses `templates/fragments/layout.html`, which loads
  `src/main/resources/static/css/style.css` via `th:href="@{/css/style.css}"`
  (Spring serves `/static` automatically) plus the Poppins Google Font.
- **HTML → Java**: controllers in `com.rizq.controller` return template names and
  put data in the `Model`; Thymeleaf renders it (`th:each`, `th:text`, `th:object`).
- **Java → MySQL**: entities (`User`, `Donation`, `Ngo`) are JPA `@Entity` classes;
  Spring Data JPA repositories talk to MySQL using the settings in
  `src/main/resources/application.properties`. Tables are created automatically
  (`spring.jpa.hibernate.ddl-auto=update`).
- **Login**: Spring Security form login (`SecurityConfig`) + BCrypt, with
  `RoleRedirectHandler` sending each role to its dashboard.
- **Demo data**: `config/DataSeeder.java` inserts demo users, NGOs and donations
  the first time the app starts.

## Step by step

1. **Install** JDK 17+ (or 21), MySQL 8, and IntelliJ IDEA.
2. **Unzip** `rizq-java.zip` somewhere, e.g. `C:\projects\rizq-java`.
3. **Create the database** — in MySQL Workbench or terminal:
   ```sql
   CREATE DATABASE rizq_db CHARACTER SET utf8mb4;
   ```
4. **Open in IntelliJ**: File → Open → select the folder that contains `pom.xml`
   → Open as Project → Trust Project. Wait for Maven to download dependencies
   (bottom-right progress bar).
5. **Set your MySQL password** in `src/main/resources/application.properties`:
   ```
   spring.datasource.username=root
   spring.datasource.password=YOUR_PASSWORD
   ```
6. **Check the SDK**: File → Project Structure → Project → SDK = 17 or newer.
7. **Run**: open `src/main/java/com/rizq/RizqApplication.java` and click the green
   ▶ next to the class name (or Maven panel → `spring-boot:run`).
8. **Open** http://localhost:8080 in your browser.

## Demo logins

| Role  | Email            | Password  |
|-------|------------------|-----------|
| Admin | admin@rizq.com   | admin123  |
| NGO   | ngo@rizq.com     | ngo123    |
| Donor | donor@rizq.com   | donor123  |

Register a new account at `/register` (Donor or NGO — admin is assigned only in DB).

## Troubleshooting

- **`Access denied for user 'root'`** → wrong password in `application.properties`.
- **`Communications link failure`** → MySQL service isn't running.
- **Port 8080 in use** → change `server.port=8081`.
- **Page looks unstyled** → hard refresh (Ctrl+F5); `spring.thymeleaf.cache=false`
  is already set so template/CSS edits show after a refresh.
- **Maven not resolving** → right-click `pom.xml` → Maven → Reload Project.

## Structure

```
src/main/java/com/rizq/
  RizqApplication.java      app entry point
  config/                   SecurityConfig, RoleRedirectHandler, DataSeeder
  controller/               Home, Auth, Donor, Ngo, Admin
  model/                    User, Donation, Ngo, Role, DonationStatus
  repository/               Spring Data JPA interfaces
  service/                  business logic + UserDetailsService
src/main/resources/
  application.properties    MySQL + JPA config
  static/css/style.css      full sky-blue (#38BDF8) theme
  templates/                all Thymeleaf pages (+ fragments/layout.html)
```

## Photos

All photos now ship inside the project at `src/main/resources/static/images/` and are served from `/images/...`, so they work with no internet connection.

If you already ran the app once, the old photo links are stored in MySQL. In phpMyAdmin drop the `rizq_db` database, create it again (`utf8mb4_unicode_ci`), and run the app so the demo data reloads with the local photos.
