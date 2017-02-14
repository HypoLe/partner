package com.boco.eoms.commons.sheet.special.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.commons.sheet.special.model.TawSheetSpecial;

/**
 * 
 * @author panlong 下午05:38:50
 */
public interface TawSheetSpecialDao extends Dao {

	/**
	 * Retrieves all of the tawSheetSpecials
	 */
	public List getTawSheetSpecials(TawSheetSpecial tawSheetSpecial);

	/**
	 * Gets tawSheetSpecial's information based on primary key. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param id
	 *            the tawSheetSpecial's id
	 * @return tawSheetSpecial populated tawSheetSpecial object
	 */
	public TawSheetSpecial getTawSheetSpecial(final Integer id);

	/**
	 * Saves a tawSheetSpecial's information
	 * 
	 * @param tawSheetSpecial
	 *            the object to be saved
	 */
	public void saveTawSheetSpecial(TawSheetSpecial tawSheetSpecial);

	/**
	 * Removes a tawSheetSpecial from the database by id
	 * 
	 * @param id
	 *            the tawSheetSpecial's id
	 */
	public void removeTawSheetSpecial(final Integer id);

	/**
	 * ���ڷ�ҳ��ʾ curPage ��ǰҳ�� pageSize ÿҳ��ʾ��
	 */
	public Map getTawSheetSpecials(final Integer curPage, final Integer pageSize);

	public Map getTawSheetSpecials(final Integer curPage,
			final Integer pageSize, final String whereStr);

	/**
	 * 根据专业ID查询专业信息
	 * 
	 * @param areaid
	 * @return
	 */
	public TawSheetSpecial getSpecialByspecialId(String specialid);

	/**
	 * 查询某专业的下一级专业信息
	 * 
	 * @param specialid
	 * @return
	 */
	public List getSonspecialByspecialId(String specialid);

	/**
	 * 查询同级专业信息
	 * 
	 * @param parentspecialid
	 * @param ordercode
	 * @return
	 */
	public List getSameLevelspecial(String parentspecialid, Integer ordercode);

	/**
	 * 查询某专业名称是否存在
	 * 
	 * @param specialname
	 * @return
	 */
	public boolean isExitspecialName(String specialid);

	/**
	 * 查询某专业的所有子专业信息
	 * 
	 * @param specialid
	 * @return
	 */
	public List getAllSonspecialByspecialid(String specialid);

	/**
	 * 通过专业类型获取专业列表
	 * 
	 * @param specialType 专业类型
	 * @return 专业列表
	 */
	public List listSpecialsByType(String specialType);
}
