package com.nequi.prueba.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.nequi.prueba.repositories.BranchRepository;
import com.nequi.prueba.repositories.FranchiseRepository;
import com.nequi.prueba.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FranchiseServiceImpl implements FranchiseService {

    @Autowired
    FranchiseRepository franchiseRepository;

    @Autowired
    BranchProductRepository branchProductRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Override
    public FranchiseModel add(FranchiseDTO dto) throws Exception {
        if (franchiseRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new Exception("Franquicia ya existe");
        }

        FranchiseModel franchise = new FranchiseModel(dto.getNombre());
        franchise = franchiseRepository.save(franchise);

        List<BranchModel> branchsTemp = new ArrayList<>();

        for (BranchDTO branchDto : dto.getBranchs()) {
            Optional<BranchModel> existingBranch = branchRepository.findByNombreAndFranchise_Id(
                    branchDto.getNombre(), franchise.getId());

            if (existingBranch.isPresent()) {
                branchsTemp.add(existingBranch.get());
            } else {
                BranchModel branch = new BranchModel(branchDto.getNombre(), franchise);
                branch = branchRepository.save(branch);
                branchsTemp.add(branch);
            }
        }

        for (int i = 0; i < dto.getBranchs().size(); i++) {
            BranchDTO branchDto = dto.getBranchs().get(i);
            BranchModel branch = branchsTemp.get(i);

            for (ProductDTO prodDto : branchDto.getProductos()) {
                ProductModel producto = productService.findOrSave(prodDto);

                BranchProductModel branchProduct = branchProductRepository
                        .findByBranch_IdAndProduct_Id(branch.getId(), producto.getId())
                        .orElse(new BranchProductModel(branch, producto));

                branchProduct.setStock(prodDto.getStock());
                branchProductRepository.save(branchProduct);
            }
        }

        franchise.setBranchs(branchsTemp);
        franchise = franchiseRepository.findById(franchise.getId()).orElseThrow();
        return franchise;
    }



    @Override
    public FranchiseDTO findFullData(Long id) throws Exception {
        FranchiseModel franchise = franchiseRepository.findById(id).orElseThrow();
        FranchiseDTO dto = FranchiseDTO.fromEntity(franchise);

        for (int i = 0; i < franchise.getBranchs().size(); i++) {
            BranchModel branch = franchise.getBranchs().get(i);
            List<BranchProductModel> products = branchProductRepository.findAllByBranch_Id(branch.getId());
            List<ProductDTO> productDTOs = new ArrayList<>();
            for (BranchProductModel product : products) {
                ProductModel productoSinStock = productRepository.findByNombre(product.getProduct().getNombre())
                        .orElse(null);
                productDTOs.add(ProductDTO.fromEntity(productoSinStock, product.getStock()));
            }
            dto.getBranchs().get(i).setProductos(productDTOs);
        }
        return dto;
    }

}
