package com.hnieu.crtvn.vo;

import java.io.Serializable;
import java.util.Date;

public class DownloadExaminationVO implements Serializable {
	
	private String examinationCode;	
	private String professionName;
	private String courseCode;
	private String courseName;
    private String classroomCode;
	private String teacherName1;
	private String teacherName2;	
	private Date stime;
	private Date etime;	
	private int number;
	
	public DownloadExaminationVO() {
		super();
	}
	public DownloadExaminationVO(String examinationCode, String professionName, String courseCode, String courseName,
			String classroomCode, String teacherName1, String teacherName2, Date stime, Date etime, int number) {
		super();
		this.examinationCode = examinationCode;
		this.professionName = professionName;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.classroomCode = classroomCode;
		this.teacherName1 = teacherName1;
		this.teacherName2 = teacherName2;
		this.stime = stime;
		this.etime = etime;
		this.number = number;
	}
	public String getExaminationCode() {
		return examinationCode;
	}
	public void setExaminationCode(String examinationCode) {
		this.examinationCode = examinationCode;
	}
	public String getProfessionName() {
		return professionName;
	}
	public void setProfessionName(String professionName) {
		this.professionName = professionName;
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
	public String getClassroomCode() {
		return classroomCode;
	}
	public void setClassroomCode(String classroomCode) {
		this.classroomCode = classroomCode;
	}
	public String getTeacherName1() {
		return teacherName1;
	}
	public void setTeacherName1(String teacherName1) {
		this.teacherName1 = teacherName1;
	}
	public String getTeacherName2() {
		return teacherName2;
	}
	public void setTeacherName2(String teacherName2) {
		this.teacherName2 = teacherName2;
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
