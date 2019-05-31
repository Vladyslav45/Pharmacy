package com.medicine.pharmacy.repository;

import com.medicine.pharmacy.model.Preparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Preparation, Long> {
    List<Preparation> findAllBySubCategoryPreparationId(Long id);
}
