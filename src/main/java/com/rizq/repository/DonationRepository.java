package com.rizq.repository;

import com.rizq.model.Donation;
import com.rizq.model.DonationStatus;
import com.rizq.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findByDonorOrderByCreatedAtDesc(User donor);

    List<Donation> findByClaimedByOrderByCreatedAtDesc(User ngo);

    List<Donation> findByStatusOrderByCreatedAtDesc(DonationStatus status);

    List<Donation> findTop6ByStatusOrderByCreatedAtDesc(DonationStatus status);

    long countByStatus(DonationStatus status);

    @Query("select coalesce(sum(d.servings), 0) from Donation d")
    long totalServings();

    @Query("""
           select d from Donation d
           where (:q is null or lower(d.title) like lower(concat('%', :q, '%'))
                              or lower(d.description) like lower(concat('%', :q, '%')))
             and (:category is null or d.category = :category)
             and (:city is null or d.city = :city)
             and (:status is null or d.status = :status)
           order by d.createdAt desc
           """)
    List<Donation> search(@Param("q") String q,
                          @Param("category") String category,
                          @Param("city") String city,
                          @Param("status") DonationStatus status);
}
