package com.ldcgroup.util;

import java.text.SimpleDateFormat;
import java.util.Comparator;

import com.ldcgroup.model.Member;

public class MemberComparator implements Comparator<Member> {

	private int sidx;
	private String searchField;
	private String searchOper;
	private String searchString;
	
	private String[] fieldList = {"id","active","accumulation","account","company.cmp_description",
								  "role.role_description","creation_date","remark","timestamp"};
	
	public MemberComparator(){
		this.sidx = 0;
	}
	
	public MemberComparator(int sidx){
		this.sidx = sidx;
	}
	
	public MemberComparator(String sidxString) {
		this.sidx = 0;
		for (int j = 0; j < fieldList.length; j++) {
			if (fieldList[j].equalsIgnoreCase(sidxString)) {
				this.sidx = j;
				break;
			}
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

	public boolean isMatch(Member member, String searchField, String searchOper, String searchString) {
		this.searchField = searchField;
		this.searchOper = searchOper;
		this.searchString = searchString;
		boolean result = false;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Object[] valueList = {member.getId(), member.getActive().toString(), member.getAccumulation(), member.getAccount(),
						  member.getCompany().getCmp_description(), member.getRole().getRole_description(),
						  sdf.format(member.getCreation_date()),member.getRemark(), member.getTimestamp()};
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
