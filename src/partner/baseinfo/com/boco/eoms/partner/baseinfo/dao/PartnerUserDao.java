package com.boco.eoms.partner.baseinfo.dao;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;

import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;

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
 */
public interface PartnerUserDao extends CommonGenericDao<PartnerUser, String> {

    /**
    *
    *ȡ��f��Ϣ�б�
    * @return ������f��Ϣ�б�
    */
    public List getPartnerUsers();
    
    /**
    * �������ѯ��f��Ϣ
    * @param id ���
    * @return ����ĳid����f��Ϣ
    */
    public PartnerUser getPartnerUser(final String id);
    
    /**
     * 根据userId查询代维人员
     * @param userId
     * @return
     */
    public PartnerUser getPartnerUserByUserId(String userId);
    
    /**
    *
    * ������f��Ϣ    
    * @param partnerUser ��f��Ϣ
    * 
    */
    public void savePartnerUser(PartnerUser partnerUser);
    
    /**
    * ���idɾ����f��Ϣ
    * @param id ���
    * 
    */
    public void removePartnerUser(final String id);
    
    /**
    * ��ҳȡ�б�
    * @param curPage ��ǰҳ
    * @param pageSize ÿҳ��ʾ����
    * @param whereStr where���
    * @return map��totalΪ����,result(list) curPageҳ�ļ�¼
    */
    public Map getPartnerUsers(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
     * 根据今日经纬度记录表查询人员
     * @param where
     * @param toDay
     * @return
     */
    public Map getPartnerUsersForGis(final Integer curPage, final Integer pageSize,final String where ,final String toDay) ;
    
    
    //判断人力信息userId 是否唯一
    public Boolean isunique(final String userId);
    
	//批量删除某地市某厂商下所有的人力信息，参数是treeNodeId
	public void removePartnerUserByTreeNodeId(final String treeNodeId);
    /**
    * 按条件查询人员ȡ�б�
    * @param where  where条件
    * @return 符合条件的人员信息
    */	
	public List getPartnerUsers(final String where);
	
	/**
	 * 得到公司的所有USER,不包括用户自己
	 * 
	 * @param deptid
	 * @param userid
	 * @return
	 */
	public List getUserByCompidNoSelf(String deptid,String userid);
	
	/**
	 * 得到公司的所有USER,不包括用户自己
	 * 
	 * @param deptid
	 * @param userid
	 * @return
	 */
	public List getUserByCompids(String deptid);
	
}