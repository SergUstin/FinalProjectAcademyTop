package com.compony.finalproject.service.impl;

import com.compony.finalproject.model.User;
import com.compony.finalproject.repository.UserRepository;
import com.compony.finalproject.service.CRUDService;
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
        userRepository.save(item);
    }

    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user.getId();
    }

//    public double calculateAverageRating(Long id) {
//        // Получить все оценки для данного жилья
//        List<Reviews> reviews = reviewsRepository.findByAccommodationId(id);
//
//        // Если оценок нет, вернуть 0
//        if (reviews.isEmpty()) {
//            return 0;
//        }
//
//        // Суммировать все оценки
//        double sum = 0;
//        for (Reviews review : reviews) {
//            sum += review.getRating();
//        }
//
//        // Вычислить среднее значение оценок
//        double averageRating = sum / reviews.size();
//
//        // Округлить среднее значение до двух знаков после запятой
//        return Math.round(averageRating * 100) / 100.0;
//    }
}
