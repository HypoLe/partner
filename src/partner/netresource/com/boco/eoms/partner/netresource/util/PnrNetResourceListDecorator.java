package com.boco.eoms.partner.netresource.util;

import java.util.List;
import java.util.Map;

import org.displaytag.decorator.TableDecorator;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 25, 2013 2:20:49 PM
 */
public class PnrNetResourceListDecorator extends TableDecorator {
	List<String> list = PnrNetResourceUtil.parseAllConfigModel();
	
	@Override
	public String startRow() {
		return super.startRow();
	}
	
	@SuppressWarnings("unchecked")
	public String getFlag(){
		Map map = (Map)getCurrentRowObject();
		String flag = map.get("flag").toString();
		String id = map.get("id").toString();
		String model = map.get("DATASYNCH_MODEL").toString();
		String app = getPageContext().getAttribute("app").toString();
		//如果该网络资源在配置文件中配置过，才能进行同步
		if(list.contains(model)){
			if("0".equals(flag)){
//				return "<a href='"+app+"/netresource/pnrNetResourceAction.do?method=synchNetResToResConfig&id="+id+"&model="+model+"'><img src='"+app+"/images/icons/edit.gif'><a>";
				return "<a href=\"javascript:synch('"+id+"','"+model+"')\"><img src='"+app+"/images/icons/edit.gif'><a>";
			}else{
				return "已同步";
			}
		}
		return "";
	}
}
