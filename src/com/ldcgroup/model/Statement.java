package com.ldcgroup.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Statement")
public class Statement implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Double fund; // Subscription fund
	private String remark;
	private Date settlement_date;
	private Date creation_date;
	private String timestamp;
	private Member member;
	private Trade trade;
	private Type type;
	private Company company;
	
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "fund")
	public Double getFund() {
		return fund;
	}
	public void setFund(Double fund) {
		this.fund = fund;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "settlement_date")
	public Date getSettlement_date() {
		return settlement_date;
	}
	public void setSettlement_date(Date settlement_date) {
		this.settlement_date = settlement_date;
	}

	@Column(name = "creation_date")
	public Date getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	@Column(name = "timestamp")
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}	

	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}	

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}	
	
	public Trade getTrade() {
		return trade;
	}
	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}	

	public String toString() {
		return "id:" + id + " trade:[" + trade + "] type:["  + type + "] member:[" + member 
				+ "] fund:" + fund + " remark:" + remark
				+ " creation_date:" + creation_date;
	}
}
