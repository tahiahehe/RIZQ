package com.rizq.service;

import com.rizq.model.Role;
import com.rizq.model.User;
import com.rizq.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /** Registers a donor or NGO. ADMIN can never be self-assigned. */
    public User register(User user, Role requestedRole) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }
        Role safeRole = (requestedRole == Role.NGO) ? Role.NGO : Role.DONOR;
        user.setRole(safeRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
    }

    public List<User> findAll() { return userRepository.findAll(); }

    public long countByRole(Role role) { return userRepository.countByRole(role); }

    public void setEnabled(Long id, boolean enabled) {
        User u = userRepository.findById(id).orElseThrow();
        u.setEnabled(enabled);
        userRepository.save(u);
    }

    public void setVerified(Long id, boolean verified) {
        User u = userRepository.findById(id).orElseThrow();
        u.setVerified(verified);
        userRepository.save(u);
    }

    /** Admin-only role change. */
    public void changeRole(Long id, Role role) {
        User u = userRepository.findById(id).orElseThrow();
        u.setRole(role);
        userRepository.save(u);
    }
}
