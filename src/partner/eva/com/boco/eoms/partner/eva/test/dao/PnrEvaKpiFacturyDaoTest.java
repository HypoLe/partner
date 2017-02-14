package com.boco.eoms.partner.eva.test.dao;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.boco.eoms.base.test.console.ConsoleTestCase;
import com.boco.eoms.partner.eva.dao.IPnrEvaKpiFacturyDao;
import com.boco.eoms.partner.eva.model.PnrEvaKpiFactury;

public class PnrEvaKpiFacturyDaoTest extends ConsoleTestCase {

	private IPnrEvaKpiFacturyDao iPnrEvaKpiFacturyDao;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpBeforeTransaction()
	 */
	protected void onSetUpBeforeTransaction() throws Exception {
		this.setTransactionManager((PlatformTransactionManager)getBean("transactionManager"));
		this.setDefaultRollback(true);
		super.onSetUpBeforeTransaction();
		this.iPnrEvaKpiFacturyDao = (IPnrEvaKpiFacturyDao)getBean("pnrEvaKpiFacturyDao");
	}
	
	public final void testGetAllKpiFactury() {
		PnrEvaKpiFactury pnrEvaKpiFactury1 = new PnrEvaKpiFactury();
		pnrEvaKpiFactury1.setFacturyName("facturyName1");
		pnrEvaKpiFactury1.setNodeId("101");
		pnrEvaKpiFactury1.setTemplateId("11111");
		pnrEvaKpiFactury1.setFactury("123123");
		iPnrEvaKpiFacturyDao.saveKpiFactury(pnrEvaKpiFactury1);
		
		PnrEvaKpiFactury pnrEvaKpiFactury2 = new PnrEvaKpiFactury();
		pnrEvaKpiFactury2.setFacturyName("facturyName2");
		pnrEvaKpiFactury2.setNodeId("101");
		pnrEvaKpiFactury2.setTemplateId("11111");
		pnrEvaKpiFactury2.setFactury("123123");
		iPnrEvaKpiFacturyDao.saveKpiFactury(pnrEvaKpiFactury2);
		
		List facturyList = iPnrEvaKpiFacturyDao.getAllKpiFactury("11111");
		assertEquals(2, facturyList.size());
	}

	public final void testGetAllKpiFacturyByNodeId() {
		PnrEvaKpiFactury pnrEvaKpiFactury1 = new PnrEvaKpiFactury();
		pnrEvaKpiFactury1.setFacturyName("facturyName1");
		pnrEvaKpiFactury1.setNodeId("101");
		pnrEvaKpiFactury1.setTemplateId("11111");
		pnrEvaKpiFactury1.setFactury("123123");
		iPnrEvaKpiFacturyDao.saveKpiFactury(pnrEvaKpiFactury1);
		
		PnrEvaKpiFactury pnrEvaKpiFactury2 = new PnrEvaKpiFactury();
		pnrEvaKpiFactury2.setFacturyName("facturyName2");
		pnrEvaKpiFactury2.setNodeId("101");
		pnrEvaKpiFactury2.setTemplateId("11111");
		pnrEvaKpiFactury2.setFactury("123123");
		iPnrEvaKpiFacturyDao.saveKpiFactury(pnrEvaKpiFactury2);
		
		List facturyList = iPnrEvaKpiFacturyDao.getAllKpiFacturyByNodeId("101");
		assertEquals(2, facturyList.size());
	}

	public final void testRemoveKpiFactury() {
		PnrEvaKpiFactury pnrEvaKpiFactury = new PnrEvaKpiFactury();
		pnrEvaKpiFactury.setFacturyName("facturyName");
		pnrEvaKpiFactury.setNodeId("101");
		pnrEvaKpiFactury.setTemplateId("11111");
		pnrEvaKpiFactury.setFactury("123123");
		iPnrEvaKpiFacturyDao.saveKpiFactury(pnrEvaKpiFactury);

		iPnrEvaKpiFacturyDao.removeKpiFactury(pnrEvaKpiFactury);
		try {
			PnrEvaKpiFactury newPnrEvaKpiFactury = iPnrEvaKpiFacturyDao.getKpiFactury(pnrEvaKpiFactury.getId());
		} catch (ObjectRetrievalFailureException e) {
			assertNotNull(e.getMessage());
		}
	}

	public final void testSaveKpiFactury() {
		PnrEvaKpiFactury pnrEvaKpiFactury = new PnrEvaKpiFactury();
		pnrEvaKpiFactury.setFacturyName("facturyName");
		pnrEvaKpiFactury.setNodeId("101");
		pnrEvaKpiFactury.setTemplateId("11111");
		pnrEvaKpiFactury.setFactury("123123");
		iPnrEvaKpiFacturyDao.saveKpiFactury(pnrEvaKpiFactury);
		
		PnrEvaKpiFactury newPnrEvaKpiFactury = iPnrEvaKpiFacturyDao.getKpiFactury(pnrEvaKpiFactury.getId());
		assertNotNull(newPnrEvaKpiFactury.getId());
	}

	public final void testUpdateKpiFactury() {
		PnrEvaKpiFactury pnrEvaKpiFactury = new PnrEvaKpiFactury();
		pnrEvaKpiFactury.setFacturyName("facturyName");
		pnrEvaKpiFactury.setNodeId("101");
		pnrEvaKpiFactury.setTemplateId("11111");
		pnrEvaKpiFactury.setFactury("123123");
		iPnrEvaKpiFacturyDao.saveKpiFactury(pnrEvaKpiFactury);
		pnrEvaKpiFactury.setFacturyName("newFacturyName");
		iPnrEvaKpiFacturyDao.updateKpiFactury(pnrEvaKpiFactury);
		PnrEvaKpiFactury newPnrEvaKpiFactury = iPnrEvaKpiFacturyDao.getKpiFactury(pnrEvaKpiFactury.getId());
		assertEquals("newFacturyName", newPnrEvaKpiFactury.getFacturyName());
	}

	public final void testGetKpiFactury() {
		PnrEvaKpiFactury pnrEvaKpiFactury = new PnrEvaKpiFactury();
		pnrEvaKpiFactury.setFacturyName("facturyName");
		pnrEvaKpiFactury.setNodeId("101");
		pnrEvaKpiFactury.setTemplateId("11111");
		pnrEvaKpiFactury.setFactury("123123");
		iPnrEvaKpiFacturyDao.saveKpiFactury(pnrEvaKpiFactury);
		
		PnrEvaKpiFactury newPnrEvaKpiFactury = iPnrEvaKpiFacturyDao.getKpiFactury(pnrEvaKpiFactury.getId());
		assertNotNull(newPnrEvaKpiFactury.getId());
	}

	public final void testGetKpiFacturyByFactury() {
		PnrEvaKpiFactury pnrEvaKpiFactury = new PnrEvaKpiFactury();
		pnrEvaKpiFactury.setFacturyName("facturyName");
		pnrEvaKpiFactury.setNodeId("101");
		pnrEvaKpiFactury.setTemplateId("11111");
		pnrEvaKpiFactury.setFactury("123123");
		iPnrEvaKpiFacturyDao.saveKpiFactury(pnrEvaKpiFactury);
		
		PnrEvaKpiFactury newPnrEvaKpiFactury = iPnrEvaKpiFacturyDao.getKpiFacturyByFactury("123123");
		assertNotNull(newPnrEvaKpiFactury.getId());
	}

}
