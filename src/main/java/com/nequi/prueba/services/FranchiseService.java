package com.nequi.prueba.services;

import java.util.Map;

import com.nequi.prueba.dto.FranchiseDTO;
import com.nequi.prueba.models.FranchiseModel;

public interface FranchiseService {
    public FranchiseModel add(FranchiseDTO dto) throws Exception;

    public FranchiseModel findByName(String name) throws Exception;

    public FranchiseDTO findFullData(Long id) throws Exception;

    public FranchiseModel addProductToBranch(String franchiseName, String branchName, String productName)
            throws Exception;

    public FranchiseModel deleteProcuctToBranch(String franchiseName, String branchName, String productName)
            throws Exception;

    public FranchiseModel updateProductStock(String franchiseName, String branchName, String productName, int stock)
            throws Exception;

    public Map<String, ?> getMaxStock(String franchiseName)
            throws Exception;

    public FranchiseModel updateName(String franchiseName, String NewName)
            throws Exception;
}
