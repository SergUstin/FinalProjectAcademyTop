package com.compony.finalproject.controllers;

import com.compony.finalproject.model.User;
import com.compony.finalproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showUsers(Model model) {
        List<User> user = userRepository.findAll();
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
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("users", user);
        return "editUser";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute User user) {
        user.setId(id);
        userRepository.save(user);
        return "redirect:/users";
    }
}
