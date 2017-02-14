package com.boco.eoms.deviceManagement.busi.line.service.impl;

import java.util.Date;
import java.util.List;

import com.boco.eoms.deviceManagement.busi.line.dao.ProjectBaseInfoDAO;
import com.boco.eoms.deviceManagement.busi.line.dao.ProjectOperInfoDAO;
import com.boco.eoms.deviceManagement.busi.line.model.ProjectBaseInfo;
import com.boco.eoms.deviceManagement.busi.line.model.ProjectOperInfo;
import com.boco.eoms.deviceManagement.busi.line.service.LineProjectService;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class LineProjectServiceImpl implements LineProjectService {
	
	private ProjectBaseInfoDAO projectBaseInfoDAO;
	private ProjectOperInfoDAO projectOperInfoDAO;

	public void setProjectBaseInfoDAO(ProjectBaseInfoDAO projectBaseInfoDAO) {
		this.projectBaseInfoDAO = projectBaseInfoDAO;
	}
		

	public void setProjectOperInfoDAO(ProjectOperInfoDAO projectOperInfoDAO) {
		this.projectOperInfoDAO = projectOperInfoDAO;
	}

	public void applyProject(String projectId, String projectName, String projectType,
			String projectLocation, String networkType, String applyer,
			String applyCompany, String effect, String projectLength, String attachment,
			String startTime, String lastTime, String allower, String remarks) {
		ProjectBaseInfo projectBaseInfo = null;
		if("".equals(projectId)||projectId==null) {
			projectBaseInfo = new ProjectBaseInfo();
			projectBaseInfo.setProjectName(projectName);
		} else {
			projectBaseInfo = projectBaseInfoDAO.find(projectId);
		}		
		projectBaseInfo.setProjectType(projectType);
		projectBaseInfo.setProjectLocation(projectLocation);
		projectBaseInfo.setNetworkType(networkType);
		projectBaseInfo.setApplyer(applyer);
		projectBaseInfo.setApplyCompany(applyCompany);
		projectBaseInfo.setApplyTime(CommonUtils.toEomsStandardDate(new Date()));
		if(!"".equals(attachment))
		projectBaseInfo.setAttachment(attachment);
		projectBaseInfo.setEffect(effect);
		projectBaseInfo.setProjectLength(projectLength);
		projectBaseInfo.setStartTime(startTime);
		projectBaseInfo.setLastTime(lastTime);
		projectBaseInfo.setAllower(allower);
		projectBaseInfo.setRemarks(remarks);
		projectBaseInfo.setStatus("申请中");
		projectBaseInfoDAO.save(projectBaseInfo);
		ProjectOperInfo projectOperInfo = new ProjectOperInfo();
		projectOperInfo.setOperateTime(projectBaseInfo.getApplyTime());
		projectOperInfo.setOperator(projectBaseInfo.getApplyer());
		projectOperInfo.setOperateType("申请");
		projectOperInfo.setNextOperator(projectBaseInfo.getAllower());
		projectOperInfo.setProjectId(projectBaseInfo.getId());
		projectOperInfoDAO.save(projectOperInfo);
	}
	
	public SearchResult<ProjectBaseInfo> listCheck(String userid, int first, int max){
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("applyTime", true);
		search.addFilterEqual("status", "申请中");
		search.addFilterEqual("allower", userid);
		return projectBaseInfoDAO.searchAndCount(search);
	}

	public void checkProject(String userid, String id, String checker, String checkDate,
			String projectPay, String attachment, String checkRemarks, String consignee, String remarks, boolean pass) {
		ProjectBaseInfo projectBaseInfo = projectBaseInfoDAO.find(id);
		ProjectOperInfo projectOperInfo = new ProjectOperInfo();
		projectOperInfo.setProjectId(id);
		projectOperInfo.setOperateRemarks(remarks);
		projectOperInfo.setOperateTime(CommonUtils.toEomsStandardDate(new Date()));
		projectOperInfo.setOperator(userid);
		
		if(!pass) {
			projectOperInfo.setNextOperator(projectBaseInfo.getApplyer());
			projectBaseInfo.setStatus("申请驳回");
			projectOperInfo.setOperateType("申请驳回");
		} else {
			projectOperInfo.setNextOperator(consignee);
			projectBaseInfo.setChecker(checker);
			projectBaseInfo.setConsignee(consignee);
			projectBaseInfo.setProjectPay(projectPay);
			projectBaseInfo.setCheckattachment(attachment);
			projectBaseInfo.setCheckRemarks(checkRemarks);
			projectBaseInfo.setCheckDate(checkDate);
			projectOperInfo.setOperateType("申请通过");
			projectBaseInfo.setStatus("申请通过");
		}
		projectBaseInfoDAO.save(projectBaseInfo);
		projectOperInfoDAO.save(projectOperInfo);

	}
	
	public SearchResult<ProjectBaseInfo> listCommission(String userid, int first, int max){ 
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("applyTime", true);
		search.addFilterEqual("status", "申请通过");
		search.addFilterEqual("consignee", userid);
		return projectBaseInfoDAO.searchAndCount(search);
	}

	public void commisProject(String userid, String projectId, String constructor, String constructorLocation, String constructorPhone, String constructorContacter, String constructorType, String constructPay, String constructStartTime, String constructRemarks, String constructattachment) {
		ProjectBaseInfo projectBaseInfo = projectBaseInfoDAO.find(projectId);
		projectBaseInfo.setStatus("施工中");
		projectBaseInfo.setConstructattachment(constructattachment);
		projectBaseInfo.setConstructor(constructor);
		projectBaseInfo.setConstructorContacter(constructorContacter);
		projectBaseInfo.setConstructorLocation(constructorLocation);
		projectBaseInfo.setConstructorPhone(constructorPhone);
		projectBaseInfo.setConstructorType(constructPay);
		projectBaseInfo.setConstructPay(constructPay);
		projectBaseInfo.setConstructRemarks(constructRemarks);
		projectBaseInfo.setConstructStartTime(constructStartTime);
		ProjectOperInfo projectOperInfo = new ProjectOperInfo();
		projectOperInfo.setNextOperator(userid);
		projectOperInfo.setOperateTime(CommonUtils.toEomsStandardDate(new Date()));
		projectOperInfo.setOperator(userid);
		projectOperInfo.setOperateType("委托");
		projectOperInfo.setProjectId(projectBaseInfo.getId());
		projectOperInfo.setOperateRemarks(constructRemarks);
		projectBaseInfoDAO.save(projectBaseInfo);
		projectOperInfoDAO.save(projectOperInfo);

	}
	
	public SearchResult<ProjectBaseInfo> listConstruct(String userid, int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("applyTime", true);
		search.addFilterEqual("status", "施工中");
		search.addFilterEqual("consignee", userid);
		return projectBaseInfoDAO.searchAndCount(search);
	}

	public void construtProject(String userid, String projectId, String constructor2, String constructorLocation2, String constructorPhone2, String constructorContacter2, String constructStartTime2, String constructEndTime2, String constructRemarks2, String constructReview ) {
		ProjectBaseInfo projectBaseInfo = projectBaseInfoDAO.find(projectId);
		projectBaseInfo.setConstructor2(constructor2);
		projectBaseInfo.setConstructorContacter2(constructorContacter2);
		projectBaseInfo.setConstructorLocation2(constructorLocation2);
		projectBaseInfo.setConstructorPhone2(constructorPhone2);
		projectBaseInfo.setConstructRemarks2(constructRemarks2);
		projectBaseInfo.setConstructStartTime2(constructStartTime2);
		projectBaseInfo.setConstructEndTime2(constructEndTime2);
		projectBaseInfo.setStatus("待验收");
		ProjectOperInfo projectOperInfo = new ProjectOperInfo();
		projectOperInfo.setNextOperator(projectBaseInfo.getAllower());
		projectOperInfo.setOperateRemarks(constructRemarks2);
		projectOperInfo.setOperateTime(CommonUtils.toEomsStandardDate(new Date()));
		projectOperInfo.setOperateType("施工");
		projectOperInfo.setOperator(userid);
		projectOperInfo.setProjectId(projectId);
		projectBaseInfoDAO.save(projectBaseInfo);
		projectOperInfoDAO.save(projectOperInfo);
	}
	
	public SearchResult<ProjectBaseInfo> listAccept(String userid, int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("applyTime", true);
		search.addFilterEqual("status", "待验收");
		search.addFilterEqual("allower", userid);
		return projectBaseInfoDAO.searchAndCount(search);
	}
	
	public void acceptProject(String userid, String projectId, String acceptResult, String acceptRemarks, String acceptattachment) {
		ProjectBaseInfo projectBaseInfo = projectBaseInfoDAO.find(projectId);
		projectBaseInfo.setAcceptattachment(acceptattachment);
		projectBaseInfo.setAccepter(userid);
		projectBaseInfo.setAcceptResult(acceptResult);
		if("验收合格".equals(acceptResult))
			projectBaseInfo.setStatus("合格归档");
		else
			projectBaseInfo.setStatus("不合格归档");
		projectBaseInfo.setAcceptRemarks(acceptRemarks);
		ProjectOperInfo projectOperInfo = new ProjectOperInfo();
		projectOperInfo.setNextOperator("");
		projectOperInfo.setOperateRemarks(acceptRemarks);
		projectOperInfo.setOperateTime(CommonUtils.toEomsStandardDate(new Date()));
		projectOperInfo.setOperateType("验收");
		projectOperInfo.setOperator(userid);
		projectOperInfo.setProjectId(projectId);
		projectBaseInfoDAO.save(projectBaseInfo);
		projectOperInfoDAO.save(projectOperInfo);
	}
	
	
	

	public SearchResult<ProjectBaseInfo> listUncheck(String userid, int first,
			int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("applyTime", true);
		search.addFilterEqual("applyer", userid);
		search.addFilterIn("status", "申请驳回", "待申请");
		return projectBaseInfoDAO.searchAndCount(search);
	}


	public ProjectBaseInfo findById(String id) {
		return projectBaseInfoDAO.find(id);
	}	

	public void delete(String id) {
		projectBaseInfoDAO.removeById(id);		
	}
	
	
	public List<ProjectOperInfo> findReject(String id) {
		Search search = new Search();
		search.addFilterEqual("projectId", id);
		search.addFilterEqual("operateType", "申请驳回");
		return projectOperInfoDAO.search(search);
	}
	
	public List<ProjectOperInfo> findOperInfoById(String id) {
		Search search = new Search();
		search.addFilterEqual("projectId", id);
		return projectOperInfoDAO.search(search);
	}


	public SearchResult<ProjectBaseInfo> listFind(String projectName,
			String networkType, String status, int first, int max) {
		Search search = new Search();
		search.setFirstResult(first);
		search.setMaxResults(max);
		search.addSort("applyTime", true);
		if(!"".equals(projectName) && projectName != null){
			search.addFilterLike("projectName", "%"+projectName+"%");
		}
		if(!"".equals(networkType) && networkType != null) {
			search.addFilterEqual("networkType", networkType);
		}
		if(!"".equals(status) && status != null) {
			search.addFilterEqual("status", status);
		}
		return projectBaseInfoDAO.searchAndCount(search);
	}


	public void saveProject() {
		// TODO Auto-generated method stub
		
	}

}
