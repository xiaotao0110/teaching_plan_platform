package com.hnieu.crtvn.vo;

import java.io.Serializable;

public class DownloadExamVO implements Serializable{

	private String sid;
	private String studentName;
	private String classsCode;
	private String courseName;
	private String examinationCode;
	private String examinationTime;
	private Integer seatNo;

	public DownloadExamVO() {
		super();
	}
	public DownloadExamVO(String sid, String studentName, String classsCode, String courseName, String examinationCode,
			String examinationTime, Integer seatNo) {
		super();
		this.sid = sid;
		this.studentName = studentName;
		this.classsCode = classsCode;
		this.courseName = courseName;
		this.examinationCode = examinationCode;
		this.examinationTime = examinationTime;
		this.seatNo = seatNo;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClasssCode() {
		return classsCode;
	}
	public void setClasssCode(String classsCode) {
		this.classsCode = classsCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getExaminationCode() {
		return examinationCode;
	}
	public void setExaminationCode(String examinationCode) {
		this.examinationCode = examinationCode;
	}
	public String getExaminationTime() {
		return examinationTime;
	}
	public void setExaminationTime(String examinationTime) {
		this.examinationTime = examinationTime;
	}
	public Integer getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(Integer seatNo) {
		this.seatNo = seatNo;
	}
	
	
}
