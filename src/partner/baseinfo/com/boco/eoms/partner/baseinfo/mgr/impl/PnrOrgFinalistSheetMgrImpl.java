package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.poi.hssf.usermodel.HSSFRow;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.partner.baseinfo.dao.IPnrOrgFinalistSheetDao;
import com.boco.eoms.partner.baseinfo.mgr.IPnrOrgFinalistSheetMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PnrOrgFinalistSheet;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;

public class PnrOrgFinalistSheetMgrImpl extends CommonGenericServiceImpl<PnrOrgFinalistSheet>
		implements IPnrOrgFinalistSheetMgr,PnrProcessService  {
	private IPnrOrgFinalistSheetDao pnrOrgFinalistSheetDao;
	
	public void setPnrOrgFinalistSheetDao(IPnrOrgFinalistSheetDao pnrOrgFinalistSheetDao) {
		this.setCommonGenericDao(pnrOrgFinalistSheetDao);
		this.pnrOrgFinalistSheetDao=pnrOrgFinalistSheetDao;
	}
	public IPnrOrgFinalistSheetDao getPnrOrgFinalistSheetDao() {
		return pnrOrgFinalistSheetDao;
	}
	/**
	 * 删除校验
	 */
	public boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception {
		int col = 0;
		String orgName=XLSCellValidateUtil.checkIsNull(row, col++);
		String sysno=XLSCellValidateUtil.checkIsNull(row, col++);
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
			.getInstance().getBean("commonSpringJdbcService");
		String sql="select * from pnr_org_finalist_sheet org where  org.orgName='"+orgName+"' and sysno='"+sysno+"'";
		 List<ListOrderedMap> orgList = jdbcService.queryForList(sql);
		if(orgList.size()==0)
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行"+(col)+"列验证出错,该条数据不存在");
		return true;
	}
	/**
	 * 新增数据校验
	 */
	public boolean doSaveXLSFileValidate(HSSFRow row) throws Exception {
		int cellNum = 0;
		 String orgName=XLSCellValidateUtil.checkIsNull(row, cellNum++);
		 String pro =XLSCellValidateUtil.checkIsNull(row, cellNum++);
		 String pros[] = pro.split("/");
		 String dbProsStr = "";
		 try {
			for(int i=0;i<pros.length;i++){
				 if (!"".equals(dbProsStr)) {
					dbProsStr+=",";
				}
				 dbProsStr+=XLSCellValidateUtil.name2Id(pros[i], "11225");
			 }
		} catch (RuntimeException e) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行第2列验证出错:"+e.getMessage());
		}
		 String finalistTime = XLSCellValidateUtil.checkDate(row, cellNum++);
		 String finalistEndTime = XLSCellValidateUtil.checkDate(row, cellNum++);
		 XLSCellValidateUtil.compareDate(row, cellNum-2, cellNum-1, "lessThan");
		 XLSCellValidateUtil.checkLength(row, cellNum, 200, true);
		 String hql="select * from pnr_org_finalist_sheet org where org.orgName = '"+orgName+"' and org.speciality like '%"
			+dbProsStr+"%' and org.finalistTime>='"+finalistTime+"' and org.finalistEndTime <='"+finalistEndTime+"'  and isdeleted='0' ";
			CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
			.getInstance().getBean("commonSpringJdbcService");
			List<ListOrderedMap> orgList = jdbcService.queryForList(hql);
		 if(orgList.size()!=0)
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:代维组织为\""+orgName+"\"的该条信息已经存在");
		return true;
	}
	/**
	 *更新校验
	 */
	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception {
		int cellNum=0;
		 CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
			.getInstance().getBean("commonSpringJdbcService");
		String sysno=XLSCellValidateUtil.checkIsNull(row, cellNum++);
		//校验sysno是否存在;
		String sql1="select * from pnr_org_finalist_sheet where sysno='"+sysno+"' and isdeleted='0'";
		List list=new ArrayList();
		list=jdbcService.queryForList(sql1);
		if (list==null||list.size()==0) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行第"+cellNum+"列验证出错,该系统编码不存在");
		}
		String orgName=XLSCellValidateUtil.checkIsNull(row, cellNum++);
		String sql2="select * from pnr_dept where name='"+orgName+"' and deleted='0'";
		list=jdbcService.queryForList(sql2);
		//校验组织名称是否存在;
		if (list==null||list.size()==0) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行第"+cellNum+"列验证出错,该组织名称不存在");
		}
		String professionalStr=XLSCellValidateUtil.checkIsNull(row, cellNum++);
		String[] professional=professionalStr.split("/");
		try {
			for (int i = 0; i < professional.length; i++) {
				String pro = professional[i];
				XLSCellValidateUtil.name2Id(pro, "11225");
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行第3列验证出错:"+e.getMessage());
		}
		XLSCellValidateUtil.checkDate(row, cellNum++);
		XLSCellValidateUtil.checkDate(row, cellNum++);
		XLSCellValidateUtil.compareDate(row, cellNum-2, cellNum-1, "lessThan");
		XLSCellValidateUtil.checkLength(row, cellNum++, 200, true);
		return true;
	}
	/**
	 * 新增归档
	 */
	public boolean doSaveXLSFileData(HSSFRow row, HttpServletRequest request)throws Exception {
		boolean flag=false;
		int cellNum=0;
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PnrOrgFinalistSheet pnrOrgFinalistSheet=new PnrOrgFinalistSheet();
		String orgName=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
		pnrOrgFinalistSheet.setOrgName(orgName);
		//代维专业
		 String professionalStr =XLSCellValidateUtil.getCellStringValue(row, cellNum++);
		 String[] pros=professionalStr.split("/");
		 String dbProsStr = "";
		for(int i=0;i<pros.length;i++){
			 if (!"".equals(dbProsStr)) {
				 dbProsStr+=",";
			}
			 dbProsStr+=XLSCellValidateUtil.name2Id(pros[i], "11225");
		 }
		pnrOrgFinalistSheet.setSpeciality(dbProsStr);
		pnrOrgFinalistSheet.setFinalistTime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		pnrOrgFinalistSheet.setFinalistEndTime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		pnrOrgFinalistSheet.setRemark(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		List list=new ArrayList();
		list=this.getPnrOrgFinalistSheet("");
		String organizationNo = "";
		int organization =list.size();
		String no = String.valueOf(organization + 1);
		if (no.length() == 1) {
			organizationNo = "RW-000" + no;
		} else if (no.length() == 2) {
			organizationNo = "RW-00" + no;
		} else if (no.length() == 3) {
			organizationNo = "RW-0" + no;
		}else if (no.length() == 4) {
			organizationNo = "RW-" + no;
		}
		pnrOrgFinalistSheet.setSysno(organizationNo);
		pnrOrgFinalistSheet.setAddUser(sessionInfo.getUserid());
		pnrOrgFinalistSheet.setAddTime(StaticMethod.getCurrentDateTime());
		PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
		list=partnerDeptMgr.getPartnerDepts("and name='"+orgName+"'");
		if (list.size()==0) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行第1列归档出错,组织名称\""+orgName+"\"已经不存在");
		}
		PartnerDept dept=(PartnerDept)list.get(0);
		pnrOrgFinalistSheet.setOrgId(StaticMethod.null2String(dept.getId()));
		pnrOrgFinalistSheet.setAreaId(StaticMethod.null2String(dept.getAreaId()));
		pnrOrgFinalistSheet.setOrgDeptId(StaticMethod.null2String(dept.getDeptMagId()));
		pnrOrgFinalistSheet.setIsdeleted("0");
		pnrOrgFinalistSheet.setIsEffected("0");
		save(pnrOrgFinalistSheet);
		return true;
	}
	/**
	 * 更新数据归档
	 */
	public boolean doUpdateXLSFileData(HSSFRow row, HttpServletRequest request)throws Exception {
		int cellNum=0;
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PnrOrgFinalistSheet pnrOrgFinalistSheet=new PnrOrgFinalistSheet();
		String addTime = (new Date()).toString();
		String sysno=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
		//校验sysno是否存在;
		String whereStr="and sysno='"+sysno+"' and isdeleted='0' ";
		List<PnrOrgFinalistSheet> list=getPnrOrgFinalistSheet(whereStr);
		if (list==null||list.size()==0) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行第"+cellNum+"列归档出错,该系统编码已经不存在");
		}
		pnrOrgFinalistSheet=list.get(0);
		pnrOrgFinalistSheet.setSysno(sysno);
		String orgName=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
		pnrOrgFinalistSheet.setOrgName(orgName);
		//校验组织名称是否存在;
		PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
		List list2=partnerDeptMgr.getPartnerDepts("and name='"+orgName+"'");
		if (list2.size()==0) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行第2列归档出错,组织名称\""+orgName+"\"已经不存在");
		}
		PartnerDept dept=(PartnerDept)list2.get(0);
		pnrOrgFinalistSheet.setOrgId(StaticMethod.null2String(dept.getId()));
		pnrOrgFinalistSheet.setAreaId(StaticMethod.null2String(dept.getAreaId()));
		pnrOrgFinalistSheet.setOrgDeptId(StaticMethod.null2String(dept.getDeptMagId()));
		pnrOrgFinalistSheet.setOrgName(orgName);
		//代维专业
		 String professionalStr =XLSCellValidateUtil.getCellStringValue(row, cellNum++);
		 String[] pros=professionalStr.split("/");
		 String dbProsStr = "";
		 for(int i=0;i<pros.length;i++){
			 if (!"".equals(dbProsStr)) {
				 dbProsStr+=",";
			}
			 dbProsStr+=XLSCellValidateUtil.name2Id(pros[i], "11225");
		 }
		pnrOrgFinalistSheet.setSpeciality(dbProsStr);
		pnrOrgFinalistSheet.setFinalistTime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		pnrOrgFinalistSheet.setFinalistEndTime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		pnrOrgFinalistSheet.setRemark(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		save(pnrOrgFinalistSheet);
		return true;
	}
	/**
	 * 删除数据归档
	 */
	public boolean doDeleteXLSFileData(HSSFRow row, HttpServletRequest request) throws Exception {
		String orgName=XLSCellValidateUtil.getCellStringValue(row, 0);
		String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		String whereStr="and orgName='"+orgName+"' and sysno='"+sysno+"'";
		List<PnrOrgFinalistSheet> list=getPnrOrgFinalistSheet(whereStr);
		if (list.size()==0) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错,该条数据已经不存在");
		}
		PnrOrgFinalistSheet pnrOrgFinalistSheet=list.get(0);
		pnrOrgFinalistSheet.setIsdeleted("1");
		pnrOrgFinalistSheetDao.save(pnrOrgFinalistSheet);
		return true;
	}
	public XLSModel getXLSModel() {
		return new XLSModel(2, 0, 5, 2, 0, 2, 2, 0, 6);
	}
	public boolean doRestoreDeleteXLSFileData(HSSFRow row,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean doRestoreUpdateXLSFileData(HSSFRow row,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean doRestoreSaveXLSFileData(HSSFRow row,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}
	public  String createOrgSysNo() {
		return null;
	}
	public List getPnrOrgFinalistSheet(String where) throws Exception {
		return pnrOrgFinalistSheetDao.getPnrOrgFinalistSheet(where);
	}
	public boolean doLoadStaticSource() {
		// XXX Auto-generated method stub
		return false;
	}
}
