package com.java.weblaptop.service;

import com.java.weblaptop.dto.UserDetailDTO;

import com.java.weblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserDetailService {
    public List<UserDetailDTO> retrieveUserDetails();

    public Optional<UserDetailDTO> getUserDetail(Long udetailId) throws ResourceNotFoundException;

    public UserDetailDTO saveUserDetail(UserDetailDTO detailDTO) throws ResourceNotFoundException;

    public Boolean deleteUserDetail(Long udetailId) throws ResourceNotFoundException;

    public UserDetailDTO updateUserDetail(Long id, UserDetailDTO detailDTO) throws ResourceNotFoundException;

    public List<UserDetailDTO> findDetailByUser(Long userId) throws ResourceNotFoundException;
}
