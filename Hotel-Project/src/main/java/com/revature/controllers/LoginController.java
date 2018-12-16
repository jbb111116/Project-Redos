package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Staff;
import com.revature.services.LoginService;

@RestController
@RequestMapping("")
@CrossOrigin(origins="http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
public class LoginController {
	LoginService ls;
	
	@Autowired
	public LoginController(LoginService ls) {
		super();
		this.ls = ls;
	}
	
	@PostMapping("")
	public void Login(@RequestBody Staff staff) {
		Staff result = ls.Login(staff);
		if(result.equals(null)) {
			System.out.println("Login credentials invalid");
		}
		System.out.println("Successful login!");
	}
	
	@PostMapping("create")
	public void SignUp(@RequestBody Staff staff) {
		System.out.println("Inside the Login controller method: SignUp()");
		ls.SignUp(staff);
	}
}
