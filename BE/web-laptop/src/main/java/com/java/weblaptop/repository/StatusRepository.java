package com.java.weblaptop.repository;

import com.java.weblaptop.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findAllByStatusName(String status);
}
