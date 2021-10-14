package com.java.weblaptop.service.impl;

import com.rookie.webwatch.dto.*;
import com.rookie.webwatch.entity.*;
import com.rookie.webwatch.exception.ResourceNotFoundException;
import com.rookie.webwatch.repository.CTPDatRepository;
import com.rookie.webwatch.repository.PhieuDatRepository;
import com.rookie.webwatch.repository.Productrepository;
import com.rookie.webwatch.service.CTPDatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CTPDatServiceImpl implements CTPDatService {
    @Autowired
    private CTPDatRepository ctpDatRepository;

    @Autowired
    private PhieuDatRepository datRepository;

    @Autowired
    private Productrepository productrepository;

    @Override
    public List<CTPDatDTO> retrieveCTPDs() {
        List<CTPDat> phieuDats = ctpDatRepository.findAll();

        return new CTPDatDTO().toListDto(phieuDats);
    }

    @Override
    public Optional<CTPDatDTO> getCTPD(CTPDHId ctpdhId) throws ResourceNotFoundException {
        CTPDat phieuDat = ctpDatRepository.findById(ctpdhId).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id"+ctpdhId));

        return Optional.of(new CTPDatDTO().convertToDto(phieuDat));
    }

    @Override
    public CTPDatDTO saveCTPD(CTPDatDTO ctpDatDTO) throws ResourceNotFoundException {
        CTPDat phieuDat = new CTPDatDTO().convertToEti(ctpDatDTO);
        PhieuDat pd = datRepository.getById(ctpDatDTO.getId().getDatId());
        phieuDat.setPhieuDat(pd);
        return new CTPDatDTO().convertToDto(ctpDatRepository.save(phieuDat));
    }

    @Override
    public Boolean deleteCTPD(CTPDHId ctpdhId) throws ResourceNotFoundException {
        CTPDat ctpDat = ctpDatRepository.findById(ctpdhId).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id"));
        this.ctpDatRepository.delete(ctpDat);
        return true;
    }

    @Override
    public CTPDatDTO updateCTPD(CTPDHId ctpdhId, CTPDatDTO ctpDatDTO) throws ResourceNotFoundException {
        CTPDat ctpdExist = ctpDatRepository.findById(ctpdhId).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id"));
        ctpdExist.setQtyDat(ctpDatDTO.getQtyDat());
        ctpdExist.setPriceDat(ctpDatDTO.getPriceDat());

        CTPDat phieuDat = new CTPDat();
        phieuDat = ctpDatRepository.save(ctpdExist);
        return new CTPDatDTO().convertToDto(phieuDat);
    }

    @Override
    public List<CTPDatResponseDTO> findCTByPD(long datId) throws ResourceNotFoundException {
        Optional<PhieuDat> datExist = datRepository.findById(datId);
        if(!datExist.isPresent()){
            throw new ResourceNotFoundException(""+ ErrorCode.FIND_CT_PHIEU_DAT_ERROR);
        }
        PhieuDat dat = datExist.get();

        List<CTPDat> list = null;
        list = ctpDatRepository.findCTPDatByPhieuDat(dat);

        System.out.println("bcbcbcbcbcc"+dat.getDatId()+"ababababa"+dat.getCtpDats().size());
        System.out.println("gngngngg"+list.size());
//        CTPDatResponseDTO ctpDatResponseDTO = new CTPDatResponseDTO().convertToDto(ctpDatRepository.findByCtpdhIdDatId(datId));
//        Product product = productrepository.getById(ctpDatResponseDTO.getId().getProductId());
//        List<ImageDTO> dtos = new ArrayList<>();
//        if(product.getProductImages()!=null){
//            product.getProductImages().forEach(e -> {
//                dtos.add(new ImageDTO().convertToDto(e));
//            });
//        }

        List<CTPDatResponseDTO> detailDTOS = new ArrayList<>();
//        List<CTPDatResponseDTO> finalDetailDTOS = detailDTOS;
//        list.forEach(l -> {
//            System.out.println(l.getCtpdhId().getDatId()+"=========");
//            finalDetailDTOS.add(new CTPDatResponseDTO().convertToDto(l));
//        });
//        finalDetailDTOS.forEach(l -> {
//            l.setImageDTOS(dtos);
//            l.setProductName(product.getProductName());
//        });

        detailDTOS = new CTPDatResponseDTO().toListDto(list);
        return detailDTOS;
    }
}
