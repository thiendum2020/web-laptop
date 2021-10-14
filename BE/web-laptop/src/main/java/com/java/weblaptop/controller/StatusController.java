package com.java.weblaptop.controller;

import com.java.weblaptop.dto.ErrorCode;
import com.java.weblaptop.dto.ResponseDTO;
import com.java.weblaptop.dto.StatusDTO;

import com.java.weblaptop.dto.SuccessCode;
import com.java.weblaptop.exception.*;
import com.java.weblaptop.service.StatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/statuses")
public class StatusController {
    @Autowired
    private StatusService statusService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllStatus() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();
        try {
        List<StatusDTO> statuses = statusService.retrieveStatuses();
            List list = Collections.synchronizedList(new ArrayList(statuses));

            if (responseDTO.addAll(list) == true) {
                response.setData(statuses);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_STATUS_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_STATUS_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{status_id}")
    public ResponseEntity<ResponseDTO> findStatus(@PathVariable("status_id") Long statusId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<StatusDTO> statusDTO = statusService.getStatus(statusId);

            responseDTO.setData(statusDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_STATUS_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(""+ErrorCode.FIND_STATUS_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/status")
    public ResponseEntity<ResponseDTO> createStatus(@Valid @RequestBody StatusDTO statusDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            StatusDTO dto = statusService.saveStatus(statusDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_STATUS_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(""+ErrorCode.ADD_STATUS_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

//    //update
    @PutMapping("/status/{status_id}")
    public ResponseEntity<ResponseDTO> updateStatus(@PathVariable(value = "status_id") Long statusId,
                                                   @Valid @RequestBody StatusDTO statusDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            StatusDTO updateSta = statusService.updateStatus(statusId, statusDTO);

            responseDTO.setData(updateSta);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_STATUS_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_STATUS_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

//    //delete
    @DeleteMapping("/status/{status_id}")
    public ResponseEntity<ResponseDTO> deleteStatus(@PathVariable(value = "status_id") Long statusId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = statusService.deleteStatus(statusId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_STATUS_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_STATUS_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
}
