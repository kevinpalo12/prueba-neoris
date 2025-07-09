package com.nequi.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String nombre;
    private int stock;
}
