package com.boco.eoms.deviceManagement.baseInfo.dao;


import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckSegment;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;


public class CheckSegmentDAOImpl extends GenericDAOImpl<CheckSegment, String> implements
		CheckSegmentDAO, ID2NameDAO{

	public List<CheckSegment> findByParems(String name, String segmentType, int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		if(!"".equals(name) && name != null){
			search.addFilterLike(name, "%"+name+"%");
		}
		if(!"".equals(segmentType) && segmentType != null){
			search.addFilterEqual("segmentType", segmentType);
		}
		return search(search);
	}
	
	public List<CheckSegment> findAll(int first, int max){
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		return search(search);
	}
	
	public List<String> findAllCheckSegmentName() {
		List<CheckSegment> list = findAll();
		List<String> nameList = new ArrayList<String>();
		for(CheckSegment checkSegment : list) {
			nameList.add(checkSegment.getSegmentName());
		}  
		return nameList;
	}
	
	public CheckSegment findByName(String name) {
		Search search = new Search();
		search.addFilterEqual("name", name);
		return searchUnique(search);
	}
	
	public String id2Name(String id) {
		CheckSegment checkSegment = find(id);
		return checkSegment.getSegmentName();
	}

	

}
