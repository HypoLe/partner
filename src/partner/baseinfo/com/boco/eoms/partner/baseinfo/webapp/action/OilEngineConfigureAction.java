package com.boco.eoms.partner.baseinfo.webapp.action;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.baseinfo.mgr.IOilEngineConfigureMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.InstrumentConfig;
import com.boco.eoms.partner.baseinfo.model.OilEngineConfigure;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.model.Vehicle;
import com.boco.eoms.partner.baseinfo.util.OilEngineConfigureConstants;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.baseinfo.webapp.form.OilEngineConfigureForm;
import com.boco.eoms.partner.baseinfo.webapp.form.VehicleForm;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;

/**
 * <p>
 * Title:油机配置
 * </p>
 * <p>
 * Description:合作伙伴资源配置管理 油机配置
 * </p>
 * <p>
 * Sun Dec 13 21:42:25 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() wangjunfeng
 * @moudle.getVersion() 1.0
 * 
 */
public final class OilEngineConfigureAction extends BaseAction {
 
	/**
	 * 未指定方法时默认调用的方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}
 	
 	/**
	 * 新增油机配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	
/*    	*//**
    	 * 加载当前用户所属地市
    	 *//*
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List city = PartnerCityByUser.getCityByUser(userid);
    	request.setAttribute("city", city);
*/
		/*
		 * 根据当前省ID，列出所有地市
		 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List citys = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", citys);

    	
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改油机配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IOilEngineConfigureMgr oilEngineConfigureMgr = (IOilEngineConfigureMgr) getBean("iOilEngineConfigureMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		
/*    	*//**
    	 * 加载当前用户所属地市
    	 *//*
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List city = PartnerCityByUser.getCityByUser(userid);
    	request.setAttribute("city", city);
*/		
		/*
		 * 根据当前省ID，列出所有地市
		 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List citys = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", citys);

		OilEngineConfigure oilEngineConfigure = oilEngineConfigureMgr.getOilEngineConfigure(id);
		oilEngineConfigure.setProvider(oilEngineConfigure.getPartnerid()+"_"+oilEngineConfigure.getBigpartnerid());
		OilEngineConfigureForm oilEngineConfigureForm = (OilEngineConfigureForm) convert(oilEngineConfigure);
		updateFormBean(mapping, request, oilEngineConfigureForm);
		return mapping.findForward("edit");
	}
    
	/**
	 * 查看油机配置详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IOilEngineConfigureMgr oilEngineConfigureMgr = (IOilEngineConfigureMgr) getBean("iOilEngineConfigureMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		OilEngineConfigure oilEngineConfigure = oilEngineConfigureMgr.getOilEngineConfigure(id);
		OilEngineConfigureForm oilEngineConfigureForm = (OilEngineConfigureForm) convert(oilEngineConfigure);
		updateFormBean(mapping, request, oilEngineConfigureForm);
		return mapping.findForward("detail");
	}
    
	
	/**
	 * 保存油机配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IOilEngineConfigureMgr oilEngineConfigureMgr = (IOilEngineConfigureMgr) getBean("iOilEngineConfigureMgr");
		OilEngineConfigureForm oilEngineConfigureForm = (OilEngineConfigureForm) form;
		boolean isNew = (null == oilEngineConfigureForm.getId() || "".equals(oilEngineConfigureForm.getId()));
		
		oilEngineConfigureForm.setIsDel("0");
		
		OilEngineConfigure oilEngineConfigure = (OilEngineConfigure) convert(oilEngineConfigureForm);
		
		/**
		 * 判断油机配置是否已经存在（所属县区、所属公司）
		 */
		//所属县区
		String city = oilEngineConfigure.getCity();
		//所属公司
		String provider = oilEngineConfigure.getProvider();

		StringBuffer whereStr = new StringBuffer(" where 1=1 ");
		whereStr.append(" and city = '");
		whereStr.append(city);
		whereStr.append("' ");
		whereStr.append(" and provider = '");
		whereStr.append(provider);
		whereStr.append("' ");
		List isUnique = oilEngineConfigureMgr.isUnique(whereStr.toString());
//		新增合作伙伴字段
		if(!"".equals(provider)){
			String[] providers =  provider.split("_");
			oilEngineConfigure.setPartnerid(providers[0]);
			oilEngineConfigure.setBigpartnerid(providers[1]);
			oilEngineConfigure.setProvider(DictMgrLocator.getId2NameService().id2Name(providers[0], "partnerDeptDao"));
		}	
		if (isNew) {
					
			if(isUnique.size()>0){
				request.setAttribute("failure", "对不起，此条件下的油机配置已经存在（所属县区、所属公司）");
				return edit(mapping, form, request, response);
			}else{
				oilEngineConfigureMgr.saveOilEngineConfigure(oilEngineConfigure);
			}
				
			
		} else {
			if(isUnique.size()>0){
				//修改、编辑的时候配置重复的时候提示
				OilEngineConfigure oilEngineConfigureModify =(OilEngineConfigure)isUnique.get(0);
				if(oilEngineConfigureModify.getId().equals(oilEngineConfigure.getId())){
					oilEngineConfigureMgr.saveOilEngineConfigure(oilEngineConfigure);
				}else{
					request.setAttribute("failure", "对不起，此条件下的油机配置已经存在（所属县区、所属公司）");
					return edit(mapping, form, request, response);
				}
				
			}else{
				//修改后的数据之前没有，直接保存
				oilEngineConfigureMgr.saveOilEngineConfigure(oilEngineConfigure);				
			}
			
			return mapping.findForward("success");
		}
		
		//return search(mapping, oilEngineConfigureForm, request, response);
		//避免增加数据后，刷新时还增加数据
		return mapping.findForward("search");		
	}
	
	/**
	 * 删除油机配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IOilEngineConfigureMgr oilEngineConfigureMgr = (IOilEngineConfigureMgr) getBean("iOilEngineConfigureMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		oilEngineConfigureMgr.removeOilEngineConfigure(id);
		return mapping.findForward("success");
//		return search(mapping, form, request, response);
	}

	
	
	/**
	 * 伪删除油机配置 （页面删除，数据库保留，假删除后isdel为1）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeIsDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IOilEngineConfigureMgr oilEngineConfigureMgr = (IOilEngineConfigureMgr) getBean("iOilEngineConfigureMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		
		OilEngineConfigure oilEngineConfigure = oilEngineConfigureMgr.getOilEngineConfigure(id);
		//删除人员姓名
    	String userIdDel= this.getUser(request).getUserid();
    	oilEngineConfigure.setUserNameDel(userIdDel);
		
		//删除时间
		Date timeDel =StaticMethod.getLocalTime();
		oilEngineConfigure.setTimeDel(timeDel);
		
		oilEngineConfigure.setIsDel(Integer.valueOf(1));
		oilEngineConfigureMgr.saveOilEngineConfigure(oilEngineConfigure);
		
		return mapping.findForward("success");
		
//		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示油机配置列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				OilEngineConfigureConstants.OILENGINECONFIGURE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
		
/*    	*//**
    	 * 加载当前用户所属地市
    	 *//*
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List city = PartnerCityByUser.getCityByUser(userid);
    	request.setAttribute("city", city);
*/    	
		/*
		 * 根据当前省ID，列出所有地市
		 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List citys = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", citys);

    	
		IOilEngineConfigureMgr oilEngineConfigureMgr = (IOilEngineConfigureMgr) getBean("iOilEngineConfigureMgr");

	    StringBuffer whereStr = new StringBuffer(" where isdel=0 ");	    

/*	    for(int i = 0;city.size()>i;i++){
	    	TawSystemArea tawSystemArea = (TawSystemArea)city.get(i);
	    	if(i==0){
	    		whereStr.append(" and ( region = '");
	    	}else{
	    		whereStr.append(" or region = '");
	    	}
	    	whereStr.append(tawSystemArea.getAreaid());
	    	whereStr.append("' ");
	    	if(i==city.size()-1){
	    		whereStr.append(")");
	    	}
	    }
*/		
    	/*
    	 * 用户权限控制中的所属县区
    	 * countyLast 是添加''后的县区ID，便于informix数据库select in
    	 * wangjunfeng
    	 */
    	String userId = this.getUser(request).getUserid();
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder
			.getInstance().getBean("partnerUserAndAreaMgr");
		List RightCounty = partnerUserAndAreaMgr.listCountyOfPnrUserArea(userId);
		PartnerUserAndArea partnerUserAndArea = (PartnerUserAndArea)RightCounty.get(0);
		String countys = partnerUserAndArea.getAreaId();
    	String [] countyTem = countys.split(",");
    	StringBuffer countyBuffer = new StringBuffer();
    	for(int i=0;i < countyTem.length ;i++){
    		countyBuffer.append("'" );
    		countyBuffer.append(countyTem[i] );
    		countyBuffer.append("'" );
    		countyBuffer.append("," );
    	}
    	String countyLast = countyBuffer.substring(0, countyBuffer.length()-1).toString();
    	whereStr.append(" and city in (");
    	whereStr.append(countyLast);
    	whereStr.append(") ");


	    Map map = (Map) oilEngineConfigureMgr.getOilEngineConfigures(pageIndex, pageSize, whereStr.toString());
		List list = (List) map.get("result");
		request.setAttribute(OilEngineConfigureConstants.OILENGINECONFIGURE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

	
	/**
	 * 按查询条件分页显示油机配置列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchX(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				OilEngineConfigureConstants.OILENGINECONFIGURE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
/*    	*//**
    	 * 加载当前用户所属地市
    	 *//*
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List city = PartnerCityByUser.getCityByUser(userid);
    	request.setAttribute("city", city);
*/		

		/*
		 * 根据当前省ID，列出所有地市
		 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List citys = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", citys);

    	/*
    	 * 用户权限控制中的所属县区
    	 * countyLast 是添加''后的县区ID，便于informix数据库select in
    	 * wangjunfeng
    	 */
    	String userId = this.getUser(request).getUserid();
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder
			.getInstance().getBean("partnerUserAndAreaMgr");
		List RightCounty = partnerUserAndAreaMgr.listCountyOfPnrUserArea(userId);
		PartnerUserAndArea partnerUserAndArea = (PartnerUserAndArea)RightCounty.get(0);
		String countys = partnerUserAndArea.getAreaId();
    	String [] countyTem = countys.split(",");
    	StringBuffer countyBuffer = new StringBuffer();
    	for(int i=0;i < countyTem.length ;i++){
    		countyBuffer.append("'" );
    		countyBuffer.append(countyTem[i] );
    		countyBuffer.append("'" );
    		countyBuffer.append("," );
    	}
    	String countyLast = countyBuffer.substring(0, countyBuffer.length()-1).toString();
    	

    	/**
	     * 搜索条件
	     * 
	     */
    	OilEngineConfigureForm oilEngineConfigureForm = (OilEngineConfigureForm) form;
			StringBuffer whereStr = new StringBuffer(" where isdel=0 ");
			//所属地市
/*			if(oilEngineConfigureForm.getRegion()!=null && !"".equals(oilEngineConfigureForm.getRegion())){
				whereStr.append(" and oilEngineConfigure.region = '");
				whereStr.append(oilEngineConfigureForm.getRegion());
				whereStr.append("' ");				
			}else{
			    for(int i = 0;city.size()>i;i++){
			    	TawSystemArea tawSystemArea = (TawSystemArea)city.get(i);
			    	if(i==0){
			    		whereStr.append(" and ( region = '");
			    	}else{
			    		whereStr.append(" or region = '");
			    	}
			    	whereStr.append(tawSystemArea.getAreaid());
			    	whereStr.append("' ");
			    	if(i==city.size()-1){
			    		whereStr.append(")");
			    	}
			    }
			}
*/			
			if(!oilEngineConfigureForm.getRegion().equals("") && oilEngineConfigureForm.getCity().equals("")){
				whereStr.append(" and city in (");
				whereStr.append(countyLast);
				whereStr.append(") ");
				
			}else{
				//不输入地市条件时，查询当前用户权限配置的所有县区
				whereStr.append(" and city in (");
				whereStr.append(countyLast);
				whereStr.append(") ");
			}		

			//所属县区
			if(oilEngineConfigureForm.getCity()!=null && !"".equals(oilEngineConfigureForm.getCity())){
				whereStr.append(" and oilEngineConfigure.city = '");
				whereStr.append(oilEngineConfigureForm.getCity());
				whereStr.append("' ");
			}
			//所属公司
			if(oilEngineConfigureForm.getProvider()!=null && !"".equals(oilEngineConfigureForm.getProvider())){
//				修改后的查询
				String[] providers =  oilEngineConfigureForm.getProvider().split("_");	
				whereStr.append(" and oilEngineConfigure.partnerid = '");
				whereStr.append(providers[0]);
				whereStr.append("' ");
			}
			//应配数量
			if(oilEngineConfigureForm.getDisposeNo()!=null && !"".equals(oilEngineConfigureForm.getDisposeNo())){
				whereStr.append(" and oilEngineConfigure.disposeNo = '");
				whereStr.append(oilEngineConfigureForm.getDisposeNo());
				whereStr.append("' ");
			}
		
		
		
		
		
		IOilEngineConfigureMgr oilEngineConfigureMgr = (IOilEngineConfigureMgr) getBean("iOilEngineConfigureMgr");
		Map map = (Map) oilEngineConfigureMgr.getOilEngineConfigures(pageIndex, pageSize,whereStr.toString());
		List list = (List) map.get("result");
		request.setAttribute(OilEngineConfigureConstants.OILENGINECONFIGURE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

	
	/**
	 * 分页显示油机配置列表，支持Atom方式接入Portal
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
/*	public ActionForward search4Atom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			// --------------用于分页，得到当前页号-------------
			final Integer pageIndex = new Integer(request
					.getParameter("pageIndex"));
			final Integer pageSize = new Integer(request
					.getParameter("pageSize"));
			IOilEngineConfigureMgr oilEngineConfigureMgr = (IOilEngineConfigureMgr) getBean("iOilEngineConfigureMgr");
			Map map = (Map) oilEngineConfigureMgr.getOilEngineConfigures(pageIndex, pageSize, "");
			List list = (List) map.get("result");
			OilEngineConfigure oilEngineConfigure = new OilEngineConfigure();
			
			//创建ATOM源
			Factory factory = Abdera.getNewFactory();
			Feed feed = factory.newFeed();
			
			// 分页
			for (int i = 0; i < list.size(); i++) {
				oilEngineConfigure = (OilEngineConfigure) list.get(i);
				
				// TODO 请按照下面的实例给entry赋值
				Entry entry = feed.insertEntry();
				entry.setTitle("<a href='" + request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort()
						+ request.getContextPath()
						+ "/oilEngineConfigure/oilEngineConfigures.do?method=edit&id="
						+ oilEngineConfigure.getId() + "' target='_blank'>"
						+ display name for list + "</a>");
				entry.setSummary(summary);
				entry.setContent(content);
				entry.setLanguage(language);
				entry.setText(text);
				entry.setRights(tights);
				
				// 以下三项需要传入Date类型或String类型（yyyy-MM-dd）的参数
				entry.setUpdated(new java.util.Date());
				entry.setPublished(new java.util.Date());
				entry.setEdited(new java.util.Date());
				
				// 为person的name属性赋值，entry.addAuthor可以随意赋值
				Person person = entry.addAuthor(userId);
				person.setName(userName);
			}
			
			// 每页显示条数
			feed.setText(map.get("total").toString());
		    OutputStream os = response.getOutputStream();
		    PrintStream ps = new PrintStream(os);
		    feed.getDocument().writeTo(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
}