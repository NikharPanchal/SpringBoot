package com.aspire.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aspire.user.userDao.UserRepository;
import com.aspire.user.utils.Users;

@Service
public class UserService implements UserDetailsService {

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
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
//			System.out.println(user);
			Users user= repository.findByUserName(username);
			System.out.println(user);
			return new User(user.getUserName(),user.getUserPassword(),new ArrayList<>());
		}
		catch(UsernameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
		
//		if(username.equals("nikhar")) {
//			return new User("nikhar","panchal",new ArrayList<>());
//		}else
//		{
//			throw new UsernameNotFoundException("user not found");
//		}
	}

}
