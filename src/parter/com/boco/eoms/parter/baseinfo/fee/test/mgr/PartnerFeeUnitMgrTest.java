package com.boco.eoms.parter.baseinfo.fee.test.mgr;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.AbstractTransactionalSpringContextTests;

import com.boco.eoms.base.test.console.ConsoleTestCase;
import com.boco.eoms.commons.system.role.service.IRoleMgr;
import com.boco.eoms.parter.baseinfo.fee.mgr.PartnerFeeUnitMgr;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeUnit;

public class PartnerFeeUnitMgrTest extends ConsoleTestCase  {
	
	/**
	 * 单位
	 */
	private PartnerFeeUnitMgr partnerFeeUnitMgr;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpBeforeTransaction()
	 */
	protected void onSetUpBeforeTransaction() throws Exception {
		super.onSetUpBeforeTransaction();
		this.partnerFeeUnitMgr = (PartnerFeeUnitMgr) applicationContext.getBean("partnerFeeUnitMgr");
	}
	
	public void testGetPartnerFeeUnits() {
		PartnerFeeUnit partnerFeeUnit = new PartnerFeeUnit();
		partnerFeeUnit.setCreateDate(new Date());
		partnerFeeUnit.setCreateDept("1");
		partnerFeeUnit.setCreateUser("admin");
		partnerFeeUnit.setIsDelete("0");
		partnerFeeUnit.setName("中兴");
		partnerFeeUnit.setRemark("测试得到单位列表");
		partnerFeeUnitMgr.savePartnerFeeUnit(partnerFeeUnit);
		List list = partnerFeeUnitMgr.getPartnerFeeUnits();
		assertTrue(list.size()>0);
	}

	public void testGetPartnerFeeUnit() {
		PartnerFeeUnit partnerFeeUnit = new PartnerFeeUnit();
		partnerFeeUnit.setCreateDate(new Date());
		partnerFeeUnit.setCreateDept("1");
		partnerFeeUnit.setCreateUser("admin");
		partnerFeeUnit.setIsDelete("0");
		partnerFeeUnit.setName("中兴");
		partnerFeeUnit.setRemark("测试得到单位");
		partnerFeeUnitMgr.savePartnerFeeUnit(partnerFeeUnit);
		
		String unitId = partnerFeeUnit.getId();
		PartnerFeeUnit pfu = partnerFeeUnitMgr.getPartnerFeeUnit(unitId +"");
		assertNotNull(pfu);
		assertEquals(pfu.getId(),partnerFeeUnit.getId());
	}

	public void testSavePartnerFeeUnit() {
		PartnerFeeUnit partnerFeeUnit = new PartnerFeeUnit();
		partnerFeeUnit.setCreateDate(new Date());
		partnerFeeUnit.setCreateDept("1");
		partnerFeeUnit.setCreateUser("admin");
		partnerFeeUnit.setIsDelete("0");
		partnerFeeUnit.setName("中兴");
		partnerFeeUnit.setRemark("测试得到单位");
		partnerFeeUnitMgr.savePartnerFeeUnit(partnerFeeUnit);
		
		assertNotNull(partnerFeeUnit.getId());
	}

	public void testRemovePartnerFeeUnit() {
		PartnerFeeUnit partnerFeeUnit = new PartnerFeeUnit();
		partnerFeeUnit.setCreateDate(new Date());
		partnerFeeUnit.setCreateDept("1");
		partnerFeeUnit.setCreateUser("admin");
		partnerFeeUnit.setIsDelete("0");
		partnerFeeUnit.setName("中兴");
		partnerFeeUnit.setRemark("测试得到单位");
		partnerFeeUnitMgr.savePartnerFeeUnit(partnerFeeUnit);
		
		String unitId=partnerFeeUnit.getId();
		partnerFeeUnitMgr.removePartnerFeeUnit(unitId);
		PartnerFeeUnit pfu = partnerFeeUnitMgr.getPartnerFeeUnit(unitId);
		assertNull(pfu.getId());
//		try{
//			partnerFeeUnitMgr.getPartnerFeeUnit(unitId);
//			fail("PartnerFeeUnit with identifier '"+unitId+"' found in database");
//		}catch(ObjectRetrievalFailureException e){
//			assertNotNull(e.getMessage());
//		}
	}

	public void testGetPartnerFeeUnitsIntegerIntegerString() {
		PartnerFeeUnit partnerFeeUnit = new PartnerFeeUnit();
		partnerFeeUnit.setCreateDate(new Date());
		partnerFeeUnit.setCreateDept("1");
		partnerFeeUnit.setCreateUser("admin");
		partnerFeeUnit.setIsDelete("0");
		partnerFeeUnit.setName("中兴");
		partnerFeeUnit.setRemark("测试得到单位");
		partnerFeeUnitMgr.savePartnerFeeUnit(partnerFeeUnit);
		Map map = partnerFeeUnitMgr.getPartnerFeeUnits(new Integer(1), new Integer(15), "");
		List result = (List)map.get("result");
		if (result != null) {
			for (Iterator it = result.iterator(); it.hasNext();) {
				PartnerFeeUnit pfu=(PartnerFeeUnit)it.next();
				assertNull(pfu.getId());
				assertEquals(pfu.getCreateUser(), "admin");
				assertEquals(pfu.getCreateDept(),"1" );
				
			}
		}
	}
}
