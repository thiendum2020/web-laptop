package com.java.weblaptop.service;

import com.java.weblaptop.dto.PhieuDatDTO;
import com.java.weblaptop.dto.PhieuDatResponseDTO;
import com.java.weblaptop.exception.ResourceNotFoundException;

import java.util.List;

public interface PhieuDatService {
    public List<PhieuDatResponseDTO> retrievePhieuDats();

    public PhieuDatResponseDTO getPhieuDat(Long datId) throws ResourceNotFoundException;

    public PhieuDatDTO savePD(PhieuDatDTO phieuDatDTO) throws ResourceNotFoundException;

    public Boolean deletePD(Long datId) throws ResourceNotFoundException;

    public PhieuDatDTO updatePD(Long datId) throws ResourceNotFoundException;

    public PhieuDatDTO updateStatusPD(Long datId, String status) throws ResourceNotFoundException;
}
