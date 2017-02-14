package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.dao.PartnerUserAndAreaDao;

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
public class PartnerUserAndAreaMgrImpl implements PartnerUserAndAreaMgr {
 
	private PartnerUserAndAreaDao  partnerUserAndAreaDao;
 	
	public PartnerUserAndAreaDao getPartnerUserAndAreaDao() {
		return this.partnerUserAndAreaDao;
	}
 	
	public void setPartnerUserAndAreaDao(PartnerUserAndAreaDao partnerUserAndAreaDao) {
		this.partnerUserAndAreaDao = partnerUserAndAreaDao;
	}
 	
    public List getPartnerUserAndAreas() {
    	return partnerUserAndAreaDao.getPartnerUserAndAreas();
    }
    
    public PartnerUserAndArea getPartnerUserAndArea(final String id) {
    	return partnerUserAndAreaDao.getPartnerUserAndArea(id);
    }
    
    public void savePartnerUserAndArea(PartnerUserAndArea partnerUserAndArea) {
    	partnerUserAndAreaDao.savePartnerUserAndArea(partnerUserAndArea);
    }
    
    public void removePartnerUserAndArea(final String id) {
    	partnerUserAndAreaDao.removePartnerUserAndArea(id);
    }
    
    public Map getPartnerUserAndAreas(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return partnerUserAndAreaDao.getPartnerUserAndAreas(curPage, pageSize, whereStr);
	}
	//由userId得到人员地域信息
	public PartnerUserAndArea getObjectByUserId(String userId){
		return partnerUserAndAreaDao.getObjectByUserId(userId);
	}
	/**
	 * 判断人力地域表userId是否唯一
	 * 
	 *      
	 */
	public Boolean isunique(final String userId){
		return partnerUserAndAreaDao.isunique(userId);
	}
	
	/**
	 *  根据当前用户 加载当前用户所在地市 列表 (地市或县区)
	 * @return 返回当前用户所在地市列表(地市或县区)
	 * add by wangjunfeng
	 */
    public List listCityOfUser(final String userName) {
    	return partnerUserAndAreaDao.listCityOfUser(userName);
    }
    
    public List listCityOfAreaName(final String areaName) {
    	return partnerUserAndAreaDao.listCityOfAreaName(areaName);
    }
    //city 是地市
    public List listCityOfArea(final String areaid,final String areaType) {
    	return partnerUserAndAreaDao.listCityOfArea(areaid,areaType);
    }
    //country是县区
    public List listCountyOfCity(final String cityId) {
    	return partnerUserAndAreaDao.listCountyOfCity(cityId);
    }
    
    
    public List listCityIdByCityNodeId(final String cityId) {
    	return partnerUserAndAreaDao.listCityIdByCityNodeId(cityId);
    }
    
    
    public List listProviderOfCity(final String cityId) {
    	return partnerUserAndAreaDao.listProviderOfCity(cityId);
    }
    

    public List listCityByProvince(final String province) {
    	return partnerUserAndAreaDao.listCityByProvince(province);
    }

    public List listCityByRootArea(final String rootAreaId) {
        return partnerUserAndAreaDao.listCityByRootArea(rootAreaId);
    }

    public List listCountyOfPnrUserArea(final String userId) {
    	return partnerUserAndAreaDao.listCountyOfPnrUserArea(userId);
    }

    
    public List listCountyOfUserRight(final String countys,final String cityId) {
    	return partnerUserAndAreaDao.listCountyOfUserRight(countys,cityId);
    }

	@Override
	public PartnerUserAndArea getPartnerUserAndAreaByAreaId(String areaId) {
		// TODO Auto-generated method stub
    	return partnerUserAndAreaDao.getPartnerUserAndAreaByAreaId(areaId);
	}
   

}