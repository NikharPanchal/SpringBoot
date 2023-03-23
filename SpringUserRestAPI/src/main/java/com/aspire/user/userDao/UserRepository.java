package com.aspire.user.userDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aspire.user.utils.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
	public void deleteById(int id);
 
	public Users findByUserName(String username);
} 