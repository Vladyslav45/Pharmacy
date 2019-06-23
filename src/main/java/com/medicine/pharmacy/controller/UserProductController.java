package com.medicine.pharmacy.controller;

import com.medicine.pharmacy.model.Basket;
import com.medicine.pharmacy.model.Preparation;
import com.medicine.pharmacy.model.User;
import com.medicine.pharmacy.repository.BasketRepository;
import com.medicine.pharmacy.service.BasketService;
import com.medicine.pharmacy.service.ProductService;
import com.medicine.pharmacy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private BasketService basketService;

    @GetMapping(value = "/userproduct/addbasket/{id}")
    public ModelAndView addProductToBasket(ModelAndView modelAndView, @PathVariable("id") Long id, Principal principal) {
        User user = getUser(principal);
        Preparation preparation = productService.getById(id);
        Basket basket = basketService.findByPreparationId(preparation.getId());

        if (basket == null){
            basketService.addProduct(preparation, user);
        } else if (basket.getPreparation().getName().equals(preparation.getName())){
            int count = basket.getCount();
            double price = basket.getPrice();
            basket.setCount(++count);
            basket.setPrice(price + basket.getPreparation().getPrice());
            basketService.update(basket);
        }
        modelAndView.addObject("msg", "Drug add your basket!");
        modelAndView.setViewName("alert");

        return modelAndView;
    }

    private User getUser(Principal principal) {
        return userService.findById(Long.parseLong(userService.findUserByEmail(principal.getName()).getId().toString()));
    }
}
