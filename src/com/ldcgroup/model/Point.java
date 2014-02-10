package com.ldcgroup.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Point")
public class Point implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Double value; // value of points
	private String remark;
	private Date settlement_date;
	private Date creation_date;
	private String timestamp;
	private Member member;
	private Task task;
	private Item item;
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
	
	@Column(name = "value")
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
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

	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}	
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}	

	public String toString() {
		return "id:" + id + " task:[" + task + "] item:["  + item + "] member:[" + member 
				+ "] value:" + value + " remark:" + remark
				+ " creation_date:" + creation_date;
	}
}
