package com.revature.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;

import com.revature.controllers.EmployeeController;
import com.revature.controllers.LoginController;
import com.revature.controllers.ManagerController;
import com.revature.models.Route;

public class DispatcherServlet extends DefaultServlet {
	
	EmployeeController empController = new EmployeeController();
	ManagerController manController = new ManagerController();
	LoginController logController = new LoginController();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		response.addHeader("Access-Control-Allow-Credentials","true");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		response.addHeader("Access-Control-Allow-Methods","GET, POST, DELETE, PUT, OPTIONS, HEAD");
		super.service(request, response);
	}

	// Java GET is for SELECT in SQL, unless logging in
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Route route = getRoute(request);

		switch (route) {
		case MANAGER:
			manController.get(request, response);
			break;
		case EMPLOYEE:
			empController.get(request, response);
			break;
		case STATIC:
			writeStaticFile(request,response); 
			break;
		case NOT_FOUND:
		default: response.setStatus(404);
		}
	}
	
	// Java POST is for INSERT in SQL
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Route route = getRoute(request);
		
		switch(route) {
			case LOGIN: logController.post(request, response); break;
			case MANAGER: manController.post(request, response); break;
			case EMPLOYEE: empController.post(request, response); break;
			case NOT_FOUND:
			default: response.setStatus(404);
		}
	}
	
	// According to CRUD, Java's PUT method is for UPDATE in SQL
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Route route = getRoute(request);
		
		switch(route) {
		case MANAGER: manController.put(request, response); break;
		case NOT_FOUND:
		default: response.setStatus(404);
		}
	}
	
	
	//1
	static Route getRoute(HttpServletRequest request) {
		String suffix = request.getRequestURI().substring("/ERS-Project/".length());
		String routeString = suffix.split("/")[0];
		try {
			return Route.valueOf(routeString.toUpperCase());
		} catch(IllegalArgumentException e) {
			return Route.NOT_FOUND;
			}
	}
	//1
	
	void writeStaticFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getNamedDispatcher("default");
		HttpServletRequest wrapped = new HttpServletRequestWrapper(request) {
			public String getServletPath() { return "";}
			
		};
		rd.forward(wrapped, response);
	}

}
