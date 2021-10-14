package com.java.weblaptop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order", indexes = @Index(name = "orderIndex", columnList = "order_id, user_id, status_id"), schema = "public")
@JsonIgnoreProperties (value = { "hibernateLazyInitializer", "handler"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long order_id;

    @Column(name = "total_qty")
    @Min(value = 1)
    private int totalQty;

    @Column(name = "total_price")
    @Min(value = 1)
    private float totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<OrderDetail> orderDetails;

    public Order(int totalQty, float totalPrice, User user, Status status) {
        this.totalQty = totalQty;
        this.totalPrice = totalPrice;
        this.user = user;
        this.status = status;
    }

}
