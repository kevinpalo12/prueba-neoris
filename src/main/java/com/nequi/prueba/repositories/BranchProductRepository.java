package com.nequi.prueba.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nequi.prueba.models.BranchProductId;
import com.nequi.prueba.models.BranchProductModel;

public interface BranchProductRepository extends JpaRepository<BranchProductModel, BranchProductId> {

    Optional<BranchProductModel> findByBranch_IdAndProduct_Id(Long branchId, Long productId);
    List<BranchProductModel> findAllByBranch_Id(Long branchId);
}
