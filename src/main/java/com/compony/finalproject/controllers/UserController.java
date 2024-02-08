package com.compony.finalproject.controllers;

import com.compony.finalproject.dto.UserDto;
import com.compony.finalproject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUsers(Model model) {
        List<UserDto> userDtos = userService.getAll();
        model.addAttribute("userDtos", userDtos);
        return "users";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "createUser";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserDto userDto) {
        userService.registerUser(userDto);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        UserDto userDto = userService.getById(id);
        model.addAttribute("userDto", userDto);
        return "editUser";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute UserDto userDto) {
        userDto.setId(id);
        userService.updateUser(userDto);
        return "redirect:/users";
    }
}
