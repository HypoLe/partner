package com.boco.eoms.partner.baseinfo.displaytag.support;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.partner.baseinfo.model.Resume;

/**
 * <p>
 * Title:页面分页显示checkbox
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
public class ResumeDisplaytagDecorator extends TableDecorator {
	/**
	 * id属性的checkbox
	 * 
	 * @return 一个带有checkbox的属性
	 */
	public String getId() {		
		Resume resume = (Resume) getCurrentRowObject();
		return "<input type='checkbox' id='" + resume.getId()
				+ "' name='ids' value='" + resume.getId() + "'>";
	}
}
