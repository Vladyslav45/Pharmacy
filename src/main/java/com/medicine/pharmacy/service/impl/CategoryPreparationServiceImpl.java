package com.medicine.pharmacy.service.impl;

import com.medicine.pharmacy.model.CategoryPreparation;
import com.medicine.pharmacy.model.SubCategoryPreparation;
import com.medicine.pharmacy.repository.CategoryPreparationRepository;
import com.medicine.pharmacy.service.CategoryPreparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryPreparationServiceImpl implements CategoryPreparationService {

    @Autowired
    private CategoryPreparationRepository categoryPreparationRepository;

    @Override
    public List<CategoryPreparation> findAll() {
        return categoryPreparationRepository.findAll();
    }

    @Override
    public CategoryPreparation getById(Long id) {
        return categoryPreparationRepository.findById(id)
                .orElse(null);
    }
}
