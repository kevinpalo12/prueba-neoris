package com.nequi.prueba.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nequi.prueba.models.ProductModel;

public interface  ProductRepository extends JpaRepository<ProductModel, Long>{
    
    Optional<ProductModel> findByNombre(String nombre);
}
