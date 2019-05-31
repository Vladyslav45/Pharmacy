package com.medicine.pharmacy.controller;


import com.medicine.pharmacy.model.Preparation;
import com.medicine.pharmacy.model.SubCategoryPreparation;
import com.medicine.pharmacy.service.ProductService;
import com.medicine.pharmacy.service.SubCategoryPreparationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubCategoryController {

    @Autowired
    private SubCategoryPreparationService subCategoryPreparationService;
    @Autowired
    private ProductService productService;


    @GetMapping(value = "/subcategory/{id}")
    public ModelAndView showPreparationBySubCategory(ModelAndView modelAndView, @PathVariable("id") Long id){
        SubCategoryPreparation subCategoryPreparation = subCategoryPreparationService.findById(id);
        List<Preparation> preparations = productService.findAllBySubCategoryId(subCategoryPreparation.getId());

        modelAndView.addObject("preparationList", preparations);
        modelAndView.setViewName("user/product");

        return modelAndView;
    }
}
