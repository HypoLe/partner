package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.map.ListOrderedMap;


import com.boco.eoms.base.expression.Expression2;
import com.boco.eoms.base.expression.Param;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.transaction.test.model.ApplyMain;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.partner.deviceAssess.dao.AssessConfigDao;
import com.boco.eoms.partner.deviceAssess.mgr.AssessConfigService;
import com.boco.eoms.partner.deviceAssess.mgr.AssessIndicatorService;
import com.boco.eoms.partner.deviceAssess.mgr.AssessIndicatorSubService;
import com.boco.eoms.partner.deviceAssess.mgr.FacilityQuantityMgr;
import com.boco.eoms.partner.deviceAssess.model.AssessConfig;
import com.boco.eoms.partner.deviceAssess.model.AssessIndicator;
import com.boco.eoms.partner.deviceAssess.model.AssessIndicatorSub;
import com.boco.eoms.partner.deviceAssess.model.FacilityQuantity;
import com.boco.eoms.partner.deviceAssess.util.StatisticCalculate;
import com.boco.eoms.partner.deviceAssess.util.StatisticModel;
import com.googlecode.genericdao.search.Search;

public class AssessConfigServiceImpl extends
		CommonGenericServiceImpl<AssessConfig> implements AssessConfigService {
	private AssessConfigDao assessConfigDao;
	private static Map finalMap;
	public AssessConfigDao getAssessConfigDao() {
		return assessConfigDao;
	}

	public void setAssessConfigDao(AssessConfigDao assessConfigDao) {
		this.assessConfigDao = assessConfigDao;
		this.setCommonGenericDao(assessConfigDao);
	}
	
	static{
		finalMap = new HashMap();
	}

	/**
	 * 厂家故障率计算
	 * 
	 * @param factoryList
	 *            此设备类型的厂家
	 * @param quantityMap
	 *            每个厂家对应的设备数量
	 * @param deviceType
	 *            设备类型ID
	 * @return
	 */
	public Map<String, Float> calculateDeviceFault(List factoryList,
			Map<String, Integer> quantityMap, String deviceType,String startTime,String endTime) {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "select factory,sum(total) from (select factory,total  from pnr_deviceAssess_FACDISPOSE where equipment_type='"
				+ deviceType
				+ "' and create_time>'"+startTime+"' and create_time<'"+endTime+"'  union all "
				+ " select factory,total  from pnr_deviceAssess_insideDispose where equipment_type='"
				+ deviceType +"' and create_time>'"+startTime+"' and create_time<'"+endTime+ "') group by factory  ";
		List result = jdbcService.queryForList(sql);
		Map<String, Integer> faultNum = new HashMap<String, Integer>();// 每个厂家对应的设备故障次数
		Map<String, Float> fault = new HashMap<String, Float>();// 每个厂家对应的设备故障率
		for (int i = 0; i < result.size(); i++) {
			Map map = (Map) result.get(i);
			BigDecimal sum = (BigDecimal)map.get("(sum)");
			faultNum.put((String) map.get("factory"),sum.intValue());
		}
		for (int i = 0; i < factoryList.size(); i++) {
			String factory = (String) factoryList.get(i);
			fault.put(factory, faultNum.containsKey(factory) ? new Float(faultNum.get(factory))/ new Float(quantityMap.get(factory)) : 0 / new Float(quantityMap.get(factory)));
		}

		return fault; 
	}

	/**
	 * 坏板率计算
	 * 
	 * @param factoryList
	 * @param quantityMap
	 * @param deviceType
	 * @return
	 */
	public Map<String, Float> calculateBoardFaule(List factoryList,
			Map<String, Integer> quantityMap, String deviceType,String startTime,String endTime) {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "select factory, sum(total) from pnr_deviceassess_repairinfo where equipment_type='"
				+ deviceType +"' and create_time>'"+startTime+"' and create_time<'"+endTime+ "'  group by factory";
		List result = jdbcService.queryForList(sql);
		Map<String, Integer> num = new HashMap<String, Integer>();
		Map<String, Float> numRat = new HashMap<String, Float>();
		for (int i = 0; i < result.size(); i++) {
			Map map = (Map) result.get(i);
			BigDecimal sum = (BigDecimal)map.get("(sum)");
			num.put((String) map.get("factory"),sum.intValue());
		}
		for (int i = 0; i < factoryList.size(); i++) {
			String factory = (String) factoryList.get(i);
			numRat.put(factory, num.containsKey(factory) ? new Float(num.get(factory))/ new Float(quantityMap.get(factory)) : 0 / new Float(quantityMap.get(factory)));
		}

		return numRat;
	}

	/**
	 * 重大设备故障次数
	 * 
	 * @param factoryList
	 * @param quantityMap
	 * @param deviceType
	 * @return
	 */
	public Map<String, Integer> calculateBigFaultScore(List factoryList,
			Map<String, Integer> quantityMap, String deviceType,String startTime,String endTime) {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "select factory, sum(total) from pnr_deviceAssess_bigFault where equipment_type='"
				+ deviceType +"' and create_time>'"+startTime+"' and create_time<'"+endTime+ "' group by factory";
		List result = jdbcService.queryForList(sql);
		Map<String, Integer> num = new HashMap<String, Integer>();
		Map<String, Integer> numRat = new HashMap<String, Integer>();
		for (int i = 0; i < result.size(); i++) {
			Map map = (Map) result.get(i);
			BigDecimal sum = (BigDecimal)map.get("(sum)");
			num.put((String) map.get("factory"),-1*sum.intValue());
		}
		for (int i = 0; i < factoryList.size(); i++) {
			String factory = (String) factoryList.get(i);
			numRat.put(factory, num.containsKey(factory) ? num
					.get(factory) : 0);
		}
		return numRat;
	}

	/**
	 * 设备问题数
	 * 
	 * @param factoryList
	 * @param quantityMap
	 * @param deviceType
	 * @return
	 */
	public Map<String, Integer> calculateProScore(List factoryList,
			Map<String, Integer> quantityMap, String deviceType,String startTime,String endTime) {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "select factory, sum(total) from pnr_deviceassess_facilityinfo where equipment_type='"
				+ deviceType +"' and occur_time>'"+startTime+"' and occur_time<'"+endTime+ "' group by factory";
		System.out.print(sql);
		List result = jdbcService.queryForList(sql);
		Map<String, Integer> num = new HashMap<String, Integer>();
		Map<String, Integer> numRat = new HashMap<String, Integer>();
		for (int i = 0; i < result.size(); i++) {
			Map map = (Map) result.get(i);
			BigDecimal sum = (BigDecimal)map.get("(sum)");
			num.put((String) map.get("factory"),-1*sum.intValue());
		}
		for (int i = 0; i < factoryList.size(); i++) {
			String factory = (String) factoryList.get(i);
			numRat.put(factory, num.containsKey(factory) ? num
					.get(factory) : 0);
		}
		return numRat;
	}

	/**
	 * 软件申请及升级问题
	 * 
	 * @param factoryList
	 * @param quantityMap
	 * @param deviceType
	 * @return
	 */
	public List calculateUpdateFault(List factoryList,
			Map<String, Integer> quantityMap, String deviceType,String startTime,String endTime) {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sqlUpdata = "select factory,sum(total) from PNR_DEVICEASSESS_SOFTUPINFO where equipment_type='"
				+ deviceType +"' and create_time>'"+startTime+"' and create_time<'"+endTime+ "' group by factory";
		String sql = "select factory,sum(total) from PNR_DEVICEASSESS_SOFTAPPLYRECORD where equipmenttype='"
				+ deviceType +"' and createtime>'"+startTime+"' and createtime<'"+endTime+ "' group by factory";
		List result = jdbcService.queryForList(sql);
		List list = new ArrayList();
		Map num = new HashMap();
		num.put("材料不足", "b");
		for (int i = 0; i < result.size(); i++) {
			Map map = (Map) result.get(i);
			BigDecimal sum = (BigDecimal)map.get("(sum)");
			num.put((String) map.get("factory"), -1*sum.intValue());
		}
		list.add(num);
		Map filedNum = new HashMap();
		filedNum.put("升级失败", "a");
		List filedresult = jdbcService.queryForList(sqlUpdata);
		for (int i = 0; i < filedresult.size(); i++) {
			Map map = (Map) filedresult.get(i);
			BigDecimal sum = (BigDecimal)map.get("(sum)");
			num.put((String) map.get("factory"), -1*sum.intValue());

		}
		list.add(filedNum);
		return list;
	}

	/**
	 * 障处理平均时长
	 * 
	 * @param factoryList
	 * @param quantityMap
	 * @param deviceType
	 * @return
	 */
	public Map<String, Float> calculateFaultTalkTime(List factoryList,
			Map<String, Integer> quantityMap, String deviceType,String startTime,String endTime) {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "SELECT factory, CAST(SUM(INTERVAL(0) MINUTE(4) TO MINUTE+fault_long) as varchar(20))/count(*) from pnr_deviceAssess_FACDISPOSE where equipment_type='"
				+ deviceType +"' and create_time>'"+startTime+"' and create_time<'"+endTime+ "' group by  factory";
		List result = jdbcService.queryForList(sql);
		Map<String, Float> num = new HashMap<String, Float>();
		Map<String, Float> numRat = new HashMap<String, Float>();
		for (int i = 0; i < result.size(); i++) {
			Map map = (Map) result.get(i);
			num.put((String) map.get("factory"),  new Float((Double)map.get("(expression)")));
		}
		for (int i = 0; i < factoryList.size(); i++) {
			String factory = (String) factoryList.get(i);
			numRat.put(factory, num.containsKey(factory) ? new Float(num
					.get(factory)) : 0);
		}
		return numRat;
	}

	/**
	 * 业务恢复处理平均时长
	 * 
	 * @param factoryList
	 * @param quantityMap
	 * @param deviceType
	 * @return
	 */
	public Map<String, Float> calculateBusiTalkTime(List factoryList,
			Map<String, Integer> quantityMap, String deviceType,String startTime,String endTime) {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "SELECT factory, CAST(SUM(INTERVAL(0) MINUTE(4) TO MINUTE+resume_long) as varchar(20))/count(*) from pnr_deviceAssess_FACDISPOSE where equipment_type='"
				+ deviceType +"' and create_time>'"+startTime+"' and create_time<'"+endTime+ "' group by  factory";
		List result = jdbcService.queryForList(sql);
		Map<String, Float> num = new HashMap<String, Float>();
		Map<String, Float> numRat = new HashMap<String, Float>();
		for (int i = 0; i < result.size(); i++) {
			Map map = (Map) result.get(i);
			num.put((String) map.get("factory"), new Float((Double)map.get("(expression)")));
		}
		for (int i = 0; i < factoryList.size(); i++) {
			String factory = (String) factoryList.get(i);
			numRat.put(factory, num.containsKey(factory) ? new Float(num
					.get(factory)) : 0);
		}
		return numRat;
	}

	/**
	 * 板件返修平均时长
	 * 
	 * @param factoryList
	 * @param quantityMap
	 * @param deviceType
	 * @return
	 */
	public Map<String, Float> calculateBoardTalkTime(List factoryList,
			Map<String, Integer> quantityMap, String deviceType,String startTime,String endTime) {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "SELECT factory, CAST(SUM(INTERVAL(0) MINUTE(4) TO MINUTE+repair_long_hour) as varchar(20))/count(*) from pnr_deviceassess_repairinfo where equipment_type='"
				+ deviceType +"' and create_time>'"+startTime+"' and create_time<'"+endTime+ "' group by  factory";
		List result = jdbcService.queryForList(sql);
		Map<String, Float> num = new HashMap<String, Float>();
		Map<String, Float> numRat = new HashMap<String, Float>();
		for (int i = 0; i < result.size(); i++) {
			Map map = (Map) result.get(i);
			num.put((String) map.get("factory"),  new Float((Double)map.get("(expression)")));
		}
		for (int i = 0; i < factoryList.size(); i++) {
			String factory = (String) factoryList.get(i);
			numRat.put(factory, num.containsKey(factory) ? new Float(num
					.get(factory)) : 0);
		}
		return numRat;
	}

	/**
	 * 咨询服务反馈平均时长
	 * 
	 * @param factoryList
	 * @param quantityMap
	 * @param deviceType
	 * @return
	 */
	public Map<String, Float> calculateReferTalkTime(List factoryList,
			Map<String, Integer> quantityMap, String deviceType,String startTime,String endTime) {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "SELECT factory, CAST(SUM(INTERVAL(0) MINUTE(4) TO MINUTE+finally_long) as varchar(20))/count(*) from pnr_deviceAssess_counsel where equipment_type='"
				+ deviceType +"' and create_time>'"+startTime+"' and create_time<'"+endTime+ "' group by  factory";
		List result = jdbcService.queryForList(sql);
		Map<String, Float> num = new HashMap<String, Float>();
		Map<String, Float> numRat = new HashMap<String, Float>();
		for (int i = 0; i < result.size(); i++) {
			Map map = (Map) result.get(i);
			num.put((String) map.get("factory"),  new Float((Double)map.get("(expression)")));
		}
		for (int i = 0; i < factoryList.size(); i++) {
			String factory = (String) factoryList.get(i);
			numRat.put(factory, num.containsKey(factory) ? new Float(num
					.get(factory)) : 0);
		}
		return numRat;
	}

	/**
	 * 技术服务满意度
	 * 
	 * @param factoryList
	 * @param quantityMap
	 * @param deviceType
	 * @return
	 */
	public Map<String, Float> calculateSkillServe(List factoryList,
			Map<String, Integer> quantityMap, String deviceType,String startTime,String endTime) {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "select factory,sum(satisfaction)/count(*) from  (select equipment_type,factory,satisfaction,create_time  from pnr_deviceAssess_lserveinfo union  all "
				+ "select equipment_type,factory,satisfaction,create_time  from pnr_deviceAssess_peventinfo union  all "
				+ "select equipment_type ,factory,satisfaction,create_time  from pnr_deviceAssess_FACDISPOSE) where equipment_type='"
				+ deviceType +"' and create_time>'"+startTime+"' and create_time<'"+endTime+ "' group by  factory";
		List result = jdbcService.queryForList(sql);
		Map<String, Float> num = new HashMap<String, Float>();
		Map<String, Float> numRat = new HashMap<String, Float>();
		for (int i = 0; i < result.size(); i++) {
			Map map = (Map) result.get(i);
			num.put((String) map.get("factory"), new Float((Double)map.get("(expression)")));
		}
		for (int i = 0; i < factoryList.size(); i++) {
			String factory = (String) factoryList.get(i);
			numRat.put(factory, num.containsKey(factory) ? new Float(num
					.get(factory)) : 0);
		}
		return numRat;
	}

	/**
	 * 工程服务满意度
	 * 
	 * @param factoryList
	 * @param quantityMap
	 * @param deviceType
	 * @return
	 */
	public Map<String, Float> calculateProjectServe(List factoryList,
			Map<String, Integer> quantityMap, String deviceType,String startTime,String endTime) {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "select factory, sum(satisfaction)/count(*)  from pnr_deviceAssess_peventinfo where equipment_type='"
				+ deviceType +"' and create_time>'"+startTime+"' and create_time<'"+endTime + "' group by  factory";
		List result = jdbcService.queryForList(sql);
		Map<String, Float> num = new HashMap<String, Float>();
		Map<String, Float> numRat = new HashMap<String, Float>();
		for (int i = 0; i < result.size(); i++) {
			Map map = (Map) result.get(i);
			num.put((String) map.get("factory"), new Float((Double)map.get("(expression)")));
		}
		for (int i = 0; i < factoryList.size(); i++) {
			String factory = (String) factoryList.get(i);
			numRat.put(factory, num.containsKey(factory) ? new Float(num
					.get(factory)) : 0);
		}
		return numRat;
	}

	/**
	 * 培训服务满意度
	 * 
	 * @param factoryList
	 * @param quantityMap
	 * @param deviceType
	 * @return
	 */
	public Map<String, Float> calculateTrainServe(List factoryList,
			Map<String, Integer> quantityMap, String deviceType,String startTime,String endTime) {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String specilIdSql  = "select parentdictid from taw_system_dicttype where  dictid='"+deviceType+"'";
		List<ListOrderedMap> list = jdbcService.queryForList(specilIdSql);
		String specilID = (String)list.get(0).get("parentdictid");
		String sql = "select factory, sum(satisfaction)/count(*)  from pnr_deviceassess_ftrain_info where speciality='"
				+ specilID +"' and create_time>'"+startTime+"' and create_time<'"+endTime+ "' group by  factory";
		List result = jdbcService.queryForList(sql);
		Map<String, Float> num = new HashMap<String, Float>();
		Map<String, Float> numRat = new HashMap<String, Float>();
		for (int i = 0; i < result.size(); i++) {
			Map map = (Map) result.get(i);
			num.put((String) map.get("factory"),new Float((Double)map.get("(expression)")));
		}
		for (int i = 0; i < factoryList.size(); i++) {
			String factory = (String) factoryList.get(i);
			numRat.put(factory, num.containsKey(factory) ? new Float(num
					.get(factory)) : 0);
		}
		return numRat;
	}

	/**
	 * 计算最后得分的MAP
	 * 
	 * @param factoryList
	 *            厂商列表
	 * @param quantityMap
	 *            厂商对应的设备数量的MAP
	 * @param deviceType
	 *            统计的设备类型
	 * @param indicatorName
	 *            统计的项目名字
	 * @return scoreMap 每个厂家对应的一个分数
	 */
	public Map scoreMap(List factoryList, Map<String, Integer> quantityMap,
			String deviceType, String indicatorName, String id,String startTime,String endTime) {

		Map scoreMap = new HashMap();
		String arithmetic = StatisticCalculate.expression(indicatorName, id);
		AssessIndicatorSubService assessIndicatorSubService = (AssessIndicatorSubService) ApplicationContextHolder
				.getInstance().getBean("assessIndicatorSubService");
		AssessIndicatorSub assessIndicatorSub = assessIndicatorSubService
				.searchUnique((new Search().addFilterEqual("indicatorName",
						indicatorName).addFilterEqual("indicatorid", id)));
		String score = assessIndicatorSub.getScore();
		String[] scores = score.split(";");
		//在每个if里面计算一行的得分最后返回每个厂家对应的分数
		if ("设备故障率".equals(indicatorName)) {
			//调用对应的SQL 算出对应的比例或者分数
			Map<String, Float> map = calculateDeviceFault(factoryList,
					quantityMap, deviceType,startTime,endTime);
			String min = "0";
			if (map.size() > 0) {
				Map tempMap = StatisticCalculate.maxOrMin(map, "min");
				Set keySet = tempMap.keySet();
				for (Object key : keySet) {
					min = String.valueOf(tempMap
							.get(key));
				}
			}
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String) factoryList.get(i);
				String self = String.valueOf(map.get(factoryId));
				if("0.0".equals(self)){
					self="1";
					min="1";
				}

				Expression2 boya = new Expression2(arithmetic, new Param("a",
						scores[0].substring(2, scores[0].length())), new Param(
						"b", scores[1].substring(2, scores[1].length())),
						new Param("min", min), new Param("self", self));
				scoreMap.put(factoryId, boya.getResult());

			}
			return scoreMap;
		} else if ("坏板率".equals(indicatorName)) {
			Map<String, Float> map = calculateBoardFaule(factoryList,
					quantityMap, deviceType,startTime,endTime);
			String min = "0";
			if (map.size() > 0) {
				Map tempMap = StatisticCalculate.maxOrMin(map, "min");
				Set keySet = tempMap.keySet();
				for (Object key : keySet) {
					min =  String.valueOf(tempMap
							.get(key));
				}
			}
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String) factoryList.get(i);
				String self = String.valueOf(map.get(factoryId));
				if("0.0".equals(self)){
					self="1";
					min="1";
				}

				Expression2 boya = new Expression2(arithmetic, new Param("a",
						scores[0].substring(2, scores[0].length())), new Param(
						"b", scores[1].substring(2, scores[1].length())),
						new Param("min", min), new Param("self", self));
				scoreMap.put(factoryId, boya.getResult());

			}
			return scoreMap;
			
		} else if ("设备重大故障数".equals(indicatorName)) {
			Map<String, Integer> map = calculateBigFaultScore(factoryList,
					quantityMap, deviceType,startTime,endTime);
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String) factoryList.get(i);
				String time = map.containsKey(factoryId)?String.valueOf(map.get(factoryId)):"0";
			Expression2 boya = new Expression2(arithmetic,new Param("x",scores[0].substring(2,scores[0].length())),new Param("time",time));
			scoreMap.put(factoryId, boya.getResult());
			}
			return scoreMap;
		} else if ("设备问题数".equals(indicatorName)) {
			Map<String, Integer> map = calculateProScore(factoryList,
					quantityMap, deviceType,startTime,endTime);
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String) factoryList.get(i);
				String time = map.containsKey(factoryId)?String.valueOf(map.get(factoryId)):"0";
			Expression2 boya = new Expression2(arithmetic,new Param("x",scores[0].substring(2,scores[0].length())),new Param("time",time));
			scoreMap.put(factoryId, boya.getResult());
			}
			return scoreMap;
		} else if ("软件申请及升级问题".equals(indicatorName)) {
			AssessIndicatorService assessIndicatorService = (AssessIndicatorService) ApplicationContextHolder
			.getInstance().getBean("assessIndicatorService");
			AssessIndicator sub =  assessIndicatorService.find(id);
			//计算分数
			List list =calculateUpdateFault(factoryList,
					quantityMap, deviceType,startTime,endTime);
			Map materialMap =(Map)list.get(0);
			Map updateMap = (Map)list.get(1);
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String) factoryList.get(i);
				String timeA = updateMap.containsKey(factoryId)?String.valueOf(updateMap.get(factoryId)):"0";
				String timeB = materialMap.containsKey(factoryId)?String.valueOf(materialMap.get(factoryId)):"0";
			Expression2 boya = new Expression2(arithmetic,new Param("a",scores[0].substring(2,scores[0].length())),
			new Param("b",scores[1].substring(2, scores[1].length())),
			new Param("timeA",timeA),new Param("timeB",timeB));
			scoreMap.put(factoryId,String.valueOf((new Float(sub.getNameScor())-new Float(boya.getResult()))));
			}
			return scoreMap;
			
		} else if ("故障处理平均时长".equals(indicatorName)) {
			Map<String, Float> map = calculateFaultTalkTime(factoryList,
					quantityMap, deviceType,startTime,endTime);
			String min = "1";
			if (map.size() > 0) {
				Map tempMap = StatisticCalculate.maxOrMin(map, "min");
				Set keySet = tempMap.keySet();
				for (Object key : keySet) {
					min=String.valueOf(tempMap
							.get(key));
					if("0.0".equals(min) )
						min="1";
				}
			}
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String) factoryList.get(i);
				String self = map.get(factoryId) == 0 ? "1" : String
						.valueOf(map.get(factoryId));
				Expression2 boya = new Expression2(arithmetic, new Param("a",
						scores[0].substring(2, scores[0].length())), new Param(
						"b", scores[1].substring(2, scores[1].length())),
						new Param("min", min), new Param("self", self));
				scoreMap.put(factoryId, boya.getResult());

			}
			return scoreMap;
		}
		else if ("业务恢复处理平均时长".equals(indicatorName)) {
			Map<String, Float> map = calculateBusiTalkTime(factoryList,
					quantityMap, deviceType,startTime,endTime);
			String min = "1";
			if (map.size() > 0) {
				Map tempMap = StatisticCalculate.maxOrMin(map, "min");
				Set keySet = tempMap.keySet();
				for (Object key : keySet) {
					min=String.valueOf(tempMap
							.get(key));
					if("0.0".equals(min) )
						min="1";
				}
			}
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String) factoryList.get(i);
				String self = map.get(factoryId) == 0 ? "1" : String
						.valueOf(map.get(factoryId));
				Expression2 boya = new Expression2(arithmetic, new Param("a",
						scores[0].substring(2, scores[0].length())), new Param(
						"b", scores[1].substring(2, scores[1].length())),
						new Param("min", min), new Param("self", self));
				scoreMap.put(factoryId, boya.getResult());

			}
			return scoreMap;
		}
		else if ("板件返修平均时长".equals(indicatorName)) {
			Map<String, Float> map = calculateBoardTalkTime(factoryList,
					quantityMap, deviceType,startTime,endTime);
			String min = "1";
			if (map.size() > 0) {
				Map tempMap = StatisticCalculate.maxOrMin(map, "min");
				Set keySet = tempMap.keySet();
				for (Object key : keySet) {
					min=String.valueOf(tempMap
							.get(key));
					if("0.0".equals(min) )
						min="1";
				}
			}
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String) factoryList.get(i);
				String self = map.get(factoryId) == 0 ? "1" : String
						.valueOf(map.get(factoryId));
				Expression2 boya = new Expression2(arithmetic, new Param("a",
						scores[0].substring(2, scores[0].length())), new Param(
						"b", scores[1].substring(2, scores[1].length())),
						new Param("min", min), new Param("self", self));
				scoreMap.put(factoryId, boya.getResult());

			}
			return scoreMap;
		}
		else if ("咨询服务反馈平均时长".equals(indicatorName)) {
			Map<String, Float> map = calculateReferTalkTime(factoryList,
					quantityMap, deviceType,startTime,endTime);
			String min = "1";
			if (map.size() > 0) {
				Map tempMap = StatisticCalculate.maxOrMin(map, "min");
				Set keySet = tempMap.keySet();
				for (Object key : keySet) {
					min=String.valueOf(tempMap
							.get(key));
					if("0.0".equals(min) )
						min="1";
				}
			}
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String) factoryList.get(i);
				String self = map.get(factoryId) == 0 ? "1" : String
						.valueOf(map.get(factoryId));
				Expression2 boya = new Expression2(arithmetic, new Param("a",
						scores[0].substring(2, scores[0].length())), new Param(
						"b", scores[1].substring(2, scores[1].length())),
						new Param("min", min), new Param("self", self));
				scoreMap.put(factoryId, boya.getResult());

			}
			return scoreMap;
		}
		else if ("技术服务满意度".equals(indicatorName)) {
			Map<String, Float> map = calculateSkillServe(factoryList,
					quantityMap, deviceType,startTime,endTime);
			String max = "1";
			if (map.size() > 0) {
				Map tempMap = StatisticCalculate.maxOrMin(map, "max");
				Set keySet = tempMap.keySet();
				for (Object key : keySet) {
					max=String.valueOf(tempMap
							.get(key));
					if("0.0".equals(max) )
						max="1";
				}
			}
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String) factoryList.get(i);
				String self =  String.valueOf(map.get(factoryId));
				Expression2 boya = new Expression2(arithmetic, new Param("a",
						scores[0].substring(2, scores[0].length())), new Param(
						"b", scores[1].substring(2, scores[1].length())),
						new Param("max", max), new Param("self", self));
				scoreMap.put(factoryId, boya.getResult());

			}
			return scoreMap;
		}
		else if ("工程服务满意度".equals(indicatorName)) {
			Map<String, Float> map = calculateProjectServe(factoryList,
					quantityMap, deviceType,startTime,endTime);
			String max = "1";
			if (map.size() > 0) {
				Map tempMap = StatisticCalculate.maxOrMin(map, "max");
				Set keySet = tempMap.keySet();
				for (Object key : keySet) {
					max=String.valueOf(tempMap
							.get(key));
					if("0.0".equals(max) )
						max="1";
				}
			}
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String) factoryList.get(i);
				String self =  String.valueOf(map.get(factoryId));
				Expression2 boya = new Expression2(arithmetic, new Param("a",
						scores[0].substring(2, scores[0].length())), new Param(
						"b", scores[1].substring(2, scores[1].length())),
						new Param("max", max), new Param("self", self));
				scoreMap.put(factoryId, boya.getResult());

			}
			return scoreMap;
		}
		else if ("培训服务满意度".equals(indicatorName)) {
			Map<String, Float> map = calculateTrainServe(factoryList,
					quantityMap, deviceType,startTime,endTime);
			String max = "1";
			if (map.size() > 0) {
				Map tempMap = StatisticCalculate.maxOrMin(map, "max");
				Set keySet = tempMap.keySet();
				for (Object key : keySet) {
					max=String.valueOf(tempMap
							.get(key));
					if("0.0".equals(max) )
						max="1";
				}
			}
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String) factoryList.get(i);
				String self =  String.valueOf(map.get(factoryId));
				Expression2 boya = new Expression2(arithmetic, new Param("a",
						scores[0].substring(2, scores[0].length())), new Param(
						"b", scores[1].substring(2, scores[1].length())),
						new Param("max", max), new Param("self", self));
				scoreMap.put(factoryId, boya.getResult());

			}
			return scoreMap;
		}

		return null;
	}
	
	/**
	 * 拼接要显示的表格
	 * @param tabelList
	 * @param factoryList
	 * @param quantityMap
	 * @param deviceType
	 * @param indicatorType
	 * @return
	 */
	public List creatTableList(List<List> tabelList,List factoryList, Map<String,Integer> quantityMap,String deviceType,String indicatorType,String startTime, String endTime){
		AssessIndicatorService assessIndicatorService = (AssessIndicatorService) ApplicationContextHolder
		.getInstance().getBean("assessIndicatorService");
		AssessConfigService assessConfigService = (AssessConfigService) ApplicationContextHolder
		.getInstance().getBean("assessConfigService");
		
		
		AssessConfig assessConfig = assessConfigService.searchUnique(new Search().addFilterEqual("devicetype", deviceType));
		Map<String,Float> sumMap = new HashMap<String,Float>();
		List<AssessIndicator> assessIndicatorList = assessIndicatorService.search(new Search().addFilterEqual("configid", assessConfig.getId()).addFilterEqual("indicatorType", indicatorType));
		for (int j=0;j<assessIndicatorList.size()+1;j++) {
			if(j<assessIndicatorList.size()){
			AssessIndicator indicator =assessIndicatorList.get(j);
			List<StatisticModel>  rowList = new ArrayList<StatisticModel>();
			if(j==0){
			StatisticModel  tdModel = new StatisticModel();
			tdModel.setDisplay(indicatorType+assessIndicatorList.get(0).getTypeScore()+"%");
			tdModel.setRowspan(String.valueOf(assessIndicatorList.size()+1));
			rowList.add(tdModel);
			}
			StatisticModel  tdModelIName = new StatisticModel();
			tdModelIName.setDisplay(indicator.getIndicatorName());
			tdModelIName.setRowspan("1");
			rowList.add(tdModelIName);
			
			Map map = assessConfigService.scoreMap(factoryList, quantityMap, deviceType,indicator.getIndicatorName(),indicator.getId(),startTime,endTime);
			for (int i = 0; map!=null && map.isEmpty()==false && i < factoryList.size(); i++) {
				String factoryId = (String)factoryList.get(i);
				StatisticModel td = new StatisticModel();
				td.setDisplay((String)map.get(factoryId));
				String name = indicator.getIndicatorName();
				if(name.equals("设备重大故障数")||name.equals("设备问题数")||name.equals("软件申请及升级问题")){
					td.setUrl("/partner/deviceAssess/statistic.do?method=detailSum&type="+name+"&deviceType="+deviceType+"&startTime="+startTime+"&endTime="+endTime);
				}
				if(sumMap.containsKey(factoryId)){
				Float ff = sumMap.get(factoryId)+new Float((String)map.get(factoryId));
				sumMap.put(factoryId, ff);
				}
				else
				{
					sumMap.put(factoryId, new Float((String)map.get(factoryId)));
				}
				td.setRowspan("1");
				rowList.add(td);
			}
			tabelList.add(rowList);
			}
			else{
				StatisticModel tdModel = new StatisticModel();
			tdModel.setDisplay("合计");
			tdModel.setRowspan("1");
			List<StatisticModel>  rowList = new ArrayList<StatisticModel>();
			rowList.add(tdModel);
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String)factoryList.get(i);
				StatisticModel td = new StatisticModel();
				td.setDisplay(String.valueOf(sumMap.get(factoryId)));
				if(this.finalMap.containsKey(factoryId)){
					Float a =(Float)sumMap.get(factoryId);
					Float b = null==(Float)this.finalMap.get(factoryId)?0:(Float)this.finalMap.get(factoryId);
					this.finalMap.put(factoryId, a+b);
				}
				else{
				this.finalMap.put(factoryId, sumMap.get(factoryId));
				}
				td.setRowspan("1");
				rowList.add(td);
		}
			tabelList.add(rowList);
			}
		}
		if("服务满意度".equals(indicatorType)){
			StatisticModel tdModel = new StatisticModel();
			tdModel.setDisplay("总分");
			tdModel.setColspan("2");
			List<StatisticModel>  rowList = new ArrayList<StatisticModel>();
			rowList.add(tdModel);
			for (int i = 0; i < factoryList.size(); i++) {
				String factoryId = (String)factoryList.get(i);
				StatisticModel td = new StatisticModel();
				td.setDisplay(String.valueOf(this.finalMap.get(factoryId)));
				rowList.add(td);
		}
			tabelList.add(rowList);
			this.finalMap.clear();
		}
		
	
		return tabelList;
	}
	
	/**
	 * 全专业统计调用的接口方法
	 * @author WUchunhui
	 * @param specilId  专业的ID
	 * @param  startTime 开始时间
	 * * @param  startTime 结束时间
	 * * @param  strings  需要统计的工厂ID. 不传则统计全部.
	 */
	public Map resultMap(String specilId,String startTime,String endTime ,String...strings ){
		Map reusultMap =new HashMap();
		ITawSystemDictTypeManager manager = (ITawSystemDictTypeManager)ApplicationContextHolder.getInstance().getBean("ItawSystemDictTypeManager");
		List<TawSystemDictType> specilList = manager.getDictSonsByDictid(specilId);
		for (TawSystemDictType type : specilList) {
			List tabelList = new ArrayList();
			Map listMap = new HashMap();
			List factoryList = new ArrayList();
			if(strings!=null){
			for (int i = 0; i < strings.length; i++) {
				factoryList.add(strings);
			}
			 FacilityQuantityMgr facilityQuantityService = (FacilityQuantityMgr)ApplicationContextHolder.getInstance().getBean("facilityQuantityMgr");
			 Search quantitySearch = new Search();
			 quantitySearch.addFilterEqual("deviceType", type.getDictId());
			 quantitySearch.addFilterGreaterThan("quantity", 0);
			 Map<String,Integer> quantityMap = new HashMap<String,Integer> ();
			 List<FacilityQuantity> quantityList =facilityQuantityService.search(quantitySearch);
			for (FacilityQuantity quantity : quantityList) {
				factoryList.add(quantity.getFactory());
				quantityMap.put(quantity.getFactory(), quantity.getQuantity());
			}
			creatTableList(tabelList,factoryList,quantityMap,type.getDictId(),"设备质量",startTime,endTime);
			creatTableList(tabelList,factoryList,quantityMap,type.getDictId(),"服务质量",startTime,endTime);
			creatTableList(tabelList,factoryList,quantityMap,type.getDictId(),"服务满意度",startTime,endTime);
			List rowList =(List)tabelList.get(tabelList.size()-1);
			for (int i = 1; i <rowList.size(); i++) {
				StatisticModel model = (StatisticModel)rowList.get(i);
				Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
				String code = (String)model.getDisplay();
				Matcher isNum = pattern.matcher(code);
				if(isNum.matches()){
					listMap.put(factoryList.get(i-1),model.getDisplay());
				}
				
			}
			}
			if(listMap.size()!=0)
			reusultMap.put(type.getDictId(), listMap);
			tabelList.clear();
		} 
		return reusultMap;
	}
	

}
