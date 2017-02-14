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
import com.boco.eoms.partner.personnel.dao.CertificateDao;
import com.boco.eoms.partner.personnel.mgr.CertificateMgr;
import com.boco.eoms.partner.personnel.model.Certificate;
import com.boco.eoms.partner.personnel.util.PartnerUserHander;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSToFormFileImport;
	/**
 * <p>
 * Title:人员证书管理
 * </p>
 * <p>
 * Description:人员证书管理
 * </p>
 * <p>
 * Jul 10, 2012 10:45:13 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class CertificateMgrImpl extends CommonGenericServiceImpl<Certificate> implements
		CertificateMgr,PnrProcessService {
	private CertificateDao certificateDao;

	public CertificateDao getCertificateDao() {
		return certificateDao;
	}

	public void setCertificateDao(CertificateDao certificateDao) {
		this.certificateDao = certificateDao;
		this.setCommonGenericDao(certificateDao);
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
	public Certificate validateEachRow(HSSFRow row,Certificate cert,String type){
		int cellNum=0;
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 if ("personCardNo".equals(dwinfoValidateType)) {
			XLSCellValidateUtil.checkPersonCardNoIsExist(row, cellNum++, false);//采用身份证校验
		}else {
			XLSCellValidateUtil.checkByUserIdAndGetUser(row, cellNum++);
		}
		if ("update".equals(type)) {
			cert.setSysno(XLSCellValidateUtil.checkIsNull(row, cellNum++));
		}
		cert.setType(XLSCellValidateUtil.checkIsNull(row, cellNum++));
		cert.setIssuetime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		cert.setValidity(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		XLSCellValidateUtil.compareDate(row, cellNum-2, cellNum-1, "lessThan");
		cert.setLevel(XLSCellValidateUtil.checkLength(row, cellNum++,20,true));
		cert.setIssueorg(XLSCellValidateUtil.checkLength(row, cellNum++,20,true));
		cert.setCodeno(XLSCellValidateUtil.checkLength(row, cellNum++,50,true));
		cert.setMemo(XLSCellValidateUtil.checkLength(row, cellNum++,200,true));
		return cert;
	}
	/**
	 * 
	* @Title: getRowObject 
	* @Description: 解析当前的excel文件的每一行为一个model
	* @param 
	* @return cert    返回类型 
	* @throws
	 */
	public Certificate getRowObject(HSSFRow row,Certificate cert,HttpServletRequest request,String type){
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
		 cert.setPersonCardNo(StaticMethod.null2String(user.getPersonCardNo()));
		 cert.setWorkerid(StaticMethod.null2String(user.getUserId()));
		 cert.setWorkername(StaticMethod.null2String(user.getName()));
		 cert.setDeptid(StaticMethod.null2String(user.getDeptId()));
		 cert.setAreaid(StaticMethod.null2String(user.getAreaId()));
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String operationTime = (new Date()).toString();
		if ("update".equals(type)) {
			cert.setSysno(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		}else if ("add".equals(type)) {
			cert.setAdduser(sessionInfo.getUsername());
			cert.setAdddept(sessionInfo.getDeptname());
			cert.setAddtime(operationTime);
		}
		cert.setType(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		cert.setIssuetime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		cert.setValidity(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		cert.setLevel(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		cert.setIssueorg(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		cert.setCodeno(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		cert.setMemo(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
		String certType=cert.getType();
		cert.setEgCertType(0);
		cert.setDgCertType(0);//将其置0的原因是为了避免在修改证书类型时使其数量发生变化
		cert.setZlCertType(0);
		cert.setJsCertType(0);
		cert.setOtherCertType(0);
		if ("电工证".equals(certType)) {
			cert.setEgCertType(1);
		}else if ("登高证".equals(certType)) {
			cert.setDgCertType(1);
		}else if ("制冷证".equals(certType)) {
			cert.setZlCertType(1);
		}else if ("驾驶证".equals(certType)) {
			cert.setJsCertType(1);
		}else{
			cert.setOtherCertType(1);
		}
		cert.setLastediterid(sessionInfo.getUserid());
		cert.setLasteditername(sessionInfo.getUsername());
		cert.setLastedittime(operationTime);
		cert.setIsdelete("0");
		return cert;
		
	}
	/*
	 * 新增数据归档
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doSaveXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doSaveXLSFileData(HSSFRow row,HttpServletRequest request) throws Exception {
		//取出excel数据 构建实体 写入数据库
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		Certificate cert = new Certificate();
		cert=getRowObject(row, cert, request, "add");
		//cert.setSysno(createCertSystemNo(""));
		this.certificateDao.save(cert);
		return true;
	}
	/*
	 * 修改数据归档
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doUpdateXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doUpdateXLSFileData(HSSFRow row,HttpServletRequest request) throws Exception {
		//取出excel数据 构建实体 写入数据库
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 List<Certificate> certList =new ArrayList<Certificate>();
		 String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		 String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 certList=certificateDao.findByHql("from Certificate cert where cert.personCardNo ='"+id+"' and cert.isdelete='0' and cert.sysno='"+sysno+"'");
		 }else {
			 certList=certificateDao.findByHql("from Certificate cert where cert.workerid='"+id+"' and cert.isdelete='0' and cert.sysno='"+sysno+"'");
		}
		if (certList.size()<1) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错，该条记录已经不存在");
		}
		Certificate cert=certList.get(0);
		cert=getRowObject(row, cert, request, "update");
		 certificateDao.save(cert);
		return true;
	}
/*
 * 删除数据归档
 * (non-Javadoc)
 * @see com.boco.eoms.partner.process.service.PnrProcessService#doDeleteXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
 */
	public boolean doDeleteXLSFileData(HSSFRow row,HttpServletRequest request) throws Exception {
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 List<Certificate> certList =new ArrayList<Certificate>();
		 String sysno=XLSCellValidateUtil.getCellStringValue(row, 1);
		 String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 certList=certificateDao.findByHql("from Certificate cert where cert.personCardNo ='"+id+"' and cert.isdelete='0' and cert.sysno='"+sysno+"'");
		 }else {
			 certList=certificateDao.findByHql("from Certificate cert where cert.workerid='"+id+"' and cert.isdelete='0' and cert.sysno='"+sysno+"'");
		}
		Certificate cert = new Certificate();
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String operationTime = (new Date()).toString();
		if (certList.size()<1) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行归档出错，该条记录已经不存在");
		}
		cert=certList.get(0);
		cert.setIsdelete("1");
		cert.setLastediterid(sessionInfo.getUserid());
		cert.setLasteditername(sessionInfo.getUsername());
		cert.setLastedittime(operationTime);
		this.save(cert);
		return true;
	}
/*
 * 删除数据校验
 * (non-Javadoc)
 * @see com.boco.eoms.partner.process.service.PnrProcessService#doDeleteXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
 */
	public boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception {
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 List<Certificate> certList =new ArrayList<Certificate>();
		 String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		 String sysno=XLSCellValidateUtil.checkIsNull(row, 1);
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 XLSCellValidateUtil.checkPersonCardNoIsExist(row, 0, false);//校验身份证
			 certList=certificateDao.findByHql("from Certificate cert where cert.personCardNo ='"+id+"' and cert.isdelete='0' and cert.sysno='"+sysno+"'");
		 }else {
			 XLSCellValidateUtil.checkByUserIdAndGetUser(row, 0);
			 certList=certificateDao.findByHql("from Certificate cert where cert.workerid='"+id+"' and cert.isdelete='0' and cert.sysno='"+sysno+"'");
		}
		if (certList.size()<1) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"验证出错，该条记录不存在");
		}
		return true;
	}
/*
 * 新增数据校验
 * (non-Javadoc)
 * @see com.boco.eoms.partner.process.service.PnrProcessService#doSaveXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
 */
	public boolean doSaveXLSFileValidate(HSSFRow row) throws Exception {
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 List<Certificate> certList =new ArrayList<Certificate>();
		 String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		 Certificate cert=new Certificate();
		 cert=validateEachRow(row, cert, "add");
//		 String issuetime=cert.getIssuetime();
//		 String validity=cert.getValidity();
//		 String type=cert.getType();
//		 String  level=cert.getLevel();
//		 if ("personCardNo".equals(dwinfoValidateType)) {
//			 String personCardNo=cert.getPersonCardNo();
//			 certList=this.certificateDao.findByHql("from Certificate cert where cert.personCardNo ='"+id+"' and cert.level='"+level
//						+"'and cert.issuetime='"+issuetime+"' and cert.validity='"+validity+"' and cert.isdelete='0'  and cert.type='"+type+"'");
//		 }else {
//			 certList=this.certificateDao.findByHql("from Certificate cert where cert.workerid ='"+id+"' and cert.level='"+level
//						+"'and cert.issuetime='"+issuetime+"' and cert.validity='"+validity+"' and cert.isdelete='0'  and cert.type='"+type+"'");
//		}
//		if (certList.size()>0) {
//			throw new RuntimeException("第"+(row.getRowNum()+1)+"行校验出错，该条记录已经存在");
//		}
		return true;
	}
/*
 * 修改数据校验
 * (non-Javadoc)
 * @see com.boco.eoms.partner.process.service.PnrProcessService#doUpdateXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
 */
	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception {
		
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		 String dwinfoValidateType=setList.getDwInfoValidateType();
		 PartnerUser user=new PartnerUser();
		 List<Certificate> certList =new ArrayList<Certificate>();
		 String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		 String sysno=XLSCellValidateUtil.checkIsNull(row, 1);
		 if ("personCardNo".equals(dwinfoValidateType)) {
			 XLSCellValidateUtil.checkPersonCardNoIsExist(row, 0, false);//校验身份证
			 certList=certificateDao.findByHql("from Certificate cert where cert.personCardNo ='"+id+"' and cert.isdelete='0' and cert.sysno='"+sysno+"'");
		 }else {
			 XLSCellValidateUtil.checkByUserIdAndGetUser(row, 0);
			 certList=certificateDao.findByHql("from Certificate cert where cert.workerid='"+id+"' and cert.isdelete='0' and cert.sysno='"+sysno+"'");
		}
		 Certificate cert=new Certificate();
		cert=validateEachRow(row, cert, "update");
		if (certList.size()<1) {
			throw new RuntimeException("第"+row.getRowNum()+1+"行校验出错，该条记录不存在");
		}
		return true;
	}

	public XLSModel getXLSModel() {
		return new  XLSModel(2, 0,8, 2, 0,2, 2, 0,9);
	}

	public void removeAllByUserId(String userid) {
		List<Certificate> list = certificateDao.findByHql("from Certificate c where c.workerid = '"+userid+"'");
		if (list.size()>0) {
			for (Certificate certificate : list) {
				certificate.setIsdelete("1");
				certificateDao.save(certificate);
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
	 * 移动管理员批量导入
	 */
	public ImportResult importFromFile(FormFile formFile,HttpServletRequest request) throws Exception {
		XLSToFormFileImport xlsFileImport=new XLSToFormFileImport(){
			
			public XLSModel getXLSModel() {
				return new  XLSModel(2, 0,8, 2, 0,2, 2, 0, 9);
			}
//			@Override
//			public PreparedStatement addPsBach(PreparedStatement ps,HSSFRow row, HttpServletRequest request) throws Exception {
//				Certificate cert=new Certificate();
//				cert=getRowObject(row, cert, request, "add");
//				String certType=cert.getType();
//				cert.setEgCertType(0);
//				cert.setDgCertType(0);//将其置0的原因是为了避免在修改证书类型时使其数量发生变化
//				cert.setZlCertType(0);
//				cert.setJsCertType(0);
//				cert.setOtherCertType(0);
//				if ("电工证".equals(certType)) {
//					cert.setEgCertType(1);
//				}else if ("登高证".equals(certType)) {
//					cert.setDgCertType(1);
//				}else if ("制冷证".equals(certType)) {
//					cert.setZlCertType(1);
//				}else if ("驾驶证".equals(certType)) {
//					cert.setJsCertType(1);
//				}else{
//					cert.setOtherCertType(1);
//				}
//				ps.setString(1, CommonSqlHelper.generateUUID());
//				ps.setString(2, StaticMethod.null2String(cert.getWorkerid()));
//				ps.setString(3, StaticMethod.null2String(cert.getType()));
//				ps.setString(4, StaticMethod.null2String(cert.getLevel()));
//				ps.setString(5, StaticMethod.null2String(cert.getIssueorg()));
//				ps.setString(6, StaticMethod.null2String(cert.getIssuetime()));
//				ps.setString(7, StaticMethod.null2String(cert.getValidity()));
//				ps.setString(8, StaticMethod.null2String(cert.getCodeno()));
//				ps.setString(9, StaticMethod.null2String(cert.getImagepath()));
//				ps.setString(10, StaticMethod.null2String(cert.getMemo()));
//				ps.setString(11, StaticMethod.null2String(cert.getLastedittime()));
//				ps.setString(12, StaticMethod.null2String(cert.getLastediterid()));
//				ps.setString(13, StaticMethod.null2String(cert.getLastediterid()));
//				ps.setString(14, StaticMethod.null2String(cert.getAdduser()));
//				ps.setString(15, StaticMethod.null2String(cert.getAdddept()));
//				ps.setString(16, StaticMethod.null2String(cert.getAddtime()));
//				ps.setString(17, StaticMethod.null2String(cert.getIsdelete()));
//				ps.setString(18, StaticMethod.null2String(cert.getWorkername()));
//				ps.setInt(19, cert.getEgCertType());
//				ps.setInt(20, cert.getDgCertType());
//				ps.setInt(21, cert.getZlCertType());
//				ps.setInt(22, cert.getJsCertType());
//				ps.setInt(23, cert.getOtherCertType());
//				ps.setString(24, StaticMethod.null2String(cert.getDeptid()));
//				ps.setString(25, StaticMethod.null2String(cert.getAreaid()));
//				ps.setString(26, StaticMethod.null2String(cert.getPersonCardNo()));
//				ps.addBatch();
//				return ps;
//			}
//
//			@Override
//			public boolean doValidate(HSSFRow row, HttpServletRequest request,	String type) throws Exception {
//				Certificate cert=new Certificate();
//				validateEachRow(row, cert, "add");
//				return true;
//			}
			
			@Override
			public String getBachSql() throws Exception {
				String  sql="INSERT INTO  partner_certificate" +
						"(id, workerid, type1, level1, issueorg, issuetime, validity, codeno, imagepath, memo," +
						" lastedittime, lastediterid, lasteditername, adduser, adddept, addtime, isdelete, workername, " +
						"eg_certtype, dg_certtype, zl_certtype, js_certtype, other_certtype, deptid, areaid, person_card_no) " +
						"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
				return sql;
			}
			@Override
			public boolean doSaveRow2Data(HSSFRow row,
					HttpServletRequest request) throws Exception {
				return false;
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
				 PartnerUser user;
				 if ("personCardNo".equals(validateType)) {
					 user=XLSCellValidateUtil.checkPersonCardNoIsExist(row, cellNum++, false);//采用身份证校验
				}else {
					user=XLSCellValidateUtil.checkByUserIdAndGetUser(row, cellNum++);
				}
				TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
				String operationTime = (new Date()).toString();
				String certType=XLSCellValidateUtil.checkIsNull(row, cellNum++);
				String issuetime=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
				String validity=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
				XLSCellValidateUtil.compareDate(row, cellNum-2, cellNum-1, "lessThan");
				String level=XLSCellValidateUtil.checkLength(row, cellNum++,20,true);
				String issueorg=XLSCellValidateUtil.checkLength(row, cellNum++,20,true);
				String codeno=XLSCellValidateUtil.checkLength(row, cellNum++,50,true);
				String memo=XLSCellValidateUtil.checkLength(row, cellNum++,200,true);
				int egType=0;
				int dgType=0;
				int zlType=0;
				int jsType=0;
				int otherType=0;
				if ("电工证".equals(certType)) {
					egType=1;
				}else if ("登高证".equals(certType)) {
					dgType=1;
				}else if ("制冷证".equals(certType)) {
					zlType=1;
				}else if ("驾驶证".equals(certType)) {
					jsType=1;
				}else{
					otherType=1;
				}
				ps.setString(1, CommonSqlHelper.generateUUID());
				ps.setString(2, user.getUserId());
				ps.setString(3, certType);
				ps.setString(4, level);
				ps.setString(5, issueorg);
				ps.setString(6,issuetime);
				ps.setString(7, validity);
				ps.setString(8, codeno);
				ps.setString(9,"");
				ps.setString(10, memo);
				ps.setString(11, operationTime);
				ps.setString(12, sessionInfo.getUserid());
				ps.setString(13, sessionInfo.getUsername());
				ps.setString(14, sessionInfo.getUsername());
				ps.setString(15,sessionInfo.getDeptname());
				ps.setString(16, operationTime);
				ps.setString(17, "0");
				ps.setString(18,user.getName());
				ps.setInt(19, egType);
				ps.setInt(20, dgType);
				ps.setInt(21, zlType);
				ps.setInt(22,jsType);
				ps.setInt(23,otherType);
				ps.setString(24,user.getDeptId());
				ps.setString(25,user.getAreaId());
				ps.setString(26,user.getPersonCardNo());
				ps.addBatch();
				return null;
			}
		};
		ImportResult result=xlsFileImport.xlsFileValidate(formFile,request);
		return result;
	}

	public List getCertificateByUserid(String userid) throws Exception {
		List list=new ArrayList<Certificate>();
		list= certificateDao.findByHql("from Certificate c where c.isdelete='0' and c.workerid = '"+userid+"'");
		return list;
	}
	/**
	* @Title: createCertSystemNo 
	* @Description: 生成证书的系统编号
	* @param 
	* @Time:Dec 22, 2012-11:31:47 PM
	* @Author:fengguangping
	* @return String    返回类型 
	* @throws
	 */
	public String createCertSystemNo(String id){
		String sysNo=PartnerUserHander.creatSysno(PartnerUserConstants.CERTIFICATION_TABLENAME,PartnerUserConstants.CERTIFICATION_SYSNO_FLAG);
		return sysNo;
	}

	public Map getPartnerCertStatisticsList(Integer curPage, Integer pageSize,	String whereStr) {
		return certificateDao.getPartnerCertStatisticsList(curPage, pageSize, whereStr);
	}

	public boolean doLoadStaticSource() {
		return false;
	}
}

