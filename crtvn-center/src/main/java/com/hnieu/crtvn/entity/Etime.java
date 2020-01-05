package com.hnieu.crtvn.entity;
// Generated 2019-2-23 22:25:06 by Hibernate Tools 4.3.1.Final

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Etime generated by hbm2java
 */
@Entity
@Table(name = "etime", catalog = "crtvn", uniqueConstraints = @UniqueConstraint(columnNames = "etime"))
public class Etime implements java.io.Serializable {

	private Integer id;
	private College college;
	private Date stime;
	private Date etime;
	private int mark;
	private Integer reserve1;
	private String reserve2;
	private Set<Examination> examinations = new HashSet<Examination>(0);

	public Etime() {
	}

	public Etime(College college, Date stime, Date etime, int mark) {
		this.college = college;
		this.stime = stime;
		this.etime = etime;
		this.mark = mark;
	}

	public Etime(College college, Date stime, Date etime, int mark, Integer reserve1, String reserve2,
			Set<Examination> examinations) {
		this.college = college;
		this.stime = stime;
		this.etime = etime;
		this.mark = mark;
		this.reserve1 = reserve1;
		this.reserve2 = reserve2;
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
	@JoinColumn(name = "CID", nullable = false)
	public College getCollege() {
		return this.college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "stime", nullable = false, length = 19)
	public Date getStime() {
		return this.stime;
	}

	public void setStime(Date stime) {
		this.stime = stime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "etime", unique = true, nullable = false, length = 19)
	public Date getEtime() {
		return this.etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}

	@Column(name = "mark", nullable = false)
	public int getMark() {
		return this.mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "etime")
	public Set<Examination> getExaminations() {
		return this.examinations;
	}

	public void setExaminations(Set<Examination> examinations) {
		this.examinations = examinations;
	}

}
