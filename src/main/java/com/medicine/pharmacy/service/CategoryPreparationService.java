package com.medicine.pharmacy.service;

import com.medicine.pharmacy.model.CategoryPreparation;
import com.medicine.pharmacy.model.SubCategoryPreparation;

import java.util.List;

public interface CategoryPreparationService {
    List<CategoryPreparation> findAll();
    CategoryPreparation getById(Long id);

}
