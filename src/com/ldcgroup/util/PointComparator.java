package com.ldcgroup.util;

import java.util.Comparator;
import com.ldcgroup.model.Point;

public class PointComparator implements Comparator<Point> {

	public static int SORT_BY_ID = 0;
	public static int SORT_BY_TK_NO = 1;
	public static int SORT_BY_ACCOUNT = 2;
	public static int SORT_BY_COMPANY = 3;
	public static int SORT_BY_VALUE = 4;
	public static int SORT_BY_ITEM = 5;
	public static int SORT_BY_SETTLEMENT = 6;
	public static int SORT_BY_DATE = 7;
	public static int SORT_BY_REMARK = 8;
	public static int SORT_BY_TIMESTAMP = 9;
	private int sidx;	
	
	public PointComparator(){
		this.sidx = SORT_BY_ID;
	}
	
	public PointComparator(int sidx){
		this.sidx = sidx;
	}
	
	public PointComparator(String sidxString) {
		if ("id".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_ID;
		} else if ("task.tk_no".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_TK_NO;
		} else if ("member.account".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_ACCOUNT;
		} else if ("company.cmp_description".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_COMPANY;
		} else if ("value".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_VALUE;
		} else if ("item.item_description".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_ITEM;
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
	public int compare(Point o1, Point o2) {
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
				result = o1.getTask().getTk_no().compareTo(o2.getTask().getTk_no());
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
				result = o1.getItem().getItem_description().compareTo(o2.getItem().getItem_description());
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
				result = o1.getTask().getTk_no().compareTo(o2.getTask().getTk_no());
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
