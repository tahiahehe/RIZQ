package com.rizq.service;

import com.rizq.model.Ngo;
import com.rizq.repository.NgoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NgoService {

    private final NgoRepository ngoRepository;

    public NgoService(NgoRepository ngoRepository) { this.ngoRepository = ngoRepository; }

    public List<Ngo> findAll() { return ngoRepository.findAll(); }

    public Ngo findById(Long id) {
        return ngoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("NGO not found: " + id));
    }

    public Ngo save(Ngo ngo) { return ngoRepository.save(ngo); }

    public void delete(Long id) { ngoRepository.deleteById(id); }

    public long count() { return ngoRepository.count(); }
}
