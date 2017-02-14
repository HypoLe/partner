package com.boco.eoms.partner.personnel.mgr.impl;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.boco.eoms.partner.personnel.dao.DWInfoDao;
import com.boco.eoms.partner.personnel.mgr.DWInfoMgr;
import com.boco.eoms.partner.personnel.model.DWInfo;
import com.boco.eoms.partner.personnel.util.PartnerUserHander;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSToFormFileImport;
	/**
 * <p>
 * Title:人员资质信息管理
 * </p>
 * <p>
 * Description:人员资质信息管理
 * </p>
 * <p>
 * Jul 16, 2012 2:45:13 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class DWInfoMgrImpl extends CommonGenericServiceImpl<DWInfo> implements
	DWInfoMgr,PnrProcessService {
	private DWInfoDao dwinfoDao;

	public DWInfoDao getDwinfoDao() {
		return dwinfoDao;
	}

	public void setDwinfoDao(DWInfoDao dwinfoDao) {
		this.dwinfoDao = dwinfoDao;
		this.setCommonGenericDao(dwinfoDao);
	}

	/**
	 * 移动管理员批量导入
	 */
	public ImportResult importFromFile(FormFile formFile,HttpServletRequest request) throws Exception {
		XLSToFormFileImport xlsFileImport=new XLSToFormFileImport(){
			public boolean doSaveRow2Data(HSSFRow row,	HttpServletRequest request) throws Exception {
				DWInfo dwinfo=new DWInfo();
				validateEachRow(row, dwinfo, "add");
				dwinfo=getRowObject(row, dwinfo, request, "add");
				PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
				.getInstance().getBean("pnrDeptAndUserConfigSetList");
				 String dwinfoValidateType=setList.getDwInfoValidateType();
				 PartnerUser user=new PartnerUser();
				 List<DWInfo> list =new ArrayList<DWInfo>();
				 String professonal=dwinfo.getProfessional();
				 if ("personCardNo".equals(dwinfoValidateType)) {
					 list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.personCardNo='"+dwinfo.getPersonCardNo()
							 +"' and dw.professional='"+professonal+"'");
					 if(list.size()>0){
						 throw new RuntimeException("第"+(row.getRowNum()+1)+"行保存出错:身份证为\""+dwinfo.getPersonCardNo()+"\"的员工已经拥有该项技能");
					 }
				 }else {
					 list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.workerid='"+dwinfo.getWorkerid()+"' and dw.professional='"+professonal+"'");
					 if(list.size()>0){
						 throw new RuntimeException("第"+(row.getRowNum()+1)+"行保存出错:用户ID为\""+dwinfo.getWorkerid()+"\"的员工已经拥有该项技能");
					 }
				}
				dwinfo.setSysno(createdwInfoSystemNo(""));
				save(dwinfo);
				return true;
			}
			public XLSModel getXLSModel() {
				return new  XLSModel(2, 0, 6, 2,0,2, 2, 0, 7);
			}
//			@Override
//			public PreparedStatement addPsBach(PreparedStatement ps, HSSFRow row,
//					HttpServletRequest request) throws Exception {
//				DWInfo object=new DWInfo();
//				object=getRowObject(row, object, request, "add");
//				ps.setString(1,CommonSqlHelper.generateUUID());
//				//INSERT INTO  partner_dwinfo(id, groups, professional, skilllevel, duty, accountno, qualificationlist, memo, 
//				//lastedittime, lastediterid, lasteditername, adduser, adddept, addtime, isdelete, workerid, workername, 
//				//junior_skilllevel, middle_skilllevel, advanced_skilllevel, deptid, areaid, sysno, person_card_no) 
//				ps.setString(2,StaticMethod.null2String(object.getGroup()));
//				ps.setString(3,StaticMethod.null2String(object.getProfessional()));
//				ps.setString(4,StaticMethod.null2String(object.getSkilllevel()));
//				ps.setString(5,StaticMethod.null2String(object.getDuty()));
//				ps.setString(6,StaticMethod.null2String(object.getAccountno()));
//				ps.setString(7,StaticMethod.null2String(object.getQualificationlist()));
//				ps.setString(8,StaticMethod.null2String(object.getMemo()));
//				ps.setString(9,StaticMethod.null2String(object.getLastedittime()));
//				ps.setString(10,StaticMethod.null2String(object.getLastediterid()));
//				ps.setString(11,StaticMethod.null2String(object.getLasteditername()));
//				ps.setString(12,StaticMethod.null2String(object.getAdduser()));
//				ps.setString(13,StaticMethod.null2String(object.getAdddept()));
//				ps.setString(14,StaticMethod.null2String(object.getAddtime()));
//				ps.setString(15,StaticMethod.null2String(object.getIsdelete()));
//				ps.setString(16,StaticMethod.null2String(object.getWorkerid()));
//				ps.setString(17,StaticMethod.null2String(object.getWorkername()));
//				ps.setInt(18,object.getJuniorSkillLevel());
//				ps.setInt(19,object.getMiddleSkillLevel());
//				ps.setInt(20,object.getAdvancedSkillLevel());
//				ps.setString(21,StaticMethod.null2String(object.getDeptid()));
//				ps.setString(22,StaticMethod.null2String(object.getAreaid()));
//				ps.setString(23,StaticMethod.null2String(object.getPersonCardNo()));
//				ps.addBatch();
//				return ps;
//			}
//			@Override
//			public boolean doValidate(HSSFRow row, HttpServletRequest request, String type)throws Exception {
//				doSaveXLSFileValidate(row);
//				return true;
//			}
			@Override
			public String getBachSql() throws Exception {
				String sql="INSERT INTO  partner_dwinfo(id, groups, professional, skilllevel, duty, accountno, qualificationlist, memo, " +
						"lastedittime, lastediterid, lasteditername, adduser, adddept, addtime, isdelete, workerid, workername, junior_skilllevel, " +
						"middle_skilllevel, advanced_skilllevel, deptid, areaid, person_card_no) " +
						"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				return sql;
			}
			@Override
			public boolean getWay() throws Exception {
				return false;
			}
			@Override
			public void loadStaticSource() throws Exception {
				PnrProcessCach.loadDictCache("11225");
				PnrProcessCach.loadDictCache("12410");
				PnrProcessCach.loadDictCache("12401");
			}
			@Override
			public PreparedStatement validateAndPrepareStatement(HSSFRow row,HttpServletRequest request, String type,
					PreparedStatement ps, String validateType) throws Exception {
				int cellNum=0;
				TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
				String operationTime = (new Date()).toString();
				 PartnerUser user=new PartnerUser();
				 if ("personCardNo".equals(validateType)) {
					 user=XLSCellValidateUtil.checkPersonCardNoIsExist(row, cellNum++, false);//采用身份证校验
				}else {
					user=XLSCellValidateUtil.checkByUserIdAndGetUser(row, cellNum++);
				}
				XLSCellValidateUtil.dictNameToDictId(row, cellNum++, "11225", false);
				String  professional = XLSCellValidateUtil.dictNameToDictId(row, cellNum-1, "11225", false);
				XLSCellValidateUtil.dictNameToDictId(row, cellNum++, "12410", false);
				String skillLevel=XLSCellValidateUtil.dictNameToDictId(row, cellNum-1, "12410", false);//2技能等级；
				String duty = XLSCellValidateUtil.checkIsNull(row, cellNum++);//3岗位;
				String count0=XLSCellValidateUtil.getCellStringValue(row, cellNum++);//4账号;
				String accountno = "";
				if (!"".equals(count0)) {
					accountno=XLSCellValidateUtil.dictNameToDictId(row, cellNum-1, "12401", false);
				}
				int juniorSkillLevel=0;
				int middleSkillLevel=0;
				int advancedSkillLevel=0;
				if ("1241001".equals(skillLevel)) {//初级
					juniorSkillLevel = 1;
					
				}else if ("1241002".equals(skillLevel)) {//中级
					middleSkillLevel = 1;
					
				}else if ("1241003".equals(skillLevel)) {//高级
					advancedSkillLevel = 1;
				}
				String id=XLSCellValidateUtil.getCellStringValue(row, 0);
				String pro=XLSCellValidateUtil.getCellStringValue(row, 1);
				String rowNumStr=row.getRowNum()+1+"";
				 if ("personCardNo".equals(validateType)) {
					 //list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.personCardNo='"+id+"' and dw.professional='"+professinal+"'");
					 if(PnrProcessCach.dwInfoListCach.containsKey("personCardNo_"+id+",pro_"+professional)){
						 if (PnrProcessCach.dwInfoListCach.containsKey("personCardNo_"+id+",pro_"+professional+",row")) {
							 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:身份证为\""+id+"\"专业为\""
									 +pro+"\"的信息与Excel中的第"+PnrProcessCach.dwInfoListCach.get("personCardNo_"+id+",pro_"+professional+",row")+"行重复.");
						}
						 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:身份证为\""+id+"\"专业为\""
								 +pro+"\"的信息已经存在.");
					 }
					 //如果校验通过将该值插入map中防止下一个相同的被插入数据库中而未发生校验;
					 PnrProcessCach.dwInfoListCach.put("personCardNo_"+id+",pro_"+professional, professional);
					 PnrProcessCach.dwInfoListCach.put("personCardNo_"+id+",pro_"+professional+",row", rowNumStr);
				}else {
					//list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.workerid='"+id+"' and dw.professional='"+professinal+"'");
					if(PnrProcessCach.dwInfoListCach.containsKey("userId_"+id+",pro_"+professional)){
						 if (PnrProcessCach.dwInfoListCach.containsKey("userId_"+id+",pro_"+professional+",row")) {
							 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:用户ID为\""+id+"\"专业为\""
									 +pro+"\"的信息与Excel中的第"+PnrProcessCach.dwInfoListCach.get("userId_"+id+",pro_"+professional+",row")+"行重复.");
						}
						throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:用户ID为\""+id+"\"专业为\""
								+pro+"\"的信息已经存在.");
					}
					PnrProcessCach.dwInfoListCach.put("userId_"+id+",pro_"+professional, professional);
					PnrProcessCach.dwInfoListCach.put("userId_"+id+",pro_"+professional+",row", rowNumStr);
				}
				String memo = XLSCellValidateUtil.checkLength(row, cellNum++, 200);
				ps.setString(1,CommonSqlHelper.generateUUID());
				ps.setString(2,StaticMethod.null2String(user.getDeptName()));
				ps.setString(3,StaticMethod.null2String(professional));
				ps.setString(4,StaticMethod.null2String(skillLevel));
				ps.setString(5,StaticMethod.null2String(duty));
				ps.setString(6,StaticMethod.null2String(accountno));
				ps.setString(7,StaticMethod.null2String(""));
				ps.setString(8,StaticMethod.null2String(memo));
				ps.setString(9,StaticMethod.null2String(operationTime));
				ps.setString(10,StaticMethod.null2String(sessionInfo.getUserid()));
				ps.setString(11,StaticMethod.null2String(sessionInfo.getUsername()));
				ps.setString(12,StaticMethod.null2String(sessionInfo.getUsername()));
				ps.setString(13,StaticMethod.null2String(sessionInfo.getDeptname()));
				ps.setString(14,StaticMethod.null2String(operationTime));
				ps.setString(15,StaticMethod.null2String("0"));
				ps.setString(16,StaticMethod.null2String(StaticMethod.null2String(user.getUserId())));
				ps.setString(17,StaticMethod.null2String(StaticMethod.null2String(user.getName())));
				ps.setInt(18,juniorSkillLevel);
				ps.setInt(19,middleSkillLevel);
				ps.setInt(20,advancedSkillLevel);
				ps.setString(21,StaticMethod.null2String(user.getDeptId()));
				ps.setString(22,StaticMethod.null2String(user.getAreaId()));
				ps.setString(23,StaticMethod.null2String(user.getPersonCardNo()));
				ps.addBatch();
				return ps;
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
	* @Time:Nov 27, 2012-4:19:53 PM
	* @Author:fengguangping
	* @return DWInfo    返回类型 
	* @throws
	 */
	public DWInfo getRowObject(HSSFRow row,DWInfo dwInfo,HttpServletRequest request,String type) throws Exception{
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
		dwInfo.setWorkerid(StaticMethod.null2String(user.getUserId()));
		dwInfo.setWorkername(StaticMethod.null2String(user.getName()));
		dwInfo.setPersonCardNo(StaticMethod.null2String(user.getPersonCardNo()));
		dwInfo.setDeptid(StaticMethod.null2String(user.getDeptId()));
		dwInfo.setAreaid(StaticMethod.null2String(user.getAreaId()));
		if ("add".equals(type)) {
			dwInfo.setAdduser(sessionInfo.getUsername());
			dwInfo.setAdddept(sessionInfo.getDeptname());
			dwInfo.setAddtime(operationTime);
		}else if ("update".equals(type)) {
			dwInfo.setSysno(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		}
		if (user!=null) {
			dwInfo.setGroup(StaticMethod.null2String(user.getDeptName()));
		}
		XLSCellValidateUtil.dictNameToDictId(row, cellNum++, "11225", false);
		dwInfo.setProfessional(XLSCellValidateUtil.dictNameToDictId(row, cellNum-1, "11225", false));
		XLSCellValidateUtil.dictNameToDictId(row, cellNum++, "12410", false);
		dwInfo.setSkilllevel(XLSCellValidateUtil.dictNameToDictId(row, cellNum-1, "12410", false));//2技能等级；
		dwInfo.setDuty(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//3岗位;
		String count0=XLSCellValidateUtil.getCellStringValue(row, cellNum++);//4账号;
		if (!"".equals(count0)) {
			dwInfo.setAccountno(XLSCellValidateUtil.dictNameToDictId(row, cellNum-1, "12401", false));
		}else {
			dwInfo.setAccountno("");
		}
//		PartnerUserHander hander=new PartnerUserHander();
//		dwInfo=hander.handleSkillLevelInfo(dwInfo);
		String skillLevel=StaticMethod.null2String(dwInfo.getSkilllevel());
		if ("1241001".equals(skillLevel)) {//初级
			dwInfo.setJuniorSkillLevel(1);
			dwInfo.setMiddleSkillLevel(0);
			dwInfo.setAdvancedSkillLevel(0);
		}else if ("1241002".equals(skillLevel)) {//中级
			dwInfo.setJuniorSkillLevel(0);
			dwInfo.setMiddleSkillLevel(1);
			dwInfo.setAdvancedSkillLevel(0);
		}else if ("1241003".equals(skillLevel)) {//高级
			dwInfo.setJuniorSkillLevel(0);
			dwInfo.setMiddleSkillLevel(0);
			dwInfo.setAdvancedSkillLevel(1);
		}
		dwInfo.setMemo(XLSCellValidateUtil.getCellStringValue(row,cellNum++));
		dwInfo.setLastediterid(sessionInfo.getUserid());
		dwInfo.setLasteditername(sessionInfo.getUsername());
		dwInfo.setLastedittime(operationTime);
		dwInfo.setIsdelete("0");
		return dwInfo;
	}
	/**
	 * 
	* @Title: validateEachRow 
	* @Description: TODO
	* @param 
	* @Time:Nov 27, 2012-4:19:44 PM
	* @Author:fengguangping
	* @return DWInfo    返回类型 
	* @throws
	 */
	public DWInfo validateEachRow(HSSFRow row,DWInfo dwInfo,String type) throws Exception{
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
			dwInfo.setSysno(XLSCellValidateUtil.checkIsNull(row, cellNum++));
		}
		if (user!=null) {
			dwInfo.setGroup(StaticMethod.null2String(user.getDeptName()));
		}
		XLSCellValidateUtil.checkDictName(row, cellNum++, "11225",false);
		dwInfo.setProfessional(XLSCellValidateUtil.dictNameToDictId(row, cellNum-1, "11225", false));
		XLSCellValidateUtil.checkDictName(row, cellNum++, "12410",false);
		//dwInfo.setSkilllevel(XLSCellValidateUtil.name2Id(row, cellNum++, "12410"));//2技能等级；
		dwInfo.setDuty(XLSCellValidateUtil.checkLength(row, cellNum++, 50, false));//3岗位;
		String count0=XLSCellValidateUtil.getCellStringValue(row, cellNum++);//4账号;
		if (!"".equals(count0)) {
			XLSCellValidateUtil.checkDictName(row, cellNum-1, "12401",false);
		}else {
			dwInfo.setAccountno("");
		}
		dwInfo.setMemo(XLSCellValidateUtil.checkLength(row, cellNum++, 200, true));
		return dwInfo;
	}
	/*
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doSaveXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doSaveXLSFileData(HSSFRow row,HttpServletRequest request) throws Exception {
		//取出excel数据 构建实体 写入数据库
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		DWInfo dwinfo = new DWInfo();
		dwinfo=getRowObject(row, dwinfo, request, "add");
		//dwinfo.setSysno(createdwInfoSystemNo(""));
		this.dwinfoDao.save(dwinfo);
		return true;
	}
	/*
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doUpdateXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doUpdateXLSFileData(HSSFRow row,HttpServletRequest request) throws Exception {
		//取出excel数据 构建实体 写入数据库
		DWInfo dwInfo = new DWInfo();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 List<DWInfo> list =new ArrayList<DWInfo>();
		 String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		 String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.personCardNo='"+id+"' and dw.sysno='"+sysno+"'");
			 if(list.size()<1){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"归档出错:身份证为\""+id+"\"的该条信息不存在");
			 }
		 }else {
			 list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.workerid='"+id+"' and dw.sysno='"+sysno+"'");
			 if(list.size()<1){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"归档出错:用户ID为\""+id+"\"的该条信息不存在");
			 }
		}
		 dwInfo=list.get(0);
		 dwInfo=getRowObject(row, dwInfo, request, "update");
		 this.dwinfoDao.save(dwInfo);	
		return true;
	}
	/*
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doDeleteXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doDeleteXLSFileData(HSSFRow row,HttpServletRequest request) throws Exception {
		//取出excel数据 构建实体 写入数据库
		DWInfo dwInfo = new DWInfo();
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String operationTime = (new Date()).toString();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 List<DWInfo> list =new ArrayList<DWInfo>();
		 String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		 String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.personCardNo='"+id+"' and dw.sysno='"+sysno+"'");
			 if(list.size()<1){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"归档出错:身份证为\""+id+"\"的该条信息不存在");
			 }
		 }else {
			 list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.workerid='"+id+"' and dw.sysno='"+sysno+"'");
			 if(list.size()<1){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"归档出错:用户ID为\""+id+"\"的该条信息不存在");
			 }
		}
		dwInfo=list.get(0);
		dwinfoDao.removeById(dwInfo.getId());
		return true;
	}
	/*
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doDeleteXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	public boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception {
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 List<DWInfo> list =new ArrayList<DWInfo>();
		 String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		 String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 user=XLSCellValidateUtil.checkPersonCardNoIsExist(row, 0, false);//采用身份证校验
			 list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.personCardNo='"+id+"' and dw.sysno='"+sysno+"'");
			 if(list.size()<1){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"校验出错:身份证为\""+id+"\"的该条信息不存在");
			 }
		 }else {
			 user=XLSCellValidateUtil.checkByUserIdAndGetUser(row, 0);
			 list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.workerid='"+id+"' and dw.sysno='"+sysno+"'");
			 if(list.size()<1){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"校验出错:用户ID为\""+id+"\"的该条信息不存在");
			 }
		}
		 return true;
	}

	public boolean doSaveXLSFileValidate(HSSFRow row) throws Exception {
		DWInfo dwInfo = new DWInfo();
		dwInfo=validateEachRow(row, dwInfo, "add");
		String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		String pro=XLSCellValidateUtil.getCellStringValue(row, 1);
		// List<DWInfo> list =new ArrayList<DWInfo>();
		Map map=PnrProcessCach.dwInfoListCach;
		String rowNumStr=row.getRowNum()+1+"";
		String professinal=dwInfo.getProfessional();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 //list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.personCardNo='"+id+"' and dw.professional='"+professinal+"'");
			 if(map.get("personCardNo_"+id+",pro_"+professinal)!=null){
				 if (map.get("personCardNo_"+id+",pro_"+professinal+",row")!=null) {
					 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:身份证为\""+id+"\"专业为\""
							 +pro+"\"的信息与Excel中的第"+map.get("personCardNo_"+id+",pro_"+professinal+",row")+"行重复.");
				}
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:身份证为\""+id+"\"专业为\""
						 +pro+"\"的信息已经存在.");
			 }
			 //如果校验通过将该值插入map中防止下一个相同的被插入数据库中而未发生校验;
			 map.put("personCardNo_"+id+",pro_"+professinal, professinal);
			 map.put("personCardNo_"+id+",pro_"+professinal+",row", rowNumStr);
		}else {
			//list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.workerid='"+id+"' and dw.professional='"+professinal+"'");
			if(map.get("userId_"+id+",pro_"+professinal)!=null){
				 if (map.get("userId_"+id+",pro_"+professinal+",row")!=null) {
					 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:用户ID为\""+id+"\"专业为\""
							 +pro+"\"的信息与Excel中的第"+map.get("userId_"+id+",pro_"+professinal+",row")+"行重复.");
				}
				throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:用户ID为\""+id+"\"专业为\""
						+pro+"\"的信息已经存在.");
			}
			 map.put("userId_"+id+",pro_"+professinal, professinal);
			 map.put("userId_"+id+",pro_"+professinal+",row", rowNumStr);
		}
		return true;
	}
	/*
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doUpdateXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception {
		 DWInfo dwInfo = new DWInfo();
		 dwInfo=validateEachRow(row, dwInfo, "update");
		String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		String sysno=XLSCellValidateUtil.getCellStringValue(row,1);
		 List<DWInfo> list =new ArrayList<DWInfo>();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.personCardNo='"+id+"' and dw.sysno='"+sysno+"'");
			 if(list.size()<1){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:身份证为\""+id+"\"的该条信息不存在");
			 }
		}else {
			list=dwinfoDao.findByHql("from DWInfo dw where dw.isdelete='0' and  dw.workerid='"+id+"' and dw.sysno='"+sysno+"'");
			 if(list.size()<1){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错:用户ID\""+id+"\"的该条信息不存在");
			 }
		}
		return true;
	}

	public XLSModel getXLSModel() {
		return new XLSModel(2, 0, 6, 2, 0, 2, 2, 0, 7);
	}
	
	public void removeAllByUserId(String userid) {
		List<DWInfo> list = dwinfoDao.findByHql("from DWInfo d where d.isdelete='0' and d.workerid = '"+userid+"'");
		if (list.size()>0) {
			for (DWInfo dwinfo : list) {
				dwinfo.setIsdelete("1");
				dwinfoDao.save(dwinfo);
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
	/**
	 * 
	* @Title: createdwInfoSystemNo 
	* @Description: 生成系统编码
	* @param 
	* @Time:Dec 24, 2012-1:50:45 PM
	* @Author:fengguangping
	* @return String    返回类型 
	* @throws
	 */
	public String createdwInfoSystemNo(String id){
		String sysNo=PartnerUserHander.creatSysno(PartnerUserConstants.DWINFO_TABLENAME,PartnerUserConstants.DWINFO_SYSNO_FLAG);
		return sysNo;
	}

	public void removeAllByUserId(String userid, boolean logic) {
		List<DWInfo> list = dwinfoDao.findByHql("from DWInfo d where d.isdelete='0' and d.workerid = '"+userid+"'");
		if (list.size()>0) {
			for (DWInfo dwinfo : list) {
				if (logic) {
					dwinfo.setIsdelete("1");
					dwinfoDao.save(dwinfo);
				}else {
					dwinfoDao.removeById(dwinfo.getId());
				}
			}
		}
	}

	public List<DWInfo> findByHql(String hql) {
		List<DWInfo> list=new ArrayList<DWInfo>();
		hql="from DWInfo where isdelete='0' "+hql;
		list=dwinfoDao.findByHql(hql);
		return list;
	}

	public boolean doLoadStaticSource() {
		// XXX Auto-generated method stub
		return false;
	}

}

