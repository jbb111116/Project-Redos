package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Staff;
import com.revature.repositories.StaffRepository;

@Service
public class LoginService {
	StaffRepository sr;
	
	@Autowired
	public LoginService(StaffRepository sr) {
		super();
		System.out.println("Autowiring repository to the LoginService");
		this.sr = sr;
	}
	
	public Staff Login(Staff staff) {
		String username = staff.getUsername();
		System.out.println("Inside login service method: Login()");
		return sr.Login(username);
	}
	
	public void SignUp(Staff staff) {
		System.out.println("Inside login service method: SignUp()");
		sr.insert(staff);
	}

}
