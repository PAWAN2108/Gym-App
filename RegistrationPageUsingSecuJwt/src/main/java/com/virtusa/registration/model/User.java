package com.virtusa.registration.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
private String username;
private String email;
private String phone;
private String password;
private boolean enabled=true;
private String profile;

@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="user")
@JsonIgnore
private Set<UserRole> userRoles=new HashSet<>();



public User() {
	
}

public User(int id, String username, String email, String phone, String password, boolean enabled, String profile) {
	super();
	this.id = id;
	this.username = username;
	this.email = email;
	this.phone = phone;
	this.password = password;
	this.enabled = enabled;
	this.profile = profile;
}
public Set<UserRole> getUserRoles() {
	return userRoles;
}

public void setUserRoles(Set<UserRole> userRoles) {
	this.userRoles = userRoles;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public boolean isEnabled() {
	return enabled;
}
public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}

public String getProfile() {
	return profile;
}
public void setProfile(String profile) {
	this.profile = profile;
}

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {

	Set<Authority> set=new HashSet<>();
	
	this.userRoles.forEach(userRole->{
		set.add(new Authority(userRole.getRole().getRoleName()));
	});
	
	return set;
}

@Override
public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isCredentialsNonExpired() {
	// TODO Auto-generated method stub
	return true;
}

}
