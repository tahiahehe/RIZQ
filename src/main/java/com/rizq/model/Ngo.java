package com.rizq.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ngos")
public class Ngo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    private String city;
    private String focusArea;
    private String phone;
    private String email;
    private String logoUrl;
    private boolean verified = true;
    private int mealsDistributed;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getFocusArea() { return focusArea; }
    public void setFocusArea(String focusArea) { this.focusArea = focusArea; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
    public int getMealsDistributed() { return mealsDistributed; }
    public void setMealsDistributed(int mealsDistributed) { this.mealsDistributed = mealsDistributed; }
}
