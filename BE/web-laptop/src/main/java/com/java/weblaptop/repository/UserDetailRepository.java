package com.java.weblaptop.repository;

import com.java.weblaptop.entity.User;
import com.java.weblaptop.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    List<UserDetail> getUserDetailByUser(User user);
}
