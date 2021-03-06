package com.boco.eoms.workplan.dao.hibernate;

import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.workplan.dao.ITawwpExecuteCheckDao;
import com.boco.eoms.workplan.model.TawwpExecuteCheck;

public class TawwpExecuteCheckDaoHibernate extends BaseDaoHibernate implements
		ITawwpExecuteCheckDao {

	/**
	 * 保存执行作业计划检查
	 * 
	 * @param _tawwpExecuteCheck
	 *            TawwpExecuteCheck 执行作业计划检查类
	 * @throws Exception
	 *             操作异常
	 */
	public void saveExecuteCheck(TawwpExecuteCheck _tawwpExecuteCheck) {
		getHibernateTemplate().save(_tawwpExecuteCheck);
	}

	/**
	 * 删除执行作业计划检查
	 * 
	 * @param _tawwpExecuteCheck
	 *            TawwpExecuteCheck 执行作业计划检查类
	 * @throws Exception
	 *             操作异常
	 */
	public void deleteExecuteCheck(TawwpExecuteCheck _tawwpExecuteCheck) {
		getHibernateTemplate().delete(_tawwpExecuteCheck);

	}

	/**
	 * 修改执行作业计划检查
	 * 
	 * @param _tawwpExecuteCheck
	 *            TawwpExecuteCheck 执行作业计划检查类
	 * @throws Exception
	 *             操作异常
	 */
	public void updateExecuteCheck(TawwpExecuteCheck _tawwpExecuteCheck) {
		getHibernateTemplate().update(_tawwpExecuteCheck);
	}

	/**
	 * 查询执行作业计划检查信息
	 * 
	 * @param id
	 *            String 执行作业计划检查标识
	 * @throws Exception
	 *             操作异常
	 * @return TawwpExecuteCheck 执行作业计划检查类
	 */
	public TawwpExecuteCheck loadExecuteCheck(String executeId) {
		TawwpExecuteCheck tawwpExecuteCheck = new TawwpExecuteCheck();
		String hSql = "";
		hSql = "from TawwpExecuteCheck as tawwpExecuteCheck where tawwpExecuteCheck.executeId='"+executeId+"'";

		List result = getHibernateTemplate().find(hSql);
		if (result != null && result.size() > 0) {
			tawwpExecuteCheck = (TawwpExecuteCheck) result.get(0);
		}
		return tawwpExecuteCheck;
	}

}
