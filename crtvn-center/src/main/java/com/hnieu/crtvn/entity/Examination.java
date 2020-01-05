package com.hnieu.crtvn.entity;
// Generated 2019-2-23 22:25:06 by Hibernate Tools 4.3.1.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Examination generated by hbm2java
 */
@Entity
@Table(name = "examination", catalog = "crtvn")
public class Examination implements java.io.Serializable {

	private Integer id;
	private Classroom classroom;
	private Course course;
	private Etime etime;
	private Profession profession;
	private Teacher teacherByTwotid;
	private Teacher teacherByOnetid;
	private String code;
	private int mark;
	private int numbers;
	private Integer reserve1;
	private String reserve2;
	private Set<Exam> exams = new HashSet<Exam>(0);

	public Examination() {
	}

	public Examination(Classroom classroom, Course course, Etime etime, Profession profession, Teacher teacherByTwotid,
			Teacher teacherByOnetid, String code, int mark, int numbers) {
		this.classroom = classroom;
		this.course = course;
		this.etime = etime;
		this.profession = profession;
		this.teacherByTwotid = teacherByTwotid;
		this.teacherByOnetid = teacherByOnetid;
		this.code = code;
		this.mark = mark;
		this.numbers = numbers;
	}

	public Examination(Classroom classroom, Course course, Etime etime, Profession profession, Teacher teacherByTwotid,
			Teacher teacherByOnetid, String code, int mark, int numbers, Integer reserve1, String reserve2,
			Set<Exam> exams) {
		this.classroom = classroom;
		this.course = course;
		this.etime = etime;
		this.profession = profession;
		this.teacherByTwotid = teacherByTwotid;
		this.teacherByOnetid = teacherByOnetid;
		this.code = code;
		this.mark = mark;
		this.numbers = numbers;
		this.reserve1 = reserve1;
		this.reserve2 = reserve2;
		this.exams = exams;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CSID", nullable = false)
	public Classroom getClassroom() {
		return this.classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COID", nullable = false)
	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TTID", nullable = false)
	public Etime getEtime() {
		return this.etime;
	}

	public void setEtime(Etime etime) {
		this.etime = etime;
	}

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID", nullable = false)
	public Profession getProfession() {
		return this.profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TWOTID", nullable = false)
	public Teacher getTeacherByTwotid() {
		return this.teacherByTwotid;
	}

	public void setTeacherByTwotid(Teacher teacherByTwotid) {
		this.teacherByTwotid = teacherByTwotid;
	}

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ONETID", nullable = false)
	public Teacher getTeacherByOnetid() {
		return this.teacherByOnetid;
	}

	public void setTeacherByOnetid(Teacher teacherByOnetid) {
		this.teacherByOnetid = teacherByOnetid;
	}

	@Column(name = "code", nullable = false, length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "mark", nullable = false)
	public int getMark() {
		return this.mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	@Column(name = "numbers", nullable = false)
	public int getNumbers() {
		return this.numbers;
	}

	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}

	@Column(name = "reserve1")
	public Integer getReserve1() {
		return this.reserve1;
	}

	public void setReserve1(Integer reserve1) {
		this.reserve1 = reserve1;
	}

	@Column(name = "reserve2", length = 50)
	public String getReserve2() {
		return this.reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "examination")
	public Set<Exam> getExams() {
		return this.exams;
	}

	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}

}