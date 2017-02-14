package com.boco.eoms.partner.netresource.service;

import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.netresource.model.EomsModel;
import com.googlecode.genericdao.search.SearchResult;

public interface IEomsService<T extends EomsModel> extends CommonGenericService<T> {
	
	/**
	 * 
	 * 方法说明： 解决无法动太创建泛型来查询数据的缺陷
	 * 作   者: zhangkeqi
	 * 创建时间: Aug 22, 2012-5:30:58 PM
	 * @param <RT>
	 * @param eomsSearch
	 * @return
	 */
	public <RT> SearchResult<RT> searchAndCount(EomsSearch eomsSearch);
	public void setPersistentClass(Class persistentClass);
}
