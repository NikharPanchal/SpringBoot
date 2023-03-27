package com.aspire.user.utils;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtToken {
	
	private String userName;
	private String userPassword; 
	public String getUserName() {
		return userName;
	}
	@Override
	public String toString() {
		return "JwtToken [userName=" + userName + ", userpPassword=" + userPassword + "]";
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public JwtToken(String userName, String userPassword) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
	}
	public String getUserpassword() {
		return userPassword;
	}
	public void setUserpassword(String userpassword) {
		this.userPassword = userpassword;
	}
}
