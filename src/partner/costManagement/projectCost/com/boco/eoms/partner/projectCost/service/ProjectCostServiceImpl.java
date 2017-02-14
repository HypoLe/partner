package com.boco.eoms.partner.projectCost.service;

import base.service.Nop3GenericServiceImpl;

import com.boco.eoms.partner.projectCost.dao.ProjectCostDao;
import com.boco.eoms.partner.projectCost.model.ProjectCostModel;

public class ProjectCostServiceImpl extends Nop3GenericServiceImpl<ProjectCostModel> implements ProjectCostService {
	private ProjectCostDao projectCostDao;

	public ProjectCostDao getProjectCostDao() {
		return projectCostDao;
	}

	public void setProjectCostDao(ProjectCostDao projectCostDao) {
		this.projectCostDao = projectCostDao;
		this.setNop3GenericDao(projectCostDao);
	}
	
	


	
	
}
	



