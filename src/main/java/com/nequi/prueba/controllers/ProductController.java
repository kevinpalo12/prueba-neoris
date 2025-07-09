package com.nequi.prueba.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nequi.prueba.dto.FranchiseDTO;
import com.nequi.prueba.models.FranchiseModel;
import com.nequi.prueba.services.FranchiseService;
import com.nequi.prueba.services.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    FranchiseService franchiseService;

    @PutMapping("/add")
    public ResponseEntity<FranchiseDTO> addProduct(@RequestParam String franchise, @RequestParam String branch,
            @RequestParam String product) {
        try {
            FranchiseModel franchiseModel = franchiseService.addProductToBranch(franchise, branch, product);
            FranchiseDTO dto = franchiseService.findFullData(franchiseModel.getId());
            return new ResponseEntity<>(dto, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);

        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<FranchiseDTO> deleteProduct(@RequestParam String franchise, @RequestParam String branch,
            @RequestParam String product) {
        try {
            FranchiseModel franchiseModel = franchiseService.deleteProcuctToBranch(franchise, branch, product);
            FranchiseDTO dto = franchiseService.findFullData(franchiseModel.getId());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);

        }
    }


    @PutMapping("/updateStock")
    public ResponseEntity<FranchiseDTO> updateStock(@RequestParam String franchise, @RequestParam String branch,
            @RequestParam String product, @RequestParam int stock) {
        try {
            FranchiseModel franchiseModel = franchiseService.updateProductStock(franchise, branch, product,stock);
            FranchiseDTO dto = franchiseService.findFullData(franchiseModel.getId());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);

        }
    }
}
