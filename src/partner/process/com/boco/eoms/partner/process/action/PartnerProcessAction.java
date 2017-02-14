package com.boco.eoms.partner.process.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.accessories.model.TawCommonsAccessories;
import com.boco.eoms.commons.accessories.service.ITawCommonsAccessoriesManager;
import com.boco.eoms.commons.accessories.util.AccessoriesMgrLocator;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.personnel.util.PageData;
import com.boco.eoms.partner.personnel.util.SearchUtil;
import com.boco.eoms.partner.process.model.PartnerProcessLink;
import com.boco.eoms.partner.process.model.PartnerProcessMain;
import com.boco.eoms.partner.process.service.PartnerProcessLinkService;
import com.boco.eoms.partner.process.service.PartnerProcessMainService;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.process.util.PnrProcessHandler;
import com.boco.eoms.partner.resourceInfo.model.OilEngine;
import com.boco.eoms.partner.resourceInfo.service.OilEngineService;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class PartnerProcessAction  extends BaseAction{
	/**
	 * 进入申请页面
	 */
	public ActionForward goToApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String dictId=request.getParameter("dictId");
		request.setAttribute("dictId", dictId);
		return mapping.findForward("goToApply");
	}
	/**
	 * 下载导入模板文件
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String changeType=request.getParameter("changeType");
		String referenceModel=request.getParameter("referenceModel");
		ID2NameService service = (ID2NameService) getBean("ID2NameGetServiceCatch");
		changeType =service.id2Name(changeType, "tawSystemDictTypeDao");
		referenceModel =service.id2Name(referenceModel, "tawSystemDictTypeDao");
		String fileName=changeType+referenceModel+".xls";
		String rootPath = request.getRealPath(File.separator+"partner"+ File.separator+"processExcelModel");
		File file = new File(rootPath + File.separator + fileName);
		InputStream inStream = new FileInputStream(file);// 文件的存放路径
		response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("GB2312");
		response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileName.getBytes("gbk"), "iso8859-1"));
		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len = 0;
		 OutputStream os=response.getOutputStream();
		while ((len = inStream.read(b)) > 0)  {
			os.write(b, 0, len);
		}
		os.flush();
		os.close();
		inStream.close();
		return null;
	}
	
	/**
	 * 上传的excel文件校验
	 */
	public ActionForward xlsValidate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		String changeType=request.getParameter("changeType");
		String referenceModel=request.getParameter("referenceModel");
		String changeAttachment=request.getParameter("changeAttachment");
		String dataReceiver=request.getParameter("dataReceiver");
		String idMain=StaticMethod.null2String(request.getParameter("idMain"));	
		PartnerProcessMainService ppMainService=(PartnerProcessMainService)getBean("partnerProcessMainService");
		PartnerProcessLinkService ppLinkService=(PartnerProcessLinkService)getBean("partnerProcessLinkService");
		PartnerProcessMain ppMain=ppMainService.find(idMain);
		if (ppMain==null) {
			ppMain=new PartnerProcessMain();
		}
		PartnerProcessLink  ppLink=new PartnerProcessLink();
		ppMain.setChangeAttachment(changeAttachment);
		ppMain.setReferenceModel(referenceModel);
		ppMain.setChangeType(changeType);
		PrintWriter writer=response.getWriter();
		try {
			long startTime=System.currentTimeMillis();
			PnrProcessCach.loadAllOperation();
			ImportResult importResult=ppMainService.validateXLSFile(ppMain);
			if (importResult.getImportCount()<1) {
				writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "您导入的记录条数为0，请重新导入!").build()));
				return null;
			}
			if (importResult.getResultCode().equals("200")) {
				//设置状态为审核中,字典值
				ppMain.setCurrentState("123050201");
				ppMain.setStartTime(CommonUtils.toEomsStandardDate(new Date()));
				String createUser=this.getUserId(request);
				ppMain.setCreateUser(createUser);
				ppMain.setEndTime(CommonUtils.toEomsStandardDate(new Date()));
				ppMain.setDeleted("0");
				ppLink.setReason("No reason");
				ppLink.setState("123050201");
				//记录产生时间（状态：草稿->审核中产生时间）
				ppLink.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
				//数据发起者
				ppLink.setDataSender(createUser);
				//数据接收者
				ppLink.setDataReceiver(dataReceiver);
				//保存数据库
				ppMainService.save(ppMain);
				ppLink.setReferenceId(ppMain.getId());
				ppLinkService.save(ppLink);
				Date endDate=new Date();
				long endTime=System.currentTimeMillis();
				long sec=(endTime-startTime)/1000;
				long min=sec/60;
				String usedTime="";
				if (min>0) {
					usedTime+=Long.toString(min)+"分钟,";
				}
				usedTime+=Long.toString(sec)+"秒";
//				System.out.print("本次导入耗时"+usedTime);
				writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true")
						.put("msg", "ok")
						.put("infor", "文件校验成功,申请文件已经提交！本次共计导入"+importResult.getImportCount()+"条记录,\n耗时"+usedTime).build()));
			}
		}catch (Exception e) {
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
	 * 移动管理人员对申请的审核，申请有2种操作结果：通过审核/驳回 
	 */
	public ActionForward applyOperate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		PartnerProcessMainService ppMainService=(PartnerProcessMainService)getBean("partnerProcessMainService");
		PartnerProcessLinkService ppLinkService=(PartnerProcessLinkService)getBean("partnerProcessLinkService");
		//前台获取操作结果：通过审核/驳回
		String applyOperateVal=request.getParameter("operateVal");
		String state="";
		if (applyOperateVal.equals("123050301")) {
			//通过审核
			state="123050202";
		}else {
			//驳回
			state="123050203";
		}
		//前台获取获取main表的id
		String idMain=request.getParameter("idMain");
		//前台获取审批意见
		String reason=request.getParameter("reason");
		//前台申请创建人 
		String createUser=request.getParameter("createUser");
		//在link表中新增一条数据数据:状态为通过审核/驳回，
		PartnerProcessLink ppLink=new PartnerProcessLink();
		ppLink.setReferenceId(idMain);
		ppLink.setState(state);
		ppLink.setCreateTime(CommonUtils.toEomsStandardDate(new Date(System.currentTimeMillis())));
		ppLink.setReason(reason);
		//此时移动的审核人员为数据传送者，代维人员为数据接收者
		String userId=this.getUserId(request);
		ppLink.setDataSender(userId);
		ppLink.setDataReceiver(createUser );
		//main表根据Id获取实体后更新数据，最新状态设置为通过审核/驳回
		PartnerProcessMain ppMain=ppMainService.find(idMain);
		ppMain.setCurrentState(state);
		ppMain.setEndTime(CommonUtils.toEomsStandardDate(new Date()));
		//保存数据库
		try {
			ppMainService.save(ppMain);
			ppLinkService.save(ppLink);
			//重定向到待审批页面
			return mapping.findForward("goToApplying");
		} catch (RuntimeException e) {
			e.printStackTrace();
			//错误页面
			return mapping.findForward("error");
		}
	}
	/**
	 * 移动管理人员由待审批页面进入申请操作页面
	 */
	public ActionForward applyDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		String idMain=request.getParameter("idMain");
		PartnerProcessMainService ppMainService=(PartnerProcessMainService)getBean("partnerProcessMainService");
		PartnerProcessMain ppMain=ppMainService.find(idMain);
		request.setAttribute("ppMain", ppMain);
		return mapping.findForward("applyDetail");
	}
	
	/*
	 * 移动人员待审批页面中下载申请文件
	 */
	public ActionForward applyFileDownload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String idMain=request.getParameter("idMain");
		List list=new ArrayList();
		InputStream inStream=null;
		OutputStream os=null;
		String filePathAndName="";
		try {
			PartnerProcessMainService ppMainService=(PartnerProcessMainService)getBean("partnerProcessMainService");
			PartnerProcessMain ppMain=ppMainService.find(idMain);
			PnrProcessHandler ppHandler=new PnrProcessHandler();
			list = ppHandler.getFileDetail(ppMain);
			filePathAndName=list.get(1).toString();
			// 读到流中
			inStream= new FileInputStream(((File)list.get(2)));
			// 设置输出的格式
			response.reset();
			response.setContentType("application/x-msdownload;charset=GBK");
			response.setCharacterEncoding("GB2312");
			response.setHeader("Content-Disposition", "attachment; filename="+ new String(((String)list.get(0)).getBytes("gbk"), "iso8859-1"));
			// 循环取出流中的数据
			byte[] b = new byte[1024];
			int len = 0;
			os=response.getOutputStream();
			while ((len = inStream.read(b)) > 0)  {
				os.write(b, 0, len);
			}
			os.flush();
		}catch (java.io.FileNotFoundException e) {
			System.out.println(e);
			request.setAttribute("msg","文件系统中未找到"+filePathAndName+"文件,该文件可能已经被删除,请联系管理员!");
			return mapping.findForward("failure");
		}catch (Exception e1) {
			System.out.println(e1);
			request.setAttribute("msg",e1);
			return mapping.findForward("failure");
		}finally{
			if (os!=null) {
				os.close();
			}if (inStream!=null) {
				inStream.close();
			}
		}
		return null;
	}
	/**
	 * 移动管理人员进入待审批页面
	 */
	public ActionForward goToApplyOperatePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId=this.getUserId(request);
		if ("admin".equals(userId)) {
			userId="%%";
		}
		//String userId="kf10086";
		//提取登录用户的待审批信息,状态为审核中123050201
		String state="123050201";
		PnrProcessHandler ppHandler=new PnrProcessHandler();
		String dictId=ppHandler.getDictId(request);
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		String pageIndexName = new org.displaytag.util.ParamEncoder("resultList").
		encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		StringBuilder  entrySql = new StringBuilder();
		entrySql.append("select ptnmain.*  from pnr_process_main  ptnmain where ptnmain.current_state='")
		.append(state)
		.append("' and deleted <> '1' and reference_model like '"+dictId+"%'  and ptnmain.id  in(")
		.append("select b.reference_id from pnr_process_link   b where b.data_receiver like'")
		.append(userId)
		.append("')");
		StringBuilder  countSql = new StringBuilder();
		countSql.append("select count(*) as count from ").append("( ").append(entrySql).append(" )");
		SearchUtil<PartnerProcessMain> search = new SearchUtil<PartnerProcessMain>(PartnerProcessMain.class,"ptnmain",entrySql.toString(),countSql.toString());
		PageData<PartnerProcessMain> pageData = search.getDataList(pageIndex);
        request.setAttribute("pageSize", pageData.getPageSize());
		request.setAttribute("resultSize", pageData.getTotalCount());
		request.setAttribute("resultList", pageData.getList());
		return mapping.findForward("goToApplyOperatePage");
	}
	/**
	 * 
	 *@Description：移动人员查看已经审批的列表包含通过和删除的;
	 *@date May 8, 2013 2:29:17 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param mapping
	 *@param form
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	public ActionForward  goToSignedList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId=this.getUserId(request);
		String where="";
		if (!"admin".equals(userId)) {
			where=" and l.data_receiver='"+userId+"'";
		}
		PnrProcessHandler ppHandler=new PnrProcessHandler();
		String dictId=ppHandler.getDictId(request);
		request.setAttribute("dictId", dictId);
		int pageIndex = CommonUtils.getFirstResultOfDisplayTag(request, "resultList");
		int pageSize = 15;
		int skip = pageIndex*pageSize;
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl)getBean("commonSpringJdbcService");
		String sql="  l.state,data_receiver,m.start_time,m.end_time,m.deleted,m.id,m.reference_model,m.change_type,m.change_attachment,m.end_time as signedtime" +
				" from pnr_process_link l,pnr_process_main m	 where m.id=l.reference_id  " +
				" and m.current_state <> '123050201' and l.state <> '123050201' and m.reference_model like '"+dictId+"%' "+where+" order by m.start_time ";
		List<ListOrderedMap> list=jdbcService.queryForList(CommonSqlHelper.formatPageSql(sql, skip, pageSize));
		request.setAttribute("resultList", list);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("resultSize", CommonSqlHelper.getResultSize(sql));
		return mapping.findForward("goToSignedListPage");
	}
	/**
	 * 代维管理人员查看审核通过入口
	 */
	public ActionForward  applyAccepted(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//获取代维人员提交的申请结果(条件过滤)
		String userId=this.getUserId(request);
		if ("admin".equals(userId)) {
			userId="%%";
		}
		PnrProcessHandler ppHandler=new PnrProcessHandler();
		String dictId=ppHandler.getDictId(request);
		PartnerProcessMainService ppMainService=(PartnerProcessMainService)getBean("partnerProcessMainService");
		PartnerProcessLinkService ppLinkService=(PartnerProcessLinkService)getBean("partnerProcessLinkService");
		Search searchMain=new Search();
		//添加结果为审核通过条件
		searchMain.addFilterILike("currentState", "123050202");
		searchMain.addFilterNotEqual("deleted", "1");
		searchMain.addFilterILike("createUser", userId);
		searchMain.addFilterILike("referenceModel", dictId+"%");
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, "resultList");
		Integer pageSize= UtilMgrLocator.getEOMSAttributes().getPageSize();
		searchMain.setFirstResult(firstResult * pageSize.intValue());
		searchMain.setMaxResults(pageSize.intValue());
		SearchResult<PartnerProcessMain> searchMainResult=ppMainService.searchAndCount(searchMain);
		List<PartnerProcessMain> listMain=searchMainResult.getResult();
		List<Object[]> list = new ArrayList<Object[]>();
		Object[] o;
		for(PartnerProcessMain ppMain : listMain) {
			o = new Object[2];
			Search searchLink=new Search();
			searchLink.addFilterILike("referenceId", ppMain.getId());
			searchLink.addSort("createTime",true);
			SearchResult<PartnerProcessLink> searchLinkResult=ppLinkService.searchAndCount(searchLink);
			List<PartnerProcessLink> listLink=searchLinkResult.getResult();
			PartnerProcessLink ppLink=listLink.get(0);
			o[0] = ppMain;
			o[1] = ppLink;
			list.add(o);
		}
		int resultSize=searchMainResult.getTotalCount();
		request.setAttribute("resultList", list);
		request.setAttribute("resultSize", resultSize);
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("goToApplyAcceptedResultPage");
	}
	/**
	 * 代维管理人员查看未通过审核页面入口
	 */
	public ActionForward applyUnaccepted(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		//获取代维人员提交的申请结果(条件过滤)
		PnrProcessHandler ppHandler=new PnrProcessHandler();
		String dictId=ppHandler.getDictId(request);
		String userId=this.getUserId(request);
		if ("admin".equals(userId)) {
			userId="%%";
		}
		PartnerProcessMainService ppMainService=(PartnerProcessMainService)getBean("partnerProcessMainService");
		PartnerProcessLinkService ppLinkService=(PartnerProcessLinkService)getBean("partnerProcessLinkService");
		Search searchMain=new Search();
		//添加结果为审核驳回条件"123050203"
		searchMain.addFilterILike("currentState","123050203");
		searchMain.addFilterNotEqual("deleted", "1");
		searchMain.addFilterILike("createUser", userId);
		searchMain.addFilterILike("referenceModel", dictId+"%");
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, "resultList");
		Integer pageSize= UtilMgrLocator.getEOMSAttributes().getPageSize();
		searchMain.setFirstResult(firstResult * pageSize.intValue());
		searchMain.setMaxResults(pageSize.intValue());
		SearchResult<PartnerProcessMain> searchMainResult=ppMainService.searchAndCount(searchMain);
		List<PartnerProcessMain> listMain=searchMainResult.getResult();
		List<Object[]> list = new ArrayList<Object[]>();
		Object[] o;
		for(PartnerProcessMain ppMain : listMain) {
			o = new Object[2];
			Search searchLink=new Search();
			searchLink.addFilterILike("referenceId", ppMain.getId());
			searchLink.addSort("createTime",true);
			SearchResult<PartnerProcessLink> searchLinkResult=ppLinkService.searchAndCount(searchLink);
			List<PartnerProcessLink> listLink=searchLinkResult.getResult();
			PartnerProcessLink ppLink=listLink.get(0);
			o[0] = ppMain;
			o[1] = ppLink;
			list.add(o);
		}
		int resultSize=searchMainResult.getTotalCount();
		request.setAttribute("resultList", list);
		request.setAttribute("resultSize", resultSize);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("dictId", dictId);
		return mapping.findForward("goToApplyUnacceptedResultPage");
	}
	/**
	 * 被驳回后再次发起申请,修改main表中的当前状态和附件名称
	 */
	public ActionForward applyAgain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		PnrProcessHandler ppHandler=new PnrProcessHandler();
		String dictId=ppHandler.getDictId(request);
		String idMain=request.getParameter("idMain");
		String idLink=request.getParameter("idLink");
		PartnerProcessMainService ppMainService=(PartnerProcessMainService)getBean("partnerProcessMainService");
		PartnerProcessLinkService ppLinkService=(PartnerProcessLinkService)getBean("partnerProcessLinkService");
		PartnerProcessMain ppMain=ppMainService.find(idMain);
		PartnerProcessLink ppLink=ppLinkService.find(idLink);
		request.setAttribute("ppMain", ppMain);
		request.setAttribute("ppLink", ppLink);
		request.setAttribute("dictId", dictId);
		return mapping.findForward("applyAgain");
	}
	
	/**
	 * 被驳回后终止申请,将main中delete字段设为“1”
	 */
	public ActionForward applyEnd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out=null;
		String fileName="";
		try {
			out=response.getWriter();
			String idMain=request.getParameter("idMain");
			PnrProcessHandler ppHandler=new PnrProcessHandler();
			String dictId=ppHandler.getDictId(request);
			PartnerProcessMainService ppMainService=(PartnerProcessMainService)getBean("partnerProcessMainService");
			PartnerProcessMain ppMain=ppMainService.find(idMain);
			fileName=ppMain.getChangeAttachment();
			ppMain.setEndTime(CommonUtils.toEomsStandardDate(new Date()));
			ppMain.setDeleted("1");
			ppMainService.restoreXLSFileData2DB(ppMain, request);//还原数据
			ppMainService.save(ppMain);
			out.print("终止成功!");
		} catch (RuntimeException e) {
			out.print("终止失败!"+e);
		}catch (java.io.FileNotFoundException e) {
			System.out.println(e);
			out.print("终止失败!\r\n文件系统中的文件"+fileName+"不存在,可能已经被删除.请联系管理员!");
		}catch (Exception e) {
			System.out.println(e);
			out.print("终止失败,请联系管理员!"+e);
		}finally{
			if (out!=null) {
				out.close();
			}
			return null;
		}
	}
	/**
	 * 审核通过后执行归档,归档后delete设置为1，表示该申请结束
	 */
	public ActionForward applyToFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter out=null;
		String fileName="";
		try {	
			out=response.getWriter();
			String idMain=request.getParameter("idMain");
			PnrProcessHandler ppHandler=new PnrProcessHandler();
			String dictId=ppHandler.getDictId(request);
			PartnerProcessMainService ppMainService=(PartnerProcessMainService)getBean("partnerProcessMainService");
			PartnerProcessMain ppMain=ppMainService.find(idMain);
			ppMain.setEndTime(CommonUtils.toEomsStandardDate(new Date()));
			ppMain.setDeleted("1");
			fileName=ppMain.getChangeAttachment();
			boolean result=ppMainService.storeXLSFileData2DB(ppMain, request);
			ppMainService.save(ppMain);//保存归档后信息
			out.print("归档成功!");
		} catch (RuntimeException e) {
			out.print("归档失败!"+e.getMessage().toString());
		}catch (java.io.FileNotFoundException e) {
			System.out.println(e);
			out.print("归档失败!\r\n文件系统中的文件"+fileName+"不存在,可能已经被删除.请联系管理员!");
		}catch (Exception e) {
			System.out.println(e);
			out.print("归档失败,请联系管理员!"+e);
		}finally{
			if (out!=null) {
				out.close();
			}
			return null;
		}
	}
	/**
	 * 已归档列表，查询已经归档的列表
	 *fengguangping
	 * Nov 2, 2012-2:58:59 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward hasFiled(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return null;
	}
	/**
	 * 
	 *@Description 物理删除申请信息和流转信息;
	 *@date May 8, 2013 5:55:26 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param mapping
	 *@param form
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer=null;
		String id=request.getParameter("idMain");
		PartnerProcessMainService ppMainService=(PartnerProcessMainService)getBean("partnerProcessMainService");
		try {
			writer=response.getWriter();
			ppMainService.deletePPmainAndPPlinkByPPmainid(id);
			writer.print("删除成功!");
		} catch (Exception e) {
			writer.print("删除失败,请重试!"+StaticMethod.null2String(e.getMessage()));
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从GIS提交变更申请
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitChangeForGis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取文件保存路径
		String rootFilePath = AccessoriesMgrLocator
				.getAccessoriesAttributes().getUploadPath();

		String rootPath = request.getRealPath("/partner/processExcelModel");		
		String inputFilePath = rootPath + File.separator + "修改油机.xls";
		String fileName = StaticMethod
		.getCurrentDateTime("yyyyMMddHHmmss")+this.randomKey(4)+inputFilePath.substring(inputFilePath
				.lastIndexOf("."));
		String path = "/partner/uploadfile/netresource";
		String outputFilePath =rootFilePath + path + "/" + fileName;
		copyFile(inputFilePath,outputFilePath);


		OilEngineService oilEngineService = (OilEngineService) getBean("oilEngineService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		String longitude = StaticMethod.null2String(request.getParameter("longitude"));
		String latitude = StaticMethod.null2String(request.getParameter("latitude"));
		OilEngine oilEngine = oilEngineService.find(id);		
		StringBuffer gisTrack_buffer = new StringBuffer();
		Gson gson = new Gson();
		HashMap<String, String> jsonMap=new HashMap();
		
		
		
		//将值写入excel
		try{
			response.setCharacterEncoding("utf-8");
			Workbook wb = Workbook.getWorkbook(new File(outputFilePath));
//			Sheet sheet = wb.getSheet(0);
			
			WritableWorkbook wwb = Workbook.createWorkbook(new File(outputFilePath), wb);
			WritableSheet wws = wwb.getSheet(0);
//			WritableCell cell = wws.getWritableCell(3,2);
//			CellFormat cf = cell.getCellFormat();
//			jxl.write.Label lbl = new jxl.write.Label(2,2, "修改后的值");

			ITawSystemAreaManager areaManager= (ITawSystemAreaManager)ApplicationContextHolder
			.getInstance().getBean("ItawSystemAreaManager");
			TawSystemArea area =  areaManager.getAreaByAreaId(oilEngine.getArea());
			String areaName = area.getAreaname();
			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
			PartnerDept pnrDept = partnerDeptMgr.getPartnerDeptsByHql("partnerDept.deptMagId='" + oilEngine.getMaintainCompany() + "'").get(0);
			String deptName = pnrDept.getName();
			ITawSystemDictTypeManager dictManager = (ITawSystemDictTypeManager) ApplicationContextHolder.getInstance().getBean("ItawSystemDictTypeManager");			
		
			dictManager.getDictByDictId(oilEngine.getOilEngineType()).getDictName();
 			wws.addCell(new Label(0,2,String.valueOf(oilEngine.getId()))); //序号    
			wws.addCell(new Label(1,2,areaName));               //区域    
			wws.addCell(new Label(2,2,deptName));               //代维公司
			wws.addCell(new Label(3,2,oilEngine.getOilEngineNumber()));               //油机编号
			wws.addCell(new Label(4,2,oilEngine.getOilEngineModel()));               //规格型号
			wws.addCell(new Label(5,2,oilEngine.getOilEngineFactory()));               //生产厂家
			wws.addCell(new Label(6,2,oilEngine.getPowerRating()));               //额定功率
			wws.addCell(new Label(7,2,oilEngine.getStandardFuelConsumption()));               //标准油耗
			wws.addCell(new Label(8,2,dictManager.getDictByDictId(oilEngine.getOilEngineType()).getDictName()));               //油机类型
			wws.addCell(new Label(9,2,dictManager.getDictByDictId(oilEngine.getOilEngineProperty()).getDictName()));               //产权归属
			wws.addCell(new Label(10,2,dictManager.getDictByDictId(oilEngine.getOilEngineStatus()).getDictName()));              //油机状态
			wws.addCell(new Label(11,2,dictManager.getDictByDictId(oilEngine.getFuleType()).getDictName()));              //燃料种类
			wws.addCell(new Label(12,2,areaManager.getAreaByAreaId(oilEngine.getPlace()).getAreaname()));              //存放地点
			wws.addCell(new Label(13,2,longitude));              //经度
			wws.addCell(new Label(14,2,latitude));              //纬度

		
			wwb.write();
			wwb.close();	
		

		
		// 附件信息入库

		ITawCommonsAccessoriesManager mgr = (ITawCommonsAccessoriesManager) getBean("ItawCommonsAccessoriesManager");
		
		TawCommonsAccessories accessories = new TawCommonsAccessories();
		accessories.setAccessoriesCnName("修改油机.xls");
		accessories.setAccessoriesName(fileName);
		accessories.setAccessoriesPath(path);
		accessories.setAccessoriesSize(28160);
		accessories
				.setAccessoriesUploadTime(StaticMethod
						.getLocalTime());
		accessories.setAppCode("netresource");
		mgr.saveTawCommonsAccessories(accessories);
		
		String changeType="123050102";
		String referenceModel="123050403";
		String changeAttachment="'" + fileName + "'";
		String dataReceiver=StaticMethod.null2String(request.getParameter("dataReceiver"));              
//		String dataReceiver = "admin";
		PartnerProcessMainService ppMainService=(PartnerProcessMainService)getBean("partnerProcessMainService");
		PartnerProcessLinkService ppLinkService=(PartnerProcessLinkService)getBean("partnerProcessLinkService");
		PartnerProcessMain ppMain=new PartnerProcessMain();
		PartnerProcessLink  ppLink=new PartnerProcessLink();
		ppMain.setChangeAttachment(changeAttachment);
		ppMain.setReferenceModel(referenceModel);
		ppMain.setChangeType(changeType);
		//设置状态为审核中,字典值
		ppMain.setCurrentState("123050201");
		ppMain.setStartTime(CommonUtils.toEomsStandardDate(new Date()));
		String createUser=this.getUserId(request);
		ppMain.setCreateUser(createUser);
		ppMain.setEndTime(CommonUtils.toEomsStandardDate(new Date()));
		ppMain.setDeleted("0");
		ppLink.setReason("No reason");
		ppLink.setState("123050201");
		//记录产生时间（状态：草稿->审核中产生时间）
		ppLink.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
		//数据发起者
		ppLink.setDataSender(createUser);
		//数据接收者
		ppLink.setDataReceiver(dataReceiver);
		//保存数据库
		ppMainService.save(ppMain);
		ppLink.setReferenceId(ppMain.getId());
		ppLinkService.save(ppLink);		
		jsonMap.put("type", "success");
		gisTrack_buffer.append(gson.toJson(jsonMap));
		response.getWriter().write(gisTrack_buffer.toString());
		
		}catch (IOException e) {
			jsonMap.put("type", "fail");
			gisTrack_buffer.append(gson.toJson(jsonMap));
			response.getWriter().write(gisTrack_buffer.toString());
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			jsonMap.put("type", "fail");
			gisTrack_buffer.append(gson.toJson(jsonMap));
			response.getWriter().write(gisTrack_buffer.toString());
			e.printStackTrace();
		}
		return null;
	}	
	/** 
     * 复制单个文件 
     * @param oldPath String 原文件路径 如：c:/fqf.txt 
     * @param newPath String 复制后路径 如：f:/fqf.txt 
     * @return boolean 
     */ 
   public void copyFile(String oldPath, String newPath) { 
       try { 
           int bytesum = 0; 
           int byteread = 0; 
           File oldfile = new File(oldPath); 
           if (oldfile.exists()) { //文件存在时 
               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
               FileOutputStream fs = new FileOutputStream(newPath); 
               File fileNew = new File(newPath);
               if (!fileNew.exists()){
            	   fileNew.mkdir();
               }
               byte[] buffer = new byte[1444]; 
               int length; 
               while ( (byteread = inStream.read(buffer)) != -1) { 
                   bytesum += byteread; //字节数 文件大小 
                   System.out.println(bytesum); 
                   fs.write(buffer, 0, byteread); 
               } 
               inStream.close(); 
           } 
       } 
       catch (Exception e) { 
           System.out.println("复制单个文件操作出错"); 
           e.printStackTrace(); 

       } 			   
   }	
	//生成随机数，防止循环调用附件下载接口时出现系统文件名重复的问题，传进来的sLen是几位就生成几位的随机数
	public String randomKey(int sLen) {
		String base;
		String temp;
		int i;
		int p;

		base = "1234567890";
		temp = "";
		for (i = 0; i < sLen; i++) {
			p = (int) (Math.random() * 10);
			temp += base.substring(p, p + 1);
		}
		return (temp);
	}   
}
