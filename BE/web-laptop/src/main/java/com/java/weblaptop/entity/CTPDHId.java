package com.java.weblaptop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CTPDHId implements Serializable {
    private long productId;

    private long datId;

    // default constructor


    public CTPDHId() {
    }

    public CTPDHId(long productId, long datId) {
        this.productId = productId;
        this.datId = datId;
    }

    // equals() and hashCode()


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CTPDHId ctpdhId = (CTPDHId) o;
        return productId == ctpdhId.productId && datId == ctpdhId.datId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, datId);
    }
}
