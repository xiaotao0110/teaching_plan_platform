package com.hnieu.crtvn.vo;

import java.io.Serializable;

public class AcademicianVO implements Serializable{

	private Integer academicianId;
	private String academicianName;
	private int collegeId;
	private String collegeName;
	private String employeeNo;
	private String password;
	
	public AcademicianVO() {
		super();
	}
	public AcademicianVO(Integer academicianId, String academicianName, int collegeId, String collegeName,
			String employeeNo, String password) {
		super();
		this.academicianId = academicianId;
		this.academicianName = academicianName;
		this.collegeId = collegeId;
		this.collegeName = collegeName;
		this.employeeNo = employeeNo;
		this.password = password;
	}
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
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
