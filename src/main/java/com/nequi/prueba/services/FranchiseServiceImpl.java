package com.nequi.prueba.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nequi.prueba.dto.BranchDTO;
import com.nequi.prueba.dto.FranchiseDTO;
import com.nequi.prueba.dto.ProductDTO;
import com.nequi.prueba.models.BranchModel;
import com.nequi.prueba.models.BranchProductModel;
import com.nequi.prueba.models.FranchiseModel;
import com.nequi.prueba.models.ProductModel;
import com.nequi.prueba.repositories.BranchProductRepository;
import com.nequi.prueba.repositories.BranchRepository;
import com.nequi.prueba.repositories.FranchiseRepository;
import com.nequi.prueba.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FranchiseServiceImpl implements FranchiseService {

    @Autowired
    FranchiseRepository franchiseRepository;

    @Autowired
    BranchProductRepository branchProductRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Override
    public FranchiseModel add(FranchiseDTO dto) throws Exception {
        if (franchiseRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new Exception("Franquicia ya existe");
        }

        FranchiseModel franchise = new FranchiseModel(dto.getNombre());
        franchise = franchiseRepository.save(franchise);

        List<BranchModel> branchsTemp = new ArrayList<>();

        for (BranchDTO branchDto : dto.getBranchs()) {
            Optional<BranchModel> existingBranch = branchRepository.findByNombreAndFranchise_Id(
                    branchDto.getNombre(), franchise.getId());
            if (existingBranch.isPresent()) {
                branchsTemp.add(existingBranch.get());
            } else {
                BranchModel branch = new BranchModel(branchDto.getNombre(), franchise);
                branch = branchRepository.save(branch);
                branchsTemp.add(branch);
            }
        }

        for (int i = 0; i < dto.getBranchs().size(); i++) {
            BranchDTO branchDto = dto.getBranchs().get(i);
            BranchModel branch = branchsTemp.get(i);

            for (ProductDTO prodDto : branchDto.getProductos()) {
                ProductModel producto = productService.findOrSave(prodDto);

                BranchProductModel branchProduct = branchProductRepository
                        .findByBranch_IdAndProduct_Id(branch.getId(), producto.getId())
                        .orElse(new BranchProductModel(branch, producto));

                branchProduct.setStock(prodDto.getStock());
                branchProductRepository.save(branchProduct);
            }
        }

        franchise.setBranchs(branchsTemp);
        franchise = franchiseRepository.findById(franchise.getId()).orElseThrow();
        return franchise;
    }

    @Override
    public FranchiseDTO findFullData(Long id) throws Exception {
        FranchiseModel franchise = franchiseRepository.findById(id).orElseThrow();
        FranchiseDTO dto = FranchiseDTO.fromEntity(franchise);

        for (int i = 0; i < franchise.getBranchs().size(); i++) {
            BranchModel branch = franchise.getBranchs().get(i);
            List<BranchProductModel> products = branchProductRepository.findAllByBranch_Id(branch.getId());
            List<ProductDTO> productDTOs = new ArrayList<>();
            for (BranchProductModel product : products) {
                ProductModel productoSinStock = productRepository.findByNombre(product.getProduct().getNombre())
                        .orElse(null);
                productDTOs.add(ProductDTO.fromEntity(productoSinStock, product.getStock()));
            }
            dto.getBranchs().get(i).setProductos(productDTOs);
        }
        return dto;
    }

    @Override
    public FranchiseModel findByName(String name) throws Exception {
        return franchiseRepository.findByNombre(name).orElseThrow();
    }

    @Override
    public FranchiseModel deleteProcuctToBranch(String franchiseName, String branchName, String productName)
            throws Exception {
        ProductModel productMode = new ProductModel();
        productMode.setNombre(productName);
        ProductModel productModel = productService.findOrSave(ProductDTO.fromEntity(productMode));

        FranchiseModel franchiseModel = findByName(franchiseName);

        BranchModel branch = branchRepository.findByNombreAndFranchise_Id(
                branchName, franchiseModel.getId()).orElseThrow();

        List<BranchProductModel> products = branchProductRepository.findAllByBranch_Id(branch.getId());
        Boolean existe = validateProduct(products, productName);

        if (!existe) {
            throw new Exception("El producto no esta registrado en la franquisia");
        }

        BranchProductModel branchProduct = branchProductRepository
                .findByBranch_IdAndProduct_Id(branch.getId(), productModel.getId())
                .orElse(null);
        if (branchProduct != null)
            branchProductRepository.deleteById(branchProduct.getId());

        return franchiseModel;

    }

    @Override
    public FranchiseModel addProductToBranch(String franchiseName, String branchName, String productName)
            throws Exception {

        ProductModel productModel = productService.findOrSave(productName);
        FranchiseModel franchiseModel = findByName(franchiseName);

        BranchModel branch = branchRepository.findByNombreAndFranchise_Id(
                branchName, franchiseModel.getId()).orElseThrow();

        List<BranchProductModel> products = branchProductRepository.findAllByBranch_Id(branch.getId());

        for (BranchProductModel branchProductModel : products) {
            if (branchProductModel.getProduct().getNombre().equals(productName)) {
                throw new Exception("El producto ya se encuentra registrado en la sucursal");
            }
        }

        BranchProductModel branchProduct = branchProductRepository
                .findByBranch_IdAndProduct_Id(branch.getId(), productModel.getId())
                .orElse(new BranchProductModel(branch, productModel));

        branchProductRepository.save(branchProduct);

        return franchiseModel;
    }

    @Override
    public FranchiseModel updateProductStock(String franchiseName, String branchName, String productName, int stock)
            throws Exception {

        ProductModel productModel = productService.findByName(productName);
        FranchiseModel franchiseModel = findByName(franchiseName);

        BranchModel branch = branchRepository.findByNombreAndFranchise_Id(
                branchName, franchiseModel.getId()).orElseThrow();

        List<BranchProductModel> products = branchProductRepository.findAllByBranch_Id(branch.getId());

        Boolean existe = validateProduct(products, productName);

        if (!existe) {
            throw new Exception("El producto no existe o no esta registrado en la franquisia");
        }

        BranchProductModel branchProduct = branchProductRepository
                .findByBranch_IdAndProduct_Id(branch.getId(), productModel.getId())
                .orElse(null);

        if (branchProduct == null) {
            throw new Exception("El producto no existe o no esta registrado en la franquisia");
        }
        branchProduct.setStock(stock);
        branchProductRepository.save(branchProduct);

        return franchiseModel;

    }

    private Boolean validateProduct(List<BranchProductModel> products, String productName) {
        Boolean existe = false;
        for (BranchProductModel branchProductModel : products) {

            if (branchProductModel.getProduct().getNombre().equals(productName)) {
                existe = true;
            }
        }
        return existe;

    }

    @Override
    public Map<String, ?> getMaxStock(String franchiseName) throws Exception {

        Map<String, Object> response = new HashMap<>();
        FranchiseModel franchiseModel = findByName(franchiseName);

        for (BranchModel branchTemp : franchiseModel.getBranchs()) {
            BranchModel branch = branchRepository.findByNombreAndFranchise_Id(
                    branchTemp.getNombre(), franchiseModel.getId()).orElseThrow();
            List<BranchProductModel> products = branchProductRepository.findAllByBranch_Id(branch.getId());

            Optional<BranchProductModel> maxProductModel = products.stream()
                    .max(Comparator.comparingInt(BranchProductModel::getStock));

            if (maxProductModel.isPresent()) {
                BranchProductModel model = maxProductModel.get();
                Map<String, Object> producto = new HashMap<>();
                producto.put("stock", model.getStock());
                producto.put("nombre", model.getProduct().getNombre());
                response.put(branchTemp.getNombre(), producto);
            }
        }

        return response;
    }

    @Override
    public FranchiseModel updateName(String franchiseName, String NewName) throws Exception {
        Optional<FranchiseModel> franchiseModelOpt = franchiseRepository.findByNombre(franchiseName);

        if (!franchiseModelOpt.isPresent()) {
            throw new Exception("La franquisia con el nombre " + franchiseName + " no existe");
        }

        FranchiseModel franchiseModel = franchiseModelOpt.get();
        franchiseModel.setNombre(NewName);
        return franchiseRepository.save(franchiseModel);

    }
}
