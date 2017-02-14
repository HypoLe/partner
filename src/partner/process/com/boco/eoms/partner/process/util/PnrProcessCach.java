package com.boco.eoms.partner.process.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import bsh.This;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.netresource.dao.IEomsDao;
import com.mcm.zyzl.zlgl.stationdel;

/**  
 * @Title: PnrProcessCach.java
 * @Package com.boco.eoms.partner.process.util
 * @Description: Excel文件上传缓存加载map减少和数据库的交互,提高校验速度;
 * @author fengguangping fengguangping@boco.com.cn
 * @date Apr 3, 2013  9:18:25 AM  
 */
public class PnrProcessCach {
public static ConcurrentMap<String, String>  areaCach = new ConcurrentHashMap<String, String>();
public static ConcurrentMap<String, String>  operatingPostCach = new ConcurrentHashMap<String, String>();
public static ConcurrentMap<String, String>  operatingPostNameCach = new ConcurrentHashMap<String, String>();
public static ConcurrentMap<String, PartnerDept> deptCompanyCach =  new ConcurrentHashMap<String, PartnerDept>();
public static ConcurrentMap<String,PartnerUser> deptUserCach = new ConcurrentHashMap<String,PartnerUser>();
public static ConcurrentMap<String, String> dwInfoListCach =  new ConcurrentHashMap<String, String>();
public static ConcurrentMap<String, String> carnumAndGPSnumListCach=  new ConcurrentHashMap<String, String>();
public static ConcurrentMap<String, ConcurrentMap<String, String>> dictMap = new ConcurrentHashMap<String, ConcurrentMap<String, String>>();
//public static ConcurrentMap<String, String> certListCach =  new ConcurrentHashMap<String, String>();
//public static ConcurrentMap<String, String> workExListCach =  new ConcurrentHashMap<String, String>();
//public static ConcurrentMap<String, String> stuExListCach =  new ConcurrentHashMap<String, String>();
//public static ConcurrentMap<String, String> rewardListCach =  new ConcurrentHashMap<String, String>();
//public static ConcurrentMap<String, String> pxExListCach =  new ConcurrentHashMap<String, String>();
//public static ConcurrentMap<String, String> qualificationListCach =  new ConcurrentHashMap<String, String>();
//public static ConcurrentMap<String, String> pnrOrgFinalSheetListCach =  new ConcurrentHashMap<String, String>();

	public static void loadAllOperation() {
		loadAreaCache();
		loadCompanyCache();
		loadUserCach();
		loadDwinfoList();
		loadCarnumAndGPSnumList();
		loadOperatingPost("10209");//加载专业类别下面的工作岗位
		loadDictCache("1230101");//加载仪器仪表专业字典
		loadDictCache("123010101");//加载基站维护专业仪器仪表名称字典
		loadDictCache("123010102");//加载塔干仪器仪表名称字典
		loadDictCache("123010103");//加载集客家客仪表名称字典
		loadDictCache("123010104");//加载传输线路仪表名称字典
		loadDictCache("1230102");//加载仪器仪表管理的状态字典；
		loadDictCache("1230103");//加载仪器仪表管理的产权管理字典
		loadDictCache("1230201");//加载车辆产权属性字典
		loadDictCache("1230202");//加载车辆状态字典
		loadDictCache("1230203");//加载车辆燃料种类字典
		loadDictCache("1230301");//加载油机额定功率字典
		loadDictCache("1230302");//加载油机类型字典
		loadDictCache("1230303");//加载油机使用状态字典
		loadDictCache("1230304");//加载油机产权归属字典
		loadDictCache("1230305");//加载油机燃料种类字典
		loadDictCache("1230401");//加载移动终端类型字典
		loadDictCache("1230402");//加载sim类型字典
	}

	/**
	 * 加载专业类别下面的工作岗位
	 * @param parentDictId 专业类别ID值
	 */
	public static  void loadOperatingPost(String parentDictId) {
		//System.out.println("----------------专业类别ID值为："+parentDictId+";加载专业类别下面的工作岗位的字典值-----------------------");
		IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select t.dictname,t.dictid from taw_system_dicttype t where t.parentdictid in( select t.dictid from taw_system_dicttype t where t.parentdictid = '"+parentDictId+"') order by t.dictid";
		try {
			conn = ed.getCon();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				operatingPostCach.put(rs.getString(1), rs.getString(2));
				operatingPostNameCach.put(rs.getString(2), rs.getString(1));
			}
			System.out.println("----------------加载"+operatingPostCach.size()+"个工作岗位信息----------------");
			System.out.println("----------------加载"+operatingPostNameCach.size()+"个工作岗位信息----------------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) {
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					if(!conn.isClosed()) {
						conn.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void loadCarnumAndGPSnumList() {
		if (carnumAndGPSnumListCach==null) {
			carnumAndGPSnumListCach=new ConcurrentHashMap<String, String>();
		}
		IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select id,car_number,car_gps_number from pnr_carinfo where deleted='0' ";
		try {
			conn = ed.getCon();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String id=rs.getString(1);
				String carNumber=rs.getString(2);
				String gpsNumber=rs.getString(3);
				carnumAndGPSnumListCach.put(carNumber, id+","+carNumber);
				carnumAndGPSnumListCach.put(gpsNumber, id+","+gpsNumber);
			}
//			System.out.println("----------------加载"+carnumAndGPSnumListCach.size()/2+"个车辆信息----------------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) {
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					if(!conn.isClosed()) {
						conn.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static ConcurrentMap<String, String> loadAreaCache() {
		if (areaCach==null) {
			areaCach=new ConcurrentHashMap<String, String>();
		}
		IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select areaname,areaid from TAW_SYSTEM_AREA";
		try {
			conn = ed.getCon();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				areaCach.put(rs.getString(1), rs.getString(2));
			}
			System.out.println("----------------加载"+areaCach.size()+"个区域信息----------------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) {
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					if(!conn.isClosed()) {
						conn.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	public static void loadCompanyCache() {
		if(deptCompanyCach==null){
			deptCompanyCach = new ConcurrentHashMap<String, PartnerDept>();
		}
		IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PartnerDept dept;
		String sql = "select name,id,area_id,area_name,dept_mag_id,organizationno,ifcompany from pnr_dept where deleted='0' ";
		try {
			conn = ed.getCon();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String name=rs.getString(1);
				String id=rs.getString(2);
				String areaId=rs.getString(3);
				String areaName=rs.getString(4);
				String deptMagId=rs.getString(5);
				String orgNo=rs.getString(6);
				String ifCompany=rs.getString(7);
				dept=new PartnerDept();
				dept.setName(name);
				dept.setId(id);
				dept.setDeptMagId(deptMagId);
				dept.setAreaId(areaId);
				dept.setAreaName(areaName);
				dept.setOrganizationNo(orgNo);
				dept.setIfCompany(ifCompany);
				deptCompanyCach.put(name,dept);
			}
			System.out.println("----------------加载"+deptCompanyCach.size()+"个代维组织信息----------------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) {
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					if(!conn.isClosed()) {
						conn.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static  void loadDictCache(String parentDictId) {
		System.out.println("~~~~~~~~~~~~~~parentDictId="+parentDictId);
		System.out.println("----------------加载"+parentDictId+"的字典值-----------------------");
		IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select dictname,dictid from taw_system_dicttype where parentdictid='"+parentDictId+"'";
		ConcurrentMap<String, String> map = new ConcurrentHashMap<String, String>();
		try {
			conn = ed.getCon();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				map.put(rs.getString(1), rs.getString(2));
			}
			dictMap.put(parentDictId, map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) {
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					if(!conn.isClosed()) {
						conn.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static Map<String,PartnerUser> loadUserCach() {
		if(deptUserCach==null){
			deptUserCach = new ConcurrentHashMap<String, PartnerUser>();
		}
		IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select user_id,person_card_no,name,id,dept_name,dept_id,area_id from pnr_user where deleted='0' ";
		try {
			conn = ed.getCon();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			PartnerUser partnerUser;
			while(rs.next()) {
				//将人员的userid放入map中为了避免key值重复，采用加后缀"_userId"的方式,
				//子所以是userid+标志和person_card_no+标志，因为校验在目前系统的校验方式有userid和身份证2种校验
				partnerUser = new PartnerUser();
				String useridString=rs.getString(1);
				String personCardNo=rs.getString(2);
				String name=rs.getString(3);
				String id=rs.getString(4);
				String deptName=rs.getString(5);
				String deptId=rs.getString(6);
				String areaId=rs.getString(7);
				partnerUser.setUserId(useridString);
				partnerUser.setPersonCardNo(personCardNo);
				partnerUser.setName(name);
				partnerUser.setId(id);
				partnerUser.setDeptName(deptName);
				partnerUser.setDeptId(deptId);
				partnerUser.setAreaId(areaId);
				deptUserCach.put(useridString, partnerUser);
				deptUserCach.put(personCardNo, partnerUser);
				deptUserCach.put(name, partnerUser);
			}
			System.out.println("----------------加载"+deptUserCach.size()/3+"个用户信息----------------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) {
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					if(!conn.isClosed()) {
						conn.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	public static void loadDwinfoList(){
		if (dwInfoListCach==null) {
			dwInfoListCach=new ConcurrentHashMap<String, String>();
		}
		IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Map<String,String> cache = new HashMap<String,String>(500);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select workerid,person_card_no,professional from partner_dwinfo where isdelete='0' ";
		try {
			conn = ed.getCon();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String userid=StaticMethod.null2String(rs.getString(1));
				String personCardNo=StaticMethod.null2String(rs.getString(2));
				String  professional=StaticMethod.null2String(rs.getString(3));
				dwInfoListCach.put("userId_"+userid+",pro_"+professional, professional);
				dwInfoListCach.put("personCardNo_"+personCardNo+",pro_"+professional, professional);
			}
			System.out.println("----------------加载"+dwInfoListCach.size()/2+"个技能信息----------------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) {
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					if(!conn.isClosed()) {
						conn.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 *@Description 重新加载人员信息缓存;
	 *@date May 3, 2013 5:51:22 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 */
	public static void reloadUserCach(){
		deptUserCach.clear();
		loadUserCach();
	}
	/**
	 * 
	 *@Description 重新加载代维公司信息缓存;
	 *@date May 3, 2013 5:51:22 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 */
	public static void reloadCompanyCache(){
		deptCompanyCach.clear();
		loadCompanyCache();
	}
	/**
	 * 
	 *@Description 重新加载区域信息缓存;
	 *@date May 3, 2013 5:51:22 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 */
	public static void reloadAreaCache(){
		areaCach.clear();
		loadAreaCache();
	}
	/**
	 * 
	 *@Description 重新加载车辆信息缓存;
	 *@date May 3, 2013 5:51:22 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 */
	public static void reloadCarnumAndGPSnumList(){
		carnumAndGPSnumListCach.clear();
		loadCarnumAndGPSnumList();
	}
	/**
	 * 
	 *@Description 重新技能信息缓存;
	 *@date May 3, 2013 5:51:22 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 */
	public static void reloadDwinfoList(){
		dwInfoListCach.clear();
		loadDwinfoList();
	}
	
}
