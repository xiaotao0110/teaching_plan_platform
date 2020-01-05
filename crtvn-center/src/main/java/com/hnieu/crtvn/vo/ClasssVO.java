package com.hnieu.crtvn.vo;

import java.io.Serializable;

public class ClasssVO implements Serializable {
	
	private int collegeId;
	private int thisId ;
	private String collegeName;	
	private int professionId;
	private String professionName;	
	private int teacherId;
	private String teacherName;
	private int classsId;
	private String classsCode;
	private int numbers;
	
	
	public ClasssVO() {
		super();
	}

	
	public ClasssVO(int numbers) {
		super();
		this.numbers = numbers;
	}

	

	public ClasssVO(int classsId, String classsCode) {
		super();
		this.classsId = classsId;
		this.classsCode = classsCode;
	}


	public ClasssVO(int collegeId, String collegeName, int professionId, String professionName, int teacherId,
			String teacherName, int classsId, String classsCode) {
		super();
		this.collegeId = collegeId;
		this.collegeName = collegeName;
		this.professionId = professionId;
		this.professionName = professionName;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.classsId = classsId;
		this.classsCode = classsCode;
	}


	public ClasssVO(int collegeId, String collegeName, int professionId, String professionName, int teacherId,
			String teacherName, int classsId, String classsCode, int numbers) {
		super();
		this.collegeId = collegeId;
		this.collegeName = collegeName;
		this.professionId = professionId;
		this.professionName = professionName;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.classsId = classsId;
		this.classsCode = classsCode;
		this.numbers = numbers;
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

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
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

	public int getNumbers() {
		return numbers;
	}

	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}


	public int getThisId() {
		return thisId;
	}


	public void setThisId(int thisId) {
		this.thisId = thisId;
	}
	
	
}
