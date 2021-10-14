package com.java.weblaptop.service.impl;

import com.rookie.webwatch.dto.*;
import com.rookie.webwatch.entity.*;
import com.rookie.webwatch.exception.ResourceNotFoundException;
import com.rookie.webwatch.repository.*;
import com.rookie.webwatch.service.PhieuDatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PhieuDatServiceImpl implements PhieuDatService {
    @Autowired
    private PhieuDatRepository phieuDatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CTPDatRepository datRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private Productrepository productrepository;

    @Override
    public List<PhieuDatResponseDTO> retrievePhieuDats() {
        List<PhieuDat> phieuDats = phieuDatRepository.findAll();

        return new PhieuDatResponseDTO().toListDto(phieuDats);
    }

    @Override
    public PhieuDatResponseDTO getPhieuDat(Long datId) throws ResourceNotFoundException {
        PhieuDat phieuDat = phieuDatRepository.findById(datId).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id: "+datId));


        PhieuDatResponseDTO phieuDatResponseDTO = new PhieuDatResponseDTO();

        phieuDatResponseDTO.setDatId(phieuDat.getDatId());
        phieuDatResponseDTO.setCreateDate(phieuDat.getCreateDate());
        phieuDatResponseDTO.setUsername(phieuDat.getUser().getUserName());
        phieuDatResponseDTO.setStatusName(phieuDat.getStatus().getStatusName());



        List<CTPDatResponseDTO> ctpDatResponseDTOS = new ArrayList<>();
        
        phieuDat.getCtpDats().forEach(d -> {
            CTPDatResponseDTO ctpDatResponseDTO = new CTPDatResponseDTO();
            
            Product product = productrepository.getById(d.getCtpdhId().getProductId());
            ctpDatResponseDTO.setProductName(product.getProductName());

            List<ImageDTO> dtos = new ArrayList<>();

            if(product.getProductImages()!=null){
                product.getProductImages().forEach(e -> {
                    dtos.add(new ImageDTO().convertToDto(e));
                });
            }
            ctpDatResponseDTO.setId(d.getCtpdhId());
            ctpDatResponseDTO.setImageDTOS(dtos);
            ctpDatResponseDTO.setQtyDat(d.getQtyDat());
            ctpDatResponseDTO.setPriceDat(d.getPriceDat());

            ctpDatResponseDTOS.add(ctpDatResponseDTO);
        });


        phieuDatResponseDTO.setCtpDatDTOS(ctpDatResponseDTOS);

//        CTPDatResponseDTO ctpDatResponseDTO = new CTPDatResponseDTO().convertToDto(datRepository.findByCtpdhIdDatId(datId));
//        List<CTPDatResponseDTO> ctpDatResponseDTOS = new CTPDatResponseDTO().toListDto(ctpDat);
//
//        Product product = productrepository.getById(ctpDatResponseDTO.getId().getProductId());
//
//
//        ctpDatResponseDTO.setProductName(product.getProductName());
//
//        List<ImageDTO> dtos = new ArrayList<>();
//
//        if(product.getProductImages()!=null){
//            product.getProductImages().forEach(e -> {
//                dtos.add(new ImageDTO().convertToDto(e));
//            });
//        }
//        ctpDatResponseDTO.setImageDTOS(dtos);
        
        
//        CTPDat ct = datRepository.findByCtpdhIdDatId(datId);
//        ctpDatResponseDTO.setQtyDat(ct.getQtyDat());
//        ctpDatResponseDTO.setPriceDat(ct.getPriceDat());
//
//        System.out.println(ctpDatResponseDTO.getProductName()+"gia, so luong ===="+ctpDatResponseDTO.getImageDTOS());
//
//        List<CTPDatResponseDTO> listDto = new ArrayList<>();
//        ctpDat.forEach(d -> {
//
//            System.out.println(d.getCtpdhId().getDatId()+"=========");
//            listDto.add(new CTPDatResponseDTO().convertToDto(d));
//        });
//        listDto.forEach(l -> {
//            l.setImageDTOS(dtos);
//            l.setProductName(product.getProductName());
//        });
//        PhieuDatResponseDTO phieuDatResponseDTO = new PhieuDatResponseDTO();
//        //phieuDatResponseDTO.convertToDto(phieuDat);
//        phieuDatResponseDTO.setDatId(phieuDat.getDatId());
//        phieuDatResponseDTO.setCreateDate(phieuDat.getCreateDate());
//        phieuDatResponseDTO.setUsername(phieuDat.getUser().getUserName());
//        phieuDatResponseDTO.setStatusName(phieuDat.getStatus().getStatusName());
//        phieuDatResponseDTO.setCtpDatDTOS(listDto);
//


        return phieuDatResponseDTO;
    }

    @Override
    public PhieuDatDTO savePD(PhieuDatDTO phieuDatDTO) throws ResourceNotFoundException {
        User user = userRepository.findById(phieuDatDTO.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+phieuDatDTO.getUserId()));
        Status status = statusRepository.findById(phieuDatDTO.getStatusId()).orElseThrow(() ->
                new ResourceNotFoundException("status not found for this id: "+phieuDatDTO.getStatusId()));

        PhieuDat phieuDat = new PhieuDatDTO().convertToEti(phieuDatDTO);
        phieuDat.setUser(user);
        phieuDat.setStatus(status);

        return new PhieuDatDTO().convertToDto(phieuDatRepository.save(phieuDat));
    }

    @Override
    public Boolean deletePD(Long datId) throws ResourceNotFoundException {
        PhieuDat phieuDat = phieuDatRepository.findById(datId).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id: "+datId));
        this.phieuDatRepository.delete(phieuDat);
        return true;
    }

    @Override
    public PhieuDatDTO updatePD(Long datId) throws ResourceNotFoundException {
        PhieuDat phieuDatExist = phieuDatRepository.findById(datId).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id: "+datId));

        phieuDatExist.setCreateDate(LocalDate.now());

        PhieuDat phieuDat = new PhieuDat();
        phieuDat = phieuDatRepository.save(phieuDatExist);
        return new PhieuDatDTO().convertToDto(phieuDat);
    }

    @Override
    public PhieuDatDTO updateStatusPD(Long datId, String status) throws ResourceNotFoundException {
        PhieuDat datExist = phieuDatRepository.findById(datId).orElseThrow(() ->
                new ResourceNotFoundException("phieu dat not found for this id: "+datId));

        if(status.equals("waiting confirm")){
            Status stt = statusRepository.findAllByStatusName(status);
            datExist.setStatus(stt);
        } else if(status.equals("waiting receipt")){
            Status stt = statusRepository.findAllByStatusName(status);
            datExist.setStatus(stt);
        } else if(status.equals("no receipt")){
            Status stt = statusRepository.findAllByStatusName(status);
            datExist.setStatus(stt);
        }
         else if(status.equals("cancel")) {
            Status stt = statusRepository.findAllByStatusName(status);
            datExist.setStatus(stt);

        } else if(status.equals("complete")) {
            Status stt = statusRepository.findAllByStatusName(status);
            datExist.setStatus(stt);
        }

        PhieuDat phieuDat = new PhieuDat();
        phieuDat = phieuDatRepository.save(datExist);

        return new PhieuDatDTO().convertToDto(phieuDat);
    }
}
