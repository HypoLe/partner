package com.boco.eoms.materials.service;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.dao.ContactCompanyDao;
import com.boco.eoms.materials.model.ContactCompany;

public interface ContactCompanyManager {

	public abstract void setContactCompanyDao(ContactCompanyDao dao);

	/**
	 * 获取所有的往来单位信息
	 * 
	 * @return List<ContactCompany>
	 */
	public abstract List<ContactCompany> getContactCompany();

	/**
	 * 根据id获取往来单位信息
	 * 
	 * @param id
	 * @return ContactCompany
	 */
	public abstract ContactCompany getContactCompany(final String id);

	/**
	 * 保存往来单位信息
	 * 
	 * @param contactCompany
	 */
	public abstract void saveContactCompany(ContactCompany contactCompany);

	/**
	 * 删除往来单位信息
	 * 
	 * @param id
	 */
	public abstract void removeContactCompany(final String id);

	/**
	 * 分页获取往来单位信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getContactCompany(
			final Integer curPage, final Integer pageSize);

	/**
	 * 分页获取往来单位信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getContactCompany(
			final Integer curPage, final Integer pageSize, final String whereStr);

}