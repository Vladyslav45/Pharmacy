package com.medicine.pharmacy.controller;

import com.medicine.pharmacy.model.CategoryPreparation;
import com.medicine.pharmacy.model.SubCategoryPreparation;
import com.medicine.pharmacy.service.CategoryPreparationService;
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
public class CategoryController {


    @Autowired
    private CategoryPreparationService categoryPreparationService;

    @Autowired
    private SubCategoryPreparationService subCategoryPreparationService;


    @GetMapping(value = "/category")
    public ModelAndView showAllCategory(ModelAndView modelAndView) {
        List<CategoryPreparation> categoryPreparationList = categoryPreparationService.findAll();
        modelAndView.addObject("categoryList", categoryPreparationList);
        modelAndView.setViewName("user/categoryproduct");

        return modelAndView;
    }

    @GetMapping(value = "/category/{id}")
    public ModelAndView showSubCategoryByCategory(ModelAndView modelAndView, @PathVariable("id") Long id) {
        CategoryPreparation categoryPreparation = categoryPreparationService.getById(id);
        List<SubCategoryPreparation> subCategoryPreparations = subCategoryPreparationService.findAllByCategoryId(categoryPreparation.getId());

        modelAndView.addObject("subcategoryList", subCategoryPreparations);
        modelAndView.setViewName("user/subcategoryproduct");

        return modelAndView;
    }

}
