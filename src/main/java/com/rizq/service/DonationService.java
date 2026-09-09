package com.rizq.service;

import com.rizq.model.Donation;
import com.rizq.model.DonationStatus;
import com.rizq.model.User;
import com.rizq.repository.DonationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public Donation create(Donation donation, User donor) {
        donation.setDonor(donor);
        donation.setStatus(DonationStatus.AVAILABLE);
        return donationRepository.save(donation);
    }

    public List<Donation> search(String q, String category, String city, DonationStatus status) {
        return donationRepository.search(
                (q == null || q.isBlank()) ? null : q.trim(),
                (category == null || category.isBlank()) ? null : category,
                (city == null || city.isBlank()) ? null : city,
                status);
    }

    public List<Donation> featured() {
        return donationRepository.findTop6ByStatusOrderByCreatedAtDesc(DonationStatus.AVAILABLE);
    }

    public Donation findById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Donation not found: " + id));
    }

    public List<Donation> byDonor(User donor) { return donationRepository.findByDonorOrderByCreatedAtDesc(donor); }

    public List<Donation> byNgo(User ngo) { return donationRepository.findByClaimedByOrderByCreatedAtDesc(ngo); }

    public List<Donation> available() {
        return donationRepository.findByStatusOrderByCreatedAtDesc(DonationStatus.AVAILABLE);
    }

    public List<Donation> findAll() { return donationRepository.findAll(); }

    public void claim(Long id, User ngo) {
        Donation d = findById(id);
        if (d.getStatus() != DonationStatus.AVAILABLE) {
            throw new IllegalStateException("This donation is no longer available.");
        }
        d.setClaimedBy(ngo);
        d.setStatus(DonationStatus.CLAIMED);
        donationRepository.save(d);
    }

    public void updateStatus(Long id, DonationStatus status) {
        Donation d = findById(id);
        d.setStatus(status);
        donationRepository.save(d);
    }

    public void delete(Long id) { donationRepository.deleteById(id); }

    public long countByStatus(DonationStatus status) { return donationRepository.countByStatus(status); }
    public long totalDonations() { return donationRepository.count(); }
    public long totalServings() { return donationRepository.totalServings(); }
}
