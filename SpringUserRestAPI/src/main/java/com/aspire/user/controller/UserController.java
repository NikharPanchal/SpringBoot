package com.aspire.user.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.user.config.JwtAuthentication;
import com.aspire.user.config.SecurityConfig;
import com.aspire.user.service.UserService;
import com.aspire.user.utils.JwtToken;
import com.aspire.user.utils.MyToken;
import com.aspire.user.utils.Users;

@RestController
@CrossOrigin
public class UserController { 

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService; 
		
	@Autowired
	private JwtAuthentication authentication;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SecurityConfig securityConfig;
	
//	Collection<SimpleGrantedAuthority> oldAuthorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//	SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ANOTHER");
//	List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
//	updatedAuthorities.add(authority);
//	updatedAuthorities.addAll(oldAuthorities);
	
	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody JwtToken token) throws Exception{
		
		System.out.println(token.getUserName());
		System.out.println(token.getUserpassword());
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token.getUserName(),token.getUserpassword()));	
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong credentials");
		}
		UserDetails userdetails=userService.loadUserByUsername(token.getUserName());
		System.out.println(userdetails);
		
		String mytoken =authentication.generateToken(userdetails);
		return ResponseEntity.ok().body(new MyToken(mytoken));	
	}

	@GetMapping("/home")
	public String home() {
		return "home works from rest api";
	}

	@GetMapping("/users")
	public ResponseEntity<List<Users>> getUsers() {
		List<Users> userList = userService.getAllUsers();
		System.out.println(userList);
		if(userList.size()<0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok().body(userList);
	} 

	@PostMapping("/save")
	public Users saveUser(@RequestBody Users user) {
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		Users userdata=userService.saveUserDetails(user);
		return userdata;  
	}

	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable("id") int id) {
		userService.deleteUser(id);
	}

	@GetMapping("/user/{id}")
	public Optional<Users> getUserById(@PathVariable("id") int id) {
		Optional<Users> user = userService.getUserbyId(id);
		return user;
	}

	@PutMapping("/edit/{id}")
	public Users editUser(@PathVariable("id") int id, @RequestBody Users user) {
		Optional<Users> userRespo = userService.getUserbyId(id);
		System.out.println(user.toString());
		return user= userService.saveUserDetails(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Users> checkLoginCredential(@RequestBody Users username) {
		System.out.println(username.getUserName());
		Users user=userService.findByUsername(username.getUserName());
		System.out.println(user);
		if(user==null)
		{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();	
		}
		return ResponseEntity.ok(user);
	}
	
}
