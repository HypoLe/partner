package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.deviceManagement.common.utils.PinYinUtil;
import com.boco.eoms.partner.baseinfo.dao.PartnerUserDao;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.util.PersonalCardValidity;
import com.boco.eoms.partner.personnel.mgr.CertificateMgr;
import com.boco.eoms.partner.personnel.mgr.DWInfoMgr;
import com.boco.eoms.partner.personnel.mgr.PXExperienceMgr;
import com.boco.eoms.partner.personnel.mgr.RewardMgr;
import com.boco.eoms.partner.personnel.mgr.StudyExperienceMgr;
import com.boco.eoms.partner.personnel.mgr.WorkExperienceMgr;
import com.boco.eoms.partner.personnel.model.DWInfo;
import com.boco.eoms.partner.personnel.util.ExcelUtil;
import com.boco.eoms.partner.personnel.util.MyUtil;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSToFormFileImport;
import com.googlecode.genericdao.search.Search;

/**
 * <p>
 * Title:��f��Ϣ
 * </p>
 * <p>
 * Description:��f��Ϣ
 * </p>
 * <p>
 * Tue Feb 10 17:33:14 CST 2009
 * </p>
 * 
 * @author liujinlong
 * @version 3.5
 * 
 * @author lihaolin
 * @version 3.6
 */
public class PartnerUserMgrImpl  extends CommonGenericServiceImpl<PartnerUser> implements PartnerUserMgr,PnrProcessService {
 
	private PartnerUserDao  partnerUserDao;
 	
	public PartnerUserDao getPartnerUserDao() {
		return this.partnerUserDao;
	}
 	
	public void setPartnerUserDao(PartnerUserDao partnerUserDao) {
		this.setCommonGenericDao(partnerUserDao);
		this.partnerUserDao = partnerUserDao;
	}
 	
    public List getPartnerUsers() {
    	return partnerUserDao.getPartnerUsers();
    }
    
    public PartnerUser getPartnerUser(final String id) {
    	return partnerUserDao.getPartnerUser(id);
    }
    
    public void savePartnerUser(PartnerUser partnerUser) {
    	
    	if(partnerUser.getDeleted().equals("1")){
    		String userid = partnerUser.getUserId();
    		CertificateMgr certificateMgr = (CertificateMgr) ApplicationContextHolder.getInstance().getBean("refcertificateMgr");
    		DWInfoMgr dwinfoMgr = (DWInfoMgr) ApplicationContextHolder.getInstance().getBean("refdwinfoMgr");
    		PXExperienceMgr pxexperienceMgr = (PXExperienceMgr) ApplicationContextHolder.getInstance().getBean("refpxexperienceMgr");
    		RewardMgr rewardMgr = (RewardMgr) ApplicationContextHolder.getInstance().getBean("refrewardMgr");
    		StudyExperienceMgr studyExperienceMgr = (StudyExperienceMgr) ApplicationContextHolder.getInstance().getBean("refstudyExperienceMgr");
    		certificateMgr.removeAllByUserId(userid);
    		dwinfoMgr.removeAllByUserId(userid);
    		pxexperienceMgr.removeAllByUserId(userid);
    		rewardMgr.removeAllByUserId(userid);
    		studyExperienceMgr.removeAllByUserId(userid);
    	}
    	
    	partnerUserDao.savePartnerUser(partnerUser);
    }
    
    public void removePartnerUser(final String id) {
    	partnerUserDao.removePartnerUser(id);
    }
    
    public Map getPartnerUsers(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return partnerUserDao.getPartnerUsers(curPage, pageSize, whereStr);
	}
    
    //add by chenruoke
    public Map getPartnerUsersForGis(final Integer curPage, final Integer pageSize,
			final String whereStr , final String toDay) {
		return partnerUserDao.getPartnerUsersForGis(curPage, pageSize, whereStr, toDay);
	}
    
    //判断人力信息userId 是否唯一
    public Boolean isunique(final String userId){
    	return partnerUserDao.isunique(userId);
    }
    
    public PartnerUser getPartnerUserByUserId(String userId){
    	
    	return partnerUserDao.getPartnerUserByUserId(userId);
    }
    
    public PartnerUser getPartnerUserByUserId(String userId,String password){
    	String hql = "from PartnerUser where userId='"+userId+"' and userPassword='"+password+"' and postState !='112020403'";
		List list =partnerUserDao.findByHql(hql);
		if(list.size()>0){
			return (PartnerUser)list.get(0);
		}
		return null;
    }
    
	//批量删除某地市某厂商下所有的人力信息，参数是treeNodeId
	public void removePartnerUserByTreeNodeId(final String treeNodeId){
		partnerUserDao.removePartnerUserByTreeNodeId(treeNodeId);
	}
	public List getPartnerUsers(final String where){
		return partnerUserDao.getPartnerUsers(where);
	}
	public List getUserByCompidNoSelf(String deptid, String userid) {
		List list = new ArrayList();
		list = partnerUserDao.getUserByCompidNoSelf(deptid, userid);
		return list;
	}
	
	/**
	 * 得到部门的所有USER
	 * 
	 * @param deptid
	 * @return
	 */
	public List getUserByCompids(String deptid) {
		List list = new ArrayList();
		list = partnerUserDao.getUserByCompids(deptid);
		return list;
	}
	
	/**
	 * 审批
	 */
	public void approve(String id,String approveStatus){
		PartnerUser u = partnerUserDao.getPartnerUser(id);
		u.setApproveStatus(Integer.parseInt(approveStatus));
		partnerUserDao.save(u);
	}
	
	public void approveAll(String id,String approveStatus){
		
	}
	
	/**
	 * 根据部门ID查询该部门所有子孙部门的代维人员
	 */
	public List<PartnerUser> findAllPnrTestters(String deptid){
		List<PartnerUser> users = new ArrayList<PartnerUser>();
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
		String hql = " deptMagId like '"+deptid+"%'";
		List<PartnerDept> list = partnerDeptMgr.getPartnerDeptsByHql(hql);
		for (PartnerDept partnerDept : list) {
			String hql2 = " and approveStatus =1 and postState='112020403' and partnerid='" ;
			hql2 += partnerDept.getId()+"'";
			List<PartnerUser> puList = this.getPartnerUsers(hql2);
			users.addAll(puList);
		}
		return users;
	}
	
	/**
	 * 根据部门ID查询该部门所有子孙部门的代维人员总数
	 */
	public int findAllPnrTesttersCount(String deptid){
		List<PartnerUser> users = new ArrayList<PartnerUser>();
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
		String hql = " deptMagId like '"+deptid+"%'";
		List<PartnerDept> list = partnerDeptMgr.getPartnerDeptsByHql(hql);
		
		for (PartnerDept partnerDept : list) {
			String hql2 = " and approveStatus =1 and postState='112020403' and partnerid='" ;
			hql2 += partnerDept.getId()+"'";
			List<PartnerUser> puList = this.getPartnerUsers(hql2);
			users.addAll(puList);
		}
		return users.size();
	}
	
	public PartnerUser getPartnerUserByPersonCardNo(String personCardNo){
		String hql = "from PartnerUser where deleted='0' and personCardNo='"+personCardNo+"'";
		List list = partnerUserDao.findByHql(hql);
		if(list.size()>0){
			return (PartnerUser)list.get(0);
		}
		return null;
	}
	
	/**
	 * 验证身份证号是否唯一
	 * @param personCardNo
	 * @return
	 */
	public boolean isPersonCardNoUnique(String personCardNo){
		PartnerUser pu = getPartnerUserByPersonCardNo(personCardNo);
		return pu == null ? true : false;
	}
	/**
	 * 
	* @Title: isPersonCardNoUnique 
	* @Description: 验证身份证号是否唯一：修改的时候需要添加userid
	* @param 
	* @Time:Dec 22, 2012-5:51:51 PM
	* @Author:fengguangping
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean isPersonCardNoUnique(String personCardNo,String userid){
		PartnerUser pu = getPartnerUserByPersonCardNo(personCardNo,userid);
		return pu == null ? true : false;
	}
	private PartnerUser getPartnerUserByPersonCardNo(String personCardNo,String userid) {
		String hql = "from PartnerUser where personCardNo='"+personCardNo+"' and userId <>'"+userid+"'";
		List list = partnerUserDao.findByHql(hql);
		if(list.size()>0){
			return (PartnerUser)list.get(0);
		}
		return null;
	}
	public Search searchPrivFilter(Search search, String userId,String deptId,HttpServletRequest request){
		search.addFilterNotEqual("deleted", "1");
		//获取区域id作为删选条件
		HashMap<String, String> map=new HashMap<String, String>();
		try {
			map = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
			String flag=map.get("isPersonnel");
			if (flag.equals("y")) {//代维人员
				search.addFilterLike("deptId", map.get("deptMagId")+"%");
			}else if (flag.equals("n")) {//移动人员
				search.addFilterLike("areaId", map.get("areaId")+"%");
			}else if (flag.equals("admin")) {//admin
				
			}
		} catch (Exception e) {
			throw new RuntimeException("非法用户!");
		}
		return search;
	}
	/*
	 * (non-Javadoc)
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doDeleteXLSFileValidate(org.apache.poi.hssf.usermodel.HSSFRow)
	 */
	public boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception {
		XLSCellValidateUtil.checkUserId(row, 0);
		return true;
	}
	/**
	 * 
	* @Title: getRowObject 
	* @Description: 解析excel文件的当前行为一个model
	* @param 
	* @Time:Nov 26, 2012-2:13:53 PM
	* @Author:fengguangping
	* @return PartnerUser    返回类型 
	* @throws
	 */
	public PartnerUser getRowObject(HSSFRow row,PartnerUser partnerUser,String type) throws Exception{
		int cellNum=0;
		if ("update".equals(type)) {
			partnerUser.setUserId(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			partnerUser.setUpdateTime(CommonUtils.toEomsStandardDate(new Date()));
		}
		partnerUser.setName(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//员工姓名
		partnerUser.setSex(XLSCellValidateUtil.checkDictName(row, cellNum++,"1120202",false));//性别
		partnerUser.setNativePlace(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//籍贯
		if (!"".equals(XLSCellValidateUtil.nullCell2String(row, cellNum++))) {
			partnerUser.setNationality(XLSCellValidateUtil.checkDictName(row, cellNum-1, "12408",false));//民族
		}else {
			partnerUser.setNationality("");
		}
		String personCardNo=XLSCellValidateUtil.getCellStringValue(row,cellNum++);//身份证
		partnerUser.setPersonCardNo(personCardNo);
		if(!"".equals(personCardNo)){
//			身份证可能为空了
			String year1 =  personCardNo.substring(6,10);   
			String month1 = personCardNo.substring(10,12);   
			String day1 = personCardNo.substring(12,14);
			partnerUser.setBirthdey(year1+"-"+month1+"-"+day1);//获取生日
			Date now=new Date();
			int age=now.getYear()+1900-Integer.parseInt(year1);
			partnerUser.setAge(age+"");//获取年龄
		}
		partnerUser.setMobilePhone(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//手机号码
		partnerUser.setGroupPhone(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//集团短号
		partnerUser.setEmail(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//Email;
		partnerUser.setGraduateSchool(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//毕业院校;
		partnerUser.setLearnSpecialty(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//所学专业;
		partnerUser.setDiploma(XLSCellValidateUtil.checkDictName(row, cellNum++, "12405",false));//学历；
		partnerUser.setWorkTime(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//第一次参加工作时间
		partnerUser.setDeptName(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//所属代维公司名称;
		ConcurrentMap map=PnrProcessCach.deptCompanyCach;
		if(map==null||map.size()==0){
			PnrProcessCach.reloadCompanyCache();
			map=PnrProcessCach.deptCompanyCach;
		}
		PartnerDept dept=(PartnerDept)map.get(partnerUser.getDeptName());
		if(dept==null||dept.getName()==null){
			PnrProcessCach.reloadCompanyCache();
			map=PnrProcessCach.deptCompanyCach;
		}
		partnerUser.setAreaId(dept.getAreaId());
		partnerUser.setDeptId(dept.getDeptMagId());
		partnerUser.setAreaName(dept.getAreaName());
		String orgId=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
		partnerUser.setUserNo(orgId);
		partnerUser.setPostState(XLSCellValidateUtil.checkDictName(row, cellNum++, "12409",false));//在职状态;
		/*如果是离职，需要对离职时间进行处理 begin*/
		String postState=StaticMethod.null2String(partnerUser.getPostState());
		if ("1240903".equals(postState)) {
			String leaveTime=XLSCellValidateUtil.getCellStringValue(row, cellNum++);//15离职时间
			String leaveReason=XLSCellValidateUtil.getCellStringValue(row, cellNum++);//16离职原因
			String leaveTimeYear=leaveTime.substring(0,4);
			String leaveTimeMonth=leaveTime.substring(5,7);
			String  day=leaveTime.substring(8,10);
			String time=leaveTimeYear+leaveTimeMonth+day;
			long leaveTimeLongValue=Long.parseLong(time);
			partnerUser.setLeavaTime(leaveTime);
			partnerUser.setLeavaReason(leaveReason);
			partnerUser.setLeavaTimeYear(leaveTimeYear);
			partnerUser.setLeavaTimeMonth(leaveTimeMonth);
			partnerUser.setLeavaTimeLongValue(leaveTimeLongValue);
		}else {
			cellNum++;//跳过；
			cellNum++;//跳过
		}
		if ("add".equals(type)) {//如果是新增的一条离职信息，将保存时间也设置为当前时间
			partnerUser.setSaveTime(CommonUtils.toEomsStandardDate(new Date()));
			String saveTimeYear=partnerUser.getSaveTime().substring(0,4);
			String saveTimeMonth=partnerUser.getSaveTime().substring(5,7);
			String  day2=partnerUser.getSaveTime().substring(8,10);
			String time2=saveTimeYear+saveTimeMonth+day2;
			long saveTimeLongValue=Long.parseLong(time2);
			partnerUser.setSaveTimeYear(saveTimeYear);
			partnerUser.setSaveTimeMonth(saveTimeMonth);
			partnerUser.setSaveTimeLongValue(saveTimeLongValue);
		}
		//黑名单表示;
		String blackListFlag=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
		if ("是".equals(blackListFlag)){
			partnerUser.setBlacklist("1");
		}else {
			partnerUser.setBlacklist("0");
		}
		partnerUser.setRemark(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//备注;
		partnerUser.setUserId(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//人员帐号;
//		代维标识
		String isPartenrString=XLSCellValidateUtil.getCellStringValue(row, cellNum++);
		if ("自维".equals(isPartenrString)){
			partnerUser.setIsPartner(1);
		}else {
			partnerUser.setIsPartner(0);
		}
		partnerUser.setDeleted("0");
		return partnerUser;
	}
	/**
	 * 
	* @Title: validateEachRow 
	* @Description: 验证当前行
	* @param 
	* @Time:Nov 26, 2012-3:10:11 PM
	* @Author:fengguangping
	* @return PartnerUser    返回类型 
	* @throws
	 */
	public PartnerUser validateEachRow(HSSFRow row,PartnerUser partnerUser,String type) throws Exception{
		int cellNum=0;
		String userid="";
		if ("update".equals(type)) {//修改数据时校验,比新增多一个userid校验;
			String[] userinfo=XLSCellValidateUtil.checkUserId(row, cellNum++);
			userid=userinfo[0];//获取userid
		}
		//新增数据时校验
		partnerUser.setName(XLSCellValidateUtil.checkLength(row,cellNum++,10,false));//0员工姓名
		partnerUser.setSex(XLSCellValidateUtil.checkDictName(row, cellNum++, "1120202",false));//1性别
		partnerUser.setNativePlace(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//2籍贯
		if (!"".equals(XLSCellValidateUtil.nullCell2String(row, cellNum++))) {
			partnerUser.setNationality(XLSCellValidateUtil.checkDictName(row, cellNum-1, "12408",false));//3民族
		}
		partnerUser.setPersonCardNo(XLSCellValidateUtil.checkLength(row, cellNum++, 18, true));//4身份证:改成非必填
		//验证身份证
		/*String personCardNo=partnerUser.getPersonCardNo();
		try {
			this.checkIdCord(personCardNo);
		} catch (RuntimeException e) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行第5列验证出错"+e.getMessage());
		}
		if ("update".equals(type)) {//更新时将userid传入查询
			if (!isPersonCardNoUnique(personCardNo,userid)) {
				throw new RuntimeException("第"+(row.getRowNum()+1)+"行第5列验证出错,该人员的身份证信息与系统身份证信息不相符!");
			}
		}else {
			if (!isPersonCardNoUnique(personCardNo)) {
				throw new RuntimeException("第"+(row.getRowNum()+1)+"行第5列验证出错,该身份证已经存在!");
			}
			if(!PersonalCardValidity.checkIDCard(personCardNo)){
				throw new RuntimeException("第"+(row.getRowNum()+1)+"行第5列验证出错,该身份证不合法!");
			}
		}*/
		partnerUser.setMobilePhone(XLSCellValidateUtil.checkNumber(row, cellNum++,false));//5手机号码
		partnerUser.setGroupPhone(XLSCellValidateUtil.checkNumber(row, cellNum++, true));//6集团短号可以为空;不为空时校验是否是数值
		partnerUser.setEmail(XLSCellValidateUtil.checkEmail(row, cellNum++,true));//7Email;
		partnerUser.setGraduateSchool(XLSCellValidateUtil.checkLength(row, cellNum++, 100, true));//8毕业院校;
		partnerUser.setLearnSpecialty(XLSCellValidateUtil.checkLength(row, cellNum++, 100, true));//9所学专业;
		partnerUser.setDiploma(XLSCellValidateUtil.checkDictName(row, cellNum++, "12405",false));//10学历；
		if (!"".equals(XLSCellValidateUtil.getCellStringValue(row, cellNum++))) {
			partnerUser.setWorkTime(XLSCellValidateUtil.checkDate(row, cellNum-1));//11第一次参加工作时间
		}
		ConcurrentMap map=PnrProcessCach.deptCompanyCach;
		if(map==null||map.size()==0){
			PnrProcessCach.reloadCompanyCache();
			map=PnrProcessCach.deptCompanyCach;
		}
		String orgName=XLSCellValidateUtil.getCellStringValue(row, cellNum++);//12
		String orgId=XLSCellValidateUtil.getCellStringValue(row, cellNum++);//13
		if(!map.containsKey(orgName)){
			PnrProcessCach.reloadCompanyCache();
			map=PnrProcessCach.deptCompanyCach;
		}
		if(!map.containsKey(orgName)){
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行第"+(cellNum-1)+"列验证出错,组织名称\""+orgName+"\"不存在!");
		}else {
			PartnerDept dept=(PartnerDept)map.get(orgName);
			if(!dept.getOrganizationNo().equals(orgId)){
				throw new RuntimeException("第"+(row.getRowNum()+1)+"行第"+cellNum+"列验证出错,组织名称\""+orgName+"\"与组织编码不相符");
			}
		}
		//PartnerDept dept = this.checkDeptNameFromDeptCode(orgId,orgName);
		partnerUser.setPostState(XLSCellValidateUtil.checkDictName(row, cellNum++, "12409", false));//14在职状态;
		/*如果是离职，需要对离职时间进行处理 begin*/
		String postState=StaticMethod.null2String(partnerUser.getPostState());
		if ("1240903".equals(postState)) {
			//获取离职时间和离职原因
			XLSCellValidateUtil.checkDate(row, cellNum++);//15离职时间
			XLSCellValidateUtil.checkLength(row, cellNum++, 200, false);//16离职原因
			/*如果是离职，需要对离职时间进行处理 end*/
		}else {
			cellNum++;//跳过离职时间;
			cellNum++;//跳过离职原因;
		}
		partnerUser.setBlacklist(XLSCellValidateUtil.getCellStringValue(row, cellNum++));//17黑名单表示;
		String blackListFlag=partnerUser.getBlacklist();
		if (!"是".equals(blackListFlag)&&!"否".equals(blackListFlag)) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行第"+cellNum+"列验证出错,内容必须为\"是\"或者\"否\"");
		}
		partnerUser.setRemark(XLSCellValidateUtil.checkLength(row, cellNum++, 200, true));//18备注;
		partnerUser.setUserId(XLSCellValidateUtil.checkLength(row, cellNum++, 20, true));//19人员账号;
		String isPartnerString = XLSCellValidateUtil.getCellStringValue(row, cellNum++);//20代维标识;
		if (!"自维".equals(isPartnerString)&&!"代维".equals(isPartnerString)) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行第"+cellNum+"列验证出错,内容必须为\"自维\"或者\"代维\"");
		}else if("自维".equals(isPartnerString)){
			partnerUser.setIsPartner(1);
		}else{
			partnerUser.setIsPartner(0);
		}
		return partnerUser;
	}
	public boolean doSaveXLSFileValidate(HSSFRow row) throws Exception {
		PartnerUser partnerUser=new PartnerUser();
		validateEachRow(row, partnerUser, "add");
		return true;
	}

	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception {
		PartnerUser partnerUser=new PartnerUser();
		
		validateEachRow(row, partnerUser, "update");
		return true;
	}

	public boolean doSaveXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		PartnerUser partnerUser = new PartnerUser();
		ITawSystemUserManager userMgr = (ITawSystemUserManager) ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");
		partnerUser=getRowObject(row, partnerUser, "add");
		String userId = partnerUser.getUserId();//人员id
		if("".equals(userId)){
			String userName=partnerUser.getName();//获取用户名然后转化为userid;
			String pingyinUserId = PinYinUtil.getPinYin(userName);
			int n = 0;
			//如果userid已经存在了则在其后面累加数字直到不重复为止
			String pingyinUserIdTemp = pingyinUserId;
			while(!this.isunique(pingyinUserIdTemp)&&userMgr.getUserByuserid(pingyinUserIdTemp).getId()!=null){//如果存在
				n++;
				pingyinUserIdTemp = pingyinUserId + n;
			}
			partnerUser.setUserId(pingyinUserIdTemp);//设置用户的userid
		}else{
			String userIdTemp = userId;
			if(!this.isunique(userIdTemp)&&userMgr.getUserByuserid(userIdTemp).getId()!=null){
				throw new RuntimeException("第"+(row.getRowNum()+1)+"行第21列人员帐号存在");
			}else{
				partnerUser.setUserId(userIdTemp);//设置用户的userid
			}
		}
		 
		if ("".equals(StaticMethod.null2String(partnerUser.getUserPassword()))) {
			;
//			partnerUser.setUserPassword("123456");
			partnerUser.setUserPassword(new Md5PasswordEncoder().encodePassword("Boco42@#", new String()));
			
		}
		 partnerUser.setPhone(partnerUser.getMobilePhone());
		//参加工作时间，deptid,所属公司名，areaname,areaId，在职状态，是否黑名单
		 String orgName=XLSCellValidateUtil.getCellStringValue(row, 12);
		 String orgId=XLSCellValidateUtil.getCellStringValue(row, 13);
		PartnerDept dept=new PartnerDept();;
		try {
			dept = this.checkDeptNameFromDeptCode(orgId,orgName);
		} catch (RuntimeException e) {
			throw  new RuntimeException("第"+(row.getRowNum()+1)+"行第13列与14列验证出错,"+e.getMessage());
		}
		partnerUser.setDeptId(dept.getDeptMagId());
		partnerUser.setPartnerid(dept.getId());
		partnerUser.setDeptName(dept.getName());
		partnerUser.setAreaName(dept.getAreaName());
		partnerUser.setAreaId(dept.getAreaId());
		 //生日，年龄，用户编码,密码,备注
		String idCardNo=partnerUser.getPersonCardNo();
		if(!"".equals(idCardNo)){
//			身份证可能会空
			partnerUser.setBirthdey(this.getBornDateFromIdCard(idCardNo));
			partnerUser.setAge(this.getAgeFromIdCard(idCardNo));
		}
		partnerUser.setUserNo(this.getOrgCodeFromDeptOrgCode(dept.getOrganizationNo()));
		//删除标识，审批通过
		partnerUser.setDeleted("0");
		partnerUser.setApproveStatus(1);
		this.partnerUserDao.savePartnerUser(partnerUser);
		insertPartnerUserToTawSystemUser(partnerUser,null);
		//更新缓存
		PnrProcessCach.deptUserCach.put(partnerUser.getUserId(), partnerUser);
		PnrProcessCach.deptUserCach.put(partnerUser.getPersonCardNo(), partnerUser);
		PnrProcessCach.deptUserCach.put(partnerUser.getName(), partnerUser);
		return true;
	}
	/*
	 * (non-Javadoc)修改代维人员信息
	 * @see com.boco.eoms.partner.process.service.PnrProcessService#doUpdateXLSFileData(org.apache.poi.hssf.usermodel.HSSFRow, javax.servlet.http.HttpServletRequest)
	 */
	public boolean doUpdateXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		PartnerUser partnerUser = new PartnerUser();
		partnerUser=getRowObject(row, partnerUser, "update");
		String userid=partnerUser.getUserId();
		ITawSystemUserManager userMgr = (ITawSystemUserManager) ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");
		String strWhere=" and partnerUser.userId='"+userid+"'";
		List<PartnerUser>list=getPartnerUsers(strWhere);
		if (list.size()<1) {
			throw new RuntimeException("第"+row.getRowNum()+1+"行修改出错，该记录已经被删除或者存在多条记录");
		}
		partnerUser.setId(StaticMethod.null2String(list.get(0).getId()));
		String userName=partnerUser.getName();//获取用户名然后转化为userid;
		String pingyinUserId = PinYinUtil.getPinYin(userName);
		 int n = 0;
		 //如果userid已经存在了则在其后面累加数字直到不重复为止
		 String pingyinUserIdTemp = pingyinUserId;
		 while(!this.isunique(pingyinUserIdTemp)&&userMgr.getUserByuserid(pingyinUserIdTemp).getId()!=null){//如果存在
			 n++;
			 pingyinUserIdTemp = pingyinUserId + n;
		 }
		 if ("".equals(StaticMethod.null2String(partnerUser.getUserPassword()))) {
			partnerUser.setUserPassword("123456");
		}
		 partnerUser.setPhone(partnerUser.getMobilePhone());
		//参加工作时间，deptid,所属公司名，areaname,areaId，在职状态，是否黑名单
		 String orgName=XLSCellValidateUtil.getCellStringValue(row, 13);
		 String orgId=XLSCellValidateUtil.getCellStringValue(row, 14);
		 PartnerDept dept=new PartnerDept();;
		try {
			dept = this.checkDeptNameFromDeptCode(orgId,orgName);
		} catch (RuntimeException e) {
			throw  new RuntimeException("第"+(row.getRowNum()+1)+"行第14列与15列验证出错,"+e.getMessage());
		}
		partnerUser.setDeptId(dept.getDeptMagId());
		partnerUser.setPartnerid(dept.getId());
		partnerUser.setDeptName(dept.getName());
		partnerUser.setAreaName(dept.getAreaName());
		partnerUser.setAreaId(dept.getAreaId());
		 //生日，年龄，用户编码,备注
		String idCardNo=partnerUser.getPersonCardNo();
		partnerUser.setBirthdey(this.getBornDateFromIdCard(idCardNo));
		partnerUser.setAge(this.getAgeFromIdCard(idCardNo));
		partnerUser.setUserNo(this.getOrgCodeFromDeptOrgCode(dept.getOrganizationNo()));
		//删除标识，审批通过
		partnerUser.setDeleted("0");
		partnerUser.setApproveStatus(1);
		this.partnerUserDao.savePartnerUser(partnerUser);
		insertPartnerUserToTawSystemUser(partnerUser,null);
		updatePartnerUserSkillDeptidAndAreaid(partnerUser.getId());
		return true;
			
	}
	/**
	 * 删除代维人员信息
	 */
	public boolean doDeleteXLSFileData(HSSFRow row, HttpServletRequest request) throws Exception {
		String userid=XLSCellValidateUtil.checkIsNull(row, 0);
		String strWhere=" and partnerUser.userId='"+userid+"'";
		List<PartnerUser>list=getPartnerUsers(strWhere);
		if (list.size()!=1) {
			throw new RuntimeException("第"+(row.getRowNum()+1)+"行删除出错，该记录已经被删除或者存在多条记录");
		}
		PartnerUser partnerUser=new PartnerUser();
		partnerUser=list.get(0);
		partnerUser.setDeleted("1");
		this.partnerUserDao.savePartnerUser(partnerUser);
		 deletePartnerUserInfoAndSkillInfo(partnerUser.getUserId());//删除相应的技能信息;
		 //从taw_system_user中将该条数据删除
		 ITawSystemUserManager userMgr = (ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");
		TawSystemUser tawSystemUser=userMgr.getUserByuserid(userid);
		if (tawSystemUser!=null&&tawSystemUser.getId()!=null) {
			tawSystemUser.setDeleted("1");
			userMgr.saveTawSystemUser(tawSystemUser);
		}
		return true;
	}

	public XLSModel getXLSModel() {
		return new XLSModel(2, 0, 19, 2, 0, 1, 2, 0, 20);
	}
	
	
	/**
	 * 通过身份证号码 获得出生日期
	 * @param cardNo
	 * @return
	 */
	private String getBornDateFromIdCard(String cardNo){
		String dateStr = cardNo.substring(6, 14);
		String year = dateStr.substring(0, 4);
		String month = dateStr.substring(4, 6);
		String d = dateStr.substring(6,8);
		return year+"-"+month+"-"+d;
	}
	
	/**
	 * 通过身份证号 获得年龄
	 * @param cardNo
	 * @return
	 */
	private String getAgeFromIdCard(String cardNo){
		String bordDateYear = this.getBornDateFromIdCard(cardNo).substring(0, 4);
		String localDateYear = MyUtil.getStrTime(new Date()).substring(0, 4);
		int age = Integer.parseInt(localDateYear) - Integer.parseInt(bordDateYear);
		return age+"";
	}

	/**
	 * 身份证 校验  简单校验（只对格式，出生日期和数据库是否重复进行验证）
	 * @param cardNo
	 */
	private void checkIdCord(String cardNo){
		if(cardNo.length()!=18)
			throw new RuntimeException("身份证号码位数有误，必须为18位");
		ExcelUtil.checkNumber(cardNo.substring(0, 16));  //检验前17位 是否为数字
		//检验最后一位是否为数字或者 X
		String lastChar = null;
		try {
			lastChar = ExcelUtil.checkNumber(cardNo.substring(16, 17));
		} catch (Exception e) {
			if(!"X".equals(lastChar))
				throw new RuntimeException("身份证号码 最后一位有误");
		}
//		if(isPersonCardNoUnique(cardNo)==false){
//			throw new RuntimeException("身份证号码 已存在");
//		}
		String dateFromIdCard = cardNo.substring(6,14);
		String regex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})" +
								"(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|" +
									"(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)";
//		if(!dateFromIdCard.matches(regex))
//			throw new RuntimeException("身份证号码 出生日期部分 "+dateFromIdCard+" 不正确");
	}
	
	/**
	 * 通过所属公司编码 得到公司信息
	 * @return  PartnerDept
	 */
	private PartnerDept checkDeptNameFromDeptCode(String code,String excelDeptName){
		if(MyUtil.isEmpty(code))
			throw new RuntimeException("所属公司编码为空");
		PartnerDeptMgr deptMgr = (PartnerDeptMgr) ApplicationContextHolder	.getInstance().getBean("partnerDeptMgr");
		PartnerDept dept = new PartnerDept();
		try {
			dept = deptMgr.getDeptByOrgCode(code);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		if(!excelDeptName.equals(dept.getName()))
			throw new RuntimeException("所属公司编码对应的公司名与填写的不一致，请核对");
		return dept;
	}
	/**
	 * 根据部门编码 生成用户编码
	 * @param deptOrgCode
	 * @return
	 */
	public String getOrgCodeFromDeptOrgCode(String deptOrgCode){
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
																		.getInstance().getBean("commonSpringJdbcService");
		String sql = "select count(*) from pnr_user where userno like '"+deptOrgCode+"-____'";
		DecimalFormat codeFormat = new DecimalFormat("0000");
		int counts =  jdbcService.queryForInt(sql);
		if(counts==9999)
			throw new RuntimeException("所属部门数量达到上限值 9999");
		return deptOrgCode+"-"+codeFormat.format(counts+1);
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
	 * 逻辑删除代维人员相关的技能信息
	 */
	public void deletePartnerUserInfoAndSkillInfo(String id) {
		PartnerUser	partnerUser=this.getPartnerUser(id);
		String workerid=partnerUser.getUserId();
		String personCardNo=partnerUser.getPersonCardNo();
		CertificateMgr certificateMgr=(CertificateMgr)ApplicationContextHolder.getInstance().getBean("refcertificateMgr");
		DWInfoMgr dwInfoMgr=(DWInfoMgr)ApplicationContextHolder.getInstance().getBean("refdwinfoMgr");
		List<DWInfo> dwList=dwInfoMgr.findByHql(" and workerid='"+workerid+"'");
		PXExperienceMgr pxExperienceMgr=(PXExperienceMgr)ApplicationContextHolder.getInstance().getBean("refpxexperienceMgr");
		StudyExperienceMgr studyExperienceMgr=(StudyExperienceMgr)ApplicationContextHolder.getInstance().getBean("refstudyExperienceMgr");
		RewardMgr rewardMgr=(RewardMgr)ApplicationContextHolder.getInstance().getBean("refrewardMgr");
		WorkExperienceMgr workExperienceMgr=(WorkExperienceMgr)ApplicationContextHolder.getInstance().getBean("workExperienceMgr");
		ITawSystemUserManager userMgr = (ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean("ItawSystemUserSaveManagerFlush");
		certificateMgr.removeAllByUserId(workerid);//逻辑删除所有的关联证书信息；
		//dwInfoMgr.removeAllByUserId(workerid);//逻辑删除所有关联的技能信息
		dwInfoMgr.removeAllByUserId(workerid,false);//物理删除所有关联的技能信息
		if (dwList.size()>0) {
			for (DWInfo d : dwList) {
				String professional=d.getProfessional();
				PnrProcessCach.dwInfoListCach.remove("userId_"+workerid+",pro_"+professional, professional);
				PnrProcessCach.dwInfoListCach.remove("personCardNo_"+personCardNo+",pro_"+professional, professional);
			}
		}
		pxExperienceMgr.removeAllByUserId(workerid);//逻辑删除所有关联的培训经历信息
		studyExperienceMgr.removeAllByUserId(workerid);//逻辑删除所有关联的教育经历信息
		rewardMgr.removeAllByUserId(workerid);//逻辑删除所有关联的奖励信息
		workExperienceMgr.removeAllByUserId(workerid);//逻辑删除所有关联的工作经历信息
	}
	/**
	 *修改相应的人员技能里面的areaid和deptid;
	 */
	public void updatePartnerUserSkillDeptidAndAreaid(String id) {
		PartnerUser	partnerUser=this.getPartnerUser(id);
		String workerid=StaticMethod.null2String(partnerUser.getUserId());
		String areaid=StaticMethod.null2String(partnerUser.getAreaId());
		String deptid=StaticMethod.null2String(partnerUser.getDeptId());
		CertificateMgr certificateMgr=(CertificateMgr)ApplicationContextHolder.getInstance().getBean("refcertificateMgr");
		DWInfoMgr dwInfoMgr=(DWInfoMgr)ApplicationContextHolder.getInstance().getBean("refdwinfoMgr");
		PXExperienceMgr pxExperienceMgr=(PXExperienceMgr)ApplicationContextHolder.getInstance().getBean("refpxexperienceMgr");
		StudyExperienceMgr studyExperienceMgr=(StudyExperienceMgr)ApplicationContextHolder.getInstance().getBean("refstudyExperienceMgr");
		WorkExperienceMgr workExperienceMgr=(WorkExperienceMgr)ApplicationContextHolder.getInstance().getBean("workExperienceMgr");
		RewardMgr rewardMgr=(RewardMgr)ApplicationContextHolder.getInstance().getBean("refrewardMgr");
		String sql1="update partner_certificate set deptid='"+deptid+"',areaid='"+areaid+"' where workerid='"+workerid+"'";
		String sql2="update partner_dwinfo set deptid='"+deptid+"',areaid='"+areaid+"' where workerid='"+workerid+"'";
		String sql3="update panter_pxexperience set deptid='"+deptid+"',areaid='"+areaid+"' where workerid='"+workerid+"'";
		String sql4="update panter_reward set deptid='"+deptid+"',areaid='"+areaid+"' where workerid='"+workerid+"'";
		String sql5="update panter_studyexperience set deptid='"+deptid+"',areaid='"+areaid+"' where workerid='"+workerid+"'";
		String sql6="update partner_workexperience set deptid='"+deptid+"',areaid='"+areaid+"' where workerid='"+workerid+"'";
		CommonSpringJdbcServiceImpl jdbcServiceImpl=(CommonSpringJdbcServiceImpl)ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		jdbcServiceImpl.executeProcedure(sql1);
		jdbcServiceImpl.executeProcedure(sql2);
		jdbcServiceImpl.executeProcedure(sql3);
		jdbcServiceImpl.executeProcedure(sql4);
		jdbcServiceImpl.executeProcedure(sql5);
		jdbcServiceImpl.executeProcedure(sql6);
	}
	/**
	 * 
	 * @Title: checkUserId 
	 * @Description: 获得partnerUser的deptid和areaid；
	 * @param 
	 * @param   设定文件 
	 * @return String[]    返回类型 
	 * @throws
	 */
	public String[]  getPartnerUserDeptIdAndAreaId(String workerid){
		PartnerUser user =this.getPartnerUserByUserId(workerid);
		String[] strs = new String[2];
		strs[0] = StaticMethod.null2String(user.getDeptId());
		strs[1] =StaticMethod.null2String(user.getAreaId());
		return strs;
	}
	/**
	 * 
	* @Title: insertPartnerUserToTawSystemUser 
	* @Description: 将partnerUser添加到tawSystem中；
	* @param 
	* @Time:Nov 28, 2012-9:34:10 AM
	* @Author:fengguangping
	* @return void    返回类型 
	* @throws
	 */
	public void insertPartnerUserToTawSystemUser(PartnerUser partnerUser,TawSystemUser sysUser){
		ITawSystemUserManager tawSystemUsersaveManager=(ITawSystemUserManager)ApplicationContextHolder.getInstance()
		.getBean("ItawSystemUserSaveManagerFlush");
		ITawSystemUserManager tawSystemUserManager=(ITawSystemUserManager)ApplicationContextHolder.getInstance()
		.getBean("itawSystemUserManager");
		TawSystemUser user = tawSystemUserManager.getTawSystemUserByuserid(partnerUser.getUserId());
		if(user==null || "".equals(user.getUserid())){
			sysUser = new TawSystemUser();
			sysUser.setUserid(partnerUser.getUserId());
			sysUser.setUsername(partnerUser.getName());
			sysUser.setDeptid(partnerUser.getDeptId());
			sysUser.setDeptname(partnerUser.getDeptName());
			sysUser.setMobile(StaticMethod.null2String(partnerUser.getMobilePhone()));
			sysUser.setEmail(StaticMethod.null2String(partnerUser.getEmail()));
			sysUser.setSex(StaticMethod.null2String(partnerUser.getSex()));
			sysUser.setUserdegree(StaticMethod.null2String(partnerUser.getDiploma()));
			sysUser.setSavetime(new Date());
			sysUser.setEnabled(true);
			sysUser.setAccountExpired(false);
			sysUser.setAccountLocked(false);
			sysUser.setCredentialsExpired(false);
			sysUser.setFailCount(0);
			sysUser.setDeleted(partnerUser.getDeleted());
			// md5加密
//			String password =StaticMethod.null2String(partnerUser.getUserPassword());
//			if("".equals(password.trim())){
//				password = "123456";
//			}
//			sysUser.setPassword(new Md5PasswordEncoder().encodePassword(password, new String()));
			sysUser.setPassword(partnerUser.getUserPassword());
			sysUser.setIsPartners((partnerUser.getIsPartner()+3)+"");//0代维；1自维
			tawSystemUsersaveManager.saveTawSystemUser(sysUser, null);
		}else{
			if (!partnerUser.getDeleted().equals("1")) {
				user.setUserid(partnerUser.getUserId());
				user.setUsername(partnerUser.getName());
				user.setDeptid(partnerUser.getDeptId());
				user.setDeptname(partnerUser.getDeptName());
				user.setMobile(StaticMethod.null2String(partnerUser.getMobilePhone()));
				user.setEmail(StaticMethod.null2String(partnerUser.getEmail()));
				user.setSex(StaticMethod.null2String(partnerUser.getSex()));
				user.setUserdegree(StaticMethod.null2String(partnerUser.getDiploma()));
				user.setSavetime(new Date());
				user.setEnabled(true);
				user.setAccountExpired(false);
				user.setAccountLocked(false);
				user.setCredentialsExpired(false);
				user.setFailCount(0);
				user.setDeleted(partnerUser.getDeleted());
				user.setPassword(partnerUser.getUserPassword());
				// md5加密
//				String password =StaticMethod.null2String(partnerUser.getUserPassword());
//				if("".equals(password.trim())){
//					password = "123456";
//				}
//				user.setPassword(new Md5PasswordEncoder().encodePassword(password, new String()));
			}else {
				user=tawSystemUserManager.getTawSystemUserByuserid(partnerUser.getUserId());
				user.setDeleted("1");
			}
			user.setIsPartners((partnerUser.getIsPartner()+3)+"");//0代维；1自维
			tawSystemUsersaveManager.saveTawSystemUser(user, null);
		}
	}
	
	/**
	 * 
	 * @param partnerUser
	 * @param sysUser
	 * @param plaintext 密码明文
	 */
	public void insertPartnerUserToTawSystemUserNew(PartnerUser partnerUser,TawSystemUser sysUser,String plaintext){
		ITawSystemUserManager tawSystemUsersaveManager=(ITawSystemUserManager)ApplicationContextHolder.getInstance()
		.getBean("ItawSystemUserSaveManagerFlush");
		ITawSystemUserManager tawSystemUserManager=(ITawSystemUserManager)ApplicationContextHolder.getInstance()
		.getBean("itawSystemUserManager");
		TawSystemUser user = tawSystemUserManager.getTawSystemUserByuserid(partnerUser.getUserId());
		if(user==null || "".equals(user.getUserid())){
			sysUser = new TawSystemUser();
			sysUser.setUserid(partnerUser.getUserId());
			sysUser.setUsername(partnerUser.getName());
			sysUser.setDeptid(partnerUser.getDeptId());
			sysUser.setDeptname(partnerUser.getDeptName());
			sysUser.setMobile(StaticMethod.null2String(partnerUser.getMobilePhone()));
			sysUser.setEmail(StaticMethod.null2String(partnerUser.getEmail()));
			sysUser.setSex(StaticMethod.null2String(partnerUser.getSex()));
			sysUser.setUserdegree(StaticMethod.null2String(partnerUser.getDiploma()));
			sysUser.setSavetime(new Date());
			sysUser.setEnabled(true);
			sysUser.setAccountExpired(false);
			sysUser.setAccountLocked(false);
			sysUser.setCredentialsExpired(false);
			sysUser.setFailCount(0);
			sysUser.setDeleted(partnerUser.getDeleted());
			// md5加密
//			String password =StaticMethod.null2String(partnerUser.getUserPassword());
//			if("".equals(password.trim())){
//				password = "123456";
//			}
//			sysUser.setPassword(new Md5PasswordEncoder().encodePassword(password, new String()));
			sysUser.setPassword(partnerUser.getUserPassword());
			sysUser.setIsPartners((partnerUser.getIsPartner()+3)+"");//0代维；1自维
			tawSystemUsersaveManager.saveTawSystemUser(sysUser,plaintext,null);
		}else{
			if (!partnerUser.getDeleted().equals("1")) {
				user.setUserid(partnerUser.getUserId());
				user.setUsername(partnerUser.getName());
				user.setDeptid(partnerUser.getDeptId());
				user.setDeptname(partnerUser.getDeptName());
				user.setMobile(StaticMethod.null2String(partnerUser.getMobilePhone()));
				user.setEmail(StaticMethod.null2String(partnerUser.getEmail()));
				user.setSex(StaticMethod.null2String(partnerUser.getSex()));
				user.setUserdegree(StaticMethod.null2String(partnerUser.getDiploma()));
				user.setSavetime(new Date());
				user.setEnabled(true);
				user.setAccountExpired(false);
				user.setAccountLocked(false);
				user.setCredentialsExpired(false);
				user.setFailCount(0);
				user.setDeleted(partnerUser.getDeleted());
				user.setPassword(partnerUser.getUserPassword());
				// md5加密
//				String password =StaticMethod.null2String(partnerUser.getUserPassword());
//				if("".equals(password.trim())){
//					password = "123456";
//				}
//				user.setPassword(new Md5PasswordEncoder().encodePassword(password, new String()));
			}else {
				user=tawSystemUserManager.getTawSystemUserByuserid(partnerUser.getUserId());
				user.setDeleted("1");
			}
			user.setIsPartners((partnerUser.getIsPartner()+3)+"");//0代维；1自维
			tawSystemUsersaveManager.saveTawSystemUser(user, plaintext,null);
		}
	}
	

	public ImportResult importFromFile(FormFile formFile,HttpServletRequest request) throws Exception {
		XLSToFormFileImport xls=new XLSToFormFileImport(){
			public boolean doSaveRow2Data(HSSFRow row,	HttpServletRequest request) throws Exception {
				PartnerUser partnerUser=new PartnerUser();
				validateEachRow(row, partnerUser, "add");
				doSaveXLSFileData(row, request);
				return true;
			}
			public XLSModel getXLSModel() {
				return new XLSModel(2, 0, 21, 2, 0, 1, 2, 0, 18);
			}
			@Override
			public String getBachSql() throws Exception {
				return null;
			}
			/*使用的是doSaveRow2Data()方法来执行保存逻辑，需要返回true*/
			@Override
			public boolean getWay() throws Exception {
				return true;
			}
			@Override
			public void loadStaticSource() throws Exception {
			}
			@Override
			public PreparedStatement validateAndPrepareStatement(HSSFRow row,HttpServletRequest request, String type,
					PreparedStatement ps, String validateType) throws Exception {
				return null;
			}
			
		};
		 ImportResult result=xls.xlsFileValidate(formFile,request);
		 return result;
	}
	/**
	 * 修改人员的坐标
	 */
	public boolean updatePnrUserLocation(String userId, String latitude,String longtitude) throws Exception {
		boolean flag=false;
		BocoLog.info(this, 0, "更新代维人员坐标开始");
		PartnerUser user=getPartnerUserByUserId(userId);
		CommonSpringJdbcServiceImpl jdbcServiceImpl=(CommonSpringJdbcServiceImpl)ApplicationContextHolder
		.getInstance().getBean("commonSpringJdbcService");
		String sql="update  pnr_user set latitude='"+latitude+"',longtitude='"+longtitude+"' where user_id='"+userId+"'";
		flag=jdbcServiceImpl.executeProcedure(sql);
		BocoLog.info(this, 0, "更新代维人员userid:"+userId+"坐标,经度:"+longtitude+"纬度:"+latitude+",更新结果为:"+flag);
		return flag;
	}

	public boolean doLoadStaticSource() {
		// XXX Auto-generated method stub
		return false;
	}
}