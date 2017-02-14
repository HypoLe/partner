package com.boco.eoms.partner.geo.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import com.boco.eoms.partner.geo.model.ResourceGeoInfo;
import com.boco.eoms.partner.geo.parse.ParseUtils;

public class ResouceInfoLocationsInfo {

	private static YddwserPortType service ;
	
	public List<ResourceGeoInfo> querryResouceInfoHistoryLocus(Map<String,String> querryMap){
		
		List<ResourceGeoInfo> reusltList = new ArrayList<ResourceGeoInfo>();
		ResourceGeoInfo temp = null;
		try {
			String[] resultString = getYddwserPortTypeSevice().f_qryhislcsdata(querryMap.get("a_srcId"), Integer.parseInt(querryMap.get("a_srcType")), querryMap.get("a_startTime"), querryMap.get("a_endTime"));
			if(resultString.length >0){
				//请求成功.
				if("1000#查询成功".equals(resultString[0])){
					for(int i=1 ;i<resultString.length;i++){
						temp = ParseUtils.parsInterfaceReturnResult(resultString[i]);
						
				    if(!reusltList.contains(temp)){
							reusltList.add(temp);
					}
				
						
					}
				
				}
				
				System.out.println("reusltList size:"+reusltList.size());
				
				//请求参数错误 
				if("1001".equals(resultString[0])){
					
				}
				if("1001".equals(resultString[0])){
					
				}
				//1002#资源不存在,
				if("1002".equals(resultString[0])){
					
				}
				//1003#查询失败
				if("1003".equals(resultString[0])){
					
				}
				System.out.println(resultString);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reusltList;
	}
	
	public ResourceGeoInfo querryResouceInfoLocus(Map<String,String> querryMap){
		
		ResourceGeoInfo temp = null;
		try {
			String resultString = getYddwserPortTypeSevice().f_qrycurlcsdata(querryMap.get("a_srcId"),Integer.parseInt(querryMap.get("a_srcType") ));
			
			temp = ParseUtils.parsInterfaceReturnResult(resultString);
			if(temp == null){
				//请求参数错误 
				if("1001#".equals(resultString)){
					
				}
				if("1001#".equals(resultString)){
					
				}
				//1002#资源不存在,
				if("1002#".equals(resultString)){
					
				}
				//1003#查询失败
				if("1003#".equals(resultString)){
					
				}
				System.out.println(resultString);
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	
	public boolean updateResourceStatus(String resourceId,String resourcType){
		
		String resultString ="";
		try {
			resultString = getYddwserPortTypeSevice().f_dolcs(resourceId, Integer.parseInt(resourcType));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if("1001#".indexOf(resultString)>0){
			return Boolean.FALSE;
		}
		if("1001#".indexOf(resultString)>0){
			return Boolean.FALSE;
		}
		//1002#资源不存在,
		if("1002#".indexOf(resultString)>0){
			return Boolean.FALSE;
		}
		//1003#查询失败
		if("1003#".indexOf(resultString)>0){
			return Boolean.FALSE;
		}
		
		if("1000#".indexOf(resultString)>0){
			return Boolean.TRUE;
		}
		System.out.println(resultString);
		return Boolean.FALSE;
	}

	public static YddwserPortType getYddwserPortTypeSevice(){
		try {
			service = (YddwserPortType)new YddwserLocator().getyddwserHttpPort();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return service;
	}
	
	
	
}
