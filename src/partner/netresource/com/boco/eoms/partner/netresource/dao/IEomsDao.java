package com.boco.eoms.partner.netresource.dao;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.boco.eoms.partner.netresource.model.EomsModel;
import com.googlecode.genericdao.search.SearchResult;

/**
 * 
 * 作 者： zhangkeqi
 * 时 间:  Aug 23, 2012-9:42:17 AM
 * 说 明： 本类是对GoogleGenericDao的功能增加，本类虽是泛型类，但可以不当作泛型类
 * 		  使用，结合EomsSearch可以不指定泛型来查找相关表的数据，使用方法参考EomsSearch
 * 		  。
 * 使 用：1：如果某类实现此接口，指定泛型，则在查找数据时，不必向EomsSearch里指定要查找的对
 * 		  象，否则当一般类使用，查找数据时必须指定查找类的Class。这是因为GenericDao解析泛型
 * 		  类型不能动动解析，故用此类来解决。
 * 		2：当在非泛型service注入此dao，或调用dao里的searchAndCount方法的步骤应该如下：
 * 		   如只用searchAndCount方法，则中需调用向EomsSearch里注入查找类型的Class类型
 * 		   即可，若要dao里的所有查询方法都好用，则需要调用setPersistentClass方法来指定要查找的类。
 *         注，一但调用setPersistentClass注入要查找的类型后，dao的默认默认查找就要默认为上一次注入进去的Class。所以建议使用时先指定查找类型。
 * 		  
 * @param <T>
 */
public interface IEomsDao<T extends EomsModel> extends CommonGenericDao<T, String> {
	public <RT> SearchResult<RT> searchAndCount(EomsSearch eomsSearch);
	public Class getPersistentClass();
	public void setPersistentClass(Class persistentClass);
}