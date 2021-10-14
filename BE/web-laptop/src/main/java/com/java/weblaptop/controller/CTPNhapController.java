package com.java.weblaptop.controller;

import com.java.weblaptop.dto.*;
import com.java.weblaptop.entity.CTPNId;
import com.java.weblaptop.exception.*;
import com.java.weblaptop.service.CTPNhapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ctpnhaps")
public class CTPNhapController {
    @Autowired
    private CTPNhapService nhapService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAll() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();
        try {
            List<CTPNhapDTO> nhapDTOS = nhapService.retrieveCTPNs();
            List list = Collections.synchronizedList(new ArrayList(nhapDTOS));

            if (responseDTO.addAll(list) == true) {
                response.setData(nhapDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_CT_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_CT_PHIEU_NHAP_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ctpnhap/{nhapId}/{productId}")
    public ResponseEntity<ResponseDTO> findCTPN(@PathVariable("nhapId") Long nhapId, @PathVariable("productId") Long productId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CTPNId ctpnId = new CTPNId(nhapId, productId);
            Optional<CTPNhapDTO> nhapDTO = nhapService.getCTPN(ctpnId);

            responseDTO.setData(nhapDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_CT_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(""+ErrorCode.FIND_CT_PHIEU_NHAP_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/ctpnhap")
    public ResponseEntity<ResponseDTO> createCTPN(@Valid @RequestBody CTPNhapDTO nhapDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CTPNhapDTO dto = nhapService.saveCTPN(nhapDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_CT_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(""+ErrorCode.ADD_CT_PHIEU_NHAP_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
    //
////    //update
    @PutMapping("/ctpnhap/{nhapId}/{productId}")
    public ResponseEntity<ResponseDTO> updatePN(@PathVariable("nhapId") Long nhapId, @PathVariable("productId") Long productId,
                                                @Valid @RequestBody CTPNhapDTO nhapDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CTPNId ctpnId = new CTPNId(nhapId, productId);
            CTPNhapDTO updatePN = nhapService.updateCTPN(ctpnId, nhapDTO);

            responseDTO.setData(updatePN);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_CT_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_CT_PHIEU_NHAP_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    ////    //delete
    @DeleteMapping("/ctpnhap/{nhapId}/{productId}")
    public ResponseEntity<ResponseDTO> deletePN(@PathVariable("nhapId") Long nhapId, @PathVariable("productId") Long productId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CTPNId ctpnId = new CTPNId(nhapId, productId);
            Boolean isDel = nhapService.deleteCTPN(ctpnId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_CT_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_CT_PHIEU_NHAP_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
}
