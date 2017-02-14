package com.boco.eoms.partner.home.webapp.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.interfaceMonitoring.util.interfaceMonitorin;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.boco.eoms.partner.home.mgr.MaterialLibMgr;
import com.boco.eoms.partner.home.model.MaterialLib;
import com.boco.eoms.partner.home.model.MatlibScopeDept;
import com.boco.eoms.partner.home.util.MyUtil;
import com.boco.eoms.partner.home.webapp.form.MaterialLibForm;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
	/**
 * <p>
 * Title:资料库管理
 * </p>
 * <p>
 * Description:资料库管理
 * </p>
 * <p>
 * Sep 3, 2012 10:56:52 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class MaterialLibAction extends BaseAction {
	/**
	 *  根据参数  得到Jsp页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward  getGsp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
				String pageName = request.getParameter("pageName");
				String pageType = StaticMethod.null2String(request.getParameter("pageType"));
				String id = StaticMethod.null2String(request.getParameter("id"));
				if("edit".equals(pageType)||"detail".equals(pageName)){
					MaterialLibMgr materialLibMgr = (MaterialLibMgr) getBean("refmaterialLibMgr");
					request.setAttribute("materialLib", materialLibMgr.find(id));
				}
				return mapping.findForward(pageName);
}
	/**
	 * 保存资料信息
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
		
			TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			
			MaterialLibMgr materialLibMgr = (MaterialLibMgr) getBean("refmaterialLibMgr");
			MaterialLibForm materialLibForm = (MaterialLibForm) form;
			MaterialLib materialLib = (MaterialLib) convert(materialLibForm);
			
			materialLib.setPublisherId(sessionInfo.getUserid());
			materialLib.setPublisherName(sessionInfo.getUsername());
			materialLib.setPublisherDeptId(sessionInfo.getDeptid());
			materialLib.setPublisherDeptName(sessionInfo.getDeptname());
			materialLib.setPublishTime(new Date());
			
			//scopeIds 必填
			String[] scopeIdArr=materialLib.getScopeIds().split(",");
			MatlibScopeDept[] matlibScopeDeptArr=new MatlibScopeDept[scopeIdArr.length];
			int i=0;
			for (String string : scopeIdArr) {
				MatlibScopeDept matlibScopeDept=new MatlibScopeDept();
				matlibScopeDept.setScopedeptid(string);
				matlibScopeDeptArr[i]=matlibScopeDept;
				i++;
			}
				
			materialLibMgr.save(materialLib,matlibScopeDeptArr);
			
			ActionForward aForward=new ActionForward();
			aForward.setPath("/materiaLib.do?method=getGsp&pageName=add_Edit&pageType=edit&id="+materialLib.getId());
			request.setAttribute("saveSuccess", true);
			request.setAttribute("saveMessage", "保存成功");
			aForward.setRedirect(false);
			return aForward;
	}
	/**
	 * 修改资料信息
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
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		
		
		MaterialLibMgr materialLibMgr = (MaterialLibMgr) getBean("refmaterialLibMgr");
		MaterialLibForm materialLibForm = (MaterialLibForm) form;
		MaterialLib materialLib = (MaterialLib) convert(materialLibForm);
		
		materialLib.setPublisherId(sessionInfo.getUserid());
		materialLib.setPublisherName(sessionInfo.getUsername());
		materialLib.setPublisherDeptId(sessionInfo.getDeptid());
		materialLib.setPublisherDeptName(sessionInfo.getDeptname());
		materialLib.setPublishTime(new Date());
		
		//scopeIds 必填
		String[] scopeIdArr=materialLib.getScopeIds().split(",");
		MatlibScopeDept[] matlibScopeDeptArr=new MatlibScopeDept[scopeIdArr.length];
		int i=0;
		for (String string : scopeIdArr) {
			MatlibScopeDept matlibScopeDept=new MatlibScopeDept();
			matlibScopeDept.setScopedeptid(string);
			matlibScopeDeptArr[i]=matlibScopeDept;
			i++;
		}
			
		materialLibMgr.save(materialLib,matlibScopeDeptArr);

		ActionForward aForward=new ActionForward();
		aForward.setPath("/materiaLib.do?method=getGsp&pageName=add_Edit&pageType=edit&id="+materialLib.getId());
		request.setAttribute("saveSuccess", true);
		request.setAttribute("saveMessage", "保存成功");
		aForward.setRedirect(false);
		
		return aForward;
	}
	/**
	 * 查询，显示资料列表
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
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String pageIndexName = new org.displaytag.util.ParamEncoder("materialLibList")
											.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		
		String operationType = StaticMethod.null2String(request.getParameter("operationType"));
		
		final Integer pageSize =CommonConstants.PAGE_SIZE;// UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String subjectStringLike=Strings.nullToEmpty(request.getParameter("subjectStringLike")).trim();
		String keyWordStringLike=Strings.nullToEmpty(request.getParameter("keyWordStringLike")).trim();
		String publishTimeDateGreaterThan=Strings.nullToEmpty(request.getParameter("publishTimeDateGreaterThan")).trim();
		String publishTimeDateLessThan=Strings.nullToEmpty(request.getParameter("publishTimeDateLessThan")).trim();
		
		
		MaterialLibMgr materialLibMgr = (MaterialLibMgr) getBean("refmaterialLibMgr");
		Search search = new Search();
		if("".equals(operationType)){ //普通用户登录查看
			//scopeIds例:40,10406,sessionInfo.getDeptid()应为scopeIds中的一个
			//search.addFilterLike("scopeIds", sessionInfo.getDeptid()); 
			StringBuilder entrySqlBf=new StringBuilder();
			StringBuilder countSqlBf=new StringBuilder();
			String filter="";
			if(!"".equals(subjectStringLike)){
				filter=filter+" and subject like '%"+subjectStringLike+"%' ";
			}
			if(!"".equals(keyWordStringLike)){
				filter=filter+" and keyWord like '%"+keyWordStringLike+"%' ";
			}
			if(!"".equals(publishTimeDateGreaterThan)){
				filter=filter+" and publishTime >= "+CommonSqlHelper.formatDateTime(publishTimeDateGreaterThan);
			}
			if(!"".equals(publishTimeDateLessThan)){
				filter=filter+" and publishTime <= "+CommonSqlHelper.formatDateTime(publishTimeDateLessThan)+" ";
			}
			
			entrySqlBf.append("SELECT mtlib.* FROM partner_materiallibrary mtlib")
			.append("  WHERE EXISTS(")
			.append("    SELECT 1 FROM partner_matlib_scopedept mtlibspd WHERE  mtlibspd.matlibid=mtlib.ID AND mtlibspd.scopedeptid='"+sessionInfo.getDeptid()+"'")
			.append("  )  ").append(filter)
			.append(" 	ORDER BY mtlib.publishtime DESC");
			countSqlBf.append("SELECT COUNT(*) count FROM partner_materiallibrary mtlib")
			.append("  WHERE EXISTS(")
			.append("    SELECT 1 FROM partner_matlib_scopedept mtlibspd WHERE  mtlibspd.matlibid=mtlib.ID AND mtlibspd.scopedeptid='"+sessionInfo.getDeptid()+"'")
			.append("  ) ").append(filter);
			Map<String,Object> rstMap=
				materialLibMgr.getDataList(MaterialLib.class, "mtlib", entrySqlBf.toString(), countSqlBf.toString(), pageIndex, pageSize);
			request.setAttribute("resultSize",  Integer.parseInt(rstMap.get("totalCount").toString()));
			request.setAttribute("materialLibList", (List<MaterialLib>)rstMap.get("list"));
		}
		else{  //operationType不为空，可以修改操作
			search.addFilterEqual("publisherId",sessionInfo.getUserid());
			search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search,"");
			search.addSortDesc("publishTime");
			search.setFirstResult(pageIndex.intValue()*pageSize);   
			search.setMaxResults(pageSize);   
			SearchResult<MaterialLib> searchResult = materialLibMgr.searchAndCount(search);
	        List<MaterialLib> list = searchResult.getResult();			
			request.setAttribute("resultSize", searchResult.getTotalCount());
			request.setAttribute("materialLibList", list);
		}
		request.setAttribute("pageSize", pageSize);	

		request.setAttribute("subjectStringLike", subjectStringLike);
		request.setAttribute("keyWordStringLike", keyWordStringLike);
		request.setAttribute("publishTimeDateGreaterThan", publishTimeDateGreaterThan);
		request.setAttribute("publishTimeDateLessThan", publishTimeDateLessThan);
				
		if(!"".equals(operationType)){
			request.setAttribute("operationType", operationType);  //列表管理标识
		}	
		String mappingStr = "list";
		if(!"".equals(operationType))
			mappingStr = "listMgr";
		
		return mapping.findForward(mappingStr);		
	}
	/**
	 * 删除资料信息
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
		MaterialLibMgr materialLibMgr = (MaterialLibMgr) getBean("refmaterialLibMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		materialLibMgr.removeById(id);
	return mapping.findForward("success");
	}
}
