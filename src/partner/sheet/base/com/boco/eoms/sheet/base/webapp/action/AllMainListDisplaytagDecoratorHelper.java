package com.boco.eoms.sheet.base.webapp.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.sheet.base.model.BaseMain;
import com.boco.eoms.sheet.base.model.EomsStartedByMeView;
import com.boco.eoms.sheet.base.task.ITask;
import com.boco.eoms.sheet.base.util.Constants;
import com.boco.eoms.sheet.base.util.SheetBeanUtils;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:2007-10-22 20:25:13
 * </p>
 * 
 * @author 陈元蜀
 * /**
 * modify by chenyuanshu 2012-04-19 begin
 * 由于某省直接把eoms里的url放到门户里，所以需要将token取出供后续页面中使用
 *
 * modify by chenyuanshu 2012-04-19 end
 *
 * @version 1.0
 *  
 */
public class AllMainListDisplaytagDecoratorHelper extends TableDecorator {
	public String getTitle() {
		EomsStartedByMeView mainObject = (EomsStartedByMeView) getCurrentRowObject();
		HashMap main = SheetBeanUtils.bean2Map(mainObject);
        String temTitle = StaticMethod.nullObject2String(main.get("title")).trim();
        return temTitle;
	}
		
	public String getSheetId() {

		EomsStartedByMeView mainObject = (EomsStartedByMeView) getCurrentRowObject();
		HashMap main = SheetBeanUtils.bean2Map(mainObject);
		String url = (String) this.getPageContext().getAttribute("url");
		String Token = (String) this.getPageContext().getAttribute("Token");
		String sheetType = StaticMethod.nullObject2String(main
				.get("sheetType"));
		if(!sheetType.equals("")){
			sheetType = sheetType.trim();
		}
		url = url.replaceAll("eomsallsheetlist.do", sheetType + ".do");
		// 由于新业务试点工单的特殊性，所以必须将之replace掉
		if (sheetType.equals("businesspilot")) {
			sheetType = "business";
		}

		url = "../" + sheetType + "/" + url;
		return "<a  href='" + url
				+ "?method=showMainDetailPage&sheetKey=" + StaticMethod.nullObject2String(main.get("id")).trim()+Token
				+ "' target='_blank'>" + StaticMethod.nullObject2String(main.get("sheetId")).trim() + "</a>";
	}

    public Date getSendTime(){
    	EomsStartedByMeView mainObject = (EomsStartedByMeView) getCurrentRowObject();
		HashMap main = SheetBeanUtils.bean2Map(mainObject);
    	Date sendTime =(Date)main.get("sendTime");    	
    	return sendTime;
    }	
	
	public String getPiid() {
		EomsStartedByMeView mainObject = (EomsStartedByMeView) getCurrentRowObject();
		HashMap main = SheetBeanUtils.bean2Map(mainObject);
		String url = (String) this.getPageContext().getAttribute("url");
		String Token = (String) this.getPageContext().getAttribute("Token");
		String status = StaticMethod.nullObject2String(main.get("status")).trim();
		String sheetType = StaticMethod.nullObject2String(main
				.get("sheetType"));
		if(!sheetType.equals("")){
			sheetType = sheetType.trim();
		}
		url = url.replaceAll("eomsallsheetlist.do", sheetType + ".do");
		// 由于新业务试点工单的特殊性，所以必须将之replace掉
		if (sheetType.equals("businesspilot")) {
			sheetType = "business";
		}
		if (status.equals(new Integer(0))) {
			return "<a  href='" + url + "?method=showCancelInputPage&piid="
					+ StaticMethod.nullObject2String(main.get("piid")).trim() + "&sheetKey=" + StaticMethod.nullObject2String(main.get("id")).trim()+Token
					+ "' target='_blank'>" + "撤消" + "</a>";
		} else {
			return "";
		}
	}
}
