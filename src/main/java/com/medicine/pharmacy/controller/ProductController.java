package com.medicine.pharmacy.controller;

import com.medicine.pharmacy.model.CategoryPreparation;
import com.medicine.pharmacy.model.Preparation;
import com.medicine.pharmacy.model.SubCategoryPreparation;
import com.medicine.pharmacy.repository.ProductRepository;
import com.medicine.pharmacy.service.CategoryPreparationService;
import com.medicine.pharmacy.service.ProductService;
import com.medicine.pharmacy.service.SubCategoryPreparationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryPreparationService categoryPreparationService;

    @Autowired
    private SubCategoryPreparationService subCategoryPreparationService;

    @GetMapping(value = "/product")
    public ModelAndView product() {
        ModelAndView modelAndView = new ModelAndView();
        List<Preparation> list = productService.findAll();
        modelAndView.addObject("productList", list);
        modelAndView.setViewName("home/product");
        return modelAndView;
    }

    @GetMapping(value = "/product/add")
    public ModelAndView addProduct() {
        ModelAndView modelAndView = new ModelAndView();
        List<CategoryPreparation> categoryPreparations = categoryPreparationService.findAll();
        modelAndView.addObject("listCategory", categoryPreparations);
//        categoryPreparations.forEach(categoryPreparation -> {
//            List<SubCategoryPreparation> subCategoryPreparation = subCategoryPreparationService.findAllByCategoryId(categoryPreparation.getId());
//            modelAndView.addObject("listSubCategory", subCategoryPreparation);
//        });
        List<SubCategoryPreparation> subCategoryPreparations = subCategoryPreparationService.findAll();
        modelAndView.addObject("listSubCategory", subCategoryPreparations);
        modelAndView.addObject("preparation", new Preparation());
        modelAndView.setViewName("product/tableproduct");

        return modelAndView;
    }

    @PostMapping(value = "/product/add")
    public ModelAndView saveProduct(Preparation preparation) {
        ModelAndView modelAndView = new ModelAndView();

        productService.addProduct(preparation);
        modelAndView.addObject("msg", "Preparation has been add successfully!");
        modelAndView.addObject("preparation", preparation);
        modelAndView.setViewName("product/tableproduct");
        return modelAndView;
    }

    @GetMapping(value = "/product/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();

        productService.delete(id);
        modelAndView.setViewName("redirect:/product");

        return modelAndView;
    }

    @GetMapping(value = "/product/update/{id}")
    public ModelAndView editForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Preparation preparation = productService.getById(id);
        List<CategoryPreparation> categoryPreparations = categoryPreparationService.findAll();
        List<SubCategoryPreparation> subCategoryPreparations = subCategoryPreparationService.findAll();
        modelAndView.addObject("listCategory", categoryPreparations);
        modelAndView.addObject("listSubCategory", subCategoryPreparations);
        modelAndView.addObject("product", preparation);
        modelAndView.setViewName("product/updateproduct");

        return modelAndView;
    }

    @PostMapping(value = "/product/update/{id}")
    public ModelAndView updatePreparation(Preparation preparation) {
        ModelAndView modelAndView = new ModelAndView();

        Preparation updatePreparation = productService.update(preparation);
        modelAndView.addObject("product", updatePreparation);
        modelAndView.setViewName("redirect:/product");

        return modelAndView;
    }
}
