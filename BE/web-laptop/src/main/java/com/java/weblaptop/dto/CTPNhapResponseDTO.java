package com.java.weblaptop.dto;

import com.java.weblaptop.entity.CTPNId;
import com.java.weblaptop.entity.CTPNhap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CTPNhapResponseDTO {
    private CTPNId id;
    private int qtyNhap;
    private float priceNhap;
    private String productName;
    private List<ImageDTO> imageDTOS;

    public CTPNhapResponseDTO convertToDto(CTPNhap detail) {
        CTPNhapResponseDTO deatailDTO = new CTPNhapResponseDTO();

        deatailDTO.setId(detail.getCtpnId());
        deatailDTO.setQtyNhap(detail.getQtyNhap());
        deatailDTO.setPriceNhap(detail.getPriceNhap());

        return deatailDTO;
    }

    public CTPNhap convertToEti(CTPNhapResponseDTO deatailDTO) {
        CTPNhap detail = new CTPNhap();

        detail.setCtpnId(deatailDTO.getId());
        detail.setQtyNhap(deatailDTO.getQtyNhap());
        detail.setPriceNhap(deatailDTO.getPriceNhap());

        return detail;
    }


    public List<CTPNhapResponseDTO> toListDto(List<CTPNhap> listEntity) {
        List<CTPNhapResponseDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
