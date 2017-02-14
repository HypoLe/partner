package com.boco.eoms.partner.net.displaytag.support;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.partner.net.model.StationPoint;

/**
 * <p>
 * Title:tawDutyEventList.jsp页面分页显示checkbox
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:2009-03-26
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class SiteDisplaytagDecorator extends TableDecorator {
	/**
	 * id属性的checkbox
	 * 
	 * @return 一个带有checkbox的属性
	 */
	public String getId() {		
		StationPoint site = (StationPoint) getCurrentRowObject();
		return "<input type='checkbox' id='" + site.getId()
				+ "' name='ids' value='" + site.getId() + "'>";
	}
}
