package com.boco.eoms.materials.service;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.dao.BillMaterialDao;
import com.boco.eoms.materials.model.BillMaterial;

public interface BillMaterialManager {

	public abstract void setBillMaterialDao(BillMaterialDao dao);

	/**
	 * 获取所有的详细货品信息
	 * 
	 * @return List<BillMaterial>
	 */
	public abstract List<BillMaterial> getBillMaterial();

	public abstract void updateBillMaterial(BillMaterial billMaterial);
	
	/**
	 * 根据 单据id 查询
	 * 
	 * @return List<BillMaterial>
	 */
	public abstract List<BillMaterial> getBillMateralByBillId(String billId);
	
	/**
	 * 根据id获取详细货品信息
	 * 
	 * @param id
	 * @return BillMaterial
	 */
	public abstract BillMaterial getBillMaterial(final String id);

	/**
	 * 保存详细货品信息
	 * 
	 * @param billMaterial
	 */
	public abstract void saveBillMaterial(BillMaterial billMaterial);

	/**
	 * 删除详细货品信息
	 * 
	 * @param id
	 */
	public abstract void removeBillMaterial(final String id);
	
	/**
	 * 删除返回后的商品列表
	 * 
	 * @param id
	 */
	public abstract void removeBillMaterialall(final String id);

	/**
	 * 分页获取详细货品信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getBillMaterial(
			final Integer curPage, final Integer pageSize);

	/**
	 * 分页获取详细货品信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getBillMaterial(
			final Integer curPage, final Integer pageSize, final String whereStr);

}