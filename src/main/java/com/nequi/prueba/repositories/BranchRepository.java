package com.nequi.prueba.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nequi.prueba.models.BranchModel;

public interface BranchRepository extends JpaRepository<BranchModel, Long>{
    
    Optional<BranchModel> findByNombreAndFranchise_Id(String nombre, Long franchiseId);

}
