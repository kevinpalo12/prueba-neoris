package com.nequi.prueba.services;

import com.nequi.prueba.dto.BranchDTO;
import com.nequi.prueba.models.FranchiseModel;

public interface BranchService {

    public FranchiseModel addBranch(String FranchiseName, String branchName) throws Exception;
}
