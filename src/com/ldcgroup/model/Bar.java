package com.ldcgroup.model;

import java.io.Serializable;

public class Bar implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String label;
	
	// For Trade & Fund
	private Double fund_type_001 = 0.0; // stacked fund of type 001
	private Double fund_type_002 = 0.0; // stacked fund of type 002
	private Double fund_type_003 = 0.0; // stacked fund of type 003
	private Double fund_type_004 = 0.0; // stacked fund of type 004
	private Double fund_type_005 = 0.0; // stacked fund of type 005

	// For Task & Value
	private Double value_item_001 = 0.0; // stacked value of item 001
	private Double value_item_002 = 0.0; // stacked value of item 002
	private Double value_item_003 = 0.0; // stacked value of item 003
	private Double value_item_004 = 0.0; // stacked value of item 004
	private Double value_item_005 = 0.0; // stacked value of item 005
	private Double value_item_006 = 0.0; // stacked value of item 006
	private Double value_item_007 = 0.0; // stacked value of item 007
	private Double value_item_008 = 0.0; // stacked value of item 008
	private Double value_item_101 = 0.0; // stacked value of item 101

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
	public Double getValue_item_001() {
		return value_item_001;
	}
	public void setValue_item_001(Double value_item_001) {
		this.value_item_001 = value_item_001;
	}
	
	public Double getValue_item_002() {
		return value_item_002;
	}
	public void setValue_item_002(Double value_item_002) {
		this.value_item_002 = value_item_002;
	}
	
	public Double getValue_item_003() {
		return value_item_003;
	}
	public void setValue_item_003(Double value_item_003) {
		this.value_item_003 = value_item_003;
	}
	
	public Double getValue_item_004() {
		return value_item_004;
	}
	public void setValue_item_004(Double value_item_004) {
		this.value_item_004 = value_item_004;
	}
	
	public Double getValue_item_005() {
		return value_item_005;
	}
	public void setValue_item_005(Double value_item_005) {
		this.value_item_005 = value_item_005;
	}
	
	public Double getValue_item_006() {
		return value_item_006;
	}
	public void setValue_item_006(Double value_item_006) {
		this.value_item_006 = value_item_006;
	}
	
	public Double getValue_item_007() {
		return value_item_007;
	}
	public void setValue_item_007(Double value_item_007) {
		this.value_item_007 = value_item_007;
	}
	
	public Double getValue_item_008() {
		return value_item_008;
	}
	public void setValue_item_008(Double value_item_008) {
		this.value_item_008 = value_item_008;
	}
	
	public Double getValue_item_101() {
		return value_item_101;
	}
	public void setValue_item_101(Double value_item_101) {
		this.value_item_101 = value_item_101;
	}
}
