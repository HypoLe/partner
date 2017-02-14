package com.boco.eoms.partner.assess.AssFee.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.assess.AssFee.dao.IFeeDetailDao;
import com.boco.eoms.partner.assess.AssFee.mgr.IFeeDetailMgr;
import com.boco.eoms.partner.assess.AssFee.model.FeeDetail;


/**
 * <p>
 * Title:光缆线路付费信息
 * </p>
 * <p>
 * Description:光缆线路付费信息
 * </p>
 * <p>
 * Tue Nov 23 16:04:36 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class FeeDetailMgrImpl implements IFeeDetailMgr {
 
	private IFeeDetailDao  feeDetailDao;
 	
	public IFeeDetailDao getFeeDetailDao() {
		return this.feeDetailDao;
	}
 	
	public void setFeeDetailDao(IFeeDetailDao feeDetailDao) {
		this.feeDetailDao = feeDetailDao;
	}
 	
    public List getFeeDetails() {
    	return feeDetailDao.getFeeDetails();
    }
    
    public FeeDetail getFeeDetail(final String id) {
    	return feeDetailDao.getFeeDetail(id);
    }
    
    public void saveFeeDetail(FeeDetail feeDetail) {
    	feeDetailDao.saveFeeDetail(feeDetail);
    }
    
    public void removeFeeDetail(final String id) {
    	feeDetailDao.removeFeeDetail(id);
    }
    
    public Map getFeeDetails(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return feeDetailDao.getFeeDetails(curPage, pageSize, whereStr);
	}
	/**
	 * 按条件得到光缆线路付费信息
	 */	
	public List getFeeDetailList( final String whereStr ){
		return feeDetailDao.getFeeDetailList(whereStr);
	}
}