# Rizq — Share Food. Spread Hope

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





