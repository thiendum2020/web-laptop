package com.java.weblaptop.repository;

import com.java.weblaptop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);

    Boolean existsByuserName(String username);

    Boolean existsByuserEmail(String email);
}
