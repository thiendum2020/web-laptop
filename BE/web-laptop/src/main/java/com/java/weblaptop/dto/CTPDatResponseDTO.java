package com.java.weblaptop.dto;

import com.java.weblaptop.entity.CTPDHId;
import com.java.weblaptop.entity.CTPDat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CTPDatResponseDTO {
    private CTPDHId id;

    private int qtyDat;

    private float priceDat;
    private List<ImageDTO> imageDTOS;
    private String productName;

    public CTPDatResponseDTO convertToDto(CTPDat detail) {
        CTPDatResponseDTO deatailDTO = new CTPDatResponseDTO();
        deatailDTO.setId(detail.getCtpdhId());
        deatailDTO.setQtyDat(detail.getQtyDat());
        deatailDTO.setPriceDat(detail.getPriceDat());

        return deatailDTO;
    }

    public CTPDat convertToEti(CTPDatResponseDTO deatailDTO) {
        CTPDat detail = new CTPDat();

        detail.setCtpdhId(deatailDTO.getId());
        detail.setQtyDat(deatailDTO.getQtyDat());
        detail.setPriceDat(deatailDTO.getPriceDat());

        return detail;
    }


    public List<CTPDatResponseDTO> toListDto(List<CTPDat> listEntity) {
        List<CTPDatResponseDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
