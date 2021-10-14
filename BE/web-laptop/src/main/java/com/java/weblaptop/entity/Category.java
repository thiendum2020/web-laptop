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
@Table(name = "category", indexes = @Index(name = "firstIndex", columnList = "category_name"))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long category_id;
//    @ManyToMany
//    @JoinTable(name = "subcategories",
//            joinColumns = { @JoinColumn(name = "parent_id") },
//            inverseJoinColumns = { @JoinColumn(name = "category_id")
//            })
//    private Set<Category> subCategories = new HashSet<>();

//    @ManyToOne
//    @JoinColumn(name = "parent_id")
//    public Category parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private Set<Product> products;

    @Column(name = "category_name")
    @NotBlank
    private String categoryName;

    public Category(long category_id, String categoryName) {
        this.category_id = category_id;
//        this.products = products;
        this.categoryName = categoryName;
    }
}
