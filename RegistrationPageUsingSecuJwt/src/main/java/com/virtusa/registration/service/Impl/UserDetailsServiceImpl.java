package com.virtusa.registration.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.virtusa.registration.Repository.UserRepository;
import com.virtusa.registration.model.User;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("from loadUserBy... "+username);
		User user5=this.userRepository.findByUsername(username);
		System.out.println(user5.getUsername());
		if(user5 == null) {
			System.out.println("User not found " + username);
			throw new UsernameNotFoundException("No user found");

		}
		return user5;

	}




	}
