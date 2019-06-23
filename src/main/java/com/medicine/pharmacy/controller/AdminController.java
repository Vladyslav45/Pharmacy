package com.medicine.pharmacy.controller;

import com.medicine.pharmacy.config.JavaSenderMail;
import com.medicine.pharmacy.model.*;
import com.medicine.pharmacy.repository.BasketRepository;
import com.medicine.pharmacy.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BasketService basketService;

    @Autowired
    private CategoryPreparationService categoryPreparationService;

    @Autowired
    private SubCategoryPreparationService subCategoryPreparationService;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaSenderMail javaSenderMail;

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
        modelAndView.addObject("msg", "Drug has been add successfully!");
        modelAndView.addObject("preparation", preparation);
        modelAndView.setViewName("product/tableproduct");
        return modelAndView;
    }

    @GetMapping(value = "/product/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Basket basket = basketService.findByPreparationId(id);
        if (basket == null){
            productService.delete(id);
            modelAndView.setViewName("redirect:/admin/product");
        } else {
            modelAndView.addObject("message", "Drug has on reservation");
            modelAndView.setViewName("home/product");
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
        modelAndView.addObject("msg", "Drug has been successfully update!");
        modelAndView.addObject("product", updatePreparation);
        modelAndView.setViewName("product/updateproduct");

        return modelAndView;
    }

    @GetMapping(value = "/add")
    public ModelAndView showFormAddAdministration(ModelAndView modelAndView) {
        User admin = new User();
        modelAndView.addObject("admin", admin);
        modelAndView.setViewName("admin/addadmin");

        return modelAndView;
    }

    @PostMapping(value = "/add")
    public ModelAndView createAdmin(ModelAndView modelAndView, @Valid User admin, BindingResult result) throws Exception {

        User createAdmin = userService.findUserByEmail(admin.getEmail());

        if (createAdmin != null) {
            result.rejectValue("email", "error.log", "This email already exists!");
        }

        if (result.hasErrors()) {
            modelAndView.setViewName("admin/addadmin");
        } else {
            userService.saveAdmin(admin);
            javaSenderMail.sendEmail(admin.getEmail());
            modelAndView.addObject("msg", "Admin has been registered successfully!");
            modelAndView.addObject("admin", new User());
            modelAndView.setViewName("admin/addadmin");
        }

        return modelAndView;
    }
}
