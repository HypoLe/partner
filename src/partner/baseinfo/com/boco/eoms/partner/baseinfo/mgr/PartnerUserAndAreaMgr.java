package com.boco.eoms.partner.baseinfo.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndDept;

/**
 * <p>
 * Title:��Ա�������
 * </p>
 * <p>
 * Description:��Ա�������
 * </p>
 * <p>
 * Tue Mar 10 16:24:32 CST 2009
 * </p>
 * 
 * @author liujinlong
 * @version 3.5
 * 
 */
 public interface PartnerUserAndAreaMgr {
 
	/**
	 *
	 * ȡ��Ա������� �б�
	 * @return ������Ա��������б�
	 */
	public List getPartnerUserAndAreas();
    
	/**
	 * �������ѯ��Ա�������
	 * @param id ���
	 * @return ����ĳid�Ķ���
	 */
	public PartnerUserAndArea getPartnerUserAndArea(final String id);
    
	/**
	 * ������Ա�������
	 * @param partnerUserAndArea ��Ա�������
	 */
	public void savePartnerUserAndArea(PartnerUserAndArea partnerUserAndArea);
    
	/**
	 * ������ɾ����Ա�������
	 * @param id ���
	 */
	public void removePartnerUserAndArea(final String id);
    
	/**
	 * ��������ҳ��ѯ��Ա�������
	 * @param curPage ��ǰҳ
	 * @param pageSize ÿҳ���¼����
	 * @param whereStr ��ѯ���
	 * @return ������Ա������еķ�ҳ�б�
	 */
	public Map getPartnerUserAndAreas(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
	//由userId得到人员地域信息
	public PartnerUserAndArea getObjectByUserId(String userId);
	/**
	 * 判断人力地域表userId是否唯一
	 * 
	 *      
	 */
	public Boolean isunique(final String userId);
	
	
	/**
	 *  根据当前用户 加载当前用户所在地市 列表 (地市或县区)
	 * @return 返回当前用户所在地市列表(地市或县区)
	 * add by wangjunfeng
	 */
	public List listCityOfUser(final String userName);
	
	
	
	/**
	 *  根据当前地域信息 取出对应地域ID
	 * @return 返回地域ID列表
	 * add by wangjunfeng
	 */
	public List listCityOfAreaName(final String areaName);
	
	
	/**
	 *  根据当前用户 加载当前用户所在地市 列表（地市）
	 * @return 返回当前用户所在地市列表（地市）
	 * add by wangjunfeng
	 */
	public List listCityOfArea(final String areaid,final String areaType);
	

	/**
	 *
	 * 二级联动菜单 加载县区 列表
	 * @return 
	 * add by wangjunfeng 
	 */
	public List listCountyOfCity(final String cityId);

	
	/**
	 *
	 * 通过地域节点ID转换为地市ID
	 * @return 
	 * add by wangjunfeng 
	 */
	public List listCityIdByCityNodeId(final String cityId);
	

	/**
	 *
	 * 二级联动菜单 加载合作伙伴 列表 (列出所在地市的合作伙伴)
	 * @return 
	 * add by wangjunfeng
	 */
	public List listProviderOfCity(final String cityId);


	
	/**
	 * 列出用户省下的所有地市
	 * @return 
	 * add by wangjunfeng
	 */
	public List listCityByProvince(final String  province);
    /**
     * 列出用户所在地市
     * @return
     * add by wangjunfeng
     */
    public List listCityByRootArea(final String  rootAreaId);
	/**
	 *
	 * 根据用户ID 查出pnr_user_area中的权限县区地域ID（多个）
	 * @return 
	 * 2010-4-6
	 * add by wangjunfeng 
	 */
	public List listCountyOfPnrUserArea(final String userId);
	
	
	
	/**
	 *
	 * 二级联动菜单 根据所属地市、用户的县区权限 列出相应县区
	 * @return 
	 * 2010-4-6
	 * add by wangjunfeng 
	 */
	public List listCountyOfUserRight(final String countys,final String cityId);
	
	/**
	 * 根据地域iD选人
	 */
	public PartnerUserAndArea getPartnerUserAndAreaByAreaId(String areaId);

	
	
			
}