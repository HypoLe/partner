package com.boco.eoms.deviceManagement.busi.protectline.service;




import com.boco.eoms.deviceManagement.busi.protectline.model.ArmyPoliceCivilian;

import com.googlecode.genericdao.search.SearchResult;

public interface ArmyPoliceCivilianService {
	// extends CommonGenericService<ArmyPoliceCivilian>
	public boolean add(ArmyPoliceCivilian armyPoliceCivilian);

	public boolean deleteWa(String id);
	public void deltetAll(String[] ids);
	public SearchResult<ArmyPoliceCivilian> approvingfind(String projectName, String nameOfOrganization,
			String userid,int first, int max);
	public void edit(String id, String name, String lineType, String lineLevel,
			String segmentType, String startFlag, String endFlag);
	public SearchResult<ArmyPoliceCivilian> findByCondition(String projectName, String nameOfOrganization,String state,
			int first, int max);
//	public SearchResult<ArmyPoliceCivilian> find(String name, String segmentType,String userid
//			 int first, int max);
	public SearchResult<ArmyPoliceCivilian> find(String state,String approvingPerson,String projectName,
			String nameOfOrganization, String userid,int first, int max);
	//public SearchResult<ArmyPoliceCivilian> listAll(int i, int pageSize);
	public ArmyPoliceCivilian findDetail(String id);
	public SearchResult<ArmyPoliceCivilian> findArchied(String projectName, String nameOfOrganization,int first,int max	
	);
	public SearchResult<ArmyPoliceCivilian> approvedfind(String projectName, String nameOfOrganization,int first,int max);

	public SearchResult<ArmyPoliceCivilian> approvedFindSuccessed(String projectName, String nameOfOrganization,String approvingPerson,int first,int max);
	public SearchResult<ArmyPoliceCivilian> staticsFindwa(String projectName, String nameOfOrganization,String belongTheLocal,int first,int max );

}
