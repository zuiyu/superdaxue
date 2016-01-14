package com.superDaxue.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.superDaxue.model.Courses;
import com.superDaxue.model.TimeTable;

public class CourseScoreSql {
	private IConnection dbconnection;
	private String school;
	public CourseScoreSql(String name){
		this.dbconnection=new DBConnection();
		school=name;
	}
	/*public CourseScoreSql(){
		this.dbconnection=new POJOConnection();
	}*/
	
	public void insert(Courses courses,int userid){
		Connection connection=dbconnection.getConnect();
		String sql="INSERT INTO super_course_score(coursesname,courseCode,credit,score,schoolYear,semester,type,leanType,getType,checkType,remark,user_id,school) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=null;
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setString(1,courses.getCoursesname());
			pstmt.setString(2,courses.getCourseCode());
			pstmt.setDouble(3,courses.getCredit());
			pstmt.setString(4,courses.getScore());
			pstmt.setString(5,courses.getSchoolYear());
			pstmt.setString(6,courses.getSemester());
			pstmt.setString(7,courses.getType());
			pstmt.setString(8,courses.getLeanType());
			pstmt.setString(9,courses.getGetType());
			pstmt.setString(10,courses.getCheckType());
			pstmt.setString(11,courses.getRemark());
			pstmt.setInt(12, userid);
			pstmt.setString(13, school);
			pstmt.executeUpdate();
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
	}
	
	public List<Courses> searchForInfo(int userid){
		List<Courses> list=new ArrayList<Courses>();
		Connection connection=dbconnection.getConnect();
		String sql="SELECT id,coursesname,credit,score,schoolYear,semester FROM super_course_score WHERE user_id=? AND school= ?";
		PreparedStatement pstmt=null;
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setInt(1,userid);
			pstmt.setString(2,school);
			ResultSet rs=pstmt.executeQuery();
			while (rs.next()) {
				Courses courses=new Courses();
				courses.setId(rs.getInt("id"));
				courses.setCoursesname(rs.getString("coursesname"));
				courses.setCredit(rs.getDouble("credit"));
				courses.setScore(rs.getString("score"));
				courses.setSchoolYear(rs.getString("schoolYear"));
				courses.setSemester(rs.getString("semester"));
				list.add(courses);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} 
		return list;
	}
	
	public boolean searchBycourseCode(Courses courses,int userid){
		Connection connection=dbconnection.getConnect();
		String sql="SELECT coursesname FROM super_course_score WHERE (courseCode=? or coursesname=?) and user_id = ? and schoolYear = ? and semester = ? AND school = ?";
		PreparedStatement pstmt=null;
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setString(1,courses.getCourseCode());
			pstmt.setString(2,courses.getCoursesname());
			pstmt.setInt(3, userid);
			pstmt.setString(4, courses.getSchoolYear());
			pstmt.setString(5, courses.getSemester());
			pstmt.setString(6, school);
			ResultSet rs=pstmt.executeQuery();
			if (rs.next()) {
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
	
	public boolean searchByUserid(int userid){
		Connection connection=dbconnection.getConnect();
		String sql="SELECT coursesname FROM super_course_score WHERE user_id = ? AND school = ?";
		PreparedStatement pstmt=null;
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setInt(1, userid);
			pstmt.setString(2, school);
			ResultSet rs=pstmt.executeQuery();
			if (rs.next()) {
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
	
}
	