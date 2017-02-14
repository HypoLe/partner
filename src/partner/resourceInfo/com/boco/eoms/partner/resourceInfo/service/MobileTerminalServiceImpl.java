package com.boco.eoms.partner.resourceInfo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.dao.IMobileTerminalDao;
import com.boco.eoms.partner.resourceInfo.model.Car;
import com.boco.eoms.partner.resourceInfo.model.MobileTerminal;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSFileImport;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class MobileTerminalServiceImpl extends CommonGenericServiceImpl<MobileTerminal> implements MobileTerminalService,PnrProcessService {
	private IMobileTerminalDao mobileTerminalDao;
	public IMobileTerminalDao getMobileTerminalDao() {
		return mobileTerminalDao;
	}

	public void setMobileTerminalDao(IMobileTerminalDao mobileTerminalDao) {
		this.setCommonGenericDao(mobileTerminalDao) ;
		this.mobileTerminalDao=mobileTerminalDao;
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

	/**
	 * 管理员批量导入
	 */
	public ImportResult importFromFile(FormFile formFile) throws Exception {
		XLSFileImport xlsFileImport=new XLSFileImport(){
			public XLSModel getXLSModel() {
				XLSModel xlsModel=new XLSModel(2,1,8,2,0,1,3,0,8);
				return xlsModel;
			}
			public boolean doSaveRow2Data(HSSFRow row) throws Exception {
				MobileTerminal mobileTerminal=new MobileTerminal();
				mobileTerminal.setArea(XLSCellValidateUtil.checkAndGetAreaId(row, 1));
				mobileTerminal.setMaintainCompany(XLSCellValidateUtil.checkAndGetDeptId(row, 2));
				mobileTerminal.setMaintainTeam(XLSCellValidateUtil.checkAndGetDeptId(row, 3));
				mobileTerminal.setMobileTerminalNumber(XLSCellValidateUtil.checkIsNull(row, 4));
				XLSCellValidateUtil.checkDictName(row, 5, "1230401",false);
				mobileTerminal.setMobileTerminalType(XLSCellValidateUtil.dictNameToDictId(row, 5, "1230401",false));
				mobileTerminal.setMobileTerminalFactory(XLSCellValidateUtil.checkIsNull(row, 6));
				mobileTerminal.setMobileTerminalSerilNumber(XLSCellValidateUtil.checkIsNull(row, 7));
				mobileTerminal.setSimCardNumber(XLSCellValidateUtil.checkIsNull(row, 8));
				mobileTerminal.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
				mobileTerminal.setNotes("批量导入");
				mobileTerminal.setVisible("0");
				mobileTerminal.setDeleted("0");
				save(mobileTerminal);
				return true;
			}
		};
		ImportResult result=xlsFileImport.xlsFileValidate(formFile);
		return result;
	}
	/**
	 * 删除数据归档
	 */
	public boolean doDeleteXLSFileData(HSSFRow row, HttpServletRequest request)
			throws Exception {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getDeleteStartRowNum()) {
			return false;//数据验证是从第三行开始，前二行不做归档
		}
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=mobileTerminalDao.findByHql("from MobileTerminal where id="+id);
		MobileTerminal mobileTerminal=(MobileTerminal)list.get(0);
		mobileTerminal.setVisible("0");
		mobileTerminal.setDeleted("1");
		save(mobileTerminal);
		return true;
	}
	/**
	 * 增加数据归档
	 */
	public boolean doSaveXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getAddStartRowNum()) {
			return false;//数据验证是从第四行开始，前三行不做归档
		}
		MobileTerminal mobileTerminal=new MobileTerminal();
		mobileTerminal=getMobileTerminalObject(row, mobileTerminal);
		save(mobileTerminal);
		return true;
	}
	/**
	 * 更新数据归档
	 */
	public boolean doUpdateXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getUpdateStartRowNum()) {
			return false;//数据验证是从第四行开始，前三行不归档
		}
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=mobileTerminalDao.findByHql("from MobileTerminal where id="+id);
		MobileTerminal mobileTerminal=(MobileTerminal)list.get(0);
		mobileTerminal=getMobileTerminalObject(row, mobileTerminal);
		this.save(mobileTerminal);
		return true;
	}
	/**
	 * 删除数据校验
	 */
	public boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception{
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=new ArrayList<MobileTerminal>();
		list=mobileTerminalDao.findByHql("from MobileTerminal where id="+id+" and deleted <>  1  and visible <> 1");
		if (list.size()<1) {
			throw new RuntimeException("序号为 "+id+" 的记录不存在请核实再传！");
		}
		MobileTerminal mobileTerminal=(MobileTerminal)list.get(0);
		mobileTerminal.setVisible("1");
		mobileTerminal.setDeleted("0");
		save(mobileTerminal);
		return true;
	}
	/**
	 * 更新数据校验
	 */
	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception {
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=new ArrayList<MobileTerminal>();
		list=mobileTerminalDao.findByHql("from MobileTerminal where id="+id+" and deleted <>  1  and visible <> 1 ");
		if (list.size()<1) {
			throw new RuntimeException("序号为"+id+"的记录不存在请核实再传！");
		}
		validateEachRow(row);
		MobileTerminal mobileTerminal=(MobileTerminal)list.get(0);
		mobileTerminal.setVisible("1");
		mobileTerminal.setDeleted("0");
		save(mobileTerminal);
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
	 * 校验当前行的每一个单元格数据
	 */
	private void validateEachRow( HSSFRow row) throws Exception{
		 XLSCellValidateUtil.checkAndGetAreaId(row, 1);
		 XLSCellValidateUtil.checkAndGetDeptId(row, 2);
		 XLSCellValidateUtil.checkAndGetDeptId(row, 3);
		 XLSCellValidateUtil.checkIsNull(row, 4);
		 XLSCellValidateUtil.checkDictName(row, 5, "1230401",false);
		 XLSCellValidateUtil.checkIsNull(row, 6);
		 XLSCellValidateUtil.checkIsNull(row, 7);
		 XLSCellValidateUtil.checkIsNull(row, 8);
	}
	/**
	 * 将当前行解析为一个Model对象
	 * @param row
	 * @param mobileTerminal
	 * @return
	 * @throws Exception
	 */
	private MobileTerminal getMobileTerminalObject(HSSFRow row,MobileTerminal mobileTerminal) throws Exception{
		mobileTerminal.setArea(XLSCellValidateUtil.checkAndGetAreaId(row,1));
		mobileTerminal.setMaintainCompany(XLSCellValidateUtil.checkAndGetDeptId(row,2));
		mobileTerminal.setMaintainTeam(XLSCellValidateUtil.checkAndGetDeptId(row,3));
		mobileTerminal.setMobileTerminalNumber(XLSCellValidateUtil.getCellStringValue(row,4));
		mobileTerminal.setMobileTerminalType(XLSCellValidateUtil.dictNameToDictId(row,5, "1230401",false));
		mobileTerminal.setMobileTerminalFactory(XLSCellValidateUtil.getCellStringValue(row,6));
		mobileTerminal.setMobileTerminalSerilNumber(XLSCellValidateUtil.getCellStringValue(row,7));
		mobileTerminal.setSimCardNumber(XLSCellValidateUtil.getCellStringValue(row,8));
		mobileTerminal.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
		mobileTerminal.setNotes(" ");
		mobileTerminal.setVisible("0");
		mobileTerminal.setDeleted("0");
		return mobileTerminal;
	}

	public XLSModel getXLSModel() {
		XLSModel xlsModel=new XLSModel(2,1,8,2,0,1,2,0,8);
		return xlsModel;
	}
	
	public boolean doRestoreDeleteXLSFileData(HSSFRow row,
			HttpServletRequest request) {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getDeleteStartRowNum()) {
			return false;//数据验证是从第三行开始，前二行不做归档
		}
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=mobileTerminalDao.findByHql("from MobileTerminal where id="+id);
		MobileTerminal mobileTerminal=(MobileTerminal)list.get(0);
		mobileTerminal.setVisible("0");
		mobileTerminal.setDeleted("0");
		save(mobileTerminal);
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
		List list=mobileTerminalDao.findByHql("from MobileTerminal where id="+id);
		MobileTerminal mobileTerminal=(MobileTerminal)list.get(0);
		mobileTerminal.setVisible("0");
		mobileTerminal.setDeleted("0");
		save(mobileTerminal);
		return true;
	}

	@Override
	public MobileTerminal find(String id2) {
		int id=Integer.parseInt(id2);
		Search search=new Search();
		MobileTerminal mobileTerminal=null;
		search.addFilterEqual("id", id);
		SearchResult<MobileTerminal> result=searchAndCount(search);
		List<MobileTerminal>  list=new ArrayList<MobileTerminal>();
		list=result.getResult();
		if (list.size()<1) {
			mobileTerminal=new MobileTerminal();
		}else {
			mobileTerminal=list.get(0);
		}
		return mobileTerminal;
	}

	public boolean doLoadStaticSource() {
		// XXX Auto-generated method stub
		return false;
	}
}
