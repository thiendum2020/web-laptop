package com.java.weblaptop.service;

import com.java.weblaptop.dto.CTPDatDTO;
import com.java.weblaptop.dto.CTPDatResponseDTO;
import com.java.weblaptop.entity.CTPDHId;
import com.java.weblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CTPDatService {
    public List<CTPDatDTO> retrieveCTPDs();

    public Optional<CTPDatDTO> getCTPD(CTPDHId ctpdhId) throws ResourceNotFoundException;

    public CTPDatDTO saveCTPD(CTPDatDTO ctpDatDTO) throws ResourceNotFoundException;

    public Boolean deleteCTPD(CTPDHId ctpdhId) throws ResourceNotFoundException;

    public CTPDatDTO updateCTPD(CTPDHId ctpdhId, CTPDatDTO ctpDatDTO) throws ResourceNotFoundException;

    public List<CTPDatResponseDTO> findCTByPD(long datId) throws ResourceNotFoundException;
}
