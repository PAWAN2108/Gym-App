package com.virtusa.registration;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.virtusa.registration.model.Role;
import com.virtusa.registration.model.User;
import com.virtusa.registration.model.UserRole;
import com.virtusa.registration.service.UserService;

@SpringBootApplication
public class RegistrationPageUsingSecuJwtApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(RegistrationPageUsingSecuJwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
System.out.println("Starting Code");		
	
//	User user=new User();
//user.setUsername("Pawan Kumar");
//user.setEmail("smppp210809@gmail.com");
//user.setPhone("5632147890");
//user.setPassword("kjl5632");
//user.setProfile("default.png");
//
//Role role=new Role();
//role.setRoleId(25);
//role.setRoleName("Admin");
//
//Set<UserRole> userRoleSet=new HashSet<>();
//UserRole userRole=new UserRole();
//userRole.setRole(role);
//userRole.setUser(user);
//
//userRoleSet.add(userRole);
//
//User user1=this.userService.CreateUser(user, userRoleSet);
//System.out.println(user1.getUsername());
//

}

}