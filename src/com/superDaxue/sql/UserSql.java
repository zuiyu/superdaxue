package com.superDaxue.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.superDaxue.model.User;

public class UserSql {
	private DBConnection dbconnection;
	private String school;
	public UserSql(String name){
		this.dbconnection=new DBConnection();
		school=name;
	}
	public Boolean insertUser(User user){
		Connection connection = dbconnection.getConnect();
		PreparedStatement pstmt = null;
		String sql = "INSERT super_course_user(username,password,school) VALUES(?,?,?)";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, school);
			if(pstmt.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
		//	e.printStackTrace();
		}
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean updateUser(User user){
		Connection connection=dbconnection.getConnect();
		String sql="UPDATE super_course_user SET password = ? WHERE username = ? AND school = ?";
		PreparedStatement pstmt=null;
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getUsername());
			pstmt.setString(3, school);
			if(pstmt.executeUpdate()>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} 
		return false;
	}
	
	public int getUserId(User user){
		Connection connection=dbconnection.getConnect();
		String sql="SELECT id FROM super_course_user WHERE username = ? AND school = ? ";
		PreparedStatement pstmt=null;
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, school);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} 
		return -1;
	}
	
	public User getUser(int userid){
		Connection connection=dbconnection.getConnect();
		String sql="SELECT * FROM super_course_user WHERE id = ? AND school = ?";
		PreparedStatement pstmt=null;
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setInt(1, userid);
			pstmt.setString(2, school);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()){
				User user=new User();
				user.setId(userid);
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} 
		return null;
	}
	
	public List<User> getUserList(){
		List<User> list=new ArrayList<User>();
		Connection connection=dbconnection.getConnect();
		String sql="SELECT * FROM super_course_user WHERE school = ? limit 60,100";
		PreparedStatement pstmt=null;
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setString(1, school);
			ResultSet rs=pstmt.executeQuery();
			while (rs.next()){
				User user=new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} 
		return list;
	}
}
