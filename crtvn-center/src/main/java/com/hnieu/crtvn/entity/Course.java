package com.hnieu.crtvn.entity;
// Generated 2019-3-17 11:05:33 by Hibernate Tools 4.3.1.Final

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Course generated by hbm2java
 */
@Entity
@Table(name = "course", catalog = "crtvn", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Course implements java.io.Serializable {

	private Integer id;
	private Profession profession;
	private String name;
	private String startcd;
	private String code;
	private int mark;
	private int nature;
	private int credit;
	private int manner;
	private Integer reserve1;
	private String reserve2;
	private Set<Studentselection> studentselections = new HashSet<Studentselection>(0);
	private Set<Teach> teaches = new HashSet<Teach>(0);
	private Set<Classselection> classselections = new HashSet<Classselection>(0);
	private Set<Examination> examinations = new HashSet<Examination>(0);

	public Course() {
	}

	public Course(Profession profession, String name, String startcd, String code, int mark, int nature, int credit,
			int manner) {
		this.profession = profession;
		this.name = name;
		this.startcd = startcd;
		this.code = code;
		this.mark = mark;
		this.nature = nature;
		this.credit = credit;
		this.manner = manner;
	}

	public Course(Profession profession, String name, String startcd, String code, int mark, int nature, int credit,
			int manner, Integer reserve1, String reserve2, Set<Studentselection> studentselections, Set<Teach> teaches,
			Set<Classselection> classselections, Set<Examination> examinations) {
		this.profession = profession;
		this.name = name;
		this.startcd = startcd;
		this.code = code;
		this.mark = mark;
		this.nature = nature;
		this.credit = credit;
		this.manner = manner;
		this.reserve1 = reserve1;
		this.reserve2 = reserve2;
		this.studentselections = studentselections;
		this.teaches = teaches;
		this.classselections = classselections;
		this.examinations = examinations;
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
	@JoinColumn(name = "PID", nullable = false)
	public Profession getProfession() {
		return this.profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "startcd", nullable = false, length = 20)
	public String getStartcd() {
		return this.startcd;
	}

	public void setStartcd(String startcd) {
		this.startcd = startcd;
	}

	@Column(name = "code", unique = true, nullable = false, length = 20)
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

	@Column(name = "nature", nullable = false)
	public int getNature() {
		return this.nature;
	}

	public void setNature(int nature) {
		this.nature = nature;
	}

	@Column(name = "credit", nullable = false)
	public int getCredit() {
		return this.credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	@Column(name = "manner", nullable = false)
	public int getManner() {
		return this.manner;
	}

	public void setManner(int manner) {
		this.manner = manner;
	}

	@Column(name = "reserve1")
	public Integer getReserve1() {
		return this.reserve1;
	}

	public void setReserve1(Integer reserve1) {
		this.reserve1 = reserve1;
	}

	@Column(name = "reserve2")
	public String getReserve2() {
		return this.reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	public Set<Studentselection> getStudentselections() {
		return this.studentselections;
	}

	public void setStudentselections(Set<Studentselection> studentselections) {
		this.studentselections = studentselections;
	}

	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	public Set<Teach> getTeaches() {
		return this.teaches;
	}

	public void setTeaches(Set<Teach> teaches) {
		this.teaches = teaches;
	}

	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	public Set<Classselection> getClassselections() {
		return this.classselections;
	}

	public void setClassselections(Set<Classselection> classselections) {
		this.classselections = classselections;
	}

	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	public Set<Examination> getExaminations() {
		return this.examinations;
	}

	public void setExaminations(Set<Examination> examinations) {
		this.examinations = examinations;
	}

}
