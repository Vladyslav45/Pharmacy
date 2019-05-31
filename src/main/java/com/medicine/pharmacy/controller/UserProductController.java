package com.medicine.pharmacy.controller;

import com.medicine.pharmacy.model.Preparation;
import com.medicine.pharmacy.model.User;
import com.medicine.pharmacy.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ModelAndView addProductToBasket(ModelAndView modelAndView, @PathVariable("id") Long id, Principal principal){
        Preparation preparation = productService.getById(id);
        User user = getUser(principal);

        basketService.addProduct(preparation,user);
        modelAndView.setViewName("user/product");

        return modelAndView;
    }

    private User getUser(Principal principal){
        return userService.findById(Long.parseLong(userService.findUserByEmail(principal.getName()).getId().toString()));
    }
}