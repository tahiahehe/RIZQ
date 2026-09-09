package com.rizq.repository;

import com.rizq.model.Ngo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NgoRepository extends JpaRepository<Ngo, Long> {
    List<Ngo> findByCity(String city);
}
