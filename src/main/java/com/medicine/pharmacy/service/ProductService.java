package com.medicine.pharmacy.service;

import com.medicine.pharmacy.model.Preparation;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    void addProduct(Preparation preparation);
    List<Preparation> findAll();
    List<Preparation> findAllBySubCategoryId(Long id);
    void delete(Long id);
    Preparation getById(Long id);
    Preparation update(Preparation preparation);
}
