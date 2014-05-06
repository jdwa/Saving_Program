package com.ldcgroup.util;

import java.util.Comparator;
import com.ldcgroup.model.Pay;

public class PayComparator implements Comparator<Pay> {

	public static int SORT_BY_ID = 0;
	public static int SORT_BY_RO_NO = 1;
	public static int SORT_BY_ACCOUNT = 2;
	public static int SORT_BY_COMPANY = 3;
	public static int SORT_BY_VALUE = 4;
	public static int SORT_BY_TERM = 5;
	public static int SORT_BY_SETTLEMENT = 6;
	public static int SORT_BY_DATE = 7;
	public static int SORT_BY_REMARK = 8;
	public static int SORT_BY_TIMESTAMP = 9;
	private int sidx;	
	
	public PayComparator(){
		this.sidx = SORT_BY_ID;
	}
	
	public PayComparator(int sidx){
		this.sidx = sidx;
	}
	
	public PayComparator(String sidxString) {
		if ("id".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_ID;
		} else if ("roll.ro_no".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_RO_NO;
		} else if ("member.account".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_ACCOUNT;
		} else if ("company.cmp_description".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_COMPANY;
		} else if ("value".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_VALUE;
		} else if ("term.term_description".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_TERM;
		} else if ("settlement_date".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_SETTLEMENT;
		} else if ("creation_date".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_DATE;
		} else if ("remark".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_REMARK;	
		} else if ("timestamp".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_TIMESTAMP;	
		}
	}
	
	@Override
	public int compare(Pay o1, Pay o2) {
		int result = 0 ;
		
		switch (this.sidx) {
			case 0:
				if (o1.getId() > o2.getId()) {
					result = 1;
				} else if (o1.getId() < o2.getId()) {
					result = -1;
				} else {
					result = 0;
				}
				break;
			case 1:
				result = o1.getRoll().getRo_no().compareTo(o2.getRoll().getRo_no());
				break;
			case 2:
				result = o1.getMember().getAccount().compareTo(o2.getMember().getAccount());
				break;
			case 3:
				result = o1.getCompany().getCmp_description().compareTo(o2.getCompany().getCmp_description());
				break;
			case 4:
				if (o1.getValue() > o2.getValue()) {
					result = 1;
				} else if (o1.getValue() < o2.getValue()) {
					result = -1;
				} else {
					result = 0;
				}
				break;
			case 5:
				result = o1.getTerm().getTerm_description().compareTo(o2.getTerm().getTerm_description());
				break;
			case 6:
				result = o1.getSettlement_date().compareTo(o2.getSettlement_date());
				break;
			case 7:
				result = o1.getCreation_date().compareTo(o2.getCreation_date());
				break;
			case 8:
				result = o1.getRemark().compareTo(o2.getRemark());
				break;
			case 9:
				result = o1.getTimestamp().compareTo(o2.getTimestamp());
				break;				
			default:
				result = o1.getRoll().getRo_no().compareTo(o2.getRoll().getRo_no());
		}
		
		return result;
	}

	public int getSidx() {
		return sidx;
	}

	public void setSidx(int sidx) {
		this.sidx = sidx;
	}

}
