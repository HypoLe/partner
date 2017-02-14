package com.boco.eoms.partner.baseinfo.util;



import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
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
import com.boco.eoms.partner.baseinfo.model.TdObjModel;


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
		// 查询出结果集
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		// 构建一个MAP，放置合并的单元格。
		Map rowSpanMap = new HashMap();
		// 初始化计算器计算rowspan为多少
		Map<Integer, Integer> countRowMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < rows.length; i++) {
			countRowMap.put(i, 1);
		}// 初始化 判断级别的避免父类不同子类相合并的情况
		Map<Integer, Integer> decideMap = new HashMap<Integer, Integer>();
		// 初始化重result里面取值的map
		Map<Integer, String> levelMap = new HashMap<Integer, String>();
		for (int j = 0; j < rows.length; j++) {
			levelMap.put(j, rows[j]);
		}
		levelMap.put(rows.length, "(count)");

		int levelLength = rows.length;
		// 循环查询数据库的结果集
		List<List<TdObjModel>> tableList = new ArrayList<List<TdObjModel>>();
		List<TdObjModel> trList;
		TdObjModel tdModel;
		HashMap<String, Integer> counttdMap = new HashMap<String, Integer>();
		//判断TD的显示，如果相同的显示一次则之后不会再显示了。
		HashMap<String, Boolean> counttdBoolMap = new HashMap<String, Boolean>();
		for (int i = 0; i < resultList.size(); i++) {
			ListOrderedMap childMap = resultList.get(i);
			trList = new ArrayList<TdObjModel>();
			String tdNames = "";
			//循环每一个行
			for (int j = 0; levelLength != 0 && j <= levelLength; j++) {
				String tdName = StaticMethod.nullObject2String(childMap
						.get(levelMap.get(j)));
				tdModel = new TdObjModel();
				String aaaa=levelMap.get(j);
				 if(levelMap.get(j).contains("TypeLikedict")){
					ID2NameService service = (ID2NameService) ApplicationContextHolder
					.getInstance().getBean("ID2NameGetServiceCatch");
					tdName =service.id2Name(tdName, "tawSystemDictTypeDao");
				}
				 if(levelMap.get(j).contains("TypeLikeArea")){
					 if(tdName.equals("1014")){
						 tdName="省下属";
					 }
					 else{
						ID2NameService service = (ID2NameService) ApplicationContextHolder
						.getInstance().getBean("ID2NameGetServiceCatch");
						tdName =service.id2Name(tdName, "tawSystemAreaDao");
						if(null==tdName){
							tdName="未知";
						}
					 }
					}
				 if(levelMap.get(j).contains("TypeLikeUser")){
						ID2NameService service = (ID2NameService) ApplicationContextHolder
						.getInstance().getBean("ID2NameGetServiceCatch");
						tdName =service.id2Name(tdName, "tawSystemUserDao");
					}
				 if(levelMap.get(j).equals("big_type")){
						String ss[]=levelMap.get(j).split(",");
						String sss="";
						for(int ii=0;ii<ss.length;ii++){
							ID2NameService service = (ID2NameService) ApplicationContextHolder
							.getInstance().getBean("ID2NameGetServiceCatch");
							sss =service.id2Name(ss[ii], "tawSystemDictTypeDao")+";";
						}
						tdName=sss;
				 }
				tdModel.setName(tdName);
				
				if (!tdNames.equals("")) {
					tdNames = tdNames + "_";

				}
				tdNames = tdNames + tdName;
				//判断是否为最后的统计数字如果不是将进行比较合并
				if (j != levelLength) {
					int count = 1;
					if (counttdMap.containsKey(tdNames)) {
						count += counttdMap.get(tdNames);
					}
					counttdMap.put(tdNames, count);
					counttdBoolMap.put(tdNames, true);
					 if(levelMap.get(j).contains("TypeLikedict")){
						 url+="&"+childMap.get(j)+"="+StaticMethod.nullObject2String(childMap
									.get(levelMap.get(j)));
						}
					 else if(levelMap.get(j).contains("TypeLikeArea")){
						 url+="&"+childMap.get(j)+"="+StaticMethod.nullObject2String(childMap
									.get(levelMap.get(j)));
						}
					 else if(levelMap.get(j).contains("TypeLikeUser")){
						 url+="&"+childMap.get(j)+"="+StaticMethod.nullObject2String(childMap
									.get(levelMap.get(j)));
						}
					
					 else{
						 //URLEncoder URLEncoder = new URLEncoder();
//						 System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
		//构建最终显示的List
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
