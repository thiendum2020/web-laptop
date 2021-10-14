package com.java.weblaptop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CTPNId implements Serializable {
    private long nhapId;
    private long productId;

    public CTPNId() {
    }

    public CTPNId(long nhapId, long productId) {
        this.nhapId = nhapId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CTPNId ctpnId = (CTPNId) o;
        return nhapId == ctpnId.nhapId && productId == ctpnId.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nhapId, productId);
    }
}
