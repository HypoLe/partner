package com.boco.eoms.partner.net.displaytag.support;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.partner.net.model.Gride;

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
public class GridDisplaytagDecorator extends TableDecorator {
	/**
	 * id属性的checkbox
	 * 
	 * @return 一个带有checkbox的属性
	 */
	public String getId() {		
		Gride grid = (Gride) getCurrentRowObject();
		return "<input type='checkbox' id='" + grid.getId()
				+ "' name='ids' value='" + grid.getId() + "'>";
	}
}
