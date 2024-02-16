package com.company.finalproject.controllers;

import com.company.finalproject.model.User;
import com.company.finalproject.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
        log.info("Request to display a list of users");
        List<User> user = service.getAll();
        model.addAttribute("users", user);
        return "users";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        log.info("Request to display the user creation form");
        model.addAttribute("users", new User());
        return "createUser";
    }
    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        log.info("Creating a user: {}", user);
        // Шифрование пароля
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        service.create(user);
        return "redirect:/accommodations";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.info("Request to display the edit form of a user with an ID {}", id);
        User user = service.getById(id);
        model.addAttribute("users", user);
        return "editUser";
    }
    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute User user) {
        log.info("Editing a user with an ID {}: {}", id, user);
        service.edit(id, user);
        return "redirect:/users";
    }
    @PostMapping("/setRatingByFullName")
    public String setRatingByFullName(@RequestParam(name = "fullName") String fullName,
                                      @RequestParam(name = "rating") int rating) {
        log.info("Setting the rating for a user with the full name '{}' to the value {}", fullName, rating);
        service.setRatingByFullName(fullName, rating);
        return "redirect:/accommodations";
    }
}
