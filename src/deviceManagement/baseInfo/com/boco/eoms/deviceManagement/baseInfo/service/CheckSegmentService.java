package com.boco.eoms.deviceManagement.baseInfo.service;

import java.util.List;

import com.boco.eoms.deviceManagement.baseInfo.model.CheckSegment;
import com.googlecode.genericdao.search.SearchResult;



public interface CheckSegmentService {
		
	public boolean add(CheckSegment checkSegment);
	
	public void delete(String id);
	
	public void deltetSome(String[] ids);
	
	public void edit(String id,String name, String lineType, String lineLevel, String segmentType, String startFlag, String endFlag);
	
	public List<CheckSegment> find(String name, String segmentType, String department, int first, int max);
	
	public SearchResult<CheckSegment> find2(String name, String segmentType, String department, int first, int max);
	
	public CheckSegment findById(String id); 
	
	public List<CheckSegment> listAll2(int first, int max);
	
	public SearchResult<CheckSegment> listAll(int first, int max);
	
	public List<CheckSegment> listAll();
	
	public String name2id(String segmentName);

}
