package com.ldcgroup.model;

import java.io.Serializable;

public class Bar implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String label;
	private Double fund_type_001 = 0.0; // stacked fund of type 001
	private Double fund_type_002 = 0.0; // stacked fund of type 002
	private Double fund_type_003 = 0.0; // stacked fund of type 003
	private Double fund_type_004 = 0.0; // stacked fund of type 004
	private Double fund_type_005 = 0.0; // stacked fund of type 005
	private Long time;

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public Double getFund_type_001() {
		return fund_type_001;
	}
	public void setFund_type_001(Double fund_type_001) {
		this.fund_type_001 = fund_type_001;
	}
	
	public Double getFund_type_002() {
		return fund_type_002;
	}
	public void setFund_type_002(Double fund_type_002) {
		this.fund_type_002 = fund_type_002;
	}
	
	public Double getFund_type_003() {
		return fund_type_003;
	}
	public void setFund_type_003(Double fund_type_003) {
		this.fund_type_003 = fund_type_003;
	}
	
	public Double getFund_type_004() {
		return fund_type_004;
	}
	public void setFund_type_004(Double fund_type_004) {
		this.fund_type_004 = fund_type_004;
	}
	
	public Double getFund_type_005() {
		return fund_type_005;
	}
	public void setFund_type_005(Double fund_type_005) {
		this.fund_type_005 = fund_type_005;
	}
	
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
}
