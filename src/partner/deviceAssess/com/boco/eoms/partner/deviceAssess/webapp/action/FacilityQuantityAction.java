package com.boco.eoms.partner.deviceAssess.webapp.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONObject;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.partner.deviceAssess.mgr.FacilityQuantityMgr;
import com.boco.eoms.partner.deviceAssess.model.FacilityQuantity;
import com.boco.eoms.partner.deviceAssess.util.MapObj;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;


public final class FacilityQuantityAction extends BaseAction{

	

	   public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

		  FacilityQuantityMgr facilityQuantityMgr=(FacilityQuantityMgr)getBean("facilityQuantityMgr");
		  List mapObjList = new ArrayList();
		  String nameOrId="name";
		  mapObjList=facilityQuantityMgr.getMapObjList(nameOrId);
		  request.setAttribute("mapObjList", mapObjList);
   
			return mapping.findForward("goToAdd");
		}
	   
	   public ActionForward add(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {  
	   
	   Map map = request.getParameterMap();//将前台填写内容放入Map中     
	   FacilityQuantityMgr facilityQuantityMgr=(FacilityQuantityMgr)getBean("facilityQuantityMgr");
	   facilityQuantityMgr.saveFacilityQuantity(map);
	   
	   return mapping.findForward("success");
	   }
		
	   
	   
	   public ActionForward list(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

		      FacilityQuantityMgr facilityQuantityMgr=(FacilityQuantityMgr)getBean("facilityQuantityMgr");	
		      List mapObjNameList = new ArrayList();
		      mapObjNameList=facilityQuantityMgr.getMapObjList("name");	
		      
		      List mapObjIdList = new ArrayList();
			  mapObjIdList=facilityQuantityMgr.getMapObjList("id"); 
		      
			  Search search = new Search();	
			  
			  SearchResult<FacilityQuantity> searchResult = facilityQuantityMgr.searchAndCount(search);		 //从数据库里取得结果集 
			  List<FacilityQuantity>  facilityQuantityList = searchResult.getResult();//
			 
			  
			  String factoryDictId="1121502";//厂家字典值
			  List<TawSystemDictType> factoryDictList=new ArrayList<TawSystemDictType>();
			  ITawSystemDictTypeManager dictMgr=(ITawSystemDictTypeManager)ApplicationContextHolder
			   .getInstance().getBean("ItawSystemDictTypeManager");
			  factoryDictList=dictMgr.getDictSonsByDictid(factoryDictId);//得到所有厂家对应字典表每条数据
			  
			 List<MapObj> modelList=new ArrayList<MapObj>(); 
			 for(int i=0; i<factoryDictList.size(); i++){// 循环数据库里取得的数据，将相同厂家的信息封装在一个数据结构里面
				 
				 String factoryDict= factoryDictList.get(i).getDictId();
				 MapObj model = new MapObj();
				 String factory="";
				 List<Map> quantityList=new ArrayList<Map>();//定义一个List,用来封装每个厂家的信息
	 
				 for(int j=0; j<facilityQuantityList.size(); j++){
					 
					 Map map=new HashMap();
					 FacilityQuantity  facilityQuantity =facilityQuantityList.get(j);
					 factory=facilityQuantity.getFactory();
					 
					 if(factory.equals(factoryDict)){					 
					 String deviceType=facilityQuantity.getDeviceType();
				     Integer quantity= facilityQuantity.getQuantity();
					 map.put(deviceType,quantity);//把每种设备类型和对应的数量放一个Map里
					 quantityList.add(map);
					 } 		 
			        }
				if(!quantityList.isEmpty())
				{
				 model.setList(quantityList);
				 model.setKey(factoryDict);
				 modelList.add(model);
				}
			 } 

			 List factoryList=new ArrayList();
			 for(int i=0; i<modelList.size(); i++){
				 
		 
				 List numberList=new ArrayList();
				 MapObj modelMapObj = new MapObj();
				 modelMapObj=modelList.get(i);
				 List mapList=modelMapObj.getList();

				 String factoryName=modelMapObj.getKey();
//				 String factoryName=dictMgr.id2Name(modelMapObj.getKey());//厂家
				 MapObj modelfactory = new MapObj();
				 
//				 numberList.add(factoryName);
				 
				 
				 for(int a=0; a<mapObjIdList.size(); a++){
					 
					List tdList=((MapObj)mapObjIdList.get(a)).getList();
					for(int b=0; b<tdList.size(); b++){
							 
						String deviceType=tdList.get(b).toString();
						Integer number=0;
						 for(int j=0; j<mapList.size(); j++)
						 {
							 
							 Map map=new HashMap();
							 map=(Map)mapList.get(j);
							if(map.containsKey(deviceType)){
								number=Integer.valueOf(map.get(deviceType).toString());
							}	 
						 }
						 
						 numberList.add(number);
						
						 }
						 
					 }		 
				 modelfactory.setKey(factoryName);
				 modelfactory.setList(numberList);
				 factoryList.add(modelfactory);
				 
			 }
		  
			 
	      request.setAttribute("factoryList", factoryList);
		  request.setAttribute("mapObjList", mapObjNameList);
			return mapping.findForward("list");
		}
	   
	   
	   public ActionForward goEdit(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

		   
		   String factory=request.getParameter("factory");//取得厂家字典值ID
		   FacilityQuantityMgr facilityQuantityMgr=(FacilityQuantityMgr)getBean("facilityQuantityMgr");	
		   ITawSystemDictTypeManager dictMgr=(ITawSystemDictTypeManager)getBean("ItawSystemDictTypeManager");
		   Search search = new Search();	
		   search.addFilterEqual("factory", factory);		
		   SearchResult<FacilityQuantity> searchResult = facilityQuantityMgr.searchAndCount(search);//从数据库里取得结果集 	  
		   List<FacilityQuantity>  facilityQuantityList = searchResult.getResult();// 
		  
 
		   List mapObjList = new ArrayList();		 
		   String nameOrId="id";		  
		   mapObjList=facilityQuantityMgr.getMapObjList(nameOrId); 
		   
		   List modelList=new ArrayList();
		   JSONArray indicatorResult = new JSONArray();
		   for(int i=0;i<mapObjList.size();i++){
			   MapObj modelMapObj = new MapObj();
			    
			   String key=dictMgr.id2Name(((MapObj)mapObjList.get(i)).getKey());			
			   List  deviceTypeList=((MapObj)mapObjList.get(i)).getList();//得到每种专业下对应的设备类型集合			
			   List<Integer> numberList=new ArrayList<Integer>();//封装设备类型数量			
			   List<String>  typeList=new ArrayList<String>();//封装设备类型名	
			   List<Map> mapList=new ArrayList<Map>();
			   for(int j=0;j<deviceTypeList.size();j++){
				 Map<String,Integer> map=new HashMap<String,Integer>(); 
				 JSONObject deviceTypeJSON = new JSONObject();
				 
				 String deviceType= deviceTypeList.get(j).toString();//设备类型	
				 String deviceTypeIdName=dictMgr.id2Name(deviceType);
				 Integer deviceTypeNumber=0;
				 for(int k=0;k<facilityQuantityList.size();k++){
					 FacilityQuantity facilityQuantity=facilityQuantityList.get(k);
					 String deviceTypeName=facilityQuantity.getDeviceType();
					 if(deviceTypeName.equals(deviceType)){
						 deviceTypeNumber=facilityQuantity.getQuantity();
					 }
				 }
				 
				 deviceTypeJSON.put(deviceType, deviceTypeNumber);
				 map.put(deviceTypeIdName, deviceTypeNumber);
				 mapList.add(map);
			  }
			   modelMapObj.setKey(key);
			   modelMapObj.setMapList(mapList);
			   modelList.add(modelMapObj);
		  }
		  
		  request.setAttribute("factory", factory);
		  request.setAttribute("modelList", modelList);
  
			return mapping.findForward("goEdit");
		}
	   
	   
	   
	   public ActionForward  checkfactory(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		   
		   String factory=request.getParameter("factory");//取得厂家字典值ID
		   if(factory!=null){
		   FacilityQuantityMgr facilityQuantityMgr=(FacilityQuantityMgr)getBean("facilityQuantityMgr");	
		   Search search = new Search();	
		   search.addFilterEqual("factory", factory);			 
		   List<FacilityQuantity>  facilityQuantityList= facilityQuantityMgr.search(search);//从数据库里取得结果集 	 
		   PrintWriter  writer = response.getWriter();
		   JSONArray  aa  = new JSONArray();
		   JSONObject obj = new JSONObject();
		   int a=0;
		  
		   if(facilityQuantityList.isEmpty()){	 
			   a=1;
		   }
			   obj.put("writerResult",a);		  
			   aa.put(obj);
			   writer.print(aa.toString());
		}	   
	   return null;
	   }
	   }
