package com.boco.eoms.partner.baseinfo.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;

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
public interface PartnerUserAndAreaDao extends Dao {

    /**
    *
    *ȡ��Ա��������б�
    * @return ������Ա��������б�
    */
    public List getPartnerUserAndAreas();
    
    /**
    * �������ѯ��Ա�������
    * @param id ���
    * @return ����ĳid����Ա�������
    */
    public PartnerUserAndArea getPartnerUserAndArea(final String id);
    
    /**
    *
    * ������Ա�������    
    * @param partnerUserAndArea ��Ա�������
    * 
    */
    public void savePartnerUserAndArea(PartnerUserAndArea partnerUserAndArea);
    
    /**
    * ���idɾ����Ա�������
    * @param id ���
    * 
    */
    public void removePartnerUserAndArea(final String id);
    
    /**
    * ��ҳȡ�б�
    * @param curPage ��ǰҳ
    * @param pageSize ÿҳ��ʾ����
    * @param whereStr where���
    * @return map��totalΪ����,result(list) curPageҳ�ļ�¼
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
    *
    *根据当前用户 加载当前用户所在地市 列表 (地市或县区)
	 * @return 返回当前用户所在地市列表(地市或县区)
	 * add by wangjunfeng
    */
    public List listCityOfUser(final String userName);
    
    /**
     *根据当前地域信息 取出对应地域ID
 	 * @return 返回地域ID列表
     */
     public List listCityOfAreaName(final String areaName);

     
     /**
     *
     *根据当前用户 加载当前用户所在地市 列表 (地市)
 	 * @return 返回当前用户所在地市列表(地市)
     */
     public List listCityOfArea(final String areaid,final String areaType);

     /**
     *
     *二级联动菜单 加载县区 列表
     * @return 返回抽查记录列表
     */
     public List listCountyOfCity(final String cityId);
     
     
     public List listCityIdByCityNodeId(final String cityId);
     
     
     /**
     *
     *二级联动菜单 加载合作伙伴 列表 (列出所在地市的合作伙伴)
     * @return 返回抽查记录列表
     */
     public List listProviderOfCity(final String cityId);
     
     
     /**
     * 列出用户省下的所有地市
     */
     public List listCityByProvince(final String province);
    /**
     * 列出用户所在地市
     */
    public List listCityByRootArea(final String province);

     
     
     
     public List listCountyOfPnrUserArea(final String userId);
     
     public List listCountyOfUserRight(final String countys,final String cityId);
     
     /**
 	 * 根据地域iD选人
 	 */
 	public PartnerUserAndArea getPartnerUserAndAreaByAreaId(String areaId);
 	
}