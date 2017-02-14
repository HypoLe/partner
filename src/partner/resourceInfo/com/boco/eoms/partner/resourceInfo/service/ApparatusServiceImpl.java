package com.boco.eoms.partner.resourceInfo.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.process.util.PnrProcessHandler;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.dao.IApparatusDao;
import com.boco.eoms.partner.resourceInfo.model.Apparatus;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSFileImport;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class ApparatusServiceImpl extends CommonGenericServiceImpl<Apparatus>     implements ApparatusService,PnrProcessService {
	private IApparatusDao apparatusDao;
	

	public IApparatusDao getApparatusDao() {
		return apparatusDao;
	}
	public void setApparatusDao(IApparatusDao apparatusDao) {
		this.setCommonGenericDao(apparatusDao);
		this.apparatusDao=apparatusDao;
	}
	
public Search searchPrivFilter(Search search, String userId,String deptId,HttpServletRequest request) {
		search.addFilterNotEqual("deleted", "1");
		//获取区域id作为删选条件
		List<PartnerDept>  list0=new ArrayList<PartnerDept>();
		if (!"admin".equals(userId)) {
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)com.boco.eoms.base.util.ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
			list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+deptId+"'");
			if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
				search.addFilterILike("maintenanceCompany", deptId+"%");//代维公司权限限定
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
	/**
	 * 管理员批量导入
	 */
	public ImportResult importFromFile(FormFile formFile) throws Exception {
		XLSFileImport xlsFileImport=new XLSFileImport(){
			public XLSModel getXLSModel() {
				XLSModel xlsModel=new XLSModel(2,1,12,2,0,1,3,0,12);
				return xlsModel;
			}
			public boolean doSaveRow2Data(HSSFRow row) throws Exception {
				validateEachRow(row);
				Apparatus apparatus=new Apparatus();
				apparatus=getApparatusObject(row, apparatus);
				save(apparatus);
				return true;
			}
		};
		ImportResult result=xlsFileImport.xlsFileValidate(formFile);
		return result;
	}
	/**
	 * 将每一行解析为model
	 */
	public Apparatus getApparatusObject(HSSFRow row,Apparatus apparatus) throws Exception{
		apparatus.setArea(XLSCellValidateUtil.checkAndGetAreaId(row, 1));
		apparatus.setMaintenanceCompany(XLSCellValidateUtil.checkAndGetDeptId(row, 2));
		apparatus.setApparatusSerialNumber(XLSCellValidateUtil.getCellStringValue(row, 3));
		String major2Id=XLSCellValidateUtil.dictNameToDictId(row, 4, "1230101", false);
		apparatus.setMaintenanceMajor(major2Id);
		apparatus.setApparatusName(XLSCellValidateUtil.dictNameToDictId(row,5, major2Id,false));
		apparatus.setApparatusType(XLSCellValidateUtil.getCellStringValue(row, 6));
		apparatus.setPurchasingTime(XLSCellValidateUtil.getCellStringValue(row, 7));
		apparatus.setApparatusDateOfProduction(XLSCellValidateUtil.getCellStringValue(row, 8));
		apparatus.setApparatusServiceLife(XLSCellValidateUtil.getCellStringValue(row, 9));
		apparatus.setApparatusStatus(XLSCellValidateUtil.dictNameToDictId(row, 10, "1230102",false));
		apparatus.setApparatusProperty(XLSCellValidateUtil.dictNameToDictId(row, 11, "1230103",false));
		apparatus.setApparatusBelongs(XLSCellValidateUtil.getCellStringValue(row, 12));
		apparatus.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
		apparatus.setDeleted("0");
		apparatus.setVisible("0");
		return apparatus;
	}
	/**
	 * 删除归档
	 */
	public boolean doDeleteXLSFileData(HSSFRow row,HttpServletRequest request)	throws Exception {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getDeleteStartRowNum()) {
			return false;//数据验证是从第三行开始，前二行不做归档
		}
		Apparatus apparatus = new Apparatus();
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String cellValue=xlsUtil.getCellStringValue(row, 0);
		List list=apparatusDao.findByHql("from Apparatus where id="+cellValue);
		apparatus=(Apparatus)list.get(0);
		apparatus.setDeleted("1");
		apparatus.setVisible("0");
		save(apparatus);
		return true;
	}
	/**
	 * 新增归档
	 */
	public boolean doSaveXLSFileData(HSSFRow row,HttpServletRequest request) throws Exception {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getAddStartRowNum()) {
			return false;//数据验证是从第四行开始，前三行不做归档
		}
		Apparatus apparatus= new Apparatus();
		apparatus=this.getApparatusObject(row, apparatus);
		save(apparatus);
		return true;
	}
	/**
	 * 更新归档
	 */
	public boolean doUpdateXLSFileData(HSSFRow row,HttpServletRequest request)  throws Exception{
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getUpdateStartRowNum()) {
			return false;//数据验证是从第四行开始，前三行不归档
		}
		Apparatus apparatus = new Apparatus();
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String cellValue=xlsUtil.getCellStringValue(row, 0);
		List list=apparatusDao.findByHql("from Apparatus where id="+cellValue);
		apparatus=(Apparatus)list.get(0);
		apparatus=this.getApparatusObject(row, apparatus);
		save(apparatus);
		return true;
	}
	/**
	 * 变更类型为增加时的校验
	 */
	public boolean doSaveXLSFileValidate(HSSFRow row ) throws Exception{
		validateEachRow(row);
		return true;
	}
	/**
	 * 变更类型为修改时的校验
	 */
	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception{
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String cellValue=xlsUtil.getCellStringValue(row, 0);
		List list=apparatusDao.findByHql("from Apparatus where id='"+cellValue+"'  and deleted <> 1 and visible <> 1");
		if (list==null||list.size()<1) {
			throw new RuntimeException("序号为 "+cellValue+" 的记录不存在请核实再传！");
		}
		validateEachRow(row);
		Apparatus apparatus=new Apparatus();
		apparatus=(Apparatus)list.get(0);
		apparatus.setVisible("1");
		save(apparatus);
		return true;
	}
	/**
	 * 变更类型为删除时的校验
	 */
	public boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception{
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String cellValue=xlsUtil.getCellStringValue(row, 0);
		List list=apparatusDao.findByHql("from Apparatus where id='"+cellValue+"'  and deleted <> 1 and visible <> 1");
		if (list==null||list.size()<1) {
			throw new RuntimeException("序号为 "+cellValue+" 的记录不存在请核实再传！");
		}
		Apparatus apparatus=new Apparatus();
		apparatus=(Apparatus)list.get(0);
		apparatus.setVisible("1");
		save(apparatus);
		return true;
	}
	/**
	 * 行校验
	 */
	public void validateEachRow(HSSFRow row) throws Exception{
		XLSCellValidateUtil.checkAndGetAreaId(row, 1);
		XLSCellValidateUtil.checkAndGetDeptId(row, 2);
		XLSCellValidateUtil.checkDictName(row, 4, "1230101",false);
		String major2Id=XLSCellValidateUtil.dictNameToDictId(row, 4, "1230101",false);
		XLSCellValidateUtil.checkDictName(row,5, major2Id,false);
		XLSCellValidateUtil.checkIsNull(row, 6);
		XLSCellValidateUtil.compareDate(row, 7, 8, "greatThan");
		XLSCellValidateUtil.checkNumber(row,9);
		XLSCellValidateUtil.checkDictName(row, 10, "1230102",false);
		XLSCellValidateUtil.checkDictName(row, 11, "1230103",false);
		XLSCellValidateUtil.checkIsNull(row, 12);
	}
	public XLSModel getXLSModel() {
		XLSModel xlsModel=new XLSModel(2,1,12,2,0,1,2,0,12);
		return xlsModel;
	}
	/**
	 * 通过主键id查找model；
	 */
	public Apparatus find(String id2) {
		int id=Integer.parseInt(id2);
		Search search=new Search();
		Apparatus apparatus=null;
		search.addFilterEqual("id", id);
		SearchResult<Apparatus> result=searchAndCount(search);
		List<Apparatus> list=new ArrayList<Apparatus>();
		list=result.getResult();
		if (list.size()<1) {
			apparatus=new Apparatus();
		}else {
			apparatus=list.get(0);
		}
		return apparatus;
	}
	public boolean doRestoreDeleteXLSFileData(HSSFRow row,HttpServletRequest request) {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getDeleteStartRowNum()) {
			return false;//数据验证是从第三行开始，前二行不做归档
		}
		Apparatus apparatus = new Apparatus();
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String cellValue=xlsUtil.getCellStringValue(row, 0);
		List list=apparatusDao.findByHql("from Apparatus where id="+cellValue);
		apparatus=(Apparatus)list.get(0);
		apparatus.setDeleted("0");
		apparatus.setVisible("0");
		save(apparatus);
		return true;
	}
	
	public boolean doRestoreSaveXLSFileData(HSSFRow row,HttpServletRequest request) {
		return true;
	}
	public boolean doRestoreUpdateXLSFileData(HSSFRow row,HttpServletRequest request) {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getUpdateStartRowNum()) {
			return false;//数据验证是从第四行开始，前三行不归档
		}
		Apparatus apparatus = new Apparatus();
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String cellValue=xlsUtil.getCellStringValue(row, 0);
		List list=apparatusDao.findByHql("from Apparatus where id="+cellValue);
		apparatus=(Apparatus)list.get(0);
		apparatus.setVisible("0");
		apparatus.setDeleted("0");
		save(apparatus);
		return true;
	}
	public boolean doLoadStaticSource() {
		// XXX Auto-generated method stub
		return false;
	}
	
	
}
