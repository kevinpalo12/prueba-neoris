package com.nequi.prueba.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "branch_product")
public class BranchProductModel {

    @EmbeddedId
    private BranchProductId id = new BranchProductId();

    @ManyToOne
    @MapsId("branchId")
    @JoinColumn(name = "branch_id")
    private BranchModel branch;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductModel product;

    private int stock;

    public BranchProductModel(BranchModel branch, ProductModel product) {
        this.branch = branch;
        this.product = product;
    }


    

}
