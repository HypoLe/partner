package com.boco.eoms.netresource.modify.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.netresource.modify.dao.IModifyDao;
import com.boco.eoms.netresource.modify.mgr.IModifyMgr;
import com.boco.eoms.netresource.modify.model.Modify;

/**
 * <p>
 * Title:资源变更管理
 * </p>
 * <p>
 * Description:资源变更管理
 * </p>
 * <p>
 * Tue Feb 21 11:40:03 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public class ModifyMgrImpl implements IModifyMgr {
 
    private IModifyDao  modifyDao;

    public IModifyDao getModifyDao() {
        return this.modifyDao;
    }

    public void setModifyDao(IModifyDao modifyDao) {
        this.modifyDao = modifyDao;
    }

    /**
     * 
     * @see com.boco.eoms.netresource.modify.IModifyMgr#getModifys()
     *      
     */
    public List getModifys() {
        return modifyDao.getModifys();
    }

    /**
     * 
     * @see com.boco.eoms.netresource.modify.IModifyMgr#getModify(java.lang.String)
     *      
     */
    public Modify getModify(final String id) {
        return modifyDao.getModify(id);
    }

    /**
     * 
     * @see com.boco.eoms.netresource.modify.IModifyMgr#saveModify(com.boco.eoms.netresource.modify.Modify)
     *      
     */
    public void saveModify(Modify modify) {
        modifyDao.saveModify(modify);
    }

    /**
     * 
     * @see com.boco.eoms.netresource.modify.IModifyMgr#removeModify(java.lang.String)
     *      
     */
    public void removeModify(final String id) {
        modifyDao.removeModify(id);
    }

    /**
     * 
     * @see com.boco.eoms.netresource.modify.IModifyMgr#removeModify(java.lang.String[])
     *      
     */
    public void removeModify(final String[] ids) {
        if (null != ids) {
            for (int i = 0; i < ids.length; i++) {
                this.removeModify(ids[i]);
            }
        }
    }

    /**
     * 
     * @see com.boco.eoms.netresource.modify.IModifyMgr#getModifys(java.lang.Integer,java.lang.Integer,java.lang.String)
     *      
     */
    public Map getModifys(final Integer curPage, final Integer pageSize,
            final String whereStr) {
        return modifyDao.getModifys(curPage, pageSize, whereStr);
    }

}