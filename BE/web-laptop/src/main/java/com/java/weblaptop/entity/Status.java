package com.java.weblaptop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "status", indexes = @Index(name = "statusIndex", columnList = "status, status_name"))
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long status_id;

    @Column(name = "status")
    @Min(value = 0)
    private long status;

    @Column(name = "status_name")
    @NotBlank
    private String statusName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private Set<Order> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private Set<PhieuDat> phieuDats;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private Set<PhieuNhap> phieuNhaps;

    public Status(long status_id, long status, String statusName) {
        this.status_id = status_id;
        this.status = status;
        this.statusName = statusName;
        //this.orders = orders;
    }

}
