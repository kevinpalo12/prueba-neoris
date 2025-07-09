package com.nequi.prueba.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nequi.prueba.models.FranchiseModel;

public interface FranchiseRepository extends JpaRepository<FranchiseModel, Long> {
    Optional<FranchiseModel> findByNombre(String nombre);
}
