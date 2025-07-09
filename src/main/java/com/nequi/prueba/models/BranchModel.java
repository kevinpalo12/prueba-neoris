package com.nequi.prueba.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "branch")
public class BranchModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "franchise_id")
    private FranchiseModel franchise;

    @JsonIgnore
    @OneToMany(mappedBy = "branch", orphanRemoval = true)
    private List<BranchProductModel> branchProducts = new ArrayList<>();

    public BranchModel(String nombre, FranchiseModel franchise) {
        this.nombre = nombre;
        this.franchise = franchise;
    }

}
