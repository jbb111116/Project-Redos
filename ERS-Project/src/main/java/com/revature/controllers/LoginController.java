package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.services.LoginService;

public class LoginController {

	LoginService logService = new LoginService();
	
	ObjectMapper mapper = new ObjectMapper();
	
	public void get(HttpServletRequest request, HttpServletResponse response) {
		
	}
	
	public void post(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = mapper.readValue(request.getReader(), User.class);
		System.out.println(user);
		
		try {
			User extractedUser = logService.Login(user);
			mapper.writeValue(response.getWriter(), extractedUser);
		} catch(Exception e) {
			System.out.println("No user found");
			response.sendError(404);
		}
		
	}
	
	public void put(HttpServletRequest request, HttpServletResponse response) {
		
	}
	
}
