package com.ldcgroup.util;

import java.text.SimpleDateFormat;
import java.util.Comparator;

import com.ldcgroup.model.Roll;

public class RollComparator implements Comparator<Roll> {

	private int sidx;
	private String searchField;
	private String searchOper;
	private String searchString;
	
	String[] fieldList = {"id","valid","ro_no","subvalue","company.cmp_description",
						  "value","pay_date","settlement_date","creation_date","remark","timestamp"};

	public RollComparator(){
		this.sidx = 0;
	}
	
	public RollComparator(int sidx){
		this.sidx = sidx;
	}
	
	public RollComparator(String sidxString) {
		this.sidx = 0;
		for (int j = 0; j < fieldList.length; j++) {
			if (fieldList[j].equalsIgnoreCase(sidxString)) {
				this.sidx = j;
				break;
			}
		}
	}
	
	@Override
	public int compare(Roll o1, Roll o2) {
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
				result = o1.getValid().toString().compareTo(o2.getValid().toString());
				break;	
			case 2:
				result = o1.getRo_no().compareTo(o2.getRo_no());
				break;
			case 3:
				if (o1.getValue() > o2.getValue()) {
					result = 1;
				} else if (o1.getValue() < o2.getValue()) {
					result = -1;
				} else {
					result = 0;
				}
				break;
			case 4:
				result = o1.getCompany().getCmp_description().compareTo(o2.getCompany().getCmp_description());
				break;
			case 5:
				if (o1.getValue() > o2.getValue()) {
					result = 1;
				} else if (o1.getValue() < o2.getValue()) {
					result = -1;
				} else {
					result = 0;
				}
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
				result = o1.getRo_no().compareTo(o2.getRo_no());
		}
		
		return result;
	}

	public boolean isMatch(Roll roll, String searchField, String searchOper, String searchString) {
		this.searchField = searchField;
		this.searchOper = searchOper;
		this.searchString = searchString;
		boolean result = false;
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Object[] valueList = {roll.getId(), roll.getValid().toString(), roll.getRo_no(), roll.getSubvalue(), roll.getCompany().getCmp_description(), 
						  sdf.format(roll.getCreation_date()), roll.getRemark(), roll.getTimestamp()};
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
