package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showUsers(Model model, Principal principal) {
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("currentUser", userService.findByUsername(principal.getName()));
        model.addAttribute("newUser", new User());
        return "admin";
    }


    @PostMapping("/admin/saveUser")
    public String registration(@ModelAttribute("newUser") User user,
                               @RequestParam("role") List<Integer> roles) {
        Set<Role> newUserRoles = new HashSet<>();
        for (int rolesId : roles) {
            newUserRoles.add(roleService.findRoleById(rolesId));
        }
        user.setRoles(newUserRoles);
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @PostMapping("/admin/{id}/edit")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") int id,
                         @RequestParam("role") List<Integer> roles) {
        Set<Role> newUserRoles = new HashSet<>();
        for (int rolesId : roles) {
            newUserRoles.add(roleService.findRoleById(rolesId));
        }

        user.setRoles(newUserRoles);
        userService.update(id, user);


        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "user";
    }

}
