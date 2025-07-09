package com.nequi.prueba.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BranchDTO {
    private Long id;
    private String nombre;
    private List<ProductDTO> productos;

}
