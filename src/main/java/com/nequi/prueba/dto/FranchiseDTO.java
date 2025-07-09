package com.nequi.prueba.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FranchiseDTO {
    private Long id;
    private String nombre;
    private List<BranchDTO> branchs;

    // getters y setters
}

