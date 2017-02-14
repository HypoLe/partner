package com.boco.eoms.partner.resourceInfo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;

import clover.org.apache.velocity.runtime.parser.node.o;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.dao.IOilEngineDao;
import com.boco.eoms.partner.resourceInfo.model.Car;
import com.boco.eoms.partner.resourceInfo.model.OilEngine;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSFileImport;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class OilEngineServiceImpl extends CommonGenericServiceImpl<OilEngine> implements OilEngineService,PnrProcessService{
	private IOilEngineDao oilEngineDao;
	public IOilEngineDao getOilEngineDao() {
		return oilEngineDao;
	}
	public void setOilEngineDao(IOilEngineDao oilEngineDao) {
		this.setCommonGenericDao(oilEngineDao);
		this.oilEngineDao=oilEngineDao;
	}
	public Search searchPrivFilter(Search search, String userId,String deptId,HttpServletRequest request) {
		search.addFilterNotEqual("deleted", "1");
		//获取区域id作为删选条件
		List<PartnerDept>  list0=new ArrayList<PartnerDept>();
		if (!"admin".equals(userId)) {
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)com.boco.eoms.base.util.ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
			list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+deptId+"'");
			if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
				search.addFilterILike("maintainCompany", deptId+"%");//代维公司权限限定
			}else {
				ITawSystemDeptManager deptManager=(ITawSystemDeptManager)com.boco.eoms.base.util.ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
				TawSystemDept dept=deptManager.getDeptinfobydeptid(deptId,"0");
				if (dept!=null) {
					search.addFilterILike("area", dept.getAreaid()+"%");//区域权限限定
				}
			}
		}
		search.addFilterNotEqual("visible", "1");//1表示不可见，正在执行流程申请中
		search.addFilterNotEqual("deleted", "1");//1表示删除
		// TODO Auto-generated method stub
		return search;
	}

	public ImportResult importFromFile(FormFile formFile) throws Exception{
		XLSFileImport xlsFileImport=new XLSFileImport(){
			public XLSModel getXLSModel() {
				XLSModel xlsModel=new XLSModel(2,1,12,2,0,1,2,0,12);
				return xlsModel;
			}
			public boolean doSaveRow2Data(HSSFRow row) throws Exception {
				OilEngine oilEngine=new OilEngine();
				oilEngine.setArea(XLSCellValidateUtil.checkAndGetAreaId(row, 1));
				oilEngine.setMaintainCompany(XLSCellValidateUtil.checkAndGetDeptId(row, 2));
				oilEngine.setOilEngineNumber(XLSCellValidateUtil.checkIsNull(row, 3));
				oilEngine.setOilEngineModel(XLSCellValidateUtil.checkIsNull(row, 4));
				oilEngine.setOilEngineFactory(XLSCellValidateUtil.checkIsNull(row, 5));
				oilEngine.setPowerRating(XLSCellValidateUtil.checkNumber(row, 6));
				oilEngine.setStandardFuelConsumption(XLSCellValidateUtil.checkNumber(row,7));
				oilEngine.setOilEngineType(XLSCellValidateUtil.checkDictName(row, 8, "1230302",false));
				oilEngine.setOilEngineProperty(XLSCellValidateUtil.checkDictName(row, 9, "1230304",false));
				oilEngine.setOilEngineStatus(XLSCellValidateUtil.checkDictName(row, 10, "1230303",false));
				oilEngine.setFuleType(XLSCellValidateUtil.checkDictName(row, 11, "1230305",false));
				oilEngine.setPlace(XLSCellValidateUtil.checkAndGetAreaId(row, 12));
				oilEngine.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
				oilEngine.setNotes("");
				oilEngine.setDeleted("0");
				oilEngine.setVisible("0");
				save(oilEngine);
				return true;
			}
		};
		ImportResult result=xlsFileImport.xlsFileValidate(formFile);
		return result;
	}
	/**
	 * 校验当前行的每一个单元格数据
	 * @param row
	 * @throws Exception
	 */
	private void validateEachRow( HSSFRow row) throws Exception{
		XLSCellValidateUtil.checkAndGetAreaId(row, 1);
		XLSCellValidateUtil.checkAndGetDeptId(row, 2);
		XLSCellValidateUtil.checkIsNull(row, 3);
		XLSCellValidateUtil.checkIsNull(row, 4);
		XLSCellValidateUtil.checkIsNull(row, 5);
		XLSCellValidateUtil.checkNumber(row, 6);
		XLSCellValidateUtil.checkNumber(row,7);
		XLSCellValidateUtil.checkDictName(row, 8, "1230302",false);
		XLSCellValidateUtil.checkDictName(row, 9, "1230304",false);
		XLSCellValidateUtil.checkDictName(row, 10, "1230303",false);
		XLSCellValidateUtil.checkDictName(row, 11, "1230305",false);
		XLSCellValidateUtil.checkAndGetAreaId(row, 12);
		XLSCellValidateUtil.checkNumber(row, 13, true);
		XLSCellValidateUtil.checkNumber(row, 14, true);
	}
	/**
	 * 根据唯一性标示找出需要删除的数据是否存在
	 * 删除的模板中只需要申请人员提供唯一性标示的字段，其他字段不需要提供
	 * 如果不存在则要抛出异常
	 */
	public boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception{
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=new ArrayList<OilEngine>();
		list=oilEngineDao.findByHql("from OilEngine where id="+id+"  and  deleted <> 1 and visible <> 1");
		if (list.size()<1) {
			throw new RuntimeException("序号为 "+id+" 的记录不存在请核实再传！");
		}
		OilEngine oilEngine=(OilEngine)list.get(0);
		oilEngine.setDeleted("0");
		oilEngine.setVisible("1");
		save(oilEngine);
		return true;
	}
	/*
	 * 根据唯一性标示找出需要更新的数据是否存在，然后再检查是否符合要求，
	 * 比增加操作多一个唯一性标识字段，
	 * 如果不存在则要抛出异常
	 */
	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception{
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=oilEngineDao.findByHql("from OilEngine where id="+id+"  and deleted <>  1 and visible <> 1");
		if (list==null||list.size()<1) {
			throw new RuntimeException("序号为 "+id+" 的记录不存在请核实再传！");
		}
		validateEachRow(row);
		OilEngine oilEngine=(OilEngine)list.get(0);
		oilEngine.setDeleted("0");
		oilEngine.setVisible("1");
		save(oilEngine);
		return true;
	}
	/**
	 * 新增数据校验
	 */
	public boolean doSaveXLSFileValidate(HSSFRow row) throws Exception{
		validateEachRow(row);
		return true;
	}
	/**
	 * 删除数据归档
	 */
	public boolean doDeleteXLSFileData(HSSFRow row, HttpServletRequest request) {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getDeleteStartRowNum()) {
			return false;//数据验证是从第三行开始，前二行不做归档
		}
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=oilEngineDao.findByHql("from OilEngine where id="+id);
		if (list==null||list.size()<1) {
			throw new RuntimeException("序号为 "+id+" 的记录不存在请核实再传！");
		}
		OilEngine oilEngine=(OilEngine)list.get(0);
		oilEngine.setDeleted("1");
		oilEngine.setVisible("0");
		save(oilEngine);
		return true;
	}
	/**
	 * 新增数据归档
	 */
	public boolean doSaveXLSFileData(HSSFRow row, HttpServletRequest request) throws Exception {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getAddStartRowNum()) {
			return false;//数据验证是从第四行开始，前三行不做归档
		}
		OilEngine oilEngine=new OilEngine();
		oilEngine=getOilEngineObject(row,oilEngine);
		save(oilEngine);
		return true;
	}
	/**
	 * 更新数据归档
	 */
	public boolean doUpdateXLSFileData(HSSFRow row, HttpServletRequest request) throws Exception {
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getUpdateStartRowNum()) {
			return false;//数据验证是从第四行开始，前三行不归档
		}
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=oilEngineDao.findByHql("from OilEngine where id="+id);
		if (list==null||list.size()<1) {
			throw new RuntimeException("序号为 "+id+" 的记录不存在请核实再传！");
		}
		OilEngine oilEngine=(OilEngine)list.get(0);
//		oilEngine=getOilEngineObject(row, oilEngine);

		oilEngine.setDeleted("1");
		oilEngine.setVisible("1");
		save(oilEngine);
		String changeMan = (String) request.getAttribute("changeMan");
		String changeTime = (String) request.getAttribute("changeTime");
		OilEngine oilEngine1= new OilEngine();
		oilEngine1.setChangeMan(changeMan);
		oilEngine1.setChangeTime(changeTime);
		oilEngine=getOilEngineObject(row, oilEngine1);
		save(oilEngine1);		
		return true;
	}

	public OilEngine getOilEngineObject(HSSFRow row,OilEngine oilEngine) throws Exception{
		oilEngine.setArea(XLSCellValidateUtil.checkAndGetAreaId(row, 1));
		oilEngine.setMaintainCompany(XLSCellValidateUtil.checkAndGetDeptId(row, 2));
		oilEngine.setOilEngineNumber(XLSCellValidateUtil.getCellStringValue(row, 3));
		oilEngine.setOilEngineModel(XLSCellValidateUtil.getCellStringValue(row, 4));
		oilEngine.setOilEngineFactory(XLSCellValidateUtil.getCellStringValue(row, 5));
		oilEngine.setPowerRating(XLSCellValidateUtil.getCellStringValue(row,6));
		oilEngine.setStandardFuelConsumption(XLSCellValidateUtil.getCellStringValue(row,7));
		oilEngine.setOilEngineType(XLSCellValidateUtil.dictNameToDictId(row, 8, "1230302",false));
		oilEngine.setOilEngineProperty(XLSCellValidateUtil.dictNameToDictId(row, 9, "1230304",false));
		oilEngine.setOilEngineStatus(XLSCellValidateUtil.dictNameToDictId(row, 10, "1230303",false));
		oilEngine.setFuleType(XLSCellValidateUtil.dictNameToDictId(row, 11, "1230305",false));
		oilEngine.setPlace(XLSCellValidateUtil.checkAndGetAreaId(row, 12));
		oilEngine.setLongitude(XLSCellValidateUtil.getCellStringValue(row, 13,"1"));
		oilEngine.setLatitude(XLSCellValidateUtil.getCellStringValue(row, 14,"1"));
		oilEngine.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
		oilEngine.setNotes("");
		oilEngine.setDeleted("0");
		oilEngine.setVisible("0");
		return oilEngine;
	}
	public XLSModel getXLSModel() {
		XLSModel xlsModel=new XLSModel(2,1,12,2,0,1,2,0,12);
		return xlsModel;
	}
	
	public boolean doRestoreDeleteXLSFileData(HSSFRow row,HttpServletRequest request) {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getDeleteStartRowNum()) {
			return false;//数据验证是从第三行开始，前二行不做归档
		}
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=oilEngineDao.findByHql("from OilEngine where id="+id);
		OilEngine oilEngine=(OilEngine)list.get(0);
		oilEngine.setDeleted("0");
		oilEngine.setVisible("0");
		save(oilEngine);
		return false;
	}
	public boolean doRestoreSaveXLSFileData(HSSFRow row,
			HttpServletRequest request) {
		return true;
	}
	public boolean doRestoreUpdateXLSFileData(HSSFRow row,
			HttpServletRequest request) {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getUpdateStartRowNum()) {
			return false;//数据验证是从第三行开始，前二行不做归档
		}
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=oilEngineDao.findByHql("from OilEngine where id="+id);
		OilEngine oilEngine=(OilEngine)list.get(0);
		oilEngine.setDeleted("0");
		oilEngine.setVisible("0");
		save(oilEngine);
		return false;
	}
	@Override
	public OilEngine find(String id2) {
		int id=Integer.parseInt(id2);
		Search search=new Search();
		OilEngine oilEngine=null;
		search.addFilterEqual("id", id);
		SearchResult<OilEngine> result=searchAndCount(search);
		List<OilEngine> list=new ArrayList<OilEngine>();
		list=result.getResult();
		if (list.size()<1) {
			oilEngine=new OilEngine();
		}else {
			oilEngine=list.get(0);
		}
		return oilEngine;
	}
	public boolean doLoadStaticSource() {
		// XXX Auto-generated method stub
		return false;
	}
	public List getOilEngineChangeList(final String whereStr){
		List<OilEngine> list = oilEngineDao.findByHql(whereStr);
		return list;
	}
	
}
