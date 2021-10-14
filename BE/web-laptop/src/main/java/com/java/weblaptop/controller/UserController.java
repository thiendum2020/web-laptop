package com.java.weblaptop.controller;

import com.java.weblaptop.dto.ErrorCode;
import com.java.weblaptop.dto.ResponseDTO;
import com.java.weblaptop.dto.SuccessCode;
import com.java.weblaptop.dto.UserDTO;

import com.java.weblaptop.exception.*;

import com.java.weblaptop.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllUser() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();
        try {
            List<UserDTO> userDTOS = userService.retrieveUsers();
            List list = Collections.synchronizedList(new ArrayList(userDTOS));

            if (responseDTO.addAll(list) == true) {
                response.setData(userDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_USER_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_USER_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<ResponseDTO> findUser(@PathVariable("user_id") Long userId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
        Optional<UserDTO> userDTO = userService.getUser(userId);

            responseDTO.setData(userDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_USER_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(""+ErrorCode.FIND_USER_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseDTO> createUser(@Valid @RequestBody UserDTO userDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            UserDTO dto = userService.saveUser(userDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_USER_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(""+ErrorCode.ADD_USER_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

//    //update
    @PutMapping("/user/{user_id}")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable(value = "user_id") Long userId,
                                                 @Valid @RequestBody UserDTO userDTO) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            UserDTO updateUser = userService.updateUser(userId, userDTO);

            responseDTO.setData(updateUser);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_USER_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_USER_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

//    //delete
    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable(value = "user_id") Long userId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = userService.deleteUser(userId);
                responseDTO.setData(isDel);
                responseDTO.setSuccessCode(SuccessCode.DELETE_USER_SUCCESS);
            } catch (Exception e){
                throw new DeleteDataFail(""+ErrorCode.DELETE_USER_ERROR);
            }

        return ResponseEntity.ok(responseDTO);
    }

}
