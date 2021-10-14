package com.java.weblaptop.repository;

import com.java.weblaptop.entity.CTPDHId;
import com.java.weblaptop.entity.CTPDat;
import com.java.weblaptop.entity.PhieuDat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CTPDatRepository extends JpaRepository<CTPDat, CTPDHId> {
//    CTPDat findAllByDatIdAndProductId(long datId, long productId);
    List<CTPDat> findAllByCtpdhIdDatId(long ctpdhId_datId);
    CTPDat findByCtpdhIdDatId(long datId);
    List<CTPDat> findCTPDatByPhieuDat(PhieuDat phieuDat);
}
