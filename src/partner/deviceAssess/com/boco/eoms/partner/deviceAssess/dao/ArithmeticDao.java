package com.boco.eoms.partner.deviceAssess.dao;

import java.util.Date;
import java.util.List;

import com.boco.eoms.base.dao.Dao;

/**
 * <p>
 * Title:厂家设备重大故障事件信息
 * </p>
 * <p>
 * Description:厂家设备重大故障事件信息
 * </p>
 * <p>
 * Mon Sep 27 13:45:23 CST 2010 
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface ArithmeticDao extends Dao {
	/**
	 * (内部处理)故障事件信息
	 */	
	public List getInsideDisposesList( final String whereStr );
	/**
	 * (内部处理)故障事件信息
	 */
	public List getInsideDisposes(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) ;
	/**
	 * (厂家处理)故障事件信息
	 */	
	public List getFactoryDisposesList( final String whereStr );
	/**
	 * 按条件得到设备量
	 */	
	public List getFacilityNumsList( final String whereStr ) ;
	
	/**
	 * 按条件得到板件返修设备信息
	 */	
	public List getRepairinfosList( final String whereStr ) ;
	
	/**
	 * 按条件得到厂家设备重大故障事件信息列表
	 */	
	public List getBigFaultsList( final String whereStr ); 	
	
	/**
	 * 按条件得到设备问题信息
	 */	
	public List getFacilityinfosList( final String whereStr );
	/**
	 * 按条件得到设备问题信息
	 */	
	public List getSoftupinfosList( final String whereStr ) ;
	/**
	 * 按条件得到咨询服务信息
	 */	
	public List getCounselsList( final String whereStr );	
	/**
	 * 按条件得到现场服务事件信息
	 */	
	public List getLserveinfosList( final String whereStr ); 	
	/**
	 * 按条件得到培训服务事件信息
	 */	
	public List getPeventinfosList( final String whereStr ) ;
	/**
	 * 按条件得到工程服务事件信息
	 */	
	public List getFtraininfosList( final String whereStr ) ;	
}