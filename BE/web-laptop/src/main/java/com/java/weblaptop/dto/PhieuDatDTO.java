package com.java.weblaptop.dto;

import com.java.weblaptop.entity.PhieuDat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PhieuDatDTO {
    private long datId;
    private LocalDate createDate;
    private long userId;
    private long statusId;

    public PhieuDatDTO convertToDto(PhieuDat phieuDat) {
        PhieuDatDTO phieuDatDTO = new PhieuDatDTO();
        phieuDatDTO.setDatId(phieuDat.getDatId());
        phieuDatDTO.setCreateDate(phieuDat.getCreateDate());
        phieuDatDTO.setUserId(phieuDat.getUser().getUser_id());
        phieuDatDTO.setStatusId(phieuDat.getStatus().getStatus_id());

        return phieuDatDTO;
    }

    public PhieuDat convertToEti(PhieuDatDTO phieuDatDTO) {
        PhieuDat phieuDat = new PhieuDat();

        phieuDat.setCreateDate(LocalDate.now());

        return phieuDat;
    }


    public List<PhieuDatDTO> toListDto(List<PhieuDat> listEntity) {
        List<PhieuDatDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
