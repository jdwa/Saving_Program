package com.ldcgroup.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Rate")
public class Rate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date rate_date;
	private Double rate_value;
	private String remark;
	private Date creation_date;
	private String timestamp;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "rate_date")
	public Date getRate_date() {
		return rate_date;
	}
	public void setRate_date(Date rate_date) {
		this.rate_date = rate_date;
	}
	
	@Column(name = "rate_value")
	public Double getRate_value() {
		return rate_value;
	}
	public void setRate_value(Double rate_value) {
		this.rate_value = rate_value;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
}
