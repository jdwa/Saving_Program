package com.ldcgroup.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Term")
public class Term implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String term_no;
	private String term_description;
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
	
	@Column(name = "term_no", unique = true)
	public String getTerm_no() {
		return term_no;
	}
	public void setTerm_no(String term_no) {
		this.term_no = term_no;
	}
	
	@Column(name = "term_description")
	public String getTerm_description() {
		return term_description;
	}
	public void setTerm_description(String term_description) {
		this.term_description = term_description;
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
