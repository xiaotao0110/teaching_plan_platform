package com.hnieu.crtvn.dto;

import java.io.Serializable;

public class ClasssDTO implements Serializable {

	private int professionId;
	private String classsCode;
	private int courseId;
	public int getProfessionId() {
		return professionId;
	}
	public void setProfessionId(int professionId) {
		this.professionId = professionId;
	}
	public String getClasssCode() {
		return classsCode;
	}
	public void setClasssCode(String classsCode) {
		this.classsCode = classsCode;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}


}
