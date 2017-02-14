package com.boco.eoms.partner.inspect.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.displaytag.decorator.TableDecorator;
import org.joda.time.LocalDate;

import com.boco.eoms.partner.inspect.model.InspectPlanRes;

/** 
 * Description: 需细化巡检资源列表装饰器 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 9, 2012 12:40:29 PM 
 */
public class InspectPlanResDetailListDecorator extends TableDecorator {
	
	public String getPlanEndTime(){
		InspectPlanRes res = (InspectPlanRes)getCurrentRowObject();
		LocalDate currentDate = new LocalDate();
		LocalDate cycleEndDate = new LocalDate(res.getCycleEndTime());
		DateFormat  dFormat = new SimpleDateFormat("yyyy-MM-dd");
		if("week".equals(res.getInspectCycle())&& null == res.getPlanEndTime() 
				&& cycleEndDate.getMonthOfYear() > currentDate.getMonthOfYear()){
			return currentDate.dayOfMonth().withMaximumValue().toString();
		}
		if("week".equals(res.getInspectCycle())&& null == res.getPlanEndTime() 
				&& cycleEndDate.getMonthOfYear() <= currentDate.getMonthOfYear()){
			return cycleEndDate.toString();
		}
		if(!"week".equals(res.getInspectCycle())&& null == res.getPlanEndTime()){
			return dFormat.format(res.getCycleEndTime());
//			return currentDate.dayOfMonth().withMaximumValue().toString();
		}
		if(null != res.getPlanEndTime()){
			return new LocalDate(res.getPlanEndTime()).toString();
		}
		return "";
	}
}
