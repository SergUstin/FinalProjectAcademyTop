package com.company.finalproject.repository;

import com.company.finalproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с моделью User.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Поиск пользователя по его имени пользователя.
     *
     * @param username Имя пользователя для поиска.
     * @return Пользователь с указанным именем пользователя, либо {@code null}, если пользователь не найден.
     */
    User findByUsername(String username);

    /**
     * Поиск пользователя по его полному имени.
     *
     * @param fullName Полное имя пользователя для поиска.
     * @return Пользователь с указанным полным именем, либо {@code null}, если пользователь не найден.
     */
    User findByFullName(String fullName);
}
