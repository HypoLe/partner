package com.boco.eoms.deviceManagement.busi.line.service;


import java.util.List;

import com.boco.eoms.deviceManagement.busi.line.model.ProjectBaseInfo;
import com.boco.eoms.deviceManagement.busi.line.model.ProjectOperInfo;
import com.googlecode.genericdao.search.SearchResult;

public interface LineProjectService {
	
	public void applyProject(String projectId, String projectName, String projectType, String projectLocation, String networkType, String applyer, String applyCompany, String effect, String length, String attachment, String startTime, String lastTime, String allower, String remarks);
	
	
	public SearchResult<ProjectBaseInfo> listCheck(String userid, int first, int max);
	
	public void saveProject();
	
	public void checkProject(String userid, String projectId, String checker, String checkDate, String projectPay, String attachment, String checkRemarks, String consignee, String remarks, boolean pass);
	
	public void commisProject(String userid, String projectId, String constructor, String constructorLocation, String constructorPhone, String constructorContacter, String constructorType, String constructPay, String constructStartTime, String constructRemarks, String constructattachment);

	public SearchResult<ProjectBaseInfo> listCommission(String userid, int first, int max);
	
	public SearchResult<ProjectBaseInfo> listConstruct(String userid, int first, int max);
	
	public void construtProject(String userid, String projectId, String constructor2, String constructorLocation2, String constructorPhone2, String constructorContacter2, String constructStartTime2, String constructEndTime2, String constructRemarks2, String constructReview );
	
	public SearchResult<ProjectBaseInfo> listAccept(String userid, int first, int max);
	
	public void acceptProject(String userid, String projectId, String acceptResult, String acceptRemarks, String acceptattachment);
	
	public  SearchResult<ProjectBaseInfo> listUncheck(String userid, int first, int max);
	
	public ProjectBaseInfo findById(String id);
	
	public List<ProjectOperInfo> findReject(String id);

	public  SearchResult<ProjectBaseInfo> listFind(String projectName, String networkType, String status, int first, int max);
	
	public List<ProjectOperInfo> findOperInfoById(String id);
	
	public void delete(String id);
}
