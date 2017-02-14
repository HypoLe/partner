package com.boco.eoms.deviceManagement.baseInfo.service;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.context.ApplicationContext;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.deviceManagement.baseInfo.dao.CheckPointDao;
import com.boco.eoms.deviceManagement.baseInfo.model.CPBaseStation;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckPoint;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckPointType;
import com.boco.eoms.deviceManagement.baseInfo.model.HandWell;
import com.boco.eoms.deviceManagement.baseInfo.model.LightJoinBox;
import com.boco.eoms.deviceManagement.baseInfo.model.Pole;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.faultInfo.model.ImportResult;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.googlecode.genericdao.search.Search;

public class CheckPointServiceImpl extends CommonGenericServiceImpl<CheckPoint>
		implements CheckPointService {

	public void setCheckPointDao(CheckPointDao checkPointDao) {
		this.setCommonGenericDao(checkPointDao);
	}

	public void save(CheckPoint cp, CPBaseStation bs, String type, String oldType) {
		this.save(cp);
		CommonGenericDaoImpl<CPBaseStation, String> bsDao = getGenericDao(bs);
		bs.setCheckPointId(cp.getId());
		this.deleteCheckPointType(type, oldType, cp.getId());
//		bsDao.save(bs);
	}

	public void save(CheckPoint cp, HandWell hw, String type, String oldType) {
		this.save(cp);
		CommonGenericDaoImpl<HandWell, String> bsDao = getGenericDao(hw);
		hw.setCheckPointId(cp.getId());
		this.deleteCheckPointType(type, oldType, cp.getId());
		bsDao.save(hw);
	}

	public void save(CheckPoint cp, LightJoinBox ljb, String type,
			String oldType) {
		this.save(cp);
		CommonGenericDaoImpl<LightJoinBox, String> bsDao = getGenericDao(ljb);
		ljb.setCheckPointId(cp.getId());
		this.deleteCheckPointType(type, oldType, cp.getId());
		bsDao.save(ljb);
	}

	public void save(CheckPoint cp, Pole pole, String type, String oldType) {
		this.save(cp);
		CommonGenericDaoImpl<Pole, String> bsDao = getGenericDao(pole);
		pole.setCheckPointId(cp.getId());
		this.deleteCheckPointType(type, oldType, cp.getId());
		bsDao.save(pole);
	}

	private void deleteCheckPointType(String type, String oldType,
			String checkPointId) {
		type = this.convertCPType(type);
		oldType = this.convertCPType(oldType);
		if (!type.equals(oldType)) {
			if ("1190101".equals(oldType)) {
				CommonGenericDaoImpl<CPBaseStation, String> dao = this
						.getGenericDao(new CPBaseStation());
				
				String hql = "from CPBaseStation as o where o.checkPointId='" + checkPointId + "'";
				CPBaseStation bs = (CPBaseStation)dao.getSession().createQuery(hql).uniqueResult();
				dao.remove(bs);
			}
			if ("1190102".equals(oldType)) {
				CommonGenericDaoImpl<HandWell, String> dao = this
						.getGenericDao(new HandWell());
				String hql = "from HandWell as o where o.checkPointId='" + checkPointId + "'";
				HandWell hw = (HandWell)dao.getSession().createQuery(hql).uniqueResult();
				dao.remove(hw);
			}
			if ("1190103".equals(oldType)) {
				CommonGenericDaoImpl<LightJoinBox, String> dao = this
						.getGenericDao(new LightJoinBox());
				String hql = "from LightJoinBox as o where o.checkPointId='" + checkPointId + "'";
				LightJoinBox ljb = (LightJoinBox)dao.getSession().createQuery(hql).uniqueResult();
				dao.remove(ljb);
			}
			if ("1190104".equals(oldType)) {
				CommonGenericDaoImpl<Pole, String> dao = this
						.getGenericDao(new Pole());
				String hql = "from Pole as o where o.checkPointId='" + checkPointId + "'";
				Pole pole = (Pole)dao.getSession().createQuery(hql).uniqueResult();
				dao.remove(pole);
			}
		}
	}

	private <T, ID> CommonGenericDaoImpl<T, String> getGenericDao(T bean) {
		CommonGenericDaoImpl<T, String> dao = new CommonGenericDaoImpl<T, String>();
		CommonGenericDaoImpl<CheckPoint, String> checkPointDao = (CommonGenericDaoImpl<CheckPoint, String>) this
				.getGenericDAO();
		dao.setSessionFactory(checkPointDao.getSessionFactory());
		return dao;
	}

	@SuppressWarnings("unchecked")
	public <T> T findByType(String id, T bean) {
		CommonGenericDaoImpl<T, String> dao = this.getGenericDao(bean);
		String hql = "from " + bean.getClass().getSimpleName()
				+ " as o where o.checkPointId='" + id + "'";
		T t = (T) dao.getSession().createQuery(hql).uniqueResult();
		return t;
	}

	public void delete(CheckPoint cp) {
		String type = cp.getType();
		type = this.convertCPType(type);
		if ("1190101".equals(type)) {
			CommonGenericDaoImpl<CPBaseStation, String> dao = this
					.getGenericDao(new CPBaseStation());
			
			String hql = "from CPBaseStation as o where o.checkPointId='" + cp.getId() + "'";
			CPBaseStation bs = (CPBaseStation)dao.getSession().createQuery(hql).uniqueResult();
			this.remove(cp);
			dao.remove(bs);
		}
		if ("1190102".equals(type)) {
			CommonGenericDaoImpl<HandWell, String> dao = this
					.getGenericDao(new HandWell());
			String hql = "from HandWell as o where o.checkPointId='" + cp.getId() + "'";
			HandWell hw = (HandWell)dao.getSession().createQuery(hql).uniqueResult();
			this.remove(cp);
			dao.remove(hw);
		}
		if ("1190103".equals(type)) {
			CommonGenericDaoImpl<LightJoinBox, String> dao = this
					.getGenericDao(new LightJoinBox());
			String hql = "from LightJoinBox as o where o.checkPointId='" + cp.getId() + "'";
			LightJoinBox ljb = (LightJoinBox)dao.getSession().createQuery(hql).uniqueResult();
			this.remove(cp);
			dao.remove(ljb);
		}
		if ("1190104".equals(type)) {
			CommonGenericDaoImpl<Pole, String> dao = this
					.getGenericDao(new Pole());
			String hql = "from Pole as o where o.checkPointId='" + cp.getId() + "'";
			Pole pole = (Pole)dao.getSession().createQuery(hql).uniqueResult();
			this.remove(cp);
			dao.remove(pole);
		}
	}

	public void deleteAll(String[] ids) {
		for (String id : ids) {
			this.delete(this.find(id));
		}
	}

	public ImportResult importFromFile(InputStream is,
			String fileName, Map<String, String> params) throws Exception{
		ImportResult result = new ImportResult();
		result.setRestultMsg("");
		if (!fileName.endsWith(".xls")) { // 检查是否为Excel文件
			result.setRestultMsg(result.getRestultMsg() + "导入文件非法");
			result.setResultCode("500");
			throw new Exception("导入错误:" + result.getRestultMsg());
		}
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(new POIFSFileSystem(is));
		} catch (Exception e) {
			result.setResultCode("500");
			result.setRestultMsg(result.getRestultMsg() + "导入文件非法");
		}
		if (wb == null) { // 检查能否获取工作薄
			result.setRestultMsg("不能获取工作薄");
			result.setResultCode("501");
			throw new Exception("导入错误:" + result.getRestultMsg());
		}
		HSSFSheet sheet = wb.getSheetAt(0);
		if (sheet == null) { // 检查能否获取工作表
			result.setRestultMsg("不能获取工作表");
			result.setResultCode("502");
		}
		String type = params.get("cpType");
		boolean validate = fileTemplateCheck(type,sheet);
		if(!validate) {
			result.setRestultMsg("模板错误：模板文件与巡检点类型不匹配或模板格式错误！");
			result.setResultCode("503");
			throw new Exception("导入错误:" + result.getRestultMsg());
		}
		
		int count = 0;
		int point = 0;
		try {
			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 获取工作表中每行数据
				point = i;
				count++;
				HSSFRow row = sheet.getRow(i);
				if(!this.blankRowCheck(row,type)) {
					saveRow2CheckPoint(row,params);
				} else {
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
			result.setRestultMsg("导入错误:错误行数是:"+msg + (point + 1));
			throw new Exception("导入错误:错误行数是:" + result.getRestultMsg());
		}
		result.setRestultMsg("导入成功");
		result.setResultCode("200");
		result.setImportCount(count);
		return result;
	}

	private boolean fileTemplateCheck(String type,HSSFSheet sheet) {
		HSSFCell cell;
		HSSFRow row = sheet.getRow(0);
		int colNum = 0;
		if ("1190101".equals(type)) {//基站
			colNum = 13;
		}
		if ("1190102".equals(type)) {//人手井
			colNum = 19;
		}
		if ("1190103".equals(type)) {//光交接箱
			colNum = 12;
		}
		if ("1190104".equals(type)) {//电杆
			colNum = 19;
		}
		for(int i=0;i<=colNum;i++) {
			cell = row.getCell(i);
			if(i == colNum) {
				if(cell != null && !"".equals(cell.toString())) {
					return false;
				}
			} else {
				if(cell == null || "".equals(cell.toString())) {
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * 将每行数据构建为考核指标对象，并保存
	 */
	public void saveRow2CheckPoint(HSSFRow row,Map<String,String> params) throws Exception {
		ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		CheckSegmentService css = (CheckSegmentService)ctx.getBean("checkSegmentService");
		
		String value = "";
		String type = params.get("cpType");
		CheckPoint cp = new CheckPoint();
		
		//资源点编码
		cp.setResourceCode(row.getCell(0).getRichStringCellValue().getString());
		//资源点名称
		cp.setResourceName(row.getCell(1).getRichStringCellValue().getString());
		//经度	
		if(row.getCell(2).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			value = "" + row.getCell(2).getNumericCellValue();
			cp.setLongitude(Double.parseDouble(value));
		} else {
			cp.setLongitude(Double.parseDouble(row.getCell(2).getRichStringCellValue().getString()));
		}
		//纬度
		if(row.getCell(3).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			value = "" + row.getCell(3).getNumericCellValue();
			cp.setLatitude(Double.parseDouble(value));
		} else {
			cp.setLatitude(Double.parseDouble(row.getCell(3).getRichStringCellValue().getString()));
		}
		//地址	
		cp.setAddress(row.getCell(4).getRichStringCellValue().getString());
		//是否为检查点
		value = StaticMethod.null2String(row.getCell(5).getRichStringCellValue().getString());
		value = this.name2Id(value, "10301");
		cp.setIsCheckPoint(value);
		//所属光缆段
		cp.setCableSegment(row.getCell(6).getRichStringCellValue().getString());
		//所属光缆系统
		cp.setCableSystem(row.getCell(7).getRichStringCellValue().getString());
		//所属巡检段
		String cpsId = StaticMethod.null2String(row.getCell(8).getRichStringCellValue().getString());
		if("".equals(cpsId)) {
			throw new Exception("\"所属巡检段\"不能为空！");
		}
		cpsId = css.name2id(cpsId);
		cp.setCheckPointSegmentId(cpsId);
		//重要关注点	
		cp.setImportantFocus(row.getCell(9).getRichStringCellValue().getString());
		//类型	(name2Id 11901)
		String typeValue = StaticMethod.null2String(row.getCell(10).getRichStringCellValue().getString());
		if("".equals(typeValue)) {
			throw new Exception("\"类型\"不能为空！");
		}
		typeValue = this.name2Id(typeValue,"11901");
		cp.setType(typeValue);
		//添加时间
		cp.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
		
		//类型
		if ("1190101".equals(type)) {//基站
			CPBaseStation bs = new CPBaseStation();
			//等级
			bs.setBaseStationLevel(row.getCell(11).getRichStringCellValue().getString());
			//名称
			bs.setBaseStationName(row.getCell(12).getRichStringCellValue().getString());
			
			this.save(cp, bs, "", "");
		}
		if ("1190102".equals(type)) {//人手井
			HandWell hw = new HandWell();
			//人手井编号 
			hw.setHandleWellNum(row.getCell(11).getRichStringCellValue().getString());       
			//承载光缆中继段名称
			hw.setLoadCableSegmentName(row.getCell(12).getRichStringCellValue().getString());   
			//巡检员
			hw.setCheckPointUser(row.getCell(13).getRichStringCellValue().getString());     
			//承载系统级别   
			hw.setLoadSysLevel(row.getCell(14).getRichStringCellValue().getString());             
			//所属管道名    
			hw.setPipelineName(row.getCell(15).getRichStringCellValue().getString());                
			//是否接头井
			value = StaticMethod.null2String(row.getCell(16).getRichStringCellValue().getString());
			value = this.name2Id(value, "10301");
			hw.setIsJointWell(value);      
			//是否有光缆预留
			value = StaticMethod.null2String(row.getCell(17).getRichStringCellValue().getString());
			value = this.name2Id(value, "10301");
			hw.setIsCableObligate(value);     
			//是否重要关注点
			value = StaticMethod.null2String(row.getCell(18).getRichStringCellValue().getString());
			value = this.name2Id(value, "10301");
			hw.setIsImportantFocus(value); 
			
			this.save(cp, hw, "", "");
		}
		if ("1190103".equals(type)) {//光交接箱
			LightJoinBox ljb = new LightJoinBox();
			//名称
			ljb.setLightJoinBoxName(row.getCell(11).getRichStringCellValue().getString());
			
			this.save(cp, ljb, "", "");
		}
		if ("1190104".equals(type)) {//电杆
			Pole pole = new Pole();
			//电杆编号
			pole.setPoleNum(row.getCell(11).getRichStringCellValue().getString());                       
			//巡检员
			pole.setPoletUser(row.getCell(12).getRichStringCellValue().getString());         
			//承载光缆中继段名称
			pole.setLoadCableSegmentName(row.getCell(13).getRichStringCellValue().getString());
			//承载系统级别   
			pole.setLoadSysLevel(row.getCell(14).getRichStringCellValue().getString());           
			//所属杆路名
			pole.setPoleName(row.getCell(15).getRichStringCellValue().getString());                    
			//是否接头杆
			value = StaticMethod.null2String(row.getCell(16).getRichStringCellValue().getString());
			value = this.name2Id(value, "10301");
			pole.setIsJointPole(value);                 
			//是否有光缆预留
			value = StaticMethod.null2String(row.getCell(17).getRichStringCellValue().getString());
			value = this.name2Id(value, "10301");
			pole.setIsCableObligate(value);         
			//是否重要关注点 
			value = StaticMethod.null2String(row.getCell(18).getRichStringCellValue().getString());
			value = this.name2Id(value, "10301");
			pole.setIsImportantFocus(value);       
			
			this.save(cp, pole, "", "");
		}
	}
	private boolean blankRowCheck(HSSFRow row,String type) {
		HSSFCell cell;
		int j = 0;
		int colNum = 0;
		if ("1190101".equals(type)) {//基站
			colNum = 13;
		}
		if ("1190102".equals(type)) {//人手井
			colNum = 19;
		}
		if ("1190103".equals(type)) {//光交接箱
			colNum = 12;
		}
		if ("1190104".equals(type)) {//电杆
			colNum = 19;
		}
		for(int i=0;i<colNum;i++) {
			cell = row.getCell(i);
			if(cell == null) {
				j++;
			}else if("".equals(cell.toString())) {
				j++;
			}
		}
		if(j >= colNum) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unused")
	private String name2Id(String name,String initDictId) throws Exception{
		Map<String,String> dictMap = this.getDictMap(initDictId);
		String id = "";
		String value = "";
		for(String key : dictMap.keySet()) {
			value = dictMap.get(key);
			if(name.equals(value)) {
				id = key;
				break;
			}
		}
		if("".equals(id)) {
			throw new RuntimeException("導入數據與數據字典不匹配！");
		}
		return id;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,String> getDictMap(String initDictId) throws Exception{
		if("".equals(initDictId) || initDictId == null) {
			return null;
		}
		
		// 取字典数据
		ITawSystemDictTypeManager dictManager = (ITawSystemDictTypeManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDictTypeManager");
		List list = dictManager.getDictSonsByDictid(initDictId);

		Map<String,String> dictMap = new HashMap<String,String>();
		if(list.size()>0){
			String itemName = null;
			String itemId = null;
			// 将list中的值元素插入Map
			for (Iterator it = list.iterator(); it.hasNext();) {
				TawSystemDictType item = (TawSystemDictType) it.next();
				itemName = StaticMethod.null2String(item.getDictName());
				itemId = StaticMethod.null2String(item.getDictId());
				
				dictMap.put(itemId, itemName);
			}
		} else {
			return null;
		}
		return dictMap;
	}
	
	
	public CheckPointType getCheckPointType() {
		ApplicationContextHolder holder = ApplicationContextHolder.getInstance();
		return (CheckPointType) holder.getBean("cpType");
	}
	
	private String convertCPType(String key) {
		CheckPointType cpType = this.getCheckPointType();
		Map<String,String> typeMap = cpType.getCpType();
		return typeMap.get(key).toString();
		
	}
	
	/**
	 * 根据巡检段id查询巡检段包括的巡检点
	 * @param segmentId
	 * @return
	 */
	public List<CheckPoint> findCheckPointsBySegmentId(String segmentId){
		Search search = new Search();
		search.addFilterEqual("checkPointSegmentId", segmentId);
		search.addFilterEqual("isCheckPoint", "1030101"); //必须是检查点
		List<CheckPoint> list = search(search);
		return list;
	}
}
