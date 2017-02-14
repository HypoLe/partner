package com.boco.eoms.partner.netresource.service.impl;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.netresource.dao.IEomsDao;
import com.boco.eoms.partner.netresource.dao.hibernate.EomsDaoHibernateImpl;
import com.boco.eoms.partner.netresource.model.EomsModel;
import com.boco.eoms.partner.netresource.service.IEomsService;
import com.googlecode.genericdao.search.SearchResult;

/**
 * 说 明： 1：目前该类供网络资源使用，故此泛型约定为<T extends EomsModel>
 * 			 其实可以去除这个就可以通用，但是没有去除。指类了泛型类型的子类使用此类的所有方法均好用，
 * 			但真接用该类查找数据时，必须先调用该类的setPersistentClass方法设置查找类型，否则所有查询方法均会报错（若用EomsSearch类并指定了Class则不需要，因见），这是使用规则。
 * 作 者： zhangkeqi
 * 时 间： 2012-08-22 17：35：00
 * @param <T>
 */
public class EomsServiceImpl<T extends EomsModel> extends CommonGenericServiceImpl<T>
		implements IEomsService<T> {

	private IEomsDao<T> eomsDao;
	
	public void setEomsDao(IEomsDao<T> eomsDao) {
		this.setCommonGenericDao(eomsDao);
		this.eomsDao = eomsDao;
	}
	public IEomsDao getEomsDao() {
		return eomsDao;
	}
	
	/**
	 * 
	 * 方法说明：利用现有的dao构建一个新的dao，使用同一个sessionFactory，不建议使用了。
	 * 作   者: zhangkeqi
	 * 创建时间: Aug 23, 2012-11:13:50 AM
	 * @param <BT>
	 * @param <ID>
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private <BT, ID> EomsDaoHibernateImpl newGenericDaoByteType(Class bean) {
		EomsDaoHibernateImpl dao = new EomsDaoHibernateImpl();
		dao.setPersistentClass(bean);
		dao.setSessionFactory(((CommonGenericDao)this.getGenericDAO()).getSessionFactory());
		return dao;
	}

	public <RT> SearchResult<RT> searchAndCount(EomsSearch eomsSearch) {
		SearchResult<RT> result = null;
		Class clazz = eomsSearch.getSearchClass();
		if(clazz != null) {
			this.setPersistentClass(clazz);
			result = eomsDao.searchAndCount(eomsSearch);
		} else {
			//不使用下需的方法了（不够完美），原因很简单，只要遵循规则，就好了，即eomsSearch中必须设置Class，否则就报错。
//			IEomsDao dao  = (EomsDaoHibernateImpl)this.newGenericDaoByteType(clazz);
//			this.setPersistentClass(clazz);
			result = eomsDao.searchAndCount(eomsSearch);
		}
		return result;
	}

	public void setPersistentClass(Class persistentClass) {
		eomsDao.setPersistentClass(persistentClass);
	}
}
