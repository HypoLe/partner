package com.boco.eoms.parter.baseinfo.fee.test.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.test.console.ConsoleTestCase;
import com.boco.eoms.parter.baseinfo.fee.dao.PartnerFeeUnitDao;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeUnit;

public class PartnerFeeUnitDaoTest extends ConsoleTestCase {

	/**
	 * 单位
	 */
	private PartnerFeeUnitDao partnerFeeUnitDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpBeforeTransaction()
	 */
	protected void onSetUpBeforeTransaction() throws Exception {
		super.onSetUpBeforeTransaction();
		this.partnerFeeUnitDao = (PartnerFeeUnitDao) applicationContext
				.getBean("partnerFeeUnitDao");
	}

	public void testGetPartnerFeeUnits() {
		PartnerFeeUnit partnerFeeUnit = new PartnerFeeUnit();
		partnerFeeUnit.setCreateDate(new Date());
		partnerFeeUnit.setCreateDept("1");
		partnerFeeUnit.setCreateUser("admin");
		partnerFeeUnit.setIsDelete("0");
		partnerFeeUnit.setName("中兴");
		partnerFeeUnit.setRemark("测试得到单位列表");
		partnerFeeUnitDao.savePartnerFeeUnit(partnerFeeUnit);
		List list = partnerFeeUnitDao.getPartnerFeeUnits();
		assertTrue(list.size() > 0);
	}

	public void testGetPartnerFeeUnit() {
		PartnerFeeUnit partnerFeeUnit = new PartnerFeeUnit();
		partnerFeeUnit.setCreateDate(new Date());
		partnerFeeUnit.setCreateDept("1");
		partnerFeeUnit.setCreateUser("admin");
		partnerFeeUnit.setIsDelete("0");
		partnerFeeUnit.setName("中兴");
		partnerFeeUnit.setRemark("测试得到单位");
		partnerFeeUnitDao.savePartnerFeeUnit(partnerFeeUnit);

		String unitId = partnerFeeUnit.getId();
		PartnerFeeUnit pfu = partnerFeeUnitDao.getPartnerFeeUnit(unitId + "");
		assertNotNull(pfu);
		assertEquals(pfu.getId(), partnerFeeUnit.getId());
	}

	public void testSavePartnerFeeUnit() {
		PartnerFeeUnit partnerFeeUnit = new PartnerFeeUnit();
		partnerFeeUnit.setCreateDate(new Date());
		partnerFeeUnit.setCreateDept("1");
		partnerFeeUnit.setCreateUser("admin");
		partnerFeeUnit.setIsDelete("0");
		partnerFeeUnit.setName("中兴");
		partnerFeeUnit.setRemark("测试得到单位");
		partnerFeeUnitDao.savePartnerFeeUnit(partnerFeeUnit);

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
		partnerFeeUnitDao.savePartnerFeeUnit(partnerFeeUnit);
		flush();
		String unitId = partnerFeeUnit.getId();

		partnerFeeUnitDao.removePartnerFeeUnit(unitId);
		// FIXME 删除后不可再获取，以其他方式断言是否删除
		// PartnerFeeUnit pfu = partnerFeeUnitDao.getPartnerFeeUnit(unitId);
		// assertNull(pfu.getId());
		// try{
		// partnerFeeUnitDao.getPartnerFeeUnit(unitId);
		// fail("PartnerFeeUnit with identifier '"+unitId+"' found in
		// database");
		// }catch(ObjectRetrievalFailureException e){
		// assertNotNull(e.getMessage());
		// }
	}

	public void testGetPartnerFeeUnitsIntegerIntegerString() {
		PartnerFeeUnit partnerFeeUnit = new PartnerFeeUnit();
		partnerFeeUnit.setCreateDate(new Date());
		partnerFeeUnit.setCreateDept("1");
		partnerFeeUnit.setCreateUser("admin");
		partnerFeeUnit.setIsDelete("0");
		partnerFeeUnit.setName("中兴");
		partnerFeeUnit.setRemark("测试得到单位");
		partnerFeeUnitDao.savePartnerFeeUnit(partnerFeeUnit);
		Map map = partnerFeeUnitDao.getPartnerFeeUnits(new Integer(1),
				new Integer(15), "");
		List result = (List) map.get("result");
		if (result != null) {
			for (Iterator it = result.iterator(); it.hasNext();) {
				PartnerFeeUnit pfu = (PartnerFeeUnit) it.next();
				assertNull(pfu.getId());
				assertEquals(pfu.getCreateUser(), "admin");
				assertEquals(pfu.getCreateDept(), "1");

			}
		}
	}

}
