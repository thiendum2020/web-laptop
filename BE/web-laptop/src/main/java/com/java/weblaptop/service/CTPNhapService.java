package com.java.weblaptop.service;

import com.java.weblaptop.dto.CTPNhapDTO;
import com.java.weblaptop.entity.CTPNId;
import com.java.weblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CTPNhapService {
    public List<CTPNhapDTO> retrieveCTPNs();

    public Optional<CTPNhapDTO> getCTPN(CTPNId ctpnId) throws ResourceNotFoundException;

    public CTPNhapDTO saveCTPN(CTPNhapDTO nhapDTO) throws ResourceNotFoundException;

    public Boolean deleteCTPN(CTPNId ctpnId) throws ResourceNotFoundException;

    public CTPNhapDTO updateCTPN(CTPNId ctpnId, CTPNhapDTO nhapDTO) throws ResourceNotFoundException;
}
