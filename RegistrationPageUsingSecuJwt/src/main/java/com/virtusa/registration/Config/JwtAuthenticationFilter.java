package com.virtusa.registration.Config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.virtusa.registration.service.Impl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtUtils jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");
		System.out.println(requestTokenHeader);
		String username = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			System.out.println("Success Responsed");
			try {
				jwtToken = requestTokenHeader.substring(7);

				username = this.jwtUtil.extractUsername(jwtToken);
//				System.out.println(username);
			} catch (ExpiredJwtException e) {
				e.printStackTrace();
				System.out.println("jwt token has expired");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("error");
			}
		} else {
			System.out.println("Invalid token, not start with bearer string");   //We can also use Logger here.
		}

//		System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
		
		
		// Validate The Tokens
		if (username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
//			System.out.println(userDetails.getUsername()+ "\t"+ userDetails.getPassword());
			
//			userDetails.getAuthorities().forEach(authoritiy->System.out.println(authoritiy.getAuthority()));

			if (this.jwtUtil.validateToken(jwtToken, userDetails)) {
				
				//token is valid

				UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
			}
		} else {
			System.out.println("Token is not valid");
		}

		filterChain.doFilter(request, response);

	}

}
