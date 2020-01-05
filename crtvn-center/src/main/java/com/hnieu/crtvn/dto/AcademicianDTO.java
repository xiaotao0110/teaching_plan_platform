package com.hnieu.crtvn.dto;

import java.io.Serializable;

public class AcademicianDTO implements Serializable {
	
	private Integer academicianId;
	private String academicianName;
	private int collegeId;
	private String collegeName;
	private String password;
	
	public Integer getAcademicianId() {
		return academicianId;
	}
	public void setAcademicianId(Integer academicianId) {
		this.academicianId = academicianId;
	}
	public String getAcademicianName() {
		return academicianName;
	}
	public void setAcademicianName(String academicianName) {
		this.academicianName = academicianName;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
