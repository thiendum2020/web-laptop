package com.java.weblaptop.dto;

import com.java.weblaptop.entity.PhieuNhap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PhieuNhapResponseDTO {
    private long nhapId;
    private LocalDate createDate;
    private String username;
    private String statusName;
    private long datId;
    private List<CTPNhapResponseDTO> ctpNhapResponseDTOS;


    public PhieuNhapResponseDTO convertToDto(PhieuNhap phieuNhap) {
        PhieuNhapResponseDTO nhapDTO = new PhieuNhapResponseDTO();
        nhapDTO.setNhapId(phieuNhap.getNhapId());
        nhapDTO.setCreateDate(phieuNhap.getCreateDate());
        nhapDTO.setUsername(phieuNhap.getUser().getUserName());
        //nhapDTO.setDatId(phieuNhap.getPhieuDat().getDatId());
        nhapDTO.setStatusName(phieuNhap.getStatus().getStatusName());
        nhapDTO.setDatId(phieuNhap.getPhieuDat().getDatId());

        return nhapDTO;
    }

    public PhieuNhap convertToEti(PhieuNhapResponseDTO nhapDTO) {
        PhieuNhap nhap = new PhieuNhap();

        nhap.setCreateDate(LocalDate.now());

        return nhap;
    }


    public List<PhieuNhapResponseDTO> toListDto(List<PhieuNhap> listEntity) {
        List<PhieuNhapResponseDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
