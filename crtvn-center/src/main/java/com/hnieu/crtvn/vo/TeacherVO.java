package com.hnieu.crtvn.vo;

import java.io.Serializable;

public class TeacherVO implements Serializable{
		
	private int collegeId;
	private int thisId;
	private String collegeName;
	private int professionId;
	private String professionName;	
	private int courseId;	
	private String courseCode;
	private String courseName;
	private int teacherId;
	private String teacherCode;
	private String teacherName;
	private int gender;
	private int age;
	private String position;
	private String phone;
	private int mark;
	private int status ;
	
	public TeacherVO(int collegeId, String collegeName, int professionId, String professionName, int courseId,
			String courseCode, String courseName, int teacherId, String teacherCode, String teacherName, int gender,
			int age, String position, String phone, int mark,int status) {
		super();
		this.collegeId = collegeId;
		this.collegeName = collegeName;
		this.professionId = professionId;
		this.professionName = professionName;
		this.courseId = courseId;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.teacherId = teacherId;
		this.teacherCode = teacherCode;
		this.teacherName = teacherName;
		this.gender = gender;
		this.age = age;
		this.position = position;
		this.phone = phone;
		this.mark = mark;
		this.status = status;
	}
	public int getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public int getProfessionId() {
		return professionId;
	}
	public void setProfessionId(int professionId) {
		this.professionId = professionId;
	}
	public String getProfessionName() {
		return professionName;
	}
	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
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
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherCode() {
		return teacherCode;
	}
	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public int getThisId() {
		return thisId;
	}
	public void setThisId(int thisId) {
		this.thisId = thisId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
		
}
