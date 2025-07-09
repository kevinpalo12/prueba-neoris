package com.nequi.prueba.dto;

import com.nequi.prueba.models.ProductModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String nombre;
    private int stock;

    public static ProductDTO fromEntity(ProductModel entity, int stock) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setStock(stock);
        return dto;
    }

    public static ProductDTO fromEntity(ProductModel entity) {
        return fromEntity(entity, 0);
    }
}
