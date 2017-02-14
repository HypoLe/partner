package com.boco.eoms.partner.eva.test.dao;

import org.springframework.transaction.PlatformTransactionManager;

import com.boco.eoms.base.test.console.ConsoleTestCase;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.eva.dao.IPnrEvaKpiDao;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTreeMgr;
import com.boco.eoms.partner.eva.model.PnrEvaKpi;
import com.boco.eoms.partner.eva.model.PnrEvaTree;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaDateUtil;

public class PnrEvaKpiDaoTest extends ConsoleTestCase {

	private IPnrEvaKpiDao iPnrEvaKpiDao;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpBeforeTransaction()
	 */
	protected void onSetUpBeforeTransaction() throws Exception {
		this.setTransactionManager((PlatformTransactionManager)getBean("transactionManager"));
		this.setDefaultRollback(true);
		super.onSetUpBeforeTransaction();
		this.iPnrEvaKpiDao = (IPnrEvaKpiDao)getBean("pnrEvaKpiDao");
	}
	/**
	 * 根据主键id查询指标
	 */
	public final void testGetKpiString() {
		PnrEvaKpi pnrEvaKpi = new PnrEvaKpi();
		pnrEvaKpi.setAlgorithm("1");
		pnrEvaKpi.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaKpi.setCreator("admin");
		pnrEvaKpi.setCycle("1");
		pnrEvaKpi.setDeleted("0");
		pnrEvaKpi.setEvaScore(Float.valueOf("100"));
		pnrEvaKpi.setKpiName("name1");
		pnrEvaKpi.setRemark("test");
		pnrEvaKpi.setRuleGroupId("11");
		pnrEvaKpi.setThreshold(Float.valueOf("100"));
		pnrEvaKpi.setWeight(100);
		pnrEvaKpi.setDefaultValue(100);
		iPnrEvaKpiDao.saveKpi(pnrEvaKpi);
		
		PnrEvaKpi newPnrEvaKpi = iPnrEvaKpiDao.getKpi(pnrEvaKpi.getId());
		assertNotNull(newPnrEvaKpi);
	}
	/**
	 * 根据主键id标志和删除查询指标
	 */
	public final void testGetKpiStringString() {
		PnrEvaKpi pnrEvaKpi = new PnrEvaKpi();
		pnrEvaKpi.setAlgorithm("1");
		pnrEvaKpi.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaKpi.setCreator("admin");
		pnrEvaKpi.setCycle("1");
		pnrEvaKpi.setDeleted("0");
		pnrEvaKpi.setEvaScore(Float.valueOf("100"));
		pnrEvaKpi.setKpiName("name1");
		pnrEvaKpi.setRemark("test");
		pnrEvaKpi.setRuleGroupId("11");
		pnrEvaKpi.setThreshold(Float.valueOf("100"));
		pnrEvaKpi.setWeight(100);
		pnrEvaKpi.setDefaultValue(100);
		iPnrEvaKpiDao.saveKpi(pnrEvaKpi);
		
		PnrEvaKpi newPnrEvaKpi = iPnrEvaKpiDao.getKpi(pnrEvaKpi.getId(),"0");
		assertNotNull(newPnrEvaKpi);
	}
	/**
	 * 删除指标
	 */
	public final void testRemoveKpi() {
		PnrEvaKpi pnrEvaKpi = new PnrEvaKpi();
		pnrEvaKpi.setAlgorithm("1");
		pnrEvaKpi.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaKpi.setCreator("admin");
		pnrEvaKpi.setCycle("1");
		pnrEvaKpi.setDeleted("0");
		pnrEvaKpi.setEvaScore(Float.valueOf("100"));
		pnrEvaKpi.setKpiName("name1");
		pnrEvaKpi.setRemark("test");
		pnrEvaKpi.setRuleGroupId("11");
		pnrEvaKpi.setThreshold(Float.valueOf("100"));
		pnrEvaKpi.setWeight(100);
		pnrEvaKpi.setDefaultValue(100);
		iPnrEvaKpiDao.saveKpi(pnrEvaKpi);
		
		iPnrEvaKpiDao.removeKpi(pnrEvaKpi);
		PnrEvaKpi newPnrEvaKpi = iPnrEvaKpiDao.getKpi(pnrEvaKpi.getId(),"0");
		assertNull(newPnrEvaKpi);
	}
	/**
	 * 保存指标类型
	 */
	public final void testSaveKpi() {
		PnrEvaKpi pnrEvaKpi = new PnrEvaKpi();
		pnrEvaKpi.setAlgorithm("1");
		pnrEvaKpi.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaKpi.setCreator("admin");
		pnrEvaKpi.setCycle("1");
		pnrEvaKpi.setDeleted("0");
		pnrEvaKpi.setEvaScore(Float.valueOf("100"));
		pnrEvaKpi.setKpiName("name1");
		pnrEvaKpi.setRemark("test");
		pnrEvaKpi.setRuleGroupId("11");
		pnrEvaKpi.setThreshold(Float.valueOf("100"));
		pnrEvaKpi.setWeight(100);
		pnrEvaKpi.setDefaultValue(100);
		iPnrEvaKpiDao.saveKpi(pnrEvaKpi);
		assertNotNull(pnrEvaKpi.getId());
	}
	/**
	 * 根据指标Id返回指标名称
	 */
	public final void testId2Name() {
		PnrEvaKpi pnrEvaKpi = new PnrEvaKpi();
		pnrEvaKpi.setAlgorithm("1");
		pnrEvaKpi.setCreateTime(PnrEvaDateUtil.getLocalString());
		pnrEvaKpi.setCreator("admin");
		pnrEvaKpi.setCycle("1");
		pnrEvaKpi.setDeleted("0");
		pnrEvaKpi.setEvaScore(Float.valueOf("100"));
		pnrEvaKpi.setKpiName("name1");
		pnrEvaKpi.setRemark("test");
		pnrEvaKpi.setRuleGroupId("11");
		pnrEvaKpi.setThreshold(Float.valueOf("100"));
		pnrEvaKpi.setWeight(100);
		pnrEvaKpi.setDefaultValue(100);
		iPnrEvaKpiDao.saveKpi(pnrEvaKpi);
		PnrEvaKpi newPnrEvaKpi = iPnrEvaKpiDao.getKpi(pnrEvaKpi.getId());
		assertEquals("name1",newPnrEvaKpi.getKpiName());
		
	}
	/**
	 * 判断指标名称是否唯一
	 */
	public final void testIsunique() {
		PnrEvaTree evaTree = new PnrEvaTree();
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder
		.getInstance().getBean("iPnrEvaTreeMgr");
		// 保存树节点
		evaTree.setNodeId("11111");
		evaTree.setParentNodeId("22222");
		evaTree.setNodeName("name1");
		evaTree.setNodeType(PnrEvaConstants.NODETYPE_KPI);
		evaTree.setIsLock(PnrEvaConstants.UNLOCK);
		evaTree.setLeaf(PnrEvaConstants.NODE_LEAF);
		evaTree.setObjectId("123");
		evaTree.setWeight(100);
		evaTree.setCreateTime(PnrEvaDateUtil.getTimestamp());
		evaTree.setCreateUser("admin");
		evaTree.setCreatArea("39");
		treeMgr.saveTreeNode(evaTree);
		boolean unique = iPnrEvaKpiDao.isunique("name1", "22222").booleanValue();
		assertEquals(false,unique);
	}

}
