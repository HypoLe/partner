package com.boco.eoms.deviceManagement.baseInfo.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.baseInfo.model.BaseStation;
import com.boco.eoms.deviceManagement.baseInfo.model.BaseStationForm;
import com.boco.eoms.deviceManagement.baseInfo.service.BaseStationService;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class BaseStationManageAction extends BaseAction{

	public BaseStationService getMainBean() {
		String source = BaseStationService.class.getSimpleName();
		return (BaseStationService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}

	public BaseStationService getJdbcBean() {
		String source = BaseStationService.class.getSimpleName();
		return (BaseStationService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}

	public ActionForward forwardlist(ActionMapping mapping) {
		ActionForward forward = mapping.findForward("forwardlist");
		String path = forward.getPath();
		return new ActionForward(path, false);
	}

	public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToAdd");
	}

	public ActionForward goToImport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToImport");
	}
	@SuppressWarnings("unchecked")
	public ActionForward goToDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id =  request.getParameter("id");
		BaseStation baseStation = this.getMainBean().find(id);
		request.setAttribute("baseStation", baseStation);
		request.setAttribute("type", "check");
		return mapping.findForward("goToDetail");
	}
	public ActionForward goToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id =  request.getParameter("id");
		BaseStation baseStation = this.getMainBean().find(id);
		request.setAttribute("baseStation", baseStation);
		return mapping.findForward("goToAdd");
	}
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseStationForm baseStationForm = (BaseStationForm) form;
		BaseStation baseStation = (BaseStation) convert(baseStationForm);
		this.getMainBean().save(baseStation);
		return this.forwardlist(mapping);
	}

	@SuppressWarnings("finally")
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			String id = request.getParameter("id");
			BaseStation baseStation = this.getMainBean().find(id);
			this.getMainBean().remove(baseStation);
			return this.forwardlist(mapping);
	}
	public ActionForward deleteAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sel = StaticMethod.null2String(request
				.getParameter("sel"));
		try {
				String[] selnum = sel.split("\\|");
			if(sel!=null){
				for (int i = 0; i < selnum.length; i++) {
					getMainBean().removeById(selnum[i]);
				}
				ActionForward actionForward = new ActionForward();
				actionForward.setPath("/baseStation.do?method=list");
				actionForward.setRedirect(true);
				return actionForward;
			}
		} catch (Exception e) {
			return mapping.findForward("fail");
		}
		return mapping.findForward("fail");
	}
	
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseStationForm baseStationForm=(BaseStationForm)form;
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String addtime = CommonUtils.toEomsStandardDate(new Date());
		BaseStation baseStation = (BaseStation) convert(baseStationForm);
		baseStation.setAddman(userId);
		baseStation.setAddtime(addtime);
		Search search = new Search();
		search.addFilterEqual("baseStationName", baseStationForm.getBaseStationName());
		SearchResult<BaseStation> searchResult = this.getMainBean()
		.searchAndCount(search);
		if(searchResult.getTotalCount()==0){
			getMainBean().save(baseStation);
		}else{
			BaseStation temp=searchResult.getResult().get(0);
			String id=temp.getId();
			temp=(BaseStation)convert(baseStationForm);
			temp.setId(id);
			temp.setAddman(userId);
			temp.setAddtime(addtime);
			getMainBean().save(temp);
		}
		
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/baseStation.do?method=list");
		actionForward.setRedirect(true);
		return actionForward;
	}
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"baseStationList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		
		search.addSortDesc("addtime");
		SearchResult<BaseStation> searchResult = this.getMainBean()
				.searchAndCount(search);
		List<BaseStation> baseStationList = searchResult.getResult();
		request.setAttribute("baseStationList",baseStationList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("type", "check");
		String type = request.getParameter("type");
		if("station".equals(type)){
			return mapping.findForward("forwardlistcheck");
		}
		return mapping.findForward("forwardlist");
	}
	public ActionForward findBaseStationAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Search search = new Search();
		search.addSortDesc("addtime");
		SearchResult<BaseStation> searchResult = this.getMainBean()
		.searchAndCount(search);
		List<BaseStation> baseStationList = searchResult.getResult();
		request.setAttribute("baseStationList",baseStationList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("success");
	}
	
	/**
	 * xls导入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward xlsSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		String stationHouseType="1121802";
		String stationForm="1121801";
		String maintenanceLevel="1121803";
		ITawSystemDictTypeManager dictMgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		
		ArrayList<TawSystemDictType> maintenanceLevelList = dictMgr.getDictSonsByDictid(maintenanceLevel);
		Map<Object,Object> maintenanceLevelMap=new HashMap<Object, Object>();
		for (TawSystemDictType tawSystemDictType : maintenanceLevelList) {
			maintenanceLevelMap.put(tawSystemDictType.getDictName(),tawSystemDictType.getDictId());
		}
		
		ArrayList<TawSystemDictType> stationFormList = dictMgr.getDictSonsByDictid(stationForm);
		Map<Object,Object> stationFormMap=new HashMap<Object, Object>();
		for (TawSystemDictType tawSystemDictType : stationFormList) {
			stationFormMap.put(tawSystemDictType.getDictName(),tawSystemDictType.getDictId());
		}
		
		ArrayList<TawSystemDictType> stationHouseTypeList = dictMgr.getDictSonsByDictid(stationHouseType);
		Map<Object,Object> stationHouseTypeMap=new HashMap<Object, Object>();
		for (TawSystemDictType tawSystemDictType : stationHouseTypeList) {
			stationHouseTypeMap.put(tawSystemDictType.getDictName(),tawSystemDictType.getDictId());
		}
		String timeTag = StaticMethod.getCurrentDateTime("yyyy_MM_dd_HHmmss");
		String sysTemPath = request.getRealPath("/");
		BaseStationForm accessoryForm = (BaseStationForm) form;
		String uploadPath = "/WEB-INF/pages/deviceManagement/baseStation/upfiles";
		String filePath = sysTemPath + uploadPath + "/" + timeTag + ".xls";
		File tempFile = new File(sysTemPath + uploadPath);
		if (!tempFile.exists()) {
			tempFile.mkdir();
		}
		FormFile file = accessoryForm.getAccessoryName();
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
				BaseStation temp = new BaseStation();
				if (dataSheet.getCell(0, i).getContents() != null
						&& !"".equals(dataSheet.getCell(0, i).getContents())) {
					Search search = new Search();
					String basestationnamecel=dataSheet.getCell(0, i).getContents();
					search.addFilterEqual("baseStationName", basestationnamecel);
					SearchResult<BaseStation> searchResult = this.getMainBean()
					.searchAndCount(search);
						if (searchResult.getTotalCount()==0) {
						temp.setBaseStationName(dataSheet.getCell(0, i)
								.getContents().trim());
					
				if (dataSheet.getCell(1, i).getContents() != null
						&& !"".equals(dataSheet.getCell(1, i).getContents())) {
					if (this.getMainBean().find(dataSheet.getCell(0, i).getContents())==null){
						String carform="";
						String cform=dataSheet.getCell(1, i).getContents().trim();
						if(stationFormMap.get(cform)!=null){
							temp.setStationForm(stationFormMap.get(cform).toString());
						}
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(2));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(2));
					continue;
				}
				if (dataSheet.getCell(2, i).getContents() != null
						&& !"".equals(dataSheet.getCell(2, i).getContents())) {
					if (this.getMainBean().find(dataSheet.getCell(1, i).getContents())==null) {
						String carform="";
						String cform=dataSheet.getCell(2, i).getContents().trim();
						if(maintenanceLevelMap.get(cform)!=null){
							temp.setMaintenanceLevel(maintenanceLevelMap.get(cform).toString());
						}
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(3));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(3));
					continue;
				}
				if (dataSheet.getCell(3, i).getContents() != null
						&& !"".equals(dataSheet.getCell(3, i).getContents())) {
					String tstationHouseType=StaticMethod.nullObject2String(dataSheet.getCell(3, i).getContents().toString());
					if(stationHouseTypeMap.get(tstationHouseType)!=null){
						temp.setStationHouseType(stationHouseTypeMap.get(tstationHouseType).toString());
					}
					// temp.setStart_time(StaticMethod.String2Cal(dataSheet.getCell(3,
					// i).getContents().trim()).getGregorianChange());
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(4));
					continue;
				}
				if (dataSheet.getCell(4, i).getContents() != null
						&& !"".equals(dataSheet.getCell(4, i).getContents())) {
					
					temp.setManufacturer((dataSheet.getCell(4, i).getContents().toString()));
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(5));
					continue;
				}
				if (dataSheet.getCell(5, i).getContents() != null
						&& !"".equals(dataSheet.getCell(5, i).getContents())) {
					temp.setCarrierFacility(dataSheet.getCell(5, i).getContents().toString());
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(6));
					continue;
				}
				if (dataSheet.getCell(6, i).getContents() != null
						&& !"".equals(dataSheet.getCell(6, i).getContents())) {
					int num=Integer.parseInt(dataSheet.getCell(6, i).getContents());
					temp.setCarrierNum(num+"");
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(7));
					continue;
				}
				if (dataSheet.getCell(7, i).getContents() != null
						&& !"".equals(dataSheet.getCell(7, i).getContents())) {
					temp.setRemark(dataSheet.getCell(7, i).getContents());
					 
				}else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(8));
					continue;
				}
				
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(1));
						continue;
					}
				} else {
					break;
					// numberList.add(new Integer(i+1));
					// numberList.add(new Integer(1));
					// continue;
				}
				
				
				TawSystemSessionForm sessionForm = this.getUser(request);
				String addMan = sessionForm.getUserid();
				String addDept1=sessionForm.getDeptid();
				String addTime = CommonUtils.toEomsStandardDate(new Date());
				String connectType = sessionForm.getContactMobile();
				temp.setAddman(addMan);
				temp.setAddtime(addTime.toString());
				formList.add(temp);
			}
			for (int i = 0; i < formList.size(); i++) {
				getMainBean().save((BaseStation)formList.get(i));
			}
			String problemRow = "";
			for (int i = 0; i < numberList.size(); i++) {
				problemRow += "," + numberList.get(i);
			}
			String str = "";
			PrintWriter writer = null;
			if (!"".equals(problemRow)) {
				String sub = problemRow.substring(1, problemRow.length());
				String[] array = sub.split(",");
				str = "未成功录入！以下为不合法的未录入的信息：" + "<br>";
				for (int i = 0; i < array.length; i++) {
					str = str + "第" + array[i] + "行" + "第" + array[i + 1]
							+ "列;" + "<br>";
					i++;
				}
				writer = response.getWriter();
				writer.write(new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "false")
						.put("problemRow", str)
						.build()));
			} else {
				str = "成功录入所有信息";
				writer = response.getWriter();
				writer.write(new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true")
						.put("problemRow", str)
						.build()));
			}
			//request.setAttribute("problemRow", str);
			//response.getWriter().print("{problemRow:" + str + "}");
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
//		ActionForward actionForward = new ActionForward();
//		actionForward.setPath("/baseStation.do?method=add");
//		actionForward.setRedirect(true);
		return null;
		// return search(mapping, accessoryForm, request, response);

	}

	public ActionForward outPutModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (saveSessionBeanForm == null) {
			return mapping.findForward("timeout");
		}
		try {
			String sysTemPath = request.getRealPath("/");
			String url = sysTemPath + "/partner/model/basestation.xls";
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
	
	
}
