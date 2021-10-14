package com.java.weblaptop.repository;

import com.java.weblaptop.entity.CTPNId;
import com.java.weblaptop.entity.CTPNhap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CTPNhapRepository extends JpaRepository<CTPNhap, CTPNId> {
    List<CTPNhap> findAllByCtpnIdNhapId(long nhapId);
    CTPNhap findByCtpnIdNhapId(long nhapId);
}
