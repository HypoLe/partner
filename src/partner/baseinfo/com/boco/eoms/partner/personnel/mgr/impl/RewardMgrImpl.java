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
import com.boco.eoms.partner.personnel.dao.RewardDao;
import com.boco.eoms.partner.personnel.mgr.RewardMgr;
import com.boco.eoms.partner.personnel.model.Reward;
import com.boco.eoms.partner.personnel.util.PartnerUserHander;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSToFormFileImport;
	/**
 * <p>
 * Title:人员奖励管理
 * </p>
 * <p>
 * Description:人员奖励管理
 * </p>
 * <p>
 * Jul 12, 2012 2:45:13 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class RewardMgrImpl extends CommonGenericServiceImpl<Reward> implements
		RewardMgr,PnrProcessService {
	private RewardDao rewardDao;

	public RewardDao getRewardDao() {
		return rewardDao;
	}
	public void setRewardDao(RewardDao rewardDao) {
		this.rewardDao = rewardDao;
		this.setCommonGenericDao(rewardDao);
	}
	
	/**
	 * 移动管理员批量导入
	 */
	public ImportResult importFromFile(FormFile formFile,HttpServletRequest request) throws Exception {
		XLSToFormFileImport xlsFileImport=new XLSToFormFileImport(){
			public boolean doSaveRow2Data(HSSFRow row,	HttpServletRequest request) throws Exception {
				Reward reward=new Reward();
				validateEachRow(row, reward, "add");
				reward=getRowObject(row, reward, request, "add");
				String sysno=createRewardSystemNo("");
				reward.setSysno(sysno);
				save(reward);
				return true;
			}
			public XLSModel getXLSModel() {
				return new  XLSModel(2, 0,5, 0, 0,0, 0, 0,0);
			}
//			@Override
//			public PreparedStatement addPsBach(PreparedStatement ps,HSSFRow row, HttpServletRequest request) throws Exception {
//				Reward reward=new Reward();
//				reward=getRowObject(row, reward, request, "add");
//				ps.setString(1,StaticMethod.null2String(reward.getWorkerid()));
//				ps.setString(2,CommonSqlHelper.generateUUID());
//				ps.setString(3,StaticMethod.null2String(reward.getReward()));
//				ps.setString(4,StaticMethod.null2String(reward.getMemo()));
//				ps.setString(5,StaticMethod.null2String(reward.getLastedittime()));
//				ps.setString(6,StaticMethod.null2String(reward.getLastediterid()));
//				ps.setString(7,StaticMethod.null2String(reward.getLasteditername()));
//				ps.setString(8,StaticMethod.null2String(reward.getAdduser()));
//				ps.setString(9,StaticMethod.null2String(reward.getAdddept()));
//				ps.setString(10,StaticMethod.null2String(reward.getAddtime()));
//				ps.setString(11,StaticMethod.null2String(reward.getIsdelete()));
//				ps.setString(12,StaticMethod.null2String(reward.getWorkername()));
//				ps.setString(13,StaticMethod.null2String(reward.getDeptid()));
//				ps.setString(14,StaticMethod.null2String(reward.getAreaid()));
//				ps.setString(15,StaticMethod.null2String(reward.getAwardtime()));
//				ps.setString(16,StaticMethod.null2String(reward.getAwarddept()));
//				ps.setString(17,StaticMethod.null2String(reward.getImagepath()));
//				ps.setString(18,StaticMethod.null2String(reward.getPersonCardNo()));
//				ps.addBatch();
//				return ps;
//			}
//			@Override
//			public boolean doValidate(HSSFRow row, HttpServletRequest request,String type) throws Exception {
//				Reward reward=new Reward();
//				validateEachRow(row, reward, "add");
//				return true;
//			}
			@Override
			public String getBachSql() throws Exception {
				String sql="INSERT INTO  panter_reward" +
						"(workerid, id, reward, memo, lastedittime, lastediterid, lasteditername, " +
						"adduser, adddept, addtime, isdelete, workername, deptid, areaid, awardtime, " +
						"awarddept, imagepath, person_card_no) " +
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
			public PreparedStatement validateAndPrepareStatement(HSSFRow row,	HttpServletRequest request, String type,
					PreparedStatement ps, String validateType) throws Exception {
				int cellNum=0;
				String operationTime = (new Date()).toString();
				TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
				 PartnerUser user;
				 if ("personCardNo".equals(validateType)) {
					 user=XLSCellValidateUtil.checkPersonCardNoIsExist(row, cellNum++, false);//采用身份证校验
				}else {
					user=XLSCellValidateUtil.checkByUserIdAndGetUser(row, cellNum++);
				}
				String reward=XLSCellValidateUtil.checkIsNull(row, cellNum++);//2
				String awardtime=XLSCellValidateUtil.checkDate(row, cellNum++);//3
				String awarddept=XLSCellValidateUtil.checkIsNull(row, cellNum++);//4
				String memo=XLSCellValidateUtil.checkLength(row, cellNum++, 200);//5
				long t1=System.currentTimeMillis();
				ps.setString(1,user.getUserId());
				ps.setString(2,com.boco.eoms.sheet.base.util.UUIDHexGenerator.getInstance().getID());
				ps.setString(3,reward);
				ps.setString(4,memo);
				ps.setString(5,operationTime);
				ps.setString(6,sessionInfo.getUserid());
				ps.setString(7,sessionInfo.getUsername());
				ps.setString(8,sessionInfo.getUsername());
				ps.setString(9,sessionInfo.getDeptname());
				ps.setString(10,operationTime);
				ps.setString(11,"0");
				ps.setString(12,user.getName());
				ps.setString(13,user.getDeptId());
				ps.setString(14,user.getAreaId());
				ps.setString(15,awardtime);
				ps.setString(16,awarddept);
				ps.setString(17,"");
				ps.setString(18,user.getPersonCardNo());
				ps.addBatch();
				return null;
			}
		};
		ImportResult result=xlsFileImport.xlsFileValidate(formFile,request);
		return result;
	}
	/**
	* @Title: validateEachRow 
	* @Description: 只负责验证xls文件当前行内容是否符合要求，并不包含数据是否存在或者多余的逻辑，
	* 如果符合要求则返回一个model
	* @param row
	* @param reward
	* @param  request
	* @return Reward    返回类型 
	* @throws
	 */
	public Reward validateEachRow(HSSFRow row,Reward reward,String type){
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
		if ("update".equals(type)) {
			reward.setSysno(XLSCellValidateUtil.checkIsNull(row, cellNum++));
		}
		reward.setReward(XLSCellValidateUtil.checkIsNull(row, cellNum++));
		reward.setAwardtime(XLSCellValidateUtil.checkDate(row, cellNum++));
		reward.setAwarddept(XLSCellValidateUtil.checkIsNull(row, cellNum++));
		reward.setMemo(XLSCellValidateUtil.checkLength(row, cellNum++, 200));
		return reward;
	}
	/**
	 * 
	* @Title: getRowObject 
	* @Description: 解析当前的excel文件的每一行为一个model
	* @param 
	* @return Reward    返回类型 
	* @throws
	 */
	public Reward getRowObject(HSSFRow row,Reward reward,HttpServletRequest request,String type){
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		int cellNum=0;
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
		reward.setWorkerid(StaticMethod.null2String(user.getUserId()));
		reward.setPersonCardNo(StaticMethod.null2String(user.getPersonCardNo()));
		reward.setWorkername(StaticMethod.null2String(user.getName()));
		reward.setDeptid(StaticMethod.null2String(user.getDeptId()));
		reward.setAreaid(StaticMethod.null2String(user.getAreaId()));
		if ("add".equals(type)) {
			reward.setAdduser(sessionInfo.getUsername());
			reward.setAdddept(sessionInfo.getDeptname());
			reward.setAddtime(operationTime);
		}else if ("update".equals(type)) {
			reward.setSysno(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//1
			reward.setLastediterid(sessionInfo.getUserid());
			reward.setLasteditername(sessionInfo.getUsername());
			reward.setLastedittime(operationTime);
		}
		reward.setReward(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//2
		reward.setAwardtime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//3
		reward.setAwarddept(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//4
		reward.setMemo(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//5
		reward.setIsdelete("0");
		return reward;
		
	}
	/*
	 * 新增数据校验
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doUpdateXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	public boolean doSaveXLSFileValidate(HSSFRow row) throws Exception {
		int cellNum=0;
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 String id="";
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 user=XLSCellValidateUtil.checkPersonCardNoIsExist(row, cellNum++, false);//采用身份证校验
			 id=user.getPersonCardNo();
		}else {
			user=XLSCellValidateUtil.checkByUserIdAndGetUser(row, cellNum++);
			id=user.getUserId();
		}
		String reward=XLSCellValidateUtil.checkIsNull(row, cellNum++);
		String awardtime=XLSCellValidateUtil.checkDate(row, cellNum++);
		String warddept=XLSCellValidateUtil.checkIsNull(row, cellNum++);
		XLSCellValidateUtil.checkLength(row, cellNum++, 200);
		List<Reward> rewardList =new ArrayList<Reward>();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 rewardList=this.rewardDao.findByHql("from Reward reward where reward.personCardNo ='"+id+"' and reward.reward='"
						+reward+"' and reward.awardtime='"+awardtime+"' and reward.awarddept='"+warddept+"'  and reward.isdelete='0' ");
			 if(rewardList.size()>0)
				 throw new RuntimeException("\""+warddept+"\"于"+"\""+awardtime+"\""+"颁发给\""+id+"\""+"的\""+reward+"\"奖励已经存在");
		 }else {
			 rewardList=this.rewardDao.findByHql("from Reward reward where reward.workerid ='"+id+"' and reward.reward='"
						+reward+"' and reward.awardtime='"+awardtime+"' and reward.awarddept='"+warddept+"'  and reward.isdelete='0' ");
			 if(rewardList.size()>0)
				 throw new RuntimeException("\""+warddept+"\"于"+"\""+awardtime+"\""+"颁发给\""+id+"\""+"的\""+reward+"\"奖励已经存在");
		}
		return true;
	}
	
	/*
	 * 删除数据校验
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doUpdateXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	public boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception {
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 String id="";
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 user=XLSCellValidateUtil.checkPersonCardNoIsExist(row,0, false);//采用身份证校验
			 id=user.getPersonCardNo();
			 String sysno=XLSCellValidateUtil.checkIsNull(row, 1);
			 List<Reward> rewardList = (List<Reward>) this.rewardDao
				.findByHql("from Reward reward where reward.personCardNo ='"+id+"' and reward.sysno='"+sysno+"' and reward.isdelete='0'");
			 if(rewardList.size()==0)
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:身份证为\""+id+"\"系统编码为\""+sysno+"\"的信息不存在");
		}else {
			user=XLSCellValidateUtil.checkByUserIdAndGetUser(row,0);
			id=user.getUserId();
			String sysno=XLSCellValidateUtil.checkIsNull(row, 1);
			 List<Reward> rewardList = (List<Reward>) this.rewardDao
				.findByHql("from Reward reward where reward.workerid ='"+id+"' and reward.sysno='"+sysno+"' and reward.isdelete='0'");
			 if(rewardList.size()==0)
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:用户ID为\""+id+"\"系统编码为\""+sysno+"\"的信息不存在");
		}
		 return true;
	}
	/*
	 * 删除数据校验
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doUpdateXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception {
		int cellNum=0;
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 String id="";
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 user=XLSCellValidateUtil.checkPersonCardNoIsExist(row, cellNum++, false);//采用身份证校验
			 id=user.getPersonCardNo();
		}else {
			user=XLSCellValidateUtil.checkByUserIdAndGetUser(row, cellNum++);
			id=user.getUserId();
		}
		String sysno=XLSCellValidateUtil.checkIsNull(row, cellNum++);
		String reward=XLSCellValidateUtil.checkIsNull(row, cellNum++);
		String awardtime=XLSCellValidateUtil.checkDate(row, cellNum++);
		String warddept=XLSCellValidateUtil.checkIsNull(row, cellNum++);
		XLSCellValidateUtil.checkLength(row, cellNum++, 200);
		List<Reward> rewardList =new ArrayList<Reward>();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 rewardList=this.rewardDao.findByHql("from Reward reward where reward.personCardNo ='"+id+"' and reward.sysno='"+sysno+"' and reward.isdelete='0' ");
			 if(rewardList.size()==0)
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:身份证为\""+id+"\"系统编码为\""+sysno+"\"的信息不存在");
		}else {
			rewardList=this.rewardDao.findByHql("from Reward reward where reward.workerid ='"+id+"' and reward.sysno='"+sysno+"' and reward.isdelete='0' ");
			if(rewardList.size()==0)
				throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:用户ID为\""+id+"\"系统编码为\""+sysno+"\"的信息不存在");
		}
		 return true;
	}
	/*
	 * 新增数据的归档
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doSaveXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doSaveXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		Reward reward = new Reward();
		reward=getRowObject(row, reward, request,"add");
//		String sysno=createRewardSystemNo("");
//		reward.setSysno(sysno);
		rewardDao.save(reward);
		return true;
	}
	/*
	 * 修改后数据的归档
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doUpdateXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doUpdateXLSFileData(HSSFRow row, HttpServletRequest request) throws Exception {
		Reward reward = new Reward();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		List<Reward> rewardList=new ArrayList<Reward>();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 rewardList=rewardDao.findByHql("from Reward reward where reward.personCardNo ='"+id
					+"' and reward.sysno='"+sysno+"' and reward.isdelete='0'");
			if (rewardList.size()<1) {
				throw new RuntimeException("第"+row.getRowNum()+1+"行归档出错，该条记录已经不存在");
			}
		}else {
			rewardList =rewardDao.findByHql("from Reward reward where reward.workerid ='"+id
					+"' and reward.sysno='"+sysno+"' and reward.isdelete='0'");
			if (rewardList.size()<1) {
				throw new RuntimeException("第"+row.getRowNum()+1+"行归档出错，该条记录已经不存在");
			}
		}
		reward=rewardList.get(0);
		reward=getRowObject(row, reward, request,"update");
		rewardDao.save(reward);
		return true;
	}
	/*
	 * 删除数据归档
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doDeleteXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doDeleteXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		Reward reward = new Reward();
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String operationTime = (new Date()).toString();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		List<Reward> rewardList=new ArrayList<Reward>();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 rewardList=rewardDao.findByHql("from Reward reward where reward.personCardNo ='"+id
					+"' and reward.sysno='"+sysno+"' and reward.isdelete='0'");
			if (rewardList.size()<1) {
				throw new RuntimeException("第"+row.getRowNum()+1+"行归档出错，该条记录已经不存在");
			}
		}else {
			rewardList =rewardDao.findByHql("from Reward reward where reward.workerid ='"+id
					+"' and reward.sysno='"+sysno+"' and reward.isdelete='0'");
			if (rewardList.size()<1) {
				throw new RuntimeException("第"+row.getRowNum()+1+"行归档出错，该条记录已经不存在");
			}
		}
		reward=rewardList.get(0);
		reward.setIsdelete("1");
		reward.setLastediterid(sessionInfo.getUserid());
		reward.setLasteditername(sessionInfo.getUsername());
		reward.setLastedittime(operationTime);
		rewardDao.save(reward);
		return true;
	}
	/**
	 * 获取xls的规格
	 */
	public XLSModel getXLSModel() {
		return new  XLSModel(2, 0, 5, 2, 0,2, 2, 0,6);
	}
	/**
	 * 删除所有的用户信息
	 */
	public void removeAllByUserId(String userid) {
		List<Reward> list = rewardDao.findByHql("from Reward r where r.isdelete='0' and  r.workerid = '"+userid+"'");
		if (list.size()>0) {
			for (Reward reward : list) {
				reward.setIsdelete("1");
				rewardDao.save(reward);
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
	public String createRewardSystemNo(String id) {
		String sysNo=PartnerUserHander.creatSysno(PartnerUserConstants.REWARD_TABLENAME,PartnerUserConstants.REWARD_SYSNO_FLAG);
		return sysNo;
	}
	public boolean doLoadStaticSource() {
		// XXX Auto-generated method stub
		return false;
	}

}
