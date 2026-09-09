package com.rizq.model;

public enum DonationStatus {
    AVAILABLE("Available"),
    CLAIMED("Claimed"),
    PICKED_UP("Picked Up"),
    DELIVERED("Delivered"),
    EXPIRED("Expired");

    private final String label;
    DonationStatus(String label) { this.label = label; }
    public String getLabel() { return label; }
}
