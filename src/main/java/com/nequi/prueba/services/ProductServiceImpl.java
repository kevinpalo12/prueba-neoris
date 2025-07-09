package com.nequi.prueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nequi.prueba.dto.ProductDTO;
import com.nequi.prueba.models.ProductModel;
import com.nequi.prueba.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductModel findOrSave(ProductDTO dto) {

        return productRepository
                .findByNombre(dto.getNombre())
                .orElseGet(() -> {
                    ProductModel nuevo = new ProductModel();
                    System.out.println(dto.getNombre());
                    nuevo.setNombre(dto.getNombre());
                    return productRepository.save(nuevo);
                });
    }

}
