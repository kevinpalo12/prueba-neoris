package com.nequi.prueba.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nequi.prueba.dto.BranchDTO;
import com.nequi.prueba.dto.FranchiseDTO;
import com.nequi.prueba.dto.ProductDTO;
import com.nequi.prueba.models.FranchiseModel;

@Component
public class FranchiseMapper {


    public static FranchiseDTO toDto(FranchiseModel franchise) {
        FranchiseDTO dto = new FranchiseDTO();
        dto.setId(franchise.getId());
        dto.setNombre(franchise.getNombre());

        List<BranchDTO> branchDtos = franchise.getBranchs().stream().map(branch -> {
            BranchDTO bDto = new BranchDTO();
            bDto.setId(branch.getId());
            bDto.setNombre(branch.getNombre());

            List<ProductDTO> productos = branch.getBranchProducts().stream().map(BranchProducts -> {
                ProductDTO pDto = new ProductDTO(BranchProducts.getProduct().getId(),
                        BranchProducts.getProduct().getNombre(), BranchProducts.getStock());

                return pDto;
            }).collect(Collectors.toList());

            bDto.setProductos(productos);
            return bDto;

        }).collect(Collectors.toList());

        dto.setBranchs(branchDtos);
        return dto;
    }
}
