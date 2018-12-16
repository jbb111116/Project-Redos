package com.revature.services;

import java.util.List;

import com.revature.daos.ReimbursementRequestDao;
import com.revature.models.ReimbursementRequest;

public class ManagerService {
	ReimbursementRequestDao reimbDao = new ReimbursementRequestDao();
	
	public List<ReimbursementRequest> allPendingRequests() {
		return reimbDao.AllPendingRequests();
	}
	
	public List<ReimbursementRequest> allCompletedRequests() {
		return reimbDao.AllCompletedRequests();
	}

	public void createRequest(ReimbursementRequest request) {
		reimbDao.createRequest(request);
		
	}

	public void updateRequest(ReimbursementRequest request) {
		reimbDao.updateRequest(request);
		
	}
	
	

}
