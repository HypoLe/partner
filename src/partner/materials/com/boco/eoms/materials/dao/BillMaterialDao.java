package com.boco.eoms.materials.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.model.BillMaterial;

public interface BillMaterialDao {

	/**
	 * 获取所有的 单据中详细货品信息表(出入库中间表BillMaterial)
	 * @return List<BillMaterial>
	 */
	@SuppressWarnings("unchecked")
	public abstract List<BillMaterial> getBillMaterial();
	public  abstract void updateBillMaterial(BillMaterial billMaterial);
	/**
	 * 根据 单据id 查询
	 * 
	 * @return List<BillMaterial>
	 */
	public abstract List<BillMaterial> getBillMateralByBillId(String billId);
	
	/**
	 * 根据id  获取 BillMaterial单据中详细货品信息表(出入库中间表)
	 * 
	 * @param id
	 * @return BillMaterial
	 */
	public abstract BillMaterial getBillMaterial(String id);

	/**
	 * 分页获取 出入库中间表信息（BillMaterial）
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	@SuppressWarnings("unchecked")
	public abstract Map<Integer, Integer> getBillMaterial(
			final Integer curPage, final Integer pageSize, final String whereStr);

	/**
	 * 根据id  删除BillMaterial单据中详细货品信息表(出入库中间表)
	 * 
	 * @param id
	 */
	public abstract void removeBillMaterial(String id);
	
	public abstract void removeBillMaterialall(String id);

	/**
	 * 保存 BillMaterial 单据中详细货品信息表(出入库中间表)
	 * 
	 * @param billMaterial
	 */
	public abstract void saveBillMaterial(BillMaterial billMaterial);
	


}