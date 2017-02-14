package com.boco.eoms.partner.baseinfo.displaytag.support;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;

/**  
 *<p>
 * @Title: PartnerDeptDisplaytagDecorator.java
 *</p>
 *<p>
 * @Description:代维组织名称导出
 *</p>
 * @author fengguangping fengguangping@boco.com.cn
 * @date Apr 24, 2013 3:06:02 PM
 * @version V1.0  
 */
public class PartnerDeptDisplaytagDecorator extends TableDecorator {
	/**
	 * 
	 *@Description 
	 *@date Apr 24, 2013 3:10:54 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@return 返回一个a标签
	 */
	public String getName(){
		TawSystemSessionForm sessionForm =(TawSystemSessionForm) getPageContext().getSession().getAttribute("sessionform");
		String searchInto=StaticMethod.nullObject2String(getPageContext().getRequest().getAttribute("searchInto"));
		String hasRightForAdd=StaticMethod.nullObject2String(getPageContext().getRequest().getAttribute("hasRightForAdd"),"0");
		PartnerDept dept=(PartnerDept)getCurrentRowObject();
		return  "<a   href=\""+getPageContext().getAttribute("app")+"/partner/baseinfo/partnerDepts.do?method=detail&proId="+dept.getId()
		+"&hasRightForAdd="+hasRightForAdd+"&searchInto="+searchInto+"\">"+dept.getName()+"</a>";
	}
}


