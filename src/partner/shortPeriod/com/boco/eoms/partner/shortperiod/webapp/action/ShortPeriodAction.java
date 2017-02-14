package com.boco.eoms.partner.shortperiod.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.accessories.util.AccessoriesMgrLocator;
import com.boco.eoms.commons.accessories.util.AccessoriesUtil;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.shortperiod.model.BackTower;
import com.boco.eoms.partner.shortperiod.po.TowerModel;
import com.boco.eoms.partner.shortperiod.po.TowerQueryConditionModel;
import com.boco.eoms.partner.shortperiod.service.ITowerInspectService;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;

public class ShortPeriodAction extends SheetAction {
	/**
	 *   接受查询参数  
	 	 * @author WANGJUN
	 	 * @title: getConditionModel
	 	 * @date Nov 7, 2016 5:06:27 PM
	 	 * @param request
	 	 * @return TowerQueryConditionModel
	 */
	private TowerQueryConditionModel getConditionModel(HttpServletRequest request){
		String flag = StaticMethod.nullObject2String(request.getParameter("flag"));// 查询标识
		String region = StaticMethod.nullObject2String(request.getParameter("region"));// 地市
		String country = StaticMethod.nullObject2String(request.getParameter("country")); //区县
		String resName = StaticMethod.nullObject2String(request.getParameter("resName")); //站点名称
		String confirmNum = StaticMethod.nullObject2String(request.getParameter("confirmNum")); //产品业务确认编号
		String isModify = StaticMethod.nullObject2String(request.getParameter("isModify")); //是否修改过
		String dataFilter = StaticMethod.nullObject2String(request.getParameter("dataFilter")); //数据筛选条件
		String newProductType = StaticMethod.nullObject2String(request.getParameter("newProductType")); //产品类型（新）
		String newRoomType = StaticMethod.nullObject2String(request.getParameter("newRoomType")); //机房类型（新）
		String newAntennaHeight = StaticMethod.nullObject2String(request.getParameter("newAntennaHeight")); //天线挂高（新）
		String oldProductType = StaticMethod.nullObject2String(request.getParameter("oldProductType")); //产品类型（旧）
		String oldRoomType = StaticMethod.nullObject2String(request.getParameter("oldRoomType")); //机房类型（旧）
		String oldAntennaHeight = StaticMethod.nullObject2String(request.getParameter("oldAntennaHeight")); //天线挂高（旧）
		
		TowerQueryConditionModel towerQueryConditionModel = new TowerQueryConditionModel();
		towerQueryConditionModel.setFlag(flag);
		towerQueryConditionModel.setRegion(region);
		towerQueryConditionModel.setCountry(country);
		towerQueryConditionModel.setResName(resName);
		towerQueryConditionModel.setConfirmNum(confirmNum);
		towerQueryConditionModel.setIsModify(isModify);
		towerQueryConditionModel.setDataFilter(dataFilter);
		towerQueryConditionModel.setNewProductType(newProductType);
		towerQueryConditionModel.setNewRoomType(newRoomType);
		towerQueryConditionModel.setNewAntennaHeight(newAntennaHeight);
		towerQueryConditionModel.setOldProductType(oldProductType);
		towerQueryConditionModel.setOldRoomType(oldRoomType);
		towerQueryConditionModel.setOldAntennaHeight(oldAntennaHeight);
		return towerQueryConditionModel;
	}
	
	/**
	 * 	 拼接待回复列表查询条件
	 	 * @author WANGJUN
	 	 * @title: jointListBacklogCondition
	 	 * @date Oct 26, 2016 4:50:41 PM
	 	 * @param region
	 	 * @param country
	 	 * @param resName
	 	 * @param confirmNum
	 	 * @param isModify
	 	 * @return String
	 */
	private String jointListBacklogCondition(TowerQueryConditionModel towerQueryConditionModel){
		String condition="";
		//查询标识
		condition+="&flag="+towerQueryConditionModel.getFlag();
		//地市
		condition+="&region="+towerQueryConditionModel.getRegion();
		//区县
		condition+="&country="+towerQueryConditionModel.getCountry();
		//站点名称
		condition+="&resName="+towerQueryConditionModel.getResName();
		//产品业务确认单编号
		condition+="&confirmNum="+towerQueryConditionModel.getConfirmNum();
		//是否已修改
		condition+="&isModify="+towerQueryConditionModel.getIsModify();
		//数据筛选条件
		condition+="&dataFilter="+towerQueryConditionModel.getDataFilter();
		
		//产品类型（新）
		condition+="&newProductType="+towerQueryConditionModel.getNewProductType();
		//机房类型（新）
		condition+="&newRoomType="+towerQueryConditionModel.getNewRoomType();
		//天线挂高（新）
		condition+="&newAntennaHeight="+towerQueryConditionModel.getNewAntennaHeight();
		//产品类型（旧）
		condition+="&oldProductType="+towerQueryConditionModel.getOldProductType();
		//天线挂高（新）
		condition+="&oldRoomType="+towerQueryConditionModel.getOldRoomType();
		//天线挂高（旧）
		condition+="&oldAntennaHeight="+towerQueryConditionModel.getOldAntennaHeight();
		
		System.out.println("-------------拼接列表查询条件字符串="+condition);
		
		return condition;
	}
	
	/**
	 *   将查询条件中的产品类型（旧）、机房类型（旧）、天线挂高（旧）的枚举值转换成中文
	 	 * @author WANGJUN
	 	 * @title: oldID2Name
	 	 * @date Nov 8, 2016 10:44:59 AM
	 	 * @param towerQueryConditionModel void
	 */
	private void oldID2Name(TowerQueryConditionModel towerQueryConditionModel){
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		if(towerQueryConditionModel.getOldProductType() != null && !"".equals(towerQueryConditionModel.getOldProductType())){
			towerQueryConditionModel.setOldProductTypeName(service.id2Name(towerQueryConditionModel.getOldProductType(),"ItawSystemDictTypeDao"));
		}
		 
		if(towerQueryConditionModel.getOldRoomType() != null && !"".equals(towerQueryConditionModel.getOldRoomType())){
			towerQueryConditionModel.setOldRoomTypeName(service.id2Name(towerQueryConditionModel.getOldRoomType(),"ItawSystemDictTypeDao"));
		}
		
		if(towerQueryConditionModel.getOldAntennaHeight()!= null && !"".equals(towerQueryConditionModel.getOldAntennaHeight())){
			towerQueryConditionModel.setOldAntennaHeightName(service.id2Name(towerQueryConditionModel.getOldAntennaHeight(),"ItawSystemDictTypeDao"));
		}
	}
	
	/**
	 * 根据类型，显示未处理列表
	 */
	public ActionForward listBacklog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		String flag = StaticMethod.nullObject2String(request.getParameter("flag"));// 查询标识
		TowerQueryConditionModel towerQueryConditionModel = this.getConditionModel(request);
		
		//针对查询条件中的地市、区县
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);

		ITowerInspectService towerInspectService = (ITowerInspectService) getBean("towerInspectService");
		this.oldID2Name(towerQueryConditionModel);
		
		// 工单管理-传输局工单-抢修工单-待回复工单 集合条数
		int total = 0;
		// 工单管理-传输局工单-抢修工单-待回复工单 集合
		List<TowerModel> rPnrTransferList = null;
		if("1".equals(flag)){
			try {
				total = towerInspectService.getTowerCount(userId,towerQueryConditionModel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				rPnrTransferList = towerInspectService.getTowerList(userId,towerQueryConditionModel,firstResult,endResult, pageSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("region", towerQueryConditionModel.getRegion());
		request.setAttribute("country",towerQueryConditionModel.getCountry());
		request.setAttribute("resName",towerQueryConditionModel.getResName());
		request.setAttribute("confirmNum",towerQueryConditionModel.getConfirmNum());
		request.setAttribute("isModify", towerQueryConditionModel.getIsModify());
		request.setAttribute("dataFilter",towerQueryConditionModel.getDataFilter());
		request.setAttribute("flag", flag);
		request.setAttribute("newProductType",towerQueryConditionModel.getNewProductType());
		request.setAttribute("newRoomType",towerQueryConditionModel.getNewRoomType());
		request.setAttribute("newAntennaHeight",towerQueryConditionModel.getNewAntennaHeight());
		request.setAttribute("oldProductType",towerQueryConditionModel.getOldProductType());
		request.setAttribute("oldRoomType",towerQueryConditionModel.getOldRoomType());
		request.setAttribute("oldAntennaHeight",towerQueryConditionModel.getOldAntennaHeight());
		request.setAttribute("condition", this.jointListBacklogCondition(towerQueryConditionModel));
		return mapping.findForward("backlogList");
	}
	
	//获取request传递的查询条件封装到ConditionQueryModel中
	private String doRequestToCondition(HttpServletRequest request){
		TowerQueryConditionModel towerQueryConditionModel = this.getConditionModel(request);
		String condition = this.jointListBacklogCondition(towerQueryConditionModel);
		return condition;
	}
	
	/**
	 * 	 铁塔更新界面
	 	 * @author Wangjun
	 	 * @title: showTowerUpdatePage
	 	 * @date Oct 25, 2016 4:26:11 PM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception ActionForward
	 */
	public ActionForward showTowerUpdatePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String productNum = StaticMethod.nullObject2String(request.getParameter("productNum"));//产品业务确认单编号
		TowerQueryConditionModel towerQueryConditionModel = new TowerQueryConditionModel();
		towerQueryConditionModel.setConfirmNum(productNum);
		ITowerInspectService towerInspectService = (ITowerInspectService) getBean("towerInspectService");
		TowerModel towerModel = null;
		List<TowerModel> list  = towerInspectService.getTowerList("",towerQueryConditionModel,0,1, 1);
		if(list != null && list.size() > 0){
			towerModel = list.get(0);
		}
		request.setAttribute("towerModel", towerModel);
		request.setAttribute("condition", this.doRequestToCondition(request));
		return mapping.findForward("towerUpdatePage");
	}
	
	/**
	 * 
	 	 * @author Wangjun
	 	 * @title: updateTower
	 	 * @date Oct 26, 2016 10:06:54 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception ActionForward
	 */
	public ActionForward updateTower(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String returnPage = "failure";
		String productNum = StaticMethod.nullObject2String(request.getParameter("productNum"));//产品业务确认单编号
		String newa2 = StaticMethod.nullObject2String(request.getParameter("newa2"));//产品类型
		String newa3 = StaticMethod.nullObject2String(request.getParameter("newa3"));//机房类型
		String newa4 = StaticMethod.nullObject2String(request.getParameter("newa4"));//产品单元数1
		String newa5 = StaticMethod.nullObject2String(request.getParameter("newa5"));//对应实际最高天线挂高1
		String newa6 = StaticMethod.nullObject2String(request.getParameter("newa6"));//BBU是否放在铁塔公司机房1
		String newa8 = StaticMethod.nullObject2String(request.getParameter("newa8"));//产品单元数2
		String newa9 = StaticMethod.nullObject2String(request.getParameter("newa9"));//实际最高天线挂高2
		String newa10 = StaticMethod.nullObject2String(request.getParameter("newa10"));//BBU是否放在铁塔公司机房2
		String newa12 = StaticMethod.nullObject2String(request.getParameter("newa12"));//产品单元数3
		String newa13 = StaticMethod.nullObject2String(request.getParameter("newa13"));//实际最高天线挂高3
		String newa14 = StaticMethod.nullObject2String(request.getParameter("newa14"));//BBU是否放在铁塔公司机房3
		String newa16 = StaticMethod.nullObject2String(request.getParameter("newa16"));//期末铁塔共享用户数
		String newa21 = StaticMethod.nullObject2String(request.getParameter("newa21"));//期末机房共享用户数
		String newa26 = StaticMethod.nullObject2String(request.getParameter("newa26"));//配套共享用户数
		String newa36 = StaticMethod.nullObject2String(request.getParameter("newa36"));//场地费共享用户数
		String newa41 = StaticMethod.nullObject2String(request.getParameter("newa41"));//电力引入费共享用户数
		String newa31 = StaticMethod.nullObject2String(request.getParameter("newa31"));//维护费共享用户数
		
		String newa17 = StaticMethod.nullObject2String(request.getParameter("newa17"));//铁塔共享运营商1的起租日期(核查)
		String newa18 = StaticMethod.nullObject2String(request.getParameter("newa18"));//铁塔共享运营商1起租后的共享折扣(核查)
		String newa19 = StaticMethod.nullObject2String(request.getParameter("newa19"));//铁塔共享运营商2的起租日期(核查)
		String newa20 = StaticMethod.nullObject2String(request.getParameter("newa20"));//铁塔共享运营商2起租后的共享折扣(核查)
		String newa22 = StaticMethod.nullObject2String(request.getParameter("newa22"));//机房共享运营商1的起租日期(核查)
		String newa23 = StaticMethod.nullObject2String(request.getParameter("newa23"));//机房共享运营商1起租后的共享折扣(核查)
		String newa24 = StaticMethod.nullObject2String(request.getParameter("newa24"));//机房共享运营商2的起租日期(核查)
		String newa25 = StaticMethod.nullObject2String(request.getParameter("newa25"));//机房共享运营商2起租后的共享折扣(核查)
		String newa27 = StaticMethod.nullObject2String(request.getParameter("newa27"));//配套共享运营商1的起租日期(核查)
		String newa28 = StaticMethod.nullObject2String(request.getParameter("newa28"));//配套共享运营商1起租后的共享折扣(核查)
		String newa29 = StaticMethod.nullObject2String(request.getParameter("newa29"));//配套共享运营商2的起租日期(核查)
		String newa30 = StaticMethod.nullObject2String(request.getParameter("newa30"));//配套共享运营商2起租后的共享折扣(核查)
		String newa32 = StaticMethod.nullObject2String(request.getParameter("newa32"));//维护费共享运营商1的起租日期(核查)
		String newa33 = StaticMethod.nullObject2String(request.getParameter("newa33"));//维护费共享运营商1起租后的共享折扣(核查)
		String newa34 = StaticMethod.nullObject2String(request.getParameter("newa34"));//维护费共享运营商2的起租日期(核查)
		String newa35 = StaticMethod.nullObject2String(request.getParameter("newa35"));//维护费共享运营商2起租后的共享折扣(核查)
		String newa48 = StaticMethod.nullObject2String(request.getParameter("newa48"));//场地费(核查)
		String newa49 = StaticMethod.nullObject2String(request.getParameter("newa49"));//电力引入费(核查)
		String newa37 = StaticMethod.nullObject2String(request.getParameter("newa37"));//场地费共享运营商1的起租日期(核查)
		String newa38 = StaticMethod.nullObject2String(request.getParameter("newa38"));//场地费共享运营商1起租后的共享折扣(核查)
		String newa39 = StaticMethod.nullObject2String(request.getParameter("newa39"));//场地费共享运营商2的起租日期(核查)
		String newa40 = StaticMethod.nullObject2String(request.getParameter("newa40"));//场地费共享运营商2起租后的共享折扣(核查)
		String newa42 = StaticMethod.nullObject2String(request.getParameter("newa42"));//电力引入费共享运营商1的起租日期(核查)
		String newa43 = StaticMethod.nullObject2String(request.getParameter("newa43"));//电力引入费共享运营商1起租后的共享折扣(核查)
		String newa44 = StaticMethod.nullObject2String(request.getParameter("newa44"));//电力引入费共享运营商2的起租日期(核查)
		String newa45 = StaticMethod.nullObject2String(request.getParameter("newa45"));//电力引入费共享运营商2起租后的共享折扣(核查)
		
		String towerRemark = StaticMethod.nullObject2String(request.getParameter("towerRemark"));//备注
		String towerDescribe = StaticMethod.nullObject2String(request.getParameter("towerDescribe"));//描述

		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		ITowerInspectService towerInspectService = (ITowerInspectService) getBean("towerInspectService");
		Search search = new Search();
		search.addFilterEqual("towerId", productNum);
		List<BackTower> backTowerList = towerInspectService.search(search);
		if (backTowerList != null) {
			BackTower backTower = backTowerList.get(0);
			if(backTower != null){
				backTower.setA2(newa2);
				backTower.setA3(newa3);
				backTower.setA4(newa4);
				backTower.setA5(newa5);
				backTower.setA6(newa6);
				backTower.setA8(newa8);
				backTower.setA9(newa9);
				backTower.setA10(newa10);
				backTower.setA12(newa12);
				backTower.setA13(newa13);
				backTower.setA14(newa14);
				backTower.setA16(newa16);
				backTower.setA21(newa21);
				backTower.setA26(newa26);
				backTower.setA36(newa36);
				backTower.setA41(newa41);
				backTower.setA31(newa31);
				
				backTower.setA17(newa17);
				backTower.setA18(newa18);
				backTower.setA19(newa19);
				backTower.setA20(newa20);
				backTower.setA22(newa22);
				backTower.setA23(newa23);
				backTower.setA24(newa24);
				backTower.setA25(newa25);
				backTower.setA27(newa27);
				backTower.setA28(newa28);
				backTower.setA29(newa29);
				backTower.setA30(newa30);
				backTower.setA32(newa32);
				backTower.setA33(newa33);
				backTower.setA34(newa34);
				backTower.setA35(newa35);
				backTower.setA48(newa48);
				backTower.setA49(newa49);
				backTower.setA37(newa37);
				backTower.setA38(newa38);
				backTower.setA39(newa39);
				backTower.setA40(newa40);
				backTower.setA42(newa42);
				backTower.setA43(newa43);
				backTower.setA44(newa44);
				backTower.setA45(newa45);
				backTower.setTowerRemark(towerRemark);
				backTower.setTowerDescribe(towerDescribe);
				backTower.setLastModifyUserid(userId);
				backTower.setLastModifyTime(new Date());
				towerInspectService.save(backTower);
				returnPage = "success";
				request.setAttribute("condition", request.getParameter("condition"));
			}else{
				request.setAttribute("msg", "未找到，产品业务确认单编号:"+productNum+"的铁塔核查信息！");
			}
	}else{
		request.setAttribute("msg", "不存在，产品业务确认单编号:"+productNum+"的铁塔核查信息！");
	}
		return mapping.findForward(returnPage);
	}
	
	//导出一次核验、二次抽检、周期报表详情
	public ActionForward exportTowerList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter writer=null;
		//获取登录人userID
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		TowerQueryConditionModel towerQueryConditionModel = this.getConditionModel(request);
		this.oldID2Name(towerQueryConditionModel);
		ITowerInspectService towerInspectService = (ITowerInspectService) getBean("towerInspectService");
		HSSFWorkbook wb = towerInspectService.exportTowerList(userId,towerQueryConditionModel);
	
		/*写入临时文件-----------------------------------------------------------------*/	
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
     		String nowDate=df.format(new Date());// new Date()为获取当前系统时间
     		//String path=System.getProperty("catalina.home")+"/webapps"+request.getContextPath()+"/sceneExcelAccessory/temporary/";
     		String path = uploadPath();
     		AccessoriesUtil.createFile(path, "/");
     		File file = new File(path);
			if (!file.exists()) {
				file.mkdir();
			}
     		String fileName="tietaxunjian"+nowDate+".xls";
     		String filePath = path+fileName; //文件的完整路径
     		System.out.println("---------------文件的完整路径："+filePath);
     		FileOutputStream fout = null;
         try {   
        	 fout = new FileOutputStream(filePath);  
             wb.write(fout); 
             
	        writer=response.getWriter();
	        writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("fileName", fileName)
							.build()));
    	 } catch (IOException e) {  
            e.printStackTrace();  
            writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false")
							.put("msg", "failure")
							.build()));  
    	 } finally {
    		 if(fout != null){
    			 fout.close();
    		 }
    		 
    		 if(writer != null){
 				writer.close();
			}
    	 }
         
		return null;
	}
	
	public ActionForward downloadTowerFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		 String fileName = StaticMethod.nullObject2String(request.getParameter("fileName"));//文件名
		 OutputStream outputStream = null;
		 InputStream inputStream = null;
		 try {   
			 	//String path=System.getProperty("catalina.home")+"/webapps"+request.getContextPath()+"/sceneExcelAccessory/temporary/";
			 	String path = uploadPath();
	            String filepath=path+fileName;//要下载的文件完整路径
			    response.setContentType("text/plain");
			    response.setHeader("Location",fileName);
			    response.setHeader("Content-Disposition", "attachment; filename=" +new String(fileName.getBytes("gb2312"),"iso8859-1")); 
			    response.setCharacterEncoding("utf-8"); 
			    outputStream = response.getOutputStream();
			    inputStream = new FileInputStream(filepath);
			    byte[] buffer = new byte[1024];
			    int i = -1;
			    while ((i = inputStream.read(buffer)) != -1) {
			    	outputStream.write(buffer, 0, i);
			    }
		        outputStream.flush();
 	 } catch (IOException e) {  
	            e.printStackTrace();  
	            System.out.println("Output   is   closed ");  
 	 } finally {
 		 if(outputStream != null){
 			outputStream.close();
 		 }
 		 if(inputStream != null){
 			inputStream.close();
 		 }
 	 }
 	 return null;
	}
	
	private String uploadPath(){
		String fileName1 = StaticMethod.getCurrentDateTime("yyyyMMddHHmmss")+StaticMethod.getRandomCharAndNumr(4);
		String tPath = fileName1.substring(0, 6);
		String rootFilePath = AccessoriesMgrLocator.getAccessoriesAttributes().getUploadPath();
		String filePath = rootFilePath +"/tietaxunjian/"+tPath+"/";
		return filePath;
	}
}
