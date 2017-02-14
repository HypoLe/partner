package com.boco.eoms.deviceManagement.charge.service;


import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.charge.dao.FeeApplicationDao;
import com.boco.eoms.deviceManagement.charge.model.FeeAppliImportResult;
import com.boco.eoms.deviceManagement.charge.model.FeeApplicationMain;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.deviceManagement.qualify.model.TaskOrder;


public class FeeApplicationServiceImpl extends
              CommonGenericServiceImpl<FeeApplicationMain> implements
              FeeApplicationService {

	private FeeApplicationDao feeApplicationDao;
	
	public void setFeeApplicationDao(FeeApplicationDao feeApplicationDao) {
		this.setCommonGenericDao(feeApplicationDao);
//		this.feeApplicationDao=feeApplicationDao;
	}
	
	
	public String bigRole;
	
	public String parterRole;
	
	public String getBigRole() {
		return bigRole;
	}

	public void setBigRole(String bigRole) {
		this.bigRole = bigRole;
	}

	public String getParterRole() {
		return parterRole;
	}

	public void setParterRole(String parterRole) {
		this.parterRole = parterRole;
	}
	
	

	public String importRecord(InputStream is, String fileName,Map<String,String> params) throws Exception {
		
		
//		FeeApplicationService feeApplicationService=(FeeApplicationService)getBean("feeApplicationService");
		
		int count = 0;
		if (!fileName.endsWith(".xls")) { // 检查是否为Excel文件
			throw new Exception(":导入模板文件非法");
		}
		HSSFWorkbook wb = null;
		wb = new HSSFWorkbook(new POIFSFileSystem(is));
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow titleRow = sheet.getRow(0);
		if(!"费用类型".equals(titleRow.getCell(0).getRichStringCellValue().getString())
				||!"费用金额".equals(titleRow.getCell(1).getRichStringCellValue().getString())
				||!"费用描述".equals(titleRow.getCell(2).getRichStringCellValue().getString())
				||!"备注".equals(titleRow.getCell(3).getRichStringCellValue().getString())){
			throw new Exception(":导入模板格式错误");
		}
	
		List<FeeApplicationMain> list=new ArrayList<FeeApplicationMain>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {	
		FeeApplicationMain feeApplicationMain=new FeeApplicationMain();
		HSSFRow row = sheet.getRow(i);	
		if(row.getCell(0) == null || "".equals(row.getCell(0).getRichStringCellValue().getString())) break;
		feeApplicationMain.setFeeApplicationType(row.getCell(0).getRichStringCellValue().getString());
		feeApplicationMain.setFeeApplicationMoney(String.valueOf(row.getCell(1).getNumericCellValue()));
		feeApplicationMain.setFeeApplicationDiscribe(row.getCell(2).getRichStringCellValue().getString());
		feeApplicationMain.setFeeApplicationRemark(row.getCell(3).getRichStringCellValue().getString());
		
		feeApplicationMain.setFeeApplicationUser(params.get("feeApplicationUser"));
		feeApplicationMain.setFeeApplicationDept(params.get("feeApplicationDept"));
		feeApplicationMain.setFeeApplicationCall(params.get("feeApplicationCall"));
		feeApplicationMain.setFeeApplicationCompanyName(params.get("feeApplicationCompanyName"));
		feeApplicationMain.setFeeApplicationRoleID(params.get("feeApplicationRoleID"));
		feeApplicationMain.setFeeApplicationGreatTime(CommonUtils.toEomsStandardDate(new Date()));
		feeApplicationMain.setDeleted("0");
		feeApplicationMain.setFeeApplicationStatus("1");
	
		list.add(feeApplicationMain);
		}
		
		
		for(FeeApplicationMain feeApplicationMain : list) {
			
			feeApplicationDao.save(feeApplicationMain);
			count++;
		}
	
		return "共导入"+count+"条";
	}
	
	
}
