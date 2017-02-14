package com.boco.eoms.partner.personnel.dao.impl;

import java.util.Map;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.personnel.dao.CertificateDao;
import com.boco.eoms.partner.personnel.model.Certificate;
	/**
 * <p>
 * Title:人员证书管理
 * </p>
 * <p>
 * Description:人员证书管理
 * </p>
 * <p>
 * Jul 10, 2012 10:41:10 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class CertificateDaoImpl extends CommonGenericDaoImpl<Certificate,String> implements CertificateDao {

		public Map getPartnerCertStatisticsList(Integer curPage,Integer pageSize, String whereStr) {
			
			return null;
		}
}
