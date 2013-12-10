package com.ldcgroup.util;

import org.apache.log4j.Logger;

public class PropertyBean {
	
	private static Logger logger = Logger.getLogger(PropertyBean.class.getName());
	
	private int seniority = 3; //計畫年資預設值

	public int getSeniority() {
		return seniority;
	}

	public void setSeniority(int seniority) {
		logger.info("System Property : Seniority = [" + seniority + "]");
		this.seniority = seniority;
	}

}

