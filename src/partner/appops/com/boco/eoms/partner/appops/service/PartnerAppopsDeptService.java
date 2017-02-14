package com.boco.eoms.partner.appops.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsDept;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PnrQualification;

/**
 * <p>
 * Title:����
 * </p>
 * <p>
 * Description:����
 * </p>
 * <p>
 * Mon Feb 09 10:57:12 CST 2009
 * </p>
 * 
 * @author liujinlong
 * @version 3.5
 * 
 */
 public interface PartnerAppopsDeptService {
 
	 
	 public PartnerDept getDeptByOrgCode(String orgCode);
	 
	 /**
		 * 根据上级部门编码 生成当前部门编码 ,没有上级(省下属) 则传入 null或者""
		 * @param upOrgCode
		 * @return 当前部门组织编码
		 */
		public String getOrgCode(String upOrgCode);
	 
	/**
	 *
	 * ȡ���� �б�
	 * @return ���س����б�
	 */
	public List getPartnerDepts();
	/**
	 * 根据公司级别来查询公司
	 * @param level
	 * @return
	 */
	public Map getPartnerDeptsByDeptLevel(final Integer curPage, final Integer pageSize,
			IPnrPartnerAppOpsDept dept,String deptid,String hasRight,String deptLevel);
    
	/**
	 * �������ѯ����
	 * @param id ���
	 * @return ����ĳid�Ķ���
	 */
	public IPnrPartnerAppOpsDept getPartnerDept(final String id);
	
	
	public Boolean getPartnerDeptByName(final String name);
    
	/**
	 * ���泧��
	 * @param partnerDept ����
	 */
	public void savePartnerDept(IPnrPartnerAppOpsDept partnerDept);
    
	/**
	 * ������ɾ����
	 * @param id ���
	 */
	public void removePartnerDept(final String id);
    
	/**
	 * ��������ҳ��ѯ����
	 * @param curPage ��ǰҳ
	 * @param pageSize ÿҳ���¼����
	 * @param whereStr ��ѯ���
	 * @return ���س��̵ķ�ҳ�б�
	 */
	public Map getPartnerDepts(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
	/**
	 * 由字段treeId得到partnerDept
	 */
	public IPnrPartnerAppOpsDept getPartnerDeptByTreeId(final String treeId);
	/**
	 * 由字段treeNodeId得到partnerDept
	 */
	public IPnrPartnerAppOpsDept getPartnerDeptByTreeNodeId(final String treeNodeId);
	/**
	 * id:EOMS-liujinlong-20090924143614
	 * 开发人邮箱：liujinlong@boco.com.cn
	 * 时间：2009-09-24
	 * 开发目的：将删除改为设置删除字段置deleted为1
	 * 参数parentNodeId：合作伙伴父节点nodeID，也可能是合作伙伴的nodeId
	 */
	public void removePartnerDeptByNodeId(final String parentNodeId);
	
	/**
	 * 按条件查询返回合作伙伴的集合
	 */	
	public List getPartnerDepts(final String where);
	
	
	public void remove(String id);
	
	/**
	 * 查询父代维公司
	 * @param curPage
	 * @param pageSize
	 * @param dept
	 * @return
	 */
	public Map getPartnerDepts(final Integer curPage, final Integer pageSize,
			IPnrPartnerAppOpsDept dept,String deptid,String hasRight);
	 /**
	 * 根据地区查询对应的公司
	 */
	public List<IPnrPartnerAppOpsDept> getCompanyByProvience(String Id,String firstdeptsimble,String user);
	
	/**
	 * @author Steve
	 * Retrive all records according to hql, no paging.
	 */
	public List<IPnrPartnerAppOpsDept> getPartnerDeptsByHql(String hql);
	
	/**
	 * 根据地区查询对应的公司
	 */
	public List<TawSystemArea> getAreas(final String parentareaid);
	
	/**
	 * 得到下一级子部门的部门信息
	 * 
	 * @param pardeptid
	 * @param delid
	 * @return
	 */
	public List getNextLevecDepts(String pardeptid, String delid,String userdeptid,int isPartner);
	
	

	/**
	 *
	 * 二级联动菜单 加载合作伙伴 列表 (根据地市、县区)
	 * @return 返回抽查记录列表
	 */
	public List listProviderByRegionOrByCity(final String region,final String city);
	
	/**
	 *
	 * 删除组织信息
	 * @return 返回抽查记录列表
	 */
	
	public boolean remove(String[] ids);
	public IPnrPartnerAppOpsDept updateStatisticInfo(IPnrPartnerAppOpsDept partnerDept) throws Exception;
	/**
	 * 
	* @Title: getPartnerDeptsByAreaidOrDeptid 
	* @Description: 通过代维管理人员的deptid和移动人员的areaid来获取管辖的代维公司
	 * @param interfaceHeadId TODO
	* @param 
	* @Time:Dec 13, 2012-10:50:20 PM
	* @Author:fengguangping
	* @return Map    返回类型 
	* @throws
	 */
	public Map getPartnerDeptsByAreaidOrDeptid(final Integer curPage, final Integer pageSize,String areaid,
			String deptid,IPnrPartnerAppOpsDept dept,String deptLevel, String interfaceHeadId,int isPartner);
	/**
	 * 
	* @Title: checkIsHasUserBeforeDelDept 
	* @Description: 删除部门前先检查该部门以及下属部门是否含有员工
	* @param 
	* @Time:Jan 10, 2013-11:27:15 AM
	* @Author:fengguangping
	* @return List    返回包含员工的部门名称 
	* @throws
	 */
	public List checkIsHasUserBeforeDelDept(String[] ids) throws Exception;
	/**
	 * 
	* @Title: getPartnerDeptsByUserRight 
	* @Description: 通过用户自身的身份（移动人员、代维人员、admin）来获取管辖的范围
	* @return
	* @throws Exception    
	* @return List     
	* @throws
	 */
	public List<IPnrPartnerAppOpsDept> getPartnerDeptsByUserRight(HttpServletRequest request) throws Exception;
	/**
	 * 
	* @Title: saveDeptAndRelatedQualification 
	* @Description: 保存部门信息并且保存相应的资质信息
	* @param dept
	* @param list
	* @return
	* @throws Exception    
	* @return boolean     
	* @throws
	 */
	public  boolean saveDeptAndRelatedQualification(IPnrPartnerAppOpsDept dept,List<PnrQualification> list,String level) throws Exception;
	/**
	 * 
	*@Title: getNextLevecCompByAreaid 
	*@Description: 根据areaid和树当前节点来获取部门,
	*@param parentdeptid：当前树节点的deptUUid
	*@param delid：是否删除标志,"0"-未删除,"1"-删除
	*@param areaId：区域id
	*@return ArrayList     
	*@author fengguangping 2013.04.19
	*@throws
	 */
	public List getNextLevecCompByAreaid(String deptid, String delid,String areaId,int isParnter) throws Exception;
	/**
	 * 
	 *@Description 通过deptmagId获取PartnerDept
	 *@date Apr 24, 2013 5:18:20 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param node：部门的deptmagid
	 *@return
	 */
	public IPnrPartnerAppOpsDept getPartnerDeptByDeptMagId(String node);
	/**
	 * 
	 *@Description 更新pnr_user的deptName
	 *@date May 9, 2013 9:53:13 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param partnerDept
	 */
	public void updatePnrUserDeptname(IPnrPartnerAppOpsDept partnerDept) throws Exception;
	/**
	 * 
	 *@Description：将更新pnr_user,pnr_dept,taw_system_user，taw_system_dept以及资质信息封装在同一个事务中;
	 *@date May 9, 2013 10:25:38 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param oldDeptName:修改前部门名称
	 *@param request
	 *@param partnerDept：部门信息
	 *@param qualifyList：资质信息
	 *@param deptLevel：部门级别
	 *@throws Exception
	 */
	public void updateDeptAndUser(String oldDeptName,HttpServletRequest request,IPnrPartnerAppOpsDept partnerDept,List qualifyList,String deptLevel) throws Exception;
}