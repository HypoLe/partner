package com.boco.eoms.sheet.base.webapp.action;

import java.util.Date;
import java.util.Map;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.sheet.base.model.BaseMain;
import com.boco.eoms.sheet.base.task.ITask;
import com.boco.eoms.sheet.base.util.Constants;
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
 * @author 曲静波
 * @version 1.0
 *  
 */
public class MainListDisplaytagDecoratorHelper extends TableDecorator {
	public String getTitle() {
		Map main = (Map) getCurrentRowObject();
        String temTitle = StaticMethod.nullObject2String(main.get("title")).trim();
        return temTitle;
	}
		
	public String getSheetId() {

		Map main = (Map) getCurrentRowObject();
		String url = (String) this.getPageContext().getAttribute("url");

		return "<a  href='" + url
				+ "?method=showMainDetailPage&sheetKey=" + StaticMethod.nullObject2String(main.get("id")).trim()
				+ "'>" + StaticMethod.nullObject2String(main.get("sheetId")).trim() + "</a>";
	}

    public Date getSendTime(){
		Map main = (Map) getCurrentRowObject();
    	Date sendTime =(Date)main.get("sendTime");    	
    	return sendTime;
    }	
	
	public String getPiid() {
		Map main = (Map) getCurrentRowObject();
		String url = (String) this.getPageContext().getAttribute("url");
		String status = StaticMethod.nullObject2String(main.get("status")).trim();
		if (status.equals(new Integer(0))) {
			return "<a  href='" + url + "?method=showCancelInputPage&piid="
					+ StaticMethod.nullObject2String(main.get("piid")).trim() + "&sheetKey=" + StaticMethod.nullObject2String(main.get("id")).trim()
					+ "'>" + "撤消" + "</a>";
		} else {
			return "";
		}
	}
}
