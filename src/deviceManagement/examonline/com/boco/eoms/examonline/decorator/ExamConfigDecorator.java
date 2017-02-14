package com.boco.eoms.examonline.decorator;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.examonline.model.ExamConfig;
import com.boco.eoms.gz.util.TimeUtil;

public class ExamConfigDecorator extends TableDecorator {
	
	public String getTime(){
		ExamConfig examConfig = (ExamConfig)this.getCurrentRowObject();
		
		String c=StaticMethod.getCurrentDateTime();

		if(TimeUtil.timeCompareto(c,StaticMethod.getTimestampString(examConfig.getStartTime()))>0
    		  &&TimeUtil.timeCompareto(c,StaticMethod.getTimestampString(examConfig.getEndTime()))<0){
			return "<input type=\"button\"  class=\"btn\" value=\"开始考试\" onclick=\"star('"+examConfig.getIssueId()+"')\">" ;
		}else if(TimeUtil.timeCompareto(c,StaticMethod.getTimestampString(examConfig.getStartTime()))<0) {
			return "<input type=\"button\" disabled=\"disabled\"  class=\"btn\" value=\"未到时考试\"> ";
		}else if(TimeUtil.timeCompareto(c,StaticMethod.getTimestampString(examConfig.getEndTime()))>0){
			return " <input type=\"button\" disabled=\"disabled\"  class=\"btn\" value=\"过期考试\"> ";
		}else{
			return "<input type=\"button\"  class=\"btn\" value=\"开始考试\" onclick=\"star('"+examConfig.getIssueId()+"')\">";
		}
	}
}
