package com.superDaxue.sql;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.superDaxue.model.TimeTable;

public class CoursesTimetableSql {
	private IConnection dbconnection;
	private String school;
	public CoursesTimetableSql(String name){
		this.dbconnection=new DBConnection();
		school=name;
	}
	/*public CoursesTimetableSql(){
		this.dbconnection=new POJOConnection();
	}*/
	
	public void insert(TimeTable timeTable,int userid){
		Connection connection = dbconnection.getConnect();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO super_course_timetable(courseName,courseCode,credit,type,teacher,classId,classNum,user_id,address,cycle,week,time,singleDouble,schoolyear,semester,school) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, timeTable.getCourseName());
			pstmt.setString(2, timeTable.getCourseCode());
			pstmt.setDouble(3, timeTable.getCredit());
			pstmt.setString(4, timeTable.getType());
			pstmt.setString(5, timeTable.getTeacher());
			pstmt.setString(6, timeTable.getClassId());
			pstmt.setString(7, timeTable.getClassNum());
			pstmt.setInt(8, userid);
			pstmt.setString(9, timeTable.getAddress());
			pstmt.setString(10, timeTable.getCycle());
			pstmt.setString(11, timeTable.getWeek());
			pstmt.setString(12, timeTable.getTime());
			pstmt.setString(13, timeTable.getSingleDouble());
			pstmt.setString(14, timeTable.getSchoolyear());
			pstmt.setString(15, timeTable.getSemester());
			pstmt.setString(16, school);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<TimeTable> searchForInfo(int userid,String schoolyear,String semester){
		List<TimeTable> list=new ArrayList<TimeTable>();
		Connection connection=dbconnection.getConnect();
		String sql="SELECT id,courseName,address,cycle,week,time,singleDouble FROM super_course_timetable WHERE user_id=?  AND schoolyear=? AND semester=? AND school = ?";
		PreparedStatement pstmt=null;
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setInt(1,userid);
			pstmt.setString(2, schoolyear);
			pstmt.setString(3, semester);
			pstmt.setString(4, school);
			ResultSet rs=pstmt.executeQuery();
			while (rs.next()) {
				TimeTable timeTable=new TimeTable();
				timeTable.setId(rs.getInt("Id"));
				timeTable.setCourseName(rs.getString("courseName"));
				timeTable.setAddress(rs.getString("address"));
				timeTable.setCycle(rs.getString("cycle"));
				timeTable.setWeek(rs.getString("week"));
				timeTable.setTime(rs.getString("time"));
				timeTable.setSingleDouble(rs.getString("singleDouble"));
				list.add(timeTable);
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
	
	
	public boolean searchByAll(TimeTable timeTable,int userid){
		Connection connection=dbconnection.getConnect();
		String sql="SELECT courseName FROM super_course_timetable WHERE courseName=? and user_id=? and address=? and (cycle=? or cycle is null) and week=? and time=? and schoolyear=? and semester=? and school =?";
		PreparedStatement pstmt=null;
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setString(1, timeTable.getCourseName());
			pstmt.setInt(2, userid);
			pstmt.setString(3, timeTable.getAddress());
			pstmt.setString(4, timeTable.getCycle());
			pstmt.setString(5, timeTable.getWeek());
			pstmt.setString(6, timeTable.getTime());
			pstmt.setString(7, timeTable.getSchoolyear());
			pstmt.setString(8, timeTable.getSemester());
			pstmt.setString(9, school);
			/*pstmt.setString(1, timeTable.getCourseName());
			pstmt.setString(2, timeTable.getCourseCode());
			pstmt.setFloat(3, Float.parseFloat(timeTable.getCredit()+""));
			pstmt.setString(4, timeTable.getType());
			pstmt.setString(5, timeTable.getTeacher());
			pstmt.setString(6, timeTable.getClassId());
			pstmt.setString(7, timeTable.getClassNum());
			pstmt.setInt(8, userid);
			pstmt.setString(9, timeTable.getAddress());
			pstmt.setString(10, timeTable.getCycle());
			pstmt.setString(11, timeTable.getWeek());
			pstmt.setString(12, timeTable.getTime());
			pstmt.setString(13, timeTable.getSingleDouble());*/
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
		String sql="SELECT courseName FROM super_course_timetable WHERE user_id = ? AND school = ?";
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
	public List<TimeTable> searchAll(int userid,String schoolyear,String semester){
		List<TimeTable> list=new ArrayList<TimeTable>();
		Connection connection=dbconnection.getConnect();
		String sql="SELECT id,courseName,address,cycle,week,time,singleDouble FROM super_course_timetable WHERE user_id=?  and schoolyear=? and semester=? and school = ?";
		PreparedStatement pstmt=null;
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setInt(1,userid);
			pstmt.setString(2, schoolyear);
			pstmt.setString(3, semester);
			pstmt.setString(4, school);
			ResultSet rs=pstmt.executeQuery();
			while (rs.next()) {
				TimeTable timeTable=new TimeTable();
				timeTable.setId(rs.getInt("Id"));
				timeTable.setCourseName(rs.getString("courseName"));
				timeTable.setAddress(rs.getString("address"));
				timeTable.setCycle(rs.getString("cycle"));
				timeTable.setWeek(rs.getString("week"));
				timeTable.setTime(rs.getString("time"));
				timeTable.setSingleDouble(rs.getString("singleDouble"));
				list.add(timeTable);
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
}
	