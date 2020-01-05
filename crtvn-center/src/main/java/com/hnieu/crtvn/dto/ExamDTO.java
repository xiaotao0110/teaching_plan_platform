package com.hnieu.crtvn.dto;

import java.io.Serializable;

public class ExamDTO implements Serializable{

	private Integer mark; //自动，手动
	private Integer examinationId;
	private Integer classsId;
	private Integer studentId;
	
	private Integer professionId;
	private Integer courseId;
	private Integer etimeId;
	private String classrooms;
	private String  teachers;
	
	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	public Integer getExaminationId() {
		return examinationId;
	}
	public void setExaminationId(Integer examinationId) {
		this.examinationId = examinationId;
	}
	public Integer getClasssId() {
		return classsId;
	}
	public void setClasssId(Integer classsId) {
		this.classsId = classsId;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Integer getProfessionId() {
		return professionId;
	}
	public void setProfessionId(Integer professionId) {
		this.professionId = professionId;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public Integer getEtimeId() {
		return etimeId;
	}
	public void setEtimeId(Integer etimeId) {
		this.etimeId = etimeId;
	}
	public String getClassrooms() {
		return classrooms;
	}
	public void setClassrooms(String classrooms) {
		this.classrooms = classrooms;
	}
	public String getTeachers() {
		return teachers;
	}
	public void setTeachers(String teachers) {
		this.teachers = teachers;
	}

	
	
}
