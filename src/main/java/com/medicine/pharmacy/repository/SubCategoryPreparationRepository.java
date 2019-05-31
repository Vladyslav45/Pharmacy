package com.medicine.pharmacy.repository;

import com.medicine.pharmacy.model.Preparation;
import com.medicine.pharmacy.model.SubCategoryPreparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryPreparationRepository extends JpaRepository<SubCategoryPreparation, Long> {
    List<SubCategoryPreparation> findAllByCategoryPreparationId(Long id);
}
