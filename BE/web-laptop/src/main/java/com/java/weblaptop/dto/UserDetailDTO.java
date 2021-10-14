package com.java.weblaptop.dto;

import com.java.weblaptop.entity.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {
    private long udetail_id;

    @NotNull
    private long udetailPhone;

    @NotNull
    private String udetailAddress;
    private long user_id;


    public UserDetailDTO convertToDto(UserDetail detail) {
        UserDetailDTO detailDTO = new UserDetailDTO();
        detailDTO.setUdetail_id(detail.getUdetail_id());
        detailDTO.setUdetailPhone(detail.getUdetailPhone());
        detailDTO.setUdetailAddress(detail.getUdetailAddress());
        detailDTO.setUser_id(detail.getUser().getUser_id());

        return detailDTO;
    }

    public UserDetail convertToEti(UserDetailDTO detailDTO) {
        UserDetail detail = new UserDetail();

        detail.setUdetailPhone(detailDTO.getUdetailPhone());
        detail.setUdetailAddress(detailDTO.getUdetailAddress());

        return detail;
    }


    public List<UserDetailDTO> toListDto(List<UserDetail> listEntity) {
        List<UserDetailDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }

}
