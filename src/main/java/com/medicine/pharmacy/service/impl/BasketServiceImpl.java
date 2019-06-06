package com.medicine.pharmacy.service.impl;

import com.medicine.pharmacy.model.Basket;
import com.medicine.pharmacy.model.Preparation;
import com.medicine.pharmacy.model.User;
import com.medicine.pharmacy.repository.BasketRepository;
import com.medicine.pharmacy.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Override
    public void addProduct(Preparation preparation, User user) {
        Basket basket = new Basket();
        basket.setCount(1);
        basket.setPrice(preparation.getPrice());
        basket.setUser(user);
        basket.setPreparation(preparation);

        basketRepository.save(basket);
    }

    @Override
    public List<Basket> findAll(Long id) {
        return basketRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        basketRepository.deleteAllByUserId(id);
    }

    @Override
    public void deleteItemFromBasket(Long id) {
        basketRepository.deleteById(id);
    }

    @Override
    public Basket update(Basket basket) {
        return basketRepository.save(basket);
    }

    @Override
    public Basket findByPreparationId(Long id) {
        return basketRepository.findByPreparationId(id);
    }
}
