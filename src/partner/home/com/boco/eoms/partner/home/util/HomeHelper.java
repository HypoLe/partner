package com.boco.eoms.partner.home.util;

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

public class HomeHelper  {
	/**
	 * @author huhao
	 * @param numMin 合并前几列
 	 * @param numMax 合并后几列，如果不合并则为0
	 * @param toCol  后几列根据第几列合并，如果不合并则为0
	 * @param resultList 输入结果集
	 * @return tableList
	 */
	public static List<List<TdObjModel>> verticalGrowp(int numMin,int numMax,int toCol, List<List> resultList) {
		
		
		// 构建一个MAP，放置合并的单元格。
		Map rowSpanMap = new HashMap();
		// 初始化计算器计算rowspan为多少
		Map<Integer, Integer> countRowMap = new HashMap<Integer, Integer>();
        
		int levelLength=0;
		if(!resultList.isEmpty()){
		List list=resultList.get(0);
		 levelLength=list.size();
		}
		// 循环查询数据库的结果集
		Map<Double,List<List<TdObjModel>>> tableMap=new HashMap<Double,List<List<TdObjModel>>>();
		String mapKey="";	
		Double totalFee=0d;
		List<List<TdObjModel>> tableList = new ArrayList<List<TdObjModel>>();
		List<TdObjModel> trList;
		TdObjModel tdModel;
		HashMap<String, Integer> counttdMap = new HashMap<String, Integer>();
		//判断TD的显示，如果相同的显示一次则之后不会再显示了。
		HashMap<String, Boolean> counttdBoolMap = new HashMap<String, Boolean>();
		for (int i = 0; i < resultList.size(); i++) {
			List childList = resultList.get(i);
			trList = new ArrayList<TdObjModel>();
			String tdNames = "";
			String urlString="";
			
			
			//循环每一个行
			for (int j = 0; levelLength != 0 && j <levelLength; j++) {
				String tdValue = StaticMethod.nullObject2String(childList
						.get(j));
				tdModel = new TdObjModel();
				tdModel.setName(tdValue);
				if (!tdNames.equals("")) {
					tdNames = tdNames + "_";
				}
				tdNames = tdNames + tdValue;
				//判断是否为最后的统计数字如果不是将进行比较合并
				if (j < levelLength) {
					int count = 1;
					if(j<numMin){
					if (counttdMap.containsKey(tdNames)) {
						count += counttdMap.get(tdNames);
					}}
					counttdMap.put(tdNames, count);
					counttdBoolMap.put(tdNames, true);
		         }
				if (j == 1){	
					 try {
						 tdValue = URLEncoder.encode(tdValue, "UTF-8");
						 urlString+=tdValue;
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tdModel.setUrl(urlString);
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
				String tdValue = tdObj.getName();
				if (!tdNames.equals("")) {
					tdNames = tdNames + "_";
				}
				tdNames = tdNames + tdValue;
				int rowspan = counttdMap.get(tdNames);
				tdObj.setRowspan(rowspan);
				if(i<numMin){
					if(counttdBoolMap.containsKey(tdNames)){
						counttdBoolMap.remove(tdNames);
						tdObj.setShow(true);
					}else{
						tdObj.setShow(false);
					}
				}else{tdObj.setShow(true);}
				
				i+=1;
			}
		}
		//合并后几列
		int rowspanAll=0;
		if (numMax!=0&&toCol!=0) {//不为0就跳过
			for(int a=0;a<tableList.size();){
				int rowspan=1;
				List list=tableList.get(a);        
				TdObjModel tom=(TdObjModel)list.get(toCol-1);
				rowspan=tom.getRowspan();    
				rowspanAll=rowspanAll+rowspan;
				for(int b=numMax-1;b<list.size();b++){
					TdObjModel tomMax=(TdObjModel)list.get(b);     
					tomMax.setRowspan(rowspan);
					tomMax.setShow(true);
				}
				for(int c=a+1;c<rowspanAll;c++){
					List otherList=tableList.get(c); 
					for(int d=numMax-1;d<otherList.size();d++){ 
						TdObjModel tomOtherMax=(TdObjModel)otherList.get(d);
						tomOtherMax.setRowspan(rowspan);
						tomOtherMax.setShow(false);
					}
				}
				a=a+rowspan;
			}
		}
		return tableList;
	}
	
	/**
	 * 强制保留小数位
	 * @param format 传入要强制保留小数的字符串
	 * @param formatNum 传入要强制保留的小数位数
	 * @return
	 */
	public static String formatObject(String format,int formatNum){
		String[] newformat=format.split("\\u002E");
		String returnFormat=format;
		String end="";
		if(newformat.length==2){
			 end=newformat[1];
			if(end.length()<formatNum){
				int sum=formatNum-end.length();
				if(sum>0){
					for(int i=0;i<sum;i++){
						returnFormat=returnFormat+"0";
					}
				}
				
			}
		}else if(newformat.length==1){
			 end=newformat[0];
			 returnFormat=returnFormat+".";
			 for(int i=0;i<formatNum;i++){
				 returnFormat=returnFormat+"0";
			 }
		}
		
				return returnFormat;
		
		
	}
}
