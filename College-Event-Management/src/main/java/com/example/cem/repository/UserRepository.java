package com.example.cem.repository;

import com.example.cem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ Custom method for login
    User findByEmail(String email);
}
