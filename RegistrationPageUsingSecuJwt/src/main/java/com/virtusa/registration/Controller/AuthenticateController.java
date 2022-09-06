package com.virtusa.registration.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.registration.Config.JwtUtils;
import com.virtusa.registration.Exception.UserNotFoundException;
import com.virtusa.registration.model.JwtRequest;
import com.virtusa.registration.model.JwtResponse;
import com.virtusa.registration.service.Impl.UserDetailsServiceImpl;

@RestController
public class AuthenticateController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	//Generate Tokens
	
//	public ResponseEntity<?> generateToken()                                                                       //2
	
	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception  {
		try {
			
			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
			
		}catch(UserNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User not found");
		}
		UserDetails userDetails=this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token=this.jwtUtils.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	public String generateInternalToken(JwtRequest jwtRequest) throws Exception  {
		try {
			System.out.println("Succesfully running auth!");
			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

		}catch(UserNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User not found");
		}
		UserDetails userDetails=this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token=this.jwtUtils.generateToken(userDetails);
		return new JwtResponse(token).getToken();
	}
			
			
//			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
//			
//		}catch(UsernameNotFoundException e) {
//			e.printStackTrace();
//			throw new Exception("User not found");
//		}
//	UserDetails userDetails=this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
//	String token=this.jwtUtils.generateToken(userDetails);
//	return ResponseEntity.ok(new JwtResponse(token));
//	}
                                                                                                               //1
	private void authenticate(String username, String password) throws Exception
	{
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		} catch (DisabledException e) {
             throw new Exception("USER DISABLED" +e.getMessage());
		}catch(BadCredentialsException e)
		{
			throw new Exception("Invalid Credentials" +e.getMessage());
		}
	}
}
