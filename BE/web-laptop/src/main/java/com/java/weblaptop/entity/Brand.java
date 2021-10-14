package com.java.weblaptop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "brand", indexes = @Index(name = "brandIndex", columnList = "brand_name"))
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long brand_id;

    @Column(name = "brand_name")
    @NotBlank
    private String brandName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    private Set<Product> products;
}
