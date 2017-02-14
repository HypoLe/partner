package com.boco.eoms.sheet.base.webapp.action;

import java.util.Date;
import java.util.Map;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.sheet.base.model.BaseMain;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:2008-08-16 
 * </p>
 * 
 * @author 杨亮亮
 * @version 1.0
 *  
 */
public class AdminListDisplaytagDecoratorHelper extends TableDecorator{

    /**
     * 修改此方法，根据返回值的类型不同，获取工单号方式也有所不同。 用于故障工单事后质检的快速查询
     * modify by 秦敏 20090901
     * @return
     */
	public String getSheetId() {
		String url ="" ;
        Object object=getCurrentRowObject();
        if(object.getClass().equals(BaseMain.class)){
          BaseMain main = (BaseMain) getCurrentRowObject();
          url= "<a  href='" + (String) this.getPageContext().getAttribute("url")
			+ "?method=showMainDetailPage&sheetKey=" + main.getId()
			+ "&isAdmin=true' >" + main.getSheetId() + "</a>";
        }else if(object.getClass().equals(java.util.HashMap.class)){
          Map main = (Map) getCurrentRowObject();	
          url= "<a  href='" + (String) this.getPageContext().getAttribute("url")
			+ "?method=showMainDetailPage&sheetKey=" + StaticMethod.nullObject2String(main.get("id")).trim()
			+ "&isAdmin=true' >" + StaticMethod.nullObject2String(main.get("sheetId")).trim() + "</a>";
        }
         return url;
		
	}
	
    public Date getSendTime(){
    	BaseMain main = (BaseMain) getCurrentRowObject();
    	Date sendTime = main.getSendTime();    	
    	return sendTime;
    }		
	
/**
	public String getStatus() {
		BaseMain main = (BaseMain) getCurrentRowObject();
		String statusName = "";
		if(main.getStatus().intValue()==0){
			statusName = "运行中";
		}else if(main.getStatus().intValue()==1){
			statusName = "已归档";
		}else if(main.getStatus().intValue()==-1){
			statusName = "已撤销";
		}else if(main.getStatus().intValue()==-2){
			statusName = "已删除";
		}else if(main.getStatus().intValue()==-10){
			statusName = "强制归档";
		}else if(main.getStatus().intValue()==-11){
			statusName = "强制作废";
		}else if(main.getStatus().intValue()== -12){
			statusName = "作废";
		}else{
			statusName = "";
		}
		return statusName;
		
	}
*/
	
}
