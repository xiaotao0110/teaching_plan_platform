package com.hnieu.crtvn.dto;

import java.io.Serializable;

public class ExaminationDTO implements Serializable {

	private Integer examinationId;
	private Integer professionId;
	private Integer collegeId;
	private Integer classroomId;
	private Integer courseId;
	private Integer etimeId;
	private Integer teacherId1;
	private Integer teacherId2;
	private Integer mark;
	private Integer status ; //监考一、二
	private Integer renew ;  //重新排考标记 
	
	private String classrooms;
	private String  teachers;
	
	private String download; //下载标记
	
	public Integer getExaminationId() {
		return examinationId;
	}
	public void setExaminationId(Integer examinationId) {
		this.examinationId = examinationId;
	}
	public Integer getProfessionId() {
		return professionId;
	}
	public void setProfessionId(Integer professionId) {
		this.professionId = professionId;
	}
	public Integer getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(Integer collegeId) {
		this.collegeId = collegeId;
	}
	public Integer getClassroomId() {
		return classroomId;
	}
	public void setClassroomId(Integer classroomId) {
		this.classroomId = classroomId;
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
	public Integer getTeacherId1() {
		return teacherId1;
	}
	public void setTeacherId1(Integer teacherId1) {
		this.teacherId1 = teacherId1;
	}
	public Integer getTeacherId2() {
		return teacherId2;
	}
	public void setTeacherId2(Integer teacherId2) {
		this.teacherId2 = teacherId2;
	}
	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRenew() {
		return renew;
	}
	public void setRenew(Integer renew) {
		this.renew = renew;
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
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
	
	
	
}
