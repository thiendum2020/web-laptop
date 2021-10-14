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
public class PhieuDatResponseDTO {
    private long datId;
    private LocalDate createDate;
    private String username;
    private String statusName;
    private List<CTPDatResponseDTO> ctpDatDTOS;


    public PhieuDatResponseDTO convertToDto(PhieuDat phieuDat) {
        PhieuDatResponseDTO phieuDatDTO = new PhieuDatResponseDTO();
        phieuDatDTO.setDatId(phieuDat.getDatId());
        phieuDatDTO.setCreateDate(phieuDat.getCreateDate());
        phieuDatDTO.setUsername(phieuDat.getUser().getUserName());
        phieuDatDTO.setStatusName(phieuDat.getStatus().getStatusName());
//        List<CTPDat> ctpDat = datRepository.findAllByCtpdhIdDatId(phieuDat.getDatId());
//        List<CTPDatDTO> listDto = new ArrayList<>();
//        ctpDat.forEach(d -> {
//            System.out.println(d.getCtpdhId().getDatId()+"=========");
//            listDto.add(new CTPDatDTO().convertToDto(d));
//        });
//
//        phieuDatDTO.setCtpDatDTOS(listDto);
//        List<CTPDatDTO> listDto = new ArrayList<>();
//        System.out.println(phieuDat.getCtpDats().size());
//        if(phieuDat.getCtpDats()!=null){
//            phieuDat.getCtpDats().forEach(e -> {
//                System.out.println(e.getCtpdhId().getDatId()+"======="+phieuDat.getDatId());
//                if(e.getCtpdhId().getDatId() == phieuDat.getDatId()) {
//                    listDto.add(new CTPDatDTO().convertToDto(e));
//                }
//            });
//        }
//        phieuDatDTO.setCtpDatDTOS(listDto);

        return phieuDatDTO;
    }

    public PhieuDat convertToEti(PhieuDatResponseDTO phieuDatDTO) {
        PhieuDat phieuDat = new PhieuDat();

        phieuDat.setCreateDate(LocalDate.now());

        return phieuDat;
    }


    public List<PhieuDatResponseDTO> toListDto(List<PhieuDat> listEntity) {
        List<PhieuDatResponseDTO> listDto = new ArrayList<>();

        listEntity.forEach(e->{
            listDto.add(this.convertToDto(e));
        });
        return listDto;
    }
}
