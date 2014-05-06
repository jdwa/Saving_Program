package com.ldcgroup.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Roll")
public class Roll implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String ro_no;
	private Double value; // Subscription fund
	private String remark;
	private Date pay_date;
	private Date settlement_date;
	private Date creation_date;
	private String timestamp;
	private Boolean valid;
	private Company company;
	private transient Double subvalue; // (Value of specified member) Not permanent data, dynamically calculated by action.
	
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ro_no", unique = true)
	public String getRo_no() {
		return ro_no;
	}
	public void setRo_no(String ro_no) {
		this.ro_no = ro_no;
	}
	
	/* For struts2 jquery autocompleter use, it's a workaround solution for the autocompleter bug. */
	public String getRo_no_widget() {
		return ro_no;
	}
	public void setRo_no_widget(String ro_no_widget) {
		this.ro_no = ro_no_widget;
	}

	@Column(name = "value")
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	@Column(name = "pay_date")
	public Date getPay_date() {
		return pay_date;
	}
	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
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

	@Column(name = "valid")
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}

	public Double getSubvalue() {
		return subvalue;
	}
	public void setSubvalue(Double subvalue) {
		this.subvalue = subvalue;
	}
	
	public String toString() {
		return "id:" + id + " ro_no:" + ro_no // + " type_id:" + type_id
				+ " value:" + value + " remark:" + remark
				+ " creation_date:" + creation_date;
	}
}
