package com.medicine.pharmacy.service;

import com.medicine.pharmacy.model.Basket;
import com.medicine.pharmacy.model.Preparation;
import com.medicine.pharmacy.model.User;

import java.util.List;

public interface BasketService {

    void addProduct(Preparation preparation, User user);

    List<Basket> findAll(Long id);

    void delete(Long id);

    void deleteItemFromBasket(Long id);

    Basket update(Basket basket);

    Basket findByPreparationId(Long id);
}
