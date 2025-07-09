package com.nequi.prueba.services;

import com.nequi.prueba.dto.BranchDTO;
import com.nequi.prueba.dto.FranchiseDTO;
import com.nequi.prueba.models.FranchiseModel;

public interface FranchiseService {
    public FranchiseModel add(FranchiseDTO dto) throws Exception;

    public FranchiseModel addBranch(Long id, BranchDTO dto) throws Exception;
}
