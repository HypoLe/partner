/**
 * 
 */
package com.boco.eoms.partner.assess.AssTree.dao.hibernate;

import com.boco.eoms.partner.assess.AssTree.model.AssTree;

/**
 * <p>
 * Title:后评估模板树dao操作类(传输专业)
 * </p>
 * <p>
 * Description:后评估模板树dao操作类(传输专业)
 * </p>
 * <p>
 * Date:Nov 23, 2010 1:58:50 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class TranAssTreeDaoHibernate extends AssTreeDaoHibernate {


	public String id2Name(String nodeId) {
		AssTree node = getTreeNodeByNodeId(nodeId);
		return node.getNodeName();
	}
}
