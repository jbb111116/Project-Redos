package com.revature.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.ReimbursementRequest;
import com.revature.services.ManagerService;

public class ManagerController {
	ObjectMapper om = new ObjectMapper();
	ManagerService manService = new ManagerService();

	
	public void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uri = request.getRequestURI();
		String[] parts = uri.substring("/ERS-Project/manager/".length()).split("/");
		
		String choice = parts[0];
		
		switch(choice) {
		case "pending":
			try {
				List<ReimbursementRequest> requests = manService.allPendingRequests();
				if(requests.isEmpty()) {
					System.out.println("No pending requests found");
					response.sendError(404);
				}
				om.writeValue(response.getWriter(), requests);
			} catch(NumberFormatException e) {
				System.out.println("Requests not found");
			}
			break;
			
		case "processed":
			try {
				List<ReimbursementRequest> requests = manService.allCompletedRequests();
				if(requests.isEmpty()) {
					System.out.println("No completed requests found");
					response.sendError(404);
				}
				om.writeValue(response.getWriter(), requests);
			} catch(NumberFormatException e) {
				System.out.println("Requests not found");
			}
			break;
		}
	}
	
	public void post(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uri = request.getRequestURI();
		String[] parts = uri.substring("/ERS-Project/manager/".length()).split("/");
		System.out.println("Post method endpoint in Manager controller was hit");
		String choice = parts[0];
		switch(choice) {
		case "create":
			ReimbursementRequest newRequest = om.readValue(request.getReader(), ReimbursementRequest.class);
			System.out.println(newRequest);
			try {
				manService.createRequest(newRequest);
				System.out.println("New request is being sent to the service layer!");
				response.getWriter().write("Transaction was successful");
			} catch (Exception e) {
				System.out.println("Transaction failed");
				response.sendError(404);
			}
			break;
//		default: System.out.println("Invalid input");
	}
		
	}
	
	public void put(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uri = request.getRequestURI();
		String[] parts = uri.substring("/ERS-Project/manager/".length()).split("/");
		String choice = parts[0];
		switch(choice) {
		case "update":
			ReimbursementRequest updatedRequest = om.readValue(request.getReader(), ReimbursementRequest.class);
			System.out.println(updatedRequest);
			
			try {
				manService.updateRequest(updatedRequest);
			} catch (Exception e) {
				System.out.println("Update of request failed");
				response.sendError(404);
			}
		}
	}

}
