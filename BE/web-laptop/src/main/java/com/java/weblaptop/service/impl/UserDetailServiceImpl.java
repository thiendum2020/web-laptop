package com.java.weblaptop.service.impl;

import com.rookie.webwatch.dto.ErrorCode;
import com.rookie.webwatch.dto.UserDetailDTO;

import com.rookie.webwatch.entity.User;
import com.rookie.webwatch.entity.UserDetail;
import com.rookie.webwatch.exception.ResourceNotFoundException;
import com.rookie.webwatch.repository.UserDetailRepository;

import com.rookie.webwatch.repository.UserRepository;
import com.rookie.webwatch.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailRepository detailRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDetailDTO> retrieveUserDetails() {
        List<UserDetail> userDetails = detailRepository.findAll();
        return new UserDetailDTO().toListDto(userDetails);
    }



    @Override
    public Optional<UserDetailDTO> getUserDetail(Long udetailId) throws ResourceNotFoundException {
        UserDetail detail = detailRepository.findById(udetailId).orElseThrow(() -> new ResourceNotFoundException("user detail not found for this id: "+udetailId));
        return Optional.of(new UserDetailDTO().convertToDto(detail));
    }

    @Override
    public UserDetailDTO saveUserDetail(UserDetailDTO detailDTO) throws ResourceNotFoundException {
        User user = userRepository.findById(detailDTO.getUser_id()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+detailDTO.getUser_id()));

        UserDetail detail = new UserDetailDTO().convertToEti(detailDTO);

        detail.setUser(user);
        return new UserDetailDTO().convertToDto(detailRepository.save(detail));
    }

    @Override
    public Boolean deleteUserDetail(Long udetailId) throws ResourceNotFoundException {
        UserDetail userDetail = detailRepository.findById(udetailId).orElseThrow(() -> new ResourceNotFoundException("user_detail not found for this id: " + udetailId));
        this.detailRepository.delete(userDetail);
        return true;
    }

    @Override
    public UserDetailDTO updateUserDetail(Long id, UserDetailDTO detailDTO) throws ResourceNotFoundException {
        UserDetail detailExist = detailRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("user detail not found for this id: "+id));

        detailExist.setUdetailPhone(detailDTO.getUdetailPhone());
        detailExist.setUdetailAddress(detailDTO.getUdetailAddress());

        UserDetail detail = new UserDetail();
        detail = detailRepository.save(detailExist);

        return new UserDetailDTO().convertToDto(detail);
    }

    @Override
    public List<UserDetailDTO> findDetailByUser(Long userId) throws ResourceNotFoundException {
        Optional<User> userExist = userRepository.findById(userId);
        if(!userExist.isPresent()){
            throw new ResourceNotFoundException(""+ ErrorCode.FIND_USER_ERROR);
        }
        User user = userExist.get();

        List<UserDetail> list = null;
        list = detailRepository.getUserDetailByUser(user);

        List<UserDetailDTO> detailDTOS = new ArrayList<>();
        detailDTOS = new UserDetailDTO().toListDto(list);
        return detailDTOS;
    }
}
