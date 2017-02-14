package com.boco.eoms.materials.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.materials.dao.MaterialDao;
import com.boco.eoms.materials.model.Material;
import com.boco.eoms.materials.service.MaterialManager;

/**
 * 材料信息(Material) 业务实现类
 * 
 * @author wanghongliang
 * 
 */
public class MaterialManagerImpl extends BaseManager implements MaterialManager {

	private MaterialDao dao;

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.MaterialManager#setMaterialDao(com.boco.eoms.materials.dao.MaterialDao)
	 */
	public void setMaterialDao(MaterialDao dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.MaterialManager#getMaterial()
	 */
	public List<Material> getMaterial() {
		return dao.getMaterial();
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.MaterialManager#getMaterial(java.lang.String)
	 */
	public Material getMaterial(final String id) {
		return dao.getMaterial(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.MaterialManager#saveMaterial(com.boco.eoms.materials.model.Material)
	 */
	public void saveMaterial(Material material) {
		dao.saveMaterial(material);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.MaterialManager#removeMaterial(java.lang.String)
	 */
	public void removeMaterial(final String id) {
		dao.removeMaterial(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.MaterialManager#getMaterial(java.lang.Integer, java.lang.Integer)
	 */
	public Map<Integer, Integer> getMaterial(final Integer curPage,
			final Integer pageSize) {
		return dao.getMaterial(curPage, pageSize, null);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.MaterialManager#getMaterial(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public Map<Integer, Integer> getMaterial(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		return dao.getMaterial(curPage, pageSize, whereStr);
	}
}
