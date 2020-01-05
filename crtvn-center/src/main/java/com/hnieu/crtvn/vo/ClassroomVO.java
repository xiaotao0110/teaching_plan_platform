package com.hnieu.crtvn.vo;

import java.io.Serializable;

public class ClassroomVO implements Serializable {
	
	private Integer  classroomId;
	private int thisId ;
	private int collegeId;
	private String code;
	private int building;
	private int seats;
	private int number;
	private int mark;
	private String collegeName;
	private int status;
	
	public ClassroomVO() {
		super();
	}


	public ClassroomVO(Integer classroomId, int collegeId, String code, int status ,int building, int seats, int number, int mark,
			String collegeName) {
		super();
		this.classroomId = classroomId;
		this.collegeId = collegeId;
		this.code = code;
		this.building = building;
		this.seats = seats;
		this.number = number;
		this.mark = mark;
		this.collegeName = collegeName;
		this.status = status;
	}


	public Integer getClassroomId() {
		return classroomId;
	}


	public void setClassroomId(Integer classroomId) {
		this.classroomId = classroomId;
	}


	public int getCollegeId() {
		return collegeId;
	}


	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public int getBuilding() {
		return building;
	}


	public void setBuilding(int building) {
		this.building = building;
	}


	public int getSeats() {
		return seats;
	}


	public void setSeats(int seats) {
		this.seats = seats;
	}


	public int getMark() {
		return mark;
	}


	public void setMark(int mark) {
		this.mark = mark;
	}


	public String getCollegeName() {
		return collegeName;
	}


	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}


	public int getThisId() {
		return thisId;
	}


	public void setThisId(int thisId) {
		this.thisId = thisId;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}

	
}
