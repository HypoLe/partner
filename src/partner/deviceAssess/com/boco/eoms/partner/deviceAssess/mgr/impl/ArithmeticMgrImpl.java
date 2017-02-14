package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.util.Date;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.deviceAssess.dao.ArithmeticDao;
import com.boco.eoms.partner.deviceAssess.mgr.ArithmeticMgr;
import com.boco.eoms.partner.deviceAssess.model.Counsel;
import com.boco.eoms.partner.deviceAssess.model.FacilityNum;
import com.boco.eoms.partner.deviceAssess.model.FactoryDispose;
import com.boco.eoms.partner.deviceAssess.model.Ftraininfo;
import com.boco.eoms.partner.deviceAssess.model.Lserveinfo;
import com.boco.eoms.partner.deviceAssess.model.Peventinfo;
import com.boco.eoms.partner.deviceAssess.model.Repairinfo;
import com.boco.eoms.partner.deviceAssess.model.Softupinfo;
import com.boco.eoms.partner.deviceAssess.util.FacilityNumConstants;

/**
 * <p>
 * Title:后评估指标体系
 * </p> 
 * <p>
 * Description:后评估指标体系
 * </p>
 * <p>
 * Thu Oct 14 10:55:23 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class ArithmeticMgrImpl implements ArithmeticMgr {
 
	private ArithmeticDao  arithmeticDao;
	
	/**
	 * ∑(内部处理)故障事件信息(次)
	 */
	public List getInsideDisposes(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		return arithmeticDao.getInsideDisposes(timeEnd, timeStart, factory, speciality, type);
	}
	/**
	 * 设备故障率
	 */	
	public double getFixingFaultRate(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1 = 1 ");
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			whereStr.append(" and  pigeonholeTime>'"); 
			whereStr.append(StaticMethod.date2String(timeStart));
			whereStr.append("' and pigeonholeTime<'" );
			whereStr.append(StaticMethod.date2String(timeEnd));
			whereStr.append("' ");			
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr.append(" and  pigeonholeTime>to_date('"); 
			whereStr.append(timeStart);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') and pigeonholeTime<to_date('" );
			whereStr.append(timeEnd);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') ");
		} 		
		whereStr.append(" and factory = '");
		whereStr.append(factory);
		whereStr.append("' ");
		if(!"".equals(speciality)){
			whereStr.append(" and speciality = '");
			whereStr.append(speciality);
			whereStr.append("' ");
		}
		if(!"".equals(type)){
			whereStr.append(" and equipmentType = '");
			whereStr.append(type);
			whereStr.append("' ");
		}
		List insideDisposesList = arithmeticDao.getInsideDisposesList(whereStr.toString());
		List factoryDisposesList = arithmeticDao.getFactoryDisposesList(whereStr.toString());
		
		int sum = insideDisposesList.size() + factoryDisposesList.size();
		StringBuffer whereStrBE = new StringBuffer();
		whereStrBE.append(" where factory = '");
		whereStrBE.append(factory);
		whereStrBE.append("' ");		
		List facilityNumsList = arithmeticDao.getFacilityNumsList(whereStrBE.toString());
		int facilityNumber = 0;
		if(facilityNumsList.size()>0){
			FacilityNum backEstimate = (FacilityNum)facilityNumsList.get(0);
			if("".equals(type)){
				facilityNumber = FacilityNumConstants.sumBackEstimate(speciality,backEstimate);
			} else {
//				类型等于啥就取那个值
			}
		}
		double ret = 0.00;
		if(facilityNumber!=0){
			ret = (Double.parseDouble(String.valueOf(sum))/Double.parseDouble(String.valueOf(facilityNumber)))*100.00;
			ret = Double.parseDouble(new java.text.DecimalFormat("#.00").format(ret));
		}
		
		return ret;
	}
	
	/**
	 * 坏板率
	 */	
	public double getBadBattenRate(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		double ret = 0.00;
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1 = 1 ");
		
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			whereStr.append(" and  pigeonholeTime>'"); 
			whereStr.append(StaticMethod.date2String(timeStart));
			whereStr.append("' and pigeonholeTime<'" );
			whereStr.append(StaticMethod.date2String(timeEnd));
			whereStr.append("' ");			
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr.append(" and  pigeonholeTime>to_date('"); 
			whereStr.append(timeStart);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') and pigeonholeTime<to_date('" );
			whereStr.append(timeEnd);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') ");
		} 	
		
		whereStr.append(" and factory = '");
		whereStr.append(factory);
		whereStr.append("' ");
		if(!"".equals(speciality)){
			whereStr.append(" and speciality = '");
			whereStr.append(speciality);
			whereStr.append("' ");
		}
		if(!"".equals(type)){
			whereStr.append(" and equipmentType = '");
			whereStr.append(type);
			whereStr.append("' ");
		}
		List repairinfosList = arithmeticDao.getRepairinfosList(whereStr.toString());
		
		int blockNum = 0;
		
		for(int i = 0 ; repairinfosList.size()>i ; i++){
			Repairinfo repairinfo = (Repairinfo)repairinfosList.get(i);
			blockNum = blockNum + repairinfo.getBlockNum();
		}
		
		StringBuffer whereStrBE = new StringBuffer();
		whereStrBE.append(" where factory = '");
		whereStrBE.append(factory);
		whereStrBE.append("' ");		
		List facilityNumsList = arithmeticDao.getFacilityNumsList(whereStrBE.toString());
		int facilityNumber = 0;
		if(facilityNumsList.size()>0){
			FacilityNum backEstimate = (FacilityNum)facilityNumsList.get(0);
			if("".equals(type)){
				facilityNumber = FacilityNumConstants.sumBackEstimate(speciality,backEstimate);
			} else {
				
			}
		}
		
		if(facilityNumber!=0){
			ret = (Double.parseDouble(String.valueOf(blockNum))/Double.parseDouble(String.valueOf(facilityNumber)))*100.00;
			ret = Double.parseDouble(new java.text.DecimalFormat("#.00").format(ret));
		}		
		
		return ret;
	}

	/**
	 * 重大故障次数
	 */	
	public int getBigFaultNum(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1 = 1 ");
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			whereStr.append(" and  pigeonholeTime>'"); 
			whereStr.append(StaticMethod.date2String(timeStart));
			whereStr.append("' and pigeonholeTime<'" );
			whereStr.append(StaticMethod.date2String(timeEnd));
			whereStr.append("' ");		
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr.append(" and  pigeonholeTime>to_date('"); 
			whereStr.append(timeStart);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') and pigeonholeTime<to_date('" );
			whereStr.append(timeEnd);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') ");
		} 			
		whereStr.append(" and factory = '");
		whereStr.append(factory);
		whereStr.append("' ");
		if(!"".equals(speciality)){
			whereStr.append(" and speciality = '");
			whereStr.append(speciality);
			whereStr.append("' ");
		}
		if(!"".equals(type)){
			whereStr.append(" and equipmentType = '");
			whereStr.append(type);
			whereStr.append("' ");
		}
		List repairinfosList = arithmeticDao.getBigFaultsList(whereStr.toString());	
		
		return repairinfosList.size();
	}

	/**
	 * 设备问题数
	 */	
	public int getFacilityNum(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1 = 1 ");
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			whereStr.append(" and  occurTime>'"); 
			whereStr.append(StaticMethod.date2String(timeStart));
			whereStr.append("' and occurTime<'" );
			whereStr.append(StaticMethod.date2String(timeEnd));
			whereStr.append("' ");		
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr.append(" and  pigeonholeTime>to_date('"); 
			whereStr.append(timeStart);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') and pigeonholeTime<to_date('" );
			whereStr.append(timeEnd);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') ");
		} 			
		whereStr.append(" and factory = '");
		whereStr.append(factory);
		whereStr.append("' ");
		if(!"".equals(speciality)){
			whereStr.append(" and speciality = '");
			whereStr.append(speciality);
			whereStr.append("' ");
		}
		if(!"".equals(type)){
			whereStr.append(" and equipmentType = '");
			whereStr.append(type);
			whereStr.append("' ");
		}		
		List facilityinfosList = arithmeticDao.getFacilityinfosList(whereStr.toString());	
		
		return facilityinfosList.size();
	}
	/**
	 * 软件升级(含补丁)成功率
	 */	
	public double getSoftwareUpRate(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		double ret = 100.00;
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1 = 1 ");
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			whereStr.append(" and  pigeonholeTime>'"); 
			whereStr.append(StaticMethod.date2String(timeStart));
			whereStr.append("' and pigeonholeTime<'" );
			whereStr.append(StaticMethod.date2String(timeEnd));
			whereStr.append("' ");		
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr.append(" and  pigeonholeTime>to_date('"); 
			whereStr.append(timeStart);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') and pigeonholeTime<to_date('" );
			whereStr.append(timeEnd);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') ");
		} 			
		whereStr.append(" and factory = '");
		whereStr.append(factory);
		whereStr.append("' ");
		if(!"".equals(speciality)){
			whereStr.append(" and speciality = '");
			whereStr.append(speciality);
			whereStr.append("' ");
		}
		if(!"".equals(type)){
			whereStr.append(" and equipmentType = '");
			whereStr.append(type);
			whereStr.append("' ");
		}
		int sumUp = 0;
		int sumUpSuccess = 0;
		List softupinfosList = arithmeticDao.getSoftupinfosList(whereStr.toString());
		
//		for (int i = 0;softupinfosList.size()>i;i++ ){
//			Softupinfo softupinfo = (Softupinfo)softupinfosList.get(i);
//			sumUp = sumUp + softupinfo.getUpfixtureAmount();
//			sumUpSuccess = sumUpSuccess + softupinfo.getUphitAmount();
//		}
		if(sumUp!=0){
			ret = (Double.parseDouble(String.valueOf(sumUpSuccess))/Double.parseDouble(String.valueOf(sumUp)))*100.00;
			ret = Double.parseDouble(new java.text.DecimalFormat("#.00").format(ret));
		}
		
		return ret;
	}

	/**
	 * 故障处理平均时长
	 */	
	public double getFaultAvgTime(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		
		double ret = 0.00;
		
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1 = 1 ");
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			whereStr.append(" and  pigeonholeTime>'"); 
			whereStr.append(StaticMethod.date2String(timeStart));
			whereStr.append("' and pigeonholeTime<'" );
			whereStr.append(StaticMethod.date2String(timeEnd));
			whereStr.append("' ");			
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr.append(" and  pigeonholeTime>to_date('"); 
			whereStr.append(timeStart);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') and pigeonholeTime<to_date('" );
			whereStr.append(timeEnd);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') ");
		} 			
		whereStr.append(" and factory = '");
		whereStr.append(factory);
		whereStr.append("' ");
		if(!"".equals(speciality)){
			whereStr.append(" and speciality = '");
			whereStr.append(speciality);
			whereStr.append("' ");
		}
		if(!"".equals(type)){
			whereStr.append(" and equipmentType = '");
			whereStr.append(type);
			whereStr.append("' ");
		}
		
		List factoryDisposesList = arithmeticDao.getFactoryDisposesList(whereStr.toString());
		
		int timeH = 0;
		int timeM = 0;
		int timeHSum = 0;
		int timeMSum = 0;
		for(int i = 0 ; factoryDisposesList.size()>i ; i++){
			 FactoryDispose factoryDispose = (FactoryDispose)factoryDisposesList.get(i);
			 
			 String faultLong = factoryDispose.getFaultLong();
			 String[] time = faultLong.split(":");
			 timeH = Integer.parseInt(time[0]);
			 timeM = Integer.parseInt(time[1]);
			 
			 timeHSum = timeHSum + timeH;
			 timeMSum = timeMSum + timeM;
		}
		timeMSum = timeHSum*60+timeMSum;
		
		if(factoryDisposesList.size()>0){
			ret = (Double.parseDouble(String.valueOf(timeMSum))/Double.parseDouble(String.valueOf(factoryDisposesList.size())))/Double.parseDouble(String.valueOf(60));
			ret = Double.parseDouble(new java.text.DecimalFormat("#.00").format(ret));
		}
		
		return ret;
	}

	/**
	 * 业务恢复平均时长
	 */	
	public double getResumeAvgTime(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		
		double ret = 0.00;
		
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1 = 1 ");
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			whereStr.append(" and  pigeonholeTime>'"); 
			whereStr.append(StaticMethod.date2String(timeStart));
			whereStr.append("' and pigeonholeTime<'" );
			whereStr.append(StaticMethod.date2String(timeEnd));
			whereStr.append("' ");			
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr.append(" and  pigeonholeTime>to_date('"); 
			whereStr.append(timeStart);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') and pigeonholeTime<to_date('" );
			whereStr.append(timeEnd);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') ");
		} 			
		whereStr.append(" and factory = '");
		whereStr.append(factory);
		whereStr.append("' ");
		if(!"".equals(speciality)){
			whereStr.append(" and speciality = '");
			whereStr.append(speciality);
			whereStr.append("' ");
		}
		if(!"".equals(type)){
			whereStr.append(" and equipmentType = '");
			whereStr.append(type);
			whereStr.append("' ");
		}
		
		List factoryDisposesList = arithmeticDao.getFactoryDisposesList(whereStr.toString());
		
		int timeH = 0;
		int timeM = 0;
		int timeHSum = 0;
		int timeMSum = 0;
		for(int i = 0 ; factoryDisposesList.size()>i ; i++){
			 FactoryDispose factoryDispose = (FactoryDispose)factoryDisposesList.get(i);
			 
			 String faultLong = factoryDispose.getResumeLong();
			 String[] time = faultLong.split(":");
			 timeH = Integer.parseInt(time[0]);
			 timeM = Integer.parseInt(time[1]);
			 
			 timeHSum = timeHSum + timeH;
			 timeMSum = timeMSum + timeM;
		}
		timeMSum = timeHSum*60+timeMSum;
		if(factoryDisposesList.size()>0){
			ret = (Double.parseDouble(String.valueOf(timeMSum))/Double.parseDouble(String.valueOf(factoryDisposesList.size())))/Double.parseDouble(String.valueOf(60));
			ret = Double.parseDouble(new java.text.DecimalFormat("#.00").format(ret));
		}

		return ret;
	}	
	

	/**
	 * 板件返修平均时长
	 */	
	public double getPlankRepairAvgTime(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		
		double ret = 0.00;
		
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1 = 1 ");
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			whereStr.append(" and  pigeonholeTime>'"); 
			whereStr.append(StaticMethod.date2String(timeStart));
			whereStr.append("' and pigeonholeTime<'" );
			whereStr.append(StaticMethod.date2String(timeEnd));
			whereStr.append("' ");			
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr.append(" and  pigeonholeTime>to_date('"); 
			whereStr.append(timeStart);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') and pigeonholeTime<to_date('" );
			whereStr.append(timeEnd);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') ");
		} 			
		whereStr.append(" and factory = '");
		whereStr.append(factory);
		whereStr.append("' ");
		if(!"".equals(speciality)){
			whereStr.append(" and speciality = '");
			whereStr.append(speciality);
			whereStr.append("' ");
		}
		if(!"".equals(type)){
			whereStr.append(" and equipmentType = '");
			whereStr.append(type);
			whereStr.append("' ");
		}
		
		List repairinfosList = arithmeticDao.getRepairinfosList(whereStr.toString());
		int faultLong = 0;
		int faultLongSum = 0;
		for(int i = 0 ; repairinfosList.size()>i ; i++){
			 Repairinfo repairinfo = (Repairinfo)repairinfosList.get(i);
			 
			 faultLong = repairinfo.getRepairLong();
			 faultLongSum = faultLongSum + faultLong;
		}
		if(repairinfosList.size()>0){
			ret = (Double.parseDouble(String.valueOf(faultLongSum))/Double.parseDouble(String.valueOf(repairinfosList.size())));
			ret = Double.parseDouble(new java.text.DecimalFormat("#.00").format(ret));
		}
		return ret;
	}	
	
	/**
	 * 咨询服务反馈平均时长
	 */	
	public double getReferServeAvgTime(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		
		double ret = 0.00;
		
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1 = 1 ");
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			whereStr.append(" and  pigeonholeTime>'"); 
			whereStr.append(StaticMethod.date2String(timeStart));
			whereStr.append("' and pigeonholeTime<'" );
			whereStr.append(StaticMethod.date2String(timeEnd));
			whereStr.append("' ");			
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr.append(" and  pigeonholeTime>to_date('"); 
			whereStr.append(timeStart);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') and pigeonholeTime<to_date('" );
			whereStr.append(timeEnd);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') ");
		} 			
		whereStr.append(" and factory = '");
		whereStr.append(factory);
		whereStr.append("' ");
		if(!"".equals(speciality)){
			whereStr.append(" and speciality = '");
			whereStr.append(speciality);
			whereStr.append("' ");
		}
		if(!"".equals(type)){
			whereStr.append(" and equipmentType = '");
			whereStr.append(type);
			whereStr.append("' ");
		}
		
		List counselsList = arithmeticDao.getCounselsList(whereStr.toString());
		
		int timeH = 0;
		int timeM = 0;
		int timeHSum = 0;
		int timeMSum = 0;
		for(int i = 0 ; counselsList.size()>i ; i++){
			 Counsel counsel = (Counsel)counselsList.get(i);
			 
			 String finallyLong = counsel.getFinallyLong();
			 String[] time = finallyLong.split(":");
			 timeH = Integer.parseInt(time[0]);
			 timeM = Integer.parseInt(time[1]);
			 
			 timeHSum = timeHSum + timeH;
			 timeMSum = timeMSum + timeM;
		}
		
		timeMSum = timeHSum*60+timeMSum;	
		
		if(counselsList.size()>0){
			ret = (Double.parseDouble(String.valueOf(timeMSum))/Double.parseDouble(String.valueOf(counselsList.size())))/Double.parseDouble(String.valueOf(1440));
			ret = Double.parseDouble(new java.text.DecimalFormat("#.00").format(ret));
		}

		return ret;
	}	

	/**
	 * 技术服务满意度
	 */	
	public int getArtDegree(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		
		int ret = 100 ;
		
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1 = 1 ");
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			whereStr.append(" and  pigeonholeTime>'"); 
			whereStr.append(StaticMethod.date2String(timeStart));
			whereStr.append("' and pigeonholeTime<'" );
			whereStr.append(StaticMethod.date2String(timeEnd));
			whereStr.append("' ");			
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr.append(" and  pigeonholeTime>to_date('"); 
			whereStr.append(timeStart);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') and pigeonholeTime<to_date('" );
			whereStr.append(timeEnd);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') ");
		} 			
		whereStr.append(" and factory = '");
		whereStr.append(factory);
		whereStr.append("' ");
		if(!"".equals(speciality)){
			whereStr.append(" and speciality = '");
			whereStr.append(speciality);
			whereStr.append("' ");
		}
//		if(!"".equals(type)){
//			whereStr.append(" and equipmentType = '");
//			whereStr.append(type);
//			whereStr.append("' ");
//		}
		
		List softupinfosList = arithmeticDao.getSoftupinfosList(whereStr.toString());
		List lserveinfosList = arithmeticDao.getLserveinfosList(whereStr.toString());
		List repairinfosList = arithmeticDao.getRepairinfosList(whereStr.toString());
		List counselsList = arithmeticDao.getCounselsList(whereStr.toString());
		
		int sum = 0;
		int total = counselsList.size()+lserveinfosList.size()+repairinfosList.size()+softupinfosList.size();
		
		for(int i = 0 ; counselsList.size()>i ; i++){
			 Counsel counsel = (Counsel)counselsList.get(i);
			 sum = sum + counsel.getSatisfaction();
		}
		for(int i = 0 ; lserveinfosList.size()>i ; i++){
			 Lserveinfo lserveinfo = (Lserveinfo)lserveinfosList.get(i);
			 sum = sum + lserveinfo.getSatisfaction();
		}
//		for(int i = 0 ; repairinfosList.size()>i ; i++){
//			 Repairinfo repairinfo = (Repairinfo)repairinfosList.get(i);
//			 sum = sum + repairinfo.getSatisfaction();
//		}
//		for(int i = 0 ; softupinfosList.size()>i ; i++){
//			 Softupinfo softupinfo = (Softupinfo)softupinfosList.get(i);
//			 sum = sum + softupinfo.getSatisfaction();
//		}
//		
		if(total>0){
			ret = sum/total;
		}
		
		return ret;
	}

	/**
	 * 工程服务满意度
	 */	
	public int getProjectSer(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		
		int ret = 100 ;
		
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1 = 1 ");
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			whereStr.append(" and  endTime>'"); 
			whereStr.append(StaticMethod.date2String(timeStart));
			whereStr.append("' and endTime<'" );
			whereStr.append(StaticMethod.date2String(timeEnd));
			whereStr.append("' ");				
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr.append(" and  endTime>to_date('"); 
			whereStr.append(timeStart);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') and endTime<to_date('" );
			whereStr.append(timeEnd);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') ");
		} 			
		whereStr.append(" and factory = '");
		whereStr.append(factory);
		whereStr.append("' ");
		if(!"".equals(speciality)){
			whereStr.append(" and speciality = '");
			whereStr.append(speciality);
			whereStr.append("' ");
		}
		
		List ftraininfosList = arithmeticDao.getFtraininfosList(whereStr.toString());
		
		int sum = 0;
		int total = ftraininfosList.size();
		
		for(int i = 0 ; ftraininfosList.size()>i ; i++){
			 Ftraininfo ftraininfo = (Ftraininfo)ftraininfosList.get(i);
			 sum = sum + ftraininfo.getSatisfaction();
		}
		
		if(total>0){
			ret = sum/total;
		}
		
		return ret;
	}

	/**
	 * 培训服务满意度
	 */	
	public int getTrainSer(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		
		int ret = 100 ;
		
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1 = 1 ");
		if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
			whereStr.append(" and  endTime>'"); 
			whereStr.append(StaticMethod.date2String(timeStart));
			whereStr.append("' and endTime<'" );
			whereStr.append(StaticMethod.date2String(timeEnd));
			whereStr.append("' ");				
		}
		else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
			whereStr.append(" and  endTime>to_date('"); 
			whereStr.append(timeStart);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') and endTime<to_date('" );
			whereStr.append(timeEnd);
			whereStr.append("','yyyy-MM-dd HH24:mi:ss') ");
		} 			
		whereStr.append(" and factory = '");
		whereStr.append(factory);
		whereStr.append("' ");
		if(!"".equals(speciality)){
			whereStr.append(" and speciality = '");
			whereStr.append(speciality);
			whereStr.append("' ");
		}
		
		List peventinfosList = arithmeticDao.getPeventinfosList(whereStr.toString());
		
		int sum = 0;
		int total = peventinfosList.size();
		
		for(int i = 0 ; peventinfosList.size()>i ; i++){
			 Peventinfo peventinfo = (Peventinfo)peventinfosList.get(i);
			 sum = sum + peventinfo.getSatisfaction();
		}
		
		if(total>0){
			ret = sum/total;
		}
		
		return ret;
	}	
	public ArithmeticDao getArithmeticDao() {
		return arithmeticDao;
	}
	public void setArithmeticDao(ArithmeticDao arithmeticDao) {
		this.arithmeticDao = arithmeticDao;
	}
	
	
}