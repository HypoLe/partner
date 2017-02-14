package com.boco.eoms.deviceManagement.busi.protectline.service.impl;


import com.boco.eoms.deviceManagement.busi.protectline.dao.PublicizeArticleApprovalDao;
import com.boco.eoms.deviceManagement.busi.protectline.model.PublicizeArticleApproval;
import com.boco.eoms.deviceManagement.busi.protectline.service.PublicizeArticleApprovalService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

public class PublicizeArticleApprovalServiceImpl extends
		CommonGenericServiceImpl<PublicizeArticleApproval> implements
		PublicizeArticleApprovalService {

	public void setPublicizeArticleApprovalDao(
			PublicizeArticleApprovalDao publicizeArticleApprovalDao) {
		this.setCommonGenericDao(publicizeArticleApprovalDao);
	}
	
//	public ImportResult importFromFile(InputStream is, String fileName,Map<String,String> params) throws Exception {
//		ImportResult result = new ImportResult();
//		result.setRestultMsg("");
//		if (!fileName.endsWith(".xls")) { // 检查是否为Excel文件
//			result.setRestultMsg(result.getRestultMsg() + "导入文件非法");
//			result.setResultCode("500");
//			throw new Exception("导入错误:" + result.getRestultMsg());
//		}
//		HSSFWorkbook wb = null;
//		try {
//			wb = new HSSFWorkbook(new POIFSFileSystem(is));
//		} catch (Exception e) {
//			result.setResultCode("500");
//			result.setRestultMsg(result.getRestultMsg() + "导入文件非法");
//		}
//		if (wb == null) { // 检查能否获取工作薄
//			result.setRestultMsg("不能获取工作薄");
//			result.setResultCode("501");
//			throw new Exception("导入错误:" + result.getRestultMsg());
//		}
//		HSSFSheet sheet = wb.getSheetAt(0);
//		if (sheet == null) { // 检查能否获取工作表
//			result.setRestultMsg("不能获取工作表");
//			result.setResultCode("502");
//		}
//		int count = 0;
//		int point = 0;
//		try {
//			for (int i = 2; i <= sheet.getLastRowNum(); i++) { // 获取工作表中每行数据
//				point = i;
//				count++;
//				HSSFRow row = sheet.getRow(i);
//				BaseStationFaultRecord record;
//				if(!this.blankRowCheck(row)) {
//					record = fromRow2NewExamineInfor(row,params);
//					this.save(record);
//				} else {
//					break;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.setResultCode("503");
//			String msg = result.getRestultMsg();
//			if (!msg.equals("")) {
//				msg += ",";
//			}
//			result.setRestultMsg("导入错误:错误行数是:"+msg + (point + 1));
//			throw new Exception("导入错误:错误行数是:" + result.getRestultMsg());
//		}
//		result.setRestultMsg("导入成功");
//		result.setResultCode("200");
//		result.setImportCount(count);
//		return result;
//	}
//	
//	private boolean blankRowCheck(HSSFRow row) {
//		HSSFCell cell;
//		int j = 0;
//		for(int i=1;i<=20;i++) {
//			cell = row.getCell(i);
//			if(cell == null) {
//				j++;
//			}else if("".equals(cell.toString())) {
//				j++;
//			}
//		}
//		if(j >= 20) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	/*
//	 * 将每行数据构建为考核指标对象
//	 */
//	public advertisement fromRow2NewExamineInfor(HSSFRow row,Map<String,String> params) throws Exception {
//		SimpleDateFormat sdf = null;
//		HSSFCell cell = null;
//		BaseStationFaultRecord infor = new BaseStationFaultRecord();
//		
//		//日期
//		//sdf = new SimpleDateFormat("MM月dd日");
//		sdf = new SimpleDateFormat("yyyy-mm-dd");
//		cell = row.getCell(1);
//		infor.setBsfrDateTime(sdf.format(cell.getDateCellValue()));
//		//驻点	
//		infor.setStagnationPoint(row.getCell(2).getRichStringCellValue().getString());
//		//基站名称	
//		infor.setBaseStationName(row.getCell(3).getRichStringCellValue().getString());
//		//维护级别	(name2Id 11801)
//		String maintainLevel = StaticMethod.null2String(row.getCell(4).getRichStringCellValue().getString());
//		if(!"".equals(maintainLevel)) {
//			maintainLevel = this.name2Id(maintainLevel,"11801");
//		}
//		infor.setMaintainLevel(maintainLevel);
//		//工单类别	(name2Id 11802)
//		String sheetType = row.getCell(5).getRichStringCellValue().getString();
//		if(!"".equals(sheetType)) {
//			sheetType = this.name2Id(sheetType, "11802");
//		}
//		infor.setSheetType(sheetType);
//		//工单任务信息
//		infor.setSheetTaskInfor(row.getCell(6).getRichStringCellValue().getString());
//		//工单创建时间	
//		infor.setSheetStartTime(row.getCell(7).getRichStringCellValue().getString());
//		//工单确认时间
//		infor.setSheetConfirmTime(row.getCell(8).getRichStringCellValue().getString());
//		//	工单结束时间	
//		infor.setSheeEndTime(row.getCell(9).getRichStringCellValue().getString());
//		//历时	
//		sdf = new SimpleDateFormat("H:mm:ss");
//		cell = row.getCell(10);
//		infor.setTakeTime(sdf.format(cell.getDateCellValue()));
//		//工单接收人员	
//		infor.setSheetReceivePerson(row.getCell(11).getRichStringCellValue().getString());
//		//工单结束人员	
//		infor.setSheetEndPerson(row.getCell(12).getRichStringCellValue().getString());
//		//工单创建类别		(name2Id 11803)
//		String sheetStartType = row.getCell(13) == null?"":row.getCell(13).getRichStringCellValue().getString();
//		if(!"".equals(sheetStartType)) {
//			sheetStartType = this.name2Id(sheetStartType, "11803");
//		}
//		infor.setSheetStartType(sheetStartType);
//		//处理结果	
//		infor.setProcessingResults(row.getCell(14).getRichStringCellValue().getString());
//		//故障类别		(name2Id 11804)
//		String faultType = row.getCell(15) == null?"":row.getCell(15).getRichStringCellValue().getString();
//		if(!"".equals(faultType)) {
//			faultType = this.name2Id(faultType, "11804");
//		}
//		infor.setFaultType(faultType);
//		//是否退服		(name2Id 10301)
//		String isExit = row.getCell(16) == null?"":row.getCell(16).getRichStringCellValue().getString();
//		if(!"".equals(isExit)) {
//			isExit = this.name2Id(isExit, "10301");
//		}
//		infor.setIsExit(isExit);
//		//设备调整	
//		infor.setDeviceAdjust(row.getCell(17) == null?"":row.getCell(17).getRichStringCellValue().getString());
//		//遗留问题	
//		infor.setResidualProblem(row.getCell(18) == null?"":row.getCell(18).getRichStringCellValue().getString());
//		//其它
//		infor.setOther(row.getCell(19) == null?"":row.getCell(19).getRichStringCellValue().getString());
//		//工单编号
//		infor.setSheetId(row.getCell(20).getRichStringCellValue().getString());
//		
//		//其它
//		infor.setReportPerson(params.get("userId"));
//		infor.setDeptId(params.get("deptId"));
//		infor.setRoleId(params.get("roleId"));
//		infor.setContactNumber(params.get("phone"));
//		infor.setReportTime(CommonUtils.toEomsStandardDate(new Date()));
//		infor.setIsDeleted("0");
//		
//		//throw new Exception("记录参数错误");
//		
//		return infor;
//	}
//	
//	private String name2Id(String name,String initDictId) throws Exception{
//		Map<String,String> dictMap = this.getDictMap(initDictId);
//		String id = "";
//		String value = "";
//		for(String key : dictMap.keySet()) {
//			value = dictMap.get(key);
//			if(name.equals(value)) {
//				id = key;
//				break;
//			}
//		}
//		if("".equals(id)) {
//			throw new RuntimeException("導入數據與數據字典不匹配！");
//		}
//		return id;
//	}
//	
//	@SuppressWarnings("unchecked")
//	private Map<String,String> getDictMap(String initDictId) throws Exception{
//		if("".equals(initDictId) || initDictId == null) {
//			return null;
//		}
//		
//		// 取字典数据
//		ITawSystemDictTypeManager dictManager = (ITawSystemDictTypeManager) ApplicationContextHolder
//				.getInstance().getBean("ItawSystemDictTypeManager");
//		List list = dictManager.getDictSonsByDictid(initDictId);
//
//		Map<String,String> dictMap = new HashMap<String,String>();
//		if(list.size()>0){
//			String itemName = null;
//			String itemId = null;
//			// 将list中的值元素插入Map
//			for (Iterator it = list.iterator(); it.hasNext();) {
//				TawSystemDictType item = (TawSystemDictType) it.next();
//				itemName = StaticMethod.null2String(item.getDictName());
//				itemId = StaticMethod.null2String(item.getDictId());
//				
//				dictMap.put(itemId, itemName);
//			}
//		} else {
//			return null;
//		}
//		return dictMap;
//	}
}
