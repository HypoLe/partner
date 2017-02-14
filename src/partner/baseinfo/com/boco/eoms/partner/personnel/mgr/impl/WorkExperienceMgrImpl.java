package com.boco.eoms.partner.personnel.mgr.impl;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.util.PartnerUserConstants;
import com.boco.eoms.partner.baseinfo.util.PnrDeptAndUserConfigSetList;
import com.boco.eoms.partner.personnel.dao.WorkExperienceDao;
import com.boco.eoms.partner.personnel.mgr.WorkExperienceMgr;
import com.boco.eoms.partner.personnel.model.WorkExperience;
import com.boco.eoms.partner.personnel.util.PartnerUserHander;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSToFormFileImport;
/**
 * 
* @ClassName WorkExperienceMgrImpl
 * @Description TODO
 * @author  fengguangping
 * @date Nov 29, 2012
 */
public class WorkExperienceMgrImpl extends CommonGenericServiceImpl<WorkExperience> implements
	WorkExperienceMgr,PnrProcessService {
	private WorkExperienceDao workExperienceDao;
	public WorkExperienceDao getWorkExperienceDao() {
		return workExperienceDao;
	}
	public void setWorkExperienceDao(WorkExperienceDao workExperienceDao) {
		this.workExperienceDao = workExperienceDao;
		this.setCommonGenericDao(workExperienceDao);
	}
	/**
	 * 移动管理员批量导入
	 */
	public ImportResult importFromFile(FormFile formFile,HttpServletRequest request) throws Exception {
		XLSToFormFileImport xlsFileImport=new XLSToFormFileImport(){
			public boolean doSaveRow2Data(HSSFRow row,	HttpServletRequest request) throws Exception {
				WorkExperience workExperience=new WorkExperience();
				validateEachRow(row, workExperience, "add");
				workExperience=getRowObject(row, workExperience, request, "add");
				workExperience.setSysno(createWorExSystemNo(""));
				save(workExperience);
				return true;
			}
			public XLSModel getXLSModel() {
				return new  XLSModel(2, 0,6, 0, 0,0, 0, 0,0);
			}
//			@Override
//			public PreparedStatement addPsBach(PreparedStatement ps,
//					HSSFRow row, HttpServletRequest request) throws Exception {
//				WorkExperience workE=new WorkExperience();
//				workE=getRowObject(row, workE, request, "add");
//				ps.setString(2,StaticMethod.null2String(workE.getWorkerid()));
//				ps.setString(3,StaticMethod.null2String(workE.getEntryTime()));
//				ps.setString(4,StaticMethod.null2String(workE.getLeaveTime()));
//				ps.setString(5,StaticMethod.null2String(workE.getCompany()));
//				ps.setString(6,StaticMethod.null2String(workE.getDuty()));
//				ps.setString(7,StaticMethod.null2String(workE.getMemo()));
//				ps.setString(8,StaticMethod.null2String(workE.getLastedittime()));
//				ps.setString(9,StaticMethod.null2String(workE.getLastediterid()));
//				ps.setString(10,StaticMethod.null2String(workE.getLasteditername()));
//				ps.setString(11,StaticMethod.null2String(workE.getAdduser()));
//				ps.setString(12,StaticMethod.null2String(workE.getAdddept()));
//				ps.setString(13,StaticMethod.null2String(workE.getAddtime()));
//				ps.setString(14,StaticMethod.null2String(workE.getIsdelete()));
//				ps.setString(15,StaticMethod.null2String(workE.getWorkername()));
//				ps.setString(16,StaticMethod.null2String(workE.getAreaid()));
//				ps.setString(17,StaticMethod.null2String(workE.getDeptid()));
//				ps.setString(18,StaticMethod.null2String(workE.getPersonCardNo()));
//				ps.addBatch();
//				return ps;
//			}
//			@Override
//			public boolean doValidate(HSSFRow row, HttpServletRequest request,
//					String type) throws Exception {
//				WorkExperience workE=new WorkExperience();
//				validateEachRow(row, workE, "add");
//				return true;
//			}
			@Override
			public String getBachSql() throws Exception {
				String sql="INSERT INTO  partner_workexperience" +
						"(id, workerid, entrytime, leavetime, company, duty, memo, lastedittime, lastediterid, " +
						"lasteditername, adduser, adddept, addtime, isdelete, workername, areaid, deptid, person_card_no) " +
						"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				return sql;
			}
			@Override
			public boolean getWay() throws Exception {
				return false;
			}
			@Override
			public void loadStaticSource() throws Exception {
			}
			@Override
			public PreparedStatement validateAndPrepareStatement(HSSFRow row,HttpServletRequest request, String type,
					PreparedStatement ps, String validateType) throws Exception {
				int cellNum=0;
				String operationTime = (new Date()).toString();
				TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
				PartnerUser user=new PartnerUser();
				 if ("personCardNo".equals(validateType)) {
					 user=XLSCellValidateUtil.checkPersonCardNoIsExist(row, cellNum++, false);//采用身份证校验
				}else {
					user=XLSCellValidateUtil.checkByUserIdAndGetUser(row, cellNum++);
				}
				String entryTime=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
				String leaveTime=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
				XLSCellValidateUtil.compareDate(row, cellNum-2, cellNum-1, "lessThan");
				String company=XLSCellValidateUtil.checkLength(row, cellNum++, 50,false);
				String duty=XLSCellValidateUtil.checkLength(row, cellNum++,50,true);
				String memo=XLSCellValidateUtil.checkLength(row,cellNum++,200);
				ps.setString(1,CommonSqlHelper.generateUUID());
				ps.setString(2,StaticMethod.null2String(user.getUserId()));
				ps.setString(3,entryTime);
				ps.setString(4,leaveTime);
				ps.setString(5,company);
				ps.setString(6,duty);
				ps.setString(7,memo);
				ps.setString(8,StaticMethod.null2String(operationTime));
				ps.setString(9,StaticMethod.null2String(sessionInfo.getUserid()));
				ps.setString(10,StaticMethod.null2String(sessionInfo.getUsername()));
				ps.setString(11,StaticMethod.null2String(sessionInfo.getUsername()));
				ps.setString(12,StaticMethod.null2String(sessionInfo.getDeptid()));
				ps.setString(13,StaticMethod.null2String(operationTime));
				ps.setString(14,"0");
				ps.setString(15,StaticMethod.null2String(user.getName()));
				ps.setString(16,StaticMethod.null2String(user.getAreaId()));
				ps.setString(17,StaticMethod.null2String(user.getDeptId()));
				ps.setString(18,StaticMethod.null2String(user.getPersonCardNo()));
				ps.addBatch();
				return null;
			}
		};
		ImportResult result=xlsFileImport.xlsFileValidate(formFile,request);
		return result;
	}
	/**
	 * 
	* @Title: getRowObject 
	* @Description:将excel的当前行解析为一个model
	* @param 
	* @Time:Nov 25, 2012-2:54:34 PM
	* @Author:fengguangping
	* @return Reward    返回类型 
	* @throws
	 */
	public WorkExperience  getRowObject(HSSFRow row,WorkExperience workExperience,HttpServletRequest request,String type)  throws Exception{
		int cellNum=0;
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String operationTime = (new Date()).toString();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 user=XLSCellValidateUtil.checkPersonCardNoIsExist(row, cellNum++, false);//采用身份证校验
		}else {
			user=XLSCellValidateUtil.checkByUserIdAndGetUser(row, cellNum++);
		}
		workExperience.setWorkerid(StaticMethod.null2String(user.getUserId()));
		workExperience.setWorkername(StaticMethod.null2String(user.getName()));
		workExperience.setDeptid(StaticMethod.null2String(user.getDeptId()));
		workExperience.setAreaid(StaticMethod.null2String(user.getAreaId()));
		workExperience.setPersonCardNo(StaticMethod.null2String(user.getPersonCardNo()));
		if ("update".equals(type)) {
			workExperience.setSysno(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			workExperience.setLastediterid(sessionInfo.getUserid());
			workExperience.setLasteditername(sessionInfo.getUsername());
			workExperience.setLastedittime(operationTime);
		}else if ("add".equals(type)) {
			workExperience.setAdduser(sessionInfo.getUsername());
			workExperience.setAdddept(sessionInfo.getDeptname());
			workExperience.setAddtime(operationTime);
		}
		workExperience.setEntryTime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		workExperience.setLeaveTime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		workExperience.setCompany(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		workExperience.setDuty(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		workExperience.setMemo(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		workExperience.setIsdelete("0");
		return  workExperience;
	}
	/**
	 * 
	* @Title: validateEachRow 
	* @Description: 验证数据每一行是否符合规范要求，不包含任何的逻辑；
	* @param 
	* @Time:Nov 25, 2012-2:57:18 PM
	* @Author:fengguangping
	* @return WorkExperience    返回类型 
	* @throws
	 */
	public WorkExperience validateEachRow (HSSFRow row,WorkExperience workExperience,String type) throws Exception{
		int cellNum=0;
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 user=XLSCellValidateUtil.checkPersonCardNoIsExist(row, cellNum++, false);//采用身份证校验
		}else {
			user=XLSCellValidateUtil.checkByUserIdAndGetUser(row, cellNum++);
		}
		workExperience.setWorkerid(StaticMethod.null2String(user.getUserId()));
		workExperience.setWorkername(StaticMethod.null2String(user.getName()));
		workExperience.setDeptid(StaticMethod.null2String(user.getDeptId()));
		workExperience.setAreaid(StaticMethod.null2String(user.getAreaId()));
		workExperience.setPersonCardNo(StaticMethod.null2String(user.getPersonCardNo()));
		if ("update".equals(type)) {
			workExperience.setSysno(XLSCellValidateUtil.checkIsNull(row, cellNum++));
		}
		workExperience.setEntryTime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		workExperience.setLeaveTime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		XLSCellValidateUtil.compareDate(row, cellNum-2, cellNum-1, "lessThan");
		workExperience.setCompany(XLSCellValidateUtil.checkLength(row, cellNum++, 50,false));
		workExperience.setDuty(XLSCellValidateUtil.checkLength(row, cellNum++,50,true));
		workExperience.setMemo(XLSCellValidateUtil.checkLength(row,cellNum++,200));
		return  workExperience;
	}
	/*
	 * 删除数据校验
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doDeleteXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	public boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception {
		String personCardNo=XLSCellValidateUtil.getCellStringValue(row, 0);
		String sysno=XLSCellValidateUtil.checkIsNull(row, 1);
		List<WorkExperience> workExperiencedyList =new ArrayList<WorkExperience>();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 XLSCellValidateUtil.checkPersonCardNoIsExist(row,0, false);//采用身份证校验
			 workExperiencedyList=workExperienceDao.findByHql("from WorkExperience w  where w.personCardNo ='"+personCardNo+"' and w.sysno='"+sysno+"' and w.isdelete='0' ");
			 if(workExperiencedyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错: 身份证为\""+personCardNo+"\"的该条记录不存在");
			 }
		}else {
			XLSCellValidateUtil.checkByUserIdAndGetUser(row,0);
			workExperiencedyList=workExperienceDao.findByHql("from WorkExperience w  where w.workerid ='"+personCardNo+"' and w.sysno='"+sysno+"' and w.isdelete='0' ");
			 if(workExperiencedyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错: 用户ID为为\""+personCardNo+"\"的该条记录不存在");
			 }
		}
		
		return true;
	}
	/*
	 *新增数据校验 
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doSaveXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	public boolean doSaveXLSFileValidate(HSSFRow row) throws Exception {
		WorkExperience workExperience=new WorkExperience();
		workExperience=validateEachRow(row, workExperience,"add");
		String personCardNo=workExperience.getPersonCardNo();
		String workerid=workExperience.getWorkerid();
		String starttime=workExperience.getEntryTime();
		String endtime=workExperience.getLeaveTime();
		String company=workExperience.getCompany();
		List<WorkExperience> workExperiencedyList =new ArrayList<WorkExperience>();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 workExperiencedyList =workExperienceDao.findByHql("from WorkExperience w  where w.personCardNo ='"+personCardNo+"' and w.company='"
					 +company+"' and w.entryTime='"+starttime+"' and w.leaveTime='"+endtime+"' and w.isdelete='0' ");
			 if(workExperiencedyList.size()>0)
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行的信息已经存在");
		 }else {
			 workExperiencedyList =workExperienceDao.findByHql("from WorkExperience w  where w.workerid ='"+workerid+"' and w.company='"
					 +company+"' and w.entryTime='"+starttime+"' and w.leaveTime='"+endtime+"' and w.isdelete='0' ");
			 if(workExperiencedyList.size()>0)
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行的信息已经存在");
		}
		return true;
	}
	/*
	 * 修改数据校验
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doUpdateXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception {
		WorkExperience workExperience=new WorkExperience();
		workExperience=validateEachRow(row, workExperience,"update");
		String personCardNo=workExperience.getPersonCardNo();
		String sysno=workExperience.getSysno();
		String workerid=workExperience.getWorkerid();
		List<WorkExperience> workExperiencedyList =new ArrayList<WorkExperience>();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 workExperiencedyList =workExperienceDao.findByHql("from WorkExperience w  where w.personCardNo ='"+personCardNo+"' and w.sysno='"+sysno+"'  " +
				"and w.isdelete='0' ");
			 if(workExperiencedyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错: 身份证为\""+personCardNo+"\"的该条记录不存在");
			 }
		 }else {
			 workExperiencedyList =workExperienceDao.findByHql("from WorkExperience w  where w.workerid ='"+workerid+"' and w.sysno='"+sysno+"'  " +
				"and w.isdelete='0' ");
			 if(workExperiencedyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错: 用户ID为\""+workerid+"\"的该条记录不存在");
			 }
		}
		return true;
	}
	public boolean doSaveXLSFileData(HSSFRow row, HttpServletRequest request) throws Exception {
		WorkExperience workExperience = new WorkExperience();
		workExperience=getRowObject(row, workExperience, request, "add");
		//workExperience.setSysno(createWorExSystemNo(""));
		this.workExperienceDao.save(workExperience);
		return true;
	}

	public boolean doUpdateXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		WorkExperience workExperience=new WorkExperience();
		String personCardNo=XLSCellValidateUtil.getCellStringValue(row, 0);
		String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		List<WorkExperience> workExperiencedyList =new ArrayList<WorkExperience>();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 workExperiencedyList =workExperienceDao.findByHql("from WorkExperience w  where w.personCardNo ='"+personCardNo+"' and w.sysno='"+sysno+"'  " +
				"and w.isdelete='0' ");
			 if(workExperiencedyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错: 身份证为\""+personCardNo+"\"的该条记录不存在");
			 }
		 }else {
			 workExperiencedyList =workExperienceDao.findByHql("from WorkExperience w  where w.workerid ='"+personCardNo+"' and w.sysno='"+sysno+"'  " +
				"and w.isdelete='0' ");
			 if(workExperiencedyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错: 用户ID为\""+personCardNo+"\"的该条记录不存在");
			 }
		}
		 workExperience=workExperiencedyList.get(0);
		 workExperience=getRowObject(row, workExperience,request,"update");
		this.workExperienceDao.save(workExperience);	
		return true;
	}
	/*
	 * 删除数据归档
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doDeleteXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doDeleteXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		WorkExperience workExperience = new WorkExperience();
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String operationTime = (new Date()).toString();
		String personCardNo=XLSCellValidateUtil.getCellStringValue(row, 0);
		String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		List<WorkExperience> workExperiencedyList =new ArrayList<WorkExperience>();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 workExperiencedyList =workExperienceDao.findByHql("from WorkExperience w  where w.personCardNo ='"+personCardNo+"' and w.sysno='"+sysno+"'  " +
				"and w.isdelete='0' ");
			 if(workExperiencedyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错: 身份证为\""+personCardNo+"\"的该条记录不存在");
			 }
		 }else {
			 workExperiencedyList =workExperienceDao.findByHql("from WorkExperience w  where w.workerid ='"+personCardNo+"' and w.sysno='"+sysno+"'  " +
				"and w.isdelete='0' ");
			 if(workExperiencedyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错: 用户ID为\""+personCardNo+"\"的该条记录不存在");
			 }
		}
		workExperience=workExperiencedyList.get(0);
		workExperience.setIsdelete("1");
		workExperience.setLastediterid(sessionInfo.getUserid());
		workExperience.setLasteditername(sessionInfo.getUsername());
		workExperience.setLastedittime(operationTime);
		this.workExperienceDao.save(workExperience);
		return true;
	}
	public XLSModel getXLSModel() {
		return new  XLSModel(2, 0, 6, 2,0,2, 2, 0, 7);
	}
	/**
	 * 删除当前用户的所有的工作经历信息
	 */
	public void removeAllByUserId(String userid) {
		List<WorkExperience> list = workExperienceDao.findByHql("from WorkExperience s where s.workerid = '"+userid+"' and s.isdelete='0'");
		if (list.size()>0) {
			for (WorkExperience workExperience : list) {
				workExperience.setIsdelete("1");
				workExperienceDao.save(workExperience);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doRestoreDeleteXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doRestoreDeleteXLSFileData(HSSFRow row,HttpServletRequest request) {
		return true;
	}
	/*
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doRestoreUpdateXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doRestoreUpdateXLSFileData(HSSFRow row,HttpServletRequest request) {
		return true;
	}
	/*
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doRestoreSaveXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doRestoreSaveXLSFileData(HSSFRow row,HttpServletRequest request) {
		return true;
	}
	public String createWorExSystemNo(String id) {
		String sysNo=PartnerUserHander.creatSysno(PartnerUserConstants.WORKEXPERIENCE_TABLENAME,PartnerUserConstants.WORKEXPERIENCE_SYSNO_FLAG);
		return sysNo;
	}
	public boolean doLoadStaticSource() {
		// XXX Auto-generated method stub
		return false;
	}
}
