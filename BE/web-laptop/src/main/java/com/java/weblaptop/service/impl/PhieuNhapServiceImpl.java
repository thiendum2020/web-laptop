package com.java.weblaptop.service.impl;


import com.rookie.webwatch.dto.*;
import com.rookie.webwatch.entity.*;
import com.rookie.webwatch.exception.ResourceNotFoundException;

import com.rookie.webwatch.repository.*;
import com.rookie.webwatch.service.PhieuNhapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PhieuNhapServiceImpl implements PhieuNhapService {
    @Autowired
    private PhieuNhapRepository nhapRepository;

    @Autowired
    private PhieuDatRepository datRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private Productrepository productrepository;

    @Autowired
    private CTPNhapRepository ctpNhapRepository;

    @Override
    public List<PhieuNhapResponseDTO> retrievePhieuNhaps() {
        List<PhieuNhap> nhaps = nhapRepository.findAll();

        return new PhieuNhapResponseDTO().toListDto(nhaps);
    }

    @Override
    public PhieuNhapResponseDTO getPhieuNhap(Long nhapId) throws ResourceNotFoundException {
        PhieuNhap nhap = nhapRepository.findById(nhapId).orElseThrow(() -> new ResourceNotFoundException("phieu nhap not found for this id: "+nhapId));

        PhieuNhapResponseDTO phieuNhapResponseDTO = new PhieuNhapResponseDTO();

        phieuNhapResponseDTO.setNhapId(nhap.getNhapId());
        phieuNhapResponseDTO.setCreateDate(nhap.getCreateDate());
        phieuNhapResponseDTO.setUsername(nhap.getUser().getUserName());
        phieuNhapResponseDTO.setStatusName(nhap.getStatus().getStatusName());
        phieuNhapResponseDTO.setDatId(nhap.getPhieuDat().getDatId());

        List<CTPNhapResponseDTO> ctpNhapResponseDTOS = new ArrayList<>();

        System.out.println("------------------"+nhap.getCtpNhaps().size());
        nhap.getCtpNhaps().forEach(d -> {
            CTPNhapResponseDTO ctpNhapResponseDTO = new CTPNhapResponseDTO();

            Product product = productrepository.getById(d.getCtpnId().getProductId());
            ctpNhapResponseDTO.setProductName(product.getProductName());

            List<ImageDTO> dtos = new ArrayList<>();

            if(product.getProductImages()!=null){
                product.getProductImages().forEach(e -> {
                    dtos.add(new ImageDTO().convertToDto(e));
                });
            }
            ctpNhapResponseDTO.setId(d.getCtpnId());
            ctpNhapResponseDTO.setImageDTOS(dtos);
            ctpNhapResponseDTO.setQtyNhap(d.getQtyNhap());
            ctpNhapResponseDTO.setPriceNhap(d.getPriceNhap());

            ctpNhapResponseDTOS.add(ctpNhapResponseDTO);
        });


        phieuNhapResponseDTO.setCtpNhapResponseDTOS(ctpNhapResponseDTOS);

        return phieuNhapResponseDTO;
    }

    @Override
    public PhieuNhapDTO savePN(PhieuNhapDTO nhapDTO) throws ResourceNotFoundException {
        User user = userRepository.findById(nhapDTO.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+nhapDTO.getUserId()));

        Status status = statusRepository.findById(nhapDTO.getStatusId()).orElseThrow(() ->
                new ResourceNotFoundException("status not found for this id: "+nhapDTO.getStatusId()));

        PhieuDat dat = datRepository.findById(nhapDTO.getDatId()).orElseThrow(() ->
                new ResourceNotFoundException("phieu dat not found for this id: "+nhapDTO.getDatId()));

        PhieuNhap nhap = new PhieuNhapDTO().convertToEti(nhapDTO);
        nhap.setUser(user);
        nhap.setPhieuDat(dat);
        nhap.setStatus(status);

        return new PhieuNhapDTO().convertToDto(nhapRepository.save(nhap));
    }

    @Override
    public Boolean deletePN(Long nhapId) throws ResourceNotFoundException {
        PhieuNhap nhap = nhapRepository.findById(nhapId).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id: "+nhapId));
        this.nhapRepository.delete(nhap);
        return true;
    }

    @Override
    public PhieuNhapDTO updatePN(Long nhapId) throws ResourceNotFoundException {
        PhieuNhap nhapExist = nhapRepository.findById(nhapId).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id: "+nhapId));

        nhapExist.setCreateDate(LocalDate.now());

        PhieuNhap nhap = new PhieuNhap();
        nhap = nhapRepository.save(nhapExist);
        return new PhieuNhapDTO().convertToDto(nhap);
    }

    @Override
    public PhieuNhapDTO updateStatusPN(Long nhapId, String status) throws ResourceNotFoundException {
        PhieuNhap nhapExist = nhapRepository.findById(nhapId).orElseThrow(() ->
                new ResourceNotFoundException("phieu nhap not found for this id: "+nhapId));

        if(status.equals("waiting receipt")){
            Status stt = statusRepository.findAllByStatusName(status);
            nhapExist.setStatus(stt);
            //Status sttDat = statusRepository.findAllByStatusName("complete");
            nhapExist.getPhieuDat().setStatus(stt);
            datRepository.save(nhapExist.getPhieuDat());
        } else if(status.equals("receipted")){
            Status stt = statusRepository.findAllByStatusName(status);
            nhapExist.setStatus(stt);
        }
        else if(status.equals("cancel")) {
            Status stt = statusRepository.findAllByStatusName(status);
            nhapExist.setStatus(stt);

            //Status sttDat = statusRepository.findAllByStatusName("no receipt");
            nhapExist.getPhieuDat().setStatus(stt);
            datRepository.save(nhapExist.getPhieuDat());

        } else if(status.equals("complete")) {
            Status stt = statusRepository.findAllByStatusName(status);
            nhapExist.setStatus(stt);
            List<CTPNhap> ctpNhapList = ctpNhapRepository.findAllByCtpnIdNhapId(nhapId);
            ctpNhapList.forEach(n -> {
                Product product2 = productrepository.getById(n.getCtpnId().getProductId());
                System.out.println("product 2 id ========"+n.getCtpnId().getProductId());
                product2.setProductQty(product2.getProductQty() + n.getQtyNhap());
                productrepository.save(product2);
                System.out.println("sl sau khi add"+product2.getProductQty());
            });
            nhapExist.getPhieuDat().setStatus(stt);
            datRepository.save(nhapExist.getPhieuDat());
        }

        PhieuNhap nhap = new PhieuNhap();
        nhap = nhapRepository.save(nhapExist);

        return new PhieuNhapDTO().convertToDto(nhap);
    }
}
