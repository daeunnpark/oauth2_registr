package com.example.springsecurityjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecurityjpa.model.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer>{
	boolean existsByUsername(String username);
	Optional<User> findByUsername(String username);
	Optional<User> findByName(String name);
}
