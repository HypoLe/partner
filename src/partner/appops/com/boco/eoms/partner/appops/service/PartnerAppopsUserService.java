package com.boco.eoms.partner.appops.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsUser;
import com.boco.eoms.partner.appops.model.PnrAppOpsUserModel;
import com.boco.eoms.partner.appops.model.StatisticsAppopsUserBySpecaillty;
import com.boco.eoms.partner.appops.model.StatisticsByCompanyAndDept;
import com.boco.eoms.partner.appops.model.StatisticsBySpecialityAndDept;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;

public interface PartnerAppopsUserService extends CommonGenericService<IPnrPartnerAppOpsUser>  {
	public Map getPartnerUsers(final Integer curPage, final Integer pageSize,
			final String whereStr);
	 /**
	    * 按条件查询人员ȡ�б�
	    * @param where  where条件
	    * @return 符合条件的人员信息
	    */	
   public List getPartnerUsers(final String where);	
   
   /**
	 * 批量导入
	  * @author wangyue
	  * @title: importFromFile
	  * @date Sep 27, 2014 10:27:22 AM
	  * @param formFile
	  * @param request
	  * @return
	  * @throws Exception ImportResult
	 */
	public ImportResult importFromFile(FormFile formFile,HttpServletRequest request) throws Exception;
	/**
	 * 根据专业类型统计运维人员人数
	  * @author wangyue
	  * @title: getStatisticsBySpecaillty
	  * @date Sep 28, 2014 6:29:08 PM
	  * @return List<StatisticsAppopsUserBySpecaillty>
	 */
	public List<StatisticsAppopsUserBySpecaillty> getStatisticsBySpecaillty(String userid,String areaid,String city,String specialty,PnrAppOpsUserModel pnrAppOpsUserModel);
	/**
	 * 根据分公司和部门统计运维人员人数
	  * @author wangyue
	  * @title: getStatisticsByCompanyAndDept
	  * @date Sep 29, 2014 7:18:26 PM
	  * @return List<StatisticsByCompanyAndDept>
	 */
	public List<StatisticsByCompanyAndDept> getStatisticsByCompanyAndDept(String userid,String areaid,String[] str,IPnrPartnerAppOpsUser user,PnrAppOpsUserModel pnrAppOpsUserModel);
	/**
	 * 根据部门和专业类别统计运维人员人数
	  * @author wangyue
	  * @title: getStatisticsBySpecialtyAndDept
	  * @date Oct 30, 2014 4:11:13 PM
	  * @param str
	  * @return List
	 */
	public List<StatisticsBySpecialityAndDept> getStatisticsBySpecialtyAndDept(String userid,String areaid,String[] str,String[] specialty,IPnrPartnerAppOpsUser user,PnrAppOpsUserModel pnrAppOpsUserModel);
	/**
	 * 获取指定地市下的部门和专业类别进行统计运维人员人数
	  * @author wangyue
	  * @title: getStatisticsByCityAndSpecialty
	  * @date Oct 31, 2014 2:33:01 PM
	  * @param organization 一级组织名称
	  * @param city  选择统计的地市
	  * @param specialty 所有专业类型
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
	 * 校验手机号码唯一性
	 * @param mobile
	 * @return
	 */
	public int getMobileCheck(String mobile,String userId);
	
	
}
