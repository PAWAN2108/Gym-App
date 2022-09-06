package com.virtusa.registration.Controller;

import java.net.URI;
import java.util.*;

//import com.sun.deploy.net.HttpResponse;
import com.virtusa.registration.Config.JwtUtils;
import com.virtusa.registration.model.*;
import com.virtusa.registration.service.Impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.web.bind.annotation.*;

import com.virtusa.registration.service.UserService;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/createUser")
	public User createUser(@RequestBody User user) throws Exception {
		
//		user.setProfile("default.png");
		System.out.println("User route hit");
		Set<UserRole> roles=new HashSet<>();
		Role role=new Role();
		role.setRoleId(35);
		role.setRoleName("Manager");
		
		UserRole userRole=new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		roles.add(userRole);
		
		return this.userService.CreateUser(user, roles);
	}
	
	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username) {
		return this.userService.getUser(username);
	}

	@GetMapping("/abby")
	public  String getAbby()
	{
		return "yes";
	}
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") int userId) {
		this.userService.deleteUser(userId);
	}



	public String notFound() {
		return "";
	}

	@PostMapping("/verifyToken")
	public ResponseEntity<Boolean> verifyToken(@RequestBody UserToken token){

		JwtUtils jwtUtils = new JwtUtils();
		String SECRET_KEY = "gymportal";
UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();
//		UserDetails userDetails =userDetailsService.loadUserByUsername(Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.getUserToken()).getBody().getSubject());
		if(jwtUtils.verifyToken(token.getUserToken())){
			System.out.println("verified");
			return ResponseEntity.ok().body(true);
		}
		else{
			System.out.println("not verified");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
		}

	}
	@PostMapping("/loginRedirect")
	public ResponseEntity<String> loginRedirect(){
		return ResponseEntity.ok().body("/dashboard");
	}
	String username = "empty";
	@PostMapping("/loginUser")
	public ResponseEntity<String> loginUser(@RequestBody User user) {
		System.out.println("HITH");
		try{
User userRetrived = this.userService.getUser(user.getUsername());

//		if(userRetrived.getUsername().isEmpty())
//		{
//			return "User not found";
//		}
		username = this.userService.getUser(user.getUsername()).getUsername();
//		return ResponseEntity.status(HttpStatus.OK).body(username);
		// since the username exists let's check for password
			if(user.getPassword().equals(userRetrived.getPassword()))
			{
				JwtUtils jwtUtils = new JwtUtils();
				Map<String, Object> claims = new HashMap<>();
				String token=jwtUtils.createToken(claims,username);
				System.out.println("token generated "+token);
				return ResponseEntity.ok().body(token);


			}
			else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such user with username "+user.getUsername()+" exists");
			}
//
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such user with username "+user.getUsername()+" exists");
		}
//		catch(NullPointerException e)
//		{
//			System.out.println("Catch block");
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such user with username "+user.getUsername()+" exists");
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}

	}
//	@ExceptionHandler(UserNotFoundException.class)
//	public ResponseEntity<?> exceptionHandler(UserNotFoundException ex)
//	{
//		return ResponseEntity<T>;
//	}
}
