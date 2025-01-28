package com.example.user_practice_1.repository;

import com.example.user_practice_1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
}
