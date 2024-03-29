package com.company.finalproject.service.impl;

import com.company.finalproject.model.User;
import com.company.finalproject.repository.UserRepository;
import com.company.finalproject.service.CRUDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class UserServiceImpl implements CRUDService<User> {
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User getById(Long id) {
        log.info("Getting user by id: " + id);
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    @Override
    public List<User> getAll() {
        log.info("Getting all users");
        return userRepository.findAll();
    }
    @Override
    public void create(User item) {
        log.info("Creating new user: " + item.getUsername());
        // По умолчанию для нового пользователя статус в "ACTIVE"
        item.setStatus("ACTIVE");
        item.setRating(0);
        item.setNumberOfRatings(0L);
        // Сохранить пользователя в базу данных
        userRepository.save(item);
        log.info("User created successfully: " + item.getUsername());
    }
    @Override
    public void edit(Long id, User item) {
        log.info("Editing user with id: " + id);
        userRepository.findById(id);
        userRepository.save(item);
        log.info("User edited successfully");
    }
    public Long getUserIdByUsername(String username) {
        log.info("Getting user id by username: " + username);
        User user = userRepository.findByUsername(username);
        log.info("User id for username " + username + " is " + user.getId());
        return user.getId();
    }
    public void setRatingByFullName(String fullName, int newRating) {
        User user = userRepository.findByFullName(fullName);
        if (user == null) {
            throw new IllegalArgumentException("User with full name " + fullName + " does not exist");
        }
        log.info("Updating rating for user {}", fullName);
        user.setNumberOfRatings(user.getNumberOfRatings() + 1);
        long countRating = user.getNumberOfRatings();
        Integer currentRatings = user.getRating();
        if (currentRatings == null || currentRatings == 0) {
            user.setRating(newRating);
        } else {
            long newAverageRating = (int) ((currentRatings * (countRating - 1)) + newRating) / countRating;
            newAverageRating = (long) (Math.round(newAverageRating * 100.0) / 100.0);
            user.setRating((int) newAverageRating);
            updateStatus(user);
        }
        userRepository.save(user);
        log.info("Rating updated successfully for user {}", fullName);
    }
    private void updateStatus(User user) {
        // Если рейтинг пользователя больше или равен 2, то установить его статус в "ACTIVE"
        if (user.getRating() >= 2) {
            user.setStatus("ACTIVE");
        } else {
            // Если рейтинг пользователя меньше 2, то установить его статус в "BLOCKED"
            user.setStatus("BLOCKED");
        }
    }
}
