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
//@IdClass(CTPDHId.class)
@Table(name = "ct_phieu_dat")
public class CTPDat {
    @EmbeddedId
    private CTPDHId ctpdhId;

//    @Id
//    private long datId;

    @Column(name = "so_luong")
    @Min(value = 1)
    private int qtyDat;

    @Column(name = "don_gia")
    @Min(value = 1)
    private float priceDat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pd_id")
    private PhieuDat phieuDat;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private Product product;
}
