package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping({"/", "/user"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ModelAndView index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        ModelAndView modelAndView = new ModelAndView("users/index");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
