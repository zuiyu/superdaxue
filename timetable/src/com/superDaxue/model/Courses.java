package com.superDaxue.model;

public class Courses {
	private int id;
	private String  coursesname;//课程名
	private String courseCode;//课程编号
	private double credit;//学分
	private String score;//成绩
	private String schoolYear;//学年
	private String semester;//学期
	
	private String type;//课程类型  
	private String leanType;//初修类型
	private String getType; //取得类型
	private String checkType;//考核方式方式
	private String remark;//备注
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCoursesname() {
		return coursesname;
	}
	public void setCoursesname(String coursesname) {
		this.coursesname = coursesname;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getGetType() {
		return getType;
	}
	public void setGetType(String getType) {
		this.getType = getType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getLeanType() {
		return leanType;
	}
	public void setLeanType(String leanType) {
		this.leanType = leanType;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	@Override
	public String toString() {
		return "Courses [id=" + id + ", coursesname=" + coursesname
				+ ", courseCode=" + courseCode + ", credit=" + credit
				+ ", score=" + score + ", schoolYear=" + schoolYear
				+ ", semester=" + semester + ", type=" + type + ", leanType="
				+ leanType + ", getType=" + getType + ", checkType="
				+ checkType + ", remark=" + remark + "]";
	}
	

	
}
