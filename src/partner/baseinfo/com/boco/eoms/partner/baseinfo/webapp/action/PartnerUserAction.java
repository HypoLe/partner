package com.boco.eoms.partner.baseinfo.webapp.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserRefRoleManager;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.deviceManagement.common.utils.PinYinUtil;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.mgr.TawApparatusMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PartnerUserConstants;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.baseinfo.util.RoleIdList;
import com.boco.eoms.partner.baseinfo.util.TableHelper;
import com.boco.eoms.partner.baseinfo.webapp.form.PartnerUserForm;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.resourceInfo.form.ApparatusForm;
import com.boco.eoms.partner.resourceInfo.service.ApparatusService;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/**
 * <p>
 * Title:��f��Ϣ
 * </p>
 * <p>
 * Description:��f��Ϣ
 * </p>
 * <p>
 * Tue Feb 10 17:33:14 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() liujinlong
 * @moudle.getVersion() 3.5
 * 
 */
public final class PartnerUserAction extends BaseAction {
	
	public  static String defaultTreeNodeId = "";//当save 或 remove 方法，调用 search方法时，利用这个变量，定位到相应人力信息的树节点下
 
	/**
	 * δָ������ʱĬ�ϵ��õķ���
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
 	 * 跳转到电子照片上传页面
 	 */
	public ActionForward toUploadphotoPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("info", "附件最大为4MB");
		return mapping.findForward("uploadPhoto");
	}
	/**
	 * 实现电子照片上传功能，保存在文件夹里
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward savaPhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerUserForm partnerUserForm = (PartnerUserForm)form;
		FormFile file = partnerUserForm.getAccessoryName();
		if(file!=null){
			System.out.println("file.size() = "+file.getFileSize());
			response.setContentType("text/html; charset=GBK");
			String timeTag = StaticMethod.getCurrentDateTime("yyyyMMddHHmmss");
			String sysTemPaht = request.getRealPath("/"); // 取当前系统路径
			String fileName = timeTag + file.getFileName().substring(file.getFileName().length()-4,file.getFileName().length());
			String uploadPath = "/partner/upUserPhotos/";
			String dbPath = uploadPath + fileName;
			String path = sysTemPaht + uploadPath;
			String filePath = sysTemPaht + dbPath;
			File tempFile = new File(path);
			if (!tempFile.exists()) {
				tempFile.mkdir();
			}
			try {
				InputStream stream = file.getInputStream(); // 把文件读入
				OutputStream outputStream = new FileOutputStream(filePath); // 建立一个上传文件的输出流
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				outputStream.close();
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}	
			request.setAttribute("imgSrc", dbPath);
			request.setAttribute("isSaveFile", "1");
			request.setAttribute("photo", new String(file.getFileName().getBytes("ISO-8859-1"),"UTF-8"));
			request.setAttribute("accessory", fileName);
			request.setAttribute("info", "附件最大为4MB");
		}
		else request.setAttribute("tooLargeInfo", "文件太大，无法上传！");
		return mapping.findForward("uploadPhoto");
	}
	/**
	 * ajax实现删除电子照片
	 */
	public ActionForward delPhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PartnerUser partnerUser = null;
		String fileName = null;
		if(!id.equals("")){
		   partnerUser = partnerUserMgr.getPartnerUser(id);
		   fileName = partnerUser.getAccessory();
		   partnerUser.setAccessory("");
		   partnerUser.setPhoto("");
		   partnerUserMgr.savePartnerUser(partnerUser);
		}
		else{
			fileName = request.getParameter("accessory") ;
		}
		String sysTemPaht = request.getRealPath("/"); // 取当前系统路径
		String uploadPath = "/partner/upUserPhotos/";
		String dbPath = uploadPath + fileName;
		String filePath = sysTemPaht + dbPath;
		File fileDel = new File(filePath);
		if (fileDel.exists())
			fileDel.delete();
		String aaa = "delIsSuccess";
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(aaa);
		response.getWriter().flush();
		return null;
	}

 	/**
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
    	String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
    	AreaDeptTree user = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId);//人力信息节点
    	AreaDeptTree factory = areaDeptTreeMgr.getAreaDeptTreeByNodeId(user.getParentNodeId());//人力信息父节点——厂商（部门），利用这个对象得到厂商名，给字段所属公司赋值
    	AreaDeptTree area = areaDeptTreeMgr.getAreaDeptTreeByNodeId(factory.getParentNodeId());
    	PartnerUserForm partnerUserForm = (PartnerUserForm)form;//(PartnerUserForm) form;
    	
    	String deptId = factory.getNodeId();
    	partnerUserForm.setDeptId(deptId);
    	partnerUserForm.setAreaId(area.getNodeId());
    	List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");//得到所有地市
		List listName = new ArrayList();
		List list_id=new ArrayList();
		for(int i=0;i<listId.size();i++){
			String tempId = ((AreaDeptTree)(listId.get(i))).getNodeId();
			listName.add(areaDeptTreeMgr.id2Name(tempId));
			list_id.add(tempId);
		}
		
    	
    	
//    	partnerUserForm.setDeptName(factory.getNodeName());
//    	partnerUserForm.setAreaName(area.getNodeName());
    	partnerUserForm.setTreeNodeId(nodeId);//存人力信息节点的nodeId
    	request.setAttribute("hasRightForDel", "1");
    	request.setAttribute("treeNodeId", nodeId);
		request.setAttribute("listName", listName);
		request.setAttribute("listId", list_id);
    	request.setAttribute("partnerUserForm", partnerUserForm);
    	request.setAttribute("isEdit", "add");
		//updateFormBean(mapping, request, partnerUserForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * �޸���f��Ϣ
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
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
    	String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
    	String personCardNo = StaticMethod.null2String(request.getParameter("personCardNo"));
    	String personCardNo1 = StaticMethod.null2String(request.getParameter("personCardNo1"));
    	PartnerUserForm partnerUserForm = (PartnerUserForm)form;//(PartnerUserForm) form;
		String id = StaticMethod.null2String(request.getParameter("id"));

		if(nodeId.equals("")){
    		nodeId = partnerUserForm.getTreeNodeId();
    	}
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
    	AreaDeptTree user = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId);//人力信息节点
    	AreaDeptTree factory = areaDeptTreeMgr.getAreaDeptTreeByNodeId(user.getParentNodeId());//人力信息父节点——厂商（部门），利用这个对象得到厂商名，给字段所属公司赋值
    	AreaDeptTree area = areaDeptTreeMgr.getAreaDeptTreeByNodeId(factory.getParentNodeId());
    	
    	String deptId = factory.getNodeId();
    	partnerUserForm.setDeptId(deptId);
    	partnerUserForm.setAreaId(area.getNodeId());
    	partnerUserForm.setTreeNodeId(nodeId);


		if(personCardNo.equals("")){
			personCardNo = personCardNo1;
		}
		List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
		List listName = new ArrayList();
		List list_id=new ArrayList();
		for(int i=0;i<listId.size();i++){
			String tempId = ((AreaDeptTree)(listId.get(i))).getNodeId();
			listName.add(areaDeptTreeMgr.id2Name(tempId));
			list_id.add(tempId);
		}
		
		//add 王思轩 当前用户是否后有合作伙伴管理角色下的 合作伙伴人力资源管理 角色
		String hasRightForDel = "0";
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
		
		//2009-5-26 新建类RoleIdList，定义角色属性
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");

		if(sessionForm.getUserid().equals("admin")){
			hasRightForDel = "1";
		}else{
			List roleList = sessionForm.getRolelist();
			for(int i=0;i<roleList.size();i++){
				TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
				//2009-5-26 登陆用户角色是合作伙伴人力信息管理员，才能删除
				if(tempRole.getRoleId() == roleIdList.getUserRoleId().intValue()){//PartnerUserConstants.PARTNER_USER_ROLEID
					hasRightForDel = "1";
				}
			}
		}
		
		if(!id.equals("")){
			PartnerUser partnerUser = partnerUserMgr.getPartnerUser(id);
			partnerUserForm = (PartnerUserForm) convert(partnerUser);
			String workTime = StaticMethod.null2String(partnerUserForm.getWorkTime());
			String deptWorkTime = StaticMethod.null2String(partnerUserForm.getDeptWorkTime());
			if (!workTime.equals("")&&workTime.length()>=10) {
				workTime = workTime.substring(0, 10);
				partnerUserForm.setWorkTime(workTime);
			}
			if(!deptWorkTime.equals("")&&deptWorkTime.length()>=10){
				deptWorkTime = deptWorkTime.substring(0, 10);
				partnerUserForm.setDeptWorkTime(deptWorkTime);
			}
			updateFormBean(mapping, request, partnerUserForm);
		}
		if(!personCardNo.equals("")){
			List list = partnerUserMgr.getPartnerUsers(" and personCardNo= '"+personCardNo+"'");
			if(list.size()>0){
				PartnerUser partnerUser = (PartnerUser)list.get(0);
				partnerUserForm = (PartnerUserForm) convert(partnerUser);
				String workTime = StaticMethod.null2String(partnerUserForm.getWorkTime());
				String deptWorkTime = StaticMethod.null2String(partnerUserForm.getDeptWorkTime());
				if (!workTime.equals("")&&workTime.length()>=10) {
					workTime = workTime.substring(0, 10);
					partnerUserForm.setWorkTime(workTime);
				}
				if(!deptWorkTime.equals("")&&deptWorkTime.length()>=10){
					deptWorkTime = deptWorkTime.substring(0, 10);
					partnerUserForm.setDeptWorkTime(deptWorkTime);
				}
				updateFormBean(mapping, request, partnerUserForm);
			}
		}

		//处理电子照片
		String accessory = partnerUserForm.getAccessory();
		if(accessory!=null&&!accessory.equals("")){
			String dbPath = "/partner/upUserPhotos/" + accessory;
			request.setAttribute("imgSrc", dbPath);
		}
		request.setAttribute("nodeId", nodeId);
		request.setAttribute("hasRightForDel", hasRightForDel);
		request.setAttribute("listName", listName);
		request.setAttribute("listId", list_id);
		return mapping.findForward("edit");
	}
	/**
	 * 人员查看页面
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
    	String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}

		request.setAttribute("isPartner", isPartner);
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
//    	String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
    	String personCardNo = StaticMethod.null2String(request.getParameter("personCardNo"));
    	String personCardNo1 = StaticMethod.null2String(request.getParameter("personCardNo1"));
    	PartnerUserForm partnerUserForm = (PartnerUserForm)form;//(PartnerUserForm) form;
		String id = StaticMethod.nullObject2String((request.getParameter("id")));

//		if(nodeId.equals("")){
//    		nodeId = partnerUserForm.getTreeNodeId();
//    	}
//		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
//    	AreaDeptTree user = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId);//人力信息节点
//    	AreaDeptTree factory = areaDeptTreeMgr.getAreaDeptTreeByNodeId(user.getParentNodeId());//人力信息父节点——厂商（部门），利用这个对象得到厂商名，给字段所属公司赋值
//    	AreaDeptTree area = areaDeptTreeMgr.getAreaDeptTreeByNodeId(factory.getParentNodeId());
    	
//    	String deptId = factory.getNodeId();
//    	partnerUserForm.setDeptId(deptId);
//    	partnerUserForm.setAreaId(area.getNodeId());
//    	partnerUserForm.setTreeNodeId(nodeId);


		if(personCardNo.equals("")){
			personCardNo = personCardNo1;
		}
//		List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
//		List listName = new ArrayList();
//		List list_id=new ArrayList();
//		for(int i=0;i<listId.size();i++){
//			String tempId = ((AreaDeptTree)(listId.get(i))).getNodeId();
//			listName.add(areaDeptTreeMgr.id2Name(tempId));
//			list_id.add(tempId);
//		}
//		//add 王思轩 当前用户是否后有合作伙伴管理角色下的 合作伙伴人力资源管理 角色
//		String hasRightForDel = "0";
//		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
//			.getSession().getAttribute("sessionform");
//		
//		//2009-5-26 新建类RoleIdList，定义角色属性
//		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
//
//		if(sessionForm.getUserid().equals("admin")){
//			hasRightForDel = "1";
//		}else{
//			List roleList = sessionForm.getRolelist();
//			for(int i=0;i<roleList.size();i++){
//				TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
//				//2009-5-26 登陆用户角色是合作伙伴人力信息管理员，才能删除
//				if(tempRole.getRoleId() == roleIdList.getUserRoleId().intValue()){//PartnerUserConstants.PARTNER_USER_ROLEID
//					hasRightForDel = "1";
//				}
//			}
//		}
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		/*只有移动管理人员才能有删除、增加和修改权限 2012-11-28*/
		String hasRightForDelAndAdd= "1";
		//不是管理员则只能查看自己部门的人力信息
		if(!sessionForm.getUserid().equals("admin")){
			String userDeptId = sessionForm.getDeptid();
			if (!"".equals(userDeptId)) {/**/
				/*先判断是移动公司人员还是代维公司人员*/
				PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
				List<PartnerDept> list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+userDeptId+"' and substr(deptMagId,1,3) !='"+com.boco.eoms.partner.process.util.CommonUtils.startDeptMagId+"'");
				/*if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
					代维公司人员只能浏览本公司代维公司员工的权利
					hasRightForDelAndAdd="0";
				}else {
					移动公司人员拥有删除、修改、增加本区域代维公司员工的权利
					hasRightForDelAndAdd="1";
				}*/
				if(userDeptId.length()>5){
					hasRightForDelAndAdd="0";
				}else{
					hasRightForDelAndAdd="1";
					
				}
			}
		}
		/*只有移动管理人员才能有删除、增加和修改的权限 2012-11-28*/
		if(!id.equals("")){
			PartnerUser partnerUser = partnerUserMgr.getPartnerUser(id);
			partnerUserForm = (PartnerUserForm) convert(partnerUser);
			String workTime = StaticMethod.null2String(partnerUserForm.getWorkTime());
			String deptWorkTime = StaticMethod.null2String(partnerUserForm.getDeptWorkTime());
			if (!workTime.equals("")&&workTime.length()>=10) {
				workTime = workTime.substring(0, 10);
				partnerUserForm.setWorkTime(workTime);
			}
			if(!deptWorkTime.equals("")&&deptWorkTime.length()>=10){
				deptWorkTime = deptWorkTime.substring(0, 10);
				partnerUserForm.setDeptWorkTime(deptWorkTime);
			}
			updateFormBean(mapping, request, partnerUserForm);
			//return mapping.findForward("detail");
		}
		if(!personCardNo.equals("")){
			/*List list = partnerUserMgr.getPartnerUsers(" and personCardNo= '"+personCardNo+"'");
			if(list.size()>0){
				PartnerUser partnerUser = (PartnerUser)list.get(0);
				partnerUserForm = (PartnerUserForm) convert(partnerUser);
				String workTime = StaticMethod.null2String(partnerUserForm.getWorkTime());
				String deptWorkTime = StaticMethod.null2String(partnerUserForm.getDeptWorkTime());
				if (!workTime.equals("")&&workTime.length()>=10) {
					workTime = workTime.substring(0, 10);
					partnerUserForm.setWorkTime(workTime);
				}
				if(!deptWorkTime.equals("")&&deptWorkTime.length()>=10){
					deptWorkTime = deptWorkTime.substring(0, 10);
					partnerUserForm.setDeptWorkTime(deptWorkTime);
				}
				updateFormBean(mapping, request, partnerUserForm);
			}*/
		}

		//处理电子照片
		String accessory = partnerUserForm.getAccessory();
		if(accessory!=null&&!accessory.equals("")){
			String dbPath = "/partner/upUserPhotos/" + accessory;
			request.setAttribute("imgSrc", dbPath);
		}
//		request.setAttribute("nodeId", nodeId);
		request.setAttribute("hasRightForDel", hasRightForDelAndAdd);
//		request.setAttribute("listName", listName);
//		request.setAttribute("listId", list_id);
		return mapping.findForward("detail");
	}
	
	/**
	 * ������f��Ϣ
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
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		
		PartnerUserForm partnerUserForm = (PartnerUserForm) form;
		String workTime = StaticMethod.null2String(partnerUserForm.getWorkTime());
		String deptWorkTime = StaticMethod.null2String(partnerUserForm.getDeptWorkTime());
		if (!workTime.equals("")) {
			workTime = workTime +" 00:00:00";
		}
		if(!deptWorkTime.equals("")){
			deptWorkTime = deptWorkTime +" 00:00:00";
		}
		partnerUserForm.setWorkTime(workTime);
		partnerUserForm.setDeptWorkTime(deptWorkTime);
		boolean isNew = (null == partnerUserForm.getId() || "".equals(partnerUserForm.getId()));
		
		PartnerUser partnerUser = (PartnerUser) convert(partnerUserForm);
		partnerUser.setDeleted("0");
		String areaId = partnerUser.getAreaId();
		String deptId = partnerUser.getDeptId();
		if(areaId!=null&&!areaId.equals("")){
			String areaName = areaDeptTreeMgr.getAreaDeptTreeByNodeId(areaId).getNodeName();
			partnerUser.setAreaName(areaName);
		}
		if(deptId!=null&&!deptId.equals("")){
			AreaDeptTree areaDeptTree = areaDeptTreeMgr.getAreaDeptTreeByNodeId(deptId);
			String deptName = areaDeptTree.getNodeName();
			partnerUser.setDeptName(deptName);
			partnerUser.setPartnerid(areaDeptTree.getPartnerid());
			partnerUser.setBigpartnerid(areaDeptTree.getInterfaceHeadId());
			partnerUser.setAreaidtrue(areaDeptTree.getAreaId());
		}
		
    	AreaDeptTree user = areaDeptTreeMgr.getAreaDeptTreeByNodeId(partnerUser.getTreeNodeId());//人力信息节点
    	AreaDeptTree factory = areaDeptTreeMgr.getAreaDeptTreeByNodeId(user.getParentNodeId());
       	//如果地市、厂商被修改了，那么此记录应显示在所修改的地市、厂商下的人力信息节点下
    	if(!factory.getNodeId().equals(partnerUser.getDeptId())){
			AreaDeptTree factory1 = areaDeptTreeMgr.getAreaDeptTreeByNodeId(partnerUser.getDeptId());
			List list = areaDeptTreeMgr.getChildLeafNodes(factory1.getNodeId(),"user");
			AreaDeptTree user1 = (AreaDeptTree)list.get(0);
			partnerUser.setTreeNodeId(user1.getNodeId());
    	}
    	
    	//add 王思轩 当前用户是否后有合作伙伴管理角色下的 合作伙伴人力资源管理 角色
		String hasRightForDel = "0";
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
		
		//2009-5-26 新建类RoleIdList，定义角色属性
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		
		if(sessionForm.getUserid().equals("admin")){
			hasRightForDel = "1";
		}else{
			List roleList = sessionForm.getRolelist();
			for(int i=0;i<roleList.size();i++){
				TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
				if(tempRole.getRoleId() == roleIdList.getUserRoleId().intValue()){
					hasRightForDel = "1";
				}
			}
		}
		request.setAttribute("hasRightForDel", hasRightForDel);
    	
		if (isNew) {
			if(partnerUserMgr.isunique(partnerUser.getUserId()).booleanValue()){
				partnerUserMgr.savePartnerUser(partnerUser);
			}
			else {
				updateFormBean(mapping, request, partnerUserForm);
				
				List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
				List listName = new ArrayList();
				List list_id=new ArrayList();
				for(int i=0;i<listId.size();i++){
					String tempId = ((AreaDeptTree)(listId.get(i))).getNodeId();
					listName.add(areaDeptTreeMgr.id2Name(tempId));
					list_id.add(tempId);
				}
				request.setAttribute("partnerUserForm", partnerUserForm);
				request.setAttribute("listName", listName);
				request.setAttribute("listId", list_id);
				request.setAttribute("fallure", " 用户ID已经存在");
				request.setAttribute("isEdit", "add");
				return mapping.findForward("edit");
			}
		} 
		else {
			PartnerUser puser = partnerUserMgr.getPartnerUser(partnerUser.getId());
			if(puser!=null&&puser.getUserId().equals(partnerUser.getUserId())){//userid不曾修改，则直接保存
				partnerUserMgr.savePartnerUser(partnerUser);
			}
			else {//userid 被修改后则判断是否与系统中已经存在的userid重复							
				if(partnerUserMgr.isunique(partnerUser.getUserId()).booleanValue()){
					partnerUserMgr.savePartnerUser(partnerUser);
				}
				else {
					updateFormBean(mapping, request, partnerUserForm);
					
					List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
					List listName = new ArrayList();
					List list_id=new ArrayList();
					for(int i=0;i<listId.size();i++){
						String tempId = ((AreaDeptTree)(listId.get(i))).getNodeId();
						listName.add(areaDeptTreeMgr.id2Name(tempId));
						list_id.add(tempId);
					}
					request.setAttribute("partnerUserForm", partnerUserForm);
					request.setAttribute("listName", listName);
					request.setAttribute("listId", list_id);
					request.setAttribute("fallure", " 用户ID已经存在");
					request.setAttribute("isEdit", "add");
					return mapping.findForward("edit");
				}
			}
		}
		this.defaultTreeNodeId = partnerUser.getTreeNodeId();
		request.setAttribute("operType", "save");
		request.setAttribute("nodeId", partnerUser.getTreeNodeId());
		request.setAttribute("areaName", partnerUser.getAreaName());
		request.setAttribute("deptName", partnerUser.getDeptName());
		if(isNew){
			request.setAttribute("treeNodeId", partnerUser.getTreeNodeId());
			request.setAttribute("actionDo", "partnerUsers");
			return mapping.findForward("refreshSelf");
		}else{
			request.setAttribute("treeNodeId", partnerUser.getTreeNodeId());
			request.setAttribute("actionDo", "partnerUsers");
			return mapping.findForward("refreshParent");
		}		
	}
	
	/**
	 * 逻辑删除人员和相关联的信息
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
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!id.equals("")){
			partnerUserMgr.deletePartnerUserInfoAndSkillInfo(id);//删除人员和相关联的信息
			PartnerUser user=partnerUserMgr.getPartnerUser(id);
        	user.setDeleted("1");
        	partnerUserMgr.savePartnerUser(user);
		}
		String treeNodeId = request.getParameter("treeNodeId");
		this.defaultTreeNodeId = treeNodeId;
		String[] ids = request.getParameterValues("checkbox11");
        if(ids!=null){
	        for(int i=0;i<ids.length;i++){
	        	partnerUserMgr.deletePartnerUserInfoAndSkillInfo(ids[i]);//删除人员和相关联的信息
	        	PartnerUser user=partnerUserMgr.getPartnerUser(ids[i]);
	        	user.setDeleted("1");
	        	partnerUserMgr.savePartnerUser(user);
	        	partnerUserMgr.insertPartnerUserToTawSystemUser(user, null);
	        }
        }   
        return mapping.findForward("success");
	}
	
	/**
	 * ��ҳ��ʾ��f��Ϣ�б�
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
				PartnerUserConstants.PARTNERUSER_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));//人力信息树节点
		String in = StaticMethod.null2String(request.getParameter("in"));//此条件表示点省、地市、厂商节点的查询，调用search方法。
		request.setAttribute("nodeId",nodeId);
		
		String whereStr = " where 1=1";
		if(!in.equals("")){//在省、地市、厂商下直接点查询
            String nodes = areaDeptTreeMgr.getStringNodeIdByLeaf(nodeId, "user");
			whereStr += " and treeNodeId in ("+nodes+")";
			PartnerUserForm partnerUserForm = new PartnerUserForm();
			AreaDeptTree tree = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId);
			List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
			List listName = new ArrayList();
			List list_id=new ArrayList();
			ID2NameService mgr = (ID2NameService) getBean("id2nameService");
			if(in.equals("province")){
				for(int i=0;i<listId.size();i++){
						String tempId = ((AreaDeptTree)(listId.get(i))).getNodeId();
						listName.add(mgr.id2Name(tempId,"areaDeptTreeDao"));
						list_id.add(tempId);
					}
				}
			else if(in.equals("area")){
				listName.add(tree.getNodeName());
				list_id.add(tree.getNodeId());
			}
			else if(in.equals("factory")){
				AreaDeptTree area = areaDeptTreeMgr.getAreaDeptTreeByNodeId(tree.getParentNodeId());
				listName.add(area.getNodeName());
				list_id.add(area.getNodeId());
		    	partnerUserForm.setDeptId(tree.getNodeId());	
		    	partnerUserForm.setAreaId(tree.getParentNodeId());
			}
			 if(!in.equals("factory")){
				List factory = areaDeptTreeMgr.getNextLevelAreaDeptTrees(list_id.get(0).toString());
				AreaDeptTree ff = (AreaDeptTree)factory.get(0);
		    	partnerUserForm.setDeptId(ff.getNodeId());	
		    	partnerUserForm.setAreaId(list_id.get(0).toString());
			}
			
			request.setAttribute("partnerUserForm", partnerUserForm);
			request.setAttribute("listName", listName);
			request.setAttribute("listId", list_id);
			request.setAttribute("inNodeId", nodeId);
			request.setAttribute("in", in);			
	    
		}
		else if(!nodeId.equals("")){
			whereStr += " and treeNodeId  = '"+nodeId + "'";
			request.setAttribute("treeNodeId", nodeId);
			
			//----------------
			PartnerUserForm partnerUserForm = new PartnerUserForm();
			List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
			List listName = new ArrayList();
			List list_id=new ArrayList();
			ID2NameService mgr = (ID2NameService) getBean("id2nameService");
			for(int i=0;i<listId.size();i++){
				String tempId = ((AreaDeptTree)(listId.get(i))).getNodeId();
				listName.add(mgr.id2Name(tempId,"areaDeptTreeDao"));
				list_id.add(tempId);
			}
			request.setAttribute("partnerUserForm", partnerUserForm);
			request.setAttribute("listName", listName);
			request.setAttribute("listId", list_id);
	    	AreaDeptTree user = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId);//人力信息节点
	    	AreaDeptTree factory = areaDeptTreeMgr.getAreaDeptTreeByNodeId(user.getParentNodeId());//人力信息父节点——厂商（部门），利用这个对象得到厂商名，给字段所属公司赋值
	    	AreaDeptTree area = areaDeptTreeMgr.getAreaDeptTreeByNodeId(factory.getParentNodeId());
	    	partnerUserForm.setDeptId(factory.getNodeId());
	    	partnerUserForm.setAreaId(area.getNodeId());
	    	//---------------------
	    	
		}
		else if(this.defaultTreeNodeId!=null&&!this.defaultTreeNodeId.equals("")){
			whereStr += " and treeNodeId  = '"+this.defaultTreeNodeId + "' ";
			request.setAttribute("treeNodeId", this.defaultTreeNodeId);
			request.setAttribute("nodeId",this.defaultTreeNodeId);
			
			//-----------------
			PartnerUserForm partnerUserForm = new PartnerUserForm();
			List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
			List listName = new ArrayList();
			List list_id=new ArrayList();
			ID2NameService mgr = (ID2NameService) getBean("id2nameService");
			for(int i=0;i<listId.size();i++){
				String tempId = ((AreaDeptTree)(listId.get(i))).getNodeId();
				listName.add(mgr.id2Name(tempId,"areaDeptTreeDao"));
				list_id.add(tempId);
			}
			request.setAttribute("partnerUserForm", partnerUserForm);
			request.setAttribute("listName", listName);
			request.setAttribute("listId", list_id);		
	    	AreaDeptTree user = areaDeptTreeMgr.getAreaDeptTreeByNodeId(this.defaultTreeNodeId);//人力信息节点
	    	AreaDeptTree factory = areaDeptTreeMgr.getAreaDeptTreeByNodeId(user.getParentNodeId());//人力信息父节点——厂商（部门），利用这个对象得到厂商名，给字段所属公司赋值
	    	AreaDeptTree area = areaDeptTreeMgr.getAreaDeptTreeByNodeId(factory.getParentNodeId());
	    	partnerUserForm.setDeptId(factory.getNodeId());
	    	partnerUserForm.setAreaId(area.getNodeId());
	    	//-----------------------
	    	
	    	this.defaultTreeNodeId = "";
		}
		//组装查询条件
		if(request.getParameter("nameSearch")!=null&&!request.getParameter("nameSearch").equals("")){
			whereStr += " and name like '%"+request.getParameter("nameSearch")+"%'";
		}
		//当 in 参数为空时 
		if(request.getParameter("areaId")!=null&&!request.getParameter("areaId").equals("")){
			String areaName = areaDeptTreeMgr.getAreaDeptTreeByNodeId(request.getParameter("areaId")).getNodeName();
			whereStr += " and areaName like '%"+areaName+"%'";
		}
		if(request.getParameter("deptId")!=null&&!request.getParameter("deptId").equals("")){
			String deptName = areaDeptTreeMgr.getAreaDeptTreeByNodeId(request.getParameter("deptId")).getNodeName();
			whereStr += " and deptName like '%"+deptName+"%'";
		}
		//当 in 参数不为空时 表示在省、地市、厂商下点查询
		if(request.getParameter("areaName")!=null&&!request.getParameter("areaName").equals("")){
			whereStr += " and areaName like '%"+request.getParameter("areaName")+"%'";
		}
		if(request.getParameter("deptName")!=null&&!request.getParameter("deptName").equals("")){
			whereStr += " and deptName like '%"+request.getParameter("deptName")+"%'";
		}
		//------------------
		if(request.getParameter("phoneSearch")!=null&&!request.getParameter("phoneSearch").equals("")){
			whereStr += " and phone like '%"+request.getParameter("phoneSearch")+"%'";
		}
		if(request.getParameter("emailSearch")!=null&&!request.getParameter("emailSearch").equals("")){
			whereStr += " and email like '%"+request.getParameter("emailSearch")+"%'";
		}
		if(request.getParameter("city")!=null&&!request.getParameter("city").equals("")){
			whereStr += " and areaidtrue = '"+request.getParameter("city")+"'";
		}
		if(request.getParameter("dept")!=null&&!request.getParameter("dept").equals("")){
			whereStr += " and bigpartnerid = '"+request.getParameter("dept")+"'";
		}
		//人力信息管理员才能批量删除 2009-7-29
		String hasRightForDel = "0";
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");	
		//2009-5-26 新建类RoleIdList，定义角色属性
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		if(sessionForm.getUserid().equals("admin")){
			hasRightForDel = "1";
		}else{
			List roleList = sessionForm.getRolelist();
			for(int i=0;i<roleList.size();i++){
				TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
				//2009-5-26 登陆用户角色是合作伙伴人力信息管理员，才能删除
				if(tempRole.getRoleId() == roleIdList.getUserRoleId().intValue()){//PartnerUserConstants.PARTNER_USER_ROLEID
					hasRightForDel = "1";
				}
			}
		}
		
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		String bigDeptId = StaticMethod.null2String(request.getParameter("bigDeptId"));
//		此处修改判断代码 areaId ！=null && deptId ！= null 2010-7-1 
		if( !"".equals(bigDeptId)){
			StringBuffer whereStrb = new StringBuffer();
			//TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
			whereStrb.append(" where 1=1 ");
			if(!"".equals(areaId) ){
				whereStrb.append(" and partnerUser.areaidtrue = '" + areaId + "'");
			}
			whereStrb.append(" and partnerUser.bigpartnerid = '" + bigDeptId + "'");
			Map map = (Map)partnerUserMgr.getPartnerUsers(0, 100, whereStrb.toString());
			List list = (List) map.get("result");
			Integer total = list.size();
			request.setAttribute(PartnerUserConstants.PARTNERUSER_LIST, list);
			request.setAttribute("resultSize", total);
			request.setAttribute("pageSize", total);
			return mapping.findForward("list");
		}
		
		Map map = (Map) partnerUserMgr.getPartnerUsers(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PartnerUserConstants.PARTNERUSER_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
	public ActionForward changeDep(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		List deptListId = areaDeptTreeMgr.getNextLevelAreaDeptTrees(id);
		List deptListName = new ArrayList();
//		for(int i=0;i<deptListId.size();i++){
//			String tempId = ((AreaDeptTree)(deptListId.get(i))).getNodeId();
//			deptListName.add(areaDeptTreeMgr.id2Name(tempId));
//		}
	   StringBuffer d_list = new StringBuffer();
       for(int i=0;i<deptListId.size();i++){
    	   deptListName.add(areaDeptTreeMgr.id2Name(((AreaDeptTree)(deptListId.get(i))).getNodeId()));
    	   d_list.append(	"," +((AreaDeptTree)(deptListId.get(i))).getNodeId() );
    	   d_list.append(	"," +deptListName.get(i) );
    	   
       }
       String aaa = "";
       if(deptListId!=null&&deptListId.size()>0){
           aaa = d_list.toString();
           aaa=aaa.substring(1, aaa.length());
       }
       else aaa = ",,";
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(aaa);
		response.getWriter().flush();
		return null;
	}
	
	//批量导入
	public ActionForward toXls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("problemRow", "");
		
		String treeNodeId = request.getParameter("treeNodeId");
		if(treeNodeId!=null&&!treeNodeId.equals("")){
			request.setAttribute("treeNodeId",treeNodeId );
		}
		else if(this.defaultTreeNodeId!=null&&!this.defaultTreeNodeId.equals("")){
			request.setAttribute("treeNodeId",this.defaultTreeNodeId );
			this.defaultTreeNodeId = "";
		}
		return mapping.findForward("xlsInput");
	}
	
	public ActionForward xlsSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		TawApparatusMgr apparatusMgr = (TawApparatusMgr) getBean("tawApparatusMgr");//调用仪器仪表的name2id方法
		// 首先将文件从客户端上传到服务器
		String timeTag = StaticMethod.getCurrentDateTime("yyyy_MM_dd_HHmmss");
		String sysTemPaht = request.getRealPath("/");
		PartnerUserForm partnerUserForm = (PartnerUserForm) form;
		String uploadPath = "/WEB-INF/pages/partner/baseinfo/upUserfiles";
		String filePath = sysTemPaht + uploadPath + "/" + timeTag + ".xls";
		File tempFile = new File(sysTemPaht + uploadPath);
		if (!tempFile.exists()) {
			tempFile.mkdir();
		}
		FormFile file = partnerUserForm.getAccessoryName();
//		String treeNodeId = partnerUserForm.getTreeNodeId();//存人力信息树节点nodeId
//		this.defaultTreeNodeId = treeNodeId;
		try {
			InputStream stream = file.getInputStream(); // 把文件读入
			OutputStream outputStream = new FileOutputStream(filePath); // 建立一个上传文件的输出流

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("fail");
		}
		// 然后把文件的每一条数据读入到form中
		Workbook workbook = null;
		ArrayList formList = new ArrayList();
		ArrayList numberList = new ArrayList();
		InputStream ins = new FileInputStream(filePath);
		try {
			// 构建Workbook对象, 只读Workbook对象
			// 直接从本地文件创建Workbook, 从输入流创建Workbook
			
			workbook = Workbook.getWorkbook(ins);
			Sheet dataSheet = workbook.getSheet(0);

			// 读取数据
			for (int i = 1; i < dataSheet.getRows(); i++) {
				PartnerUser partnerUser =  new PartnerUser();
				partnerUser.setDeleted("0");
//				partnerUser.setTreeNodeId(treeNodeId);//
				if (dataSheet.getCell(0, i).getContents() != null&& !"".equals(dataSheet.getCell(0, i).getContents())){
					partnerUser.setName(dataSheet.getCell(0, i).getContents().trim());
				}else{
					break;
//					numberList.add(new Integer(i+1));
//					numberList.add(new Integer(1));
//					continue;
				}

				if (dataSheet.getCell(1, i).getContents() != null&& !"".equals(dataSheet.getCell(1, i).getContents())){
					partnerUser.setPersonCardNo(dataSheet.getCell(1, i).getContents().trim());
				}else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(2));
					continue;
				}
				if (dataSheet.getCell(2, i).getContents() != null&& !"".equals(dataSheet.getCell(2, i).getContents())){
					partnerUser.setAreaName(dataSheet.getCell(2, i).getContents().trim());
					String nodeId = areaDeptTreeMgr.getNodeIdByNodeName(dataSheet.getCell(2, i).getContents().trim());
					if(nodeId!=null){
						partnerUser.setAreaId(areaDeptTreeMgr.getNodeIdByNodeName(dataSheet.getCell(2, i).getContents().trim()));
					}
					else {
						numberList.add(new Integer(i+1));
						numberList.add(new Integer(3));
						continue;
					}
				}else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(3));
					continue;
				}
				if (dataSheet.getCell(3, i).getContents() != null&& !"".equals(dataSheet.getCell(3, i).getContents())){
					partnerUser.setDeptName(dataSheet.getCell(3, i).getContents().trim());
					String parentNodeId = areaDeptTreeMgr.getNodeIdByNodeName(dataSheet.getCell(2, i).getContents().trim());
					if(parentNodeId!=null){
						String deptId  = areaDeptTreeMgr.getNodeIdByParentAndName(parentNodeId, dataSheet.getCell(3, i).getContents().trim());
						if(deptId!=null)partnerUser.setDeptId(deptId);
						else {
							numberList.add(new Integer(i+1));
							numberList.add(new Integer(4));
							continue;
						}
					}
				}else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(4));
					continue;
				}
				
				//给导入的记录treeNodeId字段赋值。
				String treeNodeId = areaDeptTreeMgr.getLeafNodeIdByNames(dataSheet.getCell(2, i).getContents().trim(), 
						dataSheet.getCell(3, i).getContents().trim(), "user");
				if(treeNodeId!=null){
					partnerUser.setTreeNodeId(treeNodeId);
					this.defaultTreeNodeId = treeNodeId;
				}
				else {
					treeNodeId = partnerUserForm.getTreeNodeId();
					partnerUser.setTreeNodeId(treeNodeId);
					this.defaultTreeNodeId = treeNodeId;
				}
				
				if (dataSheet.getCell(4, i).getContents() != null&& !"".equals(dataSheet.getCell(4, i).getContents())){
					partnerUser.setAreaNames(dataSheet.getCell(4, i).getContents().trim());
				}else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(4));
					continue;
				}
				if (dataSheet.getCell(5, i).getContents() != null&& !"".equals(dataSheet.getCell(5, i).getContents())){
					if(partnerUserMgr.isunique(dataSheet.getCell(5, i).getContents()).booleanValue()){
						partnerUser.setUserId(dataSheet.getCell(5, i).getContents().trim());
					}
					else {
						numberList.add(new Integer(i+1));
						numberList.add(new Integer(6));
						continue;
					}
				}else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(6));
					continue;
				}
				if (dataSheet.getCell(6, i).getContents() != null&& !"".equals(dataSheet.getCell(6, i).getContents())){
					if(!"".equals(apparatusMgr.name2Id(dataSheet.getCell(6, i).getContents().trim(),"1120202"))&&
							(apparatusMgr.name2Id(dataSheet.getCell(6, i).getContents().trim(),"1120202"))!=null)
					{
						partnerUser.setSex(apparatusMgr.name2Id(dataSheet.getCell(6, i).getContents().trim(),"1120202"));
					}
					else{
						numberList.add(new Integer(i+1));
						numberList.add(new Integer(7));
						continue;
					}
					
				}else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(7));
					continue;
				}
				if (dataSheet.getCell(7, i).getContents() != null&& !"".equals(dataSheet.getCell(7, i).getContents())){
					partnerUser.setPhoto(dataSheet.getCell(7, i).getContents().trim());
				}
				if (dataSheet.getCell(8, i).getContents() != null&& !"".equals(dataSheet.getCell(8, i).getContents())){
					partnerUser.setBirthdey(dataSheet.getCell(8, i).getContents().trim());
				}else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(9));
					continue;
				}
				if (dataSheet.getCell(9, i).getContents() != null&& !"".equals(dataSheet.getCell(9, i).getContents())){
					if(!"".equals(apparatusMgr.name2Id(dataSheet.getCell(9, i).getContents().trim(),"1120203"))&&
							(apparatusMgr.name2Id(dataSheet.getCell(9, i).getContents().trim(),"1120203"))!=null)
					{
						partnerUser.setDiploma(apparatusMgr.name2Id(dataSheet.getCell(9, i).getContents().trim(),"1120203"));
					}
					else{
						numberList.add(new Integer(i+1));
						numberList.add(new Integer(10));
						continue;
					}
					
				}else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(10));
					continue;
				}
				if (dataSheet.getCell(10, i).getContents() != null&& !"".equals(dataSheet.getCell(10, i).getContents())){
					DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					
					String timeexcel = StaticMethod.null2String(dataSheet.getCell(10, i).getContents().trim());
					if (!timeexcel.equals("")) {
						timeexcel = timeexcel +" 00:00:00";
					}

					Date date = df2.parse(timeexcel);
				//	partnerUser.setWorkTime(date);
				}else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(11));
					continue;
				}
				if (dataSheet.getCell(11, i).getContents() != null&& !"".equals(dataSheet.getCell(11, i).getContents())){
					DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					
					String timeexcel = StaticMethod.null2String(dataSheet.getCell(11, i).getContents().trim());
					if(!timeexcel.equals("")){
						timeexcel = timeexcel +" 00:00:00";
					}
					Date date = df2.parse(timeexcel);
					partnerUser.setDeptWorkTime(date);
				}
				else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(12));
					continue;
				}
				if (dataSheet.getCell(12, i).getContents() != null&& !"".equals(dataSheet.getCell(12, i).getContents())){
					if(!"".equals(apparatusMgr.name2Id(dataSheet.getCell(12, i).getContents().trim(),"1120201"))&&
							(apparatusMgr.name2Id(dataSheet.getCell(12, i).getContents().trim(),"1120201"))!=null)
					{
						partnerUser.setLicenseType(apparatusMgr.name2Id(dataSheet.getCell(12, i).getContents().trim(),"1120201"));
					}
					else{
						numberList.add(new Integer(i+1));
						numberList.add(new Integer(13));
						continue;
					}
					
				}
				else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(13));
					continue;
				}
				if (dataSheet.getCell(13, i).getContents() != null&& !"".equals(dataSheet.getCell(13, i).getContents())){
					partnerUser.setLicenseNo(dataSheet.getCell(13, i).getContents().trim());
				}
				else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(14));
					continue;
				}
				if (dataSheet.getCell(14, i).getContents() != null&& !"".equals(dataSheet.getCell(14, i).getContents())){
					if(!"".equals(apparatusMgr.name2Id(dataSheet.getCell(14, i).getContents().trim(),"1120205"))&&
							(apparatusMgr.name2Id(dataSheet.getCell(14, i).getContents().trim(),"1120205"))!=null)
					{
						partnerUser.setPost(apparatusMgr.name2Id(dataSheet.getCell(14, i).getContents().trim(),"1120205"));
					}
					else{
						numberList.add(new Integer(i+1));
						numberList.add(new Integer(15));
						continue;
					}
					
				}
				else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(15));
					continue;
				}
				if (dataSheet.getCell(15, i).getContents() != null&& !"".equals(dataSheet.getCell(15, i).getContents())){
					if(!"".equals(apparatusMgr.name2Id(dataSheet.getCell(15, i).getContents().trim(),"1120204"))&&
							(apparatusMgr.name2Id(dataSheet.getCell(15, i).getContents().trim(),"1120204"))!=null)
					{
						partnerUser.setPostState(apparatusMgr.name2Id(dataSheet.getCell(15, i).getContents().trim(),"1120204"));
					}
					else{
						numberList.add(new Integer(i+1));
						numberList.add(new Integer(16));
						continue;
					}
		
				}
				else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(16));
					continue;
				}
				if (dataSheet.getCell(16, i).getContents() != null&& !"".equals(dataSheet.getCell(16, i).getContents())){
					partnerUser.setPhone(dataSheet.getCell(16, i).getContents().trim());
				}
				else{
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(17));
					continue;
				}
				if (dataSheet.getCell(17, i).getContents() != null&& !"".equals(dataSheet.getCell(17, i).getContents())){
					if(PartnerUserConstants.emailFormat(dataSheet.getCell(17, i).getContents().trim())){
						partnerUser.setEmail(dataSheet.getCell(17, i).getContents().trim());
					}
					else{
						numberList.add(new Integer(i+1));
						numberList.add(new Integer(18));
						continue;
					} 
				}
				else {
					numberList.add(new Integer(i+1));
					numberList.add(new Integer(18));
					continue;
				}
				TawSystemSessionForm sessionForm = this.getUser(request);
//				String addMan = sessionForm.getUserid();
//				Date addTime = new Date();
//				String connectType = sessionForm.getContactMobile();
//				temp.setAddMan(addMan);
//				temp.setAddTime(addTime);
//				temp.setConnectType(connectType);
				formList.add(partnerUser);
			}
			for(int i = 0;i<formList.size();i++){
				partnerUserMgr.savePartnerUser((PartnerUser) formList.get(i));
			}
			String problemRow ="";
			for(int i=0;i<numberList.size();i++){
				problemRow +=","+numberList.get(i);
			}String str ="";
			if(!"".equals(problemRow)){
			 String sub = problemRow.substring(1, problemRow.length());
			 String []array = sub.split(",");
			   str = "未成功录入！以下为不合法的未录入的信息："+"<br>";
			  for(int i=0;i<array.length;i++){
			  
			  str = str+"第"+array[i]+"行"+"第"+array[i+1]+"列;"+"<br>";
			  i++;
			  }
			}else
			{
				str ="成功录入所有信息";
			}
			request.setAttribute("problemRow", str);
		} catch (Exception e) {
			workbook.close();
			
			File fileDel = new File(filePath);
			if (fileDel.exists())
				fileDel.delete();
			e.printStackTrace();
			return mapping.findForward("fail");
		} finally {
			workbook.close();
			ins.close();
			File fileDel = new File(filePath);
			if (fileDel.exists())
				fileDel.delete();
		}
		
		return mapping.findForward("xlsInput");
		
		//return search(mapping, accessoryForm, request, response);
	}
	
	//导出模板
	public ActionForward outPutModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (saveSessionBeanForm == null) {
			return mapping.findForward("timeout");
		}
		
		try{
			String sysTemPath = request.getRealPath("/");
			String url = sysTemPath + "/partner/model/partnerUser.xls";
			File file = new File(url);
			// 读到流中
			InputStream inStream = new FileInputStream(file);// 文件的存放路径
			// 设置输出的格式
			response.reset();
			response.setContentType("application/x-msdownload;charset=GBK");
			response.setCharacterEncoding("UTF-8");
			String fileName = URLEncoder.encode(file.getName(), "UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(fileName.getBytes("UTF-8"), "GBK"));

			// 循环取出流中的数据
			byte[] b = new byte[128];
			int len;
			while ((len = inStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
			inStream.close();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			return mapping.findForward("fail");
		}
		
		return null;
	}
	
	/**
	 * 
	* @Title: searchCompanyUser 
	* @Description: 查询代维公司人员信息
	* @param 
	* @Time:Nov 28, 2012-10:42:27 AM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward searchCompanyUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerUserConstants.PARTNERUSER_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String isPartner=request.getParameter("isPartner");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		//状态不为未上岗并且必须为审批通过
//		String whereStr = " where 1=1 and postState<>'112020403' and approveStatus=1 ";
		String whereStr = " where deleted=0  and approveStatus=1 ";
		//组装查询条件
		if(StringUtils.isNotEmpty(isPartner)){
			whereStr+="and isPartner="+isPartner;
		}
		if(request.getParameter("nameSearch")!=null&&!request.getParameter("nameSearch").equals("")){
			whereStr += " and name like '%"+request.getParameter("nameSearch")+"%'";
			request.setAttribute("nameSearch", request.getParameter("nameSearch"));
		}
		//------------------
		if(request.getParameter("mobilePhoneSearch")!=null&&!request.getParameter("mobilePhoneSearch").equals("")){
			whereStr += " and mobilePhone like '%"+request.getParameter("mobilePhoneSearch")+"%'";
			request.setAttribute("mobilePhoneSearch", request.getParameter("mobilePhoneSearch"));
		}
		if(request.getParameter("emailSearch")!=null&&!request.getParameter("emailSearch").equals("")){
			whereStr += " and email like '%"+request.getParameter("emailSearch")+"%'";
			request.setAttribute("emailSearch", request.getParameter("emailSearch"));
		}
		if(request.getParameter("personCardNoSearch")!=null&&!request.getParameter("personCardNoSearch").equals("")){
			whereStr += " and personCardNo like '%"+request.getParameter("personCardNoSearch")+"%'";
			request.setAttribute("personCardNoSearch", request.getParameter("personCardNoSearch"));
		}
		if(request.getParameter("prov")!=null&&!request.getParameter("prov").equals("")&&!"请选择".equals(request.getParameter("prov"))){
			whereStr += " and partnerid ='"+request.getParameter("prov")+"'";
			request.setAttribute("prov", request.getParameter("prov"));
		}
		/*只有移动管理人员才能有删除、增加和修改权限 2012-11-28*/
		String hasRightForDelAndAdd= "1";
		//不是管理员则只能查看自己部门的人力信息
		if(!sessionForm.getUserid().equals("admin")){
			String userDeptId = sessionForm.getDeptid();
			if (!"".equals(userDeptId)) {/**/
				/*先判断是移动公司人员还是代维公司人员*/
				PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
				List<PartnerDept> list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+userDeptId+"' and substr(deptMagId,1,3)!='"+com.boco.eoms.partner.process.util.CommonUtils.startDeptMagId+"'");
				if(userDeptId.length()>5){
					hasRightForDelAndAdd="0";
				}else{
					hasRightForDelAndAdd="1";
					
				}
				if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
					/*代维公司人员只能浏览本公司代维公司员工的权利*/
//					hasRightForDelAndAdd="0";
					whereStr += " and deptId like '"+userDeptId+"%'";//代维公司权限限定
				}else {
					/*移动公司人员拥有删除、修改、增加本区域代维公司员工的权利*/
//					hasRightForDelAndAdd="1";
					ITawSystemDeptManager deptManager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
					TawSystemDept dept=deptManager.getDeptinfobydeptid(userDeptId,"0");
					if (dept!=null) {
						whereStr += " and areaId like '"+StaticMethod.null2String(dept.getAreaid())+"%'";/*区域权限限定*/
					}
				}
			}
		}
		/*只有移动管理人员才能有删除、增加和修改的权限 2012-11-28*/
		Map map = (Map) partnerUserMgr.getPartnerUsers(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PartnerUserConstants.PARTNERUSER_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("hasRightForDel", hasRightForDelAndAdd);
		request.setAttribute("isPartner", isPartner);
		return mapping.findForward("listCompanyUser");
	}
	
	/**
	 * 跳转到添加页面
	 */
	 public ActionForward addCompanyUser(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
	    	String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
	    	String isPartner=request.getParameter("isPartner");
			AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
	    	PartnerUserForm partnerUserForm = (PartnerUserForm)form;//(PartnerUserForm) form;
	    	partnerUserForm.setUserPassword("Boco42@#");
	    	request.setAttribute("hasRightForDel", "1");
//	    	request.setAttribute("userPassword", "123456");
	    	request.setAttribute("partnerUserForm", partnerUserForm);
	    	request.setAttribute("isEdit", "add");
	    	request.setAttribute("isPartner", isPartner);
	    	request.setAttribute("type", request.getParameter("pnrType"));//保存类型 pnrType=partner则是从技能认证人力信息添加进入
			return mapping.findForward("editCompanyUser");
		}
	
	/**
	 * 保存
	 */
	public ActionForward saveCompanyUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		request.setAttribute("isPartner", isPartner);
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		ITawSystemUserManager userMgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		//String oldPassword=request.getParameter("oldUserPassword");
		PartnerUserForm partnerUserForm = (PartnerUserForm) form;
		String workTime = StaticMethod.null2String(partnerUserForm.getWorkTime());
		String deptWorkTime = StaticMethod.null2String(partnerUserForm.getDeptWorkTime());
		partnerUserForm.setWorkTime(workTime);
		boolean isNew = (null == partnerUserForm.getId() || "".equals(partnerUserForm.getId()));
		PartnerUser partnerUser = (PartnerUser) convert(partnerUserForm);
		partnerUser.setIsPartner(isPartner);
		String password=StaticMethod.null2String(partnerUser.getUserPassword());
		String oldPassword=StaticMethod.null2String(request.getParameter("oldUserPassword"));
//    	if("".equals(password)){//表示没有修改密码，保持原来的密码不变
//    		partnerUser.setUserPassword(oldPassword);
//    	}
		String plaintext="";	//密码明文
		
    	if(!password.equals(oldPassword)){
    		// 密码错误，抛异常
			String encodePassword = new Md5PasswordEncoder().encodePassword(password,
					new String());
    		partnerUser.setUserPassword(encodePassword);
    		plaintext=password;
    	}else{
    		partnerUser.setUserPassword(oldPassword);
    	}
    	
		/**
		 * 生成组织编码
		 */
		if(isNew){
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "SELECT organizationno from pnr_dept where deleted='0' and id='"+request.getParameter("prov")+"'";
		List<ListOrderedMap> organization = jdbcService.queryForList(sql);
		Map organizationMap = new HashMap();
		organizationMap = organization.get(0);
		String organizationNo=organizationMap.get("organizationno").toString();
		String sql1 = "SELECT count(id) as countUser from pnr_user where deleted='0' and partnerid='"+request.getParameter("prov")+"'";
		List<ListOrderedMap> countUser = jdbcService.queryForList(sql1);
		Map countMap = new HashMap();
		countMap=countUser.get(0);
		int count = Integer.parseInt(countMap.get("countUser").toString());
		String no = String.valueOf(count + 1);
		String userNo = "";
		if (no.length() == 1) {
			userNo = organizationNo+"-000" + no;
		} else if (no.length() == 2) {
			userNo = organizationNo+"-00" + no;
		} else if (no.length() == 3) {
			userNo = organizationNo+"-0" + no;
		}
		  else if (no.length() == 4) {
			  userNo = organizationNo+"-" + no;
			}
		partnerUser.setUserNo(userNo);
		}
		else{
			String prov=request.getParameter("prov");
			String oldProv=request.getParameter("oldProv");
			if(!prov.equals(oldProv)){
			CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
			String sql = "SELECT organizationno from pnr_dept where deleted='0' and id='"+request.getParameter("prov")+"'";
			List<ListOrderedMap> organization = jdbcService.queryForList(sql);
			Map organizationMap = new HashMap();
			organizationMap = organization.get(0);
			String organizationNo=organizationMap.get("organizationno").toString();
			String sql1 = "SELECT count(id) as countUser from pnr_user where deleted='0' and partnerid='"+request.getParameter("prov")+"'";
			List<ListOrderedMap> countUser = jdbcService.queryForList(sql1);
			Map countMap = new HashMap();
			countMap=countUser.get(0);
			int count = Integer.parseInt(countMap.get("countUser").toString());
			String no = String.valueOf(count + 1);
			String userNo = "";
			if (no.length() == 1) {
				userNo = organizationNo+"-000" + no;
			} else if (no.length() == 2) {
				userNo = organizationNo+"-00" + no;
			} else if (no.length() == 3) {
				userNo = organizationNo+"-0" + no;
			}
			  else if (no.length() == 4) {
				 	userNo = organizationNo+"-" + no;
				}
					partnerUser.setUserNo(userNo);
				}
		}
		partnerUser.setApproveStatus(1); //审批通过
		partnerUser.setDeleted("0");
//		String areaId = partnerUser.getAreaId();
//		String deptId = partnerUser.getDeptId();
		
		String interfaceHeadId = request.getParameter("interfaceHeadId");
		String partnerid = request.getParameter("prov");
		partnerUser.setPartnerid(partnerid);
		partnerUser.setBigpartnerid(interfaceHeadId);
		String name=StaticMethod.null2String(partnerUser.getName());
		if(isNew){
			 //根据用户名汉字自动生成拼音作为userid
			 String pingyinUserId = PinYinUtil.getPinYin(name);
			 int n = 0;
			 //如果userid已经存在了则在其后面累加数字直到不重复为止
			 String pingyinUserIdTemp = pingyinUserId;
			 while(userMgr.getTawSystemUserByuserid(pingyinUserIdTemp).getId() != null && !partnerUserMgr.isunique(pingyinUserIdTemp)){//如果存在
				 n++;
				 pingyinUserIdTemp = pingyinUserId + n;
			 }
			 partnerUser.setUserId(pingyinUserIdTemp); 
		}
		PartnerDept pd = partnerDeptMgr.getPartnerDept(partnerid);
		partnerUser.setDeptName(pd.getName());
		partnerUser.setDeptId(pd.getDeptMagId());
		partnerUser.setAreaName(pd.getAreaName());
		partnerUser.setAreaId(pd.getAreaId());
		/*	
    	//add 王思轩 当前用户是否后有合作伙伴管理角色下的 合作伙伴人力资源管理 角色
		String hasRightForDel = "0";
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		
		//2009-5-26 新建类RoleIdList，定义角色属性
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		
		if(sessionForm.getUserid().equals("admin")){
			hasRightForDel = "1";
		}else{
			List roleList = sessionForm.getRolelist();
			for(int i=0;i<roleList.size();i++){
				TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
				if(tempRole.getRoleId() == roleIdList.getUserRoleId().intValue()){
					hasRightForDel = "1";
				}
			}
		}
		request.setAttribute("hasRightForDel", hasRightForDel);
    	*/
		/*如果是离职，需要对离职时间进行处理 begin*/
		String postState=partnerUser.getPostState();
		if ("1240903".equals(postState)) {
			String leaveTime=partnerUser.getLeavaTime();
			String leaveTimeYear=leaveTime.substring(0,4);
			String leaveTimeMonth=leaveTime.substring(5,7);
			String  day=leaveTime.substring(8,10);
			String time=leaveTimeYear+leaveTimeMonth+day;
			long leaveTimeLongValue=Long.parseLong(time);
			partnerUser.setLeavaTimeYear(leaveTimeYear);
			partnerUser.setLeavaTimeMonth(leaveTimeMonth);
			partnerUser.setLeavaTimeLongValue(leaveTimeLongValue);
		}
		/*如果是离职，需要对离职时间进行处理 end*/
		/*对更新时间和加入系统时间的处理 begin*/
		if (isNew) {
			partnerUser.setSaveTime(CommonUtils.toEomsStandardDate(new Date()));
			String saveTimeYear=partnerUser.getSaveTime().substring(0,4);
			String saveTimeMonth=partnerUser.getSaveTime().substring(5,7);
			String  day=partnerUser.getSaveTime().substring(8,10);
			String time=saveTimeYear+saveTimeMonth+day;
			long saveTimeLongValue=Long.parseLong(time);
			partnerUser.setSaveTimeYear(saveTimeYear);
			partnerUser.setSaveTimeMonth(saveTimeMonth);
			partnerUser.setSaveTimeLongValue(saveTimeLongValue);
		}else {
			partnerUser.setUpdateTime(CommonUtils.toEomsStandardDate(new Date()));
		}
		/*对更新时间和加入系统时间的处理 end */
		boolean isunique = partnerUserMgr.isunique(partnerUser.getUserId()).booleanValue();
		if (isNew) {
			if(isunique==true){
				partnerUserMgr.savePartnerUser(partnerUser);
				PnrProcessCach.deptUserCach.put(partnerUser.getName(), partnerUser);
				PnrProcessCach.deptUserCach.put(partnerUser.getUserId(), partnerUser);
				PnrProcessCach.deptUserCach.put(partnerUser.getPersonCardNo(), partnerUser);
			}
			else {
				updateFormBean(mapping, request, partnerUserForm);
				request.setAttribute("partnerUserForm", partnerUserForm);
				request.setAttribute("fallure", " 用户ID已经存在");
				request.setAttribute("isEdit", "add");
				return mapping.findForward("edit");
			}
		} 
		else {
			PartnerUser puser = partnerUserMgr.getPartnerUser(partnerUser.getId());
			if(puser!=null&&puser.getUserId().equals(partnerUser.getUserId())){//userid不曾修改，则直接保存
				partnerUserMgr.savePartnerUser(partnerUser);
				
				
				
			}
			else {//userid 被修改后则判断是否与系统中已经存在的userid重复							
				if(isunique==true){
					partnerUserMgr.savePartnerUser(partnerUser);
				}
				else {
					updateFormBean(mapping, request, partnerUserForm);
					request.setAttribute("partnerUserForm", partnerUserForm);
					request.setAttribute("fallure", " 用户ID已经存在");
					request.setAttribute("isEdit", "add");
					return mapping.findForward("edit");
				}
			}
		}
//		this.defaultTreeNodeId = partnerUser.getTreeNodeId();
		request.setAttribute("operType", "save");
		request.setAttribute("areaName", partnerUser.getAreaName());
		request.setAttribute("deptName", partnerUser.getDeptName());
		//将partnerUser加入tawSystemUser
		//partnerUserMgr.insertPartnerUserToTawSystemUser(partnerUser,null);
		partnerUserMgr.insertPartnerUserToTawSystemUserNew(partnerUser, null, plaintext);
		if(isNew){
			request.setAttribute("actionDo", "partnerUsers");
			return mapping.findForward("success");
		}else{
			request.setAttribute("actionDo", "partnerUsers");
			return mapping.findForward("success");
		}	
	}
	/**
	 * 
	* @Title: toEditCompanyUser 
	* @Description: 编辑人员信息
	* @param 
	* @Time:Dec 1, 2012-2:45:11 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward toEditCompanyUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		request.setAttribute("isPartner", isPartner);
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
    	String personCardNo = StaticMethod.null2String(request.getParameter("personCardNo"));
    	String personCardNo1 = StaticMethod.null2String(request.getParameter("personCardNo1"));
    	PartnerUserForm partnerUserForm = (PartnerUserForm)form;//(PartnerUserForm) form;
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(personCardNo.equals("")){
			personCardNo = personCardNo1;
		}
		/*
		//add 王思轩 当前用户是否后有合作伙伴管理角色下的 合作伙伴人力资源管理 角色
		String hasRightForDel = "0";
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
		
		//2009-5-26 新建类RoleIdList，定义角色属性
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");

		if(sessionForm.getUserid().equals("admin")){
			hasRightForDel = "1";
		}else{
			List roleList = sessionForm.getRolelist();
			for(int i=0;i<roleList.size();i++){
				TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
				//2009-5-26 登陆用户角色是合作伙伴人力信息管理员，才能删除
				if(tempRole.getRoleId() == roleIdList.getUserRoleId().intValue()){//PartnerUserConstants.PARTNER_USER_ROLEID
					hasRightForDel = "1";
				}
			}
		}
		*/
		if(!id.equals("")){
			PartnerUser partnerUser = partnerUserMgr.getPartnerUser(id);
			partnerUserForm = (PartnerUserForm) convert(partnerUser);
			String workTime = StaticMethod.null2String(partnerUserForm.getWorkTime());
			/*
			String deptWorkTime = StaticMethod.null2String(partnerUserForm.getDeptWorkTime());
			if (!workTime.equals("")&&workTime.length()>=10) {
				workTime = workTime.substring(0, 10);
				partnerUserForm.setWorkTime(workTime);
			}
			if(!deptWorkTime.equals("")&&deptWorkTime.length()>=10){
				deptWorkTime = deptWorkTime.substring(0, 10);
				partnerUserForm.setDeptWorkTime(deptWorkTime);
			}
			*/
			updateFormBean(mapping, request, partnerUserForm);
		}
		if(!personCardNo.equals("")){
			List list = partnerUserMgr.getPartnerUsers(" and personCardNo= '"+personCardNo+"'");
			if(list.size()>0){
				PartnerUser partnerUser = (PartnerUser)list.get(0);
				partnerUserForm = (PartnerUserForm) convert(partnerUser);
				String workTime = StaticMethod.null2String(partnerUserForm.getWorkTime());
//				String deptWorkTime = StaticMethod.null2String(partnerUserForm.getDeptWorkTime());
//				if (!workTime.equals("")&&workTime.length()>=10) {
//					workTime = workTime.substring(0, 10);
//					partnerUserForm.setWorkTime(workTime);
//				}
//				if(!deptWorkTime.equals("")&&deptWorkTime.length()>=10){
//					deptWorkTime = deptWorkTime.substring(0, 10);
//					partnerUserForm.setDeptWorkTime(deptWorkTime);
//				}
				updateFormBean(mapping, request, partnerUserForm);
			}
		}

		//处理电子照片
		String accessory = partnerUserForm.getAccessory();
		if(accessory!=null&&!accessory.equals("")){
			String dbPath = "/partner/upUserPhotos/" + accessory;
			request.setAttribute("imgSrc", dbPath);
		}
//		request.setAttribute("hasRightForDel", hasRightForDel);
		request.setAttribute("isEdit", "edit");
		return mapping.findForward("editCompanyUser");
	}
	
	/**
	 * 根据父字典ID获取子字典
	 * @param fatherDictId
	 * @return
	 */
	public Map getDictMap(String fatherDictId){
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		List<TawSystemDictType> sexList = mgr.getDictSonsByDictid(fatherDictId);
		Map sexMap = new HashMap();
		for(TawSystemDictType sexType : sexList){
			sexMap.put(sexType.getDictName(), sexType.getDictId());
		}
		return sexMap;
	}
	
	/**
	 * 根据字典名取字典值
	 * @param map 子字典集合
	 * @param dictName 字典中文名
	 * @return 字典值
	 */ 
	public String getDictIdByName(Map map,String dictName){
//		 Map map = getDictMap(fatherDictId.trim());
		 Object obj = map.get(dictName);
		 return obj != null ? obj.toString() : null;
	}
	
	/**
	 * 导入
	 */
	public ActionForward importCompanyUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		ITawSystemUserManager userMgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		PrintWriter writer = null;
		String partnerid = request.getParameter("partnerid");
		PartnerDept dept = partnerDeptMgr.getPartnerDept(partnerid);
		
		response.setCharacterEncoding("utf-8");
		writer = response.getWriter();
		PartnerUserForm partnerUserForm = (PartnerUserForm)form;
		int importCount = 0;
		String result = "";
		try {
			Workbook wb = Workbook.getWorkbook(partnerUserForm.getImportFile().getInputStream());
			//获取所有工作表
			Sheet[] sheets = wb.getSheets();
			PartnerUser u = null;
			
			Map sexMap = getDictMap("1120202");
			Map serviceProfessMap = getDictMap("1121201");
			Map WorkLicenseLevelMap = getDictMap("1121301");
			Map WorkLicenseMajorMap = getDictMap("1121302");
			Map MaintainLevelMap = getDictMap("1121303");
			Map DiplomaMap = getDictMap("1120203");
			Map LicenseTypeMap = getDictMap("1120201");
			Map postTypeMap = getDictMap("1120205");
			Map PostStateMap = getDictMap("1120204");
			
			for (Sheet sheet : sheets) {
				int rows = sheet.getRows()-1; //减去一行才是正确值
				//i从1开始循环 除去表头
				for(int i=1;i<rows;i++){
					u = new  PartnerUser();
					Cell[] cells = sheet.getRow(i);
					 if(cells!=null||cells.length>0){
						 int m = 0;
						 
						 u.setPartnerid(partnerUserForm.getPartnerid());
						 u.setDeptId(dept.getDeptMagId());
						 String name = cells[m++].getContents();
						 u.setName(name);

//						 ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
						 u.setSex(getDictIdByName(sexMap,cells[m++].getContents()));
						 
						//日期需要做特殊操作(可以写到公用方法中)
						 Date birthday = ((DateCell)cells[m++]).getDate();
						 u.setBirthdey(new LocalDate(birthday).toString());
						 
						 //todo 验证省份证的唯一性
						 u.setPersonCardNo(cells[m++].getContents()); 
						 u.setPhone(cells[m++].getContents());
//						 u.setAreaNames(cells[m++].getContents());
						 u.setServiceProfessional(getDictIdByName(serviceProfessMap,cells[m++].getContents()));
//						 u.setWorkLicenseLevel(getDictIdByName(WorkLicenseLevelMap,cells[m++].getContents()));
//						 u.setWorkLicenseMajor(getDictIdByName(WorkLicenseMajorMap,cells[m++].getContents()));
						 u.setMaintainLevel(getDictIdByName(MaintainLevelMap,cells[m++].getContents()));
						 u.setResponsibility(cells[m++].getContents());
						 String isbackboneStr = cells[m++].getContents();
						 u.setIsbackbone("是".equals(isbackboneStr) ? "1" : "2");
						 u.setMobilePhone(cells[m++].getContents());
						 u.setEmail(cells[m++].getContents());
						 u.setDiploma(getDictIdByName(DiplomaMap,cells[m++].getContents()));
						 
						 Cell workTimeCell = cells[m++];
						 if(workTimeCell !=null){
							 Date workTime = ((DateCell)workTimeCell).getDate();
							// u.setWorkTime(new DateTime(workTime).minusHours(8).toDate());
						 }
						 u.setLicenseType(getDictIdByName(LicenseTypeMap,cells[m++].getContents()));
						 u.setLicenseNo(cells[m++].getContents());
//						 u.setPost(getDictIdByName(postTypeMap,cells[m++].getContents()));
						 u.setPostState(getDictIdByName(PostStateMap,cells[m++].getContents()));
						 
						 u.setDeptName(dept.getName());
						 u.setAreaName(dept.getAreaName());
						 u.setDeleted("0");
						 u.setIsmanager("0");
						 u.setUserPassword("123456");
						 u.setApproveStatus(0); //待审批
						 
						 //根据用户名汉字自动生成拼音作为userid
						 String pingyinUserId = PinYinUtil.getPinYin(name);
						 int n = 0;
						 //如果userid已经存在了则在其后面累加数字直到不重复为止
						 String pingyinUserIdTemp = pingyinUserId;
						 while(userMgr.getTawSystemUserByuserid(pingyinUserIdTemp).getId() != null || !partnerUserMgr.isunique(pingyinUserIdTemp)){//如果存在
							 n++;
							 pingyinUserIdTemp = pingyinUserId + n;
						 }
						 u.setUserId(pingyinUserIdTemp); 
						 partnerUserMgr.savePartnerUser(u);
						 partnerUserMgr.insertPartnerUserToTawSystemUser(u);
					 }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false")
							.put("msg", "failure")
							.put("infor", "导入失败，请检查excel格式").build()));
		}
		writer.write(
				new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true")
						.put("msg", "ok")
						.put("infor", "导入成功").build()));
				

		return null;
		*/
		response.setCharacterEncoding("utf-8");
		PartnerUserForm uploadForm=(PartnerUserForm)form;
		FormFile formFile=uploadForm.getImportFile();
		PrintWriter writer=null;
		try {
			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
			writer=response.getWriter();
			ImportResult result=partnerUserMgr.importFromFile(formFile,request);
			if (result.getResultCode().equals("200")) {
				writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true")
						.put("msg", "ok")
						.put("infor", "导入成功,共计导入"+result.getImportCount()+"条记录").build()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false")
							.put("msg", "failure")
							.put("infor", e.getMessage()).build()));
		}finally{
			if(writer != null){
				writer.close();
			}
		}
		return null;
	}
	
	
	/**
	 * "增加代维人员信息.xls"导入模板下载
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String rootPath = request.getRealPath("/partner/processExcelModel");
		String fileName = "增加人员信息.xls";
		File file = new File(rootPath + File.separator + fileName);
		// 读到流中
		InputStream inStream = new FileInputStream(file);// 文件的存放路径
		// 设置输出的格式
		response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("GB2312");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("gbk"), "iso8859-1"));
		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = inStream.read(b)) > 0)
			response.getOutputStream().write(b, 0, len);
		inStream.close();
		return null;
	}
	
	/**
	 *公共查询页面
	 */ 
	public ActionForward gotoCommonList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerUserConstants.PARTNERUSER_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		String whereStr = " where 1=1";
		//组装查询条件
		if(request.getParameter("nameSearch")!=null&&!request.getParameter("nameSearch").equals("")){
			whereStr += " and name like '%"+request.getParameter("nameSearch")+"%'";
		}
		//------------------
		if(request.getParameter("phoneSearch")!=null&&!request.getParameter("phoneSearch").equals("")){
			whereStr += " and phone like '%"+request.getParameter("phoneSearch")+"%'";
		}
		if(request.getParameter("emailSearch")!=null&&!request.getParameter("emailSearch").equals("")){
			whereStr += " and email like '%"+request.getParameter("emailSearch")+"%'";
		}
		if(request.getParameter("prov")!=null&&!request.getParameter("prov").equals("")&&!"请选择".equals(request.getParameter("prov"))){
			whereStr += " and partnerid ='"+request.getParameter("prov")+"'";
		}
		
		//人力信息管理员才能批量删除 2009-7-29
		String hasRightForDel = "0";
			
		//2009-5-26 新建类RoleIdList，定义角色属性
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		
		ITawSystemUserRefRoleManager mgr = (ITawSystemUserRefRoleManager) ApplicationContextHolder
		.getInstance().getBean("itawSystemUserRefRoleManager");
		
		if(sessionForm.getUserid().equals("admin")){
			hasRightForDel = "1";
		}else{
//			List roleList = sessionForm.getRolelist();
			List<TawSystemSubRole> roleList = mgr.getSubRoleByUserId(sessionForm.getUserid(),RoleConstants.ROLETYPE_SUBROLE);
			for(int i=0;i<roleList.size();i++){
				TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
				//2009-5-26 登陆用户角色是合作伙伴人力信息管理员，才能删除
				if(tempRole.getRoleId() == roleIdList.getUserRoleId().intValue()){//PartnerUserConstants.PARTNER_USER_ROLEID
					hasRightForDel = "1";
				}
			}
		}
		
		//不是管理员则只能查看自己部门的人力信息
		if(!sessionForm.getUserid().equals("admin")){
			String userDeptId = sessionForm.getDeptid();
			whereStr += " and deptId like '"+userDeptId+"% '";
		}

		
		Map map = (Map) partnerUserMgr.getPartnerUsers(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PartnerUserConstants.PARTNERUSER_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("hasRightForDel", hasRightForDel);
		return mapping.findForward("commonListCompanyUser");
	}
	
	/**
	 *公共查询详情页面
	 */ 	
	  public ActionForward commonDetail(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
//	    	String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
	    	String personCardNo = StaticMethod.null2String(request.getParameter("personCardNo"));
	    	String personCardNo1 = StaticMethod.null2String(request.getParameter("personCardNo1"));
	    	PartnerUserForm partnerUserForm = (PartnerUserForm)form;//(PartnerUserForm) form;
			String id = StaticMethod.nullObject2String((request.getParameter("id")));

			if(personCardNo.equals("")){
				personCardNo = personCardNo1;
			}
			
			//add 王思轩 当前用户是否后有合作伙伴管理角色下的 合作伙伴人力资源管理 角色
			String hasRightForDel = "0";
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
			
			//2009-5-26 新建类RoleIdList，定义角色属性
			RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");

			if(sessionForm.getUserid().equals("admin")){
				hasRightForDel = "1";
			}else{
				List roleList = sessionForm.getRolelist();
				for(int i=0;i<roleList.size();i++){
					TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
					//2009-5-26 登陆用户角色是合作伙伴人力信息管理员，才能删除
					if(tempRole.getRoleId() == roleIdList.getUserRoleId().intValue()){//PartnerUserConstants.PARTNER_USER_ROLEID
						hasRightForDel = "1";
					}
				}
			}
			
			if(!id.equals("")){
				PartnerUser partnerUser = partnerUserMgr.getPartnerUser(id);
				partnerUserForm = (PartnerUserForm) convert(partnerUser);
				String workTime = StaticMethod.null2String(partnerUserForm.getWorkTime());
				String deptWorkTime = StaticMethod.null2String(partnerUserForm.getDeptWorkTime());
				if (!workTime.equals("")&&workTime.length()>=10) {
					workTime = workTime.substring(0, 10);
					partnerUserForm.setWorkTime(workTime);
				}
				if(!deptWorkTime.equals("")&&deptWorkTime.length()>=10){
					deptWorkTime = deptWorkTime.substring(0, 10);
					partnerUserForm.setDeptWorkTime(deptWorkTime);
				}
				updateFormBean(mapping, request, partnerUserForm);
				//return mapping.findForward("detail");
			}
			if(!personCardNo.equals("")){
			}

			//处理电子照片
			String accessory = partnerUserForm.getAccessory();
			if(accessory!=null&&!accessory.equals("")){
				String dbPath = "/partner/upUserPhotos/" + accessory;
				request.setAttribute("imgSrc", dbPath);
			}
//			request.setAttribute("nodeId", nodeId);
			request.setAttribute("hasRightForDel", hasRightForDel);
//			request.setAttribute("listName", listName);
//			request.setAttribute("listId", list_id);
			return mapping.findForward("commonDetail");
		}

	  /**
	   * 查询技能认证的代维人员
	   */
	  public ActionForward findExamUser(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		  String pageIndexName = new org.displaytag.util.ParamEncoder(
					PartnerUserConstants.PARTNERUSER_LIST)
					.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
			final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
					.getPageSize();
			final Integer pageIndex = new Integer(GenericValidator
					.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
					: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
			String whereStr = " where 1=1 and postState='112020403' and approveStatus<>0 "; 
			//组装查询条件
			if(request.getParameter("nameSearch")!=null&&!request.getParameter("nameSearch").equals("")){
				whereStr += " and name like '%"+request.getParameter("nameSearch")+"%'";
			}
			//------------------
			if(request.getParameter("phoneSearch")!=null&&!request.getParameter("phoneSearch").equals("")){
				whereStr += " and phone like '%"+request.getParameter("phoneSearch")+"%'";
			}
			if(request.getParameter("emailSearch")!=null&&!request.getParameter("emailSearch").equals("")){
				whereStr += " and email like '%"+request.getParameter("emailSearch")+"%'";
			}
			if(request.getParameter("prov")!=null&&!request.getParameter("prov").equals("")&&!"请选择".equals(request.getParameter("prov"))){
				whereStr += " and partnerid ='"+request.getParameter("prov")+"'";
			}
			
			//人力信息管理员才能批量删除 2009-7-29
			String hasRightForDel = "0";
				
			//2009-5-26 新建类RoleIdList，定义角色属性
			RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
			
			ITawSystemUserRefRoleManager mgr = (ITawSystemUserRefRoleManager) ApplicationContextHolder
			.getInstance().getBean("itawSystemUserRefRoleManager");
			
			if(sessionForm.getUserid().equals("admin")){
				hasRightForDel = "1";
			}else{
				List<TawSystemSubRole> roleList = mgr.getSubRoleByUserId(sessionForm.getUserid(),RoleConstants.ROLETYPE_SUBROLE);
				for(int i=0;i<roleList.size();i++){
					TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
					//2009-5-26 登陆用户角色是合作伙伴人力信息管理员，才能删除
					if(tempRole.getRoleId() == roleIdList.getUserRoleId().intValue()){//PartnerUserConstants.PARTNER_USER_ROLEID
						hasRightForDel = "1";
					}
				}
			}
			
			//不是管理员则只能查看自己部门的人力信息
			if(!sessionForm.getUserid().equals("admin")){
				String userDeptId = sessionForm.getDeptid();
				whereStr += " and deptId like '"+userDeptId+"% '";
			}
			
			
			Map map = (Map) partnerUserMgr.getPartnerUsers(pageIndex, pageSize, whereStr);
			List list = (List) map.get("result");
			request.setAttribute(PartnerUserConstants.PARTNERUSER_LIST, list);
			request.setAttribute("resultSize", map.get("total"));
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("hasRightForDel", hasRightForDel);
		  
		  return mapping.findForward("companyExamUserList");
	  }
	  
	  /**
	   * 待审批列表
	   */
	  public ActionForward approvedList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		  String pageIndexName = new org.displaytag.util.ParamEncoder(
					PartnerUserConstants.PARTNERUSER_LIST)
					.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
			final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
					.getPageSize();
			final Integer pageIndex = new Integer(GenericValidator
					.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
					: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
			
			String whereStr = " where  approveStatus=0"; //状态为待审批
			
			//postState='112020403' 代表未上岗人员
			whereStr += " and postState='112020403' ";
			
			//组装查询条件
			if(request.getParameter("nameSearch")!=null&&!request.getParameter("nameSearch").equals("")){
				whereStr += " and name like '%"+request.getParameter("nameSearch")+"%'";
			}
			//------------------
			if(request.getParameter("phoneSearch")!=null&&!request.getParameter("phoneSearch").equals("")){
				whereStr += " and phone like '%"+request.getParameter("phoneSearch")+"%'";
			}
			if(request.getParameter("emailSearch")!=null&&!request.getParameter("emailSearch").equals("")){
				whereStr += " and email like '%"+request.getParameter("emailSearch")+"%'";
			}
			if(request.getParameter("prov")!=null&&!request.getParameter("prov").equals("")&&!"请选择".equals(request.getParameter("prov"))){
				whereStr += " and partnerid ='"+request.getParameter("prov")+"'";
			}
			
			//人力信息管理员才能批量删除 2009-7-29
			String hasRightForDel = "0";
				
			//2009-5-26 新建类RoleIdList，定义角色属性
			RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
			
			ITawSystemUserRefRoleManager mgr = (ITawSystemUserRefRoleManager) ApplicationContextHolder
			.getInstance().getBean("itawSystemUserRefRoleManager");
			
			if(sessionForm.getUserid().equals("admin")){
				hasRightForDel = "1";
			}else{
				List<TawSystemSubRole> roleList = mgr.getSubRoleByUserId(sessionForm.getUserid(),RoleConstants.ROLETYPE_SUBROLE);
				for(int i=0;i<roleList.size();i++){
					TawSystemSubRole tempRole = (TawSystemSubRole)roleList.get(i);
					//2009-5-26 登陆用户角色是合作伙伴人力信息管理员，才能删除
					if(tempRole.getRoleId() == roleIdList.getUserRoleId().intValue()){//PartnerUserConstants.PARTNER_USER_ROLEID
						hasRightForDel = "1";
					}
				}
			}
			
			//不是管理员则只能查看自己部门的人力信息
			if(!sessionForm.getUserid().equals("admin")){
				String userDeptId = sessionForm.getDeptid();
				whereStr += " and deptId like '"+userDeptId+"% '";
			}
			
			
			/**
	    	 * 列出用户所属权限下的地市
	    	 * 2010-4-28
	    	 * wangjunfeng
	    	 */
	    	String userId = this.getUser(request).getUserid();
			
			PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
			String province = pnrBaseAreaIdList.getRootAreaId();
			List city = new ArrayList();
			if(userId=="admin" || "admin".equals(userId)){//管理员登陆显示省下的所有地市
	    		 city = PartnerCityByUser.getCityByProvince(province);
	    	}else{//非管理员用户登陆，显示权限下的地市
	    		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder.getInstance().getBean("partnerUserAndAreaMgr");
				List areasRight = partnerUserAndAreaMgr.listCountyOfPnrUserArea(userId);
				PartnerUserAndArea partnerUserAndArea = (PartnerUserAndArea)areasRight.get(0);
				String areas = partnerUserAndArea.getCityId();
		    	String [] areasTem = areas.split(",");
		    	StringBuffer areasBuffer = new StringBuffer();
		    	for(int i=0;i < areasTem.length ;i++){
		    		areasBuffer.append("'" );
		    		areasBuffer.append(areasTem[i] );
		    		areasBuffer.append("'" );
		    		areasBuffer.append("," );
		    	}
		    	String areasLast = areasBuffer.substring(0, areasBuffer.length()-1).toString();
	    		// city = PartnerCityByUser.getCityByRight(province,areasLast); 
	    	}
	    	request.setAttribute("city", city);
			
			Map map = (Map) partnerUserMgr.getPartnerUsers(pageIndex, pageSize, whereStr);
			List list = (List) map.get("result");
			request.setAttribute(PartnerUserConstants.PARTNERUSER_LIST, list);
			request.setAttribute("resultSize", map.get("total"));
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("hasRightForDel", hasRightForDel);
		  return mapping.findForward("companyExamUserApproveList");
	  }
	  
	  /**
	   * 审批多个
	   */
	  public ActionForward approveAll(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		  String pass = request.getParameter("pass");
		  Integer approveStatus = "true".equals(pass) ? 1 : 2;
		  String[] ids = request.getParameterValues("checkbox11");
		  PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
	        if(ids!=null){
		        for(int i=0;i<ids.length;i++){
		        	partnerUserMgr.approve(ids[i], approveStatus.toString());
		        }
	        } 
		  return mapping.findForward("success");
	  }
	  
	  /**
	   * Ajax验证身份证唯一性
	   */
	  public ActionForward checkPersonCardNoUnique(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		  request.setCharacterEncoding("UTF-8");
		  PrintWriter out = response.getWriter();
		  String personCardNo = request.getParameter("personCardNo");
		  PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		  boolean flag = partnerUserMgr.isPersonCardNoUnique(personCardNo);
		  out.print(flag);
		  return null;
	  }
	  
	  /**
	   * Ajax验证userid唯一性
	   */
	  public ActionForward checkUserIdUnique(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		  request.setCharacterEncoding("UTF-8");
		  PrintWriter out = response.getWriter();
		  String userId = request.getParameter("userId");
		  PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		  boolean flag = partnerUserMgr.isunique(userId);
		  out.print(flag);
		  return null;
	  }
	  /**
	   * Ajax验证password是否符合要求
	   */
	  public ActionForward checkUserPassWord(ActionMapping mapping, ActionForm form,
			  HttpServletRequest request, HttpServletResponse response)
	  throws Exception {
		  request.setCharacterEncoding("UTF-8");
		  ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");
		  PrintWriter out = response.getWriter();
		  String password =StaticMethod.null2String(request.getParameter("password"));
		  boolean flag=true;
		  if (!mgr.checkPasswd(password)) {
			  flag=false;
		  }
		  out.print(flag);
		  return null;
	  }
	  
	
}