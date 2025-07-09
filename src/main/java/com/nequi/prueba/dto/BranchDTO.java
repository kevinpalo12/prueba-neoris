package com.nequi.prueba.dto;

import java.util.ArrayList;
import java.util.List;

import com.nequi.prueba.models.BranchModel;
import com.nequi.prueba.models.BranchProductModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BranchDTO {
    private Long id;
    private String nombre;
    private List<ProductDTO> productos = new ArrayList<>();

    public static BranchDTO fromEntity(BranchModel entity) {
        BranchDTO dto = new BranchDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());

        if (entity.getBranchProducts() != null) {
            for (BranchProductModel bp : entity.getBranchProducts()) {
                ProductDTO prodDto = ProductDTO.fromEntity(bp.getProduct());
                prodDto.setStock(bp.getStock());
                dto.getProductos().add(prodDto);
            }
        }

        return dto;
    }

}
