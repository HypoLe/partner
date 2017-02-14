package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.deviceAssess.dao.FacilityQuantityDao;
import com.boco.eoms.partner.deviceAssess.mgr.FacilityQuantityMgr;
import com.boco.eoms.partner.deviceAssess.model.FacilityQuantity;
import com.boco.eoms.partner.deviceAssess.util.MapObj;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * <p>
 * Title:设备量信息
 * </p>
 * <p>
 * Description:设备量信息
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
     public class FacilityQuantityMgrImpl  extends
                 CommonGenericServiceImpl<FacilityQuantity> implements FacilityQuantityMgr {

     

	public void setFacilityQuantityDao(FacilityQuantityDao facilityQuantityDao) {
                 this.setCommonGenericDao(facilityQuantityDao);
}
  
     public List getMapObjList(String nameOrId){
    	  	 
    	   String parentDictId="11216";//专业字典值
		   List<TawSystemDictType> majorList=new ArrayList<TawSystemDictType>();
		   ITawSystemDictTypeManager dictMgr=(ITawSystemDictTypeManager)ApplicationContextHolder
		   .getInstance().getBean("ItawSystemDictTypeManager");
		 
		   majorList=dictMgr.getDictSonsByDictid(parentDictId);//得到所有专业对应字典表每条数据
		   List mapObjList = new ArrayList();
		  
		   try {
		   for(int i=0;i<majorList.size();i++){//得到每种专业下对应设备类型的DictId
			   MapObj model = new MapObj();
			   int deviceTypeSize=0;
			   TawSystemDictType majorTawSystemDictType=(TawSystemDictType)majorList.get(i);
			   String majorDictId=majorTawSystemDictType.getDictId();//获取专业ID
			   String majorDictName=dictMgr.id2Name(majorDictId);
			   
//			   majorDictNameList.add(majorDictName);
			   
			   List<String> list = new ArrayList<String>();
			   
			   List<TawSystemDictType> deviceTypeList=new ArrayList<TawSystemDictType>();
			   deviceTypeList=dictMgr.getDictSonsByDictid(majorDictId);//得到所有设备类型对应字典表每条数据
	
			   for(int j=0;j<deviceTypeList.size();j++){
				  
				   TawSystemDictType deviceTypeTawSystemDictType=(TawSystemDictType)deviceTypeList.get(j);
				   String deviceTypeDictId=deviceTypeTawSystemDictType.getDictId();//获取设备类型ID
				   String deviceTypeDictName=dictMgr.id2Name(deviceTypeDictId);
				   deviceTypeSize++;
				   
				   if(nameOrId.equals("name")) { 
				   list.add(deviceTypeDictName);
				   } else
				   {list.add(deviceTypeDictId);
				   }
			   }
			  
			   
//			   deviceTypeSize+=deviceTypeSize;
			   model.setDeviceTypeSize(deviceTypeSize);
			   
			   if(nameOrId.equals("name")) { 
				   model.setKey(majorDictName);
				   } else
				   { model.setKey(majorDictId);
				   }
			   model.setList(list);
			   mapObjList.add(model);
		   }	 
		   } catch (DictDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
   
    	 return mapObjList;
     };
     
     
     
     
     
     /**
		 * 根据页面上信息存储设备量信息
		 * 
		 * @param map
		 *            从页面取得
		 */
	public void saveFacilityQuantity(Map map){
			
		String parentDictId="11216";// 专业字典值
		
		
		   List<TawSystemDictType> majorList=new ArrayList<TawSystemDictType>();
		   
		   ITawSystemDictTypeManager dictMgr=(ITawSystemDictTypeManager)ApplicationContextHolder
		   .getInstance().getBean("ItawSystemDictTypeManager");
   
		   majorList=dictMgr.getDictSonsByDictid(parentDictId);// 得到所有专业对应字典表每条数据
		   try {
		   String factory=new String(((String[])map.get("factory"))[0].toString().getBytes("ISO-8859-1"),"utf-8");
		   for(int i=0;i<majorList.size();i++){// 得到每种专业下对应设备类型的DictId
			   TawSystemDictType majorTawSystemDictType=(TawSystemDictType)majorList.get(i);
			   String majorDictId=majorTawSystemDictType.getDictId();// 获取专业ID
			   String majorDictName = dictMgr.id2Name(majorDictId);
			      
			   List<TawSystemDictType> deviceTypeList=new ArrayList<TawSystemDictType>();
			   deviceTypeList=dictMgr.getDictSonsByDictid(majorDictId);// 得到所有设备类型对应字典表每条数据
			   
			   for(int j=0;j<deviceTypeList.size();j++){
				   
				   TawSystemDictType deviceTypeTawSystemDictType=(TawSystemDictType)deviceTypeList.get(j);
				   String deviceTypeDictId=deviceTypeTawSystemDictType.getDictId();// 获取设备类型ID
				   String deviceTypeDictName=dictMgr.id2Name(deviceTypeDictId);
				 
				 
				   String aa[]=(String[])map.get(deviceTypeDictName);
				   
				  "".equals(aa[0].toString());
				   
//				   String quantityValue=new String(((String[])map.get(deviceTypeDictName))[0].toString());
				   
				   if(map.containsKey(deviceTypeDictName)&&(!"".equals(aa[0]))){
					   FacilityQuantity facilityQuantity=new FacilityQuantity();
					   Integer quantity=Integer.valueOf(new String(((String[])map.get(deviceTypeDictName))[0].toString()));						   
					   
					   
					Search search = new Search();
					search.addFilterEqual("factory", factory);
					search.addFilterEqual("deviceType", deviceTypeDictId);
					SearchResult<FacilityQuantity> searchResult = this.searchAndCount(search);		 //从数据库里取得结果集 
					List<FacilityQuantity>  facilityQuantityList = searchResult.getResult();//
					if(!facilityQuantityList.isEmpty())  {
						facilityQuantity=facilityQuantityList.get(0);
					} 
					
					   facilityQuantity.setFactory(factory);
//					   facilityQuantity.setMajor(majorDictName);
					   facilityQuantity.setMajor(majorDictId);
					  
//					   facilityQuantity.setDeviceType(deviceTypeDictName);
					   facilityQuantity.setDeviceType(deviceTypeDictId);
					   facilityQuantity.setQuantity(quantity);
					   this.save(facilityQuantity);
	                 }
				   else{
					   FacilityQuantity facilityQuantity=new FacilityQuantity();
					   facilityQuantity.setFactory(factory);
					   facilityQuantity.setMajor(majorDictId);		  
					   facilityQuantity.setDeviceType(deviceTypeDictId);
					   facilityQuantity.setQuantity(0);
					   this.save(facilityQuantity);
				   }
			      }
               }
	     } catch (DictDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	   
	}
	
 }