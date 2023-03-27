package com.aspire.user.utils;

public class MyToken {

	public MyToken(String token) {
		super();
		this.token = token;
	}

	@Override
	public String toString() {
		return "MyToken [token=" + token + "]";
	}

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
