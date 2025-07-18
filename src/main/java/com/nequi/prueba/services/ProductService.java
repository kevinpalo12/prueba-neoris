package com.nequi.prueba.services;

import com.nequi.prueba.dto.ProductDTO;
import com.nequi.prueba.models.ProductModel;

public interface ProductService {

      public ProductModel findOrSave(ProductDTO model);

      public ProductModel findOrSave(String product);

      public ProductModel findByName(String name) throws Exception;

}
