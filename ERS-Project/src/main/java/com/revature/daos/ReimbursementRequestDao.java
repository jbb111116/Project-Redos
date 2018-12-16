package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.ReimbursementRequest;
import com.revature.utils.ConnectionUtil;

public class ReimbursementRequestDao {

	/*
	 * extractCompletedRequest is supposed to extract only requests that have been
	 * processed. We would automatically assign all the fields of the object from
	 * the table data that is extracted. No presets are needed for our object.
	 */
	private ReimbursementRequest extractCompletedRequest(ResultSet rs) throws SQLException {
		ReimbursementRequest extracted = new ReimbursementRequest();
		extracted.setAmount(rs.getBigDecimal("reimb_amount"));
		extracted.setDescription(rs.getString("reimb_description"));
		extracted.setAuthor_id(rs.getInt("reimb_author"));
		extracted.setResolver_id(rs.getInt("reimb_resolver"));
		extracted.setReimbursement_id(rs.getInt("reimb_id"));
		extracted.setSubmitted(rs.getDate("reimb_submitted"));
		extracted.setResolved(rs.getDate("reimb_resolved"));
		extracted.setStatus_id(rs.getInt("reimb_status_id"));
		extracted.setType_id(rs.getInt("reimb_type_id"));

		System.out.println("Request extracted!");
		return extracted;
	}

	/*
	 * extractPendingRequest() is supposed to extract only request that have not
	 * been processed. We would have to assign all the null integers as '0'
	 * ourselves.
	 */
	private ReimbursementRequest extractPendingRequest(ResultSet rs) throws SQLException {
		ReimbursementRequest extracted = new ReimbursementRequest();
		extracted.setAmount(rs.getBigDecimal("reimb_amount"));
		extracted.setDescription(rs.getString("reimb_description"));
		extracted.setAuthor_id(rs.getInt("reimb_author"));
		extracted.setResolver_id(0);
		extracted.setReimbursement_id(rs.getInt("reimb_id"));
		extracted.setSubmitted(rs.getDate("reimb_submitted"));
		extracted.setResolved(null);
		extracted.setStatus_id(rs.getInt("reimb_status_id"));
		extracted.setType_id(rs.getInt("reimb_type_id"));

		System.out.println("Requests extracted!");
		return extracted;
	}

	/*
	 * Works with extractCompletedRequest() to extract a list of reimbursements that
	 * have been processed.
	 */
	public List<ReimbursementRequest> AllCompletedRequests() {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "SELECT * FROM reimbursement WHERE reimb_status_id > 1";
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			List<ReimbursementRequest> requests = new ArrayList<>();
			while (rs.next()) {
				ReimbursementRequest request = extractCompletedRequest(rs);
				System.out.println(request);
				requests.add(request);
			}
			return requests;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<ReimbursementRequest> AllPendingRequests() {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "SELECT * FROM reimbursement WHERE reimb_status_id = 1";
			PreparedStatement ps = conn.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			List<ReimbursementRequest> requests = new ArrayList<>();
			while (rs.next()) {
				ReimbursementRequest request = extractCompletedRequest(rs);
				System.out.println(request);
				requests.add(request);
			}
			return requests;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void createRequest(ReimbursementRequest request) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "INSERT INTO reimbursement(reimb_amount, reimb_submitted, reimb_description, "
					+ "reimb_author, reimb_status_id, reimb_type_id) \r\n"
					+ "VALUES(?,CURRENT_TIMESTAMP,?,?,?,?) RETURNING *";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setBigDecimal(1, request.getAmount());
			ps.setString(2, request.getDescription());
			ps.setInt(3, request.getAuthor_id());
			ps.setInt(4, 1);
			ps.setInt(5, request.getType_id());

			ResultSet rs = ps.executeQuery();
			rs.next();
			request.setReimbursement_id(rs.getInt("reimb_id"));
			request.setSubmitted(rs.getDate("reimb_submitted"));
			System.out.println(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("New request failed to persist");
		}
	}
	
	public void updateRequest(ReimbursementRequest request) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String query = "UPDATE reimbursement SET reimb_status_id = ?, reimb_resolver = ?, reimb_resolved = "
					+ "CURRENT_TIMESTAMP WHERE reimb_id = ? AND reimb_status_id = 1 RETURNING *";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, request.getStatus_id());
			ps.setInt(2, request.getResolver_id());
			ps.setInt(3, request.getReimbursement_id());
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			System.out.println(rs);
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't update the request");
		}
		
	}

}
