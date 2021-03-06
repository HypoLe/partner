package com.boco.eoms.commons.system.dict.dao;

// java standard library
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// eoms classes
import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;

public interface ITawSystemDictTypeDao extends Dao {

	/**
	 * Retrieves all of the tawSystemDictTypes
	 */
	public List getTawSystemDictTypes(TawSystemDictType tawSystemDictType);

	/**
	 * Gets tawSystemDictType's information based on primary key. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param id
	 *            the tawSystemDictType's id
	 * @return tawSystemDictType populated tawSystemDictType object
	 */
	public TawSystemDictType getTawSystemDictType(final Integer id);
	/*
	 * id2name，即字典id转为字典名称 added by qinmin
	 * 
	 * @see com.boco.eoms.base.dao.ID2NameDAO#id2Name(java.lang.String)
	 */
	public String id2Name(final String id) throws DictDAOException;
	/**
	 * Saves a tawSystemDictType's information
	 * 
	 * @param tawSystemDictType
	 *            the object to be saved
	 */
	public void saveTawSystemDictType(TawSystemDictType tawSystemDictType);

	/**
	 * Removes a tawSystemDictType from the database by id
	 * 
	 * @param id
	 *            the tawSystemDictType's id
	 */
	public void removeTawSystemDictType(final Integer id);

	/**
	 * Get current dict type's parent type name
	 * 
	 * @param String
	 *            _strCurCode
	 * @return String parent type name
	 */
	public String getParentTypeName(String _strCurCode);

	/**
	 * 
	 */
	public TawSystemDictType getDictByDictId(String _strDictId);
	public List getworkplanDictByDictId();

	/**
	 * 查询下一级信息
	 * 
	 * @param parentdictid
	 * @return
	 */
	public ArrayList getDictSonsByDictid(String parentdictid);

	/**
	 * 根据字id查询字典信息
	 * 
	 * @param dictid
	 * @return
	 */
	public TawSystemDictType getDictTypeByDictid(String dictid);

	/**
	 * 判断是否有相同级别的字典类型
	 * 
	 * @param systype
	 * @return
	 */
	public boolean isHaveSameLevel(String parentdictid, String systype);

	/**
	 * 查询code的字典信息
	 * 
	 * @param code
	 * @return
	 */
	public List getDictByCode(String code);

	/**
	 * 保存字典信息并返回dictId
	 * 
	 * @param tawSystemDictType
	 * @return String dictId
	 * @author liqiuye 2007-11-14
	 */
	public String saveTawSystemDictTypeReturnDictId(
			TawSystemDictType tawSystemDictType);

	/**
	 * 根据 parentDictId 和 dictCode获取 TawSystemDictType
	 * 
	 * @param tawSystemDictType
	 * @param tawSystemDictCode
	 * @return String dictId
	 * @author liqiuye 2007-11-14
	 */
	public TawSystemDictType getDictByDictType(String dictCode,
			String parentDictId);

	/**
	 * 根据 dictCode获取 TawSystemDictType
	 * 
	 * @param tawSystemDictCode
	 * @return String dictId
	 * @author liqiuye 2007-11-14
	 */
	public TawSystemDictType getDictByDictType(String dictCode);

	/**
	 * 判断此code的字典项是否存在(修改时除自己本身)
	 * 
	 * @param dictCode
	 * @param dictId
	 * @return
	 */
	public boolean isCodeExist(String dictCode, String dictId);

	/**
	 * 根据code取得字典Id
	 * 
	 * @param dict
	 * @return
	 */
	public String getDictIdByDictCode(String dictCode);

	public TawSystemDictType getDictType(int dictId, String dictType)
			throws SQLException;

	/**
	 * 根据code取得字典Id
	 * 
	 * @param dict
	 * @return
	 */
	public String getDictIdByDictCode(String parentId, String dictCode);

	/**
	 * 取父字典下的子节典，并且子节点必须属于dictIds（id集合）的
	 * 
	 * @param parentDictId
	 *            父节点
	 * @param dictIds
	 *            id集合
	 * @return 父字典下的子节典，并且子节点必须属于dictIds（id集合）的所有字典项
	 */
	public List listDictInDictIds(String parentDictId, String dictIds);
	
}
