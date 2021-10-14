package com.java.weblaptop.controller;

import com.java.weblaptop.dto.*;
import com.java.weblaptop.entity.CTPDHId;
import com.java.weblaptop.exception.*;
import com.java.weblaptop.service.CTPDatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ctpdats")
public class CTPDatController {
    @Autowired
    private CTPDatService ctpDatService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAll() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();
        try {
            List<CTPDatDTO> phieuDatDTOS = ctpDatService.retrieveCTPDs();
            List list = Collections.synchronizedList(new ArrayList(phieuDatDTOS));

            if (responseDTO.addAll(list) == true) {
                response.setData(phieuDatDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_CT_PHIEU_DAT_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_CT_PHIEU_DAT_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ctpdat/{datId}/{productId}")
    public ResponseEntity<ResponseDTO> findCTPD(@PathVariable("datId") Long datId, @PathVariable("productId") Long productId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CTPDHId ctpdhId = new CTPDHId(productId, datId);
            Optional<CTPDatDTO> phieuDatDTO = ctpDatService.getCTPD(ctpdhId);

            responseDTO.setData(phieuDatDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_CT_PHIEU_DAT_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(e.getMessage()+"===="+ErrorCode.FIND_CT_PHIEU_DAT_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/ctpdat")
    public ResponseEntity<ResponseDTO> createCTPD(@Valid @RequestBody CTPDatDTO phieuDatDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CTPDatDTO dto = ctpDatService.saveCTPD(phieuDatDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_CT_PHIEU_DAT_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(e.getMessage()+"===="+ErrorCode.ADD_CT_PHIEU_DAT_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
    //
////    //update
    @PutMapping("/ctpdat/{datId}/{productId}")
    public ResponseEntity<ResponseDTO> updatePD(@PathVariable("datId") Long datId, @PathVariable("productId") Long productId,
                                                @Valid @RequestBody CTPDatDTO phieuDatDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CTPDHId ctpdhId = new CTPDHId(productId, datId);
            CTPDatDTO updatePD = ctpDatService.updateCTPD(ctpdhId, phieuDatDTO);

            responseDTO.setData(updatePD);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_CT_PHIEU_DAT_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_CT_PHIEU_DAT_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    ////    //delete
    @DeleteMapping("/ctpdat/{datId}/{productId}")
    public ResponseEntity<ResponseDTO> deletePD(@PathVariable("datId") Long datId, @PathVariable("productId") Long productId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CTPDHId ctpdhId = new CTPDHId(productId, datId);
            Boolean isDel = ctpDatService.deleteCTPD(ctpdhId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_CT_PHIEU_DAT_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_CT_PHIEU_DAT_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/dat/{datId}")
    public ResponseEntity<ResponseDTO> findDetailByOrder(@PathVariable("datId") @NotBlank Long datId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        List<CTPDatResponseDTO> detailDTOS = ctpDatService.findCTByPD(datId);
        responseDTO.setData(detailDTOS);
        responseDTO.setSuccessCode(SuccessCode.FIND_CT_PHIEU_DAT_SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }
}
