package com.company.finalproject.controllers;

import com.company.finalproject.model.User;
import com.company.finalproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void testShowUsers() throws Exception {
        List<User> users = Arrays.asList(new User(), new User());
        given(userService.getAll()).willReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(model().attribute("users", users));
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/users/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createUser"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void testCreateUser() throws Exception {
        mockMvc.perform(post("/users/create")
                        .param("username", "testuser")
                        .param("email", "testuser@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/accommodations"));
    }

    @Test
    void testShowEditForm() throws Exception {
        User user = new User();
        given(userService.getById(1L)).willReturn(user);

        mockMvc.perform(get("/users/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editUser"))
                .andExpect(model().attribute("users", user));
    }

    @Test
    void testEditUser() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        User updatedUser = new User();
        updatedUser.setId(1L);  // Предполагается, что в методе edit сервиса происходит обновление идентификатора пользователя
        updatedUser.setUsername("testuser");
        updatedUser.setEmail("testuser@example.com");

        doNothing().when(userService).edit(1L, user);

        mockMvc.perform(post("/users/edit/1")
                        .param("username", "testuser")
                        .param("email", "testuser@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void testSetRatingByFullName() throws Exception {
        mockMvc.perform(post("/users/setRatingByFullName")
                        .param("fullName", "John Doe")
                        .param("rating", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accommodations"));
    }

}