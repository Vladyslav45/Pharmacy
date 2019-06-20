package com.medicine.pharmacy.controller;

import com.medicine.pharmacy.config.JavaSenderMail;
import com.medicine.pharmacy.model.User;
import com.medicine.pharmacy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaSenderMail javaSenderMail;

    @GetMapping(value = {"/", "/login"})
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("log/login");
        return modelAndView;
    }

    @GetMapping(value = "/signup")
    public ModelAndView showFormRegistration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("log/signup");

        return modelAndView;
    }

    @PostMapping(value = "/signup")
    public ModelAndView createUser(@Valid User user, BindingResult result) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        User createUser = userService.findUserByEmail(user.getEmail());

        if (createUser != null) {
            result.rejectValue("email", "error.log", "This email already exists!");
        }

        if (result.hasErrors()) {
            modelAndView.setViewName("log/signup");
        } else {
            userService.saveUser(user);
            javaSenderMail.sendEmail(user.getEmail());
            modelAndView.addObject("msg", "User has been registered successfully!");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("redirect:/login");
        }

        return modelAndView;
    }

    @GetMapping(value = "/home")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName());

        modelAndView.addObject("userName", user.getName());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("home/home");

        return modelAndView;
    }

    @GetMapping(value = "/update/{id}")
    public ModelAndView showFormUpdate(ModelAndView modelAndView, @PathVariable("id") Long id){
        User user = userService.findById(id);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/updateform");
        return modelAndView;
    }

    @PostMapping(value = "/update/{id}")
    public ModelAndView update(ModelAndView modelAndView, User user){
        User updateUser = userService.update(user);
        modelAndView.addObject("user", updateUser);
        modelAndView.setViewName("redirect:/home");

        return modelAndView;
    }
}
