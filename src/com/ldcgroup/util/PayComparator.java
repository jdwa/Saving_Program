package com.ldcgroup.util;

import java.text.SimpleDateFormat;
import java.util.Comparator;

import com.ldcgroup.model.Pay;

public class PayComparator implements Comparator<Pay> {

	private int sidx;
	private String searchField;
	private String searchOper;
	private String searchString;
	
	private String[] fieldList = {"id","roll.ro_no","member.account","company.cmp_description",
								  "value","term.term_description","pay_date",
								  "settlement_date","creation_date",
								  "remark","timestamp"};

	public PayComparator(){
		this.sidx = 0;
	}
	
	public PayComparator(int sidx){
		this.sidx = sidx;
	}
	
	public PayComparator(String sidxString) {
		this.sidx = 0;
		for (int j = 0; j < fieldList.length; j++) {
			if (fieldList[j].equalsIgnoreCase(sidxString)) {
				this.sidx = j;
				break;
			}
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
				result = o1.getPay_date().compareTo(o2.getPay_date());
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
				result = o1.getRoll().getRo_no().compareTo(o2.getRoll().getRo_no());
		}
		
		return result;
	}

	public boolean isMatch(Pay pay, String searchField, String searchOper, String searchString) {
		this.searchField = searchField;
		this.searchOper = searchOper;
		this.searchString = searchString;
		boolean result = false;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Object[] valueList = {pay.getId(), pay.getRoll().getRo_no(),
						  pay.getMember().getAccount(), pay.getCompany().getCmp_description(),
						  pay.getValue(), pay.getTerm().getTerm_description(), sdf.format(pay.getPay_date()),
						  sdf.format(pay.getSettlement_date()), sdf.format(pay.getCreation_date()),
						  pay.getRemark(), pay.getTimestamp()};
		for (int j = 0; j < fieldList.length; j++) {
			if (getSearchField().equals(fieldList[j])) {
				if (getSearchOper() != null && getSearchOper().equals("eq")) {
					if (valueList[j] instanceof Long) {
						try {
							if (((Long)valueList[j]).longValue() == Long.parseLong(getSearchString())) {
								result = true;
							}
						} catch (NumberFormatException e) {
							result = false;
						}
					} else if (valueList[j] instanceof Double) {
						try {
							if (((Double)valueList[j]).doubleValue() == Double.parseDouble(getSearchString())) {
								result = true;
							}
						} catch (NumberFormatException e) {
							result = false;
						}
					} else {
						if (valueList[j].toString().equals(getSearchString())) {
							result = true;
						}
					}
				} else if (getSearchOper() != null && getSearchOper().equals("ne")) {
					if (valueList[j] instanceof Long) {
						try {
							if (((Long)valueList[j]).longValue() != Long.parseLong(getSearchString())) {
								result = true;
							}
						} catch (NumberFormatException e) {
							result = false;
						}
					} else if (valueList[j] instanceof Double) {
						try {
							if (((Double)valueList[j]).doubleValue() != Double.parseDouble(getSearchString())) {
								result = true;
							}
						} catch (NumberFormatException e) {
							result = false;
						}
					} else {
						if (!valueList[j].toString().equals(getSearchString())) {
							result = true;
						}
					}
				} else if (getSearchOper() != null && getSearchOper().equals("lt")) {
					if (valueList[j] instanceof Long) {
						try {
							if (((Long)valueList[j]).longValue() < Long.parseLong(getSearchString())) {
								result = true;
							}
						} catch (NumberFormatException e) {
							result = false;
						}
					} else if (valueList[j] instanceof Double) {
						try {
							if (((Double)valueList[j]).doubleValue() < Double.parseDouble(getSearchString())) {
								result = true;
							}
						} catch (NumberFormatException e) {
							result = false;
						}
					} else {
						if (valueList[j].toString().compareTo(getSearchString()) < 0) {
							result = true;
						}
					}
				} else if (getSearchOper() != null && getSearchOper().equals("le")) {
					if (valueList[j] instanceof Long) {
						try {
							if (((Long)valueList[j]).longValue() <= Long.parseLong(getSearchString())) {
								result = true;
							}
						} catch (NumberFormatException e) {
							result = false;
						}
					} else if (valueList[j] instanceof Double) {
						try {
							if (((Double)valueList[j]).doubleValue() <= Double.parseDouble(getSearchString())) {
								result = true;
							}
						} catch (NumberFormatException e) {
							result = false;
						}
					} else {
						if (valueList[j].toString().compareTo(getSearchString()) <= 0) {
							result = true;
						}
					}
				} else if (getSearchOper() != null && getSearchOper().equals("gt")) {
					if (valueList[j] instanceof Long) {
						try {
							if (((Long)valueList[j]).longValue() > Long.parseLong(getSearchString())) {
								result = true;
							}
						} catch (NumberFormatException e) {
							result = false;
						}
					} else if (valueList[j] instanceof Double) {
						try {
							if (((Double)valueList[j]).doubleValue() > Double.parseDouble(getSearchString())) {
								result = true;
							}
						} catch (NumberFormatException e) {
							result = false;
						}
					} else {
						if (valueList[j].toString().compareTo(getSearchString()) > 0) {
							result = true;
						}
					}
				} else if (getSearchOper() != null && getSearchOper().equals("ge")) {
					if (valueList[j] instanceof Long) {
						try {
							if (((Long)valueList[j]).longValue() >= Long.parseLong(getSearchString())) {
								result = true;
							}
						} catch (NumberFormatException e) {
							result = false;
						}
					} else if (valueList[j] instanceof Double) {
						try {
							if (((Double)valueList[j]).doubleValue() >= Double.parseDouble(getSearchString())) {
								result = true;
							}
						} catch (NumberFormatException e) {
							result = false;
						}
					} else {
						if (valueList[j].toString().compareTo(getSearchString()) >= 0) {
							result = true;
						}
					}
				} else if (getSearchOper() != null && getSearchOper().equals("bw")) {
					if (valueList[j].toString().startsWith(getSearchString())) {
						result = true;
					}
				} else if (getSearchOper() != null && getSearchOper().equals("bn")) {
					if (!valueList[j].toString().startsWith(getSearchString())) {
						result = true;
					}
				} else if (getSearchOper() != null && getSearchOper().equals("in")) {
					if (valueList[j].toString().matches(getSearchString())) {
						result = true;
					}
				} else if (getSearchOper() != null && getSearchOper().equals("ni")) {
					if (!valueList[j].toString().matches(getSearchString())) {
						result = true;
					}
				} else if (getSearchOper() != null && getSearchOper().equals("ew")) {
					if (valueList[j].toString().endsWith(getSearchString())) {
						result = true;
					}
				} else if (getSearchOper() != null && getSearchOper().equals("en")) {
					if (!valueList[j].toString().endsWith(getSearchString())) {
						result = true;
					}
				} else if (getSearchOper() != null && getSearchOper().equals("cn")) {
					if (valueList[j].toString().contains(getSearchString())) {
						result = true;
					}
				} else if (getSearchOper() != null && getSearchOper().equals("nc")) {
					if (!valueList[j].toString().contains(getSearchString())) {
						result = true;
					}
				}
			}
		}

		return result;
	}

	public int getSidx() {
		return sidx;
	}

	public void setSidx(int sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}
