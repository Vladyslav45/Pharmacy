package com.medicine.pharmacy.controller;

import com.medicine.pharmacy.model.*;
import com.medicine.pharmacy.repository.BasketRepository;
import com.medicine.pharmacy.repository.ProductRepository;
import com.medicine.pharmacy.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private CategoryPreparationService categoryPreparationService;

    @Autowired
    private SubCategoryPreparationService subCategoryPreparationService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public ModelAndView showUsers(ModelAndView modelAndView) {
        List<User> users = userService.findAllByRole("ROLE_USER");
        modelAndView.addObject("userList", users);
        modelAndView.setViewName("admin/adminhome");

        return modelAndView;
    }

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
        Basket basket = basketRepository.findByPreparationId(id);
        if (basket.getPreparation().getId().equals(id)){
            modelAndView.addObject("message", "Preparation has on reservation");
            modelAndView.setViewName("home/product");
        } else {
            productService.delete(id);
            modelAndView.setViewName("redirect:/admin/product");
        }

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
