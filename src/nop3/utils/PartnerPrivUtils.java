package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.baseinfo.util.RoleIdList;

/**
 * @author chenyuanshu
 * @category 权限控制通用类
 * @category 如果是admin管理员，择根据applicationContext-partner-baseinfo-areaIdList.xml文件中所配置的省份代码来进行查看
 * @category 如果是其他管理员(user表中Userdegree为1的人员)，则根据用户所属部门，部门所属的地域来进行判断，默认为当前地域层级关系往下的均允许查看
 * @category 如果是其他非管理员用户，则先从pnr_user_area表中获取他所管理的地市，如果为空，则默认按照用户所属部门，部门所属的地域来进行判断，默认为当前地域层级关系往下来进行权限控制
 * 
 */

public class PartnerPrivUtils {

	static ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) ApplicationContextHolder
			.getInstance().getBean("ItawSystemDeptManager");
	static ITawSystemUserManager userMgr = (ITawSystemUserManager) ApplicationContextHolder
			.getInstance().getBean("itawSystemUserManager");
	static RoleIdList roleidList = (RoleIdList)ApplicationContextHolder.getInstance().getBean("roleIdList");

	@SuppressWarnings("unchecked")
	public static String getPrivSqlByArea(HttpServletRequest request)
			throws Exception {
		String returnPrivSql = "";
		String userId = ((TawSystemSessionForm) request.getSession()
				.getAttribute("sessionform")).getUserid();
		String deptId = ((TawSystemSessionForm) request.getSession()
				.getAttribute("sessionform")).getDeptid();
		StringBuffer areasBuffer = new StringBuffer();

		TawSystemUser user = userMgr.getTawSystemUserByuserid(userId);
		if (user != null) {
			if ("admin".equals(user.getUserid())) {
				PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) ApplicationContextHolder
						.getInstance().getBean("pnrBaseAreaIdList");
				returnPrivSql = " like '" + pnrBaseAreaIdList.getRootAreaId()
						+ "%'";
				return returnPrivSql;
			} else if (("1").equals(user.getUserdegree().trim())) {// 如果是管理员，则按管理员所在地市来进行控制
				TawSystemDept dept = deptMgr.getTawSystemDept(StaticMethod
						.nullObject2Integer(deptId));
				if (dept == null || dept.getAreaid() == null) {
					throw new Exception("没有权限，请联系管理员");
				} else {
					returnPrivSql = " like '" + dept.getAreaid() + "%'";
					return returnPrivSql;
				}
			}
		} else {
			throw new Exception("没有权限，请联系管理员");
		}

		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr) ApplicationContextHolder
				.getInstance().getBean("partnerUserAndAreaMgr");
		List areasRight = partnerUserAndAreaMgr.listCountyOfPnrUserArea(userId);
		if (areasRight != null) {
			PartnerUserAndArea partnerUserAndArea = (PartnerUserAndArea) areasRight
					.get(0);
			String areas = partnerUserAndArea.getCityId();
			String[] areasTem = areas.split(",");

			for (int i = 0; i < areasTem.length; i++) {
				areasBuffer.append("'");
				areasBuffer.append(areasTem[i]);
				areasBuffer.append("'");
				areasBuffer.append(",");
			}
			if (areasBuffer.length() != 0) {
				returnPrivSql = areasBuffer.substring(0,
						areasBuffer.length() - 1).toString();
				returnPrivSql = " in " + returnPrivSql;
			}
		} else {

			TawSystemDept dept = deptMgr.getTawSystemDept(StaticMethod
					.nullObject2Integer(deptId));
			if (dept == null || dept.getAreaid() == null) {
				throw new Exception("没有权限，请联系管理员");
			} else {
				returnPrivSql = " like '" + dept.getAreaid() + "%'";
			}
		}
		return returnPrivSql;

	}

	public static String getPrivSqlByArea(String userId) {
		return null;

	}

	public static boolean getOperatePrivByArea(HttpServletRequest request) {
		return false;

	}

	public static boolean getOperatePrivByArea(String userId) {
		return false;

	}
	
	
	/**
	 * 获取省代维公司deptid长度
	 * @return
	 */
	public static int getProvinceDeptLength(){
		return roleidList.getParDeptId().toString().length()+2;
	}
	
	/**
	 * 获取地市代维公司deptid长度
	 * @return
	 */
	public static int getCityDeptLength(){
		return roleidList.getParDeptId().toString().length()+4;
	}
	
	/**
	 * 获取区县代维公司deptid长度
	 * @return
	 */
	public static int getCountyDeptLength(){
		return roleidList.getParDeptId().toString().length()+6;
//		return roleidList.getParDeptId().toString().length()+6;
	}
	
	/**
	 * 获取维护小组deptid长度
	 * @return
	 */
	public static int getGroupDeptLength(){
		
		return roleidList.getParDeptId().toString().length()+8;
	}
	
	public static int notPartnerDept =-1;
	
	public static int errPartnerDept = -2;
	
	public static int Level_provinceDept =1;
	
	public static int Level_city =2;
	
	public static int Level_County =3;
	
	public static int Level_group =4;
	/**
	 * 省公司组织编码长度
	 */
	public static int Organizationno_length_Province =6;
	/**
	 * 地市公司组织编码长度
	 */
	public static int Organizationno_length_City =10;
	/**
	 * 区县公司组织编码长度
	 */
	public static int Organizationno_length_County =14;
	/**
	 * 代维小组组织编码长度
	 */
	public static int Organizationno_length_Group =18;
	
	/**
	 * 省地域编码长度
	 */
	public static int AreaId_length_Province =2;
	/**
	 * 地市地域编码长度
	 */
	public static int AreaId_length_City =4;
	/**
	 * 区县地域编码长度
	 */
	public static int AreaId_length_County =6;
	/**
	 * 
	 *@Description：通过组织编码获取公司级别
	 *@date May 10, 2013 11:18:23 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param orgNo
	 *@return
	 */
	public static int getPartnerDeptLevelByOrgNo(String orgNo){
		int length=orgNo.length();
		if(length==Organizationno_length_Province){
			return Level_provinceDept;
		}else if(length==Organizationno_length_City){
			return Level_city;
		}else if(length==Organizationno_length_County){
			return Level_County;
		}else if(length==Organizationno_length_Group){
			return Level_group;
		}
		return 0;
	}
	/**
	 * 
	 *@Description：通过组织编码获取公司级别
	 *@date May 10, 2013 11:18:23 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param orgNo
	 *@return
	 */
	public static int getPartnerDeptLevelByAreaId(String areaId){
		int length=areaId.length();
		if(length==AreaId_length_Province){
			return Level_provinceDept;
		}else if(length==AreaId_length_City){
			return Level_city;
		}else if(length==AreaId_length_County){
			return Level_County;
		}
		return 0;
	}
	/**
	 * 判断一个部门是否是代维公司部门
	 * @param deptId
	 * @return
	 */
	public static boolean isPartnerDept(String deptId){
		String rootPartnerDeptId = roleidList.getParDeptId().toString();
		if(deptId == null || !deptId.startsWith(rootPartnerDeptId)){
			return false;
		}
		return true;
	}
	
	/**
	 * 获取代维公司部门所在级别
	 * @param partnerDeptId
	 * @return
	 */
	public static int getPartnerDeptLevel(String partnerDeptId){
		if(!isPartnerDept(partnerDeptId)){
			return notPartnerDept;
		}
		int length = partnerDeptId.length();
		if(length == getProvinceDeptLength()){
			return Level_provinceDept;
		}else if(length == getCityDeptLength()){
			return Level_city;
		}else if(length == getCountyDeptLength()){
			return Level_County;
		}else if(length == getGroupDeptLength()){
			return Level_group;
		}else{
			return errPartnerDept;
		}
	}
	
	/**
	 * 获得指定部门id是否为代维公司
	 * @param deptId
	 * @return true 代维公司，false不是代维公司
	 */
	@SuppressWarnings("unchecked")
	public static boolean ifPartnerDept(String deptId){
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr)ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
		
		List list = partnerDeptMgr.getPartnerDepts(" and deptMagId='"+deptId+"'");
		if(list!=null && list.size()>0){
			return true;
		}else
			return false;
	}
	
	/**
	* @Description: 判断当前登录用户是否是代维人员，
	* 如果是代维人员则map的isPersonnel的值为"y",同时给deptMagId赋值
	* 如果是移动人员则map的isPersonnel的值为"n",同时给areaId赋值
	* 如果是admin,则map的isPersonnel的值为"admin"
	* @return Map<String,String>     
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> userIsPersonnel(HttpServletRequest request)throws Exception{
		Map map=new HashMap<String, String>();
		map.put("isPersonnel", "");
		map.put("deptMagId", "");
		map.put("areaId", "");
		TawSystemSessionForm sessionForm=(TawSystemSessionForm)request.getSession().getAttribute("sessionform");
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr)
		ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
		String deptid=sessionForm.getDeptid();
		String userid=sessionForm.getUserid();
		List<PartnerDept>  list0=new ArrayList<PartnerDept>();
		TawSystemUser user = userMgr.getTawSystemUserByuserid(userid);
		if (user != null) {
			if (!"admin".equals(sessionForm.getUserid())) {
				list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+deptid+"' and substr(deptMagId,1,3) != '"+com.boco.eoms.partner.process.util.CommonUtils.startDeptMagId+"'");
				if (list0.size()>0) {//表示人员是代维管理人员
					map.put("isPersonnel", "y");
					map.put("deptMagId",StaticMethod.null2String(list0.get(0).getDeptMagId()));
					map.put("areaId", StaticMethod.null2String(list0.get(0).getAreaId()));
				}else {//移动管理人员
					map.put("isPersonnel", "n");
					TawSystemDept dept1=deptMgr.getDeptinfobydeptid(deptid,"0");
					if(dept1!=null){
						String areaid=StaticMethod.null2String(dept1.getAreaid());
						map.put("areaId", areaid);
					}else {
						throw new Exception("没有权限，请联系管理员");
					}
				}
			}else {
				map.put("isPersonnel", "admin");
			}
		}else {
			throw new Exception("没有权限，请联系管理员");
		}
		return map;
	}
}
