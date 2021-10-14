package com.java.weblaptop.service;

import com.java.weblaptop.dto.PhieuNhapDTO;
import com.java.weblaptop.dto.PhieuNhapResponseDTO;
import com.java.weblaptop.exception.ResourceNotFoundException;

import java.util.List;

public interface PhieuNhapService {
    public List<PhieuNhapResponseDTO> retrievePhieuNhaps();

    public PhieuNhapResponseDTO getPhieuNhap(Long nhapId) throws ResourceNotFoundException;

    public PhieuNhapDTO savePN(PhieuNhapDTO nhapDTO) throws ResourceNotFoundException;

    public Boolean deletePN(Long nhapId) throws ResourceNotFoundException;

    public PhieuNhapDTO updatePN(Long nhapId) throws ResourceNotFoundException;

    public PhieuNhapDTO updateStatusPN(Long nhapId, String status) throws ResourceNotFoundException;
}
