package com.superDaxue.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.superDaxue.model.Courses;

public class DBSchoolName {
	private IConnection dbconnection;
	public DBSchoolName(){
		this.dbconnection=new DBConnection();
	}
	
	public String searchName(String school){
		Connection connection=dbconnection.getConnect();
		String sql="SELECT name FROM super_school WHERE website=?";
		PreparedStatement pstmt=null;
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setString(1,school);
			ResultSet rs=pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("name");
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
}
