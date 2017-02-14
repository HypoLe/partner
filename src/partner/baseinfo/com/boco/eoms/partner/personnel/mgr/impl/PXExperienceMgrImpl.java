package com.boco.eoms.partner.personnel.mgr.impl;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.util.PartnerUserConstants;
import com.boco.eoms.partner.baseinfo.util.PnrDeptAndUserConfigSetList;
import com.boco.eoms.partner.personnel.dao.PXExperienceDao;
import com.boco.eoms.partner.personnel.mgr.PXExperienceMgr;
import com.boco.eoms.partner.personnel.model.PXExperience;
import com.boco.eoms.partner.personnel.util.PartnerUserHander;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSToFormFileImport;
	/**
 * <p>
 * Title:人员培训经历管理
 * </p>
 * <p>
 * Description:人员培训经历管理
 * </p>
 * <p>
 * Jul 16, 2012 4:47:13 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class PXExperienceMgrImpl extends CommonGenericServiceImpl<PXExperience> implements
	PXExperienceMgr,PnrProcessService {
	private PXExperienceDao pxexperienceDao;

	public PXExperienceDao getPxexperienceDao() {
		return pxexperienceDao;
	}

	public void setPxexperienceDao(PXExperienceDao pxexperienceDao) {
		this.pxexperienceDao = pxexperienceDao;
		this.setCommonGenericDao(pxexperienceDao);
	}
	/**
	 * 移动管理员批量导入
	 */
	public ImportResult importFromFile(FormFile formFile,HttpServletRequest request) throws Exception {
		XLSToFormFileImport xlsFileImport=new XLSToFormFileImport(){
			public boolean doSaveRow2Data(HSSFRow row,	HttpServletRequest request) throws Exception {
				PXExperience pxExperience=new PXExperience();
				validateEachRow(row, pxExperience, "add");
				pxExperience=getRowObject(row, pxExperience, request, "add");
				pxExperience.setSysno(createPxExSystemNo(""));
				save(pxExperience);
				return true;
			}
			public XLSModel getXLSModel() {
				return new  XLSModel(2, 0, 6, 0, 0, 0,0, 0,0);
			}
//			@Override
//			public PreparedStatement addPsBach(PreparedStatement ps,HSSFRow row, HttpServletRequest request) throws Exception {
//				PXExperience object=new PXExperience();
//				object=getRowObject(row, object, request, "add");
//				ps.setString(1,CommonSqlHelper.generateUUID());
//				ps.setString(2,StaticMethod.null2String(object.getWorkerid()));
//				ps.setString(3,StaticMethod.null2String(object.getStarttime()));
//				ps.setString(4,StaticMethod.null2String(object.getEndtime()));
//				ps.setString(5,StaticMethod.null2String(object.getContent()));
//				ps.setString(6,StaticMethod.null2String(object.getScore()));
//				ps.setString(7,StaticMethod.null2String(object.getMemo()));
//				ps.setString(8,StaticMethod.null2String(object.getLastedittime()));
//				ps.setString(9,StaticMethod.null2String(object.getLastediterid()));
//				ps.setString(10,StaticMethod.null2String(object.getLasteditername()));
//				ps.setString(11,StaticMethod.null2String(object.getAdduser()));
//				ps.setString(12,StaticMethod.null2String(object.getAdddept()));
//				ps.setString(13,StaticMethod.null2String(object.getAddtime()));
//				ps.setString(14,StaticMethod.null2String(object.getIsdelete()));
//				ps.setString(15,StaticMethod.null2String(object.getWorkername()));
//				ps.setString(16,StaticMethod.null2String(object.getDays()));
//				ps.setString(17,StaticMethod.null2String(object.getDeptid()));
//				ps.setString(18,StaticMethod.null2String(object.getAreaid()));
//				ps.setString(19,StaticMethod.null2String(object.getImagepath()));
//				ps.setString(20,StaticMethod.null2String(object.getPersonCardNo()));
//				ps.addBatch();
//				return ps;
//			}
//			@Override
//			public boolean doValidate(HSSFRow row, HttpServletRequest request,
//					String type) throws Exception {
//				PXExperience px=new PXExperience();
//				validateEachRow(row, px, "add");
//				return true;
//			}
			@Override
			public String getBachSql() throws Exception {
				String sql="INSERT INTO  panter_pxexperience" +
						"(id, workerid, starttime, endtime, content, score, memo, lastedittime, lastediterid, " +
						"lasteditername, adduser, adddept, addtime, isdelete, workername, days, deptid, areaid, imagepath, person_card_no)" +
						"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
			public PreparedStatement validateAndPrepareStatement(HSSFRow row,	HttpServletRequest request, String type,
					PreparedStatement ps, String validateType) throws Exception {
				String operationTime = (new Date()).toString();
				TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
				int cellNum=0;
				 PartnerUser user;
				 if ("personCardNo".equals(validateType)) {
					 user=XLSCellValidateUtil.checkPersonCardNoIsExist(row, cellNum++, false);//采用身份证校验
				}else {
					user=XLSCellValidateUtil.checkByUserIdAndGetUser(row, cellNum++);
				}
				String starttime=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
				String endtime=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
				XLSCellValidateUtil.compareDate(row, cellNum-2, cellNum-1, "lessThan");
				String content=XLSCellValidateUtil.checkLength(row, cellNum++, 200, false);
				String score=XLSCellValidateUtil.checkLength(row, cellNum++, 10,true);
				String memo=XLSCellValidateUtil.checkLength(row, cellNum++, 200,true);
				String days=CommonUtils.getDisDays(starttime, endtime)+"";
				ps.setString(1,CommonSqlHelper.generateUUID());
				ps.setString(2,StaticMethod.null2String(user.getUserId()));
				ps.setString(3,starttime);
				ps.setString(4,endtime);
				ps.setString(5,content);
				ps.setString(6,score);
				ps.setString(7,memo);
				ps.setString(8,operationTime);
				ps.setString(9,StaticMethod.null2String(sessionInfo.getUserid()));
				ps.setString(10,StaticMethod.null2String(sessionInfo.getUsername()));
				ps.setString(11,StaticMethod.null2String(sessionInfo.getUsername()));
				ps.setString(12,StaticMethod.null2String(sessionInfo.getDeptname()));
				ps.setString(13,StaticMethod.null2String(operationTime));
				ps.setString(14,"0");
				ps.setString(15,StaticMethod.null2String(user.getName()));
				ps.setString(16,days);
				ps.setString(17,StaticMethod.null2String(user.getDeptId()));
				ps.setString(18,StaticMethod.null2String(user.getAreaId()));
				ps.setString(19,"");
				ps.setString(20,StaticMethod.null2String(user.getPersonCardNo()));
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
	* @Description: TODO
	* @param 
	* @Time:Nov 25, 2012-3:54:34 PM
	* @Author:fengguangping
	* @return PXExperience    返回类型 
	* @throws
	 */
	public PXExperience getRowObject(HSSFRow row,PXExperience px,HttpServletRequest request,String type){
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
		px.setWorkerid(StaticMethod.null2String(user.getUserId()));
		px.setWorkername(StaticMethod.null2String(user.getName()));
		px.setDeptid(StaticMethod.null2String(user.getDeptId()));
		px.setAreaid(StaticMethod.null2String(user.getAreaId()));
		px.setPersonCardNo(StaticMethod.null2String(user.getPersonCardNo()));
		if ("update".equals(type)) {
			px.setSysno(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			px.setLastediterid(sessionInfo.getUserid());
			px.setLasteditername(sessionInfo.getUsername());
			px.setLastedittime(operationTime);
		}else if ("add".equals(type)) {
			px.setAdduser(sessionInfo.getUsername());
			px.setAdddept(sessionInfo.getDeptname());
			px.setAddtime(operationTime);
		}
		px.setStarttime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		px.setEndtime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		px.setContent(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		px.setScore(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		px.setMemo(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		String days=CommonUtils.getDisDays(px.getStarttime(), px.getEndtime())+"";
		px.setDays(days);
		px.setIsdelete("0");
		return px;
	}
	/**
	 * 
	* @Title: validateEachRow 
	* @Description: 验证当前行
	* @param 
	* @Time:Nov 25, 2012-4:00:21 PM
	* @Author:fengguangping
	* @return PXExperience    返回类型 
	* @throws
	 */
	public PXExperience validateEachRow (HSSFRow row,PXExperience px,String type){
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
		px.setWorkerid(StaticMethod.null2String(user.getUserId()));
		px.setWorkername(StaticMethod.null2String(user.getName()));
		if ("update".equals(type)) {
			px.setSysno(XLSCellValidateUtil.checkIsNull(row, cellNum++));
		}
		px.setStarttime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		px.setEndtime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		XLSCellValidateUtil.compareDate(row, cellNum-2, cellNum-1, "lessThan");
		px.setContent(XLSCellValidateUtil.checkLength(row, cellNum++, 200, false));
		px.setScore(XLSCellValidateUtil.checkLength(row, cellNum++, 10,true));
		px.setMemo(XLSCellValidateUtil.checkLength(row, cellNum++, 200,true));
		return px;
	}
	
	/*
	 * 删除数据校验
	 * (non-Javadoc
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doDeleteXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	public boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception {
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 List<PXExperience> pxList =new ArrayList<PXExperience>();
		 String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		 String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 XLSCellValidateUtil.checkPersonCardNoIsExist(row, 0, false);
			 pxList=this.pxexperienceDao.findByHql("from PXExperience px where px.personCardNo ='"+id+"' and px.sysno='"+sysno+"' and px.isdelete='0'");
			 if(pxList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:身份证为\""+id+"\"的该条信息不存在");
			 }
		 }else {
			 XLSCellValidateUtil.checkByUserIdAndGetUser(row, 0);
			 pxList=this.pxexperienceDao.findByHql("from PXExperience px where px.workerid ='"+id+"' and px.sysno='"+sysno+"' and px.isdelete='0'");
			 if(pxList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:用户ID为\""+id+"\"的该条信息不存在");
			 }
		}
		return true;
	}
	/*
	 * 新增数据验证
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doSaveXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	public boolean doSaveXLSFileValidate(HSSFRow row) throws Exception {
		PXExperience px=new PXExperience();
		px=validateEachRow(row, px, "add");
		String startDate=px.getStarttime();
		String endDate=px.getEndtime();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 List<PXExperience> pxList =new ArrayList<PXExperience>();
		 String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 pxList=	 this.pxexperienceDao.findByHql("from PXExperience px where px.personCardNo ='"
					 +id+"' and px.starttime='"+startDate+"'  and px.endtime='"+endDate+"'  and px.isdelete='0' ");
			 if(pxList.size()>0)
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错: "+"身份证为\""+id+"" +"\"的该条信息已经存在");
		 }else {
			 pxList=	 this.pxexperienceDao.findByHql("from PXExperience px where px.workerid ='"
					 +id+"' and px.starttime='"+startDate+"'  and px.endtime='"+endDate+"'  and px.isdelete='0' ");
			 if(pxList.size()>0)
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错: "+"用户ID为\""+id+"" +"\"的该条信息已经存在");
		}
		 return true;
	}

	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception {
		PXExperience px=new PXExperience();
		px=validateEachRow(row, px, "update");
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 List<PXExperience> pxList =new ArrayList<PXExperience>();
		 String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		 String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 pxList=this.pxexperienceDao.findByHql("from PXExperience px where px.personCardNo ='"+id+"' and px.sysno='"+sysno+"' and px.isdelete='0'");
			 if(pxList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:身份证为\""+id+"\"的该条信息不存在");
			 }
		 }else {
			 pxList=this.pxexperienceDao.findByHql("from PXExperience px where px.workerid ='"+id+"' and px.sysno='"+sysno+"' and px.isdelete='0'");
			 if(pxList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:用户ID为\""+id+"\"的该条信息不存在");
			 }
		}
		 return true;
	}
/*
 * 新增数据归档
 * (non-Javadoc)
 * @see com.boco.eoms.partner.process.service.PnrProcessService#doSaveXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
 */
	public boolean doSaveXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		PXExperience px = new PXExperience();
		px=getRowObject(row, px, request, "add");
		//px.setSysno(createPxExSystemNo(""));
		this.pxexperienceDao.save(px);	
		return true;
	}
/*
 * 修改数据归档
 * (non-Javadoc)
 * @see com.boco.eoms.partner.process.service.PnrProcessService#doUpdateXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
 */
	public boolean doUpdateXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		PXExperience px=new PXExperience();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 List<PXExperience> pxList =new ArrayList<PXExperience>();
		 String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		 String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 XLSCellValidateUtil.checkPersonCardNoIsExist(row, 0, false);
			 pxList=this.pxexperienceDao.findByHql("from PXExperience px where px.personCardNo ='"+id+"' and px.sysno='"+sysno+"' and px.isdelete='0'");
			 if(pxList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错:身份证为\""+id+"\"的该条信息不存在");
			 }
		 }else {
			 XLSCellValidateUtil.checkByUserIdAndGetUser(row, 0);
			 pxList=this.pxexperienceDao.findByHql("from PXExperience px where px.workerid ='"+id+"' and px.sysno='"+sysno+"' and px.isdelete='0'");
			 if(pxList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错:用户ID为\""+id+"\"的该条信息不存在");
			 }
		}
		 px=pxList.get(0);
		 px=getRowObject(row, px, request,"update");
		 pxexperienceDao.save(px);
		return true;
	}
/*
 * 删除数据归档
 * (non-Javadoc)
 * @see com.boco.eoms.partner.process.service.PnrProcessService#doDeleteXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
 */
	public boolean doDeleteXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PXExperience px = new PXExperience();
		String operationTime = (new Date()).toString();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 List<PXExperience> pxList =new ArrayList<PXExperience>();
		 String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		 String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 XLSCellValidateUtil.checkPersonCardNoIsExist(row, 0, false);
			 pxList=this.pxexperienceDao.findByHql("from PXExperience px where px.personCardNo ='"+id+"' and px.sysno='"+sysno+"' and px.isdelete='0'");
			 if(pxList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错:身份证为\""+id+"\"的该条信息不存在");
			 }
		 }else {
			 XLSCellValidateUtil.checkByUserIdAndGetUser(row, 0);
			 pxList=this.pxexperienceDao.findByHql("from PXExperience px where px.workerid ='"+id+"' and px.sysno='"+sysno+"' and px.isdelete='0'");
			 if(pxList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错:用户ID为\""+id+"\"的该条信息不存在");
			 }
		}
		px=pxList.get(0);
		px.setIsdelete("1");
		px.setAdduser(sessionInfo.getUsername());
		px.setAdddept(sessionInfo.getDeptname());
		px.setAddtime(operationTime);
		 pxexperienceDao.save(px);
		return true;
	}
	public XLSModel getXLSModel() {
		return new  XLSModel(2, 0, 6, 2, 0, 2, 2, 0,7);
	}
	public void removeAllByUserId(String userid) {
		List<PXExperience> list = pxexperienceDao.findByHql("from PXExperience px where px.isdelete='0' and  px.workerid = '"+userid+"'");
		if (list.size()>0) {
			for (PXExperience pxExperience : list) {
				pxExperience.setIsdelete("1");
				pxexperienceDao.save(pxExperience);
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
	public List getPXExperienceByUserid(String userid) throws Exception {
		List<PXExperience> list = new ArrayList<PXExperience>();
		list=pxexperienceDao.findByHql("from PXExperience px where px.isdelete='0' and px.workerid = '"+userid+"'");
		return list;
	}

	public String createPxExSystemNo(String id) {
		String sysNo=PartnerUserHander.creatSysno(PartnerUserConstants.PXEXPERIENCE_TABLENAME,PartnerUserConstants.PXEXPERIENCE_SYSNO_FLAG);
		return sysNo;
	}

	public boolean doLoadStaticSource() {
		// XXX Auto-generated method stub
		return false;
	}

}
