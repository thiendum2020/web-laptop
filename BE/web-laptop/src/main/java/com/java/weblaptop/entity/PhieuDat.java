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
@Table(name = "phieu_dat_hang")
public class PhieuDat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pd_id")
    private long datId;

    @Column(name = "ngay_tao")
    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "phieuDat", cascade = CascadeType.ALL, orphanRemoval = true)
//    private PhieuNhap phieuNhap;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "status_id")
//    private Status status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phieuDat")
    private Set<CTPDat> ctpDats;
}
