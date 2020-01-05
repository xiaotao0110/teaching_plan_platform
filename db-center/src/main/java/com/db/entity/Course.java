package com.db.entity;

public class Course {
    private Integer id;

    private Integer pid;

    private String name;

    private String startcd;

    private String code;

    private Integer mark;

    private Integer nature;

    private Integer credit;

    private Integer manner;

    private Integer reserve1;

    private String reserve2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStartcd() {
        return startcd;
    }

    public void setStartcd(String startcd) {
        this.startcd = startcd == null ? null : startcd.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getManner() {
        return manner;
    }

    public void setManner(Integer manner) {
        this.manner = manner;
    }

    public Integer getReserve1() {
        return reserve1;
    }

    public void setReserve1(Integer reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2 == null ? null : reserve2.trim();
    }
}