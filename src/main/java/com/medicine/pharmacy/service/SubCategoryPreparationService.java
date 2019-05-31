package com.medicine.pharmacy.service;

import com.medicine.pharmacy.model.SubCategoryPreparation;

import java.util.List;

public interface SubCategoryPreparationService {
    SubCategoryPreparation findById(Long id);
    List<SubCategoryPreparation> findAll();
    List<SubCategoryPreparation> findAllByCategoryId(Long id);
}
