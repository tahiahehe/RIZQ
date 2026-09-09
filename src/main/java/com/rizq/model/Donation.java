package com.rizq.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Food title is required")
    private String title;

    @Column(length = 1000)
    private String description;

    @NotBlank(message = "Category is required")
    private String category;      // Cooked Meal, Bakery, Groceries, Fruits, Packaged

    @Min(value = 1, message = "Servings must be at least 1")
    private int servings;

    @NotBlank(message = "City is required")
    private String city;          // Dhaka, Chattogram, Sylhet ...

    private String area;          // Gulshan, Dhanmondi ...
    private String pickupAddress;
    private String contactPhone;
    private String imageUrl;

    private LocalDateTime pickupBefore;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private DonationStatus status = DonationStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "donor_id")
    private User donor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "claimed_by_id")
    private User claimedBy;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getServings() { return servings; }
    public void setServings(int servings) { this.servings = servings; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public String getPickupAddress() { return pickupAddress; }
    public void setPickupAddress(String pickupAddress) { this.pickupAddress = pickupAddress; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public LocalDateTime getPickupBefore() { return pickupBefore; }
    public void setPickupBefore(LocalDateTime pickupBefore) { this.pickupBefore = pickupBefore; }
    public DonationStatus getStatus() { return status; }
    public void setStatus(DonationStatus status) { this.status = status; }
    public User getDonor() { return donor; }
    public void setDonor(User donor) { this.donor = donor; }
    public User getClaimedBy() { return claimedBy; }
    public void setClaimedBy(User claimedBy) { this.claimedBy = claimedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
