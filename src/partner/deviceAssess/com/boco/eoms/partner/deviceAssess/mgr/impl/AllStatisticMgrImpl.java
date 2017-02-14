package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.collections.map.ListOrderedMap;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.partner.deviceAssess.dao.AllStatisticDao;
import com.boco.eoms.partner.deviceAssess.mgr.AllStatisticMgr;
import com.boco.eoms.partner.deviceAssess.mgr.AssessConfigService;
import com.boco.eoms.partner.deviceAssess.util.MapObj;

/**
 * 全专业统计
 * 
 * @author huhao
 * @version1.0
 * @time 2011-11-15
 */

public class AllStatisticMgrImpl extends CommonGenericServiceImpl<MapObj>
		implements AllStatisticMgr {

	public void setAllStatisticDao(AllStatisticDao allStatisticDao) {
		this.setCommonGenericDao(allStatisticDao);
	}

	/**
	 * 根据传入的专业进行全设备统计
	 * 
	 * @param special
	 * @return
	 */

	public List<MapObj> calculateList(String special, String factoryDictId,String startTime,String endTime) {
		List<String> factoryList = this.factorySearchList(factoryDictId);
		List<MapObj> list = new ArrayList<MapObj>();
		AssessConfigService assessConfigService=(AssessConfigService) ApplicationContextHolder
		.getInstance().getBean("assessConfigService");
		//key=设备 value=厂家Map 厂家map的key是厂家id，value是得分
		Map firstMap = assessConfigService.resultMap(special, startTime, endTime);// 调用但统计模块数据
		for (Object firstObjectKey : firstMap.keySet()) {
			String treeMapKey = String.valueOf(firstObjectKey);// 得到所选专业对应的设备类型key
			List<String> score = new ArrayList<String>();
			List<String> percent = new ArrayList<String>();
			List<String> ranking = new ArrayList<String>();
			
			MapObj mapObj = new MapObj();
			Map<String, Float> treeMap = (Map<String, Float>) firstMap
					.get(treeMapKey);// 得到所选专业对应的设备类型Map
//			ArrayList<Entry<String, Float>> l = new ArrayList<Entry<String, Float>>(
//					treeMap.entrySet()); // 将得到所选专业对应的设备类型Map
			// 进行从大到小得排序并生成一个以value值为从大到小的东东
			
			TreeMap<Float,String> treeMap2 = new TreeMap<Float,String>();
			Iterator it = treeMap.keySet().iterator();
			while(it.hasNext()){
				String factoryId = StaticMethod.nullObject2String(it.next());
				treeMap2.put(StaticMethod.getFloatValue(StaticMethod.nullObject2String(treeMap.get(factoryId))), "");
			}
			List<Float> ranklist = new ArrayList<Float>();// 进行从小到大的排序并生成一个以value值为从大到小的东东
			for (Float rank : treeMap2.keySet()) {
				ranklist.add(rank);
			}
//			Collections.sort(l, new Comparator<Map.Entry<String, Float>>() {
//				public int compare(Map.Entry<String, Float> o1,
//						Map.Entry<String, Float> o2) {
//					System.out.println(o2.getValue()-o1.getValue());
//					System.out.println(o1.getValue());
//					return (int) (o2.getValue() - o1.getValue());
//				}
//			});
			
			
			CommonSpringJdbcService commonSpringJdbcService = (CommonSpringJdbcService) ApplicationContextHolder
					.getInstance().getBean("commonSpringJdbcService");// 开始构建设备占比
			String sql = "select factory,quantity from pnr_deviceassess_facilityquantity where major='"
					+ special
					+ "'and devicetype='"
					+ treeMapKey
					+ "'and quantity>'0'";// 查询所属专业所对应的设备类型中的厂商名和对应厂商的设备数量
			String sumSql = "select sum(quantity) from pnr_deviceassess_facilityquantity where major='"
					+ special + "'and devicetype='" + treeMapKey + "'";// 查询所属专业所对应的设备总数
			List<ListOrderedMap> resultList = commonSpringJdbcService
					.queryForList(sql);
			Integer sum = commonSpringJdbcService.queryForInt(sumSql);
			List firstPercent = new ArrayList(); // 生成一个新的list里面存放 map<key
			// value> key为厂家，value设备占比
			Map<String, String> firstPercentMap = new HashMap<String, String>();
			for (int i = 0; i < resultList.size(); i++) {// 循环设备MAP<map,map<factory,quantity>>
				Map factoryAndQuantity = resultList.get(i);
				String factoryKey = "";
				Integer quantityValue = 0;
				String percenting = "";
				for (Object objectKey : factoryAndQuantity.keySet()) {// 循环mao<factory,quantity>构建map<cactory,percenting>
					String factoryAndQuantityKey = String.valueOf(objectKey);
					if ("factory".equals(factoryAndQuantityKey)) {
						factoryKey = factoryAndQuantity.get(
								factoryAndQuantityKey).toString();
					}
					if ("quantity".equals(factoryAndQuantityKey)) {
						quantityValue = Integer.parseInt(factoryAndQuantity
								.get(factoryAndQuantityKey).toString());
						NumberFormat nf = NumberFormat.getPercentInstance();
						nf.setMinimumFractionDigits(2); // 设置保留两位小数
						percenting = nf.format(new Float(quantityValue)
								/ new Float(sum.toString()));
					}
					firstPercentMap.put(factoryKey, percenting);
				}
			}
			firstPercent.add(firstPercentMap);
			for (int i = 0; i < factoryList.size(); i++) {
				String factory = factoryList.get(i).toString();
				boolean flag = true;
				for (Object objectKey : treeMap.keySet()) {// 循环设备类型Map
					String mapKey = String.valueOf(objectKey);// 得到Map的key为厂商名
					Float mapValue = StaticMethod.getFloatValue(StaticMethod.nullObject2String(treeMap.get(mapKey)));// 得到Map的value为得分
					if (mapKey.equals(factory)) {
						flag = false;
						score.add(mapValue.toString());// 把得分添加都所对应的厂商得分List
						
						
						if(treeMap.containsKey(factory)){
							int rank = ranklist.indexOf(mapValue);
							if(rank!=-1){                                 // 0   1  2 3  4         size=5
								rank=ranklist.size()-rank;               //[35,76,89,90,99]
								ranking.add(rank+"");
							}
									// 把排名添加到所对应的厂商排名
						}
						
//						for (Entry<String, Float> e : l) {
//							String rankFactory = e.getKey(); // 按排名先后进行排序后去key值
//							if (rankFactory.equals(factory)) {
//								ranking.add(String.valueOf(rank));// 把排名添加到所对应的厂商排名
//							} else {
//								rank++;
//							}
//						}
						for (int c = 0; c < firstPercent.size(); c++) {// 从新建的设备占比list中取出对应厂家的对应占比
							Map<String, String> map = (Map<String, String>) firstPercent
									.get(c);
							for (Object firstPercentKey : map.keySet()) {
								String cc = String.valueOf(firstPercentKey
										.toString());
								if (cc.equals(mapKey)) {
									String ccPercent = map.get(cc);
									percent.add(ccPercent);
								}
							}
						}
					}
				}
				if (flag) {// 判断如果所取出的对应专业的一个设备类型中没有对应的厂家得分，则添加0数据
					score.add("NaN");
					ranking.add("NaN");
					percent.add("NaN");
				}
				mapObj.setKey(treeMapKey);
				mapObj.setListScore(score);
				mapObj.setListRanking(ranking);
				mapObj.setListPercent(percent);
			}
			list.add(mapObj);
		}
		return list;
	}

	/**
	 * 得到所有厂家
	 * 
	 * @param factoryDictId
	 * @return
	 */

	public List<String> factorySearchList(String factoryDictId) {// 厂家父字典值
		List<TawSystemDictType> factoryDictList = new ArrayList<TawSystemDictType>();
		ITawSystemDictTypeManager dictMgr = (ITawSystemDictTypeManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDictTypeManager");
		factoryDictList = dictMgr.getDictSonsByDictid(factoryDictId);// 得到所有厂家对应字典表每条数据
		List<String> factoryList = new ArrayList<String>();
		for (int r = 0; r < factoryDictList.size(); r++) {
			String factoryDict = factoryDictList.get(r).getDictId();
			factoryList.add(factoryDict);
		}
		return factoryList;
	}

	/**
	 * 计算出综合得分 List<MapObj> list 为public List<MapObj> calculateList(String
	 * special)的返回值
	 * 
	 * @param list
	 * @return
	 */

	public List<String> finallyScore(List<MapObj> list,
			List<String> factorySearchList) {
		List<String> finallyScoreList = new ArrayList<String>();
		for (int i = 0; i < factorySearchList.size(); i++) {
			int sum = 0;
			Float score = 0.0f;

			for (int j = 0; j < list.size(); j++) {
				MapObj mapObj = list.get(j);// 得到单个设备对应各种list
				List<String> listScore = (List<String>) mapObj.getListScore();// 得到单个设备对应的所有厂商的得分
				String scoreing = listScore.get(i);
				if (!"NaN".equals(scoreing)) {
					score = score + Float.valueOf(scoreing);
					sum++;
				}
			}
			if (sum == 0) {// 当总数为零时，将总和赋值为1，避免分母为0
				sum = 1;
			}
			score = score / sum;
			if (score == 0.0f) {
				finallyScoreList.add("NaN");
			} else {
				finallyScoreList.add(score.toString());
			}
		}
		return finallyScoreList;
	}

	/**
	 * 计算出综合排名
	 * 
	 * @param list
	 * @return
	 */
	public List<String> finallyRank(List<String> finallyScore,List<String> factorySearchList) {
		List<Float> listScore = new ArrayList<Float>();
		TreeMap<Float,String> mapScore=new TreeMap<Float,String>();
		List<String> finallyRank = new ArrayList<String>();
		for (int i = 0; i < finallyScore.size(); i++) {
			if (!"NaN".endsWith(finallyScore.get(i))) {
				mapScore.put(Float.valueOf(finallyScore.get(i)),factorySearchList.get(i));
			}
		}
		for (Float rank : mapScore.keySet()) {
			listScore.add(rank);//生成从小到大的得分TreeMap
		}

		for (int i = 0; i < finallyScore.size(); i++){
			
			String rank="NaN";
			if(!"NaN".equals(finallyScore.get(i))){
				int  ranking=listScore.indexOf(Float.valueOf(finallyScore.get(i)));
				if(ranking!=-1){
					ranking = listScore.size()-ranking;
					finallyRank.add(String.valueOf(ranking));
				}
			}
			else{
				finallyRank.add(rank);
			
			}
			
		}
		
		
//		
//		for (int i = 0; i < finallyScore.size(); i++) {
//			if (!"/".endsWith(finallyScore.get(i))) {
//				listScore.add(Float.valueOf(finallyScore.get(i)));
//			}
//		}
//		
//		Collections.sort(listScore);// 对listScore进行从小到大排序
//		Integer cc = 1;// 初始化排序值
//		for (int j = 0; j < listScore.size(); j++) {
//			Float floatScore = listScore.get(j);
//			for (int w = 0; w < finallyScore.size(); w++) {
//				if (!"/".endsWith(finallyScore.get(w))) {
//					String asd = finallyScore.get(w);
//					if (0 == floatScore.compareTo(Float.valueOf(finallyScore
//							.get(w)))) {
//						finallyRank.set(w, cc.toString());
//						cc++;
//						break;
//					}
//				}
//			}
//		}
		return finallyRank;
	}

}
