package com.medicine.pharmacy.service.impl;

import com.medicine.pharmacy.model.Preparation;
import com.medicine.pharmacy.repository.ProductRepository;
import com.medicine.pharmacy.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addProduct(Preparation preparation) {
         productRepository.save(preparation);
    }

    @Override
    public List<Preparation> findAll() {
       return productRepository.findAll();
    }

    @Override
    public List<Preparation> findAllBySubCategoryId(Long id) {
        return productRepository.findAllBySubCategoryPreparationId(id);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Preparation getById(Long id) {
        return productRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Preparation update(Preparation preparation) {
        return productRepository.save(preparation);
    }

}
