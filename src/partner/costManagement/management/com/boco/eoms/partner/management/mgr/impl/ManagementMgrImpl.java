package com.boco.eoms.partner.management.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.management.model.Management;
import com.boco.eoms.partner.management.mgr.IManagementMgr;
import com.boco.eoms.partner.management.dao.IManagementDao;

/**
 * <p>
 * Title:管理成本
 * </p>
 * <p>
 * Description:管理成本
 * </p>
 * <p>
 * Wed Mar 28 09:29:15 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public class ManagementMgrImpl implements IManagementMgr {
 
    private IManagementDao  managementDao;

    public IManagementDao getManagementDao() {
        return this.managementDao;
    }

    public void setManagementDao(IManagementDao managementDao) {
        this.managementDao = managementDao;
    }

    /**
     * 
     * @see com.boco.eoms.partner.management.IManagementMgr#getManagements()
     *      
     */
    public List getManagements() {
        return managementDao.getManagements();
    }

    /**
     * 
     * @see com.boco.eoms.partner.management.IManagementMgr#getManagement(java.lang.String)
     *      
     */
    public Management getManagement(final String id) {
        return managementDao.getManagement(id);
    }

    /**
     * 
     * @see com.boco.eoms.partner.management.IManagementMgr#saveManagement(com.boco.eoms.partner.management.Management)
     *      
     */
    public void saveManagement(Management management) {
        managementDao.saveManagement(management);
    }

    /**
     * 
     * @see com.boco.eoms.partner.management.IManagementMgr#removeManagement(java.lang.String)
     *      
     */
    public void removeManagement(final String id) {
        managementDao.removeManagement(id);
    }

    /**
     * 
     * @see com.boco.eoms.partner.management.IManagementMgr#removeManagement(java.lang.String[])
     *      
     */
    public void removeManagement(final String[] ids) {
        if (null != ids) {
            for (int i = 0; i < ids.length; i++) {
                this.removeManagement(ids[i]);
            }
        }
    }

    /**
     * 
     * @see com.boco.eoms.partner.management.IManagementMgr#getManagements(java.lang.Integer,java.lang.Integer,java.lang.String)
     *      
     */
    public Map getManagements(final Integer curPage, final Integer pageSize,
            final String whereStr) {
        return managementDao.getManagements(curPage, pageSize, whereStr);
    }
}