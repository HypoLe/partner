package com.boco.eoms.deviceManagement.baseInfo.service;

import java.util.List;

import com.boco.eoms.deviceManagement.baseInfo.dao.CheckSegmentDAO;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckSegment;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class CheckSegmentServiceImpl implements CheckSegmentService {
	
	private CheckSegmentDAO checkSegmentDAO;

	public void setCheckSegmentDAO(CheckSegmentDAO checkSegmentDAO) {
		this.checkSegmentDAO = checkSegmentDAO;
	}

	public boolean add(CheckSegment checkSegment) {
		return checkSegmentDAO.save(checkSegment);
	}

	public void delete(String id) {		
		//return checkSegmentDAO.removeById(id);
		CheckSegment checkSegment = checkSegmentDAO.find(id);
		checkSegment.setDeleteFlag("1");
		checkSegmentDAO.save(checkSegment);
	}

	public void deltetSome(String[] ids) {
		//checkSegmentDAO.removeByIds(ids);
		for(String id : ids) {
			delete(id);
		}
	}

	public void edit(String id, String name, String lineType, String lineLevel, String segmentType, String startFlag, String endFlag) {
		CheckSegment checkSegment = checkSegmentDAO.find(id);
		//checkSegment.setSegmentName(name);
		checkSegment.setLineType(lineType);
		checkSegment.setLineLevel(lineLevel);
		checkSegment.setStartFlag(startFlag);
		checkSegment.setEndFlag(endFlag);
		checkSegmentDAO.save(checkSegment);
		return ;
	}

	public List<CheckSegment> find(String name, String segmentType, String department, int first, int max) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public SearchResult<CheckSegment> find2(String name, String segmentType, String department,
			int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addFilterNotEqual("deleteFlag", "1");
		if(!"".equals(name)&&name!=null)
			search.addFilterLike("segmentName", "%"+name+"%");
		if(!"".equals(segmentType)&&segmentType!=null)
			search.addFilterEqual("segmentType", segmentType);
		if(!"".equals(department)&&department!=null)
			search.addFilterEqual("department", department);
		return checkSegmentDAO.searchAndCount(search);
	}

	public List<CheckSegment> listAll2(int first, int max) {
		return checkSegmentDAO.findAll(first, max);
	}
	
	public SearchResult<CheckSegment> listAll(int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		//search.addFilterNotEqual("deleteFlag", "1");
		//search.addFilterNull("deleteFlag");
		search.addFilterEqual("deleteFlag", "0");
		return checkSegmentDAO.searchAndCount(search);
	}

	public CheckSegment findById(String id) {
		return checkSegmentDAO.find(id);
	}
	
	public List<CheckSegment> listAll() {
		//return checkSegmentDAO.findAll();
		Search search = new Search();
		search.addFilterEqual("deleteFlag", "0");
		return checkSegmentDAO.search(search);
	}
	
	public String name2id(String segmentName) {
		Search search = new Search();
		search.addFilterEqual("segmentName", segmentName);
		CheckSegment checkSegment = checkSegmentDAO.searchUnique(search);
		return checkSegment.getId();
	}
	
	

}
