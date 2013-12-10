package com.ldcgroup.util;

import java.util.Comparator;
import com.ldcgroup.model.Statement;

public class StatementComparator implements Comparator<Statement> {

	public static int SORT_BY_ID = 0;
	public static int SORT_BY_TX_NO = 1;
	public static int SORT_BY_TX_CATEGORY = 2;
	public static int SORT_BY_ACCOUNT = 3;
	public static int SORT_BY_COMPANY = 4;
	public static int SORT_BY_FUND = 5;
	public static int SORT_BY_TYPE = 6;
	public static int SORT_BY_SETTLEMENT = 7;
	public static int SORT_BY_DATE = 8;
	public static int SORT_BY_REMARK = 9;
	public static int SORT_BY_TIMESTAMP = 10;
	private int sidx;	
	
	public StatementComparator(){
		this.sidx = SORT_BY_ID;
	}
	
	public StatementComparator(int sidx){
		this.sidx = sidx;
	}
	
	public StatementComparator(String sidxString) {
		if ("id".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_ID;
		} else if ("trade.tx_no".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_TX_NO;
		} else if ("trade.category.category_description".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_TX_CATEGORY;
		} else if ("member.account".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_ACCOUNT;
		} else if ("company.cmp_description".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_COMPANY;
		} else if ("fund".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_FUND;
		} else if ("type.type_description".equalsIgnoreCase(sidxString)) {
			this.sidx = SORT_BY_TYPE;
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
	public int compare(Statement o1, Statement o2) {
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
				result = o1.getTrade().getTx_no().compareTo(o2.getTrade().getTx_no());
				break;
			case 2:
				result = o1.getTrade().getCategory().getCategory_description().compareTo(o2.getTrade().getCategory().getCategory_description());
				break;
			case 3:
				result = o1.getMember().getAccount().compareTo(o2.getMember().getAccount());
				break;
			case 4:
				result = o1.getCompany().getCmp_description().compareTo(o2.getCompany().getCmp_description());
				break;
			case 5:
				if (o1.getFund() > o2.getFund()) {
					result = 1;
				} else if (o1.getFund() < o2.getFund()) {
					result = -1;
				} else {
					result = 0;
				}
				break;
			case 6:
				result = o1.getType().getType_description().compareTo(o2.getType().getType_description());
				break;
			case 7:
				result = o1.getSettlement_date().compareTo(o2.getSettlement_date());
				break;
			case 8:
				result = o1.getCreation_date().compareTo(o2.getCreation_date());
				break;
			case 9:
				result = o1.getRemark().compareTo(o2.getRemark());
				break;
			case 10:
				result = o1.getTimestamp().compareTo(o2.getTimestamp());
				break;				
			default:
				result = o1.getTrade().getTx_no().compareTo(o2.getTrade().getTx_no());
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
