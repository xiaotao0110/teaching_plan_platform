package com.hnieu.crtvn.vo;

import java.io.Serializable;
import java.util.Date;

public class ExaminationVO implements Serializable {
	
	private Integer examinationId;
	private String examinationCode;
	
	private Integer professionId;
	private String professionName;
	
	private Integer courseId;
	private String courseName;
	private String courseCode;
	
	private Integer classroomId;
	private String classroomCode;

	private Integer teacherId1;
	private String teacherName1;
	private Integer teacherId2;
	private String teacherName2;
	
	private Integer etimeId;
	private Date stime;
	private Date etime;
	
	private int number;


	public ExaminationVO() {
		super();
	}


	public ExaminationVO(Integer examinationId, String examinationCode, Integer professionId, String professionName,
			Integer courseId, String courseName, String courseCode, Integer classroomId, String classroomCode,
			Integer teacherId1, String teacherName1, Integer teacherId2, String teacherName2, Integer etimeId,
			Date stime, Date etime, int number) {
		super();
		this.examinationId = examinationId;
		this.examinationCode = examinationCode;
		this.professionId = professionId;
		this.professionName = professionName;
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseCode = courseCode;
		this.classroomId = classroomId;
		this.classroomCode = classroomCode;
		this.teacherId1 = teacherId1;
		this.teacherName1 = teacherName1;
		this.teacherId2 = teacherId2;
		this.teacherName2 = teacherName2;
		this.etimeId = etimeId;
		this.stime = stime;
		this.etime = etime;
		this.number = number;
	}


	public Integer getExaminationId() {
		return examinationId;
	}


	public void setExaminationId(Integer examinationId) {
		this.examinationId = examinationId;
	}


	public String getExaminationCode() {
		return examinationCode;
	}


	public void setExaminationCode(String examinationCode) {
		this.examinationCode = examinationCode;
	}


	public Integer getProfessionId() {
		return professionId;
	}


	public void setProfessionId(Integer professionId) {
		this.professionId = professionId;
	}


	public String getProfessionName() {
		return professionName;
	}


	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}


	public Integer getCourseId() {
		return courseId;
	}


	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}


	public String getCourseName() {
		return courseName;
	}


	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}


	public String getCourseCode() {
		return courseCode;
	}


	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}


	public Integer getClassroomId() {
		return classroomId;
	}


	public void setClassroomId(Integer classroomId) {
		this.classroomId = classroomId;
	}


	public String getClassroomCode() {
		return classroomCode;
	}


	public void setClassroomCode(String classroomCode) {
		this.classroomCode = classroomCode;
	}


	public Integer getTeacherId1() {
		return teacherId1;
	}


	public void setTeacherId1(Integer teacherId1) {
		this.teacherId1 = teacherId1;
	}


	public String getTeacherName1() {
		return teacherName1;
	}


	public void setTeacherName1(String teacherName1) {
		this.teacherName1 = teacherName1;
	}


	public Integer getTeacherId2() {
		return teacherId2;
	}


	public void setTeacherId2(Integer teacherId2) {
		this.teacherId2 = teacherId2;
	}


	public String getTeacherName2() {
		return teacherName2;
	}


	public void setTeacherName2(String teacherName2) {
		this.teacherName2 = teacherName2;
	}


	public Integer getEtimeId() {
		return etimeId;
	}


	public void setEtimeId(Integer etimeId) {
		this.etimeId = etimeId;
	}


	public Date getStime() {
		return stime;
	}


	public void setStime(Date stime) {
		this.stime = stime;
	}


	public Date getEtime() {
		return etime;
	}


	public void setEtime(Date etime) {
		this.etime = etime;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}



}
