package com.company.finalproject.controllers;

import com.company.finalproject.model.User;
import com.company.finalproject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl service;

    @Autowired
    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public String showUsers(Model model) {
        List<User> user = service.getAll();
        model.addAttribute("users", user);
        return "users";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("users", new User());
        return "createUser";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        // Шифрование пароля
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        service.create(user);
        return "redirect:/accommodations";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = service.getById(id);
        model.addAttribute("users", user);
        return "editUser";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute User user) {
        service.edit(id, user);
        return "redirect:/users";
    }

    @PostMapping("/setRatingByFullName")
    public String setRatingByFullName(@RequestParam(name = "fullName") String fullName,
                                      @RequestParam(name = "rating") int rating) {
        service.setRatingByFullName(fullName, rating);
        return "redirect:/accommodations";
    }
}
