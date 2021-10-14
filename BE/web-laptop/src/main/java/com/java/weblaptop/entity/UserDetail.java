package com.java.weblaptop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_detail", indexes = @Index(name = "udetailIndex", columnList = "udetail_phone, udetail_address, user_id"))
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long udetail_id;

    @Column(name = "udetail_phone")
//    @NotBlank
    private long udetailPhone;

    @Column(name = "udetail_address")
    @NotBlank
    private String udetailAddress;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id")
    private User user;

}
