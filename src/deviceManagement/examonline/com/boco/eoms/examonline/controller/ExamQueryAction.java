package com.boco.eoms.examonline.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.examonline.dao.ExamDAO;
import com.boco.eoms.examonline.form.SelectExamDetailListForm;
import com.boco.eoms.examonline.service.ExamWarehouseService;

public class ExamQueryAction extends BaseAction {
	
	/**
	 * 试题管理
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findExamCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		ExamWarehouseService examWarehouseService = (ExamWarehouseService)ApplicationContextHolder.getInstance().getBean("examWarehouseService");
		List<Map<String,String>> list = examWarehouseService.findAllExamCount();
		request.setAttribute("list", list);
		return mapping.findForward("showExamCount");
	}
	
	
	/**
	 * 获得每个企业各个难道试题数量
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findManufacturerCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		String[] specialtyIds = request.getParameterValues("specialtyId");
		String contype = request.getParameter("contype");
		
		ExamWarehouseService examWarehouseService = (ExamWarehouseService)ApplicationContextHolder.getInstance().getBean("examWarehouseService");
		List<Map<String,String>> list = examWarehouseService.findExamCountBySpecialty(contype,specialtyIds);
		request.setAttribute("list", list);
		return mapping.findForward("difficulty_manufacturer_count");
	}
	
	/**
	 * 加载试题查询界面（examDetailList.jsp）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward goExamDetailListPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		SelectExamDetailListForm selectExamDetailListForm = (SelectExamDetailListForm)form;
		String pageHql = request.getParameter("pageHql"); //获得页面传回的分页后的hql语句
		
		//进入界面，form的所有属性都为空，
		if(selectExamDetailListForm.sureAllNull() && null==pageHql){
			return mapping.findForward("examDetailList");
		}
		
		ExamDAO dao = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");//获得分页所在方法的类对象
		PageModel pm = null;	//定义存放分页查询结果的对象
		int pageSize = 5;		//每页显示数据数
		
		if(null!=pageHql){		//跳转页面（上一页、下一页、.........）时才运行
			//获得当前页的所在位置
			String offsetStr = request.getParameter("pager.offset") == null ? "0" 
		    		: request.getParameter("pager.offset");
		    int offset  = Integer.parseInt(offsetStr);
		    
		    if(offset<0){
		    	offset=0;
		    }
		    
		  //调用分页的方法
		    pm = dao.searchPaginated(pageHql.trim(), null, offset, pageSize);
		    
		    request.setAttribute("pageHql", pageHql);
			
			request.setAttribute("showlist", pm.getDatas());
			request.setAttribute("resultSize",pm.getTotal());
			request.setAttribute("pageSize", pageSize);

			return mapping.findForward("examDetailList");
		}
		
		
		//查询数据，通过form中的条件
		String specialtySel = selectExamDetailListForm.getSpecialtySel();
		String wangyou = selectExamDetailListForm.getWangyou();
		String youhua = selectExamDetailListForm.getYouhua();
		String manufacturer = selectExamDetailListForm.getManufacturer();
		String difficulty3 = selectExamDetailListForm.getDifficulty3();
		String difficulty = selectExamDetailListForm.getDifficulty();
		String contype = selectExamDetailListForm.getContype();
		
		boolean existParameter = false;//hql语句中是否已存在where条件
		
		//动态生成hql语句
		String hql = "from ExamWarehouse";
		//如果选择了专业
		if(!"".equals(specialtySel) && null!=specialtySel){
			if(null==wangyou){//所选一级专业没有二级专业
				hql = hql+" where specialty='"+specialtySel.trim()+"'";
				existParameter = true;
			}else if("".equals(wangyou)){//有二级专业但没选择
				ExamWarehouseService examWarehouseService = (ExamWarehouseService)ApplicationContextHolder.getInstance().getBean("examWarehouseService");
				hql = hql+" where (specialty='"+specialtySel.trim()+"'"+examWarehouseService.selectSpecialty(specialtySel.trim())+")";
				existParameter = true;
			}else if(null==youhua){//选择了二级专业，但二级无下一级
				hql = hql+" where specialty='"+wangyou.trim()+"'";
				existParameter = true;
			}else if("".equals(youhua)){//选择了二级专业，二级有下一级单没选
				ExamWarehouseService examWarehouseService = (ExamWarehouseService)ApplicationContextHolder.getInstance().getBean("examWarehouseService");
				hql = hql+" where (specialty='"+wangyou.trim()+"'"+examWarehouseService.selectSpecialty(wangyou.trim())+")";
				existParameter = true;
			}else{				//选择了三级专业
				hql = hql+" where specialty='"+youhua.trim()+"'";
				existParameter = true;
			}	
		}
		//如果选择了公司
		if(!"".equals(manufacturer) && null!=manufacturer){
			if(existParameter){
				hql = hql+" and manufacturer='"+manufacturer.trim()+"'";
			}else{
				hql = hql+" where manufacturer='"+manufacturer.trim()+"'";
				existParameter = true;
			}	
		}
		//如果选择了难度
		if(!"".equals(difficulty) && null!=difficulty){
			if(existParameter){
				hql = hql+" and difficulty="+difficulty.trim();
			}else{
				hql = hql+" where difficulty="+difficulty.trim();
				existParameter = true;
			}	
		}
		if(!"".equals(difficulty3) && null!=difficulty3){
			if(existParameter){
				hql = hql+" and difficulty="+difficulty3.trim();
			}else{
				hql = hql+" where difficulty="+difficulty3.trim();
				existParameter = true;
			}	
		}
		//如果选择了题型
		if(!"".equals(contype) && null!=contype){
			if(existParameter){
				hql = hql+" and contype="+contype.trim();
			}else{
				hql = hql+" where contype="+contype.trim();
				existParameter = true;
			}	
		}	
		
		//调用分页的方法
		pm = dao.searchPaginated(hql, null, 0, pageSize);
		
		request.setAttribute("pageHql", hql);
		
		request.setAttribute("showlist", pm.getDatas());
		request.setAttribute("resultSize",pm.getTotal());
		request.setAttribute("pageSize", pageSize);

		return mapping.findForward("examDetailList");
	}
	
}
