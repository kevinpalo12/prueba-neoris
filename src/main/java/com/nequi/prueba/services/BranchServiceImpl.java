package com.nequi.prueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nequi.prueba.models.BranchModel;
import com.nequi.prueba.models.FranchiseModel;
import com.nequi.prueba.repositories.FranchiseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    @Autowired
    FranchiseRepository franchiseRepository;

    @Override
    public FranchiseModel addBranch(String franchiseName, String branchName) throws Exception {
        if (!franchiseRepository.findByNombre(franchiseName).isPresent()) {
            throw new Exception("Franquicia no existe");
        }

        FranchiseModel franchise = franchiseRepository.findByNombre(franchiseName).orElseThrow();

        for (BranchModel branch : franchise.getBranchs()) {
            if (branch.getNombre().equals(branchName)) {
                throw new Exception("Sucursal ya se encuentra registrada");
            }
        }

        BranchModel branchModel = new BranchModel(branchName, franchise);
        franchise.getBranchs().add(branchModel);

        return franchiseRepository.save(franchise);
    }

}
