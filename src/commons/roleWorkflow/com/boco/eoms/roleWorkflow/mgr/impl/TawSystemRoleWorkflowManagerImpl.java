/*
 * Created on 2007-11-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.boco.eoms.roleWorkflow.mgr.impl;

import java.util.List;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.roleWorkflow.dao.ITawSystemRoleWorkflowDAO;
import com.boco.eoms.roleWorkflow.mgr.ITawSystemRoleWorkflowManager;
import com.boco.eoms.roleWorkflow.model.TawSystemRoleWorkflow;

/**
 * @author IBM
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TawSystemRoleWorkflowManagerImpl extends BaseManager implements ITawSystemRoleWorkflowManager{
	private ITawSystemRoleWorkflowDAO dao;

	/**
	 * @return Returns the dao.
	 */
	public ITawSystemRoleWorkflowDAO getITawSystemRoleWorkflowDAO() {
		return dao;
	}
	/**
	 * @param dao The dao to set.
	 */
	public void setITawSystemRoleWorkflowDAO(ITawSystemRoleWorkflowDAO dao) {
		this.dao = dao;
	}
	/* (non-Javadoc)
	 * @see com.boco.eoms.sheet.base.service.ITawSystemWorkflowManager#getTawSystemWorkflows()
	 */
	public List getTawSystemWorkflows() {
		// TODO Auto-generated method stub
		return dao.getTawSystemWorkflows();
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.sheet.base.service.ITawSystemWorkflowManager#getTawSystemWorkflow(long)
	 */
	public TawSystemRoleWorkflow getTawSystemWorkflow(long id) {
		// TODO Auto-generated method stub
		return dao.getTawSystemWorkflow(id);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.sheet.base.service.ITawSystemWorkflowManager#saveTawSystemWorkflow(com.boco.eoms.sheet.base.model.TawSystemWorkflow)
	 */
	public void saveTawSystemWorkflow(TawSystemRoleWorkflow tawSystemWorkflow) {
		// TODO Auto-generated method stub
		dao.saveTawSystemWorkflow(tawSystemWorkflow);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.sheet.base.service.ITawSystemWorkflowManager#removeTawSystemWorkflow(long)
	 */
	public void removeTawSystemWorkflow(long id) throws Exception {
		// TODO Auto-generated method stub
		dao.removeTawSystemWorkflow(id);
	}

	/**
     * 根据流程名称查询流程信息
     * @param name 流程名称
     * @return
     * @throws Exception
     */
	public TawSystemRoleWorkflow getTawSystemWorkflowByName(String name) throws Exception {
		return dao.getTawSystemWorkflowByName(name);
	}
	public TawSystemRoleWorkflow getTawSystemWorkflowByBeanId(String mainBeanId) throws Exception {
		// TODO Auto-generated method stub
		return dao.getTawSystemWorkflowByBeanId(mainBeanId);
	}
	public TawSystemRoleWorkflow getTawSystemWorkflowByFlowId(final String flowId) throws Exception{
		return dao.getTawSystemWorkflowByFlowId(flowId);
	}
}
