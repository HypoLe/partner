package com.boco.eoms.partner.baseinfo.util;

import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.res.mgr.PnrResToWeekTimeMgr;
import com.boco.eoms.partner.res.model.PnrResToWeekTime;

/**
 * <p>
 * Title:根据当前用户取出所属地市 和 县区联动地市
 * </p>
 * <p>
 * Description:地市 县区
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */

public class PartnerCityByUser{
	

	
	/**
	 * 根据当前用户取出所属地市
	 * 
	 * @param userid 当前用户ID
	 * @return
	 * 
	 */
	public static List getCityByUser(String userid) {
		ITawSystemAreaManager iTawSystemAreaManager = (ITawSystemAreaManager)ApplicationContextHolder
        .getInstance().getBean("ItawSystemAreaManager");
//		String userName=iTawSystemUserManager.getUserByuserid(userid).getUsername();

		//根据当前用户名，返回pnr_areadepttree表的list  
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder
		.getInstance().getBean("partnerUserAndAreaMgr");
		List city =new ArrayList();
		//根据当前name 取出地域名称（地域名称可多个）
		List areaNames = partnerUserAndAreaMgr.listCityOfUser(userid);
		List cityList  = new ArrayList();
		if(areaNames.size()>0){
			PartnerUserAndArea ua = (PartnerUserAndArea)areaNames.get(0);
			String areaType = ua.getAreaType();
			String cityStr = ua.getCityId();
			String [] citys =cityStr.split(",");
			for (int i = 0; i < citys.length; i++) {
				cityList.add(iTawSystemAreaManager.getAreaByAreaId(citys[i]));
			}
		}
		return cityList;
	}
	
	/**
	 * 二级联动菜单
	 * 根据所属地市 列出相应县区
	 * @return 县区
	 *
	 */
	public static String getCountyByCity(String cityId){
		

		
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder
		.getInstance().getBean("partnerUserAndAreaMgr");
		
		List county = partnerUserAndAreaMgr.listCountyOfCity(cityId);
		
		StringBuffer c_list = new StringBuffer();
		c_list.append("," + "");
		c_list.append("," + "--请选择所在县区--");
		
		for (int i = 0; i < county.size(); i++) {
			TawSystemArea ta = (TawSystemArea)county.get(i);
			
			c_list.append("," + ta.getAreaid());
			c_list.append("," + ta.getAreaname());
		}
		String countyBuffer = c_list.toString();
		
		countyBuffer = countyBuffer.substring(1, countyBuffer.length());
		
		
		
		return countyBuffer;
	}


	/**
	 * 通过地域节点ID转换为地市ID
	 * (地域数节点转换为taw_system_area表中的areaID)
	 * @return 地域ID
	 *
	 */
	public static String getCountyByCityNodeId(String cityNodeId){
		
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder
		.getInstance().getBean("partnerUserAndAreaMgr");
		
		//通过地域节点ID转换为地市ID
		List cityId = partnerUserAndAreaMgr.listCityIdByCityNodeId(cityNodeId);
		
		AreaDeptTree areaDeptTree = (AreaDeptTree)cityId.get(0);
		String cityNo = areaDeptTree.getAreaId();
		
		//getCountyByCity(cityNo);
		
		return getCountyByCity(cityNo);
	}

	
	/**
	 * 二级联动菜单
	 * 根据所属地市 列出相应合作伙伴
	 * @return 合作伙伴
	 * 
	 */
	public static String getProviderByCity(String cityId){
		

		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder
		.getInstance().getBean("partnerUserAndAreaMgr");

		
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");


		List provider = partnerUserAndAreaMgr.listProviderOfCity(cityId);
	
		for (int i = 0; i < provider.size(); i++) {
			AreaDeptTree adt = (AreaDeptTree)provider.get(i);
			
			provider_list.append("," + adt.getNodeName());
			provider_list.append("," + adt.getNodeName());
		}
		String providerBuffer = provider_list.toString();
		
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		
		
		return providerBuffer;
	}
	
	
	
	
	
	/**
	 * 取出所有地市
	 * @param province 当前用户所在省的ID
	 * 2010-4-6
	 * author:wangjunfeng
	 * @return
	 */
	public static List getCityByProvince(String province) {

		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder
		.getInstance().getBean("partnerUserAndAreaMgr");
		List areaList = partnerUserAndAreaMgr.listCityByProvince(province);
		List cityList = new ArrayList();
		for (int i = 0; i < areaList.size(); i++) {
			TawSystemArea tawSystemArea =(TawSystemArea)areaList.get(i);
//			cityName=tawSystemArea.getAreaname();
//			cityId=tawSystemArea.getAreaid();
			cityList.add(tawSystemArea);
		}
		return cityList;

	}
    /**
     * 取出所有地市
     * @param rootAreaId 当前用户所属地区
     * 2010-4-6
     * author:wangjunfeng
     * @return
     */
    public static List getCityByRootArea(String rootAreaId) {

        PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder
                .getInstance().getBean("partnerUserAndAreaMgr");
        List areaList = partnerUserAndAreaMgr.listCityByRootArea(rootAreaId);
        List cityList = new ArrayList();
        for (int i = 0; i < areaList.size(); i++) {
            TawSystemArea tawSystemArea =(TawSystemArea)areaList.get(i);
            cityList.add(tawSystemArea);
        }
        return cityList;

    }
	/**
	 * 二级联动菜单
	 * 根据所属地市、用户的县区权限 列出相应县区
	 * @return 县区
	 * 2010-4-6
	 * author:wangjunfeng
	 */
	public static String getCountyOfUserRight(String userId,String cityId){
		
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder
		.getInstance().getBean("partnerUserAndAreaMgr");
		
		/*
		 * 权限控制以后再补（by zhangkeqi）
		//用户权限控制中的所属县区
//		List RightCounty = partnerUserAndAreaMgr.listCountyOfPnrUserArea(userId);
//		PartnerUserAndArea partnerUserAndArea = (PartnerUserAndArea)RightCounty.get(0);
//		String countys = partnerUserAndArea.getAreaId();//第一地市
		
		
		//List county = partnerUserAndAreaMgr.listCountyOfUserRight(countys,cityId);
		 * 
		 */
		List county = partnerUserAndAreaMgr.listCityByProvince(cityId);
		
		StringBuffer c_list = new StringBuffer();
		c_list.append("," + "");
		c_list.append("," + "--请选择所在县区--");
		
		for (int i = 0; i < county.size(); i++) {
			TawSystemArea ta = (TawSystemArea)county.get(i);
			
			c_list.append("," + ta.getAreaid());
			c_list.append("," + ta.getAreaname());
		}
		String countyBuffer = c_list.toString();
		
		countyBuffer = countyBuffer.substring(1, countyBuffer.length());
		
		
		
		return countyBuffer;
	}


	/**
	 * 
	 * 根据所属资源级别 列出相应巡检周期
	 * @return 字典里的巡检周期项
	 * 
	 * author:chenbing 
	 */
	public static String getWeekTimeByPnrId(String pnrId){
		
		// 取得资源级别，巡检周期 关联对象
		PnrResToWeekTimeMgr pnrResToWeekTimeMgr = (PnrResToWeekTimeMgr)ApplicationContextHolder
		.getInstance().getBean("PnrResToWeekTimeMgr");

		// 取字典数据
		ITawSystemDictTypeManager _objDictManager = (ITawSystemDictTypeManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDictTypeManager");
		// 获得字典项中巡检周期dicID	
	
	   List<PnrResToWeekTime> pnrResToWeekTime =pnrResToWeekTimeMgr.getPnrResToWeekTimeByPnrId(pnrId);
	   int size = pnrResToWeekTime.size();
	   String weekId = null ;
	   if(size>0){
		   weekId= pnrResToWeekTime.get(0).getWeekID();
		   }
		
		// 根据字典项中巡检周期dicID，获取巡检周期信息
	   TawSystemDictType tawSystemDictType =null;
	   
	   if(weekId !=null){
			 tawSystemDictType =_objDictManager.getDictByDictId(weekId);

	   }
		
		StringBuffer c_list = new StringBuffer();
		//由于前台页面是text 需要验证空值
		c_list.append("," + "");
		c_list.append("," + "");
		if(tawSystemDictType !=null){
			c_list.append("," + tawSystemDictType.getDictCode());
			c_list.append("," + tawSystemDictType.getDictName());
			c_list.append("," + tawSystemDictType.getDictId());
			c_list.append("," + tawSystemDictType.getDictName());
		}
		String tawBuffer = c_list.toString();
		tawBuffer = tawBuffer.substring(1, tawBuffer.length());
		
		
		return tawBuffer;
	}

	
}
 