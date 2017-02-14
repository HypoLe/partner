package com.boco.eoms.deviceManagement.qualify.util;

import java.util.Date;

import org.joda.time.DateTime;


import com.boco.eoms.base.util.ApplicationContextHolder;

import com.boco.eoms.deviceManagement.qualify.dao.TaskOrderDao;

public class NumberUtil {
	
	

	public static String generateNumber(String province, String type, Date date, String sequence, String seqTable) {
		StringBuffer sb = new StringBuffer();
		sb.append(province);
		sb.append("-"+type);
		String dateStr = new DateTime(date).toString("yyyyMM");
		sb.append("-"+dateStr+"-");
		TaskOrderDao taskOrderDao = (TaskOrderDao) ApplicationContextHolder.getInstance().getBean("taskOrderDao");
		String seqStr = taskOrderDao.getSequence(sequence, seqTable);
		StringBuffer sbSeq = new StringBuffer();
		int zeroLength = 5-seqStr.length();
		while(zeroLength>0){
			sbSeq.append("0");
			zeroLength--;
		}
		sb.append(sbSeq);
		sb.append(seqStr);
		return sb.toString();
	}

}
