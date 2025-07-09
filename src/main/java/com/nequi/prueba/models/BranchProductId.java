package com.nequi.prueba.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class BranchProductId implements Serializable {
    private Long branchId;
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BranchProductId))
            return false;
        BranchProductId that = (BranchProductId) o;
        return Objects.equals(branchId, that.branchId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchId, productId);
    }

}
