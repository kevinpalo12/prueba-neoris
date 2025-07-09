package com.nequi.prueba.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nequi.prueba.dto.BranchDTO;
import com.nequi.prueba.dto.FranchiseDTO;
import com.nequi.prueba.mappers.FranchiseMapper;
import com.nequi.prueba.models.FranchiseModel;
import com.nequi.prueba.services.FranchiseService;

@RestController
@RequestMapping("/api/franchise")
public class FranchiseController {

    @Autowired
    FranchiseService franchiseService;

    @PostMapping("/add")
    public ResponseEntity<FranchiseDTO> add(@RequestBody FranchiseDTO model) {
        try {
            FranchiseModel modelResponse = franchiseService.add(model);
            FranchiseDTO dto = FranchiseMapper.toDto(modelResponse);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);

        }
    }

    @PostMapping("/add_branch")
    public ResponseEntity<FranchiseDTO> addBranch(@RequestParam Long franchise, @RequestBody BranchDTO model) {
        try {
            FranchiseModel modelResponse = franchiseService.addBranch(model);
            FranchiseDTO dto = FranchiseMapper.toDto(modelResponse);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);

        }
    }
}
