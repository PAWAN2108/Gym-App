package com.virtusa.registration.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.virtusa.registration.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUsername(String username);
}
