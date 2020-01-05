package com.db.entity;

public class Exam {
    private Integer id;

    private Integer eid;

    private Integer ccid;

    private Integer sid;

    private Integer seatno;

    private Integer reserve1;

    private String reserve2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getCcid() {
        return ccid;
    }

    public void setCcid(Integer ccid) {
        this.ccid = ccid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getSeatno() {
        return seatno;
    }

    public void setSeatno(Integer seatno) {
        this.seatno = seatno;
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