package com.nequi.prueba.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nequi.prueba.dto.FranchiseDTO;
import com.nequi.prueba.mappers.FranchiseMapper;
import com.nequi.prueba.models.FranchiseModel;
import com.nequi.prueba.services.BranchService;
import com.nequi.prueba.services.FranchiseService;

@RestController
@RequestMapping("/api/franchise")
public class FranchiseController {

    @Autowired
    FranchiseService franchiseService;

    @Autowired
    BranchService branchService;

    @PostMapping("/add")
    public ResponseEntity<FranchiseDTO> add(@RequestBody FranchiseDTO model) {
        try {
            FranchiseModel modelResponse = franchiseService.add(model);
            FranchiseDTO dto = franchiseService.findFullData(modelResponse.getId());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);

        }
    }

    @PutMapping("/add_branch")
    public ResponseEntity<FranchiseDTO> addBranch(@RequestParam String franchise, @RequestParam String branch) {
        try {
            FranchiseModel modelResponse = branchService.addBranch(franchise, branch);
            FranchiseDTO dto = FranchiseMapper.toDto(modelResponse);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);

        }
    }

    @GetMapping("/max_stock")
    public ResponseEntity<Map<String, ?>> maxStock(@RequestParam String franchise) {
        try {
            Map<String, ?> modelResponse = franchiseService.getMaxStock(franchise);
            return new ResponseEntity<>(modelResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);

        }
    }

    @PostMapping("/updateName")
    public ResponseEntity<FranchiseDTO> updateName(@RequestParam String franchiseName, @RequestParam String newName) {
        try {
            FranchiseModel modelResponse = franchiseService.updateName(franchiseName, newName);
            FranchiseDTO dto = franchiseService.findFullData(modelResponse.getId());
            return new ResponseEntity<>(dto, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);

        }
    }

    @PostMapping("/updateBranchName")
    public ResponseEntity<FranchiseDTO> updateBranchName(@RequestParam String franchiseName,
            @RequestParam String branchName,
            @RequestParam String newName) {
        try {
            FranchiseModel modelResponse = franchiseService.updateBranchName(franchiseName, branchName, newName);
            FranchiseDTO dto = franchiseService.findFullData(modelResponse.getId());
            return new ResponseEntity<>(dto, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
