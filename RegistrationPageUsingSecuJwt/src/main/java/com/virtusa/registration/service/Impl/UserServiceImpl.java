package com.virtusa.registration.service.Impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.virtusa.registration.Repository.RoleRepository;
import com.virtusa.registration.Repository.UserRepository;
import com.virtusa.registration.model.User;
import com.virtusa.registration.model.UserRole;
import com.virtusa.registration.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public User CreateUser(User user, Set<UserRole> userRoles) throws Exception {
		
	User local=	this.userRepository.findByUsername(user.getUsername());
	if(local!=null)
	{
		System.out.println("User is already there!!");
		throw new Exception("User already present!!");
	}
	else {
		for(UserRole ur:userRoles)
		{
			roleRepository.save(ur.getRole());
		}
		user.getUserRoles().addAll(userRoles);
		local=this.userRepository.save(user);
	}
		return local;
	}

	@Override
	public User getUser(String username) {

		return this.userRepository.findByUsername(username);
	}

	@Override
	public void deleteUser(int userId) {

		this.userRepository.deleteById(userId);
	}
}

//	@Override
//	public User updateUser(String currentUsername, String newUsername, String newEmail, String role)
//			throws UserNotFoundException, IOException, UserNameExistException {
//		User currentUser=validateNewUsernameEmail()
//
//		return null;
//	}
	
