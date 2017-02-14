package com.boco.eoms.partner.appops.dao;

import java.util.List;

import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsUser;
import com.boco.eoms.partner.appops.model.PnrAppOpsUserModel;
import com.boco.eoms.partner.appops.model.StatisticsAppopsUserBySpecaillty;
import com.boco.eoms.partner.appops.model.StatisticsByCompanyAndDept;
import com.boco.eoms.partner.appops.model.StatisticsBySpecialityAndDept;

/**
 * 
  * @author wangyue
  * @ClassName: PartnerAppopsUserJDBCDao
  * @Copyright 亿阳信通
  * @date Sep 28, 2014 6:38:30 PM
  * @description 运维人员管理Dao接口
 */
public interface PartnerAppopsUserJDBCDao {
	
	/**
	 * 根据专业类型进行运维人员人数统计 
	  * @author wangyue
	  * @title: getStatisticsBySpecaillty
	  * @date Sep 28, 2014 6:33:06 PM
	  * @return List<StatisticsAppopsUserBySpecaillty>
	 */
	public List<StatisticsAppopsUserBySpecaillty> getStatisticsBySpecaillty(String userid,String areaid,String city,String specialty,PnrAppOpsUserModel pnrAppOpsUserModel);
	/**
	 * 根据分公司和部门统计运维人员人数
	  * @author wangyue
	  * @title: getStatisticsByCompanyAndDept
	  * @date Sep 29, 2014 7:19:42 PM
	  * @return List<StatisticsByCompanyAndDept>
	 */
	public List<StatisticsByCompanyAndDept> getStatisticsByCompanyAndDept(String userid,String areaid,String[] str,IPnrPartnerAppOpsUser user,PnrAppOpsUserModel pnrAppOpsUserModel);
	/**
	 * 根据部门名称或者班组名称获取相应的id
	  * @author wangyue
	  * @title: getNameBydeptIdOrTeamId
	  * @date Oct 11, 2014 10:58:48 AM
	  * @return String
	 */
	public String getIdBydeptIdOrTeamName(String name,String organizationNumber,String flag);
	/**
	 * 判断组织名称和组织编码是否存在
	 * @param organizationName
	 * @param organizationNumber
	 * @return
	 */
	public String getIdByOrganizationNumber(String organizationNumber,String flag);
	/**
	/**
	 * 根据专业名称，获取相应的专业字典值
	  * @author wangyuee 
	  * @title: getDictionary
	  * @date Oct 11, 2014 11:03:20 AM
	  * @param speciallyName
	  * @return String
	 */
	public String getDictionary(String speciallyName);
	
	public String getAreaIdByTeam(String name);
	/**
	 * 根据部门和专业类别统计运维人员人数
	  * @author wangyue
	  * @title: getStatisticsBySpecialtyAndDept
	  * @date Oct 30, 2014 4:12:38 PM
	  * @param str
	  * @return List
	 */
	public List<StatisticsBySpecialityAndDept> getStatisticsBySpecialtyAndDept(String userid,String areaid,String[] str,String[] specialty,IPnrPartnerAppOpsUser user,PnrAppOpsUserModel pnrAppOpsUserModel);
	/**
	 *  获取指定地市下的部门和专业类别进行统计运维人员人数
	  * @author wangyue
	  * @title: getStatisticsByCityAndSpecialty
	  * @date Oct 31, 2014 2:35:14 PM
	  * @param organization 一级组织 
	  * @param city 选择要统计的城市
	  * @param specialty  所有专业类型
	  * @return List<StatisticsBySpecialityAndDept>
	 */
	public List<StatisticsBySpecialityAndDept> getStatisticsByCityAndSpecialty(String userid,String organization,String city,String[] specialty,IPnrPartnerAppOpsUser user);
	
	/**
	 * 通过部门id值获取部门编码
	 * @param id
	 * @return
	 */
	public String getDeptCodeFromDeptId(String id);
	
	/**
	 * 验证手机号码唯一性
	 * @param mobile 手机号码
	 * @return 返回存在号码的个数，1为存在；0为不存在
	 */
	public int getMobileCheck(String mobile,String userId);

}
