package com.boco.eoms.materials.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.materials.dao.BillMaterialDao;
import com.boco.eoms.materials.model.BillMaterial;
import com.boco.eoms.materials.service.BillMaterialManager;

/**
 * 详细货品信息(出入库中间表BillMaterial) 业务实现类
 * 
 * @author wanghongliang
 * 
 */
public class BillMaterialManagerImpl extends BaseManager implements BillMaterialManager {

	private BillMaterialDao dao;

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.BillMaterialManager#setBillMaterialDao(com.boco.eoms.materials.dao.BillMaterialDao)
	 */
	public void setBillMaterialDao(BillMaterialDao dao) {
		this.dao = dao;
	}
	public  void updateBillMaterial(BillMaterial billMaterial){
		dao.updateBillMaterial(billMaterial);
	}
	
	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.BillMaterialManager#getBillMaterial()
	 */
	public List<BillMaterial> getBillMaterial() {
		return dao.getBillMaterial();
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.BillMaterialManager#getBillMaterial(java.lang.String)
	 */
	public BillMaterial getBillMaterial(final String id) {
		return dao.getBillMaterial(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.BillMaterialManager#saveBillMaterial(com.boco.eoms.materials.model.BillMaterial)
	 */
	public void saveBillMaterial(BillMaterial billMaterial) {
		dao.saveBillMaterial(billMaterial);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.BillMaterialManager#removeBillMaterial(java.lang.String)
	 */
	public void removeBillMaterial(final String id) {
		dao.removeBillMaterial(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.BillMaterialManager#getBillMaterial(java.lang.Integer, java.lang.Integer)
	 */
	public Map<Integer, Integer> getBillMaterial(final Integer curPage,
			final Integer pageSize) {
		return dao.getBillMaterial(curPage, pageSize, null);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.BillMaterialManager#getBillMaterial(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public Map<Integer, Integer> getBillMaterial(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		return dao.getBillMaterial(curPage, pageSize, whereStr);
	}

	public List<BillMaterial> getBillMateralByBillId(String billId) {
		// TODO Auto-generated method stub
		return dao.getBillMateralByBillId(billId);
	}
	public void removeBillMaterialall(String id) {
		dao.removeBillMaterialall(id);
	}
}
