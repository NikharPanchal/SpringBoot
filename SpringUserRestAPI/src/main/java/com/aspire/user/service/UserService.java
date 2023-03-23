package com.aspire.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.user.userDao.UserRepository;
import com.aspire.user.utils.Users;

@Service
public class UserService {

	@Autowired
	UserRepository repository;
	 
	public Users saveUserDetails(Users user) {
		return repository.save(user);
	}

	public List<Users> getAllUsers() {
		List<Users> userList=repository.findAll();
		return userList;
	}

	public void deleteUser(int id) {
		 repository.deleteById(id);
	}

	public Users editUserData(Users user) {
		repository.save(user);
		return null;
	}

	public Optional<Users> getUserbyId(int id) {
		Optional<Users> user=repository.findById(id);
		return user;
	}

	public Users findByUsername(String username) {
		Users user=repository.findByUserName(username);
		return user;
	}

}
