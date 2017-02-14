package com.boco.eoms.partner.statistically.utils;

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

public class TableHelper  {
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
		for (int i = 0; i < rows.length; i++) {
			countRowMap.put(i, 1);
		}
		Map<Integer, Integer> decideMap = new HashMap<Integer, Integer>();
		Map<Integer, String> levelMap = new HashMap<Integer, String>();
		for (int j = 0; j < rows.length; j++) {
			levelMap.put(j, rows[j]);
		}
		String hqlDictStrin=ApplicationContextHolder.getInstance().getHQLDialect().toString();
		boolean oracleFlag=hqlDictStrin.equals("org.hibernate.dialect.OracleDialect");
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
		ID2NameService service = (ID2NameService) ApplicationContextHolder	.getInstance().getBean("ID2NameGetServiceCatch");
		for (int i = 0; i < resultList.size(); i++) {
			ListOrderedMap childMap = resultList.get(i);
			trList = new ArrayList<TdObjModel>();
			String tdNames = "";
			for (int j = 0; levelLength != 0 && j <= levelLength; j++) {
				String tdName = StaticMethod.nullObject2String(childMap	.get(levelMap.get(j)));
				tdModel = new TdObjModel();
				 if(levelMap.get(j).contains("TypeLikedict")){
					tdName =service.id2Name(tdName, "tawSystemDictTypeDao");
				}
				 if(levelMap.get(j).contains("TypeLikeArea")){
						tdName =service.id2Name(tdName, "tawSystemAreaDao");
					}
				 if(levelMap.get(j).contains("TypeLikeUser")){
						tdName =service.id2Name(tdName, "tawSystemUserDao");
					}
				 if(levelMap.get(j).contains("TypeLikeDept")){
					 tdName =service.id2Name(tdName, "tawSystemDeptDao");
				 }
				 if(levelMap.get(j).contains("TypeFor")){
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
					 else{
						 try {
							 tdName = URLEncoder.encode(tdName, "UTF-8");
							 url+="&"+childMap.get(j).toString().toLowerCase()+"="+tdName;
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
