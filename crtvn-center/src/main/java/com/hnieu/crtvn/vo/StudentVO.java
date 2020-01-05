package com.hnieu.crtvn.vo;

import java.io.Serializable;

public class StudentVO implements Serializable {
	
	private int studentId;
	private int collegeId;
	private String collegeName;
	private int professionId;
	private String professionName;
	private int classsId;		
	private String classsCode;	
	private String SID; 
	private String studentName;
	private int mark;
	private String reason;
	private int gender;
	private int age;
	private String idcard;
	private String phone;
	private String grade;
	

	public StudentVO() {
		super();
	}
	

	public StudentVO(int studentId, int collegeId, String collegeName, int professionId, String professionName,
			int classsId, String classsCode, String sID, String studentName, int gender, int age, String idcard,
			String phone, String grade) {
		super();
		this.studentId = studentId;
		this.collegeId = collegeId;
		this.collegeName = collegeName;
		this.professionId = professionId;
		this.professionName = professionName;
		this.classsId = classsId;
		this.classsCode = classsCode;
		SID = sID;
		this.studentName = studentName;
		this.gender = gender;
		this.age = age;
		this.idcard = idcard;
		this.phone = phone;
		this.grade = grade;
	}




	public StudentVO(int studentId, int collegeId, String collegeName, int professionId, String professionName,
			int classsId, String classsCode, String sID, String studentName, int mark, String reason, int gender,
			int age, String idcard, String phone, String grade) {
		super();
		this.studentId = studentId;
		this.collegeId = collegeId;
		this.collegeName = collegeName;
		this.professionId = professionId;
		this.professionName = professionName;
		this.classsId = classsId;
		this.classsCode = classsCode;
		this.SID = sID;
		this.studentName = studentName;
		this.mark = mark;
		this.reason = reason;
		this.gender = gender;
		this.age = age;
		this.idcard = idcard;
		this.phone = phone;
		this.grade = grade;
	}


	public int getStudentId() {
		return studentId;
	}


	public void setStudentId(int studentId) {
		this.studentId = studentId;
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


	public int getClasssId() {
		return classsId;
	}


	public void setClasssId(int classsId) {
		this.classsId = classsId;
	}


	public String getClasssCode() {
		return classsCode;
	}


	public void setClasssCode(String classsCode) {
		this.classsCode = classsCode;
	}


	public String getSID() {
		return SID;
	}


	public void setSID(String sID) {
		SID = sID;
	}


	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}


	public int getMark() {
		return mark;
	}


	public void setMark(int mark) {
		this.mark = mark;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
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


	public String getIdcard() {
		return idcard;
	}


	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getGrade() {
		return grade;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
	
}
