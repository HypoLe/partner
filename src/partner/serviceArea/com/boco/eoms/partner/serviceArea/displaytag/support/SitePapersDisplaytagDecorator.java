package com.boco.eoms.partner.serviceArea.displaytag.support;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.partner.serviceArea.model.SitePapers;

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
public class SitePapersDisplaytagDecorator extends TableDecorator {
	/**
	 * id属性的checkbox
	 * 
	 * @return 一个带有checkbox的属性
	 */
	public String getId() {		
		SitePapers sitePapers = (SitePapers) getCurrentRowObject();
		return "<input type='checkbox' id='" + sitePapers.getId()
				+ "' name='ids' value='" + sitePapers.getId() + "'>";
	}
}
