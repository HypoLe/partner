package com.boco.eoms.partner.inspect.util;

import java.util.Date;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;

public class InspectPlanMainDetailByCheckUserDecorator extends TableDecorator {

	public String getOperation(){
		TawSystemSessionForm sessionForm =(TawSystemSessionForm) getPageContext().getSession().getAttribute("sessionform");
		InspectPlanRes res = (InspectPlanRes)getCurrentRowObject();
		if(res.getExecuteDept().equals(sessionForm.getDeptid())){
			if(res.getChangeState() == 0){
				if(res.getPlanStartTime().before(new Date()) && res.getPlanEndTime().after(new Date())){
					if(res.getInspectState() == 1){
						return "<a href=\"javascript:goToStartCheck("+res.getId()+",'detail' )\"> <img src=\""+getPageContext().getAttribute("app")+"/images/icons/search.gif\"></a>";
					}else{
						return "<c:if test='${sessionform.deptid eq '"+res.getExecuteDept()+"' }'><a href=\"javascript:goToStartCheck("+res.getId()+",'execute' )\"> <img src=\""+getPageContext().getAttribute("app")+"/images/icons/do.gif\"></a></c:if>";
					}
				}else{
					return "<a href=\"javascript:goToStartCheck("+res.getId()+",'detail' )\"> <img src=\""+getPageContext().getAttribute("app")+"/images/icons/search.gif\"></a>";
				}
			}else{
				return "资源变更中...";
			}
		}else{
			return "<a href=\"javascript:goToStartCheck("+res.getId()+",'detail' )\"> <img src=\""+getPageContext().getAttribute("app")+"/images/icons/search.gif\"></a>";
		}
		
		
		
	}
	public String getDeviceInspect(){
		TawSystemSessionForm sessionForm =(TawSystemSessionForm) getPageContext().getSession().getAttribute("sessionform");
		InspectPlanRes res = (InspectPlanRes)getCurrentRowObject();
		if(res.getExecuteDept().equals(sessionForm.getDeptid())){
			if(res.getChangeState() == 0){
				if(res.getPlanStartTime().before(new Date()) && res.getPlanEndTime().after(new Date())){
					if(res.getInspectState() == 1){
						
						return "<a href=\"javascript:goToDeviceInspect("+res.getId()+",'detail' )\"> <img src=\""+getPageContext().getAttribute("app")+"/images/icons/search.gif\"></a>";
					}else{
						
						return "<c:if test='${sessionform.deptid eq '"+res.getExecuteDept()+"' }'><a href=\"javascript:goToDeviceInspect("+res.getId()+",'execute' )\"> <img src=\""+getPageContext().getAttribute("app")+"/images/icons/do.gif\"></a></c:if>";
					}
				}else{
					return "<a href=\"javascript:goToDeviceInspect("+res.getId()+",'detail' )\"> <img src=\""+getPageContext().getAttribute("app")+"/images/icons/search.gif\"></a>";
				}
			}else{
				return "资源变更中...";
			}
		}else{
			
			return "<a href=\"javascript:goToDeviceInspect("+res.getId()+",'detail' )\"> <img src=\""+getPageContext().getAttribute("app")+"/images/icons/search.gif\"></a>";
		}
		
		
		
	}
	
}
