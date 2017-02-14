package com.boco.eoms.netresource.modify.util;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.netresource.line.model.Lines;
import com.boco.eoms.netresource.line.service.LinesService;
import com.boco.eoms.netresource.point.mgr.IPointsMgr;
import com.boco.eoms.netresource.point.model.Points;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.net.mgr.ISiteMgr;
import com.boco.eoms.partner.net.model.StationPoint;

/**
 * 工具类
* @Title: 
* 
* @Description: TODO
* 
* @author WangGuangping  
* 
* @date Feb 23, 2012 6:36:16 PM
* 
* @version V1.0   
*
 */

public class ModifyUtil {
	
	/**
	 * 通过当前用户与所选择资源类型，级联当前用户所属合作伙伴负责的全部资源列表
	 * @param userId
	 * @param resourceType
	 * @ 1:基站、2:线路、3:标点、4:设备
	 * @return
	 */
	public String getLinkJsonByResType(String userId , String resourceType , String siteId){
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) ApplicationContextHolder
			.getInstance().getBean("partnerUserMgr");
		PartnerUser partnerUser = (PartnerUser)partnerUserMgr.getPartnerUserByUserId(userId);
		
		//当前用户不在合作伙伴表
		if("".equals(partnerUser) || null == partnerUser){
			jitem.put("res", ","+" ---- 无可变更资源 ---- ");
			json.put(jitem);
			return json.toString();
		}
		
		String partnerid = partnerUser.getPartnerid();
		
		//基站
		if("1".equals(resourceType)){
			ISiteMgr siteMgr = (ISiteMgr)ApplicationContextHolder
				.getInstance().getBean("iSiteMgr");
			List list = siteMgr.getSitesByWhere(" where 1=1 and partnerid = '"+partnerid+"' ");
			
			StringBuffer l_list = new StringBuffer();
			l_list.append("," + "");
			l_list.append("," + " ---- 请选择站点 ---- ");
			
			StationPoint stationPoint = null;
			for (int i = 0; list != null && i < list.size(); i++) {
				stationPoint = (StationPoint)list.get(i);
				l_list.append("," + stationPoint.getId());
				l_list.append("," + stationPoint.getSiteName());
			}

			jitem.put("res", l_list.toString().substring(1));
			json.put(jitem);
			
		
		//线路
		}else if("2".equals(resourceType)){
			LinesService linesService = (LinesService)ApplicationContextHolder
				.getInstance().getBean("linesService");
			List list = linesService.searchLine(" and partner = '"+partnerid+"' ");
			
			StringBuffer l_list = new StringBuffer();
			l_list.append("," + "");
			l_list.append("," + " ---- 请选择线路 ---- ");
			
			Lines lines = null;
			for (int i = 0; list != null && i < list.size(); i++) {
				lines = (Lines)list.get(i);
				l_list.append("," + lines.getId());
				l_list.append("," + lines.getLineName());
			}

			jitem.put("res", l_list.toString().substring(1));
			json.put(jitem);
			
			
		//标点	
		}else if("3".equals(resourceType)){
			IPointsMgr pointsMgr = (IPointsMgr)ApplicationContextHolder
				.getInstance().getBean("pointsMgr");
			List list = pointsMgr.getPointsByProperty(" and partner = '"+partnerid+"' ");
		
			StringBuffer p_list = new StringBuffer();
			p_list.append("," + "");
			p_list.append("," + " ---- 请选择标点 ---- ");
			
			Points points = null;
			for (int i = 0; list != null &&  i < list.size(); i++) {
				points = (Points)list.get(i);
				p_list.append("," + points.getId());
				p_list.append("," + points.getPointName());
			}

			jitem.put("res", p_list.toString().substring(1));
			json.put(jitem);
			
			
		//设备	返回站点、标点Json。当用户选择设备的时候,先级联该用户所属合作伙伴下的全部站点/标点。
		}else if("4".equals(resourceType)){
			
			//-------站点列表-------
			ISiteMgr siteMgr = (ISiteMgr)ApplicationContextHolder
				.getInstance().getBean("iSiteMgr");
			List siteList = siteMgr.getSitesByWhere(" where 1=1 and partnerid = '"+partnerid+"' ");
			System.out.println(siteList);
			StringBuffer l_list = new StringBuffer();
			l_list.append("," + "");
			l_list.append("," + " ---- 请选择站点 ---- ");
			
			StationPoint stationPoint = null;
			for (int i = 0; siteList != null && i < siteList.size(); i++) {
				stationPoint = (StationPoint)siteList.get(i);
				System.out.println("-------------"+stationPoint.getSiteName());
				l_list.append("," + stationPoint.getId());
				l_list.append("," + stationPoint.getSiteName());
			}
			
			//--------标点列表---------
			IPointsMgr pointsMgr = (IPointsMgr)ApplicationContextHolder
				.getInstance().getBean("pointsMgr");
			List pointsList = pointsMgr.getPointsByProperty(" and partner = '"+partnerid+"' ");
		
			l_list.append("," + "");
			l_list.append("," + " ---- 请选择标点 ---- ");
			
			Points points = null;
			for (int i = 0; pointsList != null &&  i < pointsList.size(); i++) {
				points = (Points)pointsList.get(i);
				l_list.append("," + points.getId());
				l_list.append("," + points.getPointName());
			}
			
			jitem.put("res", l_list.toString().substring(1));
			json.put(jitem);
			
		
		//设备	返回设备Json。通过站点/标点ID选择设备。	
		}else if("5".equals(resourceType)){
//			IPnrInspectDeviceMgr iPnrInspectDeviceMgr = (IPnrInspectDeviceMgr)ApplicationContextHolder
//				.getInstance().getBean("iPnrInspectDeviceMgr");
//			List list = iPnrInspectDeviceMgr.getPnrInspectDeveicesByWhereStr(" where 1=1 and site = '"+siteId+"' ");
//			
//			StringBuffer p_list = new StringBuffer();
//			p_list.append("," + "");
//			p_list.append("," + " ---- 请选择设备 ---- ");
//			
//			PnrInspectDevice pnrInspectDevice = null;
//			for (int i = 0; list != null &&  i < list.size(); i++) {
//				pnrInspectDevice = (PnrInspectDevice)list.get(i);
//				p_list.append("," + pnrInspectDevice.getId());
//				p_list.append("," + pnrInspectDevice.getDeviceName());
//			}
//			
//			jitem.put("res", p_list.toString().substring(1));
//			json.put(jitem);
		}
		
		return json.toString();
	}
	
	
}


