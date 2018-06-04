package com.example.demo.repository;

import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by home on 6/5/2018.
 */
public interface UsersRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByName(String username);
}
