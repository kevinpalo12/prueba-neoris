package com.nequi.prueba.dto;

import java.util.ArrayList;
import java.util.List;

import com.nequi.prueba.models.BranchModel;
import com.nequi.prueba.models.FranchiseModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FranchiseDTO {
    private Long id;
    private String nombre;
    private List<BranchDTO> branchs = new ArrayList<>();

    public static FranchiseDTO fromEntity(FranchiseModel entity) {
        FranchiseDTO dto = new FranchiseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());

        if (entity.getBranchs() != null) {
            for (BranchModel branch : entity.getBranchs()) {
                dto.getBranchs().add(BranchDTO.fromEntity(branch));
            }
        }

        return dto;
    }
}
