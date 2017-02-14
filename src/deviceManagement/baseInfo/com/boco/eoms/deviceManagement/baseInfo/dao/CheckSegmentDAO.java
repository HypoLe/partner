package com.boco.eoms.deviceManagement.baseInfo.dao;

import java.util.List;

import com.boco.eoms.deviceManagement.baseInfo.model.CheckSegment;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface CheckSegmentDAO  extends GenericDAO<CheckSegment, String> {
	
	public List<CheckSegment> findByParems(String name, String segmentType, int first, int max);
	
	public List<CheckSegment> findAll(int first, int max);
	
	public List<String> findAllCheckSegmentName() ;
	
	public CheckSegment findByName(String name);
	

}
