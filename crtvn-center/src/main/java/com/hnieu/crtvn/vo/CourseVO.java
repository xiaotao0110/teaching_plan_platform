package com.hnieu.crtvn.vo;

/**
 * 课程视图
 */
public class CourseVO implements java.io.Serializable{
	
	private int professionId;
	private int collegeId;
	private int courseId;	
	private String professionName;
	private String professioCode;	
	private String courseCode;
	private String courseName;
	private int courseNature;
	private int courseCredit;
	private int courseManner;	
	private String collegeName;
	private int numbers;
	private int courseMark;
	private String startcd;

	public CourseVO() {
		super();
	}

	
	
	public CourseVO(int courseId, String courseName) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
	}

	public CourseVO(int professionId, int collegeId, int courseId, String professionName, String professioCode,
			String courseCode, String courseName, int courseNature, int courseCredit, int courseManner,
			String collegeName ,int courseMark,String startcd) {
		super();
		this.professionId = professionId;
		this.collegeId = collegeId;
		this.courseId = courseId;
		this.professionName = professionName;
		this.professioCode = professioCode;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.courseNature = courseNature;
		this.courseCredit = courseCredit;
		this.courseManner = courseManner;
		this.collegeName = collegeName;
		this.courseMark = courseMark;
		this.startcd = startcd;
	}

	public int getProfessionId() {
		return professionId;
	}

	public void setProfessionId(int professionId) {
		this.professionId = professionId;
	}

	public int getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getProfessionName() {
		return professionName;
	}

	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	public String getProfessioCode() {
		return professioCode;
	}

	public void setProfessioCode(String professioCode) {
		this.professioCode = professioCode;
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

	public int getCourseNature() {
		return courseNature;
	}

	public void setCourseNature(int courseNature) {
		this.courseNature = courseNature;
	}

	public int getCourseCredit() {
		return courseCredit;
	}

	public void setCourseCredit(int courseCredit) {
		this.courseCredit = courseCredit;
	}

	public int getCourseManner() {
		return courseManner;
	}

	public void setCourseManner(int courseManner) {
		this.courseManner = courseManner;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public int getNumbers() {
		return numbers;
	}

	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}

	public int getCourseMark() {
		return courseMark;
	}

	public void setCourseMark(int courseMark) {
		this.courseMark = courseMark;
	}

	public String getStartcd() {
		return startcd;
	}
	
	public void setStartcd(String startcd) {
		this.startcd = startcd;
	}
	
	
	
}