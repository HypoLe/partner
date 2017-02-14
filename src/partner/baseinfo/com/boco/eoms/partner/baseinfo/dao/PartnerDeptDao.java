package com.boco.eoms.partner.baseinfo.dao;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;

import java.util.List;
import java.util.Map;

import org.apache.axis2.addressing.AddressingConstants.Final;

import com.boco.eoms.partner.baseinfo.model.PartnerDept;

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
public interface PartnerDeptDao extends Dao {
	/**
	 * @author Steve
	 * Retrive all records according to hql, no paging.
	 */
	public List<PartnerDept> getPartnerDeptsByHql(String hql);
    /**
    *
    *ȡ�����б�
    * @return ���س����б�
    */
    public List getPartnerDepts();
    
    /**
     *
     *ȡ�����б�
     * @return ���س����б�
     */
    public Map getPartnerDeptsByDeptLevel(final Integer curPage, final Integer pageSize,
			final String whereStr);
    
    /**
    * �������ѯ����
    * @param id ���
    * @return ����ĳid�ĳ���
    */
    public PartnerDept getPartnerDept(final String id);
    
    public Boolean getPartnerDeptByName(final String name);
    
    /**
    *
    * ���泧��    
    * @param partnerDept ����
    * 
    */
    public void savePartnerDept(PartnerDept partnerDept);
    
    /**
    * ���idɾ����
    * @param id ���
    * 
    */
    public void removePartnerDept(final String id);
    
    /**
    * ��ҳȡ�б�
    * @param curPage ��ǰҳ
    * @param pageSize ÿҳ��ʾ����
    * @param whereStr where���
    * @return map��totalΪ����,result(list) curPageҳ�ļ�¼
    */
    public Map getPartnerDepts(final Integer curPage, final Integer pageSize,
			final String whereStr);
    
	/**
	 * 由字段treeId得到partnerDept
	 */
	public PartnerDept getPartnerDeptByTreeId(final String treeId);
	/**
	 * 由字段treeNodeId得到partnerDept
	 */
	public PartnerDept getPartnerDeptByTreeNodeId(final String treeNodeId);
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
	public List getPartnerDepts(final String where) ;
	
	public String id2Name(final String id);
	public List<TawSystemArea> getAreas(final String parentareaid);
	/**
	 * 根据地区查询对应的公司
	 */
	public List<PartnerDept> getCompanyByProvience(String id,String firstdeptsimble,String user);
	
	/**
	 * 得到下一级子部门的部门信息
	 * 
	 * @param pardeptid
	 * @param delid
	 * @return
	 */
	public List getNextLevecDepts(String pardeptid, String delid,String userdeptid,int isPartner);
	
	/**
	 * 根据地市或区县来获取合作伙伴，优先级为区县>地市>全省
	 * @param region
	 * @param city
	 * @return
	 */
	public List listProviderByRegionOrByCity(String region, String city);
	/**
	 * 
	* @Title: getPartnerDeptsByAreaidOrDeptis 
	* @Description: 根据代维管理人员的deptid和移动管理人员的areaid获取管辖的区域
	* @param 
	* @Time:Dec 13, 2012-10:32:31 PM
	* @Author:fengguangping
	* @return Map    返回类型 
	* @throws
	 */
	public Map getPartnerDeptsByAreaidOrDeptis(final Integer curPage,final Integer pageSize, final String whereStr);
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
	public List getNextLevecCompByAreaid(String parentdeptid, String delid,String areaId,int isPartner);
}