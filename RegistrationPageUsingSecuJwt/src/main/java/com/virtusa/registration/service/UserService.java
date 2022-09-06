package com.virtusa.registration.service;

import java.util.Set;
import com.virtusa.registration.model.User;
import com.virtusa.registration.model.UserRole;

public interface UserService {

	public User CreateUser(User user, Set<UserRole> userRoles) throws Exception;
	
	public User getUser(String username);
	
	public void deleteUser(int userId);
	
//	public User updateUser(String currentUsername, String newUsername, String newEmail, String role ) throws UserNotFoundException, IOException, UserNameExistException;
}
