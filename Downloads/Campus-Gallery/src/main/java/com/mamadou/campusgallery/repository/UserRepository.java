package com.mamadou.campusgallery.repository;

import com.mamadou.campusgallery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
