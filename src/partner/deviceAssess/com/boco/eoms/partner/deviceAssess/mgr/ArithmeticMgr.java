package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.Date;
import java.util.List;


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
public interface ArithmeticMgr {
	/**
	 * 设备故障率
	 */
	public double getFixingFaultRate(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) ;
	/**
	 * 坏板率
	 */	
	public double getBadBattenRate(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) ;
	/**
	 * 重大故障次数
	 */
	public int getBigFaultNum(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) ;
	/**
	 * 设备问题数
	 */	
	public int getFacilityNum(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) ;
	/**
	 * 软件升级(含补丁)成功率
	 */	
	public double getSoftwareUpRate(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) ;
	/**
	 * 故障处理平均时长
	 */	
	public double getFaultAvgTime(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) ;
	/**
	 * 业务恢复平均时长
	 */	
	public double getResumeAvgTime(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) ;
	/**
	 * 板件返修平均时长
	 */	
	public double getPlankRepairAvgTime(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) ;
	/**
	 * 咨询服务反馈平均时长
	 */	
	public double getReferServeAvgTime(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) ;
	/**
	 * 技术服务满意度
	 */	
	public int getArtDegree(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) ;
	/**
	 * 工程服务满意度
	 */	
	public int getProjectSer(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) ;
	/**
	 * 培训服务满意度
	 */	
	public int getTrainSer(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) ;

}