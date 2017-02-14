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
import com.boco.eoms.partner.personnel.dao.StudyExperienceDao;
import com.boco.eoms.partner.personnel.mgr.StudyExperienceMgr;
import com.boco.eoms.partner.personnel.model.StudyExperience;
import com.boco.eoms.partner.personnel.util.PartnerUserHander;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSToFormFileImport;
	/**
 * <p>
 * Title:人员学习经历管理
 * </p>
 * <p>
 * Description:人员学习经历管理
 * </p>
 * <p>
 * Jul 19, 2012 4:47:13 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class StudyExperienceMgrImpl extends CommonGenericServiceImpl<StudyExperience> implements
	StudyExperienceMgr,PnrProcessService {
	private StudyExperienceDao studyperienceDao;

	public StudyExperienceDao getStudyExperienceDao() {
		return studyperienceDao;
	}

	public void setStudyExperienceDao(StudyExperienceDao studyperienceDao) {
		this.studyperienceDao = studyperienceDao;
		this.setCommonGenericDao(studyperienceDao);
	}
	
	/**
	 * 移动管理员批量导入
	 */
	public ImportResult importFromFile(FormFile formFile,HttpServletRequest request) throws Exception {
		XLSToFormFileImport xlsFileImport=new XLSToFormFileImport(){
			
			public XLSModel getXLSModel() {
				return new  XLSModel(2, 0,8, 0, 0,0, 0, 0, 0);
			}
			/*校验数据*/
//			@Override
//			public boolean doValidate(HSSFRow row, HttpServletRequest request,	String type) throws Exception {
//				StudyExperience studyExperience=new StudyExperience();
//				validateEachRow(row, studyExperience, "add");
//				return true;
//			}
//			/*校验数据*/
//			@Override
//			public PreparedStatement addPsBach(PreparedStatement ps,HSSFRow row, HttpServletRequest request) throws Exception {
//				StudyExperience stu=new StudyExperience();
//				stu=getRowObject(row, stu, request, "add");
//				ps.setString(1, CommonSqlHelper.generateUUID());
//				ps.setString(2, StaticMethod.null2String(stu.getWorkerid()));
//				ps.setString(3, StaticMethod.null2String(stu.getIntime()));
//				ps.setString(4, StaticMethod.null2String(stu.getLeavetime()));
//				ps.setString(5, StaticMethod.null2String(stu.getUniversity()));
//				ps.setString(6, StaticMethod.null2String(stu.getProfessional()));
//				ps.setString(7, StaticMethod.null2String(stu.getDegree()));
//				ps.setString(8, StaticMethod.null2String(stu.getCode()));
//				ps.setString(9, StaticMethod.null2String(stu.getImagepath()));
//				ps.setString(10, StaticMethod.null2String(stu.getMemo()));
//				ps.setString(11, StaticMethod.null2String(stu.getLastedittime()));
//				ps.setString(12, StaticMethod.null2String(stu.getLastediterid()));
//				ps.setString(13, StaticMethod.null2String(stu.getLasteditername()));
//				ps.setString(14, StaticMethod.null2String(stu.getAdduser()));
//				ps.setString(15, StaticMethod.null2String(stu.getAdddept()));
//				ps.setString(16, StaticMethod.null2String(stu.getAddtime()));
//				ps.setString(17, StaticMethod.null2String(stu.getIsdelete()));
//				ps.setString(18, StaticMethod.null2String(stu.getWorkername()));
//				ps.setInt(19, stu.getJuniorDegree());
//				ps.setInt(20, stu.getSeniorDegree());
//				ps.setInt(21, stu.getTechnicalDegree());
//				ps.setInt(22, stu.getCollegeDegree());
//				ps.setInt(23, stu.getUndergraduateDegree());
//				ps.setInt(24, stu.getMasterDegree());
//				ps.setString(25, StaticMethod.null2String(stu.getDeptid()));
//				ps.setString(26, StaticMethod.null2String(stu.getAreaid()));
//				ps.setString(27, StaticMethod.null2String(stu.getPersonCardNo()));
//				ps.addBatch();
//				return ps;
//			}
			/*校验数据*/
			@Override
			public String getBachSql() throws Exception {
				String sql="INSERT INTO panter_studyexperience" +
						"(id, workerid, intime, leavetime, university, professional, degree, code, imagepath, " +
						"memo, lastedittime, lastediterid, lasteditername, adduser, adddept, addtime, isdelete," +
						" workername, junior_degree, technical_degree, senior_degree, college_degree," +
						" undergraduate_degree, master_degree, deptid, areaid,person_card_no)" +
						"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				return sql;
			}
			@Override
			public boolean doSaveRow2Data(HSSFRow row,HttpServletRequest request) throws Exception {
				return false;
			}
			@Override
			public boolean getWay() throws Exception {
				return false;
			}
			@Override
			public void loadStaticSource() throws Exception {
				PnrProcessCach.loadDictCache("12405");
			}
			@Override
			public PreparedStatement validateAndPrepareStatement(HSSFRow row,
					HttpServletRequest request, String type,
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
				String intime=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
				String leavetime=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
				XLSCellValidateUtil.compareDate(row, cellNum-2, cellNum-1, "lessThan");
				String code=XLSCellValidateUtil.checkLength(row, cellNum++, 100);
				XLSCellValidateUtil.checkDictName(row, cellNum++, "12405", false);
				String degree=XLSCellValidateUtil.dictNameToDictId(row, cellNum-1, "12405", false);
				int m=0,un=0,cl=0,se=0,te=0,ju=0;
				if ("1240501".equals(degree)) {//硕士及以上
					m=1;
				}else if("1240502".equals(degree)){//本科
					un=1;
				}else if("1240503".equals(degree)){//大专
					cl=1;
				}else if("1240504".equals(degree)){//高中
					se=1;
				}else if("1240505".equals(degree)){//中专
					te=1;
				}else if("1240506".equals(degree)){//初中及以下
					ju=1;
				}
				String university=XLSCellValidateUtil.checkIsNull(row, cellNum++);
				String professional=XLSCellValidateUtil.checkLength(row, cellNum++, 50);
				String memo=XLSCellValidateUtil.checkLength(row,cellNum++,200);
				ps.setString(1, CommonSqlHelper.generateUUID());
				ps.setString(2, StaticMethod.null2String(user.getUserId()));
				ps.setString(3, intime);
				ps.setString(4, leavetime);
				ps.setString(5, university);
				ps.setString(6,professional);
				ps.setString(7,degree);
				ps.setString(8,code);
				ps.setString(9,"");
				ps.setString(10, memo);
				ps.setString(11, operationTime);
				ps.setString(12, StaticMethod.null2String(sessionInfo.getUserid()));
				ps.setString(13, StaticMethod.null2String(sessionInfo.getUsername()));
				ps.setString(14, StaticMethod.null2String(sessionInfo.getUsername()));
				ps.setString(15, StaticMethod.null2String(sessionInfo.getDeptid()));
				ps.setString(16,operationTime);
				ps.setString(17, "0");
				ps.setString(18, StaticMethod.null2String(user.getName()));
				ps.setInt(19, ju);
				ps.setInt(20,se);
				ps.setInt(21,te);
				ps.setInt(22,cl);
				ps.setInt(23, un);
				ps.setInt(24, m);
				ps.setString(25, StaticMethod.null2String(user.getDeptId()));
				ps.setString(26, StaticMethod.null2String(user.getAreaId()));
				ps.setString(27, StaticMethod.null2String(user.getPersonCardNo()));
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
	public StudyExperience  getRowObject(HSSFRow row,StudyExperience stu,HttpServletRequest request,String type) 
		throws Exception{
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
		stu.setWorkerid(StaticMethod.null2String(user.getUserId()));
		stu.setPersonCardNo(StaticMethod.null2String(user.getPersonCardNo()));
		stu.setWorkername(StaticMethod.null2String(user.getName()));
		stu.setDeptid(StaticMethod.null2String(user.getDeptId()));
		stu.setAreaid(StaticMethod.null2String(user.getAreaId()));
		if ("update".equals(type)) {
			stu.setSysno(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			stu.setLastediterid(sessionInfo.getUserid());
			stu.setLasteditername(sessionInfo.getUsername());
			stu.setLastedittime(operationTime);
		}else if ("add".equals(type)) {
			stu.setAdduser(sessionInfo.getUsername());
			stu.setAdddept(sessionInfo.getDeptname());
			stu.setAddtime(operationTime);
		}
		stu.setIntime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		stu.setLeavetime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		stu.setCode(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		stu.setDegree(XLSCellValidateUtil.dictNameToDictId(row, cellNum++, "12405", false));
		stu.setUniversity(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		stu.setProfessional(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		stu.setMemo(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		String degree=StaticMethod.null2String(stu.getDegree());
		stu.setMasterDegree(0);
		stu.setUndergraduateDegree(0);
		stu.setCollegeDegree(0);
		stu.setSeniorDegree(0);
		stu.setTechnicalDegree(0);
		stu.setJuniorDegree(0);
		if ("1240501".equals(degree)) {//硕士及以上
			stu.setMasterDegree(1);
		}else if("1240502".equals(degree)){//本科
			stu.setUndergraduateDegree(1);
		}else if("1240503".equals(degree)){//大专
			stu.setCollegeDegree(1);
		}else if("1240504".equals(degree)){//高中
			stu.setSeniorDegree(1);
		}else if("1240505".equals(degree)){//中专
			stu.setTechnicalDegree(1);
		}else if("1240506".equals(degree)){//初中及以下
			stu.setJuniorDegree(1);
		}
		stu.setIsdelete("0");
		return  stu;
	}
	/**
	 * 
	* @Title: validateEachRow 
	* @Description: 验证数据每一行是否符合规范要求，不包含任何的逻辑；
	* @param 
	* @Time:Nov 25, 2012-2:57:18 PMO
	* @Author:fengguangping
	* @return StudyExperience    返回类型 
	* @throws
	 */
	public StudyExperience validateEachRow (HSSFRow row,StudyExperience stu,String type) throws Exception{
		System.out.println("---------------------进入validateEachRow---------------------------");
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 int cellNum=0;
		 PartnerUser user=new PartnerUser();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 user=XLSCellValidateUtil.checkPersonCardNoIsExist(row, cellNum++, false);//采用身份证校验
			 System.out.println("---------------------校验完身份证---------------------------");
		}else {
			user=XLSCellValidateUtil.checkByUserIdAndGetUser(row, cellNum++);
		}
		stu.setPersonCardNo(StaticMethod.null2String(user.getPersonCardNo()));
		stu.setWorkerid(StaticMethod.null2String(user.getUserId()));
		stu.setWorkername(StaticMethod.null2String(user.getName()));
		if ("update".equals(type)) {
			stu.setSysno(XLSCellValidateUtil.checkIsNull(row, cellNum++));
		}
		stu.setIntime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		System.out.println("---------------------1---------------------------");
		stu.setLeavetime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		System.out.println("---------------------7---------------------------");
		XLSCellValidateUtil.compareDate(row, cellNum-2, cellNum-1, "lessThan");
		System.out.println("---------------------2---------------------------");
		stu.setCode(XLSCellValidateUtil.checkLength(row, cellNum++, 100));
		System.out.println("---------------------3---------------------------");
		XLSCellValidateUtil.checkDictName(row, cellNum++, "12405", false);
		System.out.println("---------------------4---------------------------");
		//stu.setDegree(XLSCellValidateUtil.name2Id(row, cellNum++, "12405"));
		stu.setUniversity(XLSCellValidateUtil.checkIsNull(row, cellNum++));
		System.out.println("---------------------5---------------------------");
		stu.setProfessional(XLSCellValidateUtil.checkLength(row, cellNum++, 50));
		System.out.println("---------------------6---------------------------");
		stu.setMemo(XLSCellValidateUtil.checkLength(row,cellNum++,200));
		System.out.println("--------------------8---------------------------");
		return  stu;
	}
	/*
	 * 删除数据校验
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doDeleteXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	public boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception {
		StudyExperience stu=new StudyExperience();
		String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		String sysno=XLSCellValidateUtil.checkIsNull(row, 1);
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 XLSCellValidateUtil.checkPersonCardNoIsExist(row, 0, false);//采用身份证校验
			 List<StudyExperience> studyList =new ArrayList<StudyExperience>();
				studyList=studyperienceDao.findByHql("from StudyExperience study where study.personCardNo ='"+id+"' and study.sysno='"+sysno+"'" +
							"  and  study.isdelete='0' ");
			 if(studyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错: 身份证为\""+id+"\"的该条记录不存在");
			 }
		}else {
			XLSCellValidateUtil.checkByUserIdAndGetUser(row, 0);
			 List<StudyExperience> studyList =new ArrayList<StudyExperience>();
				studyList=studyperienceDao.findByHql("from StudyExperience study where study.workerid ='"+id+"' and study.sysno='"+sysno+"'" +
							"  and  study.isdelete='0' ");
			 if(studyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错: 用户ID为\""+id+"\"的该条记录不存在");
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
		System.out.println("---------------------进入SaveXLSFileValidate---------------------------");
		StudyExperience stu=new StudyExperience();
		stu=validateEachRow(row, stu,"add");
		System.out.println("---------------------校验完毕---------------------------");
		String starttime=stu.getIntime();
		String endtime=stu.getLeavetime();
		String code=stu.getCode();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		System.out.println("---------------------SaveXLSFileValidate结束---------------------------");
//		 String dwinfoValidateType=setList.getDwInfoValidateType();
//		 List<StudyExperience> studyList =new ArrayList<StudyExperience>();
//		 if ("personCardNo".equals(dwinfoValidateType)) {
//			studyList=studyperienceDao.findByHql("from StudyExperience study where study.personCardNo ='"+stu.getPersonCardNo()+"' and study.code='"+code+
//					"' and study.intime='"+starttime+"' and study.leavetime='"+endtime+"' and isdelete='0' ");
//			if(studyList.size()>0)
//				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行信息已经存在");
//		}else {
//			studyList=studyperienceDao.findByHql("from StudyExperience study where study.workerid ='"+stu.getWorkerid()+"' and study.code='"+code+
//					"' and study.intime='"+starttime+"' and study.leavetime='"+endtime+"' and isdelete='0' ");
//			if(studyList.size()>0)
//				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行信息已经存在");
//		}
		return true;
	}
	/*
	 * 修改数据校验
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doUpdateXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception {
		StudyExperience stu=new StudyExperience();
		stu=validateEachRow(row, stu,"update");
		String personCardNo=stu.getPersonCardNo();
		String userid=stu.getWorkerid();
		String sysno=stu.getSysno();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 List<StudyExperience> studyList =new ArrayList<StudyExperience>();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 studyList =studyperienceDao.findByHql("from StudyExperience study where study.personCardNo ='"+personCardNo+"' and study.sysno='"+sysno+"'  and study.isdelete='0' ");
			 if(studyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错: 身份证为\""+personCardNo+"\"该条记录不存在");
			 }
		 }else {
			 studyList =studyperienceDao.findByHql("from StudyExperience study where study.workerid ='"+userid+"' and study.sysno='"+sysno+"'  and study.isdelete='0' ");
			 if(studyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行验证出错: 用户ID为\""+userid+"\"该条记录不存在");
			 }
		}
		return true;
	}
	public boolean doSaveXLSFileData(HSSFRow row, HttpServletRequest request) throws Exception {
		StudyExperience study = new StudyExperience();
		study=getRowObject(row, study, request, "add");
//		study.setSysno(createStuExSystemNo(""));
		this.studyperienceDao.save(study);	
		return true;
	}

	public boolean doUpdateXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		StudyExperience stu=new StudyExperience();
		String personCardNo=XLSCellValidateUtil.getCellStringValue(row, 0);
		String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 List<StudyExperience> studyList =new ArrayList<StudyExperience>();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 studyList =studyperienceDao.findByHql("from StudyExperience study where study.personCardNo ='"+personCardNo+"' and study.sysno='"+sysno+"'  and study.isdelete='0' ");
			 if(studyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错: 身份证为\""+personCardNo+"\"该条记录不存在");
			 }
		 }else {
			 studyList =studyperienceDao.findByHql("from StudyExperience study where study.workerid ='"+personCardNo+"' and study.sysno='"+sysno+"'  and study.isdelete='0' ");
			 if(studyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错: 用户ID为\""+personCardNo+"\"该条记录不存在");
			 }
		}
		stu=studyList.get(0);
		stu=getRowObject(row, stu,request,"update");
		this.studyperienceDao.save(stu);	
		return true;
	}
	/*
	 * 删除数据归档
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doDeleteXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doDeleteXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		StudyExperience stu = new StudyExperience();
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String operationTime = (new Date()).toString();
		String personCardNo=XLSCellValidateUtil.getCellStringValue(row, 0);
		String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 List<StudyExperience> studyList =new ArrayList<StudyExperience>();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 studyList =studyperienceDao.findByHql("from StudyExperience study where study.personCardNo ='"+personCardNo+"' and study.sysno='"+sysno+"'  and study.isdelete='0' ");
			 if(studyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错: 身份证为\""+personCardNo+"\"该条记录不存在");
			 }
		 }else {
			 studyList =studyperienceDao.findByHql("from StudyExperience study where study.workerid ='"+personCardNo+"' and study.sysno='"+sysno+"'  and study.isdelete='0' ");
			 if(studyList.size()==0){
				 throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错: 用户ID为\""+personCardNo+"\"该条记录不存在");
			 }
		}
		stu=studyList.get(0);
		stu.setIsdelete("1");
		stu.setLastediterid(sessionInfo.getUserid());
		stu.setLasteditername(sessionInfo.getUsername());
		stu.setLastedittime(operationTime);
		this.studyperienceDao.save(stu);
		return true;
	}
	public XLSModel getXLSModel() {
		return new  XLSModel(2, 0, 8, 2,0,2, 2, 0,9);
	}

	public void removeAllByUserId(String userid) {
		List<StudyExperience> list = studyperienceDao.findByHql("from StudyExperience s where s.isdelete='0' and  s.workerid = '"+userid+"'");
		if (list.size()>0) {
			for (StudyExperience studyperience : list) {
				studyperience.setIsdelete("1");
				studyperienceDao.save(studyperience);
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
	public String createStuExSystemNo(String id){
		String sysNo=PartnerUserHander.creatSysno(PartnerUserConstants.STUDYEXPERIENCE_TABLENAME,PartnerUserConstants.STUDYEXPERIENCE_SYSNO_FLAG);
		return sysNo;
	}
	public StudyExperience updateDegree(StudyExperience stu){
		String degree=stu.getDegree();
		stu.setMasterDegree(0);
		stu.setUndergraduateDegree(0);
		stu.setCollegeDegree(0);
		stu.setSeniorDegree(0);
		stu.setTechnicalDegree(0);
		stu.setJuniorDegree(0);
		if ("1240501".equals(degree)) {//硕士及以上
			stu.setMasterDegree(1);
		}else if("1240502".equals(degree)){//本科
			stu.setUndergraduateDegree(1);
		}else if("1240503".equals(degree)){//大专
			stu.setCollegeDegree(1);
		}else if("1240504".equals(degree)){//高中
			stu.setSeniorDegree(1);
		}else if("1240505".equals(degree)){//中专
			stu.setTechnicalDegree(1);
		}else if("1240506".equals(degree)){//初中及以下
			stu.setJuniorDegree(1);
		}
		return stu;
	}
	public List<StudyExperience> findByHql(String hql) {
		hql="from StudyExperience where isdelete='0' "+hql;
		List<StudyExperience> list=new ArrayList<StudyExperience>();
		list=studyperienceDao.findByHql(hql);
		return list;
	}

	public boolean doLoadStaticSource() {
		// XXX Auto-generated method stub
		return false;
	}
}
