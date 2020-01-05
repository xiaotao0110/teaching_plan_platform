package com.hnieu.crtvn.vo;

import java.io.Serializable;

public class ProfessionVO implements Serializable {
	
	private int collegeId;
	private String collegeName;
	private int professionId;
	private String professionName;
	private String professionCode;
	private String departmentHead;
	private String secretary;

	public ProfessionVO(int collegeId, String collegeName, int professionId, String professionName,
			String professionCode, String departmentHead, String secretary) {
		super();
		this.collegeId = collegeId;
		this.collegeName = collegeName;
		this.professionId = professionId;
		this.professionName = professionName;
		this.professionCode = professionCode;
		this.departmentHead = departmentHead;
		this.secretary = secretary;
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
	public String getProfessionCode() {
		return professionCode;
	}
	public void setProfessionCode(String professionCode) {
		this.professionCode = professionCode;
	}
	public String getDepartmentHead() {
		return departmentHead;
	}
	public void setDepartmentHead(String departmentHead) {
		this.departmentHead = departmentHead;
	}
	public String getSecretary() {
		return secretary;
	}
	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}
	
	
}
