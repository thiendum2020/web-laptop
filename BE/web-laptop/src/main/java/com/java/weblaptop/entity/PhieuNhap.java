package com.java.weblaptop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phieu_nhap")
public class PhieuNhap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pn_id")
    private long nhapId;

    @Column(name = "ngay_tao")
    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pd_id", referencedColumnName = "pd_id")
    private PhieuDat phieuDat;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "status_id")
//    private Status status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phieuNhap")
    private Set<CTPNhap> ctpNhaps;
}
