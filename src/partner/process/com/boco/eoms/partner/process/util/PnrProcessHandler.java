package com.boco.eoms.partner.process.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.accessories.model.TawCommonsAccessories;
import com.boco.eoms.commons.accessories.service.ITawCommonsAccessoriesManager;
import com.boco.eoms.commons.accessories.util.AccessoriesMgrLocator;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.partner.process.model.PartnerProcessMain;
import com.boco.eoms.partner.process.service.PartnerProcessLinkService;
import com.boco.eoms.partner.process.service.PartnerProcessMainService;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class PnrProcessHandler {
	/**
	 * 校验文件
	 * @param file
	 * @param ppMain
	 * @return
	 * @throws Exception 
	 */
	public ImportResult validateXLSFile(PartnerProcessMain ppMain) throws Exception {
		PnrProcessService process = (PnrProcessService)ApplicationContextHolder.getInstance().getBean(getProcessService(ppMain.getReferenceModel())) ;
		ImportResult result = new ImportResult();
		int count = 0;
		int point = 0;
	    try {
			File file=(File)getFileDetail(ppMain).get(2);
			POIFSFileSystem poiFileSystem = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook workbook = new HSSFWorkbook(poiFileSystem);
			HSSFSheet sheet = workbook.getSheetAt(0);
			XLSModel xlsModel=process.getXLSModel();
			int startRowNum=0;
			String type=ppMain.getChangeType();
			if (type.equals("123050101")) {
				startRowNum=xlsModel.getAddStartRowNum();
			}else if (type.equals("123050102")) {
				startRowNum=xlsModel.getUpdateStartRowNum();
			}else if (type.equals("123050103")) {
				startRowNum=xlsModel.getDeleteStartRowNum();
			}
			process.doLoadStaticSource();
			for (int i = startRowNum; i <= sheet.getLastRowNum(); i++) { // 获取工作表中每行数据
				point = i;
				HSSFRow row = sheet.getRow(i);
				if(!this.blankRowCheck(row,ppMain,process)) {
					if(type.equals("123050101")) {//changeType为增加时
						if( process.doSaveXLSFileValidate(row)){
							count++;//验证返回true时，表示导出一条有效数据
						}
					}else if(type.equals("123050102")) {//changeType为更新
						if( process.doUpdateXLSFileValidate(row)){
							count++;//验证返回true时，表示导出一条有效数据
						}
					}else if(type.equals("123050103")) {//changeType为删除
						if( process.doDeleteXLSFileValidate(row)){
							count++;//验证返回true时，表示导出一条有效数据
						}
					}
				}else {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode("503");
			String msg = result.getRestultMsg();
			if (!msg.equals("")) {
				msg += ",";
			}
			result.setRestultMsg(e.getMessage());
			throw new RuntimeException("校验出错!\n" + result.getRestultMsg());
		}
		result.setRestultMsg("导入成功");
		result.setResultCode("200");
		result.setImportCount(count);
		return result;
	}

	
	
	/**
	 * 根据模块获取对应的beanId
	 * @param referenceModel
	 * @return
	 */
	public String getProcessService(String referenceModel) {
		ID2NameService service = (ID2NameService)ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		referenceModel=service.id2Name(referenceModel, "tawSystemDictTypeDao");
		//根据模块类型获取对应的Service
		String serviceBeanId="";
		if ("".equals(referenceModel.trim())) {
			return serviceBeanId;
		}
		//获取配置applicationContext-partner-process.xml信息
		PnrProcessServiceType pnrServiceType=(PnrProcessServiceType)ApplicationContextHolder.getInstance().getBean("processServiceType");
		 List<PnrProcessModelConfig> modelConfigList=pnrServiceType.getProcessServiceTypeList();
		for (PnrProcessModelConfig ppModelConfig : modelConfigList) {
			if (ppModelConfig.getModelName().equals(referenceModel)) {
				serviceBeanId=ppModelConfig.getModelServiceBeanId();
				break;
			}
		}
		return serviceBeanId;
	}
	/**
	 *  执行归档
	 * @param ppMain
	 * @return
	 */
	public boolean storeXLSFileData2DB(PartnerProcessMain ppMain,HttpServletRequest request) throws Exception {
		//获取入库处理的Service
		PnrProcessService process = (PnrProcessService)ApplicationContextHolder.getInstance().getBean(getProcessService(ppMain.getReferenceModel())) ;
		boolean flag=false;
		//获取附件
		File file=(File)getFileDetail(ppMain).get(2);
		POIFSFileSystem poiFileSystem = new POIFSFileSystem(new FileInputStream(file));
		HSSFWorkbook workbook = new HSSFWorkbook(poiFileSystem);
		HSSFSheet sheet = workbook.getSheetAt(0);
		//根据操作类型来调用对应的处理方法
		String type=ppMain.getChangeType();
		XLSModel xlsModel=process.getXLSModel();
		int startRowNum=0;
		if (type.equals("123050101")) {
			startRowNum=xlsModel.getAddStartRowNum();
		}else if (type.equals("123050102")) {
			startRowNum=xlsModel.getUpdateStartRowNum();
		}else if (type.equals("123050103")) {
			startRowNum=xlsModel.getDeleteStartRowNum();
		}
		process.doLoadStaticSource();
		for (int i =startRowNum; i <= sheet.getLastRowNum(); i++) { // 获取工作表中每行数据
			HSSFRow row = sheet.getRow(i);
			if (!this.blankRowCheck(row, ppMain,process)) {
				if(type.equals("123050101")) {//changeType为增加时
					flag = process.doSaveXLSFileData(row,request);
				}else if(type.equals("123050102")) {//changeType为更新
					System.out.println("执行更新操作");
					if(ppMain.getReferenceModel().equals("123050403")){
						System.out.println("执行更新油机操作");
						request.setAttribute("changeMan",ppMain.getCreateUser());
						request.setAttribute("changeTime",ppMain.getStartTime());
						request.setAttribute("changeMainId",ppMain.getId()); //预留关联变更表ID，暂时不用
					}
					flag = process.doUpdateXLSFileData(row,request);

				}else if(type.equals("123050103")) {//changeType为删除
					flag = process.doDeleteXLSFileData(row,request);
				}
			}else {
				break;
			}
		}
		return flag;
	}
	/**
	 *  终止申请后，还原数据
	 * @param ppMain
	 * @return
	 */
	public boolean restoreXLSFileData2DB(PartnerProcessMain ppMain,HttpServletRequest request) throws Exception {
		//获取入库处理的Service
		PnrProcessService process = (PnrProcessService)ApplicationContextHolder.getInstance().getBean(getProcessService(ppMain.getReferenceModel())) ;
		boolean flag=false;
		//获取附件
		File file=(File)getFileDetail(ppMain).get(2);
		POIFSFileSystem poiFileSystem = new POIFSFileSystem(new FileInputStream(file));
		HSSFWorkbook workbook = new HSSFWorkbook(poiFileSystem);
		HSSFSheet sheet = workbook.getSheetAt(0);
		//根据操作类型来调用对应的处理方法
		String type=ppMain.getChangeType();
		XLSModel xlsModel=process.getXLSModel();
		int startRowNum=0;
		if (type.equals("123050101")) {
			startRowNum=xlsModel.getAddStartRowNum();
		}else if (type.equals("123050102")) {
			startRowNum=xlsModel.getUpdateStartRowNum();
		}else if (type.equals("123050103")) {
			startRowNum=xlsModel.getDeleteStartRowNum();
		}
		for (int i =startRowNum; i <= sheet.getLastRowNum(); i++) { // 获取工作表中每行数据
			HSSFRow row = sheet.getRow(i);
			if (!this.blankRowCheck(row, ppMain,process)) {
				if(type.equals("123050101")) {//changeType为增加时
					flag = process.doRestoreSaveXLSFileData(row,request);//新增时数据还原；
				}else if(type.equals("123050102")) {//changeType为更新
					flag = process.doRestoreUpdateXLSFileData(row,request);//更新时数据还原；
				}else if(type.equals("123050103")) {//changeType为删除
					flag = process.doRestoreDeleteXLSFileData(row,request);//删除时数据还原；
				}
			}else {
				break;
			}
		}
		return flag;
	}
	/**
	 * 获取文件详情，获得文件名称、路径、文件
	 * @return：文件名称、路径、文件
	 */
	public List getFileDetail(PartnerProcessMain ppMain) throws Exception{
		List list=new ArrayList();
		String changeAttachment=ppMain.getChangeAttachment();
		ITawCommonsAccessoriesManager mgr = (ITawCommonsAccessoriesManager)ApplicationContextHolder.getInstance().
		getBean("ItawCommonsAccessoriesManager");
		List<TawCommonsAccessories> files;
		files = mgr.getAllFileById(changeAttachment);
		TawCommonsAccessories accessories = files.get(0);
		String fileName = accessories.getAccessoriesName();
		String rootFilePath = AccessoriesMgrLocator.getTawCommonsAccessoriesManagerCOS().getFilePath(accessories.getAppCode());
		String path = rootFilePath + fileName;
		File file=new File(path);
		list.add(fileName);
		list.add(path);
		list.add(file);
		return list;
	}
	/**
	 * 获取申请结果
	 * @param model
	 * @param userId
	 * @param currentState
	 * @param changeType
	 * @return 
	 */
	public List getApplyResult(String model, String userId, String currentState,String changeType) {
		PartnerProcessMainService ppMainService=(PartnerProcessMainService)ApplicationContextHolder.getInstance().getBean("partnerProcessMainService");
		Search searchMain=new Search();
		if (!"".equals(model)) {
			searchMain.addFilterILike("referenceModel", model);
		}else if (!"".equals(userId)) {
			searchMain.addFilterILike("createUser", userId);
		}else if(!"".equals(currentState)){
			searchMain.addFilterILike("currentState", currentState);
		}else if (!"".equals(changeType)) {
			searchMain.addFilterILike("changeType", changeType);
		} 
		searchMain.addFilterNotEqual("delete", "1");
		SearchResult<PartnerProcessMain> searchMainResult=ppMainService.searchAndCount(searchMain);
		List<PartnerProcessMain> listMain=searchMainResult.getResult();
		return listMain;
	}
	/**
	 * 获取状态为审核中的结果
	 * @param model
	 * @param userId
	 * @param currentState
	 * @param changeType
	 * @return
	 */
	public List getApplying(String model, String userId, String currentState,String changeType) {
		PartnerProcessMainService ppMainService=(PartnerProcessMainService)ApplicationContextHolder.getInstance().getBean("partnerProcessMainService");
		PartnerProcessLinkService ppLinkService=(PartnerProcessLinkService)ApplicationContextHolder.getInstance().getBean("partnerProcessLinkService");
		Search searchMain=new Search();
		if (!"".equals(model)) {
			searchMain.addFilterILike("referenceModel", model);
		}else if(!"".equals(currentState)){
			searchMain.addFilterILike("currentState", currentState);
		}else if (!"".equals(changeType)) {
			searchMain.addFilterILike("changeType", changeType);
		} 
		searchMain.addFilterNotEqual("delete", "1");
		SearchResult<PartnerProcessMain> searchMainResult=ppMainService.searchAndCount(searchMain);
		List<PartnerProcessMain> listMain=searchMainResult.getResult();
		for (PartnerProcessMain ppMain : listMain) {
			Search searchLink=new Search();
			searchLink.addFilterILike("referenceId", ppMain.getId());
			searchLink.addFilterILike("dataSender", userId);
		}
		return listMain;
	} 
	/**
	 * 获得模块的dictId
	 */
	public String getDictId(HttpServletRequest request){
		String dictId;
		dictId = request.getParameter("dictId");
		HttpSession session=request.getSession();
		if(dictId!=null&&!"".equals(dictId)){
			session.setAttribute("dictId", dictId);
		}
		else {
			dictId =(String)session.getAttribute("dictId");
		}
		return dictId;
	}
	/**
	 * 空行验证
	 * @param row
	 * @param ppMain
	 * @param process
	 * @return
	 */
	public boolean blankRowCheck(HSSFRow row,PartnerProcessMain ppMain,PnrProcessService process){
		XLSModel xlsModel=process.getXLSModel();
		int totalCol=0;
		int dataStartCol=0;
		String type=ppMain.getChangeType();
		if(type.equals("123050101")) {//changeType为增加时
			//当行号小于实际数据开始行时作为非空行不做验证；
			if (row.getRowNum()<xlsModel.getAddStartRowNum()) {
				return false;
			}
			totalCol=xlsModel.getAddTotalCol();
			dataStartCol=xlsModel.getAddStartCol();
		}
		else if(type.equals("123050102")) {//changeType为更新
			if (row.getRowNum()<xlsModel.getUpdateStartRowNum()) {
				return false;
			}
			totalCol=xlsModel.getUpdateTotalCol();
			dataStartCol=xlsModel.getUpdateStartCol();
		}
		else if(type.equals("123050103")) {//changeType为删除
			if (row.getRowNum()<xlsModel.getDeleteStartRowNum()) {
				return false;
			}
			totalCol=xlsModel.getDeleteTotalCol();
			dataStartCol=xlsModel.getDeleteStartCol();
		}
		HSSFCell cell;
		int j = 0;
		for(int i=0;i<totalCol;i++) {
			cell = row.getCell(i+dataStartCol);
			if(cell == null) {
				j++;
			}else if("".equals(cell.toString())) {
				j++;
			}
		}
		if(j >= totalCol) {
			return true;
		} else {
			return false;
		}
	}
	
}
