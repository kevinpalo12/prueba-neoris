package com.nequi.prueba.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nequi.prueba.dto.BranchDTO;
import com.nequi.prueba.dto.FranchiseDTO;
import com.nequi.prueba.dto.ProductDTO;
import com.nequi.prueba.models.BranchModel;
import com.nequi.prueba.models.BranchProductModel;
import com.nequi.prueba.models.FranchiseModel;
import com.nequi.prueba.models.ProductModel;
import com.nequi.prueba.repositories.BranchProductRepository;
import com.nequi.prueba.repositories.FranchiseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FranchiseServiceImpl implements FranchiseService {

    @Autowired
    FranchiseRepository franchiseRepository;

    @Autowired
    BranchProductRepository branchProductRepository;

    @Autowired
    ProductService productService;

    @Override
    public FranchiseModel add(FranchiseDTO dto) throws Exception {
        FranchiseModel franchise = franchiseRepository
                .findByNombre(dto.getNombre()).orElse(null);
        if (franchise != null) {
            throw new Exception("Franquisia ya existe");
        }
        franchise = new FranchiseModel(dto.getNombre());
        for (BranchDTO branchDto : dto.getBranchs()) {

            final BranchModel branchTemp = franchise.getBranchs().stream().filter(var -> var instanceof BranchModel)
                    .map(var -> (BranchModel) var).filter(temp -> branchDto.getNombre().equals(temp.getNombre()))
                    .findFirst().orElse(new BranchModel(branchDto.getNombre(), franchise));

            System.out.println(branchTemp.getNombre());
            List<BranchProductModel> productsOfBranch = branchProductRepository.findAllByBranch_Id(branchTemp.getId());

            for (ProductDTO prodDto : branchDto.getProductos()) {

                ProductModel producto = productService.findOrSave(prodDto);
                BranchProductModel branchProduct = branchProductRepository
                        .findByBranch_IdAndProduct_Id(branchTemp.getId(), producto.getId())
                        .orElse(new BranchProductModel(branchTemp, producto));

                branchProduct.setStock(prodDto.getStock());
                productsOfBranch.add(branchProductRepository.save(branchProduct));

            }

            branchTemp.setBranchProducts(productsOfBranch);
            franchise.getBranchs().add(branchTemp);
        }

        return franchiseRepository.save(franchise);
    }

}
