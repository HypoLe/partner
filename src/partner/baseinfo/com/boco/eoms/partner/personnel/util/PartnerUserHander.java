package com.boco.eoms.partner.personnel.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.personnel.mgr.StudyExperienceMgr;
import com.boco.eoms.partner.personnel.model.Certificate;
import com.boco.eoms.partner.personnel.model.DWInfo;
import com.boco.eoms.partner.personnel.model.StudyExperience;
import com.boco.eoms.partner.process.util.PnrProcessCach;

public class PartnerUserHander {
	/**
	 * 
	* @Title: handCertInfo 
	* @Description: 将证书类型映射到某一具体类型
	* @param 
	* @Time:Dec 3, 2012-10:57:26 AM
	* @Author:fengguangping
	* @return Certificate    返回类型 
	* @throws
	 */
	public Certificate  handleCertInfo(Certificate cert){
		String certType=StaticMethod.null2String(cert.getType());
		String userid=cert.getWorkerid();
		PartnerUserMgr panUserService = (PartnerUserMgr) ApplicationContextHolder.getInstance().getBean("partnerUserMgr");
		PartnerUser user =  panUserService.getPartnerUserByUserId(userid);
		if (user!=null) {
			cert.setDeptid(StaticMethod.null2String(user.getDeptId()));
			cert.setAreaid(StaticMethod.null2String(user.getAreaId()));//获得人员区域id和
			cert.setPersonCardNo(StaticMethod.null2String(user.getPersonCardNo()));//获得人员区域id和
		}
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
		return cert;
	}
	/**
	 * 
	* @Title: handleDegreeInfo 
	* @Description: 将学历类型映射到某一具体类型
	* @param 
	* @Time:Dec 3, 2012-11:13:55 AM
	* @Author:fengguangping
	* @return StudyExperience    返回类型 
	* @throws
	 */
	public StudyExperience handleDegreeInfo(StudyExperience stu){
		
		String userid=stu.getWorkerid();
		PartnerUserMgr panUserService = (PartnerUserMgr) ApplicationContextHolder.getInstance().getBean("partnerUserMgr");
		PartnerUser user =  panUserService.getPartnerUserByUserId(userid);
		if (user!=null) {
			stu.setDeptid(StaticMethod.null2String(user.getDeptId()));
			stu.setAreaid(StaticMethod.null2String(user.getAreaId()));//获得人员区域id和
			stu.setPersonCardNo(StaticMethod.null2String(user.getPersonCardNo()));//获得人员区域id和
		}
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
		return stu;
	}
	/**
	 * 
	* @Title: handleSkillLevelInfo 
	* @Description: 将技能映射到某一具体类型
	* @param 
	* @Time:Dec 3, 2012-11:25:17 AM
	* @Author:fengguangping
	* @return DWInfo    返回类型 
	* @throws
	 */
	public DWInfo handleSkillLevelInfo(DWInfo dwInfo){
		String userid=dwInfo.getWorkerid();
		PartnerUserMgr panUserService = (PartnerUserMgr) ApplicationContextHolder.getInstance().getBean("partnerUserMgr");
		PartnerUser user =  panUserService.getPartnerUserByUserId(userid);
		if (user!=null) {
			dwInfo.setDeptid(StaticMethod.null2String(user.getDeptId()));
			dwInfo.setAreaid(StaticMethod.null2String(user.getAreaId()));//获得人员区域id和
			dwInfo.setPersonCardNo(StaticMethod.null2String(user.getPersonCardNo()));//获得人员区域id和
		}
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
		return dwInfo;
	}
	public String[] getUserDeptidAndAreaid(String userid){
		PartnerUserMgr panUserService = (PartnerUserMgr) ApplicationContextHolder.getInstance().getBean("partnerUserMgr");
		PartnerUser user =  panUserService.getPartnerUserByUserId(userid);
		String[] strs = new String[4];
		strs[0] = userid;
		if (user!=null) {
			strs[1] =StaticMethod.null2String(user.getName());
			strs[2]=StaticMethod.null2String(user.getDeptId());
			strs[3]=StaticMethod.null2String(user.getAreaId());
		}
		return strs;
	}
	/*获取当前的编码，并将下一个编码存入map中*/
	public static String getSysnoAndCreatNextSysno(Map<String, String> map){
		//map=PnrProcessCach.loadCertSysNoMap();
		String startSysNo=StaticMethod.null2String(map.get("startSysNo"));
		String nextSysNo=StaticMethod.null2String(map.get("nextSysNo"));
		//System.out.println("------------------------初始系统编号为"+startSysNo+"--------------------------------------------");
		//System.out.println("------------------------当前系统编号为"+nextSysNo+"--------------------------------------------");
		String sysNoVal=nextSysNo.substring(3);
		String sysNoFlag=nextSysNo.substring(0,3);
		int sysNoMax=Integer.parseInt(sysNoVal);
		String no = String.valueOf(sysNoMax + 1);
		String preNext="";//下一个数据的编码
		if (no.length() == 1) {
			preNext = sysNoFlag+"000" + no;
		} else if (no.length() == 2) {
			preNext =  sysNoFlag+"00" + no;
		} else if (no.length() == 3) {
			preNext =  sysNoFlag+"0" + no;
		}else{
			preNext =  sysNoFlag+ no;
		}
		map.put("nextSysNo", preNext);//将下一个编码放入map中
		//System.out.println("------------------------下一个系统编号为"+preNext+"--------------------------------------------");
		return nextSysNo;
	}
	/*给定编码的前3位规则,和表名获取可用的最大编码*/
	public static String creatSysno(String tableName,String ruleName){
		int len=ruleName.length()+1;
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
	
		
		String sql = "SELECT "
		+"max( CAST(substr(sysno,"+len+",(LENGTH(sysno)-1)) "
		+"	AS DECIMAL)) AS SYSNO "
		+" FROM "
		+tableName
		+" WHERE "
		+"	sysno IS NOT NULL ";
			
			
		List<ListOrderedMap> countList = jdbcService.queryForList(sql);
		Map map = new HashMap();
		String sysNo = "";
		int sysNoMax=0;
		if(countList.size()>0){
			map = countList.get(0);
			String sysno=map.get("SYSNO").toString();//获取最大的可用的编号
			sysNoMax=Integer.parseInt(sysno);
		}else {
			 return sysNo=ruleName+"0001";
		}
		String no = String.valueOf(sysNoMax + 1);
		if (no.length() == 1) {
			sysNo = ruleName+"000" + no;
		} else if (no.length() == 2) {
			sysNo = ruleName+"00" + no;
		} else if (no.length() == 3) {
			sysNo = ruleName+"0" + no;
		}else{
			sysNo = ruleName+ no;
		}
		return sysNo;
	}
}
