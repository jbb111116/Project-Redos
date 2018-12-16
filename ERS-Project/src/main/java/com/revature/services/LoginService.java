package com.revature.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.revature.daos.UserDao;
import com.revature.models.User;

public class LoginService {
	UserDao userDao = new UserDao();
	
	public User Login(User user) {
		
		String incomingPass = hashing(user.getPassword());
		User extractedUser = userDao.userByUsername(user.getUsername());
		String actualPass = extractedUser.getPassword();
		
		if(actualPass.equals(incomingPass)) {
			return extractedUser;
		} else {
			return null;
		}
	}
	
	public String hashing(String password) {
		String genPass = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			genPass = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return genPass;
	}
	
}
