package com.java.weblaptop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ct_phieu_nhap")
public class CTPNhap {
    @EmbeddedId
    private CTPNId ctpnId;
//    @Id
//    private long productId;
//
//    @Id
//    private long nhapId;

    @Column(name = "so_luong")
    @Min(value = 1)
    private int qtyNhap;

    @Column(name = "don_gia")
    @Min(value = 1)
    private float priceNhap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pn_id")
    private PhieuNhap phieuNhap;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private Product product;
}
