package com.compony.finalproject.service.impl;

import com.compony.finalproject.model.Accommodation;
import com.compony.finalproject.model.User;
import com.compony.finalproject.repository.UserRepository;
import com.compony.finalproject.service.CRUDService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements CRUDService<User> {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void create(User item) {
        // По умолчанию для нового пользователя статус в "ACTIVE"
        item.setStatus("ACTIVE");

        // Сохранить пользователя в базу данных
        userRepository.save(item);
    }


    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user.getId();
    }

    public void setRatingByFullName(String fullName, double newRating) {
        User user = userRepository.findByFullName(fullName);

        if (user == null) {
            throw new IllegalArgumentException("User with full name " + fullName + " does not exist");
        }

        user.incrementNumberOfRatings();

        long countRating = user.getNumberOfRatings();

        Double currentRatings = user.getRating();
        if (currentRatings == null || currentRatings == 0.0) {
            user.setRating(newRating);
        } else {
            double newAverageRating = ((currentRatings * (countRating - 1)) + newRating) / countRating;

            newAverageRating = Math.round(newAverageRating * 100.0) / 100.0;

            user.setRating(newAverageRating);
            updateStatus(user);
        }

        userRepository.save(user);
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

//    public void setRatingAndHandleBlock(String fullName, double rating) {
//        // Найти пользователя по полному имени
//        User user = userRepository.findByFullName(fullName);
//
//        // Установить рейтинг пользователя
//        user.setRating(rating);
//
//        // Проверить, был ли пользователь ранее заблокирован
//        if (user.getStatus().equals("BLOCKED")) {
//            // Если пользователь был заблокирован, но его новый рейтинг больше 2, то разблокировать его
//            if (rating >= 2) {
//                user.setStatus("ACTIVE");
//            }
//        } else {
//            // Если пользователь не был заблокирован, но его новый рейтинг меньше 2, то заблокировать его
//            if (rating < 2) {
//                user.setStatus("BLOCKED");
//            }
//        }
//
//        // Сохранить пользователя в базу данных
//        userRepository.save(user);
//    }





}
