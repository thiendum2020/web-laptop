package com.java.weblaptop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user", indexes = @Index(name = "userIndex", columnList = "user_name, user_email, user_password"), schema = "public")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private long user_id;

    @Column(name = "user_name")
    @NotBlank
    private String userName;

    @Column(name = "user_email")
    @NotBlank
    private String userEmail;

    @Column(name = "user_password")
    @NotBlank
    private String userPassword;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserDetail> userDetails;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Order> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<ProductRating> ratings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<PhieuDat> phieuDats;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<PhieuNhap> phieuNhaps;

    public User(long user_id, String userName, String userEmail, String userPassword) {
        this.user_id = user_id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public User(String userName, String userEmail, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;

    }
}
