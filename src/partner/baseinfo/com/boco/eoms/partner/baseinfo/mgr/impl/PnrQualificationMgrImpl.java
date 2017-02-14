package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.boco.eoms.commons.interfaceMonitoring.util.interfaceMonitorin;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.baseinfo.dao.IPnrQualificationDao;
import com.boco.eoms.partner.baseinfo.mgr.IPnrQualificationMgr;
import com.boco.eoms.partner.baseinfo.model.PnrQualification;
import com.boco.eoms.partner.personnel.model.Certificate;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;

/**  
 * @Title: PnrQualificationMgrImpl.java
 * @Package com.boco.eoms.partner.baseinfo.mgr.impl
 * @Description: XXX
 * @author fengguangping fengguangping@boco.com.cn
 * @date Mar 18, 2013  5:45:39 PM  
 */
public class PnrQualificationMgrImpl extends CommonGenericServiceImpl<PnrQualification> implements IPnrQualificationMgr,PnrProcessService{
	private IPnrQualificationDao pnrQualificationDao;

	public IPnrQualificationDao getPnrQualificationDao() {
		return pnrQualificationDao;
	}

	public void setPnrQualificationDao(IPnrQualificationDao pnrQualificationDao) {
		this.setCommonGenericDao(pnrQualificationDao);
		this.pnrQualificationDao = pnrQualificationDao;
	}

	public List<PnrQualification> findPnrQualificationsByDeptUUid(String id) {
		List<PnrQualification> list=new ArrayList<PnrQualification>();
		String hql="from PnrQualification p where p.isDeleted='0' and p.relatedDeptId='"+id+"'";
		list=pnrQualificationDao.findByHql(hql);
		return list;
	}
	/**
	 * 删除归档
	 */
	public boolean doDeleteXLSFileData(HSSFRow row, HttpServletRequest request)throws Exception {
		int cellNum=0;
		String sysno=XLSCellValidateUtil.checkIsNull(row, cellNum++);
		List list=findPnrQualificationBySysno(sysno);
		if (list.size()<1) {
			int rowNumber=row.getRowNum();
			throw new RuntimeException("第"+rowNumber+"行,系统编号为\""+sysno+"\"的信息不存在");
		}
		PnrQualification p=(PnrQualification)list.get(0);
		p.setIsDeleted("1");
		this.save(p);
		return true;
	}
	/**
	 * 删除校验
	 */
	public boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception {
		int cellNum=0;
		String sysno=XLSCellValidateUtil.checkIsNull(row, cellNum++);
		List list=findPnrQualificationBySysno(sysno);
		if (list.size()<1) {
			int rowNumber=row.getRowNum();
			throw new RuntimeException("第"+rowNumber+"行,系统编号为\""+sysno+"\"的信息不存在");
		}
		return true;
	}

	public boolean doRestoreDeleteXLSFileData(HSSFRow row,HttpServletRequest request) {
		return true;
	}

	public boolean doRestoreSaveXLSFileData(HSSFRow row,HttpServletRequest request) {
		return true;
	}

	public boolean doRestoreUpdateXLSFileData(HSSFRow row,HttpServletRequest request) {
		return true;
	}
	/**
	 * 变更类型为新增归档
	 */
	public boolean doSaveXLSFileData(HSSFRow row, HttpServletRequest request)throws Exception {
		int cellNum=0;
		PnrQualification p=new PnrQualification();
		String id=XLSCellValidateUtil.checkAndGetDeptUUId(row, cellNum++);
		p.setRelatedDeptId(id);
		p.setQualifyName(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		p.setQualifyLevel(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		p.setIssueAuthority(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		p.setOutOfDate(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		p.setRemark(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		p.setSysno(createPnrQualificationSystemNo(""));
		p.setIsDeleted("0");
		this.save(p);
		return true;
	}
	/**
	 * 变更为新增类型校验
	 */
	public boolean doSaveXLSFileValidate(HSSFRow row) throws Exception {
		int cellNum=0;
		XLSCellValidateUtil.checkAndGetDeptId(row, cellNum++);//检验代维公司是否存在;
		XLSCellValidateUtil.checkIsNull(row, cellNum++);//
		XLSCellValidateUtil.checkIsNull(row, cellNum++);//
		XLSCellValidateUtil.checkIsNull(row, cellNum++);//
		XLSCellValidateUtil.checkDate(row, cellNum++);//
		XLSCellValidateUtil.checkLength(row, cellNum, 200, true);//
		return true;
	}
	/**
	 * 变更为修改类型归档
	 */
	public boolean doUpdateXLSFileData(HSSFRow row, HttpServletRequest request)throws Exception {
		int cellNum=0;
		String sysno=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
		List list=findPnrQualificationBySysno(sysno);
		if (list.size()<1) {
			int rowNumber=row.getRowNum();
			throw new RuntimeException("第"+rowNumber+"行,系统编号为\""+sysno+"\"的信息不存在");
		}
		PnrQualification p=(PnrQualification)list.get(0);
		/*当单元格内容不为空的时候才修改原来的值，为空不修改*/
		if (!XLSCellValidateUtil.cellIsBlank(row, cellNum++)) {
			p.setQualifyName(XLSCellValidateUtil.getCellStringValue(row, cellNum-1));
		}
		if (!XLSCellValidateUtil.cellIsBlank(row, cellNum++)) {
			p.setQualifyLevel(XLSCellValidateUtil.getCellStringValue(row, cellNum-1));
		}
		if (!XLSCellValidateUtil.cellIsBlank(row, cellNum++)) {
			p.setIssueAuthority(XLSCellValidateUtil.getCellStringValue(row, cellNum-1));
		}
		if (!XLSCellValidateUtil.cellIsBlank(row, cellNum++)) {
			p.setOutOfDate(XLSCellValidateUtil.getCellStringValue(row, cellNum-1));
		}
		if (!XLSCellValidateUtil.cellIsBlank(row, cellNum++)) {
			p.setRemark(XLSCellValidateUtil.getCellStringValue(row, cellNum-1));
		}
		this.save(p);
		return true;
	}
	/**
	 * 变更为修改类型校验
	 */
	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception {
		int cellNum=0;
		String sysno=XLSCellValidateUtil.checkIsNull(row, cellNum++);
		List list=findPnrQualificationBySysno(sysno);
		if (list.size()<1) {
			int rowNumber=row.getRowNum();
			throw new RuntimeException("第"+rowNumber+"行,系统编号为\""+sysno+"\"的信息不存在");
		}
		/*当单元格内容不为空的时候才校验，为空不校验*/
		if (!XLSCellValidateUtil.cellIsBlank(row, cellNum++)) {
			XLSCellValidateUtil.checkIsNull(row, cellNum-1);
		}
		if (!XLSCellValidateUtil.cellIsBlank(row, cellNum++)) {
			XLSCellValidateUtil.checkIsNull(row, cellNum-1);
		}
		if (!XLSCellValidateUtil.cellIsBlank(row, cellNum++)) {
			XLSCellValidateUtil.checkIsNull(row, cellNum-1);
		}
		if (!XLSCellValidateUtil.cellIsBlank(row, cellNum++)) {
			XLSCellValidateUtil.checkDate(row, cellNum-1);
		}
		XLSCellValidateUtil.checkLength(row, cellNum, 200, true);
		return true;
	}

	public XLSModel getXLSModel() {
		XLSModel xlsModel=new XLSModel(2,0,6,2,0,1,2,0,6);
		return xlsModel;
	}
	public String createPnrQualificationSystemNo(String id){
		List<PnrQualification> list =new ArrayList<PnrQualification>();
		String sysNo = "";
		int sysNoMax=0;
		//oracle对''和null一样的处理所以此处用length(sysno)<> 0
		list=pnrQualificationDao.findByHql("from PnrQualification  where sysno is not null and length(sysno)<> 0 order by sysno ");
		if(list.size()>0){
			String sysno=list.get(list.size()-1).getSysno();//获取最大的可用的编号
			sysno=sysno.replace("ZZ-", "");
			sysNoMax=Integer.parseInt(sysno);
		}else {
			 return sysNo="ZZ-0001";
		}
		String no = String.valueOf(sysNoMax + 1);
		if (no.length() == 1) {
			sysNo = "ZZ-000" + no;
		} else if (no.length() == 2) {
			sysNo = "ZZ-00" + no;
		} else if (no.length() == 3) {
			sysNo = "ZZ-0" + no;
		}else if (no.length() == 4) {
			sysNo = "ZZ-" + no;
		}
		return sysNo;
	}
	public List findPnrQualificationBySysno(String sysno) {
		List<PnrQualification> list =new ArrayList<PnrQualification>();
		list=pnrQualificationDao.findByHql("from PnrQualification where isDeleted='0' and sysno='"+sysno+"'");
		return list;
	}
	public boolean checkPnrQualificationIsOnly(String name){
		return false;
	}

	public boolean doLoadStaticSource() {
		// XXX Auto-generated method stub
		return false;
	}
	
}
