package com.boco.eoms.materials.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.model.ContactCompany;

public interface ContactCompanyDao {

	/**
	 * 获取所有的 往来单位（ContactCompany）
	 * 
	 * @param contactCompany
	 * @return List<ContactCompany>
	 */
	@SuppressWarnings("unchecked")
	public abstract List<ContactCompany> getContactCompany();

	/**
	 * 根据id 获取往来单位（ContactCompany）信息
	 * 
	 * @param id
	 * @return ContactCompany
	 */
	public abstract ContactCompany getContactCompany(String id);


	/**
	 * 分页获取往来单位（ContactCompany）信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	@SuppressWarnings("unchecked")
	public abstract Map<Integer, Integer> getContactCompany(
			final Integer curPage, final Integer pageSize, final String whereStr);

	/**
	 * 删除往来单位（ContactCompany）信息
	 * 
	 * @param id
	 */
	public abstract void removeContactCompany(String id);

	/**
	 * 保存往来单位（ContactCompany）信息
	 * 
	 * @param contactCompany
	 */
	public abstract void saveContactCompany(ContactCompany contactCompany);

}