package com.medicine.pharmacy.repository;

import com.medicine.pharmacy.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findAllByUserId(Long id);
    Long deleteAllByUserId(Long id);
    Basket findByPreparationId(Long id);
}
