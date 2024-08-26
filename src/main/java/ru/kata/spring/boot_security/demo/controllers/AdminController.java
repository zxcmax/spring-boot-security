package ru.kata.spring.boot_security.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("admin/index");
        modelAndView.addObject("users", userService.getAllUsers());
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addUser() {
        ModelAndView modelAndView = new ModelAndView("admin/edit_user");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("roles", roleService.getAllRoles());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addUser(@Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("admin/edit_user");
            modelAndView.addObject("roles", roleService.getAllRoles());
            return modelAndView;
        }
        userService.saveUser(user);
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/edit")
    public ModelAndView editUser(@RequestParam Integer id) {
        ModelAndView modelAndView = new ModelAndView("admin/edit_user");
        modelAndView.addObject("user", userService.getUser(id));
        modelAndView.addObject("roles", roleService.getAllRoles());
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editUser(@Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("admin/edit_user");
            modelAndView.addObject("roles", roleService.getAllRoles());
            return modelAndView;
        }
        userService.saveUser(user);
        return new ModelAndView("redirect:/admin");
    }

    @GetMapping("/delete")
    public ModelAndView deleteUser(@RequestParam Integer id) {
        userService.deleteUser(userService.getUser(id));
        return new ModelAndView("redirect:/admin");
    }
}
