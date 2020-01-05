package com.hnieu.crtvn.dto;

import java.io.Serializable;

public class CourseDTO implements Serializable {
	
	private int collegeId ;
	private int courseId;
	private int professionId ;
	private String courseCode;
	private String  courseName;
	private int courseManner;
	private int teacherId;
	private int courseMark;
	
	public int getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}
	public int getProfessionId() {
		return professionId;
	}
	public void setProfessionId(int professionId) {
		this.professionId = professionId;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getCourseManner() {
		return courseManner;
	}
	public void setCourseManner(int courseManner) {
		this.courseManner = courseManner;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public int getCourseMark() {
		return courseMark;
	}
	public void setCourseMark(int courseMark) {
		this.courseMark = courseMark;
	}
	
	
}
