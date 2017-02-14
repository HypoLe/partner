package com.boco.eoms.partner.baseinfo.webapp.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.partner.baseinfo.mgr.IInstrumentConfigMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.InstrumentConfig;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.model.PersonConfig;
import com.boco.eoms.partner.baseinfo.util.InstrumentConfigConstants;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.baseinfo.webapp.form.InstrumentConfigForm;

/**
 * <p>
 * Title:仪器仪表配置
 * </p>
 * <p>
 * Description:仪器仪表配置
 * </p>
 * <p>
 * Mon Dec 14 14:07:13 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class InstrumentConfigAction extends BaseAction {
 
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
	 * 新增仪器仪表配置
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
	 * 修改仪器仪表配置
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
		IInstrumentConfigMgr instrumentConfigMgr = (IInstrumentConfigMgr) getBean("iInstrumentConfigMgr");
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

		InstrumentConfig instrumentConfig = instrumentConfigMgr.getInstrumentConfig(id);
		instrumentConfig.setProvider(instrumentConfig.getPartnerid()+"_"+instrumentConfig.getBigpartnerid());
		InstrumentConfigForm instrumentConfigForm = (InstrumentConfigForm) convert(instrumentConfig);
		updateFormBean(mapping, request, instrumentConfigForm);
		return mapping.findForward("edit");
	}
	/**
	 * 查看仪器仪表配置
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
		IInstrumentConfigMgr instrumentConfigMgr = (IInstrumentConfigMgr) getBean("iInstrumentConfigMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		InstrumentConfig instrumentConfig = instrumentConfigMgr.getInstrumentConfig(id);
		InstrumentConfigForm instrumentConfigForm = (InstrumentConfigForm) convert(instrumentConfig);
		updateFormBean(mapping, request, instrumentConfigForm);
		return mapping.findForward("detail");
	}	
	/**
	 * 保存仪器仪表配置
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
		IInstrumentConfigMgr instrumentConfigMgr = (IInstrumentConfigMgr) getBean("iInstrumentConfigMgr");
		InstrumentConfigForm instrumentConfigForm = (InstrumentConfigForm) form;
		boolean isNew = (null == instrumentConfigForm.getId() || "".equals(instrumentConfigForm.getId()));
		InstrumentConfig instrumentConfig = (InstrumentConfig) convert(instrumentConfigForm);
		instrumentConfig.setIsDel(Integer.valueOf(0));
		
		StringBuffer where = new StringBuffer();
		where.append(" where isDel='0'");
		where.append(" and city = '");
		where.append(instrumentConfig.getCity());
		where.append("'");
		where.append(" and provider = '");
		where.append(instrumentConfig.getProvider());
		where.append("'");
		where.append(" and type = '");
		where.append(instrumentConfig.getType());
		where.append("'");
		where.append(" and serviceProfessional = '");
		where.append(instrumentConfig.getServiceProfessional());
		where.append("'");
		where.append(" and use = '");
		where.append(instrumentConfig.getUse());
		where.append("'");
		List insList = instrumentConfigMgr.getInstrumentConfigs(where.toString());
		String provider = instrumentConfig.getProvider();
//		新增合作伙伴字段
		if(!"".equals(provider)){
			String[] providers =  provider.split("_");
			instrumentConfig.setPartnerid(providers[0]);
			instrumentConfig.setBigpartnerid(providers[1]);
			instrumentConfig.setProvider(DictMgrLocator.getId2NameService().id2Name(providers[0], "partnerDeptDao"));
		}
		if (isNew) {
			if(insList.size()>0){
				request.setAttribute("err", "对不起，此条件下的仪器仪表配置已存在（县区，公司，维护专业，用途，仪表类型）");
				return edit(mapping, form, request, response);
			}
			instrumentConfigMgr.saveInstrumentConfig(instrumentConfig);
		} else {
			if(insList.size()>0){
				InstrumentConfig instrumentConfig1 = (InstrumentConfig)insList.get(0);
				if(instrumentConfig1.getId().equals(instrumentConfig.getId())){
					instrumentConfigMgr.saveInstrumentConfig(instrumentConfig);
				}else{
					request.setAttribute("err", "对不起，此条件下的人员配置已存在（县区，公司，维护专业，职责）");
					return edit(mapping, form, request, response);
				}
			}else{
				instrumentConfigMgr.saveInstrumentConfig(instrumentConfig);
			}
			return mapping.findForward("success");
		}
		return mapping.findForward("search");
	}
	
	/**
	 * 删除仪器仪表配置
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
		String userId = this.getUser(request).getUserid();
		IInstrumentConfigMgr instrumentConfigMgr = (IInstrumentConfigMgr) getBean("iInstrumentConfigMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		InstrumentConfig instrumentConfig = instrumentConfigMgr.getInstrumentConfig(id);
		instrumentConfig.setDelTime(StaticMethod.getLocalTime());
		instrumentConfig.setDelUser(userId);
		instrumentConfig.setIsDel(Integer.valueOf(1));
		instrumentConfigMgr.removeInstrumentConfig(id);
		return mapping.findForward("success");
//		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示仪器仪表配置列表
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
				InstrumentConfigConstants.INSTRUMENTCONFIG_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IInstrumentConfigMgr instrumentConfigMgr = (IInstrumentConfigMgr) getBean("iInstrumentConfigMgr");
		String serviceProfessional = StaticMethod.null2String(request.getParameter("serviceProfessional"));
		String region = StaticMethod.null2String(request.getParameter("region"));
		String provider = StaticMethod.null2String(request.getParameter("provider"));
		String city = StaticMethod.null2String(request.getParameter("city"));
 /*   	*//**
    	 * 加载当前用户所属地市
    	 *//*
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List citys = PartnerCityByUser.getCityByUser(userid);
    	request.setAttribute("city", citys);
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

		StringBuffer where = new StringBuffer();
		where.append(" where isDel = '0'");
		if(!serviceProfessional.equals("")){
			where.append(" and serviceProfessional = '");
			where.append(serviceProfessional);
			where.append("'");
		}
/*		if(!region.equals("")){
			where.append(" and region = '");
			where.append(region);
			where.append("'");
		}else{
		    for(int i = 0;citys.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)citys.get(i);
		    	if(i==0){
		    		where.append(" and ( region = '");
		    	}else{
		    		where.append(" or region = '");
		    	}
		    	where.append(tawSystemArea.getAreaid());
		    	where.append("' ");
		    	if(i==citys.size()-1){
		    		where.append(")");
		    	}
		    }
		}
*/		
		if(!region.equals("") && city.equals("")){
			where.append(" and city in (");
			where.append(countyLast);
			where.append(") ");
			
		}else{
			//不输入地市条件时，查询当前用户权限配置的所有县区
			where.append(" and city in (");
			where.append(countyLast);
			where.append(") ");
		}		

		if(!provider.equals("")){
//			修改后的查询
			String[] providers =  provider.split("_");				
			where.append(" and partnerid = '");
			where.append(providers[0]);
			where.append("'");
		}
		if(!city.equals("")){
			where.append(" and city = '");
			where.append(city);
			where.append("'");
		}

		Map map = (Map) instrumentConfigMgr.getInstrumentConfigs(pageIndex, pageSize, where.toString());
		List list = (List) map.get("result");
		request.setAttribute(InstrumentConfigConstants.INSTRUMENTCONFIG_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
}