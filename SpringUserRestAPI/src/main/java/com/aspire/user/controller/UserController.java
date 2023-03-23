package com.aspire.user.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.user.service.UserService;
import com.aspire.user.utils.Users;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;
	
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/home")
	public String home() {
		return "home works from rest api";
	}

	@GetMapping("/users")
	public ResponseEntity<List<Users>> getUsers() {
		List<Users> userList = userService.getAllUsers();
		if(userList.size()<0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok().body(userList);
	}

	@PostMapping("/save")
	public Users saveUser(@RequestBody Users user) {
//		user.setUserPassword(this.passwordEncoder.encode(user.getUserPassword()));
		userService.saveUserDetails(user);
		return user;
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
