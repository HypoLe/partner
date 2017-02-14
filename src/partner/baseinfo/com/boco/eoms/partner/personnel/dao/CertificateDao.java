package com.boco.eoms.partner.personnel.dao;

import java.util.Map;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.partner.personnel.model.Certificate;
	/**
 * <p>
 * Title:人员证书管理
 * </p>
 * <p>
 * Description:人员证书管理
 * </p>
 * <p>
 * Jul 10, 2012 10:38:32 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public interface CertificateDao extends CommonGenericDao<Certificate,String> {

		Map getPartnerCertStatisticsList(Integer curPage, Integer pageSize,String whereStr);

}
