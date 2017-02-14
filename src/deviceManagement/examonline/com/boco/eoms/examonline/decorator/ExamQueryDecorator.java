package com.boco.eoms.examonline.decorator;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.examonline.model.ExamConfig;

public class ExamQueryDecorator extends TableDecorator {
	public String getCompanyId(){
		ExamConfig examConfig = (ExamConfig)this.getCurrentRowObject();
		String companyId = examConfig.getCompanyId();
		
		 if(companyId.equals("1")) return "省网络管理中心";
		 
		 	else if (companyId.equals("13")) return "监控室";
		    else if (companyId.equals("1721")) return "交换维护室";
		    else if (companyId.equals("1722")) return "支撑室";
		    else if (companyId.equals("10103")) return "综合分析室";
		    else if (companyId.equals("1723")) return "无线网优室";
		    else if (companyId.equals("1301")) return "数据传输室";
		 
		    else if (companyId.equals("115")) return "代维公司";
		    else if (companyId.equals("14")) return "贵阳公司";
		    else if (companyId.equals("15")) return "遵义分公司";
		    else if (companyId.equals("16")) return "安顺分公司";
		    else if (companyId.equals("17")) return "黔南分公司";
		    else if (companyId.equals("18")) return "黔东南分公司";
		    else if (companyId.equals("19")) return "铜仁分公司";
		    else if (companyId.equals("20")) return "毕节分公司";
		    else if (companyId.equals("21")) return "六盘水分公司";
		    else if (companyId.equals("22")) return "黔西南分公司";
		    else{
		    	return "";
		    }
	}
}
