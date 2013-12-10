package com.ldcgroup.util;

import java.util.Comparator;

import com.ldcgroup.model.Member;

public class MemberComparator implements Comparator<Member> {

	public static int SORT_BY_ID = 0;
	public static int SORT_BY_ACTIVE = 1;
	public static int SORT_BY_ACCUMULATION = 2;
	public static int SORT_BY_ACCOUNT = 3;
	public static int SORT_BY_COMPANY = 4;
	public static int SORT_BY_ROLE = 5;
	public static int SORT_BY_DATE = 6;
	public static int SORT_BY_REMARK = 7;
	public static int SORT_BY_TIMESTAMP = 8;
	private int sidx;	
	
	public MemberComparator(){
		this.sidx = SORT_BY_ID;
	}
	
	public MemberComparator(int sidx){
		this.sidx = sidx;
	}
	
	public MemberComparator(String sidxString) {
		if ("id".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_ID;
		} else if ("active".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_ACTIVE;
		} else if ("accumulation".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_ACCUMULATION;
		} else if ("account".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_ACCOUNT;
		} else if ("company.cmp_description".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_COMPANY;
		} else if ("role.role_description".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_ROLE;
		} else if ("creation_date".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_DATE;
		} else if ("remark".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_REMARK;	
		} else if ("timestamp".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_TIMESTAMP;	
		}
	}
	
	@Override
	public int compare(Member o1, Member o2) {
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
				result = o1.getActive().toString().compareTo(o2.getActive().toString());
				break;
			case 2:
				if (o1.getAccumulation() > o2.getAccumulation()) {
					result = 1;
				} else if (o1.getAccumulation() < o2.getAccumulation()) {
					result = -1;
				} else {
					result = 0;
				}
				break;
			case 3:
				result = o1.getAccount().compareTo(o2.getAccount());
				break;
			case 4:
				result = o1.getCompany().getCmp_description().compareTo(o2.getCompany().getCmp_description());
				break;
			case 5:
				result = o1.getRole().getRole_description().compareTo(o2.getRole().getRole_description());
				break;
			case 6:
				result = o1.getCreation_date().compareTo(o2.getCreation_date());
				break;
			case 7:
				result = o1.getRemark().compareTo(o2.getRemark());
				break;
			case 8:
				result = o1.getTimestamp().compareTo(o2.getTimestamp());
				break;				
			default:
				result = o1.getAccount().compareTo(o2.getAccount());
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
