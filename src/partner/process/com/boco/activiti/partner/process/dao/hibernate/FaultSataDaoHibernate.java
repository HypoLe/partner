package com.boco.activiti.partner.process.dao.hibernate;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;

import com.boco.activiti.partner.process.dao.IFaultSataDao;
import com.boco.activiti.partner.process.model.FaultStatModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.util.xml.XmlManage;
import com.boco.eoms.partner.process.util.CommonUtils;

public class FaultSataDaoHibernate extends BaseDaoHibernate implements IFaultSataDao{
	/**
	 * 城市故障统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> faultStatCityList(String startTime,
			String endTime) {
		String path = new String("/com/boco/activiti/partner/process/config/faultSata-stat-city-config.xml");
		String sql = XmlManage.getFile(path).getProperty("byCity");
		if(!"".equals(startTime)&&!"".equals(endTime)){
			sql=sql.replaceAll("@send_time@", 
						" to_char(m.send_time, 'yyyy-mm-dd') >= '"+startTime+"' and to_char(m.send_time, 'yyyy-mm-dd') <= '"+endTime+"'");
		}else{
			sql=sql.replaceAll("@send_time@", 
					" to_char(m.send_time, 'yyyy-mm')=to_char(sysdate, 'yyyy-mm') ");
		}
		System.out.println("城市故障统计报表list查询:" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> temp = query.list();
		Iterator<Object[]> iterator = temp.iterator();
		List<FaultStatModel> list = new ArrayList<FaultStatModel>();
		try {
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				FaultStatModel faultStatModel = new FaultStatModel();
				faultStatModel.setId((isNotNULL(obj[0])).toString()); //地市id
				faultStatModel.setCity((isNotNULL(obj[1])).toString()); //地市名称
				faultStatModel.setEquipmentNum((isNotNULL(obj[2])).toString()); //光缆线路设备量
				faultStatModel.setFaultSheetNum((isNotNULL(obj[3])).toString()); //报障工单数
				faultStatModel.setImputationConfirmNum((isNotNULL(obj[4])).toString()); //归集确认故障数
				faultStatModel.setFaultRate((isNotNULL(obj[5])).toString()+"%");//故障率
				faultStatModel.setAverageFaultLast((isNotNULL(obj[6])).toString());//平均故障历时
				faultStatModel.setMaleGuestsLast((isNotNULL(obj[7])).toString()); //公客平均派单时长
				faultStatModel.setFaultRebuildNum((isNotNULL(obj[8])).toString());//故障重修数
				faultStatModel.setFaultRebuildRate((isNotNULL(obj[9])).toString()+"%");//故障重修率
				faultStatModel.setMaterialMoney((isNotNULL(obj[10])).toString());//材料金额
				faultStatModel.setGuzhanglishi((isNotNULL(obj[11])).toString());//故障历时
				faultStatModel.setGkPaidanshichang((isNotNULL(obj[12])).toString());//公客派单时长
				faultStatModel.setGkGuijiquerenNum((isNotNULL(obj[13])).toString());//公客归集确认数
				faultStatModel.setChaoshigongdanshu((isNotNULL(obj[14])).toString());//超时工单数
				faultStatModel.setGuzhangxiufujishilv((isNotNULL(obj[15])).toString()+"%");//故障修复及时率
				list.add(faultStatModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sumReportCity(list);
		System.out.println("result size == " + list.size());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("size", list.size());
		map.put("faultStatCityList", list);
		return map;
	}
	 /**
	  * 故障统计合计
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	 */
	private void sumReportCity(List<FaultStatModel> list){
		int totalEquipmentNum = 0;//光缆线路设备量（百皮长公里）
		int totalFaultSheetNum = 0;//报障工单数
		int totalImputationConfirmNum = 0;//归集确认故障数
		double totalGuzhanglishi = 0.0; //故障历时
		double totalGkPaidanshichang = 0.0; //公客派单时长
		int totalGkGuijiquerenNum = 0;//公客归集确认数
		int totalFaultRebuildNum = 0; //故障重修数
		double totalMaterialMoney = 0.0;//材料金额
		
		int totalChaoshigongdanshu = 0;//超时工单数
		
		if(list != null && list.size() > 0){
			for(int i=0; i<list.size(); i++){
				FaultStatModel faultStatModel = list.get(i);
				String equipmentNum= faultStatModel.getEquipmentNum();
				if(equipmentNum != null && !"".equals(equipmentNum)){
					totalEquipmentNum += Integer.parseInt(equipmentNum);
				}
				String faultSheetNum = faultStatModel.getFaultSheetNum();
				if(faultSheetNum !=null && !"".equals(faultSheetNum)){
					totalFaultSheetNum += Integer.parseInt(faultSheetNum);
				}
				String imputationConfirmNum = faultStatModel.getImputationConfirmNum();
				if(imputationConfirmNum != null && !"".equals(imputationConfirmNum)){
					totalImputationConfirmNum += Integer.parseInt(imputationConfirmNum);
				}
				
				String guzhanglishi = faultStatModel.getGuzhanglishi();
				if(guzhanglishi != null && !"".equals(guzhanglishi)){
					totalGuzhanglishi += Double.parseDouble(guzhanglishi);
				}
				
				String gkPaidanshichang = faultStatModel.getGkPaidanshichang();
				if(gkPaidanshichang != null && !"".equals(gkPaidanshichang)){
					totalGkPaidanshichang += Double.parseDouble(gkPaidanshichang);
				}
				
				String gkGuijiquerenNum = faultStatModel.getGkGuijiquerenNum();
				if(gkGuijiquerenNum != null && !"".equals(gkGuijiquerenNum)){
					totalGkGuijiquerenNum += Integer.parseInt(gkGuijiquerenNum);
				}
				
				String faultRebuildNum = faultStatModel.getFaultRebuildNum();
				if(faultRebuildNum != null && !"".equals(faultRebuildNum)){
					totalFaultRebuildNum += Integer.parseInt(faultRebuildNum);
				}
				
				String materialMoney = faultStatModel.getMaterialMoney();
				if(materialMoney != null && !"".equals(materialMoney)){
					totalMaterialMoney += Double.parseDouble(materialMoney);
				}
				
				String chaoshigongdanshu = faultStatModel.getChaoshigongdanshu();
				if(chaoshigongdanshu != null && !"".equals(chaoshigongdanshu)){
					totalChaoshigongdanshu += Integer.parseInt(chaoshigongdanshu);
				}
			}
		}
		//合计
		FaultStatModel faultStatModel = new FaultStatModel();
		faultStatModel.setCity("2828"); //地市名称，代表合计
		faultStatModel.setEquipmentNum(Integer.toString(totalEquipmentNum));//光缆线路设备量（百皮长公里）
		faultStatModel.setFaultSheetNum(Integer.toString(totalFaultSheetNum));//报障工单数
		faultStatModel.setImputationConfirmNum(Integer.toString(totalImputationConfirmNum));//归集确认故障数
		faultStatModel.setChaoshigongdanshu(Integer.toString(totalChaoshigongdanshu));//超时工单数
		if(totalEquipmentNum != 0 && totalImputationConfirmNum != 0){//故障率：归集确认故障数/光缆线路设备量
			faultStatModel.setFaultRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalImputationConfirmNum))/Double.parseDouble(Integer.toString(totalEquipmentNum))*100, 2)+"%");
		}else{
			faultStatModel.setFaultRate("0%");
		}
		if(totalGuzhanglishi != 0.0 && totalImputationConfirmNum != 0 ){//平均故障历时:故障历时/归集确认故障数
			faultStatModel.setAverageFaultLast(Double.toString(CommonUtils.reservedDecimalPlaces(totalGuzhanglishi/totalImputationConfirmNum, 2)));
		}else{
			faultStatModel.setAverageFaultLast("0");
		}
		if(totalGkPaidanshichang != 0.0 && totalGkGuijiquerenNum != 0){//公客平均派单时长:公客派单时长/公客归集确认数
			faultStatModel.setMaleGuestsLast(Double.toString(CommonUtils.reservedDecimalPlaces(totalGkPaidanshichang/totalGkGuijiquerenNum, 2)));
		}else{
			faultStatModel.setMaleGuestsLast("0");
		}
		faultStatModel.setFaultRebuildNum(Integer.toString(totalFaultRebuildNum));//故障重修数
		if(totalFaultRebuildNum != 0 && totalFaultSheetNum != 0){//故障重修率：故障重修数/报障工单数
			faultStatModel.setFaultRebuildRate(CommonUtils.reservedDecimalPlaces(Double.parseDouble(Integer.toString(totalFaultRebuildNum))/Double.parseDouble(Integer.toString(totalFaultSheetNum))*100, 2)+"%");
		}else{
			faultStatModel.setFaultRebuildRate("0%");
		}
		
		if(totalImputationConfirmNum != 0){//故障修复及时率
			faultStatModel.setGuzhangxiufujishilv(CommonUtils.reservedDecimalPlaces((Double.parseDouble(Integer.toString(totalImputationConfirmNum)) - Double.parseDouble(Integer.toString(totalChaoshigongdanshu)))/Double.parseDouble(Integer.toString(totalImputationConfirmNum))*100, 2)+"%");
		}else{
			faultStatModel.setGuzhangxiufujishilv("0%");
		}
		
		//材料金额
		if(totalMaterialMoney != 0.0){
			faultStatModel.setMaterialMoney(Double.toString(CommonUtils.reservedDecimalPlaces(totalMaterialMoney, 2)));
		}else{
			faultStatModel.setMaterialMoney("0");
		}
		list.add(faultStatModel);
	}
	/**
	 * 区县故障统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> faultStatCountyList(String startTime,
			String endTime,String city) {
		String path = new String("/com/boco/activiti/partner/process/config/faultSata-stat-county-config.xml");
		String sql = XmlManage.getFile(path).getProperty("byCounty");
		if(!"".equals(city)){
			sql=sql.replaceAll("@parentareaid@", 
					" parentareaid = '"+city+"'");	
		}
		if(!"".equals(startTime)&&!"".equals(endTime)){
			sql=sql.replaceAll("@send_time@", 
						" to_char(m.send_time,'yyyy-mm-dd') >= '"+startTime+"' and to_char(m.send_time,'yyyy-mm-dd') <= '"+endTime+"'");
		}else{
			sql=sql.replaceAll("@send_time@", 
					" to_char(m.send_time, 'yyyy-mm')=to_char(sysdate, 'yyyy-mm') ");
		}
		System.out.println("区县故障统计报表list查询:" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> temp = query.list();
		Iterator<Object[]> iterator = temp.iterator();
		List<FaultStatModel> list = new ArrayList<FaultStatModel>();
		try {
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				FaultStatModel faultStatModel = new FaultStatModel();
				faultStatModel.setId((isNotNULL(obj[0])).toString());
				faultStatModel.setCity((isNotNULL(obj[1])).toString());
				faultStatModel.setEquipmentNum((isNotNULL(obj[2])).toString());
				faultStatModel.setFaultSheetNum((isNotNULL(obj[3])).toString());
				faultStatModel.setImputationConfirmNum((isNotNULL(obj[4])).toString());
				faultStatModel.setFaultRate((isNotNULL(obj[5])).toString());
				faultStatModel.setAverageFaultLast((isNotNULL(obj[6])).toString());
				faultStatModel.setMaleGuestsLast((isNotNULL(obj[7])).toString());
				faultStatModel.setFaultRebuildNum((isNotNULL(obj[8])).toString());
				faultStatModel.setFaultRebuildRate((isNotNULL(obj[9])).toString()+"%");
				faultStatModel.setMaterialMoney((isNotNULL(obj[10])).toString());
				faultStatModel.setGuzhanglishi((isNotNULL(obj[11])).toString());//故障历时
				faultStatModel.setGkPaidanshichang((isNotNULL(obj[12])).toString());//公客派单时长
				faultStatModel.setGkGuijiquerenNum((isNotNULL(obj[13])).toString());//公客归集确认数
				faultStatModel.setChaoshigongdanshu((isNotNULL(obj[14])).toString());//超时工单数
				faultStatModel.setGuzhangxiufujishilv((isNotNULL(obj[15])).toString()+"%");//故障修复及时率
				list.add(faultStatModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sumReportCity(list);
		System.out.println("result size == " + list.size());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("size", list.size());
		map.put("faultStatCountyList", list);
		return map;
	}
	private String isNotNULL(Object obj){
		if(obj != null){
			return obj.toString();
		}
		return "";

	}
	/**
	 * Ojbect计算 a/b 
	 * @return 00.00%
	 */
	public static String divrate(int a , int sum){
		if(sum==0)
			return "-";
		float f = Float.parseFloat(String.valueOf(a))/Float.parseFloat(String.valueOf(sum));
		DecimalFormat df = new DecimalFormat("######0.00");
		return df.format(f*100)+"%";
	}
	/**
	  * 超时工单数量查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param type
	  * @param city
	  * @return Map<String,Object>
	 */
	@Override
	public Map<String, Object> timeoutGongdanList(String type,String city,String startTime,String endTime,String cityId) {
		String path = "";
		if("1".equals(type)){
			 path = new String("/com/boco/activiti/partner/process/config/timeout-gongdan-number-config.xml");	
		}else if("2".equals(type)){
			 path = new String("/com/boco/activiti/partner/process/config/fault-reset-number-config.xml");	
		}else if("3".equals(type)){
			 path = new String("/com/boco/activiti/partner/process/config/timeout-gongdan-number-country-config.xml");	
		}else if("4".equals(type)){
			 path = new String("/com/boco/activiti/partner/process/config/fault-reset-number-country-config.xml");	
		}
		
		String sql = XmlManage.getFile(path).getProperty("byCounty");
		if(!"".equals(city)){
			sql=sql.replaceAll("@city@", 
					" city = '"+city+"'");	
		}
		if(!"".equals(city)){
			sql=sql.replaceAll("@country@", 
					" country = '"+city+"'");	
		}
		if(!"".equals(cityId)){
			sql=sql.replaceAll("@parentareaid@", 
					" parentareaid = '"+cityId+"'");	
		}
		if(!"".equals(startTime)&&!"".equals(endTime)){
			sql=sql.replaceAll("@send_time@", 
						" to_char(m.send_time,'yyyy-mm-dd') >= '"+startTime+"' and to_char(m.send_time,'yyyy-mm-dd') <= '"+endTime+"'");
		}else{
			sql=sql.replaceAll("@send_time@", 
					" to_char(m.send_time, 'yyyy-mm')=to_char(sysdate, 'yyyy-mm') ");
		}
		System.out.println("超时工单数量:" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Object[]> temp = query.list();
		Iterator<Object[]> iterator = temp.iterator();
		List<TaskModel> list = new ArrayList<TaskModel>();
		try {
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				TaskModel model = new TaskModel();
				model.setProcessInstanceId((isNotNULL(obj[0])).toString());
				System.out.println("(isNotNULL(obj[0])).toString():" + (isNotNULL(obj[0])).toString());
				model.setTheme((isNotNULL(obj[1])).toString());
				System.out.println("(isNotNULL(obj[1])).toString():" + (isNotNULL(obj[1])).toString());
				model.setCity((isNotNULL(obj[2])).toString());
				System.out.println("(isNotNULL(obj[2])).toString():" + (isNotNULL(obj[2])).toString());
				model.setCountry((isNotNULL(obj[3])).toString());
				System.out.println("(isNotNULL(obj[3])).toString():" + (isNotNULL(obj[3])).toString());
				model.setStationName((isNotNULL(obj[4])).toString());
				System.out.println("(isNotNULL(obj[4])).toString():" + (isNotNULL(obj[4])).toString());
				model.setCreateTime(format.parse((isNotNULL(obj[5])).toString()));
				System.out.println("(isNotNULL(obj[5])).toString():" + (isNotNULL(obj[5])).toString());
				model.setSendTime(format.parse((isNotNULL(obj[6])).toString()));
				System.out.println("(isNotNULL(obj[6])).toString():" + (isNotNULL(obj[6])).toString());
				model.setTempTask((isNotNULL(obj[7])).toString());
				System.out.println("(isNotNULL(obj[7])).toString():" + (isNotNULL(obj[7])).toString());
				model.setExecutor((isNotNULL(obj[8])).toString());
				System.out.println("(isNotNULL(obj[8])).toString():" + (isNotNULL(obj[8])).toString());
				model.setSheetId((isNotNULL(obj[9])).toString());
				System.out.println("(isNotNULL(obj[9])).toString():" + (isNotNULL(obj[9])).toString());
				model.setTaskDefKeyName((isNotNULL(obj[10])).toString());
				System.out.println("(isNotNULL(obj[10])).toString():" + (isNotNULL(obj[10])).toString());
				model.setTaskDefKey((isNotNULL(obj[11])).toString());
				System.out.println("(isNotNULL(obj[11])).toString():" + (isNotNULL(obj[11])).toString());
				System.out.println("(isNotNULL(obj[12])).toString():" + (isNotNULL(obj[12])).toString());
				if(!"".equals(obj[12])&&obj[12]!=null){
				model.setLastReplyTime(format.parse((isNotNULL(obj[12])).toString()));//故障历时
				}
				if("2".equals(type)){
					model.setBarrierNumber((isNotNULL(obj[13])).toString());
					System.out.println("(isNotNULL(obj[13])).toString():" + (isNotNULL(obj[13])).toString());	
				}
				
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("result size == " + list.size());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("size", list.size());
		map.put("taskList", list);
		return map;
	}
}
