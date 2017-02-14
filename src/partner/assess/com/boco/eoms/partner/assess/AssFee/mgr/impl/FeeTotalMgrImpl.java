package com.boco.eoms.partner.assess.AssFee.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.assess.AssFee.dao.IFeeTotalDao;
import com.boco.eoms.partner.assess.AssFee.mgr.IFeeTotalMgr;
import com.boco.eoms.partner.assess.AssFee.model.FeeTotal;


/**
 * <p>
 * Title:计算结果信息
 * </p>
 * <p>
 * Description:计算结果信息
 * </p>
 * <p>
 * Tue Nov 23 16:04:36 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class FeeTotalMgrImpl implements IFeeTotalMgr {
 
	private IFeeTotalDao  feeTotalDao;
 	
	public IFeeTotalDao getFeeTotalDao() {
		return this.feeTotalDao;
	}
 	
	public void setFeeTotalDao(IFeeTotalDao feeTotalDao) {
		this.feeTotalDao = feeTotalDao;
	}
 	
    public List getFeeTotals() {
    	return feeTotalDao.getFeeTotals();
    }
    
    public FeeTotal getFeeTotal(final String id) {
    	return feeTotalDao.getFeeTotal(id);
    }
    
    public void saveFeeTotal(FeeTotal feeTotal) {
    	feeTotalDao.saveFeeTotal(feeTotal);
    }
    
    public void removeFeeTotal(final String id) {
    	feeTotalDao.removeFeeTotal(id);
    }
    
    public Map getFeeTotals(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return feeTotalDao.getFeeTotals(curPage, pageSize, whereStr);
	}

	/**
	 * 按条件得到计算结果信息
	 */	
	public List getFeeTotalsList( final String whereStr ) {
		return feeTotalDao.getFeeTotalsList(whereStr);
	}
	/**
	 * 按地市，年份得到费用
	 */	
	public FeeTotal getFeeTotalByArea( final String areaId, final String year  ) {
		FeeTotal feeTotal = null;
		List feeTotalList = getFeeTotalsList(" where year = '"+year+"' and areaId='"+areaId+"'");
		if(feeTotalList!=null&&feeTotalList.size()>0){
			feeTotal = (FeeTotal)feeTotalList.get(0);
		}
		return feeTotal;
	}
}