package com.boco.eoms.partner.appops.service.impl;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.appops.dao.PartnerAppopsUserDao;
import com.boco.eoms.partner.appops.dao.PartnerAppopsUserJDBCDao;
import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsDept;
import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsUser;
import com.boco.eoms.partner.appops.model.PnrAppOpsUserModel;
import com.boco.eoms.partner.appops.model.StatisticsAppopsUserBySpecaillty;
import com.boco.eoms.partner.appops.model.StatisticsByCompanyAndDept;
import com.boco.eoms.partner.appops.model.StatisticsBySpecialityAndDept;
import com.boco.eoms.partner.appops.service.PartnerAppopsDeptService;
import com.boco.eoms.partner.appops.service.PartnerAppopsUserService;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSToFormFileImport;
import com.googlecode.genericdao.search.Search;

public class PartnerAppopsUserSevriceImpl extends CommonGenericServiceImpl<IPnrPartnerAppOpsUser> implements PartnerAppopsUserService{

		private PartnerAppopsUserDao  partnerAppopsUserDao;
		private PartnerAppopsUserJDBCDao partnerAppopsUserJDBCDao;
		private PartnerAppopsDeptService pnrAppopsDeptService;
		private ITawSystemDeptManager itawSystemDeptManager;

		public ITawSystemDeptManager getItawSystemDeptManager() {
			return itawSystemDeptManager;
		}

		public void setItawSystemDeptManager(ITawSystemDeptManager itawSystemDeptManager) {
			this.itawSystemDeptManager = itawSystemDeptManager;
		}

		public PartnerAppopsDeptService getPnrAppopsDeptService() {
			return pnrAppopsDeptService;
		}

		public void setPnrAppopsDeptService(
				PartnerAppopsDeptService pnrAppopsDeptService) {
			this.pnrAppopsDeptService = pnrAppopsDeptService;
		}

		public PartnerAppopsUserJDBCDao getPartnerAppopsUserJDBCDao() {
			return partnerAppopsUserJDBCDao;
		}

		public void setPartnerAppopsUserJDBCDao(
				PartnerAppopsUserJDBCDao partnerAppopsUserJDBCDao) {
			this.partnerAppopsUserJDBCDao = partnerAppopsUserJDBCDao;
		}
		public PartnerAppopsUserDao getPartnerAppopsUserDao() {
			return partnerAppopsUserDao;
		}

		public void setPartnerAppopsUserDao(PartnerAppopsUserDao partnerAppopsUserDao) {
			this.partnerAppopsUserDao = partnerAppopsUserDao;
			this.setCommonGenericDao(partnerAppopsUserDao);
		}

		@Override
		public Map getPartnerUsers(Integer curPage, Integer pageSize,
				String whereStr) {
			return partnerAppopsUserDao.getPartnerUsers(curPage, pageSize, whereStr);
		}

		@Override
		public List getPartnerUsers(String where) {
			// TODO Auto-generated method stub
			return partnerAppopsUserDao.getPartnerUsers(where);
		}
		
		public Search searchPrivFilter(Search search, String userId,String deptId,HttpServletRequest request){
			search.addFilterNotEqual("isDelete", "1");
			//获取区域id作为删选条件
			HashMap<String, String> map=new HashMap<String, String>();
			
			//人员姓名
			String nameSearch = request.getParameter("nameSearch");
			if(StringUtils.isNotEmpty(nameSearch)){
				search.addFilterLike("userName", "%"+nameSearch+"%");
				
			}
			//所属组织
			String prov = request.getParameter("prov");
			if(StringUtils.isNotEmpty(prov)){
				search.addFilterEqual("team", prov);
				
			}
			//Email
			String emailSearch = request.getParameter("emailSearch");
			if(StringUtils.isNotEmpty(emailSearch)){
				search.addFilterLike("email", "%"+emailSearch+"%");
			}
			//移动电话
			String mobilePhoneSearch = request.getParameter("mobilePhoneSearch");
			if(StringUtils.isNotEmpty(mobilePhoneSearch)){
				search.addFilterLike("phone", "%"+mobilePhoneSearch+"%");
			}
			
			//不是管理员则只能查看自己部门的人力信息
			String hasRightForDelAndAdd ="";
			if(!userId.equals("admin")){
				if (!"".equals(deptId)) {/**/
					/*先判断是移动公司人员还是代维公司人员*/
					List<IPnrPartnerAppOpsDept> list0=pnrAppopsDeptService.getPartnerDepts("and deptMagId='"+deptId+"' and substr(deptMagId,1,3)!='"+com.boco.eoms.partner.process.util.CommonUtils.startDeptMagId+"'");
					if(deptId.length()>5){
						hasRightForDelAndAdd="0";
					}else{
						hasRightForDelAndAdd="1";
						
					}
					if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
						/*代维公司人员只能浏览本公司代维公司员工的权利*/
						search.addFilterLike("deptId", "%"+deptId+"%");//代维公司权限限定
						
					}else {
						/*移动公司人员拥有删除、修改、增加本区域代维公司员工的权利*/
					//	ITawSystemDeptManager deptManager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
						TawSystemDept dept=itawSystemDeptManager.getDeptinfobydeptid(deptId,"0");
						if (dept!=null) {
							//whereStr += " and areaId like '"+StaticMethod.null2String(dept.getAreaid())+"%'";/*区域权限限定*/
							search.addFilterLike("areaId", "%"+StaticMethod.null2String(dept.getAreaid())+"%");
						}
					}
				}
			}
			
		
			
//			//search.addFilterEqual(property, value);
//			System.out.println("当前登录用户id传过来了吗？"+userId);
//			System.out.println("当前登录deptId传过来了吗？"+deptId);
//			try {
//				map = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
//				String flag=map.get("isPersonnel");
//				if (flag.equals("y")) {//代维人员
//					search.addFilterLike("deptId", map.get("deptMagId")+"%");
//				}else if (flag.equals("n")) {//移动人员
//					search.addFilterLike("areaId", map.get("areaId")+"%");
//				}else if (flag.equals("admin")) {//admin
//					
//				}
//			} catch (Exception e) {
//				throw new RuntimeException("非法用户!");
//			}
			return search;
		}
		@Override
		public ImportResult importFromFile(FormFile formFile,
				HttpServletRequest request) throws Exception {
			XLSToFormFileImport xls=new XLSToFormFileImport(){
				public boolean doSaveRow2Data(HSSFRow row,	HttpServletRequest request) throws Exception {
					IPnrPartnerAppOpsUser appopsUser=new IPnrPartnerAppOpsUser();
					validateEachRow(row, appopsUser, "add");
					doSaveXLSFileData(row, request);
					System.out.println("-----------11111------------");
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
			System.out.println("-----------2222------------");
			 ImportResult result=xls.xlsFileValidate(formFile,request);
			 return result;
		}
		
		/**
		 * 验证导入的每个必填字段
		  * @author wangyue
		  * @title: validateEachRow
		  * @date Sep 27, 2014 11:57:33 AM
		  * @param row
		  * @param appopsUser
		  * @param type
		  * @return
		  * @throws Exception IPnrPartnerAppOpsUser
		 */
		public IPnrPartnerAppOpsUser validateEachRow(HSSFRow row,IPnrPartnerAppOpsUser appopsUser,String type) throws Exception{
			int cellNum=0;
			String userid="";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			if ("update".equals(type)) {//修改数据时校验,比新增多一个userid校验;
				String[] userinfo=XLSCellValidateUtil.checkUserId(row, cellNum++);
				userid=userinfo[0];//获取userid
			}
			//新增运维人员信息
			//分公司
			appopsUser.setCompany(XLSCellValidateUtil.checkDictName(row, cellNum++, "10208",false));
			cellNum++;//中间隔一个字段 
			//姓名
			appopsUser.setUserName(XLSCellValidateUtil.checkIsNull(row, cellNum++));
			//性别
			appopsUser.setUserSex(XLSCellValidateUtil.checkDictName(row, cellNum++, "1120202",false));
			//出生年份
			appopsUser.setUserBorth(XLSCellValidateUtil.checkDateYear(row, cellNum++));
			
			cellNum++;
			//手机
			appopsUser.setTelephone(XLSCellValidateUtil.checkPhoneNumber(row, cellNum++));
			//邮箱
			appopsUser.setEmail(XLSCellValidateUtil.checkEmail(row, cellNum++,false));
			//开始工作时间
			String workTime = XLSCellValidateUtil.checkDateYear(row, cellNum++);
					appopsUser.setWorkTime(workTime);
			//入司时间
			String companyTime = XLSCellValidateUtil.checkDateYear(row, cellNum++);
					appopsUser.setCompanyTime(companyTime);
			//从事维护工作时间
			String appopsTime = XLSCellValidateUtil.checkDateYear(row, cellNum++);
					appopsUser.setAppopsWorkTime(appopsTime);
			//最高学历
			appopsUser.setHighestDegree(XLSCellValidateUtil.checkDictName(row, cellNum++, "10201",false));
			//最高学历毕业院校
			appopsUser.setSchool(XLSCellValidateUtil.checkIsNull(row, cellNum++));
			cellNum = cellNum+5;
			//员工类别
			appopsUser.setWorkSort(XLSCellValidateUtil.checkDictName(row, cellNum++, "10203",false));
			//职称
			appopsUser.setJobTitle(XLSCellValidateUtil.checkDictName(row, cellNum++, "10204",false));
			//用工类别
			appopsUser.setUseSort(XLSCellValidateUtil.checkDictName(row, cellNum++, "10205",false));
			//岗位职级
			appopsUser.setJobLevel(XLSCellValidateUtil.checkDictName(row, cellNum++, "10207",false));
			//管理层级
			appopsUser.setManagerLevel(XLSCellValidateUtil.checkDictName(row, cellNum++, "10206",false));
		
			//所在部门
			String dept = XLSCellValidateUtil.getCellStringValue(row, cellNum++);
			dept = StaticMethod.null2String(dept).trim();
			String deptIsExit = partnerAppopsUserJDBCDao.getIdByOrganizationNumber(dept,"1");
			XLSCellValidateUtil.checkDeptOrTeamIsNull(row, cellNum-1, deptIsExit);
			appopsUser.setDeptName(dept);
			//组织编码
			String organizationNumber = XLSCellValidateUtil.getCellStringValue(row, cellNum++);
			organizationNumber = StaticMethod.null2String(organizationNumber).trim();
			String isExit = partnerAppopsUserJDBCDao.getIdByOrganizationNumber(organizationNumber,"2");
			XLSCellValidateUtil.checkDeptOrTeamIsNull(row, cellNum-1, isExit);
			//根据部门名称获取部门id
			String deptid = partnerAppopsUserJDBCDao.getIdBydeptIdOrTeamName(dept,organizationNumber,"1");
			appopsUser.setDept(XLSCellValidateUtil.checkDeptOrTeamIsNull(row, cellNum-1, deptid));
			//所在班组
			String team = XLSCellValidateUtil.getCellStringValue(row, cellNum++);
			team = StaticMethod.null2String(team).trim();
			appopsUser.setTeamName(team);
			//根据班组名称获取班组id
			String teamId=partnerAppopsUserJDBCDao.getIdBydeptIdOrTeamName(team,organizationNumber,"2");
			appopsUser.setTeam(XLSCellValidateUtil.checkDeptOrTeamIsNull(row, cellNum-1, teamId));
			
			//专业类别
			String specially = XLSCellValidateUtil.getCellStringValue(row, cellNum);
			appopsUser.setSpecialty(XLSCellValidateUtil.checkDictName(row, cellNum++,"10209",false));
			//工作岗位--专职--获取选择的专业类别字典值
			String dictId = partnerAppopsUserJDBCDao.getDictionary(specially);
			//工作岗位--专职
			appopsUser.setOperatingPostZ(XLSCellValidateUtil.checkDictName(row, cellNum++,dictId,false));
			//工作岗位--兼职
//			appopsUser.setOperatingPostJ(XLSCellValidateUtil.checkIsNull(row, cellNum++));
			appopsUser.setOperatingPostJ(XLSCellValidateUtil.checkOperatingPostJ(row, cellNum++,dictId,false));
			//工作职责描述
			appopsUser.setWorkDescribe(XLSCellValidateUtil.checkIsNull(row, cellNum++));
			
			
			
			return appopsUser;
		}
		/**
		 * 保存运维人员信息
		  * @author wangyue
		  * @title: doSaveXLSFileData
		  * @date Sep 27, 2014 2:03:39 PM
		  * @param row
		  * @param request
		  * @return
		  * @throws Exception boolean
		 */
		public boolean doSaveXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
			IPnrPartnerAppOpsUser appopsUser = new IPnrPartnerAppOpsUser();
			ITawSystemUserManager userMgr = (ITawSystemUserManager) ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");
			appopsUser=getRowObject(row, appopsUser, "add");
			//存入运维人员表中
			this.partnerAppopsUserDao.saveAppopsUser(appopsUser);
			return true;
		}
		/**
		 * 解析每条信息
		  * @author wangyue
		  * @title: getRowObject
		  * @date Sep 27, 2014 12:02:17 PM
		  * @param row
		  * @param partnerUser
		  * @param type
		  * @return
		  * @throws Exception PartnerUser
		 */
		public IPnrPartnerAppOpsUser getRowObject(HSSFRow row,IPnrPartnerAppOpsUser appopsUser,String type) throws Exception{
			int cellNum=0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			if ("update".equals(type)) {
				appopsUser.setId(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			}
			//获取每一字段，并存入运维人员实体类对象中
			//分公司
			appopsUser.setCompany(XLSCellValidateUtil.checkDictName(row, cellNum++,"10208",false));
			//员工编号
			appopsUser.setUserNumber(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			//姓名
			appopsUser.setUserName(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			//性别
			appopsUser.setUserSex(XLSCellValidateUtil.checkDictName(row, cellNum++,"1120202",false));
			//出生年份
			String borth= XLSCellValidateUtil.getCellStringValue(row, cellNum++);
			appopsUser.setUserBorth(borth);
			//年龄
			if(borth!=null && !"".equals(borth=borth.trim())){
				try{
					
					appopsUser.setUserAge(getNumberByTime(sdf.format(sdf.parse(borth))));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			//固定电话
			appopsUser.setTelephone(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			//手机
			appopsUser.setPhone(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			//邮箱
			appopsUser.setEmail(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			//开始工作时间
			String workTime = XLSCellValidateUtil.getCellStringValue(row, cellNum++);
			if(workTime!=null && !"".equals(workTime.trim())){
				try{
					
					appopsUser.setWorkTime(workTime);
					appopsUser.setWorkNumber(getNumberByTime(workTime));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			//入司时间
			String companyTime = XLSCellValidateUtil.getCellStringValue(row, cellNum++);
			if(companyTime!=null && !"".equals(companyTime.trim())){
				try{
					appopsUser.setCompanyTime(companyTime);
					appopsUser.setCompanyNumber(getNumberByTime(companyTime));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			//从事维护工作
			String appopsTime = XLSCellValidateUtil.getCellStringValue(row, cellNum++);
			if(appopsTime!=null && !"".equals(appopsTime.trim())){
				try{
					appopsUser.setAppopsWorkTime(appopsTime);
					appopsUser.setAppopsNumber(getNumberByTime(appopsTime));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			//最高学历
			appopsUser.setHighestDegree(XLSCellValidateUtil.checkDictName(row, cellNum++,"10201",false));
			//最高学历毕业院校
			appopsUser.setSchool(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			//第一学位
			appopsUser.setFirstDegree(XLSCellValidateUtil.checkDictName(row, cellNum++,"10202",true));
			//第一学位专业
			appopsUser.setFirstSpecialty(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			//第二学位
			appopsUser.setSecondDegree(XLSCellValidateUtil.checkDictName(row, cellNum++,"10202",true));
			//第二学位专业
			appopsUser.setSecondSpecialty(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			//其它证书
			appopsUser.setCertificate(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			//员工类别
			appopsUser.setWorkSort(XLSCellValidateUtil.checkDictName(row, cellNum++,"10203",false));
			//职称
			appopsUser.setJobTitle(XLSCellValidateUtil.checkDictName(row, cellNum++,"10204",false));
			//用工类别
			appopsUser.setUseSort(XLSCellValidateUtil.checkDictName(row, cellNum++,"10205",false));
			//岗位职级
			appopsUser.setJobLevel(XLSCellValidateUtil.checkDictName(row, cellNum++,"10207",false));
			//管理层级
			appopsUser.setManagerLevel(XLSCellValidateUtil.checkDictName(row, cellNum++,"10206",false));
			
			
			//所在部门
			String dept = XLSCellValidateUtil.getCellStringValue(row, cellNum++);
			dept = StaticMethod.null2String(dept).trim();
			String deptIsExit = partnerAppopsUserJDBCDao.getIdByOrganizationNumber(dept,"1");
			XLSCellValidateUtil.checkDeptOrTeamIsNull(row, cellNum-1, deptIsExit);
			appopsUser.setDeptName(dept);
			//组织编码
			String organizationNumber = XLSCellValidateUtil.getCellStringValue(row, cellNum++);
			organizationNumber = StaticMethod.null2String(organizationNumber).trim();
			String isExit = partnerAppopsUserJDBCDao.getIdByOrganizationNumber(organizationNumber,"2");
			XLSCellValidateUtil.checkDeptOrTeamIsNull(row, cellNum-1, isExit);
			appopsUser.setDeptCode(organizationNumber);//保存部门编码

			//根据部门名称获取部门id
			String deptId = partnerAppopsUserJDBCDao.getIdBydeptIdOrTeamName(dept,organizationNumber,"1");
			appopsUser.setDept(deptId);
			//所在班组
			String team = XLSCellValidateUtil.getCellStringValue(row, cellNum++);
			team = StaticMethod.null2String(team).trim();
			appopsUser.setTeamName(team);
			//根据班组名称获取班组id
			appopsUser.setTeam(partnerAppopsUserJDBCDao.getIdBydeptIdOrTeamName(team,organizationNumber,"2"));
			//根据班组获取地市
			appopsUser.setAreaId(partnerAppopsUserJDBCDao.getAreaIdByTeam(team));
			//专业类别
			String specially = XLSCellValidateUtil.getCellStringValue(row, cellNum);
			appopsUser.setSpecialty(XLSCellValidateUtil.checkDictName(row, cellNum++,"10209",false));
			//工作岗位--专职--获取选择的专业类别字典值
			String dictId = partnerAppopsUserJDBCDao.getDictionary(specially);
			appopsUser.setOperatingPostZ(XLSCellValidateUtil.checkDictName(row, cellNum++,dictId,false));
			//工作岗位--兼职
			//appopsUser.setOperatingPostJ(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			appopsUser.setOperatingPostJ(XLSCellValidateUtil.checkOperatingPostJ(row, cellNum++,dictId,false));
			
			//工作岗位--兼职名称
			String tempOperatingPostJId= appopsUser.getOperatingPostJ();
			String operatingPostJName = XLSCellValidateUtil.getOperatingPostJName(tempOperatingPostJId);
			appopsUser.setOperatingPostJName(operatingPostJName);
			
			
			//描述
			appopsUser.setWorkDescribe(XLSCellValidateUtil.getCellStringValue(row, cellNum++));
			//是否离职
				appopsUser.setIsDelete("0");
			
			return appopsUser;
		}
		public Integer getNumberByTime(String time){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String nowYear = sdf.format(new Date());
			if(time!=null && !"".equals(time=time.trim())){
				return Integer.parseInt(nowYear)-Integer.parseInt(time);
			}else{
				return 0;
			}
		}

		@Override
		public List<StatisticsAppopsUserBySpecaillty> getStatisticsBySpecaillty(String userid,String areaid,String city,String specialty,PnrAppOpsUserModel pnrAppOpsUserModel) {
			 
			return partnerAppopsUserJDBCDao.getStatisticsBySpecaillty(userid,areaid,city,specialty,pnrAppOpsUserModel);
		}
		public List<StatisticsByCompanyAndDept> getStatisticsByCompanyAndDept(String userid,String areaid,String[] str,IPnrPartnerAppOpsUser user,PnrAppOpsUserModel pnrAppOpsUserModel){
			
			return partnerAppopsUserJDBCDao.getStatisticsByCompanyAndDept(userid,areaid,str,user,pnrAppOpsUserModel);
		}

		@Override
		public List<StatisticsBySpecialityAndDept> getStatisticsBySpecialtyAndDept(String userid,String areaid,String[] str,String[] specialty,IPnrPartnerAppOpsUser user,PnrAppOpsUserModel pnrAppOpsUserModel) {
			return partnerAppopsUserJDBCDao.getStatisticsBySpecialtyAndDept(userid,areaid,str,specialty,user,pnrAppOpsUserModel);
			
		}

		@Override
		public List<StatisticsBySpecialityAndDept> getStatisticsByCityAndSpecialty(
				String userid,String organization, String city, String[] specialty,IPnrPartnerAppOpsUser user) {
			return partnerAppopsUserJDBCDao.getStatisticsByCityAndSpecialty(userid,organization, city, specialty,user);
		}
		
		/**
		 * 通过部门id值获取部门编码
		 * @param id
		 * @return
		 */
		public String getDeptCodeFromDeptId(String id){
			return partnerAppopsUserJDBCDao.getDeptCodeFromDeptId(id);
		}
		
		/**
		 * 校验手机号码唯一性
		 */
		public int getMobileCheck(String mobile,String userId){
			return partnerAppopsUserJDBCDao.getMobileCheck(mobile,userId);
		}
		
		
		
}
