package com.boco.eoms.partner.home.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.home.dao.MaterialLibDao;
import com.boco.eoms.partner.home.dao.MatlibScopeDeptDao;
import com.boco.eoms.partner.home.mgr.MaterialLibMgr;
import com.boco.eoms.partner.home.model.MaterialLib;
import com.boco.eoms.partner.home.model.MatlibScopeDept;
/**
 * <p>
 * Title:资料库信息
 * </p>
 * <p>
 * Description:资料库信息
 * </p>
 * <p>
 * Sep 3, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class MaterialLibMgrImpl extends CommonGenericServiceImpl<MaterialLib> implements MaterialLibMgr {

	private MaterialLibDao materialLibDao;
	private MatlibScopeDeptDao matlibScopeDeptDao;
	
	public MaterialLibDao getMaterialLibDao() {
		return materialLibDao;
	}
	public void setMaterialLibDao(MaterialLibDao materialLibDao) {
		this.materialLibDao = materialLibDao;
		this.setCommonGenericDao(materialLibDao);
	}
	public MatlibScopeDeptDao getMatlibScopeDeptDao() {
		return matlibScopeDeptDao;
	}
	public void setMatlibScopeDeptDao(MatlibScopeDeptDao matlibScopeDeptDao) {
		this.matlibScopeDeptDao = matlibScopeDeptDao;
	}
	
	/**保存和更新
	 * @param materialLib 主对象
	 * @param matlibScopeDepts materialLib的关系matlibScopeDepts
	 * */
	public void save(MaterialLib materialLib,MatlibScopeDept... matlibScopeDepts){
		materialLibDao.save(materialLib);
		String hql=" delete from MatlibScopeDept where matlibid='"+materialLib.getId()+"'";
		matlibScopeDeptDao.bulkUpdateByHql(hql);
		for (MatlibScopeDept matlibScopeDept : matlibScopeDepts) {
			matlibScopeDept.setMatlibid(materialLib.getId());
		}
		matlibScopeDeptDao.save(matlibScopeDepts);
	}
}

