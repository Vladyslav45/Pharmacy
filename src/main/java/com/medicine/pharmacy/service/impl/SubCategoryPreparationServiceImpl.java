package com.medicine.pharmacy.service.impl;

import com.medicine.pharmacy.model.SubCategoryPreparation;
import com.medicine.pharmacy.repository.SubCategoryPreparationRepository;
import com.medicine.pharmacy.service.SubCategoryPreparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryPreparationServiceImpl implements SubCategoryPreparationService {

    @Autowired
    private SubCategoryPreparationRepository subCategoryPreparationRepository;

    @Override
    public SubCategoryPreparation findById(Long id) {
        return subCategoryPreparationRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<SubCategoryPreparation> findAll() {
        return subCategoryPreparationRepository.findAll();
    }

    @Override
    public List<SubCategoryPreparation> findAllByCategoryId(Long id) {
        return subCategoryPreparationRepository.findAllByCategoryPreparationId(id);
    }
}
