package com.boco.eoms.parter.baseinfo.fee.dao;

import com.boco.eoms.base.dao.Dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeAudit;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeePlan;

/**
 * <p>
 * Title:费用管理审核
 * </p>
 * <p>
 * Description:费用管理审核
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public interface PartnerFeeAuditDao extends Dao {

	/**
	 *
	 * 取费用管理审核列表
	 * @return 返回费用管理审核列表
	 */
	public List getPartnerFeeAudits(final String feeId,final String type);
	/**
	 *
	 * 取某人的待审核列表
	 * @return 返回取某人的待审核列表
	 */
	public Map getPartnerFeeUnAudits(final Integer curPage,final Integer pageSize,final String userId,final String deptId,final String type);
  
	public PartnerFeeAudit getPartnerFeeAuditByState(final String feeId,final String type,final String state); 
	/**
	 * 根据主键查询费用管理审核信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public PartnerFeeAudit getPartnerFeeAudit(final String id);
    
	/**
	 * 保存费用管理审核信息
	 * @param partnerFeeAudit 费用管理审核信息
	 */
	public void savePartnerFeeAudit(PartnerFeeAudit partnerFeeAudit);
}