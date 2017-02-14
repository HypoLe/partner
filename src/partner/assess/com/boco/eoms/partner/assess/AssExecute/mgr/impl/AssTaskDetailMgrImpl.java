package com.boco.eoms.partner.assess.AssExecute.mgr.impl;

import java.util.List;

import com.boco.eoms.partner.assess.AssExecute.dao.IAssTaskDetailDao;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskDetailMgr;
import com.boco.eoms.partner.assess.AssExecute.model.AssTaskDetail;
/**
 * <p>
 * Title:考核任务明细业务方法类
 * </p>
 * <p>
 * Description:考核任务明细业务方法类
 * </p>
 * <p>
 * Date:Nov 26, 2010 5:52:06 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public abstract class AssTaskDetailMgrImpl implements IAssTaskDetailMgr {

	private IAssTaskDetailDao assTaskDetailDao;

	public IAssTaskDetailDao getAssTaskDetailDao() {
		return assTaskDetailDao;
	}

	public void setAssTaskDetailDao(IAssTaskDetailDao assTaskDetailDao) {
		this.assTaskDetailDao = assTaskDetailDao;
	}

	public int getMaxListNoOfTask(String taskId) {
		return assTaskDetailDao.getMaxListNoOfTask(taskId);
	}

	public List listDetailOfTaskByListNo(String taskId, String listNo) {
		return assTaskDetailDao.listDetailOfTaskByListNo(taskId, listNo);
	}

	public void saveTask(AssTaskDetail taskDetail) {
		assTaskDetailDao.saveTask(taskDetail);
	}
	
	public AssTaskDetail getTaskDetailById(String id){
		return assTaskDetailDao.getTaskDetailById(id);
	}
	public String getLeafNodesOfChild(String taskId,String parentNodeId){
		StringBuffer leafNodesOfChild = new StringBuffer();
		List list = assTaskDetailDao.getLeafNodesOfChild(taskId,parentNodeId);
		for(int i=0;i<list.size();i++){
			if(i>0){
				leafNodesOfChild.append(",");
			}
			AssTaskDetail assTaskDetail = (AssTaskDetail)list.get(i);
			leafNodesOfChild.append(assTaskDetail.getNodeId());
		}
		return leafNodesOfChild.toString();
	}
	public String getLeafNodesOfChildForDeduct(String taskId,String parentNodeId){
		StringBuffer leafNodesOfChild = new StringBuffer();
		List list = assTaskDetailDao.getLeafNodesOfChildForDeduct(taskId,parentNodeId);
		for(int i=0;i<list.size();i++){
			if(i>0){
				leafNodesOfChild.append(",");
			}
			AssTaskDetail assTaskDetail = (AssTaskDetail)list.get(i);
			leafNodesOfChild.append(assTaskDetail.getNodeId());
		}
		return leafNodesOfChild.toString();
	}
	
	public AssTaskDetail getTaskDetail(String where) {
		return assTaskDetailDao.getTaskDetail(where);
	}		
}
