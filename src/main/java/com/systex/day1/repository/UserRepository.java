package com.systex.day1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systex.day1.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
