package com.boco.activiti.partner.process.dao.hibernate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import org.hibernate.SQLQuery;
import org.apache.commons.lang.StringUtils;

import com.boco.activiti.partner.process.dao.IRepairFaultDao;
import com.boco.activiti.partner.process.model.FaultType;
import com.boco.activiti.partner.process.model.NonFaultType;
import com.boco.activiti.partner.process.model.SchemeRateRejectModel;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.util.xml.XmlManage;
import com.boco.eoms.partner.process.util.CommonUtils;

public class RepairFaultDaoHibernate extends BaseDaoHibernate implements IRepairFaultDao{
	/**
	 * 地市故障类型统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	@Override
	public Map<String,Object> faultTypeListPage(String startTime,String endTime) {
		String path = new String("/com/boco/activiti/partner/process/config/repairFault-stat-config.xml");
		String sql = XmlManage.getFile(path).getProperty("byArea");
		if(!"".equals(startTime)&&!"".equals(endTime)){
			sql=sql.replaceAll("@send_time@", 
						" to_char(m.send_time,'yyyy-mm-dd') >= '"+startTime+"' and to_char(m.send_time,'yyyy-mm-dd') <= '"+endTime+"'");
		}else{
			sql=sql.replaceAll("@send_time@", 
					" to_char(m.send_time, 'yyyy-mm')=to_char(sysdate, 'yyyy-mm') ");
		}
		System.out.println("地市故障类型统计list查询:" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> temp = query.list();
		Iterator<Object[]> iterator = temp.iterator();
		List<FaultType> list = new ArrayList<FaultType>();
		try {
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				FaultType faultType = new FaultType();
				faultType.setId((isNotNULL(obj[0])).toString());
				faultType.setCity((isNotNULL(obj[1])).toString());
				faultType.setTotalFaultNum((isNotNULL(obj[2])).toString()); //线路故障总数
				faultType.setTotalFaultAmout((isNotNULL(obj[3])).toString()); //线路故障金额
				faultType.setCheguaNum((isNotNULL(obj[4])).toString()); //车挂数量
				faultType.setCheguaRate((isNotNULL(obj[5])).toString()+"%"); //车挂占比
				faultType.setWailiNum((isNotNULL(obj[6])).toString());//外力数量
				faultType.setWailiRate((isNotNULL(obj[7])).toString()+"%");//外力占比
				faultType.setHuoshaoNum((isNotNULL(obj[8])).toString());//火烧数量
				faultType.setHuoshaoRate((isNotNULL(obj[9])).toString()+"%");//火烧占比
				faultType.setRenweiNum((isNotNULL(obj[10])).toString());//被盗或人为破坏数量
				faultType.setRenweiRate((isNotNULL(obj[11])).toString()+"%");//被盗或人为破坏占比
				
				faultType.setDuanxianNum((isNotNULL(obj[12])).toString());//自然断纤（纤芯裂化）数量
				faultType.setDuanxianRate((isNotNULL(obj[13])).toString()+"%");//自然断纤（纤芯裂化）占比
				faultType.setJinshuiNum((isNotNULL(obj[14])).toString());//接头盒进水数量
				faultType.setJinshuiRate((isNotNULL(obj[15])).toString()+"%");//接头盒进水占比
				faultType.setWeixianNum((isNotNULL(obj[16])).toString());//尾纤及法兰盘数量
				faultType.setWeixianRate((isNotNULL(obj[17])).toString()+"%");//尾纤及法兰盘占比
				faultType.setNiaozhuoNum((isNotNULL(obj[18])).toString());//鸟啄数量
				faultType.setNiaozhuoRate((isNotNULL(obj[19])).toString()+"%");//鸟啄占比
				faultType.setShuyaoNum((isNotNULL(obj[20])).toString());//鼠咬数量
				faultType.setShuyaoRate((isNotNULL(obj[21])).toString()+"%");//鼠咬占比
				
				faultType.setZaihaiNum((isNotNULL(obj[22])).toString());//自然灾害数量
				faultType.setZaihaiRate((isNotNULL(obj[23])).toString()+"%");//自然灾害占比
				faultType.setOtherNum((isNotNULL(obj[24])).toString());//其他数量
				faultType.setOtherRate((isNotNULL(obj[25])).toString()+"%");//其他占比
				list.add(faultType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sumReport(list);
		System.out.println("result size == " + list.size());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("size", list.size());
		map.put("repairFaultList", list);
		return map;
	}
	private String isNotNULL(Object obj){
		if(obj != null){
			return obj.toString();
		}
		return "";

	}
	private void sumReport(List<FaultType> dataList){
		int totalFaultNum = 0; //线路故障总数
		double totalFaultAmout = 0.0;//线路故障金额
		int totalCheguaNum = 0;//车挂数量 
		int totalWailiNum = 0;//外力数量
		int totalHuoshaoNum = 0;//火烧数量
		int totalRenweiNum = 0;//被盗或人为破坏数量
		
		int totalDuanxianNum = 0;//自然断纤（纤芯裂化）数量
		int totalJinshuiNum = 0;//接头盒进水数量
		int totalWeixianNum = 0;//尾纤及法兰盘数量
		int totalNiaozhuoNum = 0;//鸟啄数量
		int totalShuyaoNum = 0;//鼠咬数量
		int totalZaihaiNum = 0;//自然灾害数量
		int totalOtherNum = 0;//其他数量
		
		for(int i=0; i<dataList.size(); i++){
			FaultType faultType=dataList.get(i);
			String faultNum = faultType.getTotalFaultNum();
			if(faultNum != null && !"".equals(faultNum)){
				totalFaultNum += Integer.parseInt(faultNum);
			}
			
			String faultAmout = faultType.getTotalFaultAmout();
			if(faultAmout != null && !"".equals(faultAmout)){
				totalFaultAmout += Double.parseDouble(faultAmout);
			}
			
				
			String cheguaNum = faultType.getCheguaNum();
			if(cheguaNum != null && !"".equals(cheguaNum)){
				totalCheguaNum += Integer.parseInt(cheguaNum);
			}
			
			String wailiNum = faultType.getWailiNum();
			if(wailiNum != null && !"".equals(wailiNum)){
				totalWailiNum += Integer.parseInt(wailiNum);
			}
			
			String huoshaoNum = faultType.getHuoshaoNum();
			if(huoshaoNum != null && !"".equals(huoshaoNum)){
				totalHuoshaoNum += Integer.parseInt(huoshaoNum);
			}
			
			String renweiNum = faultType.getRenweiNum();
			if(renweiNum != null && !"".equals(renweiNum)){
				totalRenweiNum += Integer.parseInt(renweiNum);
			}

			String duanxianNum = faultType.getDuanxianNum();
			if(duanxianNum != null && !"".equals(duanxianNum)){
				totalDuanxianNum += Integer.parseInt(duanxianNum);
			}
			
			String jinshuiNum = faultType.getJinshuiNum();
			if(jinshuiNum != null && !"".equals(jinshuiNum)){
				totalJinshuiNum += Integer.parseInt(jinshuiNum);
			}
			
			String weixianNum = faultType.getWeixianNum();
			if(weixianNum != null && !"".equals(weixianNum)){
				totalWeixianNum += Integer.parseInt(weixianNum);
			}
			
			String niaozhuoNum = faultType.getNiaozhuoNum();
			if(niaozhuoNum != null && !"".equals(niaozhuoNum)){
				totalNiaozhuoNum += Integer.parseInt(niaozhuoNum);
			}
			
			String shuyaoNum = faultType.getShuyaoNum();
			if(shuyaoNum != null && !"".equals(shuyaoNum)){
				totalShuyaoNum += Integer.parseInt(shuyaoNum);
			}
			
			String zaihaiNum = faultType.getZaihaiNum();
			if(zaihaiNum != null && !"".equals(zaihaiNum)){
				totalZaihaiNum += Integer.parseInt(zaihaiNum);
			}
			
			String otherNum = faultType.getOtherNum();
			if(otherNum != null && !"".equals(otherNum)){
				totalOtherNum += Integer.parseInt(otherNum);
			}
		}
		
		//合计
		FaultType faultType = new FaultType();
		faultType.setCity("合计");
		faultType.setTotalFaultNum(Integer.toString(totalFaultNum));//线路故障总数
		if(totalFaultAmout != 0.0){
			faultType.setTotalFaultAmout(Double.toString(CommonUtils.reservedDecimalPlaces(totalFaultAmout, 2)));//线路故障金额
		}else{
			faultType.setTotalFaultAmout("0");
		}
		faultType.setCheguaNum(Integer.toString(totalCheguaNum));//车挂数量
		if(totalCheguaNum != 0 && totalFaultNum != 0){//车挂占比
			faultType.setCheguaRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalCheguaNum))/Double.parseDouble(Integer.toString(totalFaultNum))*100, 2)+"%");
		}else{
			faultType.setCheguaRate("0%");
		}
		faultType.setWailiNum(Integer.toString(totalWailiNum));//外力数量
		if(totalWailiNum != 0 && totalFaultNum != 0){//外力占比
			faultType.setWailiRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalWailiNum))/Double.parseDouble(Integer.toString(totalFaultNum))*100, 2)+"%");
		}else{
			faultType.setWailiRate("0%");
		}
		faultType.setHuoshaoNum(Integer.toString(totalHuoshaoNum));//火烧数量
		if(totalHuoshaoNum != 0 && totalFaultNum != 0){//火烧占比
			faultType.setHuoshaoRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalHuoshaoNum))/Double.parseDouble(Integer.toString(totalFaultNum))*100, 2)+"%");
		}else{
			faultType.setHuoshaoRate("0%");
		}
		faultType.setRenweiNum(Integer.toString(totalRenweiNum));////被盗或人为破坏数量
		if(totalRenweiNum != 0 && totalFaultNum != 0){//被盗或人为破坏占比
			faultType.setRenweiRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalRenweiNum))/Double.parseDouble(Integer.toString(totalFaultNum))*100, 2)+"%");
		}else{
			faultType.setRenweiRate("0%");
		}
		
		faultType.setDuanxianNum(Integer.toString(totalDuanxianNum));
		if(totalDuanxianNum != 0 && totalFaultNum != 0){
			faultType.setDuanxianRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalDuanxianNum))/Double.parseDouble(Integer.toString(totalFaultNum))*100, 2)+"%");
		}else{
			faultType.setDuanxianRate("0%");
		}
		
		faultType.setJinshuiNum(Integer.toString(totalJinshuiNum));
		if(totalJinshuiNum != 0 && totalFaultNum != 0){
			faultType.setJinshuiRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalJinshuiNum))/Double.parseDouble(Integer.toString(totalFaultNum))*100, 2)+"%");
		}else{
			faultType.setJinshuiRate("0%");
		}
		
		faultType.setWeixianNum(Integer.toString(totalWeixianNum));
		if(totalWeixianNum != 0 && totalFaultNum != 0){
			faultType.setWeixianRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalWeixianNum))/Double.parseDouble(Integer.toString(totalFaultNum))*100, 2)+"%");
		}else{
			faultType.setWeixianRate("0%");
		}
		
		faultType.setNiaozhuoNum(Integer.toString(totalNiaozhuoNum));
		if(totalNiaozhuoNum != 0 && totalFaultNum != 0){
			faultType.setNiaozhuoRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalNiaozhuoNum))/Double.parseDouble(Integer.toString(totalFaultNum))*100, 2)+"%");
		}else{
			faultType.setNiaozhuoRate("0%");
		}
		
		faultType.setShuyaoNum(Integer.toString(totalShuyaoNum));
		if(totalShuyaoNum != 0 && totalFaultNum != 0){
			faultType.setShuyaoRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalShuyaoNum))/Double.parseDouble(Integer.toString(totalFaultNum))*100, 2)+"%");
		}else{
			faultType.setShuyaoRate("0%");
		}
		
		faultType.setZaihaiNum(Integer.toString(totalZaihaiNum));
		if(totalZaihaiNum != 0 && totalFaultNum != 0){
			faultType.setZaihaiRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalZaihaiNum))/Double.parseDouble(Integer.toString(totalFaultNum))*100, 2)+"%");
		}else{
			faultType.setZaihaiRate("0%");
		}
		
		faultType.setOtherNum(Integer.toString(totalOtherNum));
		if(totalOtherNum != 0 && totalFaultNum != 0){
			faultType.setOtherRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalOtherNum))/Double.parseDouble(Integer.toString(totalFaultNum))*100, 2)+"%");
		}else{
			faultType.setOtherRate("0%");
		}
		dataList.add(faultType);
	}

	/**
	 * 区县故障类型统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	@Override
	public  Map<String,Object> repairFaultqxList(String county,String startTime,String endTime) {
		String path = new String("/com/boco/activiti/partner/process/config/repairFaultqx-stat-config.xml");
		String sql = XmlManage.getFile(path).getProperty("byArea");
		if(!"".equals(county)){
			sql=sql.replaceAll("@parentareaid@", 
					" parentareaid = '"+county+"'");	
		}
		if(!"".equals(startTime)&&!"".equals(endTime)){
			sql=sql.replaceAll("@send_time@", 
						" to_char(m.send_time,'yyyy-mm-dd') >= '"+startTime+"' and to_char(m.send_time,'yyyy-mm-dd') <= '"+endTime+"'");
		}else{
			sql=sql.replaceAll("@send_time@", 
					" to_char(m.send_time, 'yyyy-mm')=to_char(sysdate, 'yyyy-mm') ");
		}
		System.out.println("区县故障类型统计list查询:" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> temp = query.list();
		Iterator<Object[]> iterator = temp.iterator();
		List<FaultType> list = new ArrayList<FaultType>();
		try {
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				FaultType faultType = new FaultType();
				faultType.setId((isNotNULL(obj[0])).toString());
				faultType.setCity((isNotNULL(obj[1])).toString());
				faultType.setTotalFaultNum((isNotNULL(obj[2])).toString()); //线路故障总数
				faultType.setTotalFaultAmout((isNotNULL(obj[3])).toString()); //线路故障金额
				faultType.setCheguaNum((isNotNULL(obj[4])).toString()); //车挂数量
				faultType.setCheguaRate((isNotNULL(obj[5])).toString()+"%"); //车挂占比
				faultType.setWailiNum((isNotNULL(obj[6])).toString());//外力数量
				faultType.setWailiRate((isNotNULL(obj[7])).toString()+"%");//外力占比
				faultType.setHuoshaoNum((isNotNULL(obj[8])).toString());//火烧数量
				faultType.setHuoshaoRate((isNotNULL(obj[9])).toString()+"%");//火烧占比
				faultType.setRenweiNum((isNotNULL(obj[10])).toString());//被盗或人为破坏数量
				faultType.setRenweiRate((isNotNULL(obj[11])).toString()+"%");//被盗或人为破坏占比
				
				faultType.setDuanxianNum((isNotNULL(obj[12])).toString());//自然断纤（纤芯裂化）数量
				faultType.setDuanxianRate((isNotNULL(obj[13])).toString()+"%");//自然断纤（纤芯裂化）占比
				faultType.setJinshuiNum((isNotNULL(obj[14])).toString());//接头盒进水数量
				faultType.setJinshuiRate((isNotNULL(obj[15])).toString()+"%");//接头盒进水占比
				faultType.setWeixianNum((isNotNULL(obj[16])).toString());//尾纤及法兰盘数量
				faultType.setWeixianRate((isNotNULL(obj[17])).toString()+"%");//尾纤及法兰盘占比
				faultType.setNiaozhuoNum((isNotNULL(obj[18])).toString());//鸟啄数量
				faultType.setNiaozhuoRate((isNotNULL(obj[19])).toString()+"%");//鸟啄占比
				faultType.setShuyaoNum((isNotNULL(obj[20])).toString());//鼠咬数量
				faultType.setShuyaoRate((isNotNULL(obj[21])).toString()+"%");//鼠咬占比
				
				faultType.setZaihaiNum((isNotNULL(obj[22])).toString());//自然灾害数量
				faultType.setZaihaiRate((isNotNULL(obj[23])).toString()+"%");//自然灾害占比
				faultType.setOtherNum((isNotNULL(obj[24])).toString());//其他数量
				faultType.setOtherRate((isNotNULL(obj[25])).toString()+"%");//其他占比
				list.add(faultType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sumReport(list);
		System.out.println("result size == " + list.size());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("size", list.size());
		map.put("repairFaultqxList", list);
		return map;
	}
	/**
	 * 城市非故障统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	@Override
	public  Map<String,Object> nonFaultcsList(String startTime,String endTime) {
		String path = new String("/com/boco/activiti/partner/process/config/nonFaultcsList-stat-config.xml");
		String sql = XmlManage.getFile(path).getProperty("byArea");
		if(!"".equals(startTime)&&!"".equals(endTime)){
			sql=sql.replaceAll("@send_time@", 
						" to_char(m.send_time,'yyyy-mm-dd') >= '"+startTime+"' and to_char(m.send_time,'yyyy-mm-dd') <= '"+endTime+"'");
		}else{
			sql=sql.replaceAll("@send_time@", 
					" to_char(m.send_time, 'yyyy-mm')=to_char(sysdate, 'yyyy-mm') ");
		}
		System.out.println("城市非故障统计list查询:" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> temp = query.list();
		Iterator<Object[]> iterator = temp.iterator();
		List<NonFaultType> list = new ArrayList<NonFaultType>();
		try {
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				NonFaultType nonFaultType = new NonFaultType();
				nonFaultType.setId((isNotNULL(obj[0])).toString());
				nonFaultType.setCity((isNotNULL(obj[1])).toString());
				nonFaultType.setNonFaultNum((isNotNULL(obj[2])).toString());// 非故障触发工单数
				nonFaultType.setNonFaultAmout((isNotNULL(obj[3])).toString());//非故障触发材料金额
				nonFaultType.setGenghuandianganNum((isNotNULL(obj[4])).toString());//更换电杆数量
				nonFaultType.setGenghuandianganRate((isNotNULL(obj[5])).toString()+"%");//更换电杆占比
				nonFaultType.setGenghuandiaoxianNum((isNotNULL(obj[6])).toString());//更换吊线数量
				nonFaultType.setGenghuandiaoxianRate((isNotNULL(obj[7])).toString()+"%");//更换吊线占比
				nonFaultType.setGenghuanlaxianNum((isNotNULL(obj[8])).toString());//更换拉线数量
				nonFaultType.setGenghuanlaxianRate((isNotNULL(obj[9])).toString()+"%");//更换拉线占比
				nonFaultType.setGuanglantuoluoNum((isNotNULL(obj[10])).toString());//光缆脱落整治数量
				nonFaultType.setGuanglantuoluoRate((isNotNULL(obj[11])).toString()+"%");//光缆脱落整治占比
				nonFaultType.setDianlixianNum((isNotNULL(obj[12])).toString());//电力线防护数量
				nonFaultType.setDianlixianRate((isNotNULL(obj[13])).toString()+"%");//电力线防护占比
				nonFaultType.setGuangjiaoxiangNum((isNotNULL(obj[14])).toString());//光交箱整治数量
				nonFaultType.setGuangjiaoxiangRate((isNotNULL(obj[15])).toString()+"%");//光交箱整治占比
				nonFaultType.setRenjinggaiNum((isNotNULL(obj[16])).toString());//人井盖增补数量
				nonFaultType.setRenjinggaiRate((isNotNULL(obj[17])).toString()+"%");//人井盖增补占比
				nonFaultType.setOtherNum((isNotNULL(obj[18])).toString());//其他安全隐患处理数量
				nonFaultType.setOtherRate((isNotNULL(obj[19])).toString()+"%");//其他安全隐患处理占比
				list.add(nonFaultType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sumTotal(list);
		System.out.println("result size == " + list.size());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("size", list.size());
		map.put("nonFaultcsList", list);
		return map;
	}
	/**
	 * 区县非故障统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	@Override
	public  Map<String,Object> nonFaultqxList(String county,String startTime,String endTime) {
		String path = new String("/com/boco/activiti/partner/process/config/nonFaultqxList-stat-config.xml");
		String sql = XmlManage.getFile(path).getProperty("byArea");
		if(!"".equals(county)){
			sql=sql.replaceAll("@parentareaid@", 
					" parentareaid = '"+county+"'");	
		}
		if(!"".equals(startTime)&&!"".equals(endTime)){
			sql=sql.replaceAll("@send_time@", 
						" to_char(m.send_time,'yyyy-mm-dd') >= '"+startTime+"' and to_char(m.send_time,'yyyy-mm-dd') <= '"+endTime+"'");
		}else{
			sql=sql.replaceAll("@send_time@", 
					" to_char(m.send_time, 'yyyy-mm')=to_char(sysdate, 'yyyy-mm') ");
		}
		System.out.println("区县非故障统计list查询:" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> temp = query.list();
		Iterator<Object[]> iterator = temp.iterator();
		List<NonFaultType> list = new ArrayList<NonFaultType>();
		try {
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				NonFaultType nonFaultType = new NonFaultType();
				nonFaultType.setId((isNotNULL(obj[0])).toString());
				nonFaultType.setCity((isNotNULL(obj[1])).toString());
				nonFaultType.setNonFaultNum((isNotNULL(obj[2])).toString());// 非故障触发工单数
				nonFaultType.setNonFaultAmout((isNotNULL(obj[3])).toString());//非故障触发材料金额
				nonFaultType.setGenghuandianganNum((isNotNULL(obj[4])).toString());//更换电杆数量
				nonFaultType.setGenghuandianganRate((isNotNULL(obj[5])).toString()+"%");//更换电杆占比
				nonFaultType.setGenghuandiaoxianNum((isNotNULL(obj[6])).toString());//更换吊线数量
				nonFaultType.setGenghuandiaoxianRate((isNotNULL(obj[7])).toString()+"%");//更换吊线占比
				nonFaultType.setGenghuanlaxianNum((isNotNULL(obj[8])).toString());//更换拉线数量
				nonFaultType.setGenghuanlaxianRate((isNotNULL(obj[9])).toString()+"%");//更换拉线占比
				nonFaultType.setGuanglantuoluoNum((isNotNULL(obj[10])).toString());//光缆脱落整治数量
				nonFaultType.setGuanglantuoluoRate((isNotNULL(obj[11])).toString()+"%");//光缆脱落整治占比
				nonFaultType.setDianlixianNum((isNotNULL(obj[12])).toString());//电力线防护数量
				nonFaultType.setDianlixianRate((isNotNULL(obj[13])).toString()+"%");//电力线防护占比
				nonFaultType.setGuangjiaoxiangNum((isNotNULL(obj[14])).toString());//光交箱整治数量
				nonFaultType.setGuangjiaoxiangRate((isNotNULL(obj[15])).toString()+"%");//光交箱整治占比
				nonFaultType.setRenjinggaiNum((isNotNULL(obj[16])).toString());//人井盖增补数量
				nonFaultType.setRenjinggaiRate((isNotNULL(obj[17])).toString()+"%");//人井盖增补占比
				nonFaultType.setOtherNum((isNotNULL(obj[18])).toString());//其他安全隐患处理数量
				nonFaultType.setOtherRate((isNotNULL(obj[19])).toString()+"%");//其他安全隐患处理占比
				
				list.add(nonFaultType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sumTotal(list);
		System.out.println("result size == " + list.size());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("size", list.size());
		map.put("nonFaultqxList", list);
		return map;
	}
	private void sumTotal(List<NonFaultType> dataList){
		int totalNonFaultNum = 0;//非故障触发工单数
		double totalNonFaultAmout = 0.0;//非故障触发材料金额
		int totalGenghuandianganNum = 0;//更换电杆数量
		int totalGenghuandiaoxianNum = 0;//更换吊线数量
		int totalGenghuanlaxianNum = 0;//更换拉线数量
		int totalGuanglantuoluoNum = 0;//光缆脱落整治数量
		int totalDianlixianNum = 0;//电力线防护数量
		int totalGuangjiaoxiangNum = 0;//光交箱整治数量
		int totalRenjinggaiNum = 0;//人井盖增补数量
		int totalOtherNum = 0;//其他安全隐患处理数量
		for(int i=0; i<dataList.size(); i++){
			NonFaultType nonFaultType=dataList.get(i);
			String nonFaultNum = nonFaultType.getNonFaultNum();
			if(nonFaultNum != null && !"".equals(nonFaultNum)){
				totalNonFaultNum += Integer.parseInt(nonFaultNum);
			}
			
			String nonFaultAmout = nonFaultType.getNonFaultAmout();
			if(nonFaultAmout != null && !"".equals(nonFaultAmout)){
				totalNonFaultAmout += Double.parseDouble(nonFaultAmout);
			}
			
			String genghuandianganNum = nonFaultType.getGenghuandianganNum();
			if(genghuandianganNum != null && !"".equals(genghuandianganNum)){
				totalGenghuandianganNum += Integer.parseInt(genghuandianganNum);
			}
			
			String genghuandiaoxianNum = nonFaultType.getGenghuandiaoxianNum();
			if(genghuandiaoxianNum != null && !"".equals(genghuandiaoxianNum)){
				totalGenghuandiaoxianNum += Integer.parseInt(genghuandiaoxianNum);
			}
			
			String genghuanlaxianNum = nonFaultType.getGenghuanlaxianNum();
			if(genghuanlaxianNum != null && !"".equals(genghuanlaxianNum)){
				totalGenghuanlaxianNum += Integer.parseInt(genghuanlaxianNum);
			}
			
			String guanglantuoluoNum = nonFaultType.getGuanglantuoluoNum();
			if(guanglantuoluoNum != null && !"".equals(guanglantuoluoNum)){
				totalGuanglantuoluoNum += Integer.parseInt(guanglantuoluoNum);
			}
			
			String dianlixianNum = nonFaultType.getDianlixianNum();
			if(dianlixianNum != null && !"".equals(dianlixianNum)){
				totalDianlixianNum += Integer.parseInt(dianlixianNum);
			}
			
			String guangjiaoxiangNum = nonFaultType.getGuangjiaoxiangNum();
			if(guangjiaoxiangNum != null && !"".equals(guangjiaoxiangNum)){
				totalGuangjiaoxiangNum += Integer.parseInt(guangjiaoxiangNum);
			}
			
			String renjinggaiNum = nonFaultType.getRenjinggaiNum();
			if(renjinggaiNum != null && !"".equals(renjinggaiNum)){
				totalRenjinggaiNum += Integer.parseInt(renjinggaiNum);
			}
			
			String otherNum = nonFaultType.getOtherNum();
			if(otherNum != null && !"".equals(otherNum)){
				totalOtherNum += Integer.parseInt(otherNum);
			}
		}
		
		//合计
		NonFaultType nonFaultType=new NonFaultType();
		nonFaultType.setCity("合计");
		nonFaultType.setNonFaultNum(Integer.toString(totalNonFaultNum));// 非故障触发工单数
		if(totalNonFaultAmout != 0.0){
			nonFaultType.setNonFaultAmout(Double.toString(CommonUtils.reservedDecimalPlaces(totalNonFaultAmout, 2)));//非故障触发材料金额
		}else{
			nonFaultType.setNonFaultAmout("0");//非故障触发材料金额
		}
		nonFaultType.setGenghuandianganNum(Integer.toString(totalGenghuandianganNum));//更换电杆数量
		if(totalGenghuandianganNum != 0 && totalNonFaultNum != 0){
			nonFaultType.setGenghuandianganRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalGenghuandianganNum))/Double.parseDouble(Integer.toString(totalNonFaultNum))*100, 2)+"%");//更换电杆占比
		}else{
			nonFaultType.setGenghuandianganRate("0%");
		}
		nonFaultType.setGenghuandiaoxianNum(Integer.toString(totalGenghuandiaoxianNum));//更换吊线数量
		if(totalGenghuandiaoxianNum != 0 && totalNonFaultNum != 0){
			nonFaultType.setGenghuandiaoxianRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalGenghuandiaoxianNum))/Double.parseDouble(Integer.toString(totalNonFaultNum))*100, 2)+"%");//更换吊线占比
		}else{
			nonFaultType.setGenghuandiaoxianRate("0%");//更换吊线占比
		}
		nonFaultType.setGenghuanlaxianNum(Integer.toString(totalGenghuanlaxianNum));//更换拉线数量
		if(totalGenghuanlaxianNum != 0 && totalNonFaultNum != 0){
			nonFaultType.setGenghuanlaxianRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalGenghuanlaxianNum))/Double.parseDouble(Integer.toString(totalNonFaultNum))*100, 2)+"%");//更换拉线占比
		}else{
			nonFaultType.setGenghuanlaxianRate("0%");
		}
		nonFaultType.setGuanglantuoluoNum(Integer.toString(totalGuanglantuoluoNum));//光缆脱落整治数量
		if(totalGuanglantuoluoNum != 0 && totalNonFaultNum != 0){
			nonFaultType.setGuanglantuoluoRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalGuanglantuoluoNum))/Double.parseDouble(Integer.toString(totalNonFaultNum))*100, 2)+"%");//光缆脱落整治占比
		}else{
			nonFaultType.setGuanglantuoluoRate("0%");//光缆脱落整治占比
		}
		nonFaultType.setDianlixianNum(Integer.toString(totalDianlixianNum));//电力线防护数量
		if(totalDianlixianNum != 0 && totalNonFaultNum != 0){
			nonFaultType.setDianlixianRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalDianlixianNum))/Double.parseDouble(Integer.toString(totalNonFaultNum))*100, 2)+"%");//电力线防护占比
		}else{
			nonFaultType.setDianlixianRate("0%");//电力线防护占比
		}
		nonFaultType.setGuangjiaoxiangNum(Integer.toString(totalGuangjiaoxiangNum));//光交箱整治数量
		if(totalGuangjiaoxiangNum != 0 && totalNonFaultNum != 0){
			nonFaultType.setGuangjiaoxiangRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalGuangjiaoxiangNum))/Double.parseDouble(Integer.toString(totalNonFaultNum))*100, 2)+"%");//光交箱整治占比
		}else{
			nonFaultType.setGuangjiaoxiangRate("0%");//光交箱整治占比
		}
		nonFaultType.setRenjinggaiNum(Integer.toString(totalRenjinggaiNum));//人井盖增补数量
		if(totalRenjinggaiNum != 0 && totalNonFaultNum != 0){
			nonFaultType.setRenjinggaiRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalRenjinggaiNum))/Double.parseDouble(Integer.toString(totalNonFaultNum))*100, 2)+"%");//人井盖增补占比
		}else{
			nonFaultType.setRenjinggaiRate("0%");//人井盖增补占比
		}
		nonFaultType.setOtherNum(Integer.toString(totalOtherNum));//其他安全隐患处理数量
		if(totalOtherNum != 0 && totalNonFaultNum != 0){
			nonFaultType.setOtherRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalOtherNum))/Double.parseDouble(Integer.toString(totalNonFaultNum))*100, 2)+"%");//其他安全隐患处理占比
		}else{
			nonFaultType.setOtherRate("0%");//其他安全隐患处理占比
		}
		dataList.add(nonFaultType);
	}
}
