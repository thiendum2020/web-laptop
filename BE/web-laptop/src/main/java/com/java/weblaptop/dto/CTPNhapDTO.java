package com.java.weblaptop.dto;

import com.java.weblaptop.entity.CTPNId;
import com.java.weblaptop.entity.CTPNhap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CTPNhapDTO {
    private CTPNId id;

    @Min(value = 1)
    private int qtyNhap;

    @Min(value = 1)
    private float priceNhap;

    public CTPNhapDTO convertToDto(CTPNhap detail) {
        CTPNhapDTO deatailDTO = new CTPNhapDTO();

        deatailDTO.setId(detail.getCtpnId());
        deatailDTO.setQtyNhap(detail.getQtyNhap());
        deatailDTO.setPriceNhap(detail.getPriceNhap());


        return deatailDTO;
    }

    public CTPNhap convertToEti(CTPNhapDTO deatailDTO) {
        CTPNhap detail = new CTPNhap();

        detail.setCtpnId(deatailDTO.getId());
        detail.setQtyNhap(deatailDTO.getQtyNhap());
        detail.setPriceNhap(deatailDTO.getPriceNhap());

        return detail;
    }


    public List<CTPNhapDTO> toListDto(List<CTPNhap> listEntity) {
        List<CTPNhapDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
