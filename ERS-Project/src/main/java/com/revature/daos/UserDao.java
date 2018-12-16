package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class UserDao {

	private User extractEmployee(ResultSet rs) throws SQLException {
		User extracted = new User();
		extracted.setUser_id(rs.getInt("users_id"));
		extracted.setUsername(rs.getString("username"));
		extracted.setPassword(rs.getString("psswrd"));
		extracted.setFirstName(rs.getString("user_first_name"));
		extracted.setLastName(rs.getString("user_last_name"));
		extracted.setRole(rs.getInt("user_role_id"));

		return extracted;
	}
	
	public List<User> allUsers() {
		try(Connection conn = ConnectionUtil.getConnection()){
			String query = "SELECT * FROM users;";
			PreparedStatement ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			List<User> users = new ArrayList<>();
			while(rs.next()) {
				User user = extractEmployee(rs);
				users.add(user);
			}
			System.out.println(users);
			System.out.println("Users extracted!");
			return users;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public User userByUsername(String username) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			User user = null;
			String query = "SELECT * FROM users  WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			user = extractEmployee(rs);
			System.out.println(user);
			System.out.println(user.getUser_id());
			System.out.println(user.getRole());
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
