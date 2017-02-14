package com.boco.eoms.partner.baseinfo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.map.ListOrderedMap;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;
public class PnrDeptStatisticsTableHelper {
	/**
	 * @param
	 * 			 rows    the search  colums.
	 * 			 searchSqL	 the search Sql.
	 * 			 url	the amount wil be acquire .set to the tag <a>
	 * @return 	
	 * 			 List 	the table list to show.*/
	public static List<List<TdObjModel>> verticalGrowp(String[] rows, String searchSql,String url) {
		String urlTemp=url;
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		Map rowSpanMap = new HashMap();
		Map<Integer, Integer> countRowMap = new HashMap<Integer, Integer>();
		String hqlDictStrin=ApplicationContextHolder.getInstance().getHQLDialect().toString();
		boolean oracleFlag=hqlDictStrin.equals("org.hibernate.dialect.OracleDialect");
		for (int i = 0; i < rows.length; i++) {
			countRowMap.put(i, 1);
		}
		Map<Integer, Integer> decideMap = new HashMap<Integer, Integer>();
		Map<Integer, String> levelMap = new HashMap<Integer, String>();
		for (int j = 0; j < rows.length; j++) {
			levelMap.put(j, rows[j]);
		}
		if (oracleFlag) {
			levelMap.put(rows.length, "COUNT");
		}else {
			levelMap.put(rows.length, "count");
		}
		int levelLength = rows.length;
		List<List<TdObjModel>> tableList = new ArrayList<List<TdObjModel>>();
		List<TdObjModel> trList;
		TdObjModel tdModel;
		HashMap<String, Integer> counttdMap = new HashMap<String, Integer>();
		HashMap<String, Boolean> counttdBoolMap = new HashMap<String, Boolean>();
		ID2NameService service = (ID2NameService) ApplicationContextHolder
		.getInstance().getBean("ID2NameGetServiceCatch");
		for (int i = 0; i < resultList.size(); i++) {
			ListOrderedMap childMap = resultList.get(i);
			trList = new ArrayList<TdObjModel>();
			String tdNames = "";
			for (int j = 0; levelLength != 0 && j <= levelLength; j++) {
				String tdName = StaticMethod.nullObject2String(childMap
						.get(levelMap.get(j)));
				tdModel = new TdObjModel();
				 if(levelMap.get(j).contains("TypeLikedict")){
					tdName =service.id2Name(tdName, "tawSystemDictTypeDao");
				}else if(levelMap.get(j).contains("TypeLikeArea")){
					tdName =service.id2Name(tdName, "tawSystemAreaDao");
				}else if(levelMap.get(j).contains("TypeLikeUser")){
					tdName =service.id2Name(tdName, "tawSystemUserDao");
				}else	if(levelMap.get(j).contains("TypeLikeDept")){
					 tdName =service.id2Name(tdName, "tawSystemDeptDao");
				}else if(levelMap.get(j).contains("TypeFor")){
						String aa[]=tdName.split(",");
						String bb="";
						for(int ii=0;ii<aa.length;ii++){
							bb =bb+service.id2Name(aa[ii], "tawSystemDictTypeDao")+";";
						}
						tdName=bb;
				}
				tdModel.setName(tdName);
				if (!tdNames.equals("")) {
					tdNames = tdNames + "_";
				}
				tdNames = tdNames + tdName;
				if (j != levelLength) {
					int count = 1;
					if (counttdMap.containsKey(tdNames)) {
						count += counttdMap.get(tdNames);
					}
					counttdMap.put(tdNames, count);
					counttdBoolMap.put(tdNames, true);
					 if(levelMap.get(j).contains("TypeLikedict")){
						 url+="&"+childMap.get(j).toString().toLowerCase()+"="+StaticMethod.nullObject2String(childMap
									.get(levelMap.get(j)));
						}
					 else if(levelMap.get(j).contains("TypeLikeArea")){
						 url+="&"+childMap.get(j).toString().toLowerCase()+"="+StaticMethod.nullObject2String(childMap
									.get(levelMap.get(j)));
						}
					 else if(levelMap.get(j).contains("TypeLikeDept")){
						 url+="&"+childMap.get(j).toString().toLowerCase()+"="+StaticMethod.nullObject2String(childMap
								 .get(levelMap.get(j)));
					 }
					 else if(levelMap.get(j).contains("TypeLikeUser")){
						 url+="&"+childMap.get(j).toString().toLowerCase()+"="+StaticMethod.nullObject2String(childMap
									.get(levelMap.get(j)));
						}
					 else if(levelMap.get(j).contains("TypeFor")){
						 url+="&"+childMap.get(j).toString().toLowerCase()+"="+StaticMethod.nullObject2String(childMap
									.get(levelMap.get(j)));
						}
					 /*为组织个数统计时添加链接地址 add  by fengguangping begin*/
					 else if (levelMap.get(j).contains("pro_")) {
						 if(levelMap.get(j).contains("pro_1")){
							 String url1=url;
							 url1+="&big_type=1122501";
							 tdModel.setUrl(url1);
						 }
						 else if(levelMap.get(j).contains("pro_2")){
							 String url2=url;
							 url2+="&big_type=1122502";
							 tdModel.setUrl(url2);
						 }
						 else if(levelMap.get(j).contains("pro_3")){
							 String url3=url;
							 url3+="&big_type=1122503";
							 tdModel.setUrl(url3);
						 }
						 else if(levelMap.get(j).contains("pro_4")){
							 String url4=url;
							 url4+="&big_type=1122504";
							 tdModel.setUrl(url4);
						 }
						 else if(levelMap.get(j).contains("pro_5")){
							 String url5=url;
							 url5+="&big_type=1122505";
							 tdModel.setUrl(url5);
						 }
						 else if(levelMap.get(j).contains("pro_6")){
							 String url5=url;
							 url5+="&big_type=1122506";
							 tdModel.setUrl(url5);
						 }
						 else if(levelMap.get(j).contains("pro_7")){
							 String url5=url;
							 url5+="&big_type=1122507";
							 tdModel.setUrl(url5);
						 }
						 else if(levelMap.get(j).contains("pro_8")){
							 String url5=url;
							 url5+="&big_type=1122508";
							 tdModel.setUrl(url5);
						 }
						 else if(levelMap.get(j).contains("pro_9")){
							 String url5=url;
							 url5+="&big_type=1122509";
							 tdModel.setUrl(url5);
						 }
					}
					 /*为组织个数统计时添加链接地址 add  by fengguangping end*/
					 
					 /*为入围资金统计时添加链接地址 add  by fengguangping begin*/
					 else if(levelMap.get(j).contains("fund_")){
						 if (levelMap.get(j).contains("fund_1")){
							 String url1=url;
							 url1+="&fund=fund<100";
							 tdModel.setUrl(url1);
						}else if (levelMap.get(j).contains("fund_2")) {
							 String url1=url;
							 url1+="&fund=fund>=100 and fund<500";
							 tdModel.setUrl(url1);
						}else if (levelMap.get(j).contains("fund_3")) {
							 String url1=url;
							 url1+="&fund=fund>=500 and fund <1000";
							 tdModel.setUrl(url1);
						} else if (levelMap.get(j).contains("fund_4")) {
							 String url1=url;
							 url1+="&fund=fund >=1000";
							 tdModel.setUrl(url1);
						} 
					 }
					 /*为入围资金统计时添加链接地址 add  by fengguangping end*/
					 
					 /*为入围级别统计时添加链接地址 add  by fengguangping begin*/
					 else if(levelMap.get(j).contains("sellevel_")){
						 if (levelMap.get(j).contains("sellevel_1")){
							 String url1=url;
							 url1+="&selectedlevel=1240301";
							 tdModel.setUrl(url1);
						}else if (levelMap.get(j).contains("sellevel_2")) {
							 String url1=url;
							 url1+="&selectedlevel=1240302";
							 tdModel.setUrl(url1);
						}else if (levelMap.get(j).contains("sellevel_3")) {
							 String url1=url;
							 url1+="&selectedlevel=1240303";
							 tdModel.setUrl(url1);
						} 
					 }
					 /*为入围级别统计时添加链接地址 add  by fengguangping end*/
					 
					 /*为组织性质统计时添加链接地址 add  by fengguangping begin*/
					 else if(levelMap.get(j).contains("comtype_")){
						 if (levelMap.get(j).contains("comtype_1")){
							 String url1=url;
							 url1+="&type=112010101";
							 tdModel.setUrl(url1);
						}else if (levelMap.get(j).contains("comtype_2")) {
							 String url1=url;
							 url1+="&type=112010102";
							 tdModel.setUrl(url1);
						}else if (levelMap.get(j).contains("comtype_3")) {
							 String url1=url;
							 url1+="&type=112010103";
							 tdModel.setUrl(url1);
						}else if (levelMap.get(j).contains("comtype_4")) {
							 String url1=url;
							 url1+="&type=112010104";
							 tdModel.setUrl(url1);
						} else if (levelMap.get(j).contains("comtype_5")) {
							 String url1=url;
							 url1+="&type=112010105";
							 tdModel.setUrl(url1);
						} else if (levelMap.get(j).contains("comtype_6")) {
							 String url1=url;
							 url1+="&type=112010106";
							 tdModel.setUrl(url1);
						} else if (levelMap.get(j).contains("comtype_7")) {
							 String url1=url;
							 url1+="&type=112010107";
							 tdModel.setUrl(url1);
						} else if (levelMap.get(j).contains("comtype_8")) {
							 String url1=url;
							 url1+="&type=112010108";
							 tdModel.setUrl(url1);
						} 
					 }
					 /*为组织性质统计时添加链接地址 add  by fengguangping end*/
					 else{
						 //URLEncoder URLEncoder = new URLEncoder();
						 try {
							 tdName = URLEncoder.encode(tdName, "UTF-8");
							 url+="&"+childMap.get(j)+"="+tdName;
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 }
				}else{			
					tdModel.setUrl(url);
					url=urlTemp;
					tdModel.setShow(true);
				}
				trList.add(tdModel);
			}
			tableList.add(trList);
		}
		for (List<TdObjModel> trListTemp : tableList) {
			String tdNames = "";
			int i = 0;
			for (TdObjModel tdObj : trListTemp) {
				
				if(i==levelLength)
					continue;
				String tdName = tdObj.getName();
				if (!tdNames.equals("")) {
					tdNames = tdNames + "_";
				}
				tdNames = tdNames + tdName;
				int rowspan = counttdMap.get(tdNames);
				tdObj.setRowspan(rowspan);
				if(counttdBoolMap.containsKey(tdNames)){
					counttdBoolMap.remove(tdNames);
					tdObj.setShow(true);
				}else{
					tdObj.setShow(false);
				}
				i+=1;
			}
		}
		return tableList;
	}
}
